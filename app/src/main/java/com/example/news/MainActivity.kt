package com.example.news

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), Myadapter.setonitemclclick {
    private lateinit var mAdaptor: Myadapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getnews()
        mAdaptor = Myadapter(this)
        recyclerView.adapter = mAdaptor


    }

    private fun getnews() {

//val url = "https://newsapi.org/v2/everything?q=tesla&from=2022-11-07&sortBy=publishedAt&apiKey=1832501627f84d85ad50822c739cdb69"
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        // val url ="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=39de141f44e847f99a8ecec49a68b444"
        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val jsonArray = it.getJSONArray("articles")
                val arraylist = ArrayList<NewsData>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val news = NewsData(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("description"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage"),
                        jsonObject.getString("publishedAt")
                    )
                    arraylist.add(news)

                }
                mAdaptor.updateddata(arraylist)
            },
            {
                Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
            })
        Mysingleton.getInstance(this).addToRequestQueue(jsonRequest)

    }

    override fun onitemclicks(items: NewsData) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this@MainActivity, Uri.parse(items.url))
    }


}