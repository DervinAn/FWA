package com.example.fwa


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fwp.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun AuthScreen(navController: NavController) {
    var isLoginScreen by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoginScreen) {
                LoginScreen(onSwitch = { isLoginScreen = false },
                    success = {
                        navController.navigate(Screen.Home.route){
                        }
                    })
            } else {
                SignUpScreen(onSwitch = { isLoginScreen = true },
                    success = {
                        navController.navigate(Screen.Home.route){
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onSwitch: () -> Unit,success: ()-> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedFoodBackground()// ⬅️ Background layer

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var logMessage by remember { mutableStateOf("") }

            Text(
                "Login",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GradientButton(
                text = "Login",
                onClick = {
                    signIn(email, password) { success, error ->
                        if (success) {
                            getUserData { user ->
                                logMessage = user?.let {
                                    "✅ Signed in. ID: ${it.id}, Name: ${it.name}, Email: ${it.email}, Location: ${it.location}"
                                } ?: "❌ Failed to fetch user data"
                            }
                            success()
                        } else {
                            logMessage = "❌ Sign-in failed: $error"
                        }
                    }
                },
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))


            TextButton(onClick = onSwitch) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}

@Composable
fun SignUpScreen(onSwitch: () -> Unit,success: ()-> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var logMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedFoodBackground()// ⬅️ Background layer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Sign Up", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") })

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = logMessage, modifier = Modifier.padding(top = 16.dp))

            GradientButton(
                onClick = {
                    signUp(email, password, username) { success, error ->
                         if (success) {
                             logMessage = "✅ Sign-up successful"
                             success()
                        } else {
                             logMessage = "❌ Sign-up failed: $error"
                        }
                    }
                },
                text = "Sign in",
                modifier = Modifier
            )


            TextButton(onClick = onSwitch) {
                Text("Already have an account? Login")
            }

            SocialLoginButtons(
                onGoogleSignIn = {
                    // Launch Google Sign-In Intent
                },
                onFacebookSignIn = {
                    // Launch Facebook Sign-In Flow
                }
            )
        }
    }

}


fun signUp(
    email: String,
    password: String,
    username: String,
    onResult: (Boolean, String?) -> Unit
) {
    val auth = Firebase.auth
    val db = Firebase.firestore

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = auth.currentUser?.uid
                val photoUrl = auth.currentUser?.photoUrl?.toString() ?: ""

                if (uid != null) {
                    val user = User(
                        name = username,
                        email = email,
                        photoUrl = photoUrl,
                        location = ""
                    )

                    db.collection("users").document(uid).set(user)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }
                } else {
                    onResult(false, "UID is null after sign-up")
                }
            } else {
                onResult(false, task.exception?.localizedMessage ?: "Unknown error during sign-up")
            }
        }
}

fun signIn(
    email: String,
    password: String,
    onResult: (Boolean, String?) -> Unit
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, task.exception?.message)
            }
        }
}

fun getUserData(onResult: (User?) -> Unit) {
    val uid = Firebase.auth.currentUser?.uid ?: return onResult(null)
    val db = Firebase.firestore

    db.collection("users").document(uid).get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val user = document.toObject(User::class.java)?.copy(id = document.id)
                onResult(user)
            } else {
                onResult(null)
            }
        }
        .addOnFailureListener {
            onResult(null)
        }
}


@Composable
fun AnimatedFoodBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "food float")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 200f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offsetY"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.apple), // Replace with your drawable
            contentDescription = "Apple",
            modifier = Modifier
                .size(64.dp)
                .offset(x = 50.dp, y = offsetY.dp)
                .alpha(0.3f)
        )

        Image(
            painter = painterResource(id = R.drawable.composting), // Another icon
            contentDescription = "Bread",
            modifier = Modifier
                .size(48.dp)
                .offset(x = 300.dp, y = (offsetY/1.2).dp)
                .alpha(0.4f)
        )
        Image(
            painter = painterResource(id = R.drawable.bread), // Another icon
            contentDescription = "Bread",
            modifier = Modifier
                .size(48.dp)
                .offset(x = 250.dp, y = (offsetY / 2).dp)
                .alpha(0.4f)
        )
        Image(
            painter = painterResource(id = R.drawable.disposal), // Another icon
            contentDescription = "Bread",
            modifier = Modifier
                .size(48.dp)
                .offset(x = 120.dp, y = (offsetY / 3).dp)
                .alpha(0.4f)
        )
        Image(
            painter = painterResource(id = R.drawable.baguette), // Another icon
            contentDescription = "Bread",
            modifier = Modifier
                .size(48.dp)
                .offset(x = 200.dp, y = (offsetY / 1.8).dp)
                .alpha(0.4f)
        )
        Image(
            painter = painterResource(id = R.drawable.recycle), // Another icon
            contentDescription = "Bread",
            modifier = Modifier
                .size(48.dp)
                .offset(x = 10.dp, y = (offsetY / 3).dp)
                .alpha(0.4f)
        )
    }
}


@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xD8C2CE4E), Color(0xFFADCB8F)),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 0f) // Adjust for direction
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SocialLoginButtons(
    onGoogleSignIn: () -> Unit,
    onFacebookSignIn: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onGoogleSignIn,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Continue with Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onFacebookSignIn,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4267B2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Continue with Facebook", color = Color.White)
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {

}
