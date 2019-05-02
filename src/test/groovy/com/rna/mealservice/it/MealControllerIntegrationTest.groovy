package com.rna.mealservice.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.rna.mealservice.config.AbstractIntegrationTest
import com.rna.mealservice.controllers.dtos.MealDto
import com.rna.mealservice.documents.MealDocument
import com.rna.mealservice.repositories.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.test.StepVerifier
import spock.lang.Shared
import spock.lang.Stepwise

import static org.springframework.http.MediaType.APPLICATION_JSON

@Stepwise
class MealControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    WebTestClient webClient

    @Autowired
    MealRepository mealRepository

    @Autowired
    ObjectMapper mapper

    @Shared
    String mealName = "nameTest01"

    @WithMockUser
    def "Create a meal"() {
        given: "create a meal"
        def mealDto = new MealDto(name: mealName, score: 1, ingredients: ["toto"])

        when: "Call create meal endpoint"
        def result = webClient.post().uri("/meals")
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(mealDto))
                .exchange()

        then: "Result is correct and meal is in data base"
        result.expectStatus().isCreated()
        result.expectBody().jsonPath('$.score').isEqualTo("1")
        StepVerifier.create(mealRepository.findByName(mealName)).assertNext {
            it
        }.expectComplete().verify()

    }

    @WithMockUser
    def "get one meal"() {
        when:
        def result = webClient.get().uri("/meals/$mealName").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBody().jsonPath('$.score').isEqualTo("1").jsonPath('$.name').isEqualTo(mealName)
    }

    @WithMockUser
    def "get one unknown meal"() {
        when:
        def result = webClient.get().uri("/meals/mealName").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isNotFound()
    }

    @WithMockUser
    def "get meals"() {
        given:
        (0..5).each {
            mealRepository.save(new MealDocument(name: "meal$it", score: it, ingredients: ["ingre$it".toString(), "${it}dient".toString()])).block()
        }

        when:
        def result = webClient.get().uri("/meals").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBodyList(MealDto).hasSize(7)
    }

    @WithMockUser
    def "modify meal"() {
        given:
        def mealDto = new MealDto(name: mealName, score: 1, ingredients: ["toto_modif"])

        when:
        def result = webClient.put().uri("/meals").contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(mealDto))
                .exchange()

        then:
        result.expectStatus().isOk()
        result.expectBody().jsonPath('$.score').isEqualTo("1")
                .jsonPath('$.ingredients').isEqualTo("toto_modif")
        StepVerifier.create(mealRepository.findByName(mealName)).assertNext {
            it
        }.expectComplete().verify()
    }

    @WithMockUser
    def "modify unknown meal"() {
        given:
        def mealDto = new MealDto(name: 'unknown', score: 1, ingredients: ["toto_modif"])

        when:
        def result = webClient.put().uri("/meals").contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(mealDto))
                .exchange()

        then:
        result.expectStatus().isNotFound()
    }

    @WithMockUser
    def "find meals by ingredients"() {
        when:
        def result = webClient.get().uri("/meals?ingredients=toto_modif, ingre1").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBodyList(MealDto).hasSize(2)
    }

    @WithMockUser
    def "find meals by names"() {
        when:
        def result = webClient.get().uri("/meals?names=meal2, meal3").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBodyList(MealDto).hasSize(2)
    }

    @WithMockUser
    def "find meals by names and ingredients"() {
        when:
        def result = webClient.get().uri("/meals?names=meal2, meal3&ingredients=toto_modif").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBodyList(MealDto).hasSize(3)
    }

    @WithMockUser
    def "Search meals by name"() {
        when:
        def result = webClient.get().uri("/meals/search?name=test").exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        result.expectBodyList(MealDto).hasSize(1)
    }

    @WithMockUser
    def "Search ingredient by name"() {
        when:
        def result = webClient.get().uri("/meals/ingredients?name=ingre").exchange() as WebTestClient.ResponseSpec
//        println result.expectBodyList(String)

        then:
        result.expectStatus().isOk()
        result.expectBodyList(String).hasSize(1)
    }

    @WithMockUser
    def "delete meal"() {
        when:
        def result = webClient.delete().uri("/meals/$mealName").contentType(APPLICATION_JSON).exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isOk()
        StepVerifier.create(mealRepository.findByName(mealName)).verifyComplete()
    }

    @WithMockUser
    def "delete unknown meal"() {
        when:
        def result = webClient.delete().uri("/meals/$mealName").contentType(APPLICATION_JSON).exchange() as WebTestClient.ResponseSpec

        then:
        result.expectStatus().isNotFound()
    }
}
