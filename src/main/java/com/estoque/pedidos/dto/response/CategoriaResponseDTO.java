package com.estoque.pedidos.dto.response;

import org.springframework.hateoas.RepresentationModel;

// Mudamos de 'record' para 'class' e estendemos 'RepresentationModel' para ativar o HATEOAS
public class CategoriaResponseDTO extends RepresentationModel<CategoriaResponseDTO> {
    
    private Long id;
    private String nome;

    // Construtor vazio (obrigatório para o Spring converter para JSON)
    public CategoriaResponseDTO() {
    }

    // Construtor com os parâmetros
    public CategoriaResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters (obrigatórios)
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
}