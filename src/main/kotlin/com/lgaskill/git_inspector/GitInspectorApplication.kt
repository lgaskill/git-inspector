package com.lgaskill.git_inspector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = ["com.lgaskill"])
class GitInspectorApplication

fun main(args: Array<String>) {
	runApplication<GitInspectorApplication>(*args)
}
