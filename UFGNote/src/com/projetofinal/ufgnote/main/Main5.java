package com.projetofinal.ufgnote.main;

import com.projetofinal.ufgnote.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;


public class Main5 extends Activity {

	Button btCadastro, btProfessor, btAluno, btVisitante;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notevisitante);
	}
	
	public boolean onCreateOptionMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main1, menu);
		return true;
	}
}
