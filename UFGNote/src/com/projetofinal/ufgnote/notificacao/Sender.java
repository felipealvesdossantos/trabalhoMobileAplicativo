package com.projetofinal.ufgnote.notificacao;

public class Sender {
	public static final int COURSE_COORDINATOR = 1;
	public static final int BOARD_UNITY = 2;
	public static final int LIBRARY = 3;
	public static final int PRO_RECTORY = 4;
	public static final int RECTORY = 5;
	public static final int DOCENTE_DISCIPLINA = 6;
	public static final int GENERAL = 7;

	private int id;
	private String name;

	public Sender(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Sender getDefault(int id) {
		Sender sender = null;

		switch (id) {
		case COURSE_COORDINATOR:
			sender = new Sender(id, "Coordenador de curso");
			break;

		case BOARD_UNITY:
			sender = new Sender(id, "Dire��o de unidade");
			break;

		case LIBRARY:
			sender = new Sender(id, "Biblioteca");
			break;

		case PRO_RECTORY:
			sender = new Sender(id, "Pr� Reitorias");
			break;

		case RECTORY:
			sender = new Sender(id, "Reitoria");
			break;

		case DOCENTE_DISCIPLINA:
			sender = new Sender(id, "Docente da disciplina");
			break;
			
		case GENERAL:
			sender = new Sender(id, "Mensagens p�blicas");
			break;

		default:
			break;
		}

		return sender;
	}
}