package com.rna.mealservice.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
class MeController {

    @GetMapping("/me")
    Principal getMe(Principal principal){
        principal
    }
}
