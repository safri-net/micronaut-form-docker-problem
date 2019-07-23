package net.safri.micronaut

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.validation.Valid

@Controller("/test")
class TestController {

    final TestClient client

    TestController(TestClient client) {
        this.client = client
    }

    @Get("/")
    String index() {
        client.call(new TestCommand(name: "John")).message
    }

    @Post(value = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    TestResponse form(@Valid @Body TestCommand command) {
        new TestResponse(message: "Hello ${command.name}")
    }
}