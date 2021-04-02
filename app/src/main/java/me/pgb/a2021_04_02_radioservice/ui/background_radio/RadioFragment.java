package me.pgb.a2021_04_02_radioservice.ui.background_radio;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import me.pgb.a2021_04_02_radioservice.R;
import me.pgb.a2021_04_02_radioservice.service.RadioService;
import me.pgb.a2021_04_02_radioservice.service.ServiceContainer;

public class RadioFragment extends Fragment {

    private RadioViewModel radioViewModel;
    private Button radioToggleButton;
    private Button stopBackgroundThread;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_radio, container, false);


        radioToggleButton = root.findViewById(R.id.radio_toggle_button);

        radioToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ServiceContainer.radioService = new RadioService();
                int num = ServiceContainer.radioService.getCounter();
                Toast.makeText(getActivity().getApplicationContext(), "number: " + String.valueOf(num).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

}