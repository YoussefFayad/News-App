package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.fragments.CategoriesFragment
import com.example.newsapp.fragments.NewFragment
import com.example.newsapp.model.CategoriesScreen
import com.example.newsapp.model.NewsScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.green
import com.example.newsapp.utils.NewsDrawerSheet
import kotlinx.coroutines.launch


class NewsActivity :  ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            NewsAppTheme {
                NewScreen()
            }
        }

    }
}

@Composable
fun NewScreen(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val toolbarTitle = remember {
        mutableIntStateOf(R.string.news_app)
    }

    ModalNavigationDrawer(
        drawerContent = {
         //Drawer Sheet
            NewsDrawerSheet(
                onSettingsClick= {
                    scope.launch {
                        drawerState.close()
                    }

            }, onCategoriesClick= {
                    navController.popBackStack()
                    if (navController.currentDestination?.route != CategoriesScreen().route) {
                        navController.navigate(CategoriesScreen().route)
                    }
                    scope.launch {
                        drawerState.close()
                    }
                }
            )

        },drawerState =drawerState
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                NewsTopAppBar {
                    scope.launch { drawerState.open() }
                }
            },

            ) {
            NavHost(
                navController =navController,
                startDestination = CategoriesScreen().route,
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.pattern),
                        contentScale = ContentScale.Crop
                    )
                    .padding(top = it.calculateTopPadding()),

            ){
                composable(CategoriesScreen().route) {
                    toolbarTitle.intValue = R.string.news_app
                    CategoriesFragment(navController)
                }
                composable(
                    "${NewsScreen().route}/{category_name}/{category_id}",
                    arguments = listOf(navArgument("category_id") {
                        type = NavType.StringType
                    }, navArgument("category_name") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val categoryId = navBackStackEntry.arguments?.getString("category_id")
                    val categoryName =
                        navBackStackEntry.arguments?.getInt("category_name")
                    toolbarTitle.intValue = categoryName ?: R.string.news_app
                    NewFragment(navHostController = navController   , category = categoryId ?: "")
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(onNavigationClick : () -> Unit){
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




@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewScreenPreview(){
    NewScreen()
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsTopAppBarPreview(){
    NewsTopAppBar{}
}

