package com.estoque.pedidos.dto.request;

public class FornecedorRequestDTO {

    private String cnpj;
    private String razaoSocial;
    private String contatoVendedor;

    // Getters e Setters
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getContatoVendedor() { return contatoVendedor; }
    public void setContatoVendedor(String contatoVendedor) { this.contatoVendedor = contatoVendedor; 

    }
}   

