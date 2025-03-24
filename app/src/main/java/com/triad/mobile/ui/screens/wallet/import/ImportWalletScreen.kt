package com.triad.mobile.ui.screens.wallet.import

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

/**
 * Тип метода импорта кошелька
 */
enum class ImportMethod {
    MNEMONIC,
    PRIVATE_KEY
}

/**
 * Экран импорта кошелька
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportWalletScreen(
    onWalletImported: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ImportWalletViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var selectedImportMethod by remember { mutableStateOf(ImportMethod.MNEMONIC) }
    var walletName by remember { mutableStateOf("") }
    var mnemonic by remember { mutableStateOf("") }
    var privateKey by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    // Состояния для валидации
    var nameError by remember { mutableStateOf<String?>(null) }
    var mnemonicError by remember { mutableStateOf<String?>(null) }
    var privateKeyError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    
    // Отслеживаем состояние импорта кошелька
    val uiState = viewModel.uiState
    
    // Эффект для обработки успешного импорта кошелька
    LaunchedEffect(uiState.isSuccess, uiState.wallet) {
        if (uiState.isSuccess && uiState.wallet != null) {
            onWalletImported(uiState.wallet.id)
        }
    }
    
    // Эффект для обработки ошибок
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Импорт кошелька") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Импорт кошелька TRIAD",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Выбор метода импорта
                TabRow(
                    selectedTabIndex = selectedImportMethod.ordinal
                ) {
                    Tab(
                        selected = selectedImportMethod == ImportMethod.MNEMONIC,
                        onClick = { selectedImportMethod = ImportMethod.MNEMONIC },
                        text = { Text("Мнемоническая фраза") }
                    )
                    
                    Tab(
                        selected = selectedImportMethod == ImportMethod.PRIVATE_KEY,
                        onClick = { selectedImportMethod = ImportMethod.PRIVATE_KEY },
                        text = { Text("Приватный ключ") }
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Название кошелька
                OutlinedTextField(
                    value = walletName,
                    onValueChange = { 
                        walletName = it
                        nameError = null
                    },
                    label = { Text("Название кошелька") },
                    placeholder = { Text("Мой импортированный кошелек") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = nameError != null,
                    supportingText = { nameError?.let { Text(it) } }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Поля для выбранного метода импорта
                when (selectedImportMethod) {
                    ImportMethod.MNEMONIC -> {
                        // Поле ввода мнемонической фразы
                        OutlinedTextField(
                            value = mnemonic,
                            onValueChange = { 
                                mnemonic = it
                                mnemonicError = null
                            },
                            label = { Text("Мнемоническая фраза") },
                            placeholder = { Text("Введите 12, 15, 18, 21 или 24 слова через пробел") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            isError = mnemonicError != null,
                            supportingText = { mnemonicError?.let { Text(it) } },
                            minLines = 3
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = "Мнемоническая фраза обычно состоит из 12 или 24 слов, разделенных пробелами.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    ImportMethod.PRIVATE_KEY -> {
                        // Поле ввода приватного ключа
                        OutlinedTextField(
                            value = privateKey,
                            onValueChange = { 
                                privateKey = it
                                privateKeyError = null
                            },
                            label = { Text("Приватный ключ") },
                            placeholder = { Text("Введите приватный ключ (шестнадцатеричный формат)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            isError = privateKeyError != null,
                            supportingText = { privateKeyError?.let { Text(it) } }
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = "Приватный ключ обычно начинается с '0x' и состоит из 64 шестнадцатеричных символов.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Пароль
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        passwordError = null
                    },
                    label = { Text("Пароль для шифрования") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (passwordVisible) 
                        VisualTransformation.None 
                    else 
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) 
                                    Icons.Default.VisibilityOff 
                                else 
                                    Icons.Default.Visibility,
                                contentDescription = if (passwordVisible) 
                                    "Скрыть пароль" 
                                else 
                                    "Показать пароль"
                            )
                        }
                    },
                    isError = passwordError != null,
                    supportingText = { passwordError?.let { Text(it) } }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Этот пароль будет использоваться для шифрования приватного ключа. Запомните его, восстановить будет невозможно!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = {
                        // Валидация формы
                        var isValid = true
                        
                        if (walletName.isBlank()) {
                            nameError = "Укажите название кошелька"
                            isValid = false
                        }
                        
                        if (password.length < 8) {
                            passwordError = "Пароль должен содержать не менее 8 символов"
                            isValid = false
                        }
                        
                        when (selectedImportMethod) {
                            ImportMethod.MNEMONIC -> {
                                val words = mnemonic.trim().split("\\s+".toRegex())
                                if (words.size !in listOf(12, 15, 18, 21, 24)) {
                                    mnemonicError = "Мнемоническая фраза должна содержать 12, 15, 18, 21 или 24 слова"
                                    isValid = false
                                }
                            }
                            ImportMethod.PRIVATE_KEY -> {
                                if (privateKey.isBlank()) {
                                    privateKeyError = "Введите приватный ключ"
                                    isValid = false
                                } else if (!privateKey.matches(Regex("^(0x)?[0-9a-fA-F]{64}$"))) {
                                    privateKeyError = "Неверный формат приватного ключа"
                                    isValid = false
                                }
                            }
                        }
                        
                        if (isValid) {
                            scope.launch {
                                when (selectedImportMethod) {
                                    ImportMethod.MNEMONIC -> {
                                        viewModel.importWalletFromMnemonic(
                                            name = walletName,
                                            mnemonic = mnemonic.trim(),
                                            password = password
                                        )
                                    }
                                    ImportMethod.PRIVATE_KEY -> {
                                        viewModel.importWalletFromPrivateKey(
                                            name = walletName,
                                            privateKey = privateKey.trim(),
                                            password = password
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Импортировать кошелек")
                    }
                }
            }
        }
    }
} 