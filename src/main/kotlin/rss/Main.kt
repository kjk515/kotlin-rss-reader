package rss

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    var articles = Articles()
    var rssList = listOf(
        BaeminRss(),
        KakaoEntRss()
    )

    runBlocking {
        launch {
            while (true) {
                println("rss 동작 중...")
                coroutineScope {
                    rssList.forEach {
                        articles.refreshArticles(it.getArticles())
                    }
                }

                val displayArticles = articles.getSortedArticles(10)
                displayArticles.forEach {
                    println("제목: ${it.title}, 날짜: ${it.date}")
                }
                println()

                delay(60000)
            }
        }
    }
}