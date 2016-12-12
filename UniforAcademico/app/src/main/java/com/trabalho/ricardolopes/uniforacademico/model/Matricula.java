package com.trabalho.ricardolopes.uniforacademico.model;

import java.io.Serializable;

/**
 * Created by Administrador on 10/12/2016.
 */

public class Matricula implements IModel, Serializable{

    private Long id;
    private Long id_usuario;
    private Long id_disciplina;
    private String data;


    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Long id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
