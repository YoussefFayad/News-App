package com.example.newsapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.Constants.API_KEY
import com.example.newsapp.model.api.SourceItem
import com.example.newsapp.model.api.SourcesResponse
import com.example.newsapp.ui.theme.green
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewSourcesTabRow(category: String, onTabSelected: (sourceId: String) -> Unit){
    val selectedTabIndex = remember {
        mutableIntStateOf(0)
    }
    val sourcesList = remember {
        mutableStateListOf<SourceItem>()
    }
    LaunchedEffect(Unit) {
        ApiManager
            .getNewService()
            .getSources(apiKey = API_KEY, category = category)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    val sources = response.body()?.sourceItems
                    if (sources?.isNotEmpty() == true) {
                        sourcesList.addAll(sources)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })
    }
        if (sourcesList.isNotEmpty()) {
            LaunchedEffect(Unit) {
                val sourceId = sourcesList[0].id
                onTabSelected(sourceId ?: "")
            }
        }


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex.intValue,
        edgePadding = 16.dp,
        indicator = {},
        divider = {}
    ) {
        sourcesList.forEachIndexed{ index, item ->
            LaunchedEffect(Unit) {
               if (selectedTabIndex.intValue ==0) {
                    onTabSelected(item.id ?:"")
               }
            }
            Tab(
                selected = index == selectedTabIndex.intValue,
                onClick = {
                    selectedTabIndex.intValue =index
                    onTabSelected(item.id ?:"")
                },
                selectedContentColor = Color.White,
                unselectedContentColor = green

            ) {
                Text(
                    text = item.name ?: "",
                    fontSize = 14.sp,
                    modifier = if (selectedTabIndex.intValue ==index)
                        Modifier
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .background(green, CircleShape)
                            .padding(horizontal = 16.dp, vertical = 8.dp)

                    else
                        Modifier
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .border(2.dp, green, CircleShape)
                            .padding(horizontal = 16.dp, vertical = 8.dp)

                )
            }
        }
    }
}