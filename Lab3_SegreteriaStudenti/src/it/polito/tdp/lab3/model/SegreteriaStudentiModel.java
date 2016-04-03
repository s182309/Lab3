package it.polito.tdp.lab3.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.lab3.DAO.SegreteriaStudentiDAO;
import javafx.collections.ObservableList;

public class SegreteriaStudentiModel {
	SegreteriaStudentiDAO dao;
	
	
	public SegreteriaStudentiModel() {
		super();
		this.dao = new SegreteriaStudentiDAO();
	}

	public Studente cerca(String numeroMatricola){
		return dao.cerca(numeroMatricola);
	   }
	
	public List <Corso> caricaCorsi(){
		List<Corso> temp = new LinkedList<Corso>(dao.caricaCorsi());
		Collections.sort(temp , new Corso.ComparatoreCorsi());
		return temp;
	}
	
	
	public List<Studente> cercaCorso (Corso c){
		return dao.cercaCorso(c);
	}
	
	public List<Corso> cercaStudente (Studente s){
		return dao.cercaStudente(s);
	}
	
	public boolean cercaIscrizione(Studente s , Corso c){
		return dao.cercaIscrizione(s, c);
	}
	
	public boolean iscrivi (Studente s , Corso c){
		return dao.iscrivi(s, c);
	}
	

	public static void main (String[] args) {
		SegreteriaStudentiModel seg = new SegreteriaStudentiModel();
		System.out.println(seg.cerca("146101").getNome()+" "+seg.cerca("146101").getCognome()) ;
		System.out.println("\n"+seg.caricaCorsi());
		
	}

}
