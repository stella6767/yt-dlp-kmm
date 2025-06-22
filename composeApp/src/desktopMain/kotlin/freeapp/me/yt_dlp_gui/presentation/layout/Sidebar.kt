package freeapp.me.yt_dlp_gui.presentation.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Sidebar(modifier: Modifier = Modifier, function: () -> Unit) {

    Column(modifier = modifier.padding(vertical = 16.dp)) {

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp, vertical = 4.dp),
//            horizontalArrangement = Arrangement.End // 우측에 정렬
//        ) {
//            IconButton(onClick = { function }) { // 아이콘 클릭 시 사이드바 닫기
//                Icon(Icons.Filled.MenuOpen, contentDescription = "Close Sidebar") // 닫기 아이콘
//            }
//        }
        Spacer(Modifier.height(8.dp))

        SidebarItem(icon = Icons.Filled.Home, text = "Home", isSelected = true) // Home is selected
        SidebarItem(icon = Icons.Filled.Keyboard, text = "Keyring")
        SidebarItem(icon = Icons.Filled.History, text = "History")
    }

}

@Composable
fun SidebarItem(icon: ImageVector, text: String, isSelected: Boolean = false, count: Int? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Handle item click */ }
            .background(if (isSelected) Color(0xFFE0E0E0) else Color.Transparent) // Highlight selected
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
        if (count != null) {
            Spacer(Modifier.weight(1f))
            Text(count.toString(), style = MaterialTheme.typography.displayMedium, color = Color.Gray)
        }
    }
}
