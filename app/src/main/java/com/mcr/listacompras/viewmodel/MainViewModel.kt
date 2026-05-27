package com.mcr.listacompras.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.mcr.listacompras.dao.ItemCompraDao
import com.mcr.listacompras.dao.LojaDao
import com.mcr.listacompras.database.DatabaseHelper
import com.mcr.listacompras.model.ItemCompra
import com.mcr.listacompras.model.Loja

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dbHelper = DatabaseHelper(app)
    private val lojaDao = LojaDao(dbHelper)
    private val itemDao = ItemCompraDao(dbHelper)

    private val _lojas = MutableStateFlow<List<Loja>>(emptyList())
    val lojas: StateFlow<List<Loja>> = _lojas

    private val _itens = MutableStateFlow<List<ItemCompra>>(emptyList())
    val itens: StateFlow<List<ItemCompra>> = _itens

    init { carregarLojas() }

    fun carregarLojas() {
        viewModelScope.launch(Dispatchers.IO) {
            _lojas.value = lojaDao.listarTodas()
        }
    }

    fun adicionarLoja(nome: String, endereco: String) {
        viewModelScope.launch(Dispatchers.IO) {
            lojaDao.inserir(Loja(nome = nome, endereco = endereco))
            _lojas.value = lojaDao.listarTodas()
        }
    }

    fun deletarLoja(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            lojaDao.deletar(id)
            _lojas.value = lojaDao.listarTodas()
        }
    }

    fun carregarItens(lojaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _itens.value = itemDao.listarPorLoja(lojaId)
        }
    }

    fun adicionarItem(nome: String, qtd: Int, preco: Double, lojaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.inserir(ItemCompra(nome = nome, quantidade = qtd, preco = preco, lojaId = lojaId))
            _itens.value = itemDao.listarPorLoja(lojaId)
        }
    }

    fun marcarComprado(id: Int, comprado: Boolean, lojaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.marcarComprado(id, comprado)
            _itens.value = itemDao.listarPorLoja(lojaId)
        }
    }

    fun deletarItem(id: Int, lojaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deletar(id)
            _itens.value = itemDao.listarPorLoja(lojaId)
        }
    }
}