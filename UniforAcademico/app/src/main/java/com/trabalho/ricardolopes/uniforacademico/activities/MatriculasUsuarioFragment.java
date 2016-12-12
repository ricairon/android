package com.trabalho.ricardolopes.uniforacademico.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.database.BancoController;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;
import com.trabalho.ricardolopes.uniforacademico.model.Disciplina;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatriculasUsuarioFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private ArrayAdapter<Disciplina> adapter;
    private ArrayList<Disciplina> disciplinasMatriculadas;
    private int layoutAdapter = android.R.layout.simple_list_item_1;

    private BancoController banco;


    public MatriculasUsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matriculas_usuario, container, false);
        banco = new BancoController(getContext());

        mListView = (ListView) view.findViewById(R.id.lista_matriculaUsuario_listView);
        disciplinasMatriculadas = banco.getMatriculasUsuario(Tabela_Usuarios.POG_id_do_usuario);
        adapter = new ArrayAdapter<Disciplina>(getContext(), layoutAdapter, disciplinasMatriculadas);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
