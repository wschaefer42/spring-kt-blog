package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class RestControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `list articles`() {
        val myself = User("wschaefer", "Werner", "Schaefer")
        val articleKotlin = Article("Kotlin", "Kotlin is new", "Kotlin was introduced by ...", myself)
        val articleJakartaEE = Article("JakartaEE", "Jakarta EE replace JEE", "JEE was take over ...", myself)

        every {
            articleRepository.findAllByOrderByAddedAtDesc()
        } returns listOf(articleKotlin, articleJakartaEE)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(myself.login))
            .andExpect(jsonPath("\$.[0].slug").value(articleKotlin.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(myself.login))
            .andExpect(jsonPath("\$.[1].slug").value(articleJakartaEE.slug))
    }

    @Test
    fun `list users`() {
        val werner = User("wschaefer", "Werner", "Schaefer")
        val nobody = User("nobody", "Mister", "Nobody")

        every { userRepository.findAll() } returns listOf(werner, nobody)

        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(werner.login))
            .andExpect(jsonPath("\$.[1].login").value(nobody.login))
    }
}
