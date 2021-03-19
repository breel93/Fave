package com.kolaemiola.data.utils

import com.kolaemiola.core.model.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object MockData {
  val mockArticle0 = Article(
    author = "", title = "PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
    description = "In today’s IGN Daily Fix, Sydnee Goodman talks PlayStation Plus free games for July 2020 and reports that an Xbox Series S (aka Project Lockhart) announcemen...",
    url = "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
    urlToImage = "https://i.ytimg.com/vi/9SHwHJYIT7Q/maxresdefault.jpg",
    publishedAt = date("2020-06-29T22:31:54Z"), content = "",
    source = "YouTube"
  )
  val mockArticle1 = Article(
    author = "Joanna Nelius",
    title = "A Wild Apple ARM Benchmark Appears - Gizmodo",
    description = "Apple developers have supposedly started receiving their Apple ARM transition kits, and now a few benchmark numbers of those dev kits have also appeared in the wild. Spotted by 9to5Mac, benchmarks for the Developer Transition Kit seemed to have surfaced on Ge…",
    url = "https://gizmodo.com/a-wild-apple-arm-benchmark-appears-1844204355",
    urlToImage = "https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/uil5faf1vsjlbove01rw.jpg",
    publishedAt = date("2020-06-29T21:01:00Z"),
    content = "Apple developers have supposedly started receiving their Apple ARM transition kits, and now a few benchmark numbers of those dev kits have also appeared in the wild. Spotted by 9to5Mac, benchmarks fo… [+3362 chars]",
    source = "Gizmodo.com"
  )
  val mockArticle2 = Article(
    author = "Mike Wehner",
    title = "Sightings of a huge fireball were reported in the US - BGR",
    description = "A massive fireball appeared in the skies over several southern US states in the early morning hours of June 19th. Eyewitnesses report seeing a huge streak following by a bright flash and then a boom. It’s unclear if any pieces of the space rock made it to Ear…",
    url = "https://bgr.com/2020/06/29/fireball-video-2020-meteor/",
    urlToImage = "https://boygeniusreport.files.wordpress.com/2019/02/earth.jpg?quality=70&strip=all",
    publishedAt = date("2020-06-29T20:22:08Z"),
    content = "<ul><li>A massive fireball appeared in the skies over several southern US states in the early morning hours of June 19th. </li><li>Eyewitnesses report seeing a huge streak following by a bright flash… [+2396 chars]",
    source = "BGR"
  )
  val mockArticle3 = Article(
    author = "Jordan Novet",
    title = "Microsoft's withdrawal from livestreaming games fits recent trend of 'not chasing good money after bad' - CNBC",
    description = "Microsoft tried a variety of tactics to grow Mixer, but ultimately shuttered the service to focus on other gaming products that have better prospects",
    url = "https://www.cnbc.com/2020/06/29/microsoft-closed-mixer-after-it-failed-to-keep-up-with-amazon-twitch.html",
    urlToImage = "https://image.cnbcfm.com/api/v1/image/106594378-1593215422969gettyimages-962154338.jpeg?v=1593215637",
    publishedAt = date("2020-06-29T20:19:24Z"),
    content = "Microsoft is going head-to-head with Amazon in cloud computing and in recruiting top tech talent in the Seattle area. But when it comes to livestreaming video games, Microsoft is bowing out of a mark… [+4205 chars]",
    source = "CNBC"
  )
  val mockArticles = listOf(mockArticle0, mockArticle1, mockArticle2, mockArticle3)

  private fun date(theDate: String): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    var date = Date()
    try {
      date = format.parse(theDate)
      println(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return date
  }
}