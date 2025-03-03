package com.example.lab1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val navItems = listOf(
        BottomNavigationItem(
            title = "Linear",
            icon = ImageVector.vectorResource(R.drawable.icon_linear),
            route = "linear"
        ),
        BottomNavigationItem(
            title = "Conditional",
            icon = ImageVector.vectorResource(R.drawable.icon_conditional),
            route = "conditional"
        ),
        BottomNavigationItem(
            title = "Cycle",
            icon = ImageVector.vectorResource(R.drawable.icon_cycle),
            route = "cycle"
        )
    )

    //declare navigation controller to redirect to another screen
    val navController = rememberNavController()

    //declare variable for saving current screen
    var currentScreenState by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { currentScreenIndex, navItem ->
                    val isSelected = currentScreenState == currentScreenIndex
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            currentScreenState = currentScreenIndex;
                            navController.navigate(navItem.route)
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.title,
                                //add color to icon if it is selected or not
                                tint = if (isSelected) Color(0xFF25E4FF) else Color(0xAAFFFFFF)
                            )
                        },
                        //add label if selected
                        label = {
                            if (isSelected) Text(navItem.title)
                        }
                    )
                }
            }
        }
    ) {

    }
}