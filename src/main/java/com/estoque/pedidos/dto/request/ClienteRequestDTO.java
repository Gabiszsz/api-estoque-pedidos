package com.estoque.pedidos.dto.request;

public class ClienteRequestDTO {
    
    private String cpf;
    private String enderecoCompleto;

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEnderecoCompleto() { return enderecoCompleto; }
    public void setEnderecoCompleto(String enderecoCompleto) { this.enderecoCompleto = enderecoCompleto; 
        
    }
}
