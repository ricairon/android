package com.trabalho.ricardolopes.uniforacademico.database.campos;

/**
 * Created by ricairon on 11/12/2016.
 */

public class Tabela_Usuarios {
    public static final String TABELA_USUARIOS = "USUARIOS";
    public static String ID = "_id";
    public static String NOME = "nome";
    public static String EMAIL = "email";
    public static String SENHA = "senha";

    //Gambiarra feita para não ficar resgatando o id do usuário o tempo inteiro.
    public static long POG_id_do_usuario;
}
