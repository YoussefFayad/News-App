package com.example.newsapp.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.R
import com.example.newsapp.model.api.ArticleItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewItemCard(model: ArticleItem, navController: NavController){
    Card (modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .clickable {
            navController.navigate("newsDetails/${model.title}")
    },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ){
        GlideImage(
            model = model.urlToImage?:R.drawable.ic_sports_news_image,
            contentDescription = stringResource(id = R.string.news_image),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3F),
            //contentScale = ContentScale.Crop,
        )
        Text(
            text = model.sourceItem?.name ?: "source",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        Text(
            text = model.title ?: "title",
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
        Text(
            text = model.publishedAt ?:"time",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp)

        )
    }
}


@Composable
fun NewsList(newsList: List<ArticleItem>){
    LazyColumn {
        items(newsList.size){
            //NewItemCard(model = newsList[it],)
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewItemCardPreview(){
    //NewItemCard(model = ArticleItem(),)
}