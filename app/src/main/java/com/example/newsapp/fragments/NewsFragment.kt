package com.example.newsapp.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.Constants
import com.example.newsapp.model.api.ArticleItem
import com.example.newsapp.model.api.ArticlesResponse
import com.example.newsapp.utils.NewSourcesTabRow
import com.example.newsapp.utils.NewsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewFragment(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    category: String
){
    val articlesList = remember {
        mutableStateListOf<ArticleItem>()
    }
    Column(modifier = modifier.fillMaxSize()) {
        NewSourcesTabRow(category = category, onTabSelected = { tabSelectedId ->
            ApiManager
                .getNewService()
                .getArticles(apiKey = Constants.API_KEY,tabSelectedId)
                .enqueue(object : Callback<ArticlesResponse> {
                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                        articlesList.clear()
                        val sources = response.body()?.articles
                        if (sources?.isNotEmpty() == true) {
                            articlesList.addAll(sources)
                        }
                    }

                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        })

        NewsList(articlesList.toList())
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewFragmentPreview(){
    //NewFragment(category = "Sports")

}

