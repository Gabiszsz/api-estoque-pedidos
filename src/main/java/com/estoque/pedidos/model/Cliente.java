package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.*;
import com.estoque.pedidos.model.vo.Cpf;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Cpf cpf;

    private String enderecoCompleto;

    public Cliente() {
    }

    public Cliente(Long id, Cpf cpf, String enderecoCompleto) {
        this.id = id;
        this.cpf = cpf;
        this.enderecoCompleto = enderecoCompleto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, enderecoCompleto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Cliente other = (Cliente) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(cpf, other.cpf) &&
                Objects.equals(enderecoCompleto, other.enderecoCompleto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Cpf getCpf() {

        return cpf;
    }

    public void setCpf(Cpf cpf) {

        this.cpf = cpf;
    }

    public String getEnderecoCompleto() {

        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {

        this.enderecoCompleto = enderecoCompleto;
    }
}