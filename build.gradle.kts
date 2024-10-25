plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"

	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.kotest") version "0.3.8"
}

group = "com.lgaskill"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")
	implementation("io.micrometer:micrometer-registry-prometheus:1.10.5")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

	// Kotest
	testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.10")
	testImplementation("io.mockk:mockk:1.12.3")
	testImplementation("io.kotest:kotest-runner-junit5:4.6.2")
	testImplementation("io.kotest:kotest-framework-engine-jvm:4.6.2")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.2")
	testImplementation("io.kotest:kotest-property-jvm:4.6.2")
	testImplementation("io.kotest:kotest-framework-datatest:4.6.2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
