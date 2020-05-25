package com.rna.event

import com.mongodb.client.result.DeleteResult
import groovy.json.JsonOutput
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Stepwise

import javax.inject.Inject

@MicronautTest
@Stepwise
class EventControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client

    void "test get all event"() {
        when:
        def request = HttpRequest.GET("/events")
        def response = client.toBlocking().retrieve(request)

        then:
        response
        response == [] as String
    }

    void "Create event"() {
        when:
        def postRequest = HttpRequest.POST("/events", new Event(type: "testEvt", sender: "test"))
        def event = client.toBlocking().retrieve(postRequest, Event)
        def request = HttpRequest.GET("/events")
        def response = client.toBlocking().retrieve(request)

        then:
        event
        event.timestamp
        response
        response == JsonOutput.toJson([event])
    }

    void "Delete events"() {
        when:
        def events = []
        events << client.toBlocking().retrieve(HttpRequest.POST("/events", new Event(type: "testEvt", sender: "test")), Event)
        events << client.toBlocking().retrieve(HttpRequest.POST("/events", new Event(type: "testEvt", sender: "test")), Event)
        events << client.toBlocking().retrieve(HttpRequest.POST("/events", new Event(type: "testEvt", sender: "test")), Event)
        events << client.toBlocking().retrieve(HttpRequest.POST("/events", new Event(type: "test2Evt", sender: "test")), Event)

        def deleteResponse = client.toBlocking().exchange(HttpRequest.DELETE("/events/testEvt"))

        def request = HttpRequest.GET("/events")
        def response = client.toBlocking().retrieve(request, List)

        then:
        deleteResponse.status == HttpStatus.NO_CONTENT
        events.forEach {
            assert it.timestamp
        }
        response.size() == 1
        response.collect { it.type } == ["test2Evt"]
    }

}
