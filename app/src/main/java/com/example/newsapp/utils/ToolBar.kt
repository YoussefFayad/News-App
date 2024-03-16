package com.example.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
    onNavigationClick : () -> Unit,
    onSettingsClick: () -> Unit,
    onCategoriesClick: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.news_app),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,

                )
        },colors = TopAppBarDefaults.topAppBarColors(
            containerColor = green,
            titleContentColor = Color.White
        ),
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomStart = 24.dp,
                bottomEnd = 24.dp
            )
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(id = R.string.navigation_drawer_icon),
                modifier = Modifier
                    .padding(15.dp)
                    .clickable {
                        onNavigationClick()
                    }
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(id = R.string.icon_search),
                modifier = Modifier.padding(15.dp)
            )
        }
    )
}