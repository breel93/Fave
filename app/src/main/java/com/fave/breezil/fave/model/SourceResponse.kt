package com.fave.breezil.fave.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SourceResponse (
    @SerializedName("status")
    @Expose
    var status:String,

    @SerializedName("sources")
    @Expose
    var sources:List<Sources>

)