package com.triad.wallet.ui.explorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.triad.wallet.R;

/**
 * Фрагмент для отображения информации о блокчейне
 */
public class BlockchainExplorerFragment extends Fragment {
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        
        // Временная заглушка - заменяем содержимое на сообщение
        TextView balanceText = view.findViewById(R.id.balance_text);
        if (balanceText != null) {
            balanceText.setText("Обозреватель блокчейна (в разработке)");
        }
        
        return view;
    }
} 