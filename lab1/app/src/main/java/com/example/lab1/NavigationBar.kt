package com.example.lab1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MainScreen() {
    //declare navigation controller to redirect to another screens
    val navController = rememberNavController()

    // in scaffold we create bottom navigation bar
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        // create nav host container.
        // It has composables(screens) and nav controller to navigate to them
        NavHost(
            navController = navController,
            startDestination = "linear",
            modifier = Modifier.padding(innerPadding)
        ) {
            //we create composables that are objects of AlgorithmScreenUI
            composable("linear") {
                AlgorithmScreen(
                    algorithm = Algorithm.LinearAlgo()
                )
            }
            composable("conditional") {
                AlgorithmScreen(
                    algorithm = Algorithm.ConditionAlgo()
                )
            }
            composable("cycle") {
                AlgorithmScreen(
                    algorithm = Algorithm.CycleAlgo()
                )
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(navController: NavHostController) {
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
    //declare variable for saving current screen
    var currentScreenState by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = Color.DarkGray
    ) {
        navItems.forEachIndexed { index, navItem ->
            val isSelected = currentScreenState == index
            NavigationBarItem(
                // Remove white outline around selected icons
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                selected = isSelected,
                onClick = {
                    currentScreenState = index
                    navController.navigate(navItem.route) {
                        //save state
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        //only one instance of screen
                        launchSingleTop = true
                        //restore state
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        modifier = Modifier.size(64.dp).padding(8.dp),
                        contentDescription = navItem.title,
                        //add color to icon if it is selected or not
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else Color(
                            0xAAFFFFFF),
                    )
                },
                //add label if selected
                label = {
                    if (isSelected) {
                        Text(
                            navItem.title,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    }
}
