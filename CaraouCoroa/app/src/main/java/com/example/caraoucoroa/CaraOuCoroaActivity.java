package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CaraOuCoroaActivity extends AppCompatActivity {

    private TextView textVencedor;
    private ImageView imgVenceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_ou_coroa);

        textVencedor = findViewById(R.id.textResultado);
        imgVenceder = findViewById(R.id.imageResultado);

        int num = new Random().nextInt(2);

        if (num == 0) {
            textVencedor.setText("Cara");
            imgVenceder.setImageResource(R.drawable.moeda_cara);

        } else {
            textVencedor.setText("Coroa");
            imgVenceder.setImageResource(R.drawable.moeda_coroa);
        }

    }

    public void Voltar(View view){
        finish();
    }
}
