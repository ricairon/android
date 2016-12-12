package com.trabalho.ricardolopes.uniforacademico.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.database.BancoController;
import com.trabalho.ricardolopes.uniforacademico.database.excecoes.UsuarioJaCadastrado;
import com.trabalho.ricardolopes.uniforacademico.util.UtilRick;

/**
 * @author Ricardo Lopes de Lima
 * @version 1.0
 * Desenvolvido em 10/12/2016.
 * Classe que representa a Activity que mostrará a tela de cadastro do usuário.
 */
public class CadastrarActivity extends AppCompatActivity {

    //Widgets do layout: activity_cadastrar
    private TextInputEditText mEditTextUsuario;
    private TextInputEditText mEditTextEmail;
    private TextInputEditText mEditTextSenha;
    private Button mButtonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        mEditTextUsuario = (TextInputEditText) findViewById(R.id.vcadastro_edittext_usuario);
        mEditTextEmail = (TextInputEditText) findViewById(R.id.vcadastro_edittext_email);
        mEditTextSenha = (TextInputEditText) findViewById(R.id.vcadastro_edittext_senha);
        mButtonCadastrar = (Button) findViewById(R.id.vcadastro_button_cadastrar);

        try{
            getSupportActionBar().setTitle("Unifor - Cadastro de Usuário");
        }catch (Exception ex){
            //Faz nada.. é apenas capricho.
        }

        mButtonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificar os valores foram inseridos.
                boolean isOk = true;
                if(mEditTextUsuario.getText().toString().isEmpty()){
                    mEditTextUsuario.setError("Digite o nome do seu usuário");
                    isOk = false;
                }
                if(mEditTextEmail.getText().toString().isEmpty()){
                    mEditTextEmail.setError("Digite um e-mail");
                    isOk = false;
                }else{
                    if(!UtilRick.isEmailValido(mEditTextEmail.getText().toString())){
                        mEditTextEmail.setError("E-mail inválido!");
                        isOk = false;
                    }
                }
                if(mEditTextSenha.getText().toString().isEmpty()){
                    mEditTextSenha.setError("Digite sua senha de acesso");
                    isOk = false;
                }

                if(isOk){
                    BancoController banco = new BancoController(getBaseContext());
                    int codigo = -1;
                        codigo = banco.cadastraUsuario(mEditTextUsuario.getText().toString().trim(),mEditTextEmail.getText().toString(),mEditTextSenha.getText().toString());
                        if(codigo == 0){
                            setResult(LoginActivity.RESULT_OK);
                            finish();
                        }else if(codigo == 1){
                            mEditTextUsuario.setError("Digite outro nome de usuário");
                            Snackbar.make(view, "Desculpe, usuário já cadastrado", Snackbar.LENGTH_SHORT).show();
                        }
                }else{
                    Snackbar.make(view, "Preencha os campos com erros.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }
}
