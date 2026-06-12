package com.estoque.pedidos.dto.response;

import org.springframework.hateoas.RepresentationModel;

public class ClienteResponseDTO extends RepresentationModel<ClienteResponseDTO> {
    
    private Long id;
    private String cpf;
    private String enderecoCompleto;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String cpf, String enderecoCompleto) {
        this.id = id;
        this.cpf = cpf;
        this.enderecoCompleto = enderecoCompleto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEnderecoCompleto() { return enderecoCompleto; }
    public void setEnderecoCompleto(String enderecoCompleto) { this.enderecoCompleto = enderecoCompleto; }
}