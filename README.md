# Docker Form Issue

I have a project with a Client

```groovy
@Client(value = '${url:`http://127.0.0.1:8080/test`}')
@Validated
@CompileStatic
interface TestClient {
    @Post(value = "/form", produces = MediaType.APPLICATION_FORM_URLENCODED)
    TestResponse call(@Body TestCommand loginCommand)
}

```

When I run the project in my IDE (IntelliJ) or even as `java -jar my.jar` everything works as expected,
the client sends a correct request to the keycloak server.

*io.micronaut.http* on `TRACE`
```log
12:34:19.781 [nioEventLoopGroup-1-3] DEBUG i.m.http.client.DefaultHttpClient - Sending HTTP Request: POST /test/form
12:34:19.781 [nioEventLoopGroup-1-3] DEBUG i.m.http.client.DefaultHttpClient - Chosen Server: 127.0.0.1(8080)
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - Accept: application/json
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - content-type: application/x-www-form-urlencoded
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - host: 127.0.0.1:8080
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - connection: close
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - content-length: 9
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - Request Body
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - ----
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - name=John
12:34:19.782 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - ----
```

As soon as I run the project in a docker container, the same request has an empty body:

*io.micronaut.http* on `TRACE`
```log
10:37:58.635 [nioEventLoopGroup-1-3] DEBUG i.m.http.client.DefaultHttpClient - Sending HTTP Request: POST /test/form
10:37:58.636 [nioEventLoopGroup-1-3] DEBUG i.m.http.client.DefaultHttpClient - Chosen Server: 127.0.0.1(8080)
10:37:58.636 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - Accept: application/json
10:37:58.636 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - content-type: application/x-www-form-urlencoded
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - host: 127.0.0.1:8080
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - connection: close
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - content-length: 0
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - Request Body
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - ----
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - 
10:37:58.637 [nioEventLoopGroup-1-3] TRACE i.m.http.client.DefaultHttpClient - ----
```
To reproduce the error I have created an app with an controller that calls another method.

**Working:**  
```sh
./gradlew run
curl http://localhost:8080/test
```

**Failing:**  
```sh
./gradlew assemble 
docker build -t safri/docker-probs . 
docker run -d -p 8080:8080 safri/docker-probs
curl http://localhost:8080/test
```


