package com.rna.mealservice.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.rna.mealservice.config.AbstractIntegrationTest
import com.rna.mealservice.config.security.dto.AccountCredentials
import com.rna.mealservice.controllers.dtos.MealDto
import com.rna.mealservice.documents.MealDocument
import com.rna.mealservice.repositories.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Stepwise

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
class MealControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc

    @Autowired
    MealRepository mealRepository

    @Autowired
    ObjectMapper mapper

    @Shared
    String mealName = "nameTest01"

    def setup() {
        def accountCredentials = new AccountCredentials(userName: 'test', password: 'pass')
        def json = mapper.writeValueAsString(accountCredentials)
        def result = mockMvc.perform(post("/login").contentType(APPLICATION_JSON).content(json))

        assert result.andExpect(status().isOk())
    }

    def "Create a meal"() {
        given: "create a meal"
        def mealDto = new MealDto(name: mealName, score: 1, tags: ["toto"])
        def json = mapper.writeValueAsString(mealDto)

        when: "Call create meal endpoint"
        def result = mockMvc.perform(post("/meals").contentType(APPLICATION_JSON).content(json))

        then: "Result is correct and meal is in data base"
        result.andExpect(status().isCreated())
        result.andExpect(jsonPath('$.score').value("1"))
        mealRepository.findByName(mealName).isPresent()

    }

    @WithMockUser
    def "get one meal"() {
        when:
        def result = mockMvc.perform(get("/meals/$mealName"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.score').value("1"))
        result.andExpect(jsonPath('$.name').value(mealName))
    }

    @WithMockUser
    def "get one unknown meal"() {
        when:
        def result = mockMvc.perform(get("/meals/mealName"))

        then:
        result.andExpect(status().isNotFound())
    }

    @WithMockUser
    def "get meals"() {
        given:
        (0..5).each {
            mealRepository.save(new MealDocument(name: "meal$it", score: it, tags: ["fn$it".toString()]))
        }

        when:
        def result = mockMvc.perform(get("/meals"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.content.length()').value(7))
    }

    @WithMockUser
    def "modify meal"() {
        given:
        def mealDto = new MealDto(name: mealName, score: 1, tags: ["toto_modif"])
        def json = mapper.writeValueAsString(mealDto)

        when:
        def result = mockMvc.perform(put("/meals").contentType(APPLICATION_JSON).content(json))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.score').value("1"))
        result.andExpect(jsonPath('$.tags').value("toto_modif"))
        mealRepository.findByName(mealName)
    }

    @WithMockUser
    def "find meals by tags"() {
        when:
        def result = mockMvc.perform(get("/meals?tags=toto_modif, fn1"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.content.length()').value(2))
    }

    @WithMockUser
    def "delete meal"() {
        when:
        def result = mockMvc.perform(delete("/meals/$mealName").contentType(APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        !mealRepository.findByName(mealName).isPresent()
    }

    @WithMockUser
    def "delete unknown meal"() {
        when:
        def result = mockMvc.perform(delete("/meals/$mealName").contentType(APPLICATION_JSON))

        then:
        result.andExpect(status().isNotFound())
    }
}
