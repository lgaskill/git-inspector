# git-inspector
_**Author**: Luke Gaskill_

## Overview
A simple REST API designed to integrate with GitHub APIs to fetch basic account and public repository details.

## Service Architecture
### High-level overview
This is a **Kotlin** based **Spring Boot** web app, using **Gradle 8.10.2** for dependency/package management, with a basic controller-service design to separate request mapping 
and business logic concerns. The GitHub integration is managed with a configurable RestTemplate and exceptions are trapped
at the controller layer by the `ExceptionHandler` class.

### Module Breakdown
The modules/packages for this service are broken up by functional concern (controller, service, client) rather than 
by business domain. This is due to the current simplicity and single domain focus. If we were to later expand to additional 
GitHub data domains, or even other data providers, we could choose to restructure the modules then, as it makes sense.

### API
This service exposes a single GET endpoint which, given a GitHub username, merges basic user account details with 
a list of their public repos.

The username is passed in as a path variable: `/user-details/{username}`

**_Example:_**  
[GET] /user-details/test-user
```json
{
   "user_name": "test-user",
   "display_name": "Test User",
   "avatar": "https://avatars.githubusercontent.com/u/37128589?v=4",
   "geo_location": "Minneapolis",
   "email": "test-user@gmail.com",
   "url": "https://github.com/test-user",
   "created_at": "2018-03-07 01:57:14",
   "repos": [
      {
         "name": "test-repo",
         "url": "https://github.com/test-user/test-repo"
      }
   ]
}
```
### Testing
This uses the [Kotest](https://kotest.io/) unit testing framework. Test coverage is pretty light, with just a couple
basic tests wired up to the Service to verify the GitHub response mapping and to establish basic patterns for future tests.

### Dev and Monitoring Utilities
#### Actuator
To expose a simple health check endpoint (`/actuator/health`) and quickly enable remote troubleshooting utilities in the future, 
the Spring-Actuator library was added. This provides a simple health API out of the box and can be easily extended
and configured to provide several other debugging utilities as needed.

#### Micrometer
The [micrometer](https://micrometer.io/) library was included to automatically gather and expose HTTP and server metrics 
for quick and easy prometheus scraping. 

Prometheus metrics can be fetched through the `/actuator/prometheus` endpoint

### SpringDoc
The [Springdoc OpenApi](https://springdoc.org/) plugin allows API docs to be defined as annotations on the controller methods themselves.
These annotations, along with the data classes that form the request/response payloads define the API contracts
that are generated as OpenApi docs.

Generated docs can be fetched through the `/v3/api-docs` API endpoint

(I had to stop short of wiring up the hosted Swagger UI and service description)

### TODOs
1. Component/Integration tests
2. Connection pooling on the GitHub HTTP Client
3. Hosted Swagger UI

## Build and Run
### GitHub Authentication
This application will not run without first setting a valid GitHub personal access token through an 
environment variable named `CLIENT_TOKEN`.

A token can be acquired through the github portal by navigating to your account **Settings** > **Developer Settings** > **Personal Access Tokens**  
(Learn more here: [managing-your-personal-access-tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) )

### Docker
1. Docker build from root directory  
   `docker build -t git-inspector:<TAG> .`
2. Run it  
   `docker run git-inspector:<TAG> -p 8080:8080 --env CLIENT_TOKEN=<TOKEN>`

### Local Development IDE (IntelliJ)
This project is designed to have a locally-managed, git-ignored configuration file which can be used
to store and inject the GitHub client token into the service via the `CLIENT_TOKEN` variable.

1. Create a new `application-local.yml` file in the location:  
   `/src/main/resources/application-local.yml`

2. Paste the contents below with your personal access token:
   ```yml
   CLIENT_TOKEN: <YOUR_TOKEN>
   ```
3. Create a new run configuration with the following **Environment variables** setting:
   `spring.profiles.active=local`
4. Run it in the debugger 🚀