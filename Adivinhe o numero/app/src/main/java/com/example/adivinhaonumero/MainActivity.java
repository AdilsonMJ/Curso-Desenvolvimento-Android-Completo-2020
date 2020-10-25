package com.example.adivinhaonumero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText aposta;
    private TextView resultado, pc;
    private Button btn_Apostar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aposta = findViewById(R.id.editTextNumber);
        resultado = findViewById(R.id.TextView_Resultado);
        pc = findViewById(R.id.textViewpc);
        btn_Apostar = findViewById(R.id.buttonApostar);


        btn_Apostar.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                regras();

                }
        });

    }

    public void regras(){

        int n_sorteado = new Random().nextInt(11);
        pc.setText("O número Sorteado foi: " + n_sorteado);

        String jogador = aposta.getText().toString();
        int finalAposta = Integer.parseInt(jogador);

        if (n_sorteado == finalAposta){
            resultado.setText("Acertou!! Parabéns");

        } else{
            resultado.setText("Errou, tente novamente!");
        }

    }

}