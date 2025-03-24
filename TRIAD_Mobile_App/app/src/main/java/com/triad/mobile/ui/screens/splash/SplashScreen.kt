package com.triad.mobile.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreen(
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    // Состояния анимации
    var showHackerEffect by remember { mutableStateOf(true) }
    var showLogo by remember { mutableStateOf(false) }
    var hasWallet by remember { mutableStateOf<Boolean?>(null) }
    
    // Логика навигации
    LaunchedEffect(Unit) {
        // Показываем хакерские цифры
        delay(2000)
        // Начинаем переход к логотипу
        showHackerEffect = false
        showLogo = true
        // Проверяем, есть ли кошелек
        delay(2500) // Даем время для анимации логотипа
        hasWallet = viewModel.hasWallet()
        
        // Выполняем навигацию
        if (hasWallet == true) {
            navigateToHome()
        } else {
            navigateToOnboarding()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Эффект бегающих цифр
        HackerEffect(
            isVisible = showHackerEffect,
            modifier = Modifier.fillMaxSize()
        )
        
        // Анимация логотипа
        TriadLogo(
            isVisible = showLogo,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HackerEffect(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val transitionState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    
    LaunchedEffect(isVisible) {
        transitionState.targetState = isVisible
    }
    
    AnimatedVisibility(
        visibleState = transitionState,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(800))
    ) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current
        val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
        val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
        
        val infiniteTransition = rememberInfiniteTransition(label = "hacker")
        val animationProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "hacker_digits"
        )
        
        // Анимация для второй группы цифр
        val secondAnimationProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "hacker_digits_2"
        )
        
        Canvas(modifier = modifier) {
            // Создаем случайные позиции для цифр
            val numDigits = 800
            
            // Первый слой - бинарные цифры
            for (i in 0 until numDigits / 2) {
                val x = Random.nextFloat() * screenWidth
                val y = (Random.nextFloat() * screenHeight + animationProgress * 80) % screenHeight
                
                // Только бинарные цифры (0 и 1)
                val digit = if (Random.nextBoolean()) "0" else "1"
                
                // Рисуем цифру с зеленым цветом и случайной прозрачностью
                drawContext.canvas.nativeCanvas.drawText(
                    digit,
                    x,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.GREEN
                        alpha = (Random.nextFloat() * 180 + 75).toInt() // Прозрачность от 75 до 255
                        textSize = Random.nextFloat() * 16 + 10 // Размер от 10 до 26
                    }
                )
            }
            
            // Второй слой - все цифры
            for (i in 0 until numDigits / 2) {
                val x = Random.nextFloat() * screenWidth
                val y = (Random.nextFloat() * screenHeight + secondAnimationProgress * 50) % screenHeight
                
                // Выбираем случайную цифру и символы
                val characters = "0123456789$#@*{}[]()<>~?!"
                val char = characters[Random.nextInt(characters.length)].toString()
                
                // Рисуем с разными оттенками зеленого
                val green = 150 + Random.nextInt(105)
                
                drawContext.canvas.nativeCanvas.drawText(
                    char,
                    x,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.rgb(0, green, 0)
                        alpha = (Random.nextFloat() * 220 + 35).toInt() // Прозрачность от 35 до 255
                        textSize = Random.nextFloat() * 20 + 8 // Размер от 8 до 28
                    }
                )
            }
            
            // Вертикальные светящиеся линии
            val numLines = 10
            for (i in 0 until numLines) {
                val x = Random.nextFloat() * screenWidth
                val startY = Random.nextFloat() * screenHeight
                val length = Random.nextFloat() * 100 + 50
                
                drawLine(
                    color = Color(0, 255, 0, (Random.nextFloat() * 60 + 20).toInt()),
                    start = Offset(x, startY),
                    end = Offset(x, startY + length),
                    strokeWidth = Random.nextFloat() * 2 + 1
                )
            }
        }
    }
}

