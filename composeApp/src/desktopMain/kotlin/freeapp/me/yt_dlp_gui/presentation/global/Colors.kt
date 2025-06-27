package freeapp.me.yt_dlp_gui.presentation.global

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val DarkPrimary = Color(0xFFBB86FC) // 더 밝은 보라색 계열
val DarkPrimaryVariant = Color(0xFF3700B3)
val DarkSecondary = Color(0xFF03DAC6)
val DarkBackground = Color(0xFF121212) // 어두운 배경색
val DarkSurface = Color(0xFF121212) // 어두운 표면색
val DarkOnPrimary = Color.Black
val DarkOnSecondary = Color.Black
val DarkOnBackground = Color.White
val DarkOnSurface = Color.White
val DarkError = Color(0xFFCF6679)


val DarkColorPalette = darkColorScheme(
    primary = DarkPrimary, // 다크 모드에서 포인트 색상
    secondary = DarkSecondary,
    background = DarkBackground, // 어두운 배경
    surface = DarkSurface,       // 어두운 표면
    onPrimary = DarkOnPrimary,   // primary 색상 위 텍스트
    onSecondary = DarkOnSecondary, // secondary 색상 위 텍스트
    onBackground = DarkOnBackground, // background 색상 위 일반 텍스트
    onSurface = DarkOnSurface,     // surface 색상 위 일반 텍스트
    error = DarkError
)
