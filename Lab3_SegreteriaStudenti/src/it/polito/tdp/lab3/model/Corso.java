package it.polito.tdp.lab3.model;

import java.util.HashSet;
import java.util.Set;

public class Corso {
	
	private String codiceCorso;
	private Set <Studente> studentiIscritti;
	
	public Corso(String codiceCorso, Set<Studente> studentiIscritti) {
		super();
		this.codiceCorso = codiceCorso;
		studentiIscritti = new HashSet <Studente>();
	}

	public String getCodiceCorso() {
		return codiceCorso;
	}

	public void setCodiceCorso(String codiceCorso) {
		this.codiceCorso = codiceCorso;
	}

	public Set<Studente> getStudentiIscritti() {
		return studentiIscritti;
	}

	public void setStudentiIscritti(Set<Studente> studentiIscritti) {
		this.studentiIscritti = studentiIscritti;
	}
	
	
	
	

}
