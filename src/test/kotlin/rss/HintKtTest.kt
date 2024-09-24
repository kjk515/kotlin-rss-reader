package rss

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory


class HintKtTest {

    @Test
    fun `xml 형태로 rss 가져온다`() {
        val factory = DocumentBuilderFactory.newInstance()
        val xml = factory.newDocumentBuilder()
            .parse("https://techblog.woowahan.com/feed")

        Assertions.assertNotNull(xml)
    }

    @Test
    fun `최근 빌드 날짜를 가져온다`() {
        // Create a DocumentBuilder
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val url = URL("https://techblog.woowahan.com/feed")
        val doc = builder.parse(url.openStream())

        // Normalize the XML structure
        doc.documentElement.normalize()

        // Get channel element to find lastBuildDate
        val channelElement = doc.getElementsByTagName("channel").item(0) as Element
        val lastBuildDate = channelElement.getElementsByTagName("lastBuildDate").item(0).textContent

        // Format the lastBuildDate
        val inputFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val lastBuildDateFormatted = outputFormat.format(inputFormat.parse(lastBuildDate))

        println("RSS Last Updated Date: $lastBuildDateFormatted")
        println()
    }

    @Test
    fun `id, 제목, 날짜, 내용을 추출한다`() {
        // Create a DocumentBuilder
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val url = URL("https://techblog.woowahan.com/feed")
        val doc = builder.parse(url.openStream())

        // Normalize the XML structure
        doc.documentElement.normalize()

        // Get channel element to find lastBuildDate
        val channelElement = doc.getElementsByTagName("channel").item(0) as org.w3c.dom.Element
        val lastBuildDate = channelElement.getElementsByTagName("lastBuildDate").item(0).textContent

        // Format the lastBuildDate
        val inputFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val lastBuildDateFormatted = outputFormat.format(inputFormat.parse(lastBuildDate))

        println("RSS Last Updated Date: $lastBuildDateFormatted")
        println()

        // Get all items
        val nList = doc.getElementsByTagName("item")

        for (i in 0 until nList.length) {
            val element = nList.item(i) as org.w3c.dom.Element

            // GUID
            var guid = ""
            if (element.getElementsByTagName("guid").length > 0) {
                guid = element.getElementsByTagName("guid").item(0).textContent
            }

            // Title
            val title = element.getElementsByTagName("title").item(0).textContent

            // Link
            val link = element.getElementsByTagName("link").item(0).textContent

            // Publish Date
            val pubDate = element.getElementsByTagName("pubDate").item(0).textContent
            val date = inputFormat.parse(pubDate)
            val formattedDate = outputFormat.format(date)

            // Description / Content
            var description = ""
            if (element.getElementsByTagName("description").length > 0) {
                description = element.getElementsByTagName("description").item(0).textContent
            }
            if (element.getElementsByTagName("content:encoded").length > 0) {
                description = element.getElementsByTagName("content:encoded").item(0).textContent
            }

            // Print the details
            println("GUID : $guid")
            println("Title : $title")
            println("Link : $link")
            println("Publish Date : $formattedDate")
            println("Description : $description")
            println()
        }
    }

    @Test
    fun `추출된 데이터들을 저장한다`() {

    }
}
