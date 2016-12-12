package com.trabalho.ricardolopes.uniforacademico.model;

import java.io.Serializable;

/**
 * Created by Administrador on 10/12/2016.
 */

public class Disciplina implements IModel, Serializable{

    private Long id;
    private String nome;
    private String descricao;
    private String semestre;

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return "S" +getSemestre() +" "+getNome();
    }
}
