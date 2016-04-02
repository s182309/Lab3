package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.Collection;
import java.util.List;
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
			txtNome.clear();
			txtCognome.clear();
			String txt = "";
			for (Studente s : model.cercaCorso(box.getValue())) {
				txt += s.toString() + "\n";
			}
			if (txt.length() == 0) {
				txtOutput.setText("Nessuno studente è iscritto a questo corso");
				return;
			}
			txtOutput.setText(txt);
			return;
		}

		// secondo caso: cerco i corsi a cui uno specifico studente è iscritto: input non vuoto...corso null o vuoto
		if ( box.getValue().getCodiceCorso().compareTo("") == 0 && txtInput.getText().length() != 0) {
			try {
				this.doComplete(event);
				String txt = "";
				for (Corso c : model.cercaStudente(model.cerca(txtInput.getText()))) {
					txt += c.stampaCompleta() + "\n";
				}
				if (txt.length() == 0) {
					txtOutput.setText("Lo studente non è iscritto ai corsi");
					return;
				}
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
				txtOutput.setText("");
				txtNome.setText(st.getNome());
				txtCognome.setText(st.getCognome());
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
					+box.getValue().getNomeCorso()+"\", lo studente è già iscritto a questo corso";
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
}
