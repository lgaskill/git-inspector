package com.lgaskill.git_inspector.service

import com.lgaskill.git_inspector.client.GitHubClient
import com.lgaskill.git_inspector.controller.UserDetailsResponse
import com.lgaskill.git_inspector.controller.UserRepo
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class GitHubService(private val gitHubClient: GitHubClient) {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.of("UTC"))

    fun getUserDetails(userName: String): UserDetailsResponse {
        val user = gitHubClient.getUser(userName)
        val repos = gitHubClient.getUserRepositories(userName)

        return UserDetailsResponse(
            user.login,
            user.name,
            user.avatar_url,
            user.location,
            user.email,
            user.html_url,
            dateFormatter.format(user.created_at),
            repos.map { UserRepo(it.name, it.html_url) },
        )
    }
}