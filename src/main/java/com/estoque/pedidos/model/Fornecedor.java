package com.estoque.pedidos.model;

import java.io.Serializable;
import jakarta.persistence.*;
import com.estoque.pedidos.model.vo.Cnpj;

@Entity
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Cnpj cnpj;

    private String razaoSocial;
    private String contatoVendedor;

    public Fornecedor() {
    }

    public Fornecedor(Long id, Cnpj cnpj, String razaoSocial, String contatoVendedor) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.contatoVendedor = contatoVendedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getContatoVendedor() {
        return contatoVendedor;
    }

    public void setContatoVendedor(String contatoVendedor) {
        this.contatoVendedor = contatoVendedor;
    }
}