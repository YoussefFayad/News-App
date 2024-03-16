package com.example.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.green

@Composable
fun NewsDrawerSheet(onSettingsClick: () -> Unit, onCategoriesClick: () -> Unit){
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7F)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2F)
                .background(green),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = stringResource(id = R.string.news_app),
                fontSize = 20.sp,
                color = Color.White
            )

        }
        Spacer(modifier = Modifier.padding(8.dp))
        Column {
            DrawerItem(
                iconResId = R.drawable.ic_categories,
                text = stringResource(id = R.string.categories)
            ){
                onCategoriesClick()
            }
            Spacer(modifier = Modifier.padding(8.dp))

            DrawerItem(
                iconResId = R.drawable.ic_settings,
                text = stringResource(id = R.string.settings)
            ){
                onSettingsClick()
            }
        }
    }



}

@Composable
fun DrawerItem(iconResId:Int, text :String,onClickListener:() -> Unit){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 20.dp)
            .clickable { onClickListener() }
    ) {
        Image(
            painter = painterResource(id =iconResId),
            contentDescription = "Navigation Drawer Item"
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }




}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsDrawerSheetPreview(){
    NewsDrawerSheet(
        onSettingsClick= {

         },
        onCategoriesClick= {

        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsDrawerItemPreview(){
    Column {
        DrawerItem(
            iconResId = R.drawable.ic_categories,
            text = stringResource(id = R.string.categories)
        ){}

        DrawerItem(
            iconResId = R.drawable.ic_settings,
            text = stringResource(id = R.string.settings)
        ){}
    }

}