package com.trabalho.ricardolopes.uniforacademico.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Disciplinas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Matriculas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;

/**
 * Created by Ricardo Lopes de Lima on 11/12/2016.
 * Classe responsável pela criação e atualização do banco de dados.
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "unifor.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DROP = "DROP TABLE IF EXISTS ";

    //Comandos SQL de criação das tabelas.
    private String cria_tabela_usuario = "CREATE TABLE IF NOT EXISTS USUARIOS( " +
            " _id integer PRIMARY KEY autoincrement, " +
            " nome text UNIQUE NOT NULL, " +
            " email text," +
            " senha text NOT NULL" +
            ")";
    private String cria_tabela_disciplinas = "CREATE TABLE IF NOT EXISTS DISCIPLINAS( " +
            " _id integer PRIMARY KEY autoincrement," +
            " nome text NOT NULL," +
            " descricao text," +
            " semestre integer" +
            ")";
    private String cria_tabela_matriculas = "CREATE TABLE IF NOT EXISTS MATRICULAS( " +
            " _id integer PRIMARY KEY autoincrement," +
            " id_usuario integer NOT NULL," +
            " id_disciplina integer NOT NULL," +
            " data text," +
            " FOREIGN KEY (id_usuario) REFERENCES USUARIOS(_id), " +
            " FOREIGN KEY (id_disciplina) REFERENCES DISCIPLINAS(_id)" +
            ")";

    private String valores_Disciplinas = "INSERT INTO DISCIPLINAS VALUES"
            +"(NULL, \"Matemática discreta\", \"N096\" , 1)" +","
            +"(NULL, \"Cálculo 1\", \"N610\" , 1)" +","
            +"(NULL, \"Informática e Sociedade\", \"N605\" , 1)" +","
            +"(NULL, \"Introdução a Computação\", \"N610\" , 1)" +","
            +"(NULL, \"Lógica de Programação\", \"N573\" , 1)" +","

            +"(NULL, \"Alg Linear e Geom Analítica\", \"N094\" , 2)" +","
            +"(NULL, \"Cálculo II\", \"N611\" , 2)" +","
            +"(NULL, \"Prog Orientada A Objetos\", \"T922\" , 2)" +","
            +"(NULL, \"Projeto de Interface\", \"N579\" , 2)" +","
            +"(NULL, \"Sistemas Lógicos e Digitais\", \"N532\" , 2)" +","

            +"(NULL, \"Admin, Empreend e Inovação\", \"N620\" , 3)" +","
            +"(NULL, \"Arquit e Org de Computadores\", \"N607\" , 3)" +","
            +"(NULL, \"Estrutura de Dados\", \"T923\" , 3)" +","
            +"(NULL, \"Logica Matematica\", \"N097\" , 3)" +","
            +"(NULL, \"Técnicas de Programação\", \"N524\" , 3)" +","

            +"(NULL, \"Cálculo Numérico\", \"N617\" , 4)" +","
            +"(NULL, \"Fundamentos de Banco de Dados\", \"N673\" , 4)" +","
            +"(NULL, \"Probabilidade e Estatística\", \"N618\" , 4)" +","
            +"(NULL, \"Tecnologias Internet I\", \"N554\" , 4)" +","
            +"(NULL, \"Teoria dos Grafos\", \"N583\" , 4)" +","

            +"(NULL, \"Eng Requisitos Teste Software\", \"N672\" , 5)" +","
            +"(NULL, \"Gestao da Tecn da Informacao\", \"N565\" , 5)" +","
            +"(NULL, \"Produção de Trab Científicos\", \"N421\" , 5)" +","
            +"(NULL, \"Proj Analise de Algoritmos\", \"N584\" , 5)" +","
            +"(NULL, \"Sistemas Operacionais\", \"T952\" , 5)" +","
            +"(NULL, \"Tec de Impl de Sistemas de Bd\", \"N592\" , 5)"
            ;

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Cria as tabelas se elas não existem.
        sqLiteDatabase.execSQL(cria_tabela_usuario);
        sqLiteDatabase.execSQL(cria_tabela_disciplinas);
        sqLiteDatabase.execSQL(cria_tabela_matriculas);
        //Já coloca uns valores padrões no banco de dados disciplina.
        sqLiteDatabase.execSQL(valores_Disciplinas);
    }

    /**
     * Método que é chamado quando a atualizar a versão do banco de dados.
     * @param sqLiteDatabase O banco de dados.
     * @param oldVersion Inteiro com a velha versão do banco de dados.
     * @param newVersion Inteiro com a nova versão do banco de dados.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(DATABASE_VERSION < newVersion){
            sqLiteDatabase.execSQL(DROP + Tabela_Disciplinas.TABELA_DISCIPLINAS);
            sqLiteDatabase.execSQL(DROP + Tabela_Matriculas.TABELA_MATRICULAS);
            sqLiteDatabase.execSQL(DROP + Tabela_Usuarios.TABELA_USUARIOS);
        }

    }
}
