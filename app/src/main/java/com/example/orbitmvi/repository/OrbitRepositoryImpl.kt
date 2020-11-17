package com.example.orbitmvi.repository

class OrbitRepositoryImpl : OrbitRepository{
    override fun getNotes(): List<String> =
        listOf("Teste", "Teste 1", "Teste2")
}