package me.alumia.listacomprasjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BancoLocal extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "produtos.db";
    private static final String TABELA = "produtos";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String QUANTIDADE = "quantidade";
    private static final String VALOR = "valor";
    private static final String FOTO = "foto";
    private static final int VERSAO = 1;


    public BancoLocal(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = " CREATE TABLE " + TABELA + " ( "
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + QUANTIDADE + " INTEGER, "
                + VALOR + " REAL, "
                + FOTO + " BLOB "
                + " ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    public String insereDado(String nome, int quantidade, double valor, Bitmap foto) {
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(BancoLocal.NOME, nome);
        valores.put(BancoLocal.QUANTIDADE, quantidade);
        valores.put(BancoLocal.VALOR, valor);

        if (foto != null) {
            ByteArrayOutputStream streamFoto = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 100, streamFoto);
            valores.put(BancoLocal.FOTO, streamFoto.toByteArray());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        resultado = db.insert(BancoLocal.TABELA, null, valores);

        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro inserido com sucesso";

    }

    public Cursor carregaDados() {

        Cursor cursor;
        String[] campos = {this.ID, this.NOME};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(this.TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public ArrayList<Produto> getProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(this.TABELA, null, null, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow("_id")));
                produto.setNome(mCursor.getString(mCursor.getColumnIndexOrThrow("nome")));
                produto.setQuantidade(mCursor.getInt(mCursor.getColumnIndexOrThrow("quantidade")));
                produto.setValor(mCursor.getDouble(mCursor.getColumnIndexOrThrow("valor")));

                byte[] fotoBanco = mCursor.getBlob(mCursor.getColumnIndexOrThrow("foto"));

                if (fotoBanco != null && fotoBanco.length > 0) {
                    produto.setFoto(BitmapFactory.decodeByteArray(fotoBanco, 0, fotoBanco.length));
                }

                produtos.add(produto);

            } while (mCursor.moveToNext());
        }

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return produtos;
    }

    public void deletaRegistro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] ids = {Integer.toString(id)};
        db.delete(TABELA, BancoLocal.ID + "=?", ids);

        db.close();
    }
}
