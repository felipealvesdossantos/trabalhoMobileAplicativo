package com.projetofinal.ufgnote.main;

import com.projetofinal.ufgnote.R;
import com.projetofinal.ufgnote.persistencia.PersistenciaBD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main1 extends Activity {
	
	Button btCadastro, btLogin, btVisitante;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaprincipal);
				
		btCadastro = (Button) findViewById(R.id.btCadastrar);
		btCadastro.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent trocaTela1 = new Intent(Main1.this, Main2.class);
				Main1.this.startActivity(trocaTela1);
				Main1.this.finish();
			}
		});
					
		btLogin = (Button) findViewById(R.id.btAluno);
		btLogin.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				Intent trocaTela2 = new Intent(Main1.this, Main3.class);
				Main1.this.startActivity(trocaTela2);
				Main1.this.finish();
			}		
		});
		
		btVisitante = (Button) findViewById(R.id.btVisitante);
		btVisitante.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				Intent trocaTela2 = new Intent(Main1.this, Main5.class);
				Main1.this.startActivity(trocaTela2);
				Main1.this.finish();
			}		
		});
	}
	
	public boolean onCreateOptionMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main1, menu);
		return true;
	}
}






