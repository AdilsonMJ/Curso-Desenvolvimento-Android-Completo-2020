package com.example.organizzemymoney.activity.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzemymoney.R;
import com.example.organizzemymoney.activity.areaPrincipal.MainActivity_principal;
import com.example.organizzemymoney.config.ConfiguracaoFirebase;
import com.example.organizzemymoney.helper.Base64Custom;
import com.example.organizzemymoney.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome, email, senha;
    private Button BTN_Cadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setTitle("Cadastro");

        nome = findViewById(R.id.editNome_cadastro);
        email = findViewById(R.id.editEmail_cadastro);
        senha = findViewById(R.id.editSenha_cadastro);
        BTN_Cadastrar = findViewById(R.id.BTN_CADASTRAR);


        BTN_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = nome.getText().toString().trim();
                String e = email.getText().toString().trim();
                String s = senha.getText().toString().trim();

                if(!n.isEmpty()){
                    if (!e.isEmpty()){
                        if (!s.isEmpty()){

                            usuario = new Usuario();
                            usuario.setEmail(e);
                            usuario.setSenha(s);
                            usuario.setNome(n);
                            cadastrarUsuario();



                        } else{
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else{
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            salvardados();
                            startActivity(new Intent(CadastroActivity.this, MainActivity_principal.class));
                            finish();

                        } else{

                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Digite um email valido!";
                            } catch (FirebaseAuthUserCollisionException e ){
                                mandarParaAreaDeLogin();
                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuário" + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    excecao,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void mandarParaAreaDeLogin(){
        AlertDialog.Builder alertContaExist = new AlertDialog.Builder(CadastroActivity.this);

        //set Title
        alertContaExist.setTitle("CONTA JA EXISTE! ");

        //Set Dialog Message
        alertContaExist.setMessage("Deseja ir para área de loggin?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CadastroActivity.this.finish();
                    }
                })
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertContaExist.create();
        alertDialog.show();


    }

    public void salvardados(){
        String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
        usuario.setIdUsuario(idUsuario);
        usuario.salvar();
    }



}