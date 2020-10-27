package com.example.organizzemymoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizzemymoney.R;
import com.example.organizzemymoney.model.Movimentacao;

import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MVH> {

    List<Movimentacao> movimentacoes;
    Context context;

    public AdapterMovimentacao(List<Movimentacao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View resumoMovimentacao = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_resumo_movimentacao,parent, false);

        return new MVH(resumoMovimentacao);
    }

    @Override
    public void onBindViewHolder(@NonNull MVH holder, int position) {

        Movimentacao movimentacao = movimentacoes.get(position);

        holder.Resumo.setText(movimentacao.getDescricao());
        holder.Categoria.setText(movimentacao.getCategoria());
        holder.Valor.setText("" + movimentacao.getValor());

        if(movimentacao.getTipo() == "D" || movimentacao.getTipo().equals("D")){
            holder.Valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.Valor.setText("-" + movimentacao.getValor());
        }

    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class MVH extends RecyclerView.ViewHolder{

            TextView Categoria, Resumo, Valor;

            public MVH(@NonNull View itemView) {
                super(itemView);

                Categoria = itemView.findViewById(R.id.RC_categoria);
                Resumo    = itemView.findViewById(R.id.RC_Resumo);
                Valor     = itemView.findViewById(R.id.RC_Valor);

            }
        }
}
