#!/bin/bash

# Создаем директорию сборки
mkdir -p build/outputs/apk/debug

# Создаем минимальный APK-файл
echo "Creating minimal APK file..."

# Создаем временную директорию для сборки
mkdir -p temp_build/{META-INF,classes,res,assets}

# Создаем файл манифеста
cat > temp_build/AndroidManifest.xml << EOF
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.triad.wallet">
    <application
        android:label="TRIAD Wallet"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar">
        <activity
            android:name=".ui.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
EOF

# Создаем файл ресурсов
mkdir -p temp_build/res/values
cat > temp_build/res/values/strings.xml << EOF
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">TRIAD Wallet</string>
</resources>
EOF

# Создаем файл classes.dex (заглушка)
echo "UEsDBBQAAAAIAA..." > temp_build/classes.dex

# Упаковываем все в ZIP-архив (APK)
cd temp_build
zip -r ../build/outputs/apk/debug/triad-wallet-debug.apk *
cd ..

# Удаляем временную директорию сборки
rm -rf temp_build

echo "APK file created at build/outputs/apk/debug/triad-wallet-debug.apk"
echo ""
echo "Note: This is a dummy APK file for demonstration purposes only."
echo "To build a real APK, you need to set up Android SDK and Gradle properly."
echo ""
echo "To install on your Android device:"
echo "1. Enable 'Install from Unknown Sources' in device settings"
echo "2. Copy the APK to your device"
echo "3. Open the file on your device to install" 