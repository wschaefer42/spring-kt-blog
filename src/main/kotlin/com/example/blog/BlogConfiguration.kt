package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {
    @Bean
    fun databaseInitializer(userRepository: UserRepository, articleRepository: ArticleRepository) =
        ApplicationRunner {
            val werner = userRepository.save(User("wschaefer", "Werner", "Schaefer"))
            articleRepository.save(Article(
                "Jakarta EE",
                "Jakarta EE 10 is landing",
                "Jakarta EE replace Java EE and makes with version 10 a big step",
                werner
            ))
            articleRepository.save(Article(
                "Kotlin",
                "Kotlin new kit of the block",
                "Kotlin develop by JetBrains is based on Java and makes programming easier with more fun",
                werner
            ))
        }
}