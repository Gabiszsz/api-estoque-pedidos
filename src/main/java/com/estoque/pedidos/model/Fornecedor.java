package com.estoque.pedidos.model;

import java.io.Serializable;

public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String contatoVendedor;

    public Fornecedor() {
    }

    public Fornecedor(
        Long id,
        String cnpj,
        String razaoSocial,
        String contatoVendedor
    ) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.contatoVendedor = contatoVendedor;
    }

    public Long getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getContatoVendedor() {
        return contatoVendedor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setContatoVendedor(String contatoVendedor) {
        this.contatoVendedor = contatoVendedor;
    }
}
