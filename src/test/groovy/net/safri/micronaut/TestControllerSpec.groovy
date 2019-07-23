package net.safri.micronaut

import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Specification

import javax.inject.Inject


@MicronautTest
class TestControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @AutoCleanup @Inject @Client("/")
    RxHttpClient client



    void "test index"() {
        given:
        HttpResponse response = client.toBlocking().exchange("/test")

        expect:
        response.status == HttpStatus.OK
    }
}
