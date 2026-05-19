package com.estoque.pedidos.dto.response;

public class ClienteResponseDTO {
    
    private Long id;
    private String cpf;
    private String enderecoCompleto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEnderecoCompleto() { return enderecoCompleto; }
    public void setEnderecoCompleto(String enderecoCompleto) { this.enderecoCompleto = enderecoCompleto; 
        
    }
}
