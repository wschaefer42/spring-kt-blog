package com.example.blog

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
@Import(BlogConfiguration::class)
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `find by id and return article`() {
        val myself = User("wschaefer", "Werner", "Schaefer")
        entityManager.persist(myself)
        entityManager.flush()
        val article = Article(
            "Kotlin is cool",
            "Kotlin is modern and easy to use",
            "Kotlin was developed by JetBrains and abstract Java to make programming easier",
            myself)
        entityManager.persist(article)
        entityManager.flush()
        val found: Article? = articleRepository.findByIdOrNull(article.id!!)
        assert(found != null && found == article)
    }

    @Test
    fun `find by login and return user`() {
        val myself = User("wschaefer42", "Werner", "Schaefer")
        entityManager.persist(myself)
        entityManager.flush()
        val user = userRepository.findByLogin(myself.login)
        assert(user != null && user == myself)
    }

    @Test
    fun `select article by slug`() {
        val articles = articleRepository.findAll()
        for (a in articles) println(a.slug)
        assert(articles.count() == 2)
        val title = "Kotlin"
        val article = articleRepository.findBySlug(title.toSlug())
        assert(article != null)
    }
}