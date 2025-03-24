package com.triad.wallet.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triad.wallet.R;
import com.triad.wallet.TriadWalletApplication;
import com.triad.wallet.ui.explorer.BlockchainExplorerFragment;
import com.triad.wallet.ui.settings.SettingsFragment;
import com.triad.wallet.ui.transactions.TransactionsFragment;
import com.triad.wallet.ui.wallet.WalletFragment;

/**
 * Главное активити приложения
 */
public class MainActivity extends AppCompatActivity 
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    
    private static final String TAG = "MainActivity";
    
    private BottomNavigationView navigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Инициализируем нижнюю навигацию
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        
        // По умолчанию открываем фрагмент с кошельком
        if (savedInstanceState == null) {
            openFragment(new WalletFragment());
            navigationView.setSelectedItemId(R.id.nav_wallet);
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        
        // Определяем, какой фрагмент открыть
        int itemId = item.getItemId();
        if (itemId == R.id.nav_wallet) {
            fragment = new WalletFragment();
        } else if (itemId == R.id.nav_transactions) {
            fragment = new TransactionsFragment();
        } else if (itemId == R.id.nav_explorer) {
            fragment = new BlockchainExplorerFragment();
        } else if (itemId == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }
        
        // Открываем выбранный фрагмент
        if (fragment != null) {
            openFragment(fragment);
            return true;
        }
        
        return false;
    }
    
    /**
     * Открыть указанный фрагмент
     * @param fragment фрагмент для открытия
     */
    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Активити возобновлено");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Активити приостановлено");
    }
} 