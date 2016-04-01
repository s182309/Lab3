package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.polito.tdp.lab3.model.Studente;

public class SegreteriaStudentiDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root&password=root";
	
	public Studente cerca(String numeroMatricola){
		
		try {
			Studente temp;
			Connection conn = DriverManager.getConnection(jdbcURL);
			Statement st = conn.createStatement();
			String sql = "select matricola, nome, cognome from studente where matricola='"+numeroMatricola+"'";
			ResultSet res = st.executeQuery(sql);
			if(res.next())
			{
				temp = new Studente(res.getString("matricola"), res.getString("nome") , res.getString("cognome"));
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

	public static void main (String[] args) {
		SegreteriaStudentiDAO seg = new SegreteriaStudentiDAO();
		System.out.println(seg.cerca("146101").getNome()+" "+seg.cerca("146101").getCognome()) ;
	}
	
}
