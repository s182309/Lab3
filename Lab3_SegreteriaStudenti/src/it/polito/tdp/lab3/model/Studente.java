package it.polito.tdp.lab3.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Studente {
	
	private String matricola;
	private String nome;
	private String cognome;
	private String cds;
	
	public Studente(String matricola, String nome, String cognome , String cds) {
		super();
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.cds = cds;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String toString() {
		return matricola+"  "+nome+"  "+cognome+"  "+cds;
	}
	
	
	public String getCds() {
		return cds;
	}


	static class ComparatoreStudenti implements Comparator<Studente> {

		public int compare(Studente s1, Studente s2) {
			return s1.getMatricola().compareTo(s2.getMatricola());
		}
	}


	
}
