package com.example.fwa.presentation


import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fwa.R
import com.example.fwa.data.AuthRepositoryImpl
import com.example.fwa.domaine.usecase.auth.GetInfoUseCase
import com.example.fwa.domaine.usecase.auth.SignInUseCase
import com.example.fwa.domaine.usecase.auth.SignUpUseCase
import com.example.fwa.presentation.componenet.GradientButton


@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel) {
    var isLoginScreen by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoginScreen) {
                LoginScreen(
                    viewModel = viewModel,
                    onSwitch = { isLoginScreen = false },
                    onSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Authen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                SignUpScreen(
                    viewModel = viewModel,
                    onSwitch = { isLoginScreen = true },
                    onSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Authen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onSwitch: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedFoodBackground()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            Text("Login", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
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
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.signIn(email, password)
                    }
                },
                modifier = Modifier
            )


            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onSwitch) {
                Text("Don't have an account? Sign Up")
            }

            // Observe auth state
            when (authState) {
                is UiState.Loading -> Text("🔄 Signing in...")
                is UiState.Success -> {
                    Text("✅ Signed in successfully")
                    onSuccess()
                    viewModel.clearAuthState()
                }

                is UiState.Error -> Text("❌ ${(authState as UiState.Error).message}")
                else -> {}
            }
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onSwitch: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedFoodBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Inputs...

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
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GradientButton(
                text = "Sign Up",
                onClick = {
                    if (email.isBlank() || password.isBlank() || username.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.signUp(email, password, username)
                    }
                },
                modifier = Modifier
            )

            // Observe auth state
            when (authState) {
                is UiState.Loading -> Text("🔄 Signing up...")
                is UiState.Success<*> -> {
                    Text("✅ Sign-up successful")
                    onSuccess()
                    viewModel.clearAuthState()
                }

                is UiState.Error -> Text("❌ ${(authState as UiState.Error).message}")
                else -> {}
            }

            TextButton(onClick = onSwitch) {
                Text("Already have an account? Login")
            }
        }
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


@Preview
@Composable
private fun AuthScreenPreview() {
    val navController = rememberNavController()
    val authRepository = AuthRepositoryImpl() // ✅ Create instance
    AuthScreen(
        navController = navController,
        viewModel = AuthViewModel(
            authSi = SignInUseCase(authRepository),
            authSp = SignUpUseCase(authRepository),
            authGetInfo = GetInfoUseCase(authRepository)
        ),
    )
}
