package edu.oaklandcc.young.kyle1.project_3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SecondViewModel : ViewModel() {
    private val API: String = "a334e2bec4fcf4a4dbda000ece87c502"
    private var temp: MutableLiveData<String> = MutableLiveData()
    private var date: MutableLiveData<String> = MutableLiveData()
    private var desc: MutableLiveData<String> = MutableLiveData()
    private var icon: MutableLiveData<String> = MutableLiveData()

    var lon = 0.0
    var lat = 0.0

    private var list: MutableLiveData<ArrayList<Items>> = MutableLiveData()
    private var listItems: ArrayList<Items> = ArrayList()

    fun getList(): MutableLiveData<ArrayList<Items>> {
        return list
    }




    fun getTemp(): MutableLiveData<String>{
        return temp
    }
    fun getDate(): MutableLiveData<String>{
        return date
    }
    fun getDesc(): MutableLiveData<String>{
        return desc
    }
    fun getIcon(): MutableLiveData<String>{
        return icon
    }

    fun currentWeather(queue:RequestQueue, city:String){

        val url="https://api.openweathermap.org/data/2.5/weather?q=$city&units=imperial&appid=$API"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->


                // create JSONObject
                val obj = JSONObject(response)
                val main = obj.getJSONObject("main")
                var t = main.getDouble("temp")
                temp.setValue("%.0f °F".format(t))
                val toDate = obj.getLong("dt")
                date.setValue(SimpleDateFormat("EEE, MMMM dd",Locale.ENGLISH).format(Date(toDate*1000)))
                val weather= obj.getJSONArray("weather").getJSONObject(0)
                desc.setValue(weather.getString("main"))
                var iconUrl = "https://openweathermap.org/img/w/${weather.getString("icon")}.png"
                icon.setValue(iconUrl)
                var coord= obj.getJSONObject("coord")
                lon= coord.getDouble("lon")
                lat=coord.getDouble("lat")
                addItems(queue,lon, lat)


            },
            Response.ErrorListener { temp.value = "0°F" })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
    fun addItems(queue: RequestQueue, lon:Double, lat:Double) {
        listItems.clear()
        val url ="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=minutely,hourly,alert&units=imperial&appid=$API"
        val stringRequest =
            StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response: String? ->
                    // create JSONObject
                    val obj = JSONObject(response)
                    var wlist= obj.getJSONArray("daily")
                    for(i in 0..6) {
                        // get current weather information of a city
                        var temp = wlist.getJSONObject(i).getJSONObject("temp")
                        var daytemp= ("%.0f° F".format(temp.getDouble("day")))
                        val toDate = wlist.getJSONObject(i).getLong("dt")
                        var date=(SimpleDateFormat("EEE, MMMM dd", Locale.ENGLISH).format(Date(toDate * 1000)))
                        var icon= wlist.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")
                        var iconURL = "https://api.openweathermap.org/img/w/$icon.png"
                        listItems.add(Items(daytemp,date,iconURL))
                    }
                    list.setValue(listItems)
                },
                Response.ErrorListener {  "0° F" })
        queue.add(stringRequest)
    }

}