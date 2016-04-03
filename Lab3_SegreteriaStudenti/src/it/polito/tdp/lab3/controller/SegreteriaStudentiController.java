package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.SegreteriaStudentiModel;
import it.polito.tdp.lab3.model.Studente;
import javafx.event.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private SegreteriaStudentiModel model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Corso> box;

	@FXML
	private TextField txtInput;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	@FXML
	private TextArea txtOutput;

	Corso corsoVuoto;

	public void setModel(SegreteriaStudentiModel model) {
		this.model = model;
		corsoVuoto = new Corso("", "", "", "");
		box.getItems().add(corsoVuoto);
		box.getItems().addAll(model.caricaCorsi());
		box.setValue(corsoVuoto);
	}

	@FXML
	void doCerca(ActionEvent event) {
		
		// evito caratteri diversi da numeri
				if(this.isInputCorrect(txtInput.getText())==false){
					txtNome.clear();
					txtCognome.clear();
					txtOutput.setText("Impossbile inserire caratteri diversi da numeri nella matricola!");
					return;
				}

		// evito eccezioni di altri casi errati: studente vuoto e corso vuoto
		if (box.getValue().getCodiceCorso().length() == 0 && txtInput.getText().length() == 0) {
			txtNome.clear();
			txtCognome.clear();
			return;
		}

		// primo caso: cerco gli studenti iscritti ad un determinato corso (input vuoto..corso selezionato o non vuoto)
		if (txtInput.getText().length() == 0 && box.getValue() != corsoVuoto) {
			double start = System.nanoTime();
			txtNome.clear();
			txtCognome.clear();
			String txt = "";
			txt = this.stampaStudenti(box.getValue());
			txtOutput.setText(txt);
			double end = System.nanoTime();
			double time = (double) (end-start)/1e9 ;
			System.out.println(time);
			return;
		}

		// secondo caso: cerco i corsi a cui uno specifico studente è iscritto: input non vuoto...corso null o vuoto
		if ( box.getValue().getCodiceCorso().compareTo("") == 0 && txtInput.getText().length() != 0) {
			try {
				this.doComplete(event);
				String txt = "";
				txt = this.stampaCorsi(model.cerca(txtInput.getText()));
				txtOutput.setText(txt);
				return;
			} catch (Exception e) {
				return;
			}
		}

		// terzo caso: cerco se un determinato studente è iscritto ad uno
		// specifico corso : sia input che corso selezionato
		if (box.getValue().getCodiceCorso().length() != 0 && txtInput.getText().length() != 0) {
			String out = "";
			try {
				this.doComplete(event);
				if (model.cercaIscrizione(model.cerca(txtInput.getText()), box.getValue())) {
					out = "Lo studente " + txtNome.getText() + " " + txtCognome.getText() + " è iscritto al corso di \""
							+ box.getValue().getNomeCorso() + "\"";
					txtOutput.setText(out);
					return;
				} else {
					out = "Lo studente " + txtNome.getText() + " " + txtCognome.getText()
							+ " non è iscritto al corso di \"" + box.getValue().getNomeCorso() + "\"";
					txtOutput.setText(out);
					return;
				}

			} catch (Exception e) {
				return;
			}
		}

	}

	@FXML
	void doComplete(Event event) {
		String text = txtInput.getText();
		if (text.length() != 0) {

			if(this.isInputCorrect(text)==false){
				txtNome.clear();
				txtCognome.clear();
				txtOutput.setText("Impossbile inserire caratteri diversi da numeri nella matricola!");
				return;
			}

			Studente st = model.cerca(text);

			if (st == null) {
				txtOutput.setText("Studente non presente");
				txtNome.clear();
				txtCognome.clear();
				return;
			} else {
				String nome;
				String cognome;
				nome= String.valueOf(st.getNome().charAt(0)).toUpperCase().concat(st.getNome().substring(1).toLowerCase());
				cognome = String.valueOf(st.getCognome().charAt(0)).toUpperCase().concat(st.getCognome().substring(1).toLowerCase());
				String.valueOf(cognome.charAt(0)).toUpperCase();
				txtOutput.setText("");
				txtNome.setText(nome);
				txtCognome.setText(cognome);
			}

		}

	}

	@FXML
	void doIscrivi(ActionEvent event) {
		//controllo che matricola e corso siano presenti
		if(txtInput.getText().length()==0 || box.getValue().getCodiceCorso().compareTo("")==0){
			txtOutput.setText("Selezionare lo studente ed il corso a cui si vuole iscriverlo");
			return;
		}
		
		//controllo sulla correttezza input
		if(this.isInputCorrect(txtInput.getText())==false){
			txtNome.clear();
			txtCognome.clear();
			txtOutput.setText("Impossbile inserire caratteri diversi da numeri nella matricola!");
			return;
		}
		
		//controllo sulla presenza dello studente
		if(model.cerca(txtInput.getText())==null){
			txtOutput.setText("Studente non presente");
			return;
		}
		//prova iscrizione
		try{
		this.doComplete(event);
		String out="";
		if(model.iscrivi(model.cerca(txtInput.getText()), box.getValue())==true){
			out= "Iscrizione dello studente "+txtNome.getText()+" "+txtCognome.getText()+" al corso di \""
					+box.getValue().getNomeCorso()+"\" avvenuta con successo!";
			txtOutput.setText(out);
			return;
		}
		else{
			out= "Impossibile iscrivere lo studente "+txtNome.getText()+" "+txtCognome.getText()+" al corso di \""
					+box.getValue().getNomeCorso()+"\",\nlo studente è già iscritto al corso";
			txtOutput.setText(out);
			return;
		}
		}
		catch(Exception e){
			return;
		}
		
	}

	@FXML
	void doReset(ActionEvent event) {
		txtInput.clear();
		txtNome.clear();
		txtCognome.clear();
		txtOutput.clear();
		box.setValue(corsoVuoto);

	}

	@FXML
	void initialize() {
		assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
	
	}

	public boolean isInputCorrect(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public String stampaStudenti (Corso c){
		String result = "" ;
		int maxNome=0;
		int maxCognome=0;
		for(Studente s : model.cercaCorso(c)){
			
			if(s.getNome().length() > maxNome)
				maxNome = s.getNome().length();
			
			if(s.getCognome().length() > maxCognome)
				maxCognome = s.getCognome().length();
		}
		for(Studente s : model.cercaCorso(c)){
			int differenzaNome = maxNome - s.getNome().length() + 1;
			int differenzaCognome = maxCognome - s.getCognome().length() + 1;
			result += s.getMatricola()+"  "+s.getNome();
			for(int i=0 ; i < differenzaNome  ; i++)
				result += " ";
			result += s.getCognome();
			for(int i=0 ; i< differenzaCognome ; i++)
				result += " ";
			result += s.getCds()+"\n";
					
		}
		
		return result;
		
	}
	
	public String stampaCorsi(Studente s){
		String result="";
		int maxCod=0;
		int maxNome=0;
		int cifraCrediti =1;
		for(Corso c: model.cercaStudente(s)){
		if(c.getNomeCorso().length()>maxNome)
		maxNome = c.getNomeCorso().length();
		
		if(c.getCodiceCorso().length()>maxCod)
			maxCod = c.getCodiceCorso().length();
		if(Integer.parseInt(c.getCrediti())>9)
			cifraCrediti = 2;
		}
		
		for(Corso c : model.cercaStudente(s)){
			result += c.getCodiceCorso();
			for(int i=0; i< maxCod - c.getCodiceCorso().length() + 2 ; i++)
				result += " ";
			result += c.getCrediti();
			for(int i=0 ; i<cifraCrediti - c.getCrediti().length() +2; i++)
				result += " ";
			result += c.getNomeCorso();
			for(int i=0 ; i <maxNome - c.getNomeCorso().length() + 2 ; i++)
				result += " ";
			result += "pd:"+c.getPd()+"\n";
		}
		return result;
	}
}
