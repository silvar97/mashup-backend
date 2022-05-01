package org.hrw.mashupbackend.web

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import java.text.SimpleDateFormat
import java.time.Duration

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseWebTest {

    protected val objectMapper: ObjectMapper = ObjectMapper()

    internal var mockBackEnd: MockWebServer = MockWebServer()
    val factory = DefaultUriBuilderFactory("http://localhost:8052").apply {
        encodingMode = DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT
    }
    internal val webClient =
        WebClient.builder().uriBuilderFactory(factory).exchangeStrategies(strategies).clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create().responseTimeout(Duration.ofSeconds(10))
            )
        ).baseUrl("http://localhost:8052").build()

    @BeforeAll
    fun setUp() {
        mockBackEnd.start(8052)
        objectMapper.registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    }

    @AfterAll
    fun tearDown() {
        mockBackEnd.shutdown()
    }

    @AfterEach
    fun createNewMockBackEnd() {
        mockBackEnd.shutdown()
        mockBackEnd = MockWebServer()
        mockBackEnd.start(8052)
    }

    companion object {
        const val MAX_MEMORY_SIZE = 16 * 1024 * 1024 //value copied from stackoverflow and worked right away
        private val strategies = ExchangeStrategies.builder().codecs { codecs: ClientCodecConfigurer ->
            codecs.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE)
        }.build()

    }
}