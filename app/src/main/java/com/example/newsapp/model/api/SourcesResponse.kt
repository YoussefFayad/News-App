package com.example.newsapp.model.api


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources")
    val sourceItems: List<SourceItem>? = listOf(), // null

    @SerializedName("status")
    val status: String? = ""
)