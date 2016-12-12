package com.trabalho.ricardolopes.uniforacademico.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.database.BancoController;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Disciplinas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;
import com.trabalho.ricardolopes.uniforacademico.model.Disciplina;
import com.trabalho.ricardolopes.uniforacademico.model.Matricula;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaDisciplinasFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private ArrayList<Disciplina> matriculasArrayList;
    private ArrayAdapter<Disciplina> adapter;
    private int layoutAdapter = android.R.layout.simple_list_item_1;

    private BancoController banco;

    public static int RESULT_OK = 1;

    public ListaDisciplinasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_disciplinas, container, false);

        banco = new BancoController(getContext());

        mListView = (ListView) view.findViewById(R.id.lista_disciplinas_listView);
        matriculasArrayList = banco.getDisciplinas();

        adapter = new ArrayAdapter<Disciplina>(getContext(), layoutAdapter, matriculasArrayList);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Recupera a Instancia da disciplina que foi selecionada.
        Disciplina contato = adapter.getItem(i);
        Intent intent =  new Intent(view.getContext(), CadeiraSelecionadaActivity.class);
        intent.putExtra(Tabela_Disciplinas.TABELA_DISCIPLINAS,contato);
        startActivityForResult(intent, RESULT_OK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            View view = getView();
            if(view != null){
                Snackbar.make(getView(), "Matriculado com sucesso!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
