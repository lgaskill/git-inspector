package com.lgaskill.git_inspector.service

import com.lgaskill.git_inspector.client.GitHubClient
import com.lgaskill.git_inspector.client.User
import com.lgaskill.git_inspector.client.UserRepository
import com.lgaskill.git_inspector.controller.UserRepo
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.Instant

class GitHubServiceSpec: FunSpec ({
    val gitHubClient = mockk<GitHubClient>()

    val gitHubService = GitHubService(gitHubClient)

    test("Should happily fetch and format user details and repos from the API") {
        // Given
        val userName = "user-name"
        val user = User(
            "login",
            "name",
            "avatar_url",
            "location",
            "email",
            "html_url",
            Instant.parse("2024-09-23T03:43:58Z"),
        )
        val repos = listOf(
            UserRepository("repo-1", "https://github.com/repo-1"),
            UserRepository("repo-2", "https://github.com/repo-2"),
        )

        every { gitHubClient.getUser(userName) } returns user
        every { gitHubClient.getUserRepositories(userName) } returns repos

        // When
        val response = gitHubService.getUserDetails(userName)

        // Then
        response.user_name shouldBe "login"
        response.display_name shouldBe "name"
        response.avatar shouldBe "avatar_url"
        response.geo_location shouldBe "location"
        response.email shouldBe "email"
        response.url shouldBe "html_url"
        response.created_at shouldBe "2024-09-23 03:43:58"

        response.repos.size shouldBe 2
        response.repos[0].name shouldBe "repo-1"
        response.repos[0].url shouldBe "https://github.com/repo-1"
        response.repos[1].name shouldBe "repo-2"
        response.repos[1].url shouldBe "https://github.com/repo-2"
    }

    test("Should allow null values for email and location") {
        // Given
        val userName = "user-name"
        val user = User(
            "login",
            "name",
            "avatar_url",
            null,
            null,
            "html_url",
            Instant.parse("2024-09-23T03:43:58Z"),
        )
        val repos: List<UserRepository> = listOf()

        every { gitHubClient.getUser(userName) } returns user
        every { gitHubClient.getUserRepositories(userName) } returns repos

        // When
        val response = gitHubService.getUserDetails(userName)

        // Then
        response.user_name shouldBe "login"
        response.display_name shouldBe "name"
        response.avatar shouldBe "avatar_url"
        response.geo_location shouldBe null
        response.email shouldBe null
        response.url shouldBe "html_url"
        response.created_at shouldBe "2024-09-23 03:43:58"

        response.repos.size shouldBe 0
    }
})