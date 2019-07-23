package net.safri.micronaut

import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.validation.Validated

@Client(value = '${url:`http://127.0.0.1:8080/test`}')
@Validated
@CompileStatic
interface TestClient {
    @Post(value = "/form", produces = MediaType.APPLICATION_FORM_URLENCODED)
    TestResponse call(@Body TestCommand loginCommand)
}


