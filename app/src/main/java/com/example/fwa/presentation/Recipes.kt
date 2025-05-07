package com.example.fwa.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.fwa.ui.theme.AppBackground
import com.example.fwp.Recipe

@Composable
fun RecipesScreen(navController: NavController) {
    val sampleRecipe = Recipe(
        id = "1",
        title = "Creamy Garlic Chicken",
        ingredients = listOf(
            "4 chicken breasts",
            "2 tbsp olive oil",
            "4 garlic cloves, minced",
            "1 cup heavy cream",
            "1/2 cup chicken broth",
            "Salt and pepper to taste",
            "Fresh parsley for garnish"
        ),
        steps = listOf(
            "Heat olive oil in a skillet over medium heat.",
            "Add chicken breasts and cook until golden and cooked through. Remove and set aside.",
            "In the same pan, sauté minced garlic until fragrant.",
            "Pour in chicken broth and heavy cream. Stir and let it simmer for 5 minutes.",
            "Return the chicken to the pan and coat with the creamy sauce.",
            "Simmer for another 5 minutes until everything is well combined.",
            "Serve hot and garnish with fresh parsley."
        ),
        imageUrl = "https://www.budgetbytes.com/wp-content/uploads/2024/02/Creamy-Garlic-Chicken-Pan.jpg", // example Unsplash image
        postedBy = "Chef Olivia"
    )
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

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        RecipesItem(
                            modifier = Modifier.weight(1f),
                            recipe = sampleRecipe,
                            navController
                        )
                        Spacer(modifier = Modifier.width(13.dp))
                        RecipesItem(
                            modifier = Modifier.weight(1f),
                            recipe = sampleRecipe,
                            navController
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        RecipesItem(
                            modifier = Modifier.weight(1f),
                            recipe = sampleRecipe,
                            navController
                        )
                        Spacer(modifier = Modifier.width(13.dp))
                        RecipesItem(
                            modifier = Modifier.weight(1f),
                            recipe = sampleRecipe,
                            navController
                        )
                    }
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
                navController.navigate(Screen.RecipeDetail.route){
//                    popUpTo(0){
//
//                    }
                }
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
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        )
        Text(recipe.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
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
    RecipesScreen(navController)
}