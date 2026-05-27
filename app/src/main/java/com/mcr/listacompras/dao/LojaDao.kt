package com.mcr.listacompras.dao

import android.content.ContentValues
import com.mcr.listacompras.database.DatabaseHelper
import com.mcr.listacompras.model.Loja

class LojaDao(private val db: DatabaseHelper) {

    fun inserir(loja: Loja): Long {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COL_LOJA_NOME, loja.nome)
        cv.put(DatabaseHelper.COL_LOJA_ENDERECO, loja.endereco)
        return db.writableDatabase.insert(DatabaseHelper.TABLE_LOJA, null, cv)
    }

    fun listarTodas(): List<Loja> {
        val lista = mutableListOf<Loja>()
        val cursor = db.readableDatabase.query(
            DatabaseHelper.TABLE_LOJA, null,
            null, null, null, null, "${DatabaseHelper.COL_LOJA_NOME} ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    Loja(
                        id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_LOJA_ID)),
                        nome = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_LOJA_NOME)),
                        endereco = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_LOJA_ENDERECO)) ?: ""
                    )
                )
            }
        }
        return lista
    }

    fun deletar(id: Int): Int =
        db.writableDatabase.delete(
            DatabaseHelper.TABLE_LOJA,
            "${DatabaseHelper.COL_LOJA_ID} = ?",
            arrayOf(id.toString())
        )
}