package com.example.listadetarefas.BancoDados;

import com.example.listadetarefas.model.TarefaModel;

import java.util.List;

public interface ITarefaDAO {

    public boolean salvar(TarefaModel tarefa );
    public boolean atualizar(TarefaModel tarefa);
    public Boolean deletar(TarefaModel tarefa);

    public List<TarefaModel> listar();


}
