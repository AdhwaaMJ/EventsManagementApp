package com.project.myeventsmanagementapp.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.myeventsmanagementapp.R
import com.project.myeventsmanagementapp.component.LoginWithGoogle
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor


@Composable
fun SignUpScreen(navController: NavController,authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    Text(
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 16.dp),
        text = "Sign Up",
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryColor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            leadingIcon = {Icon(painter = painterResource(id = R.drawable.email), contentDescription = "")},
            modifier = Modifier.fillMaxWidth(0.9f),
            value = userName,
            onValueChange ={userName = it},
            label = {Text(" Username")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors()
        )


        TextField(
            leadingIcon = {Icon(painter = painterResource(id = R.drawable.email), contentDescription = "")},
            modifier = Modifier.fillMaxWidth(0.9f),
            value = email,
            onValueChange ={email = it},
            label = {Text("Email ID or Username")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.colors()
        )


        TextField(
            leadingIcon = {Icon(painter = painterResource(id = R.drawable.password), contentDescription = "")},
            modifier = Modifier.fillMaxWidth(0.9f),
            value = password,
            onValueChange ={password = it},
            label = {Text("Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors()
        )

        Button(onClick = { authViewModel.signup(email,password) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            ))
        {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = " Create Account",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
        {
            Divider(color = Color.LightGray)
            Text(
                modifier = Modifier
                    .background((Color.White))
                    .padding(vertical = 10.dp),
                text = "Or With",
                fontSize = 15.sp,
                color = Color.LightGray
            )
        }

        LoginWithGoogle(authViewModel = AuthViewModel())


    }
}