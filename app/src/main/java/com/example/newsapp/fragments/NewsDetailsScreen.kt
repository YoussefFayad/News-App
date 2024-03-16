package com.example.newsapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.NewsActivity
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.Constants
import com.example.newsapp.model.api.ArticleItem
import com.example.newsapp.model.api.ArticlesResponse
import com.example.newsapp.ui.theme.textColor
import com.example.newsapp.utils.NewsTopBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsDetailsScreen(title : String) {
    var newsItem by rememberSaveable {
        mutableStateOf<ArticleItem?>(null)
    }

    var getNewsItemCall : Call<ArticlesResponse>?=null
    LaunchedEffect(key1 = Unit) {
        getNewsItemCall = ApiManager.getNewService().getNews(apiKey=Constants.API_KEY, topic =title)
        getNewsItemCall?.enqueue(object:Callback<ArticlesResponse>{
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>,
            ) {
               if (response.isSuccessful){
                   val news= response.body()?.articles?.get(0)!!
                   newsItem=news
               }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.d("getNewsItemCall onFailure",t.localizedMessage?:"")
            }

        })
        
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            getNewsItemCall?.cancel()
        }
    }

    Scaffold(
        topBar = {
            NewsTopBar(onNavigationClick = {}, onCategoriesClick = {}, onSettingsClick = {})


        }
    ) {paddingValues ->

        newsItem?.let {
            NewsDetailsContent(paddingValues,it)
        }

    }
}

@Composable
fun NewsDetailsContent(paddingValues: PaddingValues, newsItem: ArticleItem?) {

    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .paint(
                painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(rememberScrollState())
    ) {
        newsItem?.let { item ->
            NewsDetailsCard(newsItem = item)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsDetailsCard(newsItem: ArticleItem) {
    val context =(LocalContext.current)as NewsActivity
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Text(
            text = newsItem.content?:"",
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.7f))
        Row (modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.fillMaxHeight(.6f))
            Text(
                text = stringResource(id = R.string.View_Full_Article),
                modifier= Modifier
                    .padding(8.dp)
                    .clickable {
                        val uri = Uri.parse(newsItem.url ?: "")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
                )
                    Image(painter = painterResource(id = R.drawable.right_arrow), contentDescription = null)
        }
    }
}

@Preview
@Composable
fun ShowPreview(){
    NewsDetailsScreen("Business")
}