package com.example.RockPaperScissors.Helper;

import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.List;

public interface IHistoricDAO {

    public Boolean salvar(Model_Historic historic);

    public List<Model_Historic> listar();
}
