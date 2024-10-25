package com.lgaskill.git_inspector.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Configuration
class GitHubApiConfiguration(private val gitHubApiConfigProperties: GitHubApiConfigProperties) {

    @Bean
    fun gitHubRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .rootUri(gitHubApiConfigProperties.baseUrl)
            .defaultHeader("Authorization", "Bearer ${gitHubApiConfigProperties.clientToken}")
            .defaultHeader("X-GitHub-Api-Version", gitHubApiConfigProperties.apiVersion)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build()
    }
}


@Component
@ConfigurationProperties(prefix = "github")
data class GitHubApiConfigProperties(
    var baseUrl: String = "",
    var apiVersion: String = "",
    var clientToken: String = "",
)
