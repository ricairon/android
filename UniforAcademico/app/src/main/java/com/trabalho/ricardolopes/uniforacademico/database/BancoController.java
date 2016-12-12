package com.trabalho.ricardolopes.uniforacademico.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Disciplinas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Matriculas;
import com.trabalho.ricardolopes.uniforacademico.database.campos.Tabela_Usuarios;
import com.trabalho.ricardolopes.uniforacademico.database.excecoes.UsuarioJaCadastrado;
import com.trabalho.ricardolopes.uniforacademico.model.Disciplina;
import com.trabalho.ricardolopes.uniforacademico.model.Matricula;
import com.trabalho.ricardolopes.uniforacademico.model.Usuario;

import java.util.ArrayList;

/**
 * Classe responsável pelo controle do banco de dados.
 * Created by Ricardo Lopes de Lima on 11/12/2016.
 * @version 1.0
 */
public class BancoController {

    private SQLiteDatabase db;
    private DatabaseHelper banco;

    /**
     * Construtor do banco que recebe o contexto da aplicação.
     * @param context Contexto.
     */
    public BancoController(Context context){
        this.banco = new DatabaseHelper(context);
    }

    public ArrayList<Disciplina> getDisciplinas(){
        Cursor cursor;
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        this.db = this.banco.getReadableDatabase();

        String selection = "select * from DISCIPLINAS";

        cursor = this.db.rawQuery(selection,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Disciplina novaDisciplina = new Disciplina();
                novaDisciplina.setId(cursor.getLong(cursor.getColumnIndex(Tabela_Disciplinas.ID)));
                novaDisciplina.setNome(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.NOME)));
                novaDisciplina.setDescricao(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.DESCRICAO)));
                novaDisciplina.setSemestre(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.SEMESTRE)));
                disciplinas.add(novaDisciplina);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return disciplinas;
    }

    public ArrayList<Disciplina> getMatriculasUsuario(long id_usuario){
        Cursor cursor;
        ArrayList<Disciplina> matriculas = new ArrayList<>();
        this.db = this.banco.getReadableDatabase();

        String sql = "SELECT DISTINCT DISCIPLINAS._id, DISCIPLINAS.nome, DISCIPLINAS.descricao, DISCIPLINAS.semestre "
                +"FROM MATRICULAS, DISCIPLINAS " +
                "WHERE MATRICULAS.id_usuario = "+id_usuario +" AND MATRICULAS.id_disciplina = DISCIPLINAS._id";

        try{
            cursor = this.db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(cursor.getLong(cursor.getColumnIndex(Tabela_Disciplinas.ID)));
                    disciplina.setNome(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.NOME)));
                    disciplina.setDescricao(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.DESCRICAO)));
                    disciplina.setSemestre(cursor.getString(cursor.getColumnIndex(Tabela_Disciplinas.SEMESTRE)));
                    matriculas.add(disciplina);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch (Exception e){
            Log.e("GETDISCIPLINAS", e.getMessage());
        }
        return matriculas;
    }

    public int matricularUsuarioNaDisciplina(long id_Usuario, long id_Disciplina, String data){
        this.db = this.banco.getWritableDatabase();
        ContentValues novoRegistro = new ContentValues();
        novoRegistro.putNull(Tabela_Matriculas.ID);//Ele configurará um novo id automaticamente.
        novoRegistro.put(Tabela_Matriculas.ID_USUARIO, id_Usuario);
        novoRegistro.put(Tabela_Matriculas.ID_DISCIPLINA, id_Disciplina);
        novoRegistro.put(Tabela_Matriculas.DATA, data);
        try{
            db.insertOrThrow(Tabela_Matriculas.TABELA_MATRICULAS, null, novoRegistro);
            return 0;
        }catch (SQLiteConstraintException constraintException){
            return 1;//TODO Algum erro não visto.
        }catch (Exception e){
            Log.e("UNI_BD_EXC",e.getMessage());
            return 2;
        }finally {
            db.close();
        }
    }

    /**
     * Cadastra o usuário no banco de dados.
     * @param nome Nome do Usuário.
     * @param email E-mail do Usuário.
     * @param senha Senha do Usuário
     * @return Retorna 0 caso sucesso, 1 para usuário já cadastrado e 2 para outros erros.
     */
    public int cadastraUsuario(String nome, String email, String senha){

        this.db = this.banco.getWritableDatabase();
        ContentValues novoRegistro = new ContentValues();;
        novoRegistro.putNull(Tabela_Usuarios.ID);//Ele configurará um novo id automaticamente.
        novoRegistro.put(Tabela_Usuarios.NOME, nome);
        novoRegistro.put(Tabela_Usuarios.EMAIL, email);
        novoRegistro.put(Tabela_Usuarios.SENHA,senha);
        int codigoErro;
        try{
            db.insertOrThrow(Tabela_Usuarios.TABELA_USUARIOS, null, novoRegistro);
            return 0;
        }catch (SQLiteConstraintException constraintException){
            return 1;//Usuário já cadastrado
        }catch (Exception e){
            Log.e("UNI_BD_EXC",e.getMessage());
            return 2;
        }finally {
            db.close();
        }
    }


    /**
     * Método utilizado para efetuar login no aplicativo
     * @param nome Nome de login do usuário.
     * @param senha Senha do usuário.
     * @return Usuário Objeto usuário.
     */
    public Usuario efetuarLogin(String nome, String senha){
        Cursor cursor;
        String [] colunaRetorno = {Tabela_Usuarios.ID, Tabela_Usuarios.NOME, Tabela_Usuarios.EMAIL};
        String selection = "nome = ? AND senha = ?";
        String [] selectionArgs = {nome, senha};
        this.db = this.banco.getReadableDatabase();
        try{
            cursor = this.db.query(Tabela_Usuarios.TABELA_USUARIOS, colunaRetorno,selection, selectionArgs, null, null, null);
            if(cursor!=null){
                cursor.moveToFirst();
                if(cursor.getCount() == 0){
                    return null;
                }else{
                    long _idUsuario = cursor.getLong(cursor.getColumnIndex(Tabela_Usuarios.ID));
                    String nomeUsuario = cursor.getString(cursor.getColumnIndex(Tabela_Usuarios.NOME));
                    String email = cursor.getString(cursor.getColumnIndex(Tabela_Usuarios.EMAIL));
                    return new Usuario(_idUsuario, nomeUsuario, email, "");
                }
            }
        }catch (Exception e){
            Log.e("BANC_CONT", e.getMessage());
        }finally {
            db.close();
        }
        return null;
    }


}
