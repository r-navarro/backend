package com.rna.mealservice.config

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
@DirtiesContext
@ActiveProfiles('test')
abstract class AbstractIntegrationTest extends Specification{
}
