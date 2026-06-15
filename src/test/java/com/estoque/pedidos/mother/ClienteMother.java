package com.estoque.pedidos.mother;

import com.estoque.pedidos.model.Cliente;

public class ClienteMother {
    public static Cliente criarClienteValido() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        return cliente;
    }
}