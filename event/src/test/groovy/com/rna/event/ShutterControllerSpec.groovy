package com.rna.event

import com.rna.shutter.ShutterDAO
import com.rna.shutter.ShutterDAOImpl
import com.rna.shutter.dto.MessageDTO
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.reactivex.Single
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ShutterControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client

    @Inject
    ShutterDAO shutterDAO

    private static final String SHUTTER_URL = "/shutters"
    private static final String OPEN_URL = "/open/5"
    private static final String CLOSE_URL = "/close/4"

    def "Open shutter"() {
        setup:
        shutterDAO.open(_) >> Single.just(HttpResponse.ok("ok"))

        when:
        def request = HttpRequest.POST(SHUTTER_URL + OPEN_URL, "")
        def response = client.toBlocking().exchange(request, MessageDTO)

        then:
        response
        response.body().message == "ok"
        response.status == HttpStatus.OK
    }

    def "Close shutter"() {
        setup:
        shutterDAO.close(_) >> Single.just(HttpResponse.ok("ok"))

        when:
        def request = HttpRequest.POST(SHUTTER_URL + CLOSE_URL, "")
        def response = client.toBlocking().exchange(request, MessageDTO)

        then:
        response
        response.body().message == "ok"
        response.status == HttpStatus.OK
    }

    def "Open shutter with error"() {
        setup:
        shutterDAO.open(_) >> Single.just(HttpResponse.notFound("not found dude"))

        when:
        def request = HttpRequest.POST(SHUTTER_URL + OPEN_URL, "")
        client.toBlocking().retrieve(request)

        then:
        def error = thrown(HttpClientResponseException)
        error
        def response = error.response
        response.status == HttpStatus.BAD_REQUEST
    }

    @MockBean(ShutterDAOImpl)
    ShutterDAO shutterDAO() {
        Mock(ShutterDAO)
    }
}
