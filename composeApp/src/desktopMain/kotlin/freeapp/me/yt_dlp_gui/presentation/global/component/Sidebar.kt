package freeapp.me.yt_dlp_gui.presentation.global.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import freeapp.me.yt_dlp_gui.app.Route


@Composable
fun Sidebar(modifier: Modifier = Modifier, navController: NavHostController) {

    Column(modifier = modifier.padding(vertical = 16.dp)) {

        Spacer(Modifier.height(6.dp))

        SidebarItem(
            icon = Icons.Filled.Queue,
            text = "Queue",
            Route.Queue,
            navController
        )

        SidebarItem(
            icon = Icons.Filled.Download,
            text = "Single Download",
            Route.Downloader,
            navController
        )

        SidebarItem(
            icon = Icons.Filled.Settings,
            text = "Setting",
            Route.Setting,
            navController = navController
        )
    }

}

@Composable
private fun SidebarItem(
    icon: ImageVector,
    text: String,
    route: Route,
    navController: NavHostController
) {

    val destination = navController.currentBackStackEntryAsState().value?.destination
    println(destination?.route)
    println(route::class.qualifiedName)
    val isSelected = destination?.route == route::class.qualifiedName

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route)
            }
            .background(if (isSelected) Color(0xFFE0E0E0) else Color.Transparent) // Highlight selected
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) Color.Red else Color.Gray
        )
        Spacer(Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
    }
}
