package com.triad.wallet.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс для инициализации и настройки API клиента
 */
public class ApiClient {
    private static final String BASE_URL = "https://api.triad.network/"; // Базовый URL API
    private static final int TIMEOUT = 30; // Таймаут в секундах
    
    private static Retrofit retrofit = null;
    private static TriadApiService apiService = null;
    
    /**
     * Получить экземпляр Retrofit клиента
     * @return настроенный Retrofit клиент
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Создаем логгер для HTTP запросов
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Настраиваем OkHttpClient
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging);
            
            // Настраиваем Gson для сериализации/десериализации JSON
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            
            // Создаем Retrofit клиент
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }
        
        return retrofit;
    }
    
    /**
     * Получить экземпляр API сервиса
     * @return настроенный API сервис
     */
    public static TriadApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(TriadApiService.class);
        }
        
        return apiService;
    }
    
    /**
     * Сменить базовый URL для API
     * @param newBaseUrl новый базовый URL
     */
    public static void changeBaseUrl(String newBaseUrl) {
        // Пересоздаем клиент с новым URL
        retrofit = null;
        apiService = null;
        
        // Временно меняем BASE_URL
        // В реальном приложении этот метод должен быть доработан для корректной смены URL
        // BASE_URL = newBaseUrl;
        
        // Получаем новый клиент с обновленным URL
        getClient();
    }
} 