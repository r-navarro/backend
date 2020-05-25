package com.rna.meal

import com.mongodb.client.result.DeleteResult
import io.micronaut.core.util.StringUtils
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Singleton

@Singleton
class MealsService(private val mealsRepository: MealsRepository) {

    fun save(meal: Meal): Single<Meal> {
        val observable = BehaviorSubject.create<Meal>()
        mealsRepository.find(meal.name)
                .subscribe(
                        { observable.onError(RuntimeException("Meal : ${meal.name} already exist")) },
                        { save(meal, observable) }
                )
        return observable.singleOrError()
    }

    fun delete(name: String): Single<DeleteResult> {
        val observable = BehaviorSubject.create<DeleteResult>()
        mealsRepository.find(name)
                .subscribe(
                        { delete(name, observable) },
                        { observable.onError(RuntimeException("Meal : $name doesn't exist")) }
                )
        return observable.singleOrError()
    }

    fun update(name: String, meal: Meal): Single<Meal> {
        val observable = BehaviorSubject.create<Meal>()
        mealsRepository.find(name)
                .subscribe(
                        {
                            meal.name = name
                            update(meal, observable)
                        },
                        { observable.onError(RuntimeException("Meal : $name doesn't exist")) }
                )
        return observable.singleOrError()
    }

    private fun save(meal: Meal, observable: BehaviorSubject<Meal>) {
        mealsRepository.save(meal).subscribe(
                { createdMeal ->
                    observable.onNext(createdMeal)
                    observable.onComplete()
                },
                { createError -> observable.onError(createError) })
    }

    private fun update(meal: Meal, observable: BehaviorSubject<Meal>) {
        mealsRepository.update(meal).subscribe(
                { createdMeal ->
                    observable.onNext(createdMeal)
                    observable.onComplete()
                },
                { createError -> observable.onError(createError) })
    }

    private fun delete(meal: String, observable: BehaviorSubject<DeleteResult>) {
        mealsRepository.delete(meal).subscribe(
                { deletedMeal ->
                    observable.onNext(deletedMeal)
                    observable.onComplete()
                },
                { deletedError -> observable.onError(deletedError) })
    }

    fun search(queryWrapper: QueryWrapper): Single<Meal> {
        return if (StringUtils.isNotEmpty(queryWrapper.name)) {
            mealsRepository.find(queryWrapper.name)
        } else Single.error(RuntimeException("not found"))
    }

    fun findAll(): Single<List<Meal>> {
        return mealsRepository.list()
    }
}
