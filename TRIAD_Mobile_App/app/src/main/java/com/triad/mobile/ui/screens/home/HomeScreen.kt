package com.triad.mobile.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Главный экран приложения с информацией о кошельке
 */
@Composable
fun HomeScreen(
    navigateToSend: () -> Unit,
    navigateToReceive: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToWalletDetails: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Домашний экран TRIAD",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Здесь будет отображаться информация о кошельке и балансе",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { navigateToSend() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Отправить TRIAD")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { navigateToReceive() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Получить TRIAD")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { navigateToSettings() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Настройки")
            }
        }
    }
} 