package com.example.alcoolougasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editPAlcool, editPGasolina;
    private TextView Resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    editPAlcool     = findViewById(R.id.editPAlcol);
    editPGasolina   = findViewById(R.id.editPGasolina);
    Resultado       = findViewById(R.id.Resultado);

    }

    public  void Calcular(View view){
        // Recuperar dados
        String precoAlcool = editPAlcool.getText().toString();
        String precoGasolina = editPGasolina.getText().toString();

        // Validar Os Campos
        Boolean camposValidados =  validarCampos(precoAlcool, precoGasolina);
        if ( camposValidados){

            //Convertendo string para numeros
            Double valorAlcool = Double.parseDouble(precoAlcool);
            Double valorGasolina = Double.parseDouble(precoGasolina);

            /*
            *Fazer Cálculo de menor preço,
            * Se (ValorAlcool / Valor da Gasolinha >= 0.7 é melhor utilizar gasolina.
            *
             */

            Double calculo =  valorAlcool / valorGasolina;

            if (calculo >= 0.7){
                Resultado.setText("Gasolina");
            } else {
                Resultado.setText("Alcool");
            }



        } else {
            Resultado.setText("Preencha os preços primeiro!");
        }
    }

    public Boolean validarCampos (String pAlcool, String pGasolina){

        Boolean camposValidados = true;

        if (pAlcool == null || pAlcool.equals("")){
            camposValidados = false;
        } else if (pGasolina == null || pGasolina.equals("")) {
            camposValidados = false;
        }

        return camposValidados;
    }

}
