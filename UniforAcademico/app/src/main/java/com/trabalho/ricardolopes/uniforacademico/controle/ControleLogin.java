package com.trabalho.ricardolopes.uniforacademico.controle;

import android.content.Context;
import android.content.SharedPreferences;

import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;

/**
 * Classe que irá controlar se o usuário fez login ou não e deixar seu login salvo no aplicativo.
 * Created by ricairon on 12/12/2016.
 */

public class ControleLogin {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private int MODO_PRIVADO = 0;
    private static final String PREF_NAME = "controlelogin";
    private static final String LOGADO = "logado";

    public ControleLogin(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, MODO_PRIVADO);
        editor = pref.edit();
    }

    public void salvarUsuario(Long id, String nome, String email){
        editor.putLong(Tabela_Usuarios.ID , id);
        editor.putString(Tabela_Usuarios.NOME,nome);
        editor.putString(Tabela_Usuarios.EMAIL, email);
        editor.commit();
    }

    public long getID(){
        return pref.getLong(Tabela_Usuarios.ID, -1L);
    }

    public String getUsuario(){
        return pref.getString(Tabela_Usuarios.NOME,"");
    }

    public String getEmail(){
        return pref.getString(Tabela_Usuarios.EMAIL,"");
    }

    public void setUsuarioLogado(boolean logou){
        editor.putBoolean(LOGADO, logou);
        editor.commit();
    }

    public boolean isUsuarioLogado(){
        return pref.getBoolean(LOGADO, false);
    }

}
