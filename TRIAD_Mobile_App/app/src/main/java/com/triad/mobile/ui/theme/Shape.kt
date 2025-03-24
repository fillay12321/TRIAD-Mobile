package com.triad.mobile.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Формы для различных компонентов интерфейса
val TriadShapes = Shapes(
    // Маленькие компоненты: кнопки, чипы и т.д.
    small = RoundedCornerShape(4.dp),
    
    // Средние компоненты: карточки, диалоги и т.д.
    medium = RoundedCornerShape(8.dp),
    
    // Большие компоненты: листы снизу и т.д.
    large = RoundedCornerShape(16.dp),
    
    // Экстра-большие компоненты: полноэкранные модальные окна и т.д.
    extraLarge = RoundedCornerShape(24.dp)
) 