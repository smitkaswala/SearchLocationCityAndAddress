package com.wallet.searchlocationcityandaddress

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.wallet.searchlocationcityandaddress.retrofit.ResponseSearch
import java.util.Locale

class AutoSuggestAdapter(private var context: Activity, resource: Int, textViewResourceId: Int, private var mListData: ArrayList<ResponseSearch>) :
    ArrayAdapter<ResponseSearch?>(context, resource, textViewResourceId, mListData as List<ResponseSearch?>), Filterable {
    private val mTempItem: ArrayList<ResponseSearch>? = null
    private var mSuggestion =  arrayListOf<ResponseSearch>()
    fun setData(list: ArrayList<ResponseSearch>?) {
        mListData.clear()
        mListData.addAll(list!!)
    }

    override fun getCount(): Int {
        return mListData.size
    }

    override fun getItem(position: Int): ResponseSearch? {
        return mListData[position]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item_search_auto, parent, false)
        }
        val search = mListData[position]
        if (search != null) {
            val locationName = view!!.findViewById<TextView>(R.id.text1)
            locationName?.text = search.localizedName + "," + search.country?.localizedName
        }
        return view!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String? {
            return (resultValue as ResponseSearch).localizedName
        }

        override fun performFiltering(constraint: CharSequence): FilterResults {
            return run {
                mSuggestion.clear()
                if (mTempItem != null) {
                    for (search in mTempItem) {
                        val name: String? = search.localizedName
                        if (name?.lowercase(Locale.getDefault())?.contains(
                                constraint.toString().lowercase(
                                    Locale.getDefault()
                                )
                            ) == true
                        ) {
                            mSuggestion.add(search)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = mSuggestion
                filterResults.count = mSuggestion.size
                filterResults
            }
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            try {
                val responseSearches = results.values as ArrayList<ResponseSearch>
                if (results != null && results.count > 0) {
                    clear()
                    for (search in responseSearches) {
                        add(search)
                        notifyDataSetChanged()
                    }
                }
            } catch (e: ConcurrentModificationException) {
            }
        }
    }

    init {
        mListData = ArrayList()
        mSuggestion = ArrayList()
    }
}