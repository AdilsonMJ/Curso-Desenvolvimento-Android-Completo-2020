package com.example.RockPaperScissors.model_historic;

import java.io.Serializable;

public class Model_Historic implements Serializable {
    private String date, time, result;
    private int player, computer;

    public Model_Historic(String date, String time, String result, int player, int computer) {
        this.date = date;
        this.time = time;
        this.result = result;
        this.player = player;
        this.computer = computer;

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getResult() {
        return result;
    }

    public int getPlayer() {
        return player;
    }

    public int getComputer() {
        return computer;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
