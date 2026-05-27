package com.mcr.listacompras.dao

import android.content.ContentValues
import com.mcr.listacompras.database.DatabaseHelper
import com.mcr.listacompras.model.ItemCompra

class ItemCompraDao(private val db: DatabaseHelper) {

    fun inserir(item: ItemCompra): Long {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COL_ITEM_NOME, item.nome)
        cv.put(DatabaseHelper.COL_ITEM_QUANTIDADE, item.quantidade)
        cv.put(DatabaseHelper.COL_ITEM_PRECO, item.preco)
        cv.put(DatabaseHelper.COL_ITEM_COMPRADO, if (item.comprado) 1 else 0)
        cv.put(DatabaseHelper.COL_ITEM_LOJA_ID, item.lojaId)
        return db.writableDatabase.insert(DatabaseHelper.TABLE_ITEM, null, cv)
    }

    fun listarPorLoja(lojaId: Int): List<ItemCompra> {
        val lista = mutableListOf<ItemCompra>()
        val cursor = db.readableDatabase.query(
            DatabaseHelper.TABLE_ITEM, null,
            "${DatabaseHelper.COL_ITEM_LOJA_ID} = ?",
            arrayOf(lojaId.toString()),
            null, null, "${DatabaseHelper.COL_ITEM_NOME} ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    ItemCompra(
                        id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_ID)),
                        nome = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_NOME)),
                        quantidade = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_QUANTIDADE)),
                        preco = it.getDouble(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_PRECO)),
                        comprado = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_COMPRADO)) == 1,
                        lojaId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM_LOJA_ID))
                    )
                )
            }
        }
        return lista
    }

    fun marcarComprado(id: Int, comprado: Boolean): Int {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COL_ITEM_COMPRADO, if (comprado) 1 else 0)
        return db.writableDatabase.update(
            DatabaseHelper.TABLE_ITEM, cv,
            "${DatabaseHelper.COL_ITEM_ID} = ?", arrayOf(id.toString())
        )
    }

    fun deletar(id: Int): Int =
        db.writableDatabase.delete(
            DatabaseHelper.TABLE_ITEM,
            "${DatabaseHelper.COL_ITEM_ID} = ?",
            arrayOf(id.toString())
        )
}