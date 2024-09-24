package rss

class Articles {

    private val articles: MutableSet<Article> = mutableSetOf()

    suspend fun refreshArticles(articles: Set<Article>) {
        this.articles.addAll(articles)
    }

    fun getSortedArticles(limit: Int): List<Article> {
        return this.articles.sortedByDescending { it.date }.subList(0, limit)
    }
}