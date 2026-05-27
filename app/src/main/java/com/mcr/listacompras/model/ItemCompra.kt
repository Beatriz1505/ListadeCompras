package com.mcr.listacompras.model

data class ItemCompra(
    val id: Int = 0,
    val nome: String,
    val quantidade: Int = 1,
    val preco: Double = 0.0,
    val comprado: Boolean = false,
    val lojaId: Int  // Foreign Key
)