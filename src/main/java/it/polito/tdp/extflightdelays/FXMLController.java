package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput; //in realta' queste sono le miglia disponibili

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	double distanzaInserita=0.0;
    	try {
    		distanzaInserita=Double.parseDouble(distanzaMinima.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("DEVI INSERIRE UN NUMERO!");
    		return;
    	}
    	
    	txtResult.appendText(model.creaGrafo(distanzaInserita));
    	
    	cmbBoxAeroportoPartenza.getItems().clear();
    	cmbBoxAeroportoPartenza.getItems().addAll(model.estrarreVetici());
    	
    	
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	txtResult.clear();
    	Airport selezionato=cmbBoxAeroportoPartenza.getValue();
    	if(selezionato==null) {
    		txtResult.appendText("DEVI SELEZIONARE UN AEREOPORTO");
    	}
    	
    	txtResult.appendText(model.estrarreAdiacenti(selezionato));

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	txtResult.clear();
    	Airport selezionato=cmbBoxAeroportoPartenza.getValue();
    	if(selezionato==null) {
    		txtResult.appendText("DEVI SELEZIONARE UN AEREOPORTO");
    	}
    	
    	double distanzaMassimaInserita=0.0;
    	try {
    		distanzaMassimaInserita=Double.parseDouble(numeroVoliTxtInput.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("DEVI INSERIRE UN NUMERO!");
    		return;
    	}
    	
    	txtResult.appendText(model.itinerario(selezionato, distanzaMassimaInserita));
    	
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
