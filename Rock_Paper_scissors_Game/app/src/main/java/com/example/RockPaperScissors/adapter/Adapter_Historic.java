package com.example.RockPaperScissors.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RockPaperScissors.R;

public class Adapter_Historic  extends RecyclerView.Adapter<Adapter_Historic.MVH> {


    @NonNull
    @Override
    public Adapter_Historic.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View historicList = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.historicofgames, parent, false);
        return new MVH(historicList);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Historic.MVH holder, int position) {

        holder.Date.setText("12/11");
        holder.Time.setText("13:00");
        holder.Player.setText("5");
        holder.Computer.setText("2");
        holder.Result.setText("Win");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MVH extends RecyclerView.ViewHolder {

        TextView Date, Time, Player, Computer, Result;

        public MVH(@NonNull View itemView) {
            super(itemView);

            Date        = itemView.findViewById(R.id.date_HFragment);
            Time        = itemView.findViewById(R.id.time_HFragment);
            Player      = itemView.findViewById(R.id.Player_HFragment);
            Computer    = itemView.findViewById(R.id.Computer_HFragment);
            Result      = itemView.findViewById(R.id.Result_HFragment);
        }
    }
}
