package com.trabalho.ricardolopes.uniforacademico.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.controle.ControleLogin;
import com.trabalho.ricardolopes.uniforacademico.database.BancoController;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;
import com.trabalho.ricardolopes.uniforacademico.model.Usuario;

public class LoginActivity extends AppCompatActivity{

    private LinearLayout linearLayout;
    private TextInputEditText mEditTextUsuario;
    private TextInputEditText mEditTextSenha;
    private TextView mTextViewCadastrar;
    private Button mButtonLogin;

    public static int RESULT_OK = 1;

    private BancoController banco;

    private ControleLogin controleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = (LinearLayout) findViewById(R.id.activity_login);
        mEditTextUsuario = (TextInputEditText) findViewById(R.id.vlogin_edittext_usuario);
        mEditTextSenha = (TextInputEditText) findViewById(R.id.vlogin_editText_senha);
        mTextViewCadastrar = (TextView) findViewById(R.id.vlogin_TextView_efetuarCadastro);
        mButtonLogin = (Button) findViewById(R.id.vlogin_button_login);
        banco = new BancoController(getBaseContext());

        controleLogin = new ControleLogin(this);
        //Se estiver estiver logado passará para a MainActivity
        if(controleLogin.isUsuarioLogado()){
            //Chama tela MainActivity e passa os valores pra lá.
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(Tabela_Usuarios.ID, controleLogin.getID());
            intent.putExtra(Tabela_Usuarios.NOME, controleLogin.getUsuario());
            intent.putExtra(Tabela_Usuarios.EMAIL, controleLogin.getEmail());
            startActivity(intent);
            finish();
        }

        mTextViewCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastrarActivity.class);
                startActivityForResult(intent, RESULT_OK);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPreenchido = true;
                if(mEditTextUsuario.getText().toString().isEmpty()){
                    mEditTextUsuario.setError("Digite o nome de usuário!");
                    isPreenchido = false;
                }
                if(mEditTextSenha.getText().toString().isEmpty()){
                    mEditTextSenha.setError("Digite sua senha!");
                    isPreenchido = false;
                }

                if(isPreenchido){
                    Usuario usuario;
                    usuario = banco.efetuarLogin(mEditTextUsuario.getText().toString(), mEditTextSenha.getText().toString());
                    if(usuario == null){
                        Snackbar.make(linearLayout, "Login inválido!", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(Tabela_Usuarios.ID, usuario.getId());
                        intent.putExtra(Tabela_Usuarios.NOME, usuario.getNome());
                        intent.putExtra(Tabela_Usuarios.EMAIL, usuario.getEmail());
                        //Salva a preferência do usuário.
                        controleLogin.salvarUsuario(usuario.getId(),usuario.getNome(), usuario.getEmail());
                        controleLogin.setUsuarioLogado(true);
                        startActivity(intent);
                        //Evita que o usuário volte a essa tela.
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Snackbar.make(linearLayout, "Cadastro feito com sucesso!", Snackbar.LENGTH_LONG).show();
        }
    }
}
