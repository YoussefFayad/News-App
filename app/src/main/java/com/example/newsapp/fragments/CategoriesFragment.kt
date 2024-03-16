package com.example.newsapp.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.model.NewsScreen
import com.example.newsapp.ui.theme.textColor


@Composable
fun CategoriesFragment(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.pick_your_category) +
                    "of interest", fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = textColor, modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 18.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CategoriesList(navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesFragmentPreview() {
    CategoriesFragment(rememberNavController())
}

@Composable
fun CategoriesList(navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(Constants.categories.size) { position ->
            CategoryCard(
                category = Constants.categories.get(position),
                position,
                navController = navController
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriesListPreview() {
    CategoriesList(rememberNavController())
}

@Composable
fun CategoryCard(category: Category, index: Int, navController: NavHostController) {
    Card(
        shape = if (index % 2 == 0) RoundedCornerShape(
            bottomStart = 24.dp,
            topEnd = 24.dp,
            topStart = 24.dp
        ) else
            RoundedCornerShape(bottomEnd = 24.dp, topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(
            containerColor = colorResource(id = category.backgroundColor),
        ),
        modifier =  Modifier
            .padding(18.dp)
            .height(140.dp), onClick = {
            navController.navigate(route = "${NewsScreen().route}/${category.titleResID}/${category.apiID}")
        }
    ) {
        Image(
            painter = painterResource(id = category.drawableResId),
            contentDescription = stringResource(
                R.string.category_image
            ),

            modifier = Modifier
                .fillMaxHeight(0.75F)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop

        )
        Text(
            text = stringResource(id = category.titleResID),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )


    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    CategoryCard(category = Constants.categories[0], 0, rememberNavController())
}

