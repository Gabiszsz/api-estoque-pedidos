package com.estoque.pedidos.dto.response;

import org.springframework.hateoas.RepresentationModel;

public class FornecedorResponseDTO extends RepresentationModel<FornecedorResponseDTO> {
    
    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String contatoVendedor;

    public FornecedorResponseDTO() {
    }

    public FornecedorResponseDTO(Long id, String cnpj, String razaoSocial, String contatoVendedor) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.contatoVendedor = contatoVendedor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getContatoVendedor() { return contatoVendedor; }
    public void setContatoVendedor(String contatoVendedor) { this.contatoVendedor = contatoVendedor; }
}