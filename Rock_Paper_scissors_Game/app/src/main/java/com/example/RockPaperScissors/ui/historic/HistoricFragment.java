package com.example.RockPaperScissors.ui.historic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.RockPaperScissors.Helper.HistoricDAO;
import com.example.RockPaperScissors.R;
import com.example.RockPaperScissors.adapter.Adapter_Historic;
import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.ArrayList;
import java.util.List;

public class HistoricFragment extends Fragment {

    private List<Model_Historic> historics = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historic, container, false);
        RecyclerView RC = rootView.findViewById(R.id.Rc_Historic);


        HistoricDAO historicDAO = new HistoricDAO(getContext());
        historics = historicDAO.listar();

        Adapter_Historic adapter_historic = new Adapter_Historic(historics);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RC.setLayoutManager(layoutManager);
        RC.setHasFixedSize(true);
        RC.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        RC.setAdapter(adapter_historic);



        return rootView;
    }

}