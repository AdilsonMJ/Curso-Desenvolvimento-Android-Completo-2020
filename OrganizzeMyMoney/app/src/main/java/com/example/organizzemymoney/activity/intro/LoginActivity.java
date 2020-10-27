package com.example.organizzemymoney.activity.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzemymoney.R;
import com.example.organizzemymoney.activity.areaPrincipal.MainActivity_principal;
import com.example.organizzemymoney.config.ConfiguracaoFirebase;
import com.example.organizzemymoney.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private Button BTN_LOGAR;
    private EditText campoEmail, campoSenha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        campoEmail = findViewById(R.id.editEmail_login);
        campoSenha = findViewById(R.id.editSenha_login);
        BTN_LOGAR = findViewById(R.id.BTN_LOGAR);

        BTN_LOGAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = campoEmail.getText().toString().trim();
                String s = campoSenha.getText().toString().trim();

                if (!e.isEmpty()) {
                    if (!s.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(e);
                        usuario.setSenha(s);
                        validarUsuario();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Digite Senha",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Digite Email",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void validarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            startActivity(new Intent(LoginActivity.this, MainActivity_principal.class));
                            LoginActivity.this.finish();

                        } else {

                            String excecao = "";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e){
                                excecao = "Email nao existe!";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Senha Invalida";
                            }catch (Exception e){
                                excecao = "Erro ao realizar Login: " + e.getMessage();
                                e.printStackTrace();

                                Toast.makeText(LoginActivity.this,
                                        excecao,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
}