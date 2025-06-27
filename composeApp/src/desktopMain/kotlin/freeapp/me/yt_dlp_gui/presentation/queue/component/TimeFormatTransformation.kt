package freeapp.me.yt_dlp_gui.presentation.queue.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class TimeFormatTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val formatted = buildString {
            for (i in original.indices) {
                append(original[i])
                if (i == 1 || i == 3) {
                    append(':')
                }
            }
        }

        return TransformedText(androidx.compose.ui.text.AnnotatedString(formatted), TimeOffsetMapping)
    }

    private object TimeOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset <= 1 -> offset
                offset <= 3 -> offset + 1
                else -> offset + 2
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 2 -> offset
                offset <= 5 -> offset - 1
                else -> offset - 2
            }
        }
    }
}
