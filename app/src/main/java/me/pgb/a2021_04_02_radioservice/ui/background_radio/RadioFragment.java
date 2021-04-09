package me.pgb.a2021_04_02_radioservice.ui.background_radio;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import me.pgb.a2021_04_02_radioservice.R;
import me.pgb.a2021_04_02_radioservice.models.RadioStationArray;
import me.pgb.a2021_04_02_radioservice.service.RadioService;
import me.pgb.a2021_04_02_radioservice.service.ServiceContainer;

public class RadioFragment extends Fragment {

    private RadioViewModel radioViewModel;
    private RadioStationArray radioStationArray;
    private Button radioToggleButton;
    private boolean radioOn = false;
    private Spinner stationSpinner;
    private String[] URLS;
    private String[] StationNames;
    private ImageView stationImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_radio, container, false);
        
        radioToggleButton = root.findViewById(R.id.radio_toggle_button);

        radioToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioOn){
                    ServiceContainer.radioService.radioOff();
                    radioToggleButton.setText("Turn Radio On");
                    radioOn = false;
                }
                else{
                    ServiceContainer.radioService.radioOn();
                    radioToggleButton.setText("Turn Radio Off");
                    radioOn = true;
                }
            }
        });

        //Set up radio station array from RadioStationArray model
        radioStationArray = new RadioStationArray();
        URLS = radioStationArray.getArrayOfRadioLinks();
        StationNames = radioStationArray.getArrayOfRadioNames();


        //Set up station selection dropdown menu
        stationSpinner = root.findViewById(R.id.stationSpinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, StationNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner.setAdapter(adapter);

        //Set up station imageview
        stationImage = root.findViewById(R.id.stationImage);

        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ServiceContainer.radioService.changeURL(URLS[position]);
                if (radioOn){
                    ServiceContainer.radioService.radioOff();
                    ServiceContainer.radioService.radioOn();
                }
                //Determine Image to display
                if (position == 0){
                    stationImage.setImageResource(R.drawable.bbcradioimage);
                }
                else if (position == 1){
                    stationImage.setImageResource(R.drawable.mainepublicradio);
                }
                else if (position == 2){
                    stationImage.setImageResource(R.drawable.whus);
                }
                else if (position == 3){
                    stationImage.setImageResource(R.drawable.linnjazz);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No action needs to be performed
            }
        });


        return root;
    }

}