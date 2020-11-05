package com.rna.user

import com.mongodb.client.result.DeleteResult
import com.rna.security.PasswordEncoder
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Property
import io.reactivex.Single
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct

@Context
class UsersService(private val usersRepository: UsersRepository, private val passwordEncoder: PasswordEncoder, @Property(name = "admin.password") val adminPassword: String) {

    private val log = LoggerFactory.getLogger(UsersService::class.java)

    fun save(user: User): Single<User> {
        return usersRepository.exist(user.name)
                .flatMap {
                    if (it) {
                        Single.error(RuntimeException("User : ${user.name} already exist"))
                    } else {
                        usersRepository.save(user.getEncryptedUser(passwordEncoder))
                    }
                }
    }

    fun delete(name: String): Single<DeleteResult> {
        return usersRepository.exist(name)
                .flatMap {
                    if (it) {
                        usersRepository.delete(name)
                    } else {
                        Single.error(RuntimeException("User : $name doesn't exist"))
                    }
                }
    }

    fun update(name: String, user: User): Single<User> {
        return usersRepository.exist(name)
                .flatMap {
                    if (it) {
                        user.name = name
                        usersRepository.update(user)
                    } else {
                        Single.error(RuntimeException("User : $name doesn't exist"))
                    }
                }
    }

    fun findAll(): Single<List<User>> {
        return usersRepository.list()
    }

    fun search(name: String): Single<User> {
        return usersRepository.find(name)
    }

    fun searchRemovePassword(name: String): Single<User> {
        return usersRepository.find(name)
                .onErrorReturn { throw RuntimeException("User not found") }
                .map {
                    it.password = ""
                    return@map it
                }
    }

    @PostConstruct
    fun createAdmin() {
        log.info("create admin user")
        save(User("admin", "admin@", adminPassword, listOf("ROLE_ADMIN")))
                .doOnSuccess {
                    log.info("Admin user created : ${it.name}")
                }
                .onErrorReturn {
                    log.error(it.localizedMessage)
                    User("not created")
                }
                .subscribe()
    }
}
