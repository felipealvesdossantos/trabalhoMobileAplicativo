package com.projetofinal.ufgnote.persistencia;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsuarioDAO {
	
	private SQLiteDatabase database;
    private PersistenciaBD persistencia;
    
    private String[] colunas = {
    		PersistenciaBD.COLUNA_NOME, 
    		PersistenciaBD.COLUNA_MATRICULA,
    		PersistenciaBD.COLUNA_TIPO, 
    		PersistenciaBD.COLUNA_USUARIO, 
    		PersistenciaBD.COLUNA_SENHA};
	
    public UsuarioDAO(Context context) {
        persistencia = new PersistenciaBD(context);
    }
 
    public void open() throws SQLException {
        database = persistencia.getWritableDatabase();
    }
 
    public void close() {
        persistencia.close();
    }
 
    public long Inserir(Usuario user) {
        ContentValues values = new ContentValues();

        values.put(PersistenciaBD.COLUNA_NOME, user.getNome());
        values.put(PersistenciaBD.COLUNA_MATRICULA, user.getMatricula());
        values.put(PersistenciaBD.COLUNA_TIPO, user.getTipo());
        values.put(PersistenciaBD.COLUNA_USUARIO, user.getUsuario());
        values.put(PersistenciaBD.COLUNA_SENHA, user.getSenha());
         
        return database.insert(PersistenciaBD.NOME_TABELA, null, values);
    }
    
    private Usuario listaUsuarios(Cursor cursor) {
        Usuario user = new Usuario();
        user.setNome(cursor.getString(0));
        user.setMatricula(cursor.getString(1));
        user.setTipo(cursor.getString(2));
        user.setUsuario(cursor.getString(3));
        user.setSenha(cursor.getString(4));
        
        return user;
    }
    
    public List<Usuario> Consultar() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
 
        Cursor cursor = database.query(PersistenciaBD.NOME_TABELA, colunas, PersistenciaBD.COLUNA_NOME, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Usuario userCursor = listaUsuarios(cursor);
            usuarios.add(userCursor);
            cursor.moveToNext();
        }
        cursor.close();
        return usuarios;
    }
}
