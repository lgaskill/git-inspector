package com.lgaskill.git_inspector.client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Instant

@Service
class GitHubClient(private val restTemplate: RestTemplate) {

    fun getUser(userName: String): User {
        return restTemplate.getForEntity("/users/$userName", User::class.java).body!!
    }

    fun getUserRepositories(userName: String): List<UserRepository> {
        return restTemplate.exchange(
            "/users/$userName/repos",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<UserRepository>>() {}
        ).body!!
    }
}

data class User(
    val login: String,
    val name: String,
    val avatar_url: String,
    val location: String?,
    val email: String?,
    val html_url: String,
    val created_at: Instant,
)

data class UserRepository(
    val name: String,
    val html_url: String,
)