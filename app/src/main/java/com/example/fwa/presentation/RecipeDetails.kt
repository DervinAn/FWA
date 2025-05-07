package com.example.fwa.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import com.example.fwp.Recipe
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent


@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipe: Recipe // Pass the recipe to display
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Recipe Image
            RecipeImageHeader(
                imageUrl = recipe.imageUrl,
                title = recipe.title
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Posted By
            Text(
                text = "Posted by ${recipe.postedBy}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Ingredients Section
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                recipe.ingredients.forEach { ingredient ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = ingredient, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Steps Section
            Text(
                text = "Steps",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                recipe.steps.forEachIndexed { index, step ->
                    Text(
                        text = "${index + 1}. $step",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun RecipeImageHeader(imageUrl: String, title: String) {
    Box {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SubcomposeAsyncImageContent()
            }
        }

        // Optional scroll indicator
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Scroll down",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .size(32.dp)
                .alpha(0.8f),
            tint = Color.White
        )
    }
}


@Preview
@Composable
private fun Preview() {
    val navController = rememberNavController()
    RecipeDetailScreen(navController = navController,Recipe(
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
    )
}
