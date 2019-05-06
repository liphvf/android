package me.alumia.listadecompras

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class BancoLocal(context: Context?) :
    SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO) {

    companion object {
        private val NOME_BANCO = "produtos.db"
        private val TABELA = "produtos"
        private val ID = "_id"
        private val NOME = "nome"
        private val QUANTIDADE = "quantidade"
        private val VALOR = "valor"
        private val FOTO = "foto"
        private val VERSAO = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = (" CREATE TABLE " + TABELA + " ( "
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + QUANTIDADE + " INTEGER, "
                + VALOR + " REAL, "
                + FOTO + " BLOB "
                + " ) ")

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABELA")
        onCreate(db)
    }

    fun insereDado(nome: String, quantidade: Int, valor: Double, foto: Bitmap?): String {
        val valores: ContentValues
        val resultado: Long

        valores = ContentValues()
        valores.put(NOME, nome)
        valores.put(QUANTIDADE, quantidade)
        valores.put(VALOR, valor)

        if (foto != null) {
            val streamFoto = ByteArrayOutputStream()
            foto.compress(Bitmap.CompressFormat.PNG, 100, streamFoto)
            valores.put(FOTO, streamFoto.toByteArray())
        }

        val db = this.writableDatabase

        resultado = db.insert(TABELA, null, valores)

        db.close()

        return if ( resultado.toInt()  == -1) {
            "Erro ao inserir registro."
        } else {
            "Registro inserido com sucesso."
        }
    }


    fun carregaDados(): Cursor? {

        val cursor: Cursor?
        val campos = arrayOf(ID, NOME)
        val db = this.readableDatabase
        cursor = db.query(TABELA, campos, null, null, null, null, null, null)

        cursor?.moveToFirst()
        db.close()
        return cursor
    }

    fun getProdutos(): ArrayList<Produto> {
        val produtos = ArrayList<Produto>()

        val db = this.readableDatabase
        val mCursor = db.query(TABELA, null, null, null, null, null, null, null)

        if (mCursor!!.moveToFirst()) {
            do {
                var produto = Produto();
                produto.id =mCursor.getInt(mCursor.getColumnIndexOrThrow("_id"))
                produto.nome =  mCursor.getString(mCursor.getColumnIndexOrThrow("nome"))
                produto.quantidade = mCursor.getInt(mCursor.getColumnIndexOrThrow("quantidade"))
                produto.valor = mCursor.getDouble(mCursor.getColumnIndexOrThrow("valor"))

                val fotoBanco = mCursor.getBlob(mCursor.getColumnIndexOrThrow("foto"))

                if (fotoBanco != null && fotoBanco.size > 0) {
                    produto.foto = BitmapFactory.decodeByteArray(fotoBanco, 0, fotoBanco.size)
                }

                produtos.add(produto)

            } while (mCursor.moveToNext())
        }

        if (mCursor != null && !mCursor.isClosed) {
            mCursor.close()
        }

        return produtos
    }

    fun deletaRegistro(id: Int) {
        val db = this.writableDatabase
        db.delete(TABELA, ID + "=?", arrayOf(id.toString()))

        db.close()
    }
}