package ipca.example.suspendnews

import android.os.AsyncTask
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
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

        const val BASE_API = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=1765f87e4ebc40229e80fd0f75b6416c"

        suspend fun getNews() = suspendCoroutine<JSONObject> {

            try {

                val urlc: HttpURLConnection = URL(BASE_API).openConnection() as HttpURLConnection
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

                it.resume(JSONObject(str))

            }catch (e:Exception){
                e.printStackTrace()
                //result = "Sem internet!"
            }

        }
    }

}