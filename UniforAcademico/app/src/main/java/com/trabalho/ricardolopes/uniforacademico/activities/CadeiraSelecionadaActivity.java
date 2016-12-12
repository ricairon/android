package com.trabalho.ricardolopes.uniforacademico.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.database.BancoController;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Disciplinas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;
import com.trabalho.ricardolopes.uniforacademico.model.Disciplina;

import java.util.Calendar;

public class CadeiraSelecionadaActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView nomeCadeira;
    private Button buttonMatricular;
    private Disciplina disciplina;

    private BancoController banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadeira_selecionada);

        banco = new BancoController(this);

        nomeCadeira = (TextView) findViewById(R.id.act_cad_sel_textview_nomeCadeira);
        buttonMatricular = (Button) findViewById(R.id.act_cad_sel_button_cadastrar);

        Intent chegou = getIntent();
        if(chegou != null){
            disciplina = (Disciplina) chegou.getSerializableExtra(Tabela_Disciplinas.TABELA_DISCIPLINAS);
            if(disciplina != null){
                nomeCadeira.setText(disciplina.getNome());
            }
        }

        buttonMatricular.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        int diaDoMes = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);
        int retorno =  banco.matricularUsuarioNaDisciplina(Tabela_Usuarios.POG_id_do_usuario,disciplina.getId(), diaDoMes + "/" + mes +"/" + ano);
        if(retorno == 0){
            setResult(ListaDisciplinasFragment.RESULT_OK);
            this.finish();
        }else{
            Snackbar.make(view, "Desculpe, não foi possível matrícula.", Snackbar.LENGTH_LONG).show();
        }
    }
}
