package ipca.example.suspendnews

import androidx.lifecycle.liveData
import androidx.loader.content.AsyncTaskLoader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//
// Created by lourencogomes on 03/11/2020.
//
class Backend {


    companion object {

        const val BASE_API = "https://newsapi.org/v2/"

        fun fetchArticles(typesNews:String) = liveData(IO) {
            emit(getNews(typesNews))
        }


        suspend fun getNews(newsType:String) = suspendCoroutine<List<Article>> {
            try {

                val urlc: HttpURLConnection = URL(BASE_API
                +"top-headlines?country=us&category=$newsType&apiKey=1765f87e4ebc40229e80fd0f75b6416c"
                ).openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.setConnectTimeout(1500)
                urlc.connect()
                val stream  = urlc.inputStream
                val isReader = InputStreamReader(stream)
                val brin = BufferedReader(isReader)
                var str: String = ""

                var keepReading = true
                while (keepReading) {
                    var line = brin.readLine()
                    if (line==null){
                        keepReading = false
                    }else{
                        str += line
                    }
                }
                brin.close()
                val result = JSONObject(str)
                if (result.getString("status")=="ok"){
                    val articlesJSONArray = result.getJSONArray("articles")
                    var articles : MutableList<Article> = arrayListOf()
                    for (index in 0 until articlesJSONArray.length()) {

                        val jsonArticle : JSONObject = articlesJSONArray.get(index) as JSONObject
                        var article = Article.fromJSON(jsonArticle)
                        articles.add(article)

                    }
                    it.resume(articles)

                }




            }catch (e:Exception){
                e.printStackTrace()
                //result = "Sem internet!"
            }

        }
    }

}