package it.polito.tdp.lab3.model;

import it.polito.tdp.lab3.DAO.SegreteriaStudentiDAO;

public class SegreteriaStudentiModel {
	
	public Studente cerca(String numeroMatricola){
		SegreteriaStudentiDAO dao = new SegreteriaStudentiDAO();
		return dao.cerca(numeroMatricola);
	    
	}
	
	public static void main (String[] args) {
		SegreteriaStudentiModel seg = new SegreteriaStudentiModel();
		System.out.println(seg.cerca("146101").getNome()+" "+seg.cerca("146101").getCognome()) ;
	}

}
