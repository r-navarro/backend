package com.rna.meal;

import io.reactivex.Single;

import javax.inject.Singleton;

@Singleton
public class MealsService {

    private final MealsRepository mealsRepository;

    public MealsService(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    public Single<Meal> save(Meal meal) {
        return mealsRepository.save(meal);
    }
}
