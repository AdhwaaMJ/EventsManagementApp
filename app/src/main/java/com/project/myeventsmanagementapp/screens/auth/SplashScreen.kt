package com.project.myeventsmanagementapp.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.myeventsmanagementapp.R
import com.project.myeventsmanagementapp.navigation.Screens
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor
import com.project.myeventsmanagementapp.ui.theme.Purple40
import org.checkerframework.checker.units.qual.A

@Composable
fun SplashScreen(navController: NavHostController) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Image(painter = painterResource(id = R.drawable.background), contentDescription ="")
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    text = "Dailoz" ,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Text(
                    text = "Plan what you will do to be more organized for today, tomorrow and beyond",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 33.dp)
                    )
                
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {navController.navigate(Screens.Authentication.Login.route) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(10.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text ="Login",
                        fontSize = 15.sp,
                        color = Color.White)
                }

                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable { navController.navigate(Screens.Authentication.SignUp.route) },
                    text = "Sign Up",
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

            }
}

//@Preview(showBackground = true)
//@Composable
//fun splashPreview(){
//    SplashScreen()
//}


