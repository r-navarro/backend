package com.rna.user

import com.mongodb.client.result.DeleteResult
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Property
import io.micronaut.security.authentication.providers.PasswordEncoder
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.annotation.PostConstruct

@Context
class UsersService(private val usersRepository: UsersRepository, private val passwordEncoder: PasswordEncoder, @Property(name = "admin.password") val adminPassword: String) {

    fun save(user: User): Single<User> {
        val observable = BehaviorSubject.create<User>()
        usersRepository.find(user.name)
                .subscribe(
                        { observable.onError(RuntimeException("User : ${user.name} already exist")) },
                        { save(user, observable) }
                )
        return observable.singleOrError()
    }

    fun delete(name: String): Single<DeleteResult> {
        val observable = BehaviorSubject.create<DeleteResult>()
        usersRepository.find(name)
                .subscribe(
                        { delete(name, observable) },
                        { observable.onError(RuntimeException("User : $name doesn't exist")) }
                )
        return observable.singleOrError()
    }

    fun update(name: String, user: User): Single<User> {
        val observable = BehaviorSubject.create<User>()
        usersRepository.find(name)
                .subscribe(
                        {
                            user.name = name
                            update(user, observable)
                        },
                        { observable.onError(RuntimeException("User : $name doesn't exist")) }
                )
        return observable.singleOrError()
    }

    private fun save(user: User, observable: BehaviorSubject<User>) {
        usersRepository.save(user.getEncryptedUser(passwordEncoder)).subscribe(
                { createdUser ->
                    observable.onNext(createdUser)
                    observable.onComplete()
                },
                { createError -> observable.onError(createError) })
    }

    private fun update(user: User, observable: BehaviorSubject<User>) {
        usersRepository.update(user).subscribe(
                { createdUser ->
                    observable.onNext(createdUser)
                    observable.onComplete()
                },
                { createError -> observable.onError(createError) })
    }

    private fun delete(user: String, observable: BehaviorSubject<DeleteResult>) {
        usersRepository.delete(user).subscribe(
                { deletedUser ->
                    observable.onNext(deletedUser)
                    observable.onComplete()
                },
                { deletedError -> observable.onError(deletedError) })
    }

    fun findAll(): Single<List<User>> {
        return usersRepository.list()
    }

    fun search(name: String): Single<User> {
        return usersRepository.find(name)
    }

    fun searchRemovePassword(name: String): Single<User> {
        return usersRepository.find(name).map {
            it.password = ""
            return@map it
        }
    }

    @PostConstruct
    fun createAdmin() {
        save(User("admin", "admin@", adminPassword, listOf("ROLE_ADMIN"))).onErrorReturnItem(User()).blockingGet()
    }
}
