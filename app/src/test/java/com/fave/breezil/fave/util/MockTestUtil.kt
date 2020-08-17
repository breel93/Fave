package com.fave.breezil.fave.util

import com.fave.breezil.fave.model.Article
import com.fave.breezil.fave.model.ArticleResult
import com.fave.breezil.fave.model.Source
import java.util.*

class MockTestUtil {
  fun mockArticleResponse() : ArticleResult {
    return ArticleResult("ok", 4, "", mockArticleList())
  }



  fun mockArticleList(): List<Article>{
    val articleList  = ArrayList<Article>()
    val article = Article ( "PlayStation Plus Free Games for July 2020 - IGN Daily Fix - IGN",
      "In today’s IGN Daily Fix, Sydnee Goodman talks PlayStation Plus free games for July 2020 and reports that an Xbox Series S (aka Project Lockhart) announcemen...",
      "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
      "https://i.ytimg.com/vi/9SHwHJYIT7Q/maxresdefault.jpg",
      "2020-06-29T22:31:54Z",
      Source( "YouTube")
      )
    val article1 = Article ( "A Wild Apple ARM Benchmark Appears - Gizmodo",
      "Apple developers have supposedly started receiving their Apple ARM transition kits, and now a few benchmark numbers of those dev kits have also appeared in the wild. Spotted by 9to5Mac, benchmarks for the Developer Transition Kit seemed to have surfaced on Ge…",
      "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
      "https://gizmodo.com/a-wild-apple-arm-benchmark-appears-1844204355",
      "2020-06-29T21:01:00Z",
      Source( "Gizmodo.com")
    )

    val article2 = Article ( "Sightings of a huge fireball were reported in the US - BGR",
      "A massive fireball appeared in the skies over several southern US states in the early morning hours of June 19th. Eyewitnesses report seeing a huge streak following by a bright flash and then a boom. It’s unclear if any pieces of the space rock made it to Ear…",
      "https://bgr.com/2020/06/29/fireball-video-2020-meteor/",
      "https://boygeniusreport.files.wordpress.com/2019/02/earth.jpg?quality=70&strip=all",
      "2020-06-29T20:22:08Z",
      Source( "BGR")
    )
    val article3 = Article ( "Microsoft's withdrawal from livestreaming games fits recent trend of 'not chasing good money after bad' - CNBC",
      "Microsoft tried a variety of tactics to grow Mixer, but ultimately shuttered the service to focus on other gaming products that have better prospects.",
      "https://www.cnbc.com/2020/06/29/microsoft-closed-mixer-after-it-failed-to-keep-up-with-amazon-twitch.html",
      "https://image.cnbcfm.com/api/v1/image/106594378-1593215422969gettyimages-962154338.jpeg?v=1593215637",
      "2020-06-29T20:19:24Z",
      Source( "CNBC")
    )
    articleList.add(article)
    articleList.add(article1)
    articleList.add(article2)
    articleList.add(article3)
    return articleList
  }

  fun mockArticle() :Article{
    return Article ( "A Wild Apple ARM Benchmark Appears - Gizmodo",
      "Apple developers have supposedly started receiving their Apple ARM transition kits, and now a few benchmark numbers of those dev kits have also appeared in the wild. Spotted by 9to5Mac, benchmarks for the Developer Transition Kit seemed to have surfaced on Ge…",
      "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
      "https://gizmodo.com/a-wild-apple-arm-benchmark-appears-1844204355",
      "2020-06-29T21:01:00Z",
      Source( "Gizmodo.com"))
  }

  fun mockArticle(id:Int) :Article{
    val article = Article("A Wild Apple ARM Benchmark Appears - Gizmodo",
      "Apple developers have supposedly started receiving their Apple ARM transition kits, and now a few benchmark numbers of those dev kits have also appeared in the wild. Spotted by 9to5Mac, benchmarks for the Developer Transition Kit seemed to have surfaced on Ge…",
      "https://www.youtube.com/watch?v=9SHwHJYIT7Q",
      "https://gizmodo.com/a-wild-apple-arm-benchmark-appears-1844204355",
      "2020-06-29T21:01:00Z",
      Source( "Gizmodo.com"))
    article.id = id
    return article
  }




}