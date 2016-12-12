package com.trabalho.ricardolopes.uniforacademico.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.trabalho.ricardolopes.uniforacademico.R;
import com.trabalho.ricardolopes.uniforacademico.controle.ControleLogin;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long idUsuario;
    private String nomeUsuario;
    private String emailUsuario;

    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private TextView nav_user;
    private TextView nav_email;

    private ControleLogin controleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controleLogin = new ControleLogin(this);
        if(controleLogin.isUsuarioLogado()){
            this.idUsuario = controleLogin.getID();
            Tabela_Usuarios.POG_id_do_usuario = this.idUsuario;
            this.nomeUsuario = controleLogin.getUsuario();
            this.emailUsuario = controleLogin.getEmail();
        }

        //Configurar o Splash Screen aqui e chamar o MainAcvitity
        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.conteudo_main, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null){  //Veio da activity Login e carrega as informações nos objetos locais.
            this.idUsuario = intent.getExtras().getLong(Tabela_Usuarios.ID);
            Tabela_Usuarios.POG_id_do_usuario = this.idUsuario;
            this.nomeUsuario = intent.getExtras().getString(Tabela_Usuarios.NOME);
            this.emailUsuario = intent.getExtras().getString(Tabela_Usuarios.EMAIL);
        }

        if(savedInstanceState != null){
            Bundle extras = getIntent().getExtras();
            idUsuario = extras.getLong(Tabela_Usuarios.ID);
            Tabela_Usuarios.POG_id_do_usuario = this.idUsuario;
            nomeUsuario = extras.getString(Tabela_Usuarios.NOME);
            emailUsuario = extras.getString(Tabela_Usuarios.EMAIL);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        //Seta os valores do navigationView header.
        View hView =  mNavigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.navDrawer_TextView_Usuario);
        nav_email = (TextView)hView.findViewById(R.id.navDrawer_TextView_email);
        nav_user.setText(nomeUsuario);
        nav_email.setText(emailUsuario);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putLong(Tabela_Usuarios.ID, this.idUsuario);
        saveInstanceState.putString(Tabela_Usuarios.NOME, this.nomeUsuario);
        saveInstanceState.putString(Tabela_Usuarios.EMAIL, this.emailUsuario);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.idUsuario = savedInstanceState.getLong(Tabela_Usuarios.ID);
        nav_user.setText(savedInstanceState.getString(Tabela_Usuarios.NOME));
        nav_email.setText(savedInstanceState.getString(Tabela_Usuarios.EMAIL));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_home:
                MainFragment fragment = new MainFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.conteudo_main, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_lista_disciplinas:
                ListaDisciplinasFragment fragment2 = new ListaDisciplinasFragment();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.conteudo_main, fragment2);
                fragmentTransaction2.commit();
                break;
            case R.id.nav_usuario_matricula:
                //TODO Chamar Fragment com a cadeiras que o aluno está matriculado.
                MatriculasUsuarioFragment fragment3 = new MatriculasUsuarioFragment();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.conteudo_main, fragment3);
                fragmentTransaction3.commit();
                break;
            case R.id.nav_logout:
                controleLogin.setUsuarioLogado(false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
