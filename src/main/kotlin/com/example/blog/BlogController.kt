package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class BlogController(private val repository: ArticleRepository) {
    @GetMapping("/blog")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        model["articles"] = repository.findAllByOrderByAddedAtDesc()
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = repository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found")
        model["title"] = article.title
        model["article"] = article
        return "article"
    }
}