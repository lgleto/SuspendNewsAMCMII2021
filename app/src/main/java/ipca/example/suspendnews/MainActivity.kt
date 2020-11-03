package ipca.example.suspendnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var articles : MutableList<Article> = arrayListOf()
    var adapter : ArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articles.add(Article("text", "teste", "url"))

        val listView = findViewById<ListView>(R.id.listView)
        adapter = ArticlesAdapter()
        listView.adapter = adapter

        emit{} Backend.getNews()

    }

    inner class ArticlesAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(p0: Int): Any {
            return articles[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val v = layoutInflater.inflate(R.layout.row_article,p2,false)
            val textViewTitle = v.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = v.findViewById<TextView>(R.id.textViewDescription)

            textViewTitle.text = articles[p0].title
            textViewDescription.text = articles[p0].description

            return v
        }

    }

}