package com.example.frasedodia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gerarNovaFrase(View view){

        String[] frases = {
                "Com a alma tranquila e sorrindo, eu agradeço!",
                "Amar alguém é bom, mas amar a pessoa que você se tornou, é incrível.",
                "Amar alguém é bom, mas amar a pessoa que você se tornou, é incrível.",
                "Sorria e seja feliz, ouviu? O tempo voa!",
                "Coloca um sorriso no rosto e vai!",
                "Dentro dela é o seu melhor lugar.",
                "Manter o sorriso no rosto e a fé no coração são as regras da vida!",
                "Deixe o sorriso aberto, é por ele que as coisas boas costumam entrar!",
                "Seja lembrado pelos sorrisos que você espalha!"
        };

        int n = frases.length;

        int num = new Random().nextInt(n);



        TextView texto = findViewById(R.id.c_frase);
        texto.setText( frases[num] );
    }


}
