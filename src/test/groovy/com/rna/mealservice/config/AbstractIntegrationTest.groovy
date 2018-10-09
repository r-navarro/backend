package com.rna.mealservice.config

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles('test')
abstract class AbstractIntegrationTest extends Specification{
}
