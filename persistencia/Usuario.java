package com.projetofinal.ufgnote.persistencia;

public class Usuario {
	
	private String nome;
	private String matricula;
	private String tipo;
	private String usuario;
	private String senha;

	public Usuario() {
		 
    }
     
    public Usuario(String nome, String matricula, String tipo, String usuario, String senha) {
        super();
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
        this.usuario = usuario;
        this.senha = senha;
    }
	
	
	public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getMatricula() {
        return matricula;
    }
 
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getTipo() {
        return tipo;
    }
 
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getSenha() {
        return senha;
    }
 
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
