package com.wallet.searchlocationcityandaddress

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.wallet.searchlocationcityandaddress.retrofit.ResponseSearch
import com.wallet.searchlocationcityandaddress.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private var autoSuggestAdapter: AutoSuggestAdapter? = null
    private var responseSearch =  arrayListOf<ResponseSearch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchText = findViewById<AutoCompleteTextView>(R.id.edit_1)


        autoSuggestAdapter = AutoSuggestAdapter(this@MainActivity, R.layout.list_item_search_auto, R.id.text1, responseSearch)
        val input = AutoCompleteTextView(this@MainActivity)
        input.setTextColor(resources.getColor(R.color.white))
        searchText.threshold = 1
        searchText.setAdapter(autoSuggestAdapter)

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                searchLocation(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    private fun searchLocation(text: CharSequence) {
        val call: Call<List<ResponseSearch?>?>? = RetrofitClient.getInstance().getModel().getSearch(
            "application/x-www-form-urlencoded", text,
            "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94", "en-us", "value"
        )
        call?.enqueue(object : Callback<List<ResponseSearch?>?> {
            override fun onResponse(
                call: Call<List<ResponseSearch?>?>,
                response: Response<List<ResponseSearch?>?>
            ) {
                val responseSearch = arrayListOf<ResponseSearch>()
                val search: List<ResponseSearch?>? = response.body()

                responseSearch.addAll(search as ArrayList<ResponseSearch>)
                autoSuggestAdapter?.setData(responseSearch)
                autoSuggestAdapter?.notifyDataSetChanged()
                Log.e("TAG", "onResponse:---$search")

            }

            override fun onFailure(call: Call<List<ResponseSearch?>?>, t: Throwable) {}

        })

    }

}