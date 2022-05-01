package de.ito.irisnet.middleware.config

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


/**
 * This allows CORS request for OPTIONS from localhost, which is part of the default implementation of apollo client flow.
 */
@Component
class AllowCorsFromLocalHostConfig : WebFilter {
    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        ctx.response.headers.add("Access-Control-Allow-Origin", "*")
        ctx.response.headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS")
        ctx.response.headers.add("Access-Control-Allow-Credentials", "true")
        ctx.response.headers.add(
            "Access-Control-Allow-Headers",
            "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range"
        )
        return when {
            ctx.request.method == HttpMethod.OPTIONS -> {
                ctx.response.headers.add("Access-Control-Max-Age", "1728000")
                ctx.response.statusCode = HttpStatus.NO_CONTENT
                Mono.empty()
            }
            else -> {
                ctx.response.headers.add(
                    "Access-Control-Expose-Headers",
                    "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range"
                )
                chain.filter(ctx)
            }
        }
    }

    companion object {
        private const val LOCALHOST_CLIENT_URL_WITH_PORT = "http://localhost:8080"
    }
}
