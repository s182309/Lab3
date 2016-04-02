package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.sun.javafx.collections.MappingChange.Map;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class SegreteriaStudentiDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root&password=root";
	
	public Studente cerca(String numeroMatricola){
		
		try {
			Studente temp;
			Connection conn = DriverManager.getConnection(jdbcURL);
			Statement st = conn.createStatement();
			String sql = "select matricola, nome, cognome , CDS from studente where matricola='"+numeroMatricola+"'";
			ResultSet res = st.executeQuery(sql);
			if(res.next())
			{
				temp = new Studente(res.getString("matricola"), res.getString("nome") , res.getString("cognome") , res.getString("CDS"));
				res.close();
				conn.close();
				return temp;
			}
			else
			{
				res.close();
				conn.close();
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<Corso> caricaCorsi(){
		List <Corso> listaCorsi = new LinkedList <Corso>();
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			Statement st = conn.createStatement();
			String sql= "select codins , crediti , nome , pd from corso";
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				Corso temp = new Corso (res.getString("codins") , res.getString("crediti") , res.getString("nome") , res.getString("pd") );
				listaCorsi.add(temp);
			}
			res.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaCorsi;
	}
	
	
	public List<Studente> cercaCorso(Corso c){
		List<String> studenti = new LinkedList <String>();
		List<Studente> result = new LinkedList <Studente>();
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			Statement st = conn.createStatement();
			//prima query per avere elenco studenti
			String sql = "select matricola from iscrizione where codins='"+c.getCodiceCorso()+"'" ;
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				studenti.add(res.getString("matricola"));
			}
			
			for(String s : studenti){
				String query = "select matricola, nome, cognome , CDS from studente where matricola='"+s+"'";
				res = st.executeQuery(query);
				while(res.next())
				{
				Studente	temp = new Studente(res.getString("matricola"), res.getString("nome") , res.getString("cognome") , res.getString("CDS"));
                result.add(temp);
				}
				res.close();
			}
			conn.close();
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	} 
	
	public List<Corso> cercaStudente(Studente s){
		List<String> corsi = new LinkedList <String>();
		List<Corso> result = new LinkedList <Corso>();
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			Statement st = conn.createStatement();
			//prima query per avere elenco studenti
			String sql = "select codins from iscrizione where matricola='"+s.getMatricola()+"'" ;
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				corsi.add(res.getString("codins"));
			}
			
			for(String cod : corsi){
				String query = "select codins, crediti, nome , pd from corso where codins='"+cod+"'";
				res = st.executeQuery(query);
				while(res.next())
				{
			    Corso temp = new Corso(res.getString("codins"), res.getString("crediti") , res.getString("nome") , res.getString("pd"));
                result.add(temp);
				}
				res.close();
			}
			conn.close();
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

	
	
	
	
	
	
	
	
	public static void main (String[] args) {
		SegreteriaStudentiDAO seg = new SegreteriaStudentiDAO();
		System.out.println(seg.cerca("146101").getNome()+" "+seg.cerca("146101").getCognome()) ;
		
	}
	
}
