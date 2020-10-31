package com.example.RockPaperScissors.adapter;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RockPaperScissors.R;
import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.List;

public class Adapter_Historic  extends RecyclerView.Adapter<Adapter_Historic.MVH> {


        private List<Model_Historic> listHistoric;

        public Adapter_Historic(List<Model_Historic> list) {this.listHistoric = list;}


    @NonNull
    @Override
    public Adapter_Historic.MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View historicList = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.historicofgames, parent, false);
        return new MVH(historicList);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Historic.MVH holder, int position) {

            Model_Historic h = listHistoric.get(position);
            holder.Date.setText(h.getDate());
            holder.Time.setText(h.getTime());
            holder.Result.setText(h.getResult());
            holder.Player.setText(h.getPlayer());
            holder.Computer.setText(h.getComputer());



    }

    @Override
    public int getItemCount() {
        return this.listHistoric.size();
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
