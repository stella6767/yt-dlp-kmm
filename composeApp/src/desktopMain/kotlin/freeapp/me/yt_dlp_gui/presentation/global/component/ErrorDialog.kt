package freeapp.me.yt_dlp_gui.presentation.global.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import freeapp.me.yt_dlp_gui.domain.model.DataError
import freeapp.me.yt_dlp_gui.domain.util.getErrorMessage
import io.kanro.compose.jetbrains.expui.control.ActionButton
import io.kanro.compose.jetbrains.expui.control.Icon
import io.kanro.compose.jetbrains.expui.control.Label

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    error: DataError
) {

    val (icon, iconColor) = when (error) {
        DataError.Remote.REQUEST_TIMEOUT, DataError.Remote.TOO_MANY_REQUESTS,
        DataError.Remote.NO_INTERNET, DataError.Remote.SERVER,
        DataError.Remote.SERIALIZATION, -> {
            Icons.Default.NetworkCheck to Color(0xFFF44336)
        }
        else ->  {
            Icons.Default.Error to Color(0xFFFF9800)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                markerColor = iconColor,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
//            Text(
//                text = "tiele",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center
//            )
        },
        text = {
            Label(
                text = getErrorMessage(error),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            ActionButton(
                onClick = { onDismiss() },
            ) {
                Label("ok")
            }

        }
    )
}
