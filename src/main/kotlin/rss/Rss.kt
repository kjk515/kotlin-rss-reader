package rss

interface Rss {

    suspend fun getArticles(): Set<Article>
}