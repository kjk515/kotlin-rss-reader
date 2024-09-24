package rss

import org.w3c.dom.Document
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class BaeminRss : Rss {

    companion object {
        val URL2 = "https://fe-developers.kakaoent.com/rss.xml"
        val URL = "https://techblog.woowahan.com/feed"
    }

    override suspend fun getArticles(): Set<Article> {
        val doc = this.getDocument()
        // Get all items
        val nList = doc.getElementsByTagName("item")

        val articles = mutableSetOf<Article>()
        for (i in 0 until nList.length) {
            val element = nList.item(i) as org.w3c.dom.Element

            // GUID
            var guid = ""
            if (element.getElementsByTagName("guid").length > 0) {
                guid = element.getElementsByTagName("guid").item(0).textContent
            }

            // Title
            val title = element.getElementsByTagName("title").item(0).textContent

            // Publish Date
            val inputFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val pubDate = element.getElementsByTagName("pubDate").item(0).textContent
            val date = inputFormat.parse(pubDate)

            // Description / Content
            var description = ""
            if (element.getElementsByTagName("description").length > 0) {
                description = element.getElementsByTagName("description").item(0).textContent
            }
            if (element.getElementsByTagName("content:encoded").length > 0) {
                description = element.getElementsByTagName("content:encoded").item(0).textContent
            }

            articles.add(Article(guid, title, date, description))
        }

        return articles
    }

    private fun getDocument(): Document {
        // Create a DocumentBuilder
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val url = URL(URL)
        val doc = builder.parse(url.openStream())

        // Normalize the XML structure
        doc.documentElement.normalize()

        // Get channel element to find lastBuildDate
//        val channelElement = doc.getElementsByTagName("channel").item(0) as org.w3c.dom.Element
//        val lastBuildDate = channelElement.getElementsByTagName("lastBuildDate").item(0).textContent
//
//        // Format the lastBuildDate
//        val inputFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
//        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        val lastBuildDateFormatted = outputFormat.format(inputFormat.parse(lastBuildDate))
//
//        println("RSS Last Updated Date: $lastBuildDateFormatted")
//        println()

        return doc
    }
}