@Composable
fun TriadLogo(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedVisibilityState = remember {
        MutableTransitionState(false).apply {
            targetState = isVisible
        }
    }
    
    LaunchedEffect(isVisible) {
        animatedVisibilityState.targetState = isVisible
    }
    
    // Анимация треугольников
    val triangleProgress = remember { Animatable(0f) }
    LaunchedEffect(isVisible) {
        if (isVisible) {
            triangleProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                )
            )
        }
    }
    
    // Анимация текста
    val textAlpha = remember { Animatable(0f) }
    LaunchedEffect(triangleProgress.value) {
        if (triangleProgress.value >= 0.9f && isVisible) {
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = LinearEasing
                )
            )
        }
    }
    
    // Пульсация свечения
    val glowTransition = rememberInfiniteTransition(label = "glow")
    val glowAnimation by glowTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulsation"
    )
    
    AnimatedVisibility(
        visibleState = animatedVisibilityState,
        enter = fadeIn(tween(500))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(16.dp)
        ) {
            // Три треугольника
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Рисуем треугольники с анимацией
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val center = Offset(width / 2, height / 2)
                    
                    // Размер треугольников зависит от анимации
                    val triangleSize = 100f * triangleProgress.value
                    
                    // Первый треугольник (верхний)
                    val path1 = Path().apply {
                        val top = Offset(center.x, center.y - triangleSize)
                        val bottomLeft = Offset(center.x - triangleSize, center.y + triangleSize / 2)
                        val bottomRight = Offset(center.x + triangleSize, center.y + triangleSize / 2)
                        
                        moveTo(top.x, top.y)
                        lineTo(bottomLeft.x, bottomLeft.y)
                        lineTo(bottomRight.x, bottomRight.y)
                        close()
                    }
                    
                    // Второй треугольник (слева внизу)
                    val path2 = Path().apply {
                        val left = Offset(center.x - triangleSize, center.y)
                        val rightTop = Offset(center.x + triangleSize / 2, center.y - triangleSize / 2)
                        val rightBottom = Offset(center.x + triangleSize / 2, center.y + triangleSize)
                        
                        moveTo(left.x, left.y)
                        lineTo(rightTop.x, rightTop.y)
                        lineTo(rightBottom.x, rightBottom.y)
                        close()
                    }
                    
                    // Третий треугольник (справа внизу)
                    val path3 = Path().apply {
                        val right = Offset(center.x + triangleSize, center.y)
                        val leftTop = Offset(center.x - triangleSize / 2, center.y - triangleSize / 2)
                        val leftBottom = Offset(center.x - triangleSize / 2, center.y + triangleSize)
                        
                        moveTo(right.x, right.y)
                        lineTo(leftTop.x, leftTop.y)
                        lineTo(leftBottom.x, leftBottom.y)
                        close()
                    }
                    
                    // Рисуем свечение для треугольников (внутренний слой)
                    if (triangleProgress.value > 0.5f) {
                        val glowAlpha = (100 * glowAnimation).toInt()
                        
                        drawPath(
                            path = path1,
                            color = Color(0, 255, 0, glowAlpha),
                            style = Stroke(width = 10f * glowAnimation, cap = StrokeCap.Round)
                        )
                        
                        drawPath(
                            path = path2,
                            color = Color(0, 255, 0, glowAlpha),
                            style = Stroke(width = 10f * glowAnimation, cap = StrokeCap.Round)
                        )
                        
                        drawPath(
                            path = path3,
                            color = Color(0, 255, 0, glowAlpha),
                            style = Stroke(width = 10f * glowAnimation, cap = StrokeCap.Round)
                        )
                    }
                    
                    // Рисуем треугольники линиями (внешний слой)
                    drawPath(
                        path = path1,
                        color = Color(0, 255, 0, 255),
                        style = Stroke(width = 4f, cap = StrokeCap.Round)
                    )
                    
                    drawPath(
                        path = path2,
                        color = Color(0, 255, 0, 255),
                        style = Stroke(width = 4f, cap = StrokeCap.Round)
                    )
                    
                    drawPath(
                        path = path3,
                        color = Color(0, 255, 0, 255),
                        style = Stroke(width = 4f, cap = StrokeCap.Round)
                    )
                }
            }
            
            // Текст TRIAD
            Text(
                text = "TRIAD",
                style = TextStyle(
                    color = Color(0, (200 + 55 * glowAnimation).toInt(), 0),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .alpha(textAlpha.value)
                    .padding(top = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Подзаголовок
            Text(
                text = "Blockchain Technology",
                style = TextStyle(
                    color = Color(0, (180 + 75 * glowAnimation).toInt(), 0),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.alpha(textAlpha.value * 0.8f)
            )
        }
    }
} 