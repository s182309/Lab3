package it.polito.tdp.lab3.model;

import java.util.HashSet;
import java.util.Set;

public class Corso {
	
	private String codiceCorso;
	private String crediti;
	private String nomeCorso;
	private String pd;
	

	public Corso(String codiceCorso, String crediti, String nomeCorso, String pd) {
		super();
		this.codiceCorso = codiceCorso;
		this.crediti = crediti;
		this.nomeCorso = nomeCorso;
		this.pd = pd;
		
	}

	public String getCodiceCorso() {
		return codiceCorso;
	}

	public void setCodiceCorso(String codiceCorso) {
		this.codiceCorso = codiceCorso;
	}


	public String getCrediti() {
		return crediti;
	}

	public void setCrediti(String crediti) {
		this.crediti = crediti;
	}

	public String getNomeCorso() {
		return nomeCorso;
	}

	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	@Override
	public String toString() {
		return nomeCorso;
	}
	
	public String stampaCompleta(){
		return codiceCorso+" "+crediti+" "+nomeCorso+" pd:"+pd;
	}
	
	

}
