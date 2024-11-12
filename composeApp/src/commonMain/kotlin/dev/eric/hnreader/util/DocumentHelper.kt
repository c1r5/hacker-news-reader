package dev.eric.hnreader.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URI

suspend fun fetchImage(url: String?, dispatcher: CoroutineDispatcher): Result<String> =
    withContext(dispatcher) {
        runCatching {
            val whitelistExtensions = listOf("png", "jpg", "jpeg", "svg")
            if (url == null) error("No URL provided")

            val uri = URI.create(url)
            val domain = uri.host
            val baseURL = "${uri.scheme}://$domain"
            val doc = Jsoup.connect(url).get()
            val elements = doc.select("img").filter { element ->
                val attributes = element.attributes()
                !attributes.get("class").contains("logo")
            }

//                .filter {
//                    val attrValues = it.attributes().dataset()
//                    attrValues.any { (k, v) ->
//                        println("$k: $v")
//                        !v.contains("logo") && whitelistExtensions.any { ext -> v.endsWith(ext) }
//                    }
//                }

            if (elements.isEmpty()) error("No image found")

            val image = elements.first()

            val srcURL = image?.attr("src") ?: error("No image found")

            if (srcURL.startsWith("/")) {
                "$baseURL$srcURL"
            } else {
                srcURL
            }
        }
    }