package com.example.organizzemymoney.activity.areaPrincipal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.organizzemymoney.activity.intro.LoginActivity;
import com.example.organizzemymoney.adapter.AdapterMovimentacao;
import com.example.organizzemymoney.config.ConfiguracaoFirebase;
import com.example.organizzemymoney.helper.Base64Custom;
import com.example.organizzemymoney.model.Movimentacao;
import com.example.organizzemymoney.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzemymoney.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity_principal extends AppCompatActivity {

    private TextView saudacao, saldoTotal;
    private MaterialCalendarView calendarioView;

    private RecyclerView recyclerMovimentos;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    private Double despesaTotal     =   0.0;
    private Double receitaTotal     =   0.0;
    private Double resumoUsuario    =   0.0;

    private FirebaseAuth autenticacao       = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef   = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference usuarioRef;
    private DatabaseReference movimentacaoRef;
    private ValueEventListener valueEventListenerUsuario;
    private String mesAnoSelecionado;
    private ValueEventListener valueEventListenerMovimentacoes;
    private Movimentacao excluirMovimentacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);

        Toolbar toolbar = findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Money");

        calendarioView      = findViewById(R.id.calendario_principal);
        recyclerMovimentos  = findViewById(R.id.recyclerMoviment);
        saudacao            = findViewById(R.id.textSaudacao);
        saldoTotal          = findViewById(R.id.textsaldo);
        configuraCalendarioView();
        swipe();

        //Configurar adapter
        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);
        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovimentos.setLayoutManager(layoutManager);
        recyclerMovimentos.setHasFixedSize(true);
        recyclerMovimentos.setAdapter(adapterMovimentacao);

    }


    public void swipe(){

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags   = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags  = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                excluirMoimentacao(viewHolder);

            }
        };

        new ItemTouchHelper( itemTouch ).attachToRecyclerView(recyclerMovimentos);

    }

    public void excluirMoimentacao(final RecyclerView.ViewHolder viewHolder){

        AlertDialog.Builder alertDialig = new AlertDialog.Builder(this);

        //Configurando AlertDialog
        alertDialig.setTitle("Excluir Movimentacao da Conta");
        alertDialig.setMessage("Voce tem certeza que deseja realmente excluir essa movimentacao? ");
        alertDialig.setCancelable(false);

        alertDialig.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int position = viewHolder.getAdapterPosition();
                excluirMovimentacao = movimentacoes.get(position);


                String emailUsuario = autenticacao.getCurrentUser().getEmail();
                String idUsuario    = Base64Custom.codificarBase64(emailUsuario);
                movimentacaoRef     = firebaseRef.child("movimentacao")
                        .child(idUsuario)
                        .child(mesAnoSelecionado);

                movimentacaoRef.child(excluirMovimentacao.getKey()).removeValue();
                adapterMovimentacao.notifyItemRemoved(position);
                atualizarSaldo();

            }
        });

        alertDialig.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity_principal.this, "Cancelado", Toast.LENGTH_SHORT).show();
                adapterMovimentacao.notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialig.create();
        alert.show();

    }

    public void recuperarMovimentacoes(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario    = Base64Custom.codificarBase64(emailUsuario);
        movimentacaoRef     = firebaseRef.child("movimentacao")
                                         .child(idUsuario)
                                         .child(mesAnoSelecionado);

        valueEventListenerMovimentacoes = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                movimentacoes.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                   movimentacao.setKey(dados.getKey());
                    movimentacoes.add(movimentacao);
                }

                adapterMovimentacao.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void recuperarResumo(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef = firebaseRef.child("usuarios")
                .child(idUsuario);

        valueEventListenerUsuario =  usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                despesaTotal  = usuario.getDespesaTotal();
                receitaTotal  = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);

                saudacao.setText("Olá, " +  usuario.getNome());
                saldoTotal.setText("R$ " + resultadoFormatado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void BTN_adicionarReceita(View v){
        startActivity(new Intent(this, Activity_Receitas.class));

    }

    public void BTN_adicionarDespesas(View v){
        startActivity(new Intent(this, Activity_Despesas.class));

    }

    public void configuraCalendarioView(){

        CalendarDay dataAtual = calendarioView.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth() + 1));
        mesAnoSelecionado = String.valueOf(mesSelecionado + "" + dataAtual.getYear());

        calendarioView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = ""+ (date.getMonth() + 1);
                mesAnoSelecionado = String.valueOf(mesSelecionado + "" + date.getYear());
                Log.i("tessste", "MES: " + mesAnoSelecionado);
                movimentacaoRef.removeEventListener( valueEventListenerMovimentacoes );
                recuperarMovimentacoes();

            }
        });

    }


    public void atualizarSaldo(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef = firebaseRef.child("usuarios")
                .child(idUsuario);

        if (excluirMovimentacao.getTipo().equals("R")){
            receitaTotal = receitaTotal - excluirMovimentacao.getValor();
            usuarioRef.child("receitaTotal").setValue(receitaTotal);
        } if(excluirMovimentacao.getTipo().equals("D")){
            despesaTotal = despesaTotal - excluirMovimentacao.getValor();
            usuarioRef.child("despesaTotal").setValue(despesaTotal);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                autenticacao.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
        recuperarMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
    }
}
