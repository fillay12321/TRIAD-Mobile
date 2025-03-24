package com.triad.mobile.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.triad.mobile.R
import kotlinx.coroutines.launch

/**
 * Экран онбординга для первого запуска приложения
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navigateToCreateWallet: () -> Unit,
    navigateToImportWallet: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    // Данные страниц онбординга
    val pages = listOf(
        OnboardingPage(
            title = "Добро пожаловать в TRIAD",
            description = "Децентрализованный блокчейн нового поколения для быстрых и безопасных транзакций",
            imageResId = R.drawable.onboarding_welcome
        ),
        OnboardingPage(
            title = "Безопасность прежде всего",
            description = "Ваши ключи и данные всегда под надежной защитой с современной криптографией",
            imageResId = R.drawable.onboarding_security
        ),
        OnboardingPage(
            title = "Полный контроль",
            description = "Управляйте своими активами, отправляйте и получайте средства в любое время без ограничений",
            imageResId = R.drawable.onboarding_control
        )
    )
    
    // Состояние страниц
    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()
    
    // Определяем, находимся ли мы на последней странице
    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == pages.size - 1 }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Контент страниц
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { position ->
            OnboardingPageContent(
                page = pages[position],
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Индикаторы страниц
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage
            )
        }
        
        // Кнопка "Далее" или кнопки действия на последней странице
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            AnimatedVisibility(
                visible = !isLastPage,
                enter = fadeIn(tween(200)),
                exit = fadeOut(tween(200))
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0, 200, 0)
                    )
                ) {
                    Text(
                        text = "Далее",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            AnimatedVisibility(
                visible = isLastPage,
                enter = fadeIn(tween(200)),
                exit = fadeOut(tween(200))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            // Создание нового кошелька
                            navigateToCreateWallet()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0, 200, 0)
                        )
                    ) {
                        Text(
                            text = "Создать новый кошелек",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    TextButton(
                        onClick = {
                            // Импорт существующего кошелька
                            navigateToImportWallet()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "У меня уже есть кошелек",
                            fontSize = 16.sp,
                            color = Color(0, 180, 0)
                        )
                    }
                }
            }
        }
    }
    
    // Отмечаем, что онбординг пройден
    LaunchedEffect(isLastPage) {
        if (isLastPage) {
            viewModel.markOnboardingComplete()
        }
    }
}

/**
 * Содержимое одной страницы онбординга
 */
@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 80.dp, bottom = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Изображение
        Image(
            painter = painterResource(id = page.imageResId),
            contentDescription = page.title,
            modifier = Modifier
                .weight(1f)
                .size(280.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Заголовок
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Описание
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Индикатор страниц
 */
@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentPage) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) Color(0, 200, 0)
                            else Color.Gray.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

/**
 * Данные страницы онбординга
 */
data class OnboardingPage(
    val title: String,
    val description: String,
    val imageResId: Int
) 