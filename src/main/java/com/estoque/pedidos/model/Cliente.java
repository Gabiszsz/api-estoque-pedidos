package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String enderecoCompleto;

    public Cliente() {
    }

    public Cliente(Long id, String cpf, String enderecoCompleto) {
        this.id = id;
        this.cpf = cpf;
        this.enderecoCompleto = enderecoCompleto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((enderecoCompleto == null) ? 0 : enderecoCompleto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Cliente other = (Cliente) obj;

        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;

        if (enderecoCompleto == null) {
            if (other.enderecoCompleto != null)
                return false;
        } else if (!enderecoCompleto.equals(other.enderecoCompleto))
            return false;

        return true;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }
}