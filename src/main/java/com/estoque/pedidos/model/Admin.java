package com.estoque.pedidos.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private String nivelAcesso;

    public Admin() {
    }

    public Admin(
        Long id,
        String login,
        String senha,
        String nivelAcesso
    ) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
