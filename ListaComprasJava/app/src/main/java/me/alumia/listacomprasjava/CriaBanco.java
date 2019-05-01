package me.alumia.listacomprasjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "produtos.db";
    private static final String TABELA = "produtos";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String QUANTIDADE = "quantidade";
    private static final String VALOR = "valor";
    private static final String FOTO = null;
    private static final int VERSAO = 1;
    private static SQLiteDatabase _db;

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        _db = this.getWritableDatabase();

        String sql = "CREATE TABLE" + TABELA + "("
                + ID + "integer primary key autoincrement"
                + NOME + "text"
                + QUANTIDADE + "INTEGER"
                + VALOR + "REAL"
                + FOTO + "BLOB";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABELA);
            onCreate(db);
//        }
    }

    public String insereDado(String nome, int quantidade, double valor, Bitmap foto){
        ContentValues valores;
        long resultado;

        valores = new ContentValues();
        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.QUANTIDADE, quantidade);
        valores.put(CriaBanco.VALOR, valor);

        if (foto != null) {
            ByteArrayOutputStream streamFoto = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 100, streamFoto);
            valores.put(CriaBanco.FOTO, streamFoto.toByteArray());
        }


        resultado = _db.insert(CriaBanco.TABELA, null, valores);

        _db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {this.ID,this.NOME};
        _db = this.getReadableDatabase();
        cursor = _db.query(this.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        _db.close();
        return cursor;
    }

    public ArrayList<Produto> getProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();

        Cursor mCursor = _db.query(this.TABELA, null, null, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do{
                Produto produto = new Produto();
                produto.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow("id")));
                produto.setNome(mCursor.getString(mCursor.getColumnIndexOrThrow("nome")));
                produto.setQuantidade(mCursor.getInt(mCursor.getColumnIndexOrThrow("quantidade")));
                produto.setValor(mCursor.getDouble(mCursor.getColumnIndexOrThrow("valor")));

                byte[] fotoBanco = mCursor.getBlob(mCursor.getColumnIndexOrThrow("foto"));

                produto.setFoto(BitmapFactory.decodeByteArray(fotoBanco,0, fotoBanco.length));

                produtos.add(produto);

            } while (mCursor.moveToNext());
        }

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return produtos;
    }

    public void deletaRegistro(int id){
        String where = CriaBanco.ID + "=" + id;
        _db = this.getReadableDatabase();
        _db.delete(CriaBanco.TABELA,where,null);
        _db.close();
    }
}
