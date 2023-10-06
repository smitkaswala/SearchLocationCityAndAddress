package com.wallet.searchlocationcityandaddress

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wallet.searchlocationcityandaddress.retrofit.ResponseSearch
import com.wallet.searchlocationcityandaddress.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        SearchLocation("surat")

    }

    private fun SearchLocation(text: CharSequence) {
        val call: Call<List<ResponseSearch?>?>? = RetrofitClient.getInstance().getModel().getSearch(
            "application/x-www-form-urlencoded", text,
            "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94", "en-us", "value"
        )
        call?.enqueue(object : Callback<List<ResponseSearch?>?> {
            override fun onResponse(
                call: Call<List<ResponseSearch?>?>,
                response: Response<List<ResponseSearch?>?>
            ) {
                val responseSearch = ArrayList<ResponseSearch>()
                val search: List<ResponseSearch?>? = response.body()
                Log.e("TAG", "onResponse:---$search")
            }

            override fun onFailure(call: Call<List<ResponseSearch?>?>, t: Throwable) {}
        })
    }

}