package com.example.fwa

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fwa.presentation.NavScreen
import com.example.fwa.ui.theme.FWATheme
import com.example.fwp.Comment
import com.example.fwp.CommunityPost
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            setContent {
            FWATheme {
              NavScreen()
            }
        }
    }
}
val posts = listOf(
    CommunityPost(
        userId = "ecoUser01",
        content = "🍌 Overripe bananas? Don’t toss them! Freeze them and use for smoothies or banana bread. #FoodWasteTips",
        imageUrl = "",
        timestamp = System.currentTimeMillis(),
        likedBy = listOf("userA", "userB"),
        comments = listOf(
            Comment("userC", "Great idea! I love making banana pancakes too.", System.currentTimeMillis())
        )
    ),
    CommunityPost(
        userId = "kitchenHacks2025",
        content = "🥦 Don’t throw away broccoli stems! Slice thinly and stir-fry them or add to soups. #ZeroWasteCooking",
        imageUrl = "",
        timestamp = System.currentTimeMillis(),
        likedBy = listOf("userD")
    ),
    CommunityPost(
        userId = "mealPlanner",
        content = "📆 Plan your meals before shopping to avoid impulse buys and reduce waste. Use everything you buy!",
        imageUrl = "",
        timestamp = System.currentTimeMillis()
    ),
    CommunityPost(
        userId = "noWasteNina",
        content = "♻️ Leftover rice? Turn it into fried rice or rice pudding the next day. So many ways to reuse it!",
        imageUrl = "",
        timestamp = System.currentTimeMillis(),
        likedBy = listOf("userE", "userF", "userG")
    ),
    CommunityPost(
        userId = "homeChefTips",
        content = "🥕 Store herbs like parsley in a glass of water in the fridge. Keeps them fresh much longer!",
        imageUrl = "",
        timestamp = System.currentTimeMillis()
    ),
    CommunityPost(
        userId = "greenCook",
        content = "🥬 Wilted greens? Sauté them with garlic and olive oil – delicious and saves waste.",
        imageUrl = "",
        timestamp = System.currentTimeMillis(),
        comments = listOf(
            Comment("userZ", "Works great with spinach too!", System.currentTimeMillis())
        )
    )
)

fun uploadPostsToFirestore(posts: List<CommunityPost>) {
    val db = FirebaseFirestore.getInstance()
    val postsCollection = db.collection("posts")

    posts.forEach { post ->
        postsCollection.add(post)
            .addOnSuccessListener { println("Post added: ${it.id}") }
            .addOnFailureListener { e -> println("Error: $e") }
    }
}


fun main(){
//    addRecipeToFirestore(potatoPeelChips)
}

// Step 1: Define the data class
data class Recipe(
    @get:Exclude val id: String = "",
    val title: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val imageUrl: String = "",
    val postedBy: String = ""
)

// Step 2: Function to add a recipe to Firestore
fun addRecipeToFirestore(recipe: Recipe) {
    val db = FirebaseFirestore.getInstance()

    db.collection("recipes")
        .add(recipe)
        .addOnSuccessListener { documentReference ->
            println("Recipe added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error adding recipe: $e")
        }
}
val potatoPeelChips = Recipe(
    title = "Crispy Potato Peel Chips",
    ingredients = listOf(
        "Peels from 4–5 potatoes (washed before peeling)",
        "1–2 tbsp olive oil",
        "Salt",
        "Smoked paprika or other spices (optional)"
    ),
    steps = listOf(
        "Preheat oven to 200°C (400°F).",
        "Toss potato peels with olive oil, salt, and any spices.",
        "Spread on a baking sheet in a single layer.",
        "Bake for 10–15 minutes, flipping halfway, until crisp.",
        "Let cool slightly before serving."
    ),
    imageUrl = "https://www.eatingwell.com/thmb/ixE0yw4ojvdfM0FfP4xfZmYi_oI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/PotatoPeelChips-step-5-283-2188a2e8c8c44726b62513328859ebe5.jpg",
    postedBy = "crispyPeels"
)



// Step 4: Add the recipe (e.g., inside onCreate or a button click)





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FWATheme {

    }
}