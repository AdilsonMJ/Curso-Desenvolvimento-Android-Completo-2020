package com.example.RockPaperScissors.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RockPaperScissors.GameAcitivy;
import com.example.RockPaperScissors.R;

public class HomeFragment extends Fragment {

    private RadioGroup optionofGame;
    private int optionOfGameChoice = 0;
    private static final String PREFERENCE_FILE = "PreferenceFile";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preference = this.getActivity().getSharedPreferences(PREFERENCE_FILE, 0);
        if(preference.contains("name")){
            String name = preference.getString("name", "");

            TextView Textname = rootView.findViewById(R.id.textNamePlayer);
            Textname.setText(name);
        }


        Button btn_player_Home_fragment = rootView.findViewById(R.id.button_play_fragment_home);
        btn_player_Home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionOfGameChoice != 0) {
                    Intent intent = new Intent(getActivity(), GameAcitivy.class);
                    //send to game the model of player choiced
                    intent.putExtra("choiceMode", optionOfGameChoice);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(),
                            "Choice a option of Game!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        optionofGame = rootView.findViewById(R.id.radioGroupOptGame);
        optionofGame.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radiobutton_choice_3){
                    optionOfGameChoice = 3;
                } else if(checkedId == R.id.radiobutton_choice_5){
                    optionOfGameChoice = 5;
                } else{
                    optionOfGameChoice = 10;
                }
            }
        });


        return rootView;
    }
}