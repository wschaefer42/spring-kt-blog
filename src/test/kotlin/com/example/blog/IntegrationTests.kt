package com.example.blog

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus

@Import(BlogConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {
    @Test
    fun `Assert hello page, content and status code`() {
        val entity = restTemplate.getForEntity<String>("/hello")
        assert(entity.statusCode == HttpStatus.OK)
        assert(entity.body?.contains("<h1>Hello</h1>")!!)
    }

    @Test
    fun `Assert article page, content and status code`() {
        val title = "Kotlin"
        val entity = restTemplate.getForEntity<String>("/article/${title.toSlug()}")
        assert(entity.statusCode == HttpStatus.OK)
        assert(entity.body?.contains(ignoreCase = true, title, "JetBrains") ?: false)
    }
}