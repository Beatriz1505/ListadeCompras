package com.mcr.listacompras.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "lista_compras_mcr.db"
        const val DATABASE_VERSION = 1

        // Tabela Loja
        const val TABLE_LOJA = "loja"
        const val COL_LOJA_ID = "id"
        const val COL_LOJA_NOME = "nome"
        const val COL_LOJA_ENDERECO = "endereco"

        // Tabela ItemCompra
        const val TABLE_ITEM = "item_compra"
        const val COL_ITEM_ID = "id"
        const val COL_ITEM_NOME = "nome"
        const val COL_ITEM_QUANTIDADE = "quantidade"
        const val COL_ITEM_PRECO = "preco"
        const val COL_ITEM_COMPRADO = "comprado"
        const val COL_ITEM_LOJA_ID = "loja_id" // FOREIGN KEY
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Habilita suporte a Foreign Keys no SQLite
        db.execSQL("PRAGMA foreign_keys = ON")

        val createLoja = """
            CREATE TABLE $TABLE_LOJA (
                $COL_LOJA_ID   INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_LOJA_NOME TEXT    NOT NULL,
                $COL_LOJA_ENDERECO TEXT
            )
        """.trimIndent()

        val createItem = """
            CREATE TABLE $TABLE_ITEM (
                $COL_ITEM_ID         INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ITEM_NOME       TEXT    NOT NULL,
                $COL_ITEM_QUANTIDADE INTEGER NOT NULL DEFAULT 1,
                $COL_ITEM_PRECO      REAL    NOT NULL DEFAULT 0.0,
                $COL_ITEM_COMPRADO   INTEGER NOT NULL DEFAULT 0,
                $COL_ITEM_LOJA_ID    INTEGER NOT NULL,
                FOREIGN KEY ($COL_ITEM_LOJA_ID)
                    REFERENCES $TABLE_LOJA($COL_LOJA_ID)
                    ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createLoja)
        db.execSQL(createItem)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOJA")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        // Reativa FK a cada abertura do banco
        db.execSQL("PRAGMA foreign_keys = ON")
    }
}