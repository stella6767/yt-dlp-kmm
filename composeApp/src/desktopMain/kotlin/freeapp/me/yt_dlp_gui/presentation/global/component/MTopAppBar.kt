package freeapp.me.yt_dlp_gui.presentation.global.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ActionButton
import io.kanro.compose.jetbrains.expui.control.Icon
import io.kanro.compose.jetbrains.expui.control.Tooltip
import io.kanro.compose.jetbrains.expui.window.MainToolBarScope
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import yt_dlp_gui.composeapp.generated.resources.Res
import yt_dlp_gui.composeapp.generated.resources.github
import java.awt.Desktop
import java.net.URI



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainToolBarScope.MTopAppBar(
    onToggleSidebar: () -> Unit
) {
    val svgPainter = painterResource(Res.drawable.github)


    Row(Modifier.mainToolBarItem(Alignment.Start)) {
        Tooltip("Open GitHub link in browser") {
            IconButton(onClick = onToggleSidebar, modifier = Modifier.size(40.dp),) {
                Icon(Icons.Filled.Menu, contentDescription = "Toggle Sidebar")
            }

        }
    }

    Row(Modifier.mainToolBarItem(Alignment.End)) {
        Tooltip("Open GitHub link in browser") {
            ActionButton(
                {
                    Desktop.getDesktop()
                        .browse(URI.create("https://github.com/stella6767/yt-dlp-kmm"))
                }, Modifier.size(40.dp), shape = RectangleShape
            ) {
                Icon(painter = svgPainter)
            }
        }
    }
}
