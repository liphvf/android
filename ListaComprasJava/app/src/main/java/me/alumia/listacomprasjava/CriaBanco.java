package me.alumia.listacomprasjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "produtos.db";
    private static final String TABELA = "produtos";
    private static final String ID = "_id";
    private static final String NOME = "titulo";
    private static final int QUANTIDADE = 0;
    private static final Double VALOR = 0.0;
    private static final Bitmap FOTO = null;
    private static final int VERSAO = 1;

    public CriaBanco(Context context, String name, @androidx.annotation.Nullable @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE" + TABELA + "("
                + ID + "integer primary key autoincrement"
                + NOME + "text"
                + QUANTIDADE + "INTEGER"
                + VALOR + "REAL"
                + FOTO + "BLOB";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABELA);
            onCreate(db);
        }
    }
}
