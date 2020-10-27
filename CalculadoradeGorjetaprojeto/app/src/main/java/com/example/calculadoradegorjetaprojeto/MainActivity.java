package com.example.calculadoradegorjetaprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView valorConta, valorGojeta, valorTotalConta, TextPorcentagemGojeta;
    private SeekBar seekBarGorjeta;
    private double PorcentagemGorjeta = 0;
    private boolean num = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorConta                      = findViewById(R.id.valorConta);
        valorGojeta                     = findViewById(R.id.valorGojeta);
        valorTotalConta                 = findViewById(R.id.valorTotalConta);
        TextPorcentagemGojeta           = findViewById(R.id.PorcentagemGorjeta);
        seekBarGorjeta                  = findViewById(R.id.seekBarGorjeta);


        seekBarGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                PorcentagemGorjeta = progress;
                TextPorcentagemGojeta.setText(Math.round( PorcentagemGorjeta ) + " % ");
                calcular();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void calcular(){
            String valorRecuperado = valorConta.getText().toString();

            if( valorRecuperado == null || valorRecuperado.equals("")){


                if (num ) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Digite um valor Primeiro",
                            Toast.LENGTH_SHORT
                    ).show();

                    seekBarGorjeta.setProgress(0);
                    num = false;
                } else {
                    seekBarGorjeta.setProgress(0);
                }
            } else {
                // Converter String para Double
                double valorConta = Double.parseDouble(valorRecuperado);

                //Exibir Valor da Gojeta
                double gorjeta = Math.round( valorConta * (PorcentagemGorjeta/100));
                valorGojeta.setText("R$ " + gorjeta);

                //Exibir Valor Total
                double vt = Math.round(valorConta + gorjeta);
                valorTotalConta.setText("R$ " + vt);




            }

    }

}
