package com.example.RockPaperScissors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RockPaperScissors.Helper.HistoricDAO;
import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.Random;

public class GameAcitivy extends AppCompatActivity {

    private int pc = 0;
    private int player = 0;
    private TextView ComputerResult, playerResult;
    private int modelOfGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ComputerResult = findViewById(R.id.ComputerResult);
        playerResult = findViewById(R.id.PlayerResult);

        //Get the choice of model of game
        Bundle choice = getIntent().getExtras();
        modelOfGame = choice.getInt("choiceMode");


    }

    public void selectStone(View view){
        this.rules("Stone");
    }

    public void selectPaper(View view){
        this.rules("Paper");
    }

    public void selectScissors(View view){
        this.rules("Scissors");
    }


    public String PcSelect(){

        ImageView pcPlayed = findViewById(R.id.PcPlayed);
        int number = new Random().nextInt(3);
        String[] option = {"Stone", "Paper", "Scissors"};
        String optionPC = option[number];

        switch (optionPC) {
            case "Stone":
                pcPlayed.setImageResource(R.drawable.pedra);
                break;

            case "Paper":
                pcPlayed.setImageResource(R.drawable.papel);
                break;

            case "Scissors":
                pcPlayed.setImageResource(R.drawable.tesoura);
                break;
        }
        return optionPC;
    }

    public void rules(String PlayerSelect) {
            TextView textResult = findViewById(R.id.TextResult);
            String optionpc = PcSelect();

            if (            (optionpc.equals("Stone")       && PlayerSelect.equals("Scissors")) ||
                            (optionpc.equals("Scissors")    && PlayerSelect.equals("Paper")) ||
                            (optionpc.equals("Paper")       && PlayerSelect.equals("Stone"))) {
                textResult.setText("Computer point");
                pc += 1;
            } else if (     (optionpc.equals("Scissors")    && PlayerSelect.equals("Stone")) ||
                            (optionpc.equals("Paper")       && PlayerSelect.equals("Scissors")) ||
                            (optionpc.equals("Stone")       && PlayerSelect.equals("Paper")) ) {
                textResult.setText("Player Point ");
                player += 1;
            } else {
                textResult.setText("Draw");
            }

            // Show the Result
            ComputerResult.setText("" + pc);
            playerResult.setText("" + player);

            if (pc == modelOfGame){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                salvar("1/1", "12:00", "Lose", player, pc);
                dialog.setTitle("YOU LOSE");
                dialog.setMessage("sorry, I played of best of you! ");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player = 0;
                        pc = 0;
                        playerResult.setText("" + player);
                        ComputerResult.setText("" + pc);
                    }
                }).show();
                } else if (player == modelOfGame){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    salvar("1/1", "12:00", "WIN", player, pc);
                    dialog.setTitle("YOU WIN");
                    dialog.setMessage("YOU ARE THE BEST ON THIS GAME ");
                    dialog.setIcon(R.drawable.win);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player = 0;
                            pc = 0;
                            playerResult.setText("" + player);
                            ComputerResult.setText("" + pc);
                        }
                    }).show();
            }
    }

    public void Reset(View view){

        // Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( this);

        //Configure title and message
        dialog.setTitle("Reset");
        dialog.setMessage("Are you sure you want to restart? ");

        //Configure cancellation when clicking outside the Alert
        dialog.setCancelable(false);

        //Configure icone
        dialog.setIcon(android.R.drawable.alert_dark_frame);

        //Configure yes or not
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplication(), DrawerActivity.class);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(
                     getApplicationContext(),
                     "YAAAH LET`S GO PLAY ",
                     Toast.LENGTH_LONG
                ).show();
            }
        });
        dialog.show();
    }


   public void salvar(String date, String time, String result, int player, int computer){

       HistoricDAO HDAO = new HistoricDAO(getApplicationContext());
       Model_Historic s = new Model_Historic(date, time, result, player, computer);
       HDAO.salvar(s);

    }

}

