package ipca.example.suspendnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var articles :List<Article> = arrayListOf()
    var adapter : ArticlesAdapter? = null


    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.articles.observe(this, Observer {
            articles = it
            adapter?.notifyDataSetChanged()
        })
        viewModel.setArticlesType("sports")

        val listView = findViewById<ListView>(R.id.listView)
        adapter = ArticlesAdapter()
        listView.adapter = adapter



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