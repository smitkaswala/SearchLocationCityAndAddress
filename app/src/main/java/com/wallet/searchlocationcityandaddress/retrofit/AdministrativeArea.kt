package com.wallet.searchlocationcityandaddress.retrofit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AdministrativeArea (

    @SerializedName("LocalizedName")
    var localizedName: String? = null,

    @SerializedName("ID")
    var iD: String? = null

) : Serializable