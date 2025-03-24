package com.triad.wallet.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.triad.wallet.R;
import com.triad.wallet.data.model.WalletInfo;

/**
 * Фрагмент для отображения информации о кошельке
 */
public class WalletFragment extends Fragment {
    
    private WalletViewModel viewModel;
    private TextView balanceTextView;
    private TextView addressTextView;
    private Button sendButton;
    private Button receiveButton;
    private Button refreshButton;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализируем ViewModel
        viewModel = new ViewModelProvider(this).get(WalletViewModel.class);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Раздуваем лейаут фрагмента
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        
        // Находим все необходимые вью
        balanceTextView = view.findViewById(R.id.balance_text);
        addressTextView = view.findViewById(R.id.address_text);
        sendButton = view.findViewById(R.id.send_button);
        receiveButton = view.findViewById(R.id.receive_button);
        refreshButton = view.findViewById(R.id.refresh_button);
        
        // Настраиваем обработчики нажатий
        sendButton.setOnClickListener(v -> navigateToSendScreen());
        receiveButton.setOnClickListener(v -> navigateToReceiveScreen());
        refreshButton.setOnClickListener(v -> refreshWalletData());
        
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Наблюдаем за данными из ViewModel
        viewModel.getWalletInfo().observe(getViewLifecycleOwner(), this::updateUI);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);
        
        // Запрашиваем данные при создании вью
        refreshWalletData();
    }
    
    /**
     * Обновить интерфейс на основе данных о кошельке
     */
    private void updateUI(WalletInfo walletInfo) {
        if (walletInfo != null) {
            balanceTextView.setText(getString(R.string.wallet_balance) + ": " + walletInfo.getBalance().toPlainString() + " TRIAD");
            addressTextView.setText(walletInfo.getAddress());
        }
    }
    
    /**
     * Показать сообщение об ошибке
     */
    private void showError(String error) {
        if (error != null && !error.isEmpty()) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * Перейти на экран отправки средств
     */
    private void navigateToSendScreen() {
        // В реальном приложении здесь будет переход к экрану отправки средств
        Toast.makeText(requireContext(), "Переход к экрану отправки средств", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Перейти на экран получения средств
     */
    private void navigateToReceiveScreen() {
        // В реальном приложении здесь будет переход к экрану получения средств
        Toast.makeText(requireContext(), "Переход к экрану получения средств", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Обновить данные о кошельке
     */
    private void refreshWalletData() {
        viewModel.loadWalletInfo();
    }
} 