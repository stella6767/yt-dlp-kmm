package freeapp.me.yt_dlp_gui.presentation.setting.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.ComboBox
import io.kanro.compose.jetbrains.expui.control.DropdownMenuItem
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingDropdownRow(
    label: String,
    selectedValue: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }







    Row() {
        Label(
            label,
            modifier = Modifier.padding(),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(Modifier.width(10.dp))
        ComboBox(
            options, selectedValue,
            onOptionSelected,
            modifier = Modifier.width(100.dp),
            menuModifier = Modifier.width(100.dp)
        )
    }


}
