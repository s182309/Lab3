package it.polito.tdp.lab3.model;

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
		return dao.caricaCorsi();
	}
	
	public static void main (String[] args) {
		SegreteriaStudentiModel seg = new SegreteriaStudentiModel();
		System.out.println(seg.cerca("146101").getNome()+" "+seg.cerca("146101").getCognome()) ;
		System.out.println("\n"+seg.caricaCorsi());
	}

}
