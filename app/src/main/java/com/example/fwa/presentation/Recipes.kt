package com.example.fwa.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.fwa.ui.theme.AppBackground
import com.example.fwp.Recipe
import java.nio.file.WatchEvent

@Composable
fun RecipesScreen(
    navController: NavController,
    viewModel: RecipeViewModel = viewModel(),
) {
    val state = viewModel.uiState.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Recipes", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.fillMaxHeight(0.09f))

            val uiState = state.value

            when (uiState) {
                is RecipeUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is RecipeUiState.Success -> {
                    val recipes = uiState.recipes
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(recipes.size) { rowRecipes ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                RecipesItem(
                                    modifier = Modifier.weight(1f),
                                    recipe = recipes[rowRecipes],
                                    navController = navController
                                )
                                Spacer(modifier = Modifier.width(13.dp))
                            }
                        }
                    }

                }

                is RecipeUiState.Error -> {
                    Text(
                        text = "❌ ${uiState.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }

}

@Composable
fun RecipesItem(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    navController: NavController,
) {
    Column (
        modifier = modifier
            .clickable{
                navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
            }
            .background(Color(0xFFD8DAA7), shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        )
        Text(
            recipe.title,
            fontSize = 18.sp,
            modifier = Modifier.padding(6.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            items(recipe.ingredients.size) {
                Spacer(modifier = Modifier.width(5.dp))
            Text(
                recipe.ingredients[it],
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipesPreview() {
    val navController = rememberNavController()
//    RecipesScreen(navController)
}