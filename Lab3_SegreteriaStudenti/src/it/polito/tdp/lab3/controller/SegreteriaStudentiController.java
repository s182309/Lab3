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
    
    public void setModel(SegreteriaStudentiModel model){
    	this.model = model ;
    	 box.getItems().addAll(model.caricaCorsi());
    }

    @FXML
    void doCerca(ActionEvent event) {

    }

    @FXML
    void doComplete(Event event) {
    	String text = txtInput.getText();
    	for(int i=0; i<text.length(); i++){
    		if(Character.isDigit(text.charAt(i))==false){
    			txtNome.clear();
    	    	txtCognome.clear();
    			txtOutput.setText("Impossbile inserire caratteri diversi da numeri nella matricola!");
    			return;
    		}
    	}
    	Studente st = model.cerca(text);
    	
    	if(st==null){
    		txtOutput.setText("Studente non presente");
    		txtNome.clear();
	    	txtCognome.clear();
    		return;
    	}
    	else{
    		txtOutput.setText("");	
    	txtNome.setText(st.getNome());
    	txtCognome.setText(st.getCognome());
    	}
    	

    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtInput.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtOutput.clear();
    	box.setValue(null);

    }

    @FXML
    void initialize() {
        assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
     }
}

