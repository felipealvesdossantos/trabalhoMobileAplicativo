package com.projetofinal.ufgnote.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersistenciaBD extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "ufgnote";
	private static final int DATABASE_VERSION = 1;
	public static final String NOME_TABELA = "cadastro";
	
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_MATRICULA = "matricula";
    public static final String COLUNA_TIPO = "tipo";
    public static final String COLUNA_USUARIO = "usuario";
    public static final String COLUNA_SENHA = "senha";
    private SQLiteDatabase banco;
    
    public static final String CRIA_TABELA = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_NOME + " INTEGER PRIMARY KEY," + COLUNA_MATRICULA + " TEXT," + COLUNA_USUARIO + " TEXT,"
            + COLUNA_TIPO + " TEXT" + COLUNA_USUARIO + " TEXT" + COLUNA_SENHA + " TEXT" + ")";
      
    public PersistenciaBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CRIA_TABELA);
    }
	
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PersistenciaBD.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
       db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
       onCreate(db);
    }
}
