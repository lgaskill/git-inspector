package com.lgaskill.git_inspector.controller

import com.lgaskill.git_inspector.service.GitHubService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-details")
class UserDetailsController(private val gitHubService: GitHubService) {

    @Operation(
        method = "GET",
        summary = "Get a basic information about a GitHub user account, as well as a list of their public repositories",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Returns the requested point",
            ),
            ApiResponse(
                responseCode = "404",
                description = "No user found with the requested ID",
            ),
            ApiResponse(
                responseCode = "502",
                description = "Failed to connect or integrate with the GitHub API",
            )
        ]
    )
    @GetMapping("/{userName}")
    fun getUserDetails(@PathVariable userName: String): UserDetailsResponse {
        return gitHubService.getUserDetails(userName)
    }
}

data class UserDetailsResponse(
    val user_name: String,
    val display_name: String,
    val avatar: String,
    val geo_location: String?,
    val email: String?,
    val url: String,
    val created_at: String,
    val repos: List<UserRepo>
)

data class UserRepo(
    val name: String,
    val url: String,
)