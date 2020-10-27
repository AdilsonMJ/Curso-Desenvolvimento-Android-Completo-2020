package com.example.organizzemymoney.activity.areaPrincipal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzemymoney.R;
import com.example.organizzemymoney.config.ConfiguracaoFirebase;
import com.example.organizzemymoney.helper.Base64Custom;
import com.example.organizzemymoney.helper.DateUtil;
import com.example.organizzemymoney.model.Movimentacao;
import com.example.organizzemymoney.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Activity_Receitas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView date;
    private EditText campoValor;
    private TextInputEditText campoCategoria, campoDescricao;
    private Movimentacao movimentacao;

    private Double receitaTotal, receitaAtualizada;
    private DatabaseReference firebaseref   = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth      autenticacao  = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__receitas);

        date                = findViewById(R.id.TV_DATA_ESCOLHA_Receitas);
        campoValor          = findViewById(R.id.TIL_Valor_Receitas);
        campoCategoria      = findViewById(R.id.TIL_categ_Receitas);
        campoDescricao      = findViewById(R.id.TIL_desc_Receita);
        campoValor          = findViewById(R.id.TIL_Valor_Receitas);

        //Colocar Data Atual no Campo Data
        date.setText(DateUtil.dataAtual());
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDailog();
            }
        });

        recuperarReceitaTotal();
    }

    private void showDateDailog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String a = dayOfMonth + "/" + (month + 1) + "/" + year;
        date.setText(a);
    }

    public  void  BTN_Confirma_Receitas (View v){
        if (validarCampoReceitas()) {
            movimentacao = new Movimentacao();
            String data =  date.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString().trim());
            movimentacao.setDescricao(campoDescricao.getText().toString().trim());
            movimentacao.setData(data);
            movimentacao.setTipo("R");

            receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);

            movimentacao.Salvar( data );
            startActivity(new Intent(this, MainActivity_principal.class));
        }
    }

    public Boolean validarCampoReceitas(){

        String textoValor = campoValor.getText().toString().trim();
        String textoCategoria = campoCategoria.getText().toString().trim();
        String textoDescricao = campoDescricao.getText().toString().trim();

        if (!textoValor.isEmpty()){
            if(!textoCategoria.isEmpty()){
                if (!textoDescricao.isEmpty()){
                    return true;
                } else{
                    Toast.makeText(Activity_Receitas.this, "Informe uma Descrição", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(Activity_Receitas.this, "Informe o Categoria", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(Activity_Receitas.this, "Digite Valor", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void recuperarReceitaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseref.child("usuarios")
                .child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizarReceita(Double receita){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseref.child("usuarios")
                .child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);
    }
}