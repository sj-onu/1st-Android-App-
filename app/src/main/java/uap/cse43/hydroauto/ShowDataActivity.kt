package uap.cse43.hydroauto

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {
    var listView: ListView? = null
    var heroList: MutableList<Hero>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<View>(R.id.listView) as ListView
        heroList = ArrayList<Hero>()
        loadHeroList()
    }

    private fun loadHeroList() {
        val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        progressBar.visibility = View.VISIBLE
        val stringRequest = StringRequest(Request.Method.GET, JSON_URL,
                { response ->
                    progressBar.visibility = View.INVISIBLE
                    try {
                        val obj = JSONObject(response)
                        val heroArray: JSONArray = obj.getJSONArray("feeds")
                        for (i in 0 until heroArray.length()) {
                            val heroObject = heroArray.getJSONObject(i)
                            val hero = Hero(heroObject.getString("created_at"), heroObject.getString("field1"))
                            heroList!!.add(hero)
                        }
                        val adapter = ListViewAdapter(heroList, applicationContext)
                        listView!!.adapter = adapter
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
        ) { error -> Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_SHORT).show() }
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    companion object {
        private const val JSON_URL = "https://api.thingspeak.com/channels/1334605/feeds.json"
    }
}