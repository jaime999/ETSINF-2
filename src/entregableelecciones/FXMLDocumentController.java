/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableelecciones;

import electionresults.model.ElectionResults;
//import electionresults.model.Party;
import electionresults.model.PartyResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.net.URL;
import java.text.DecimalFormat;
//import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.concurrent.Task;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Jaime Martínez Sánchez
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private PieChart chart;
    
    @FXML
    private ChoiceBox<Integer> choiceAño;
    
    @FXML
    private ChoiceBox<String> choiceProvincia;
    
    @FXML
    private ChoiceBox<String> choiceRegion;
    
    private ElectionResults resultados;
    
    private RegionResults regionResults;
    
    private ObservableList pieChartData;
            
    @FXML
    private Slider slider;
    
    @FXML
    private Button cAutonoma;
    
    @FXML
    private Label label;
    @FXML
    private ProgressBar bar;
    @FXML
    private BarChart<String, Number> barChart1;
    @FXML
    private BarChart<String, Number> barChart2;
    @FXML
    private StackedBarChart<String, Number> stackedBar;
    
    
        
    public void resultadosElecciones(int year) {
        choiceProvincia.getItems().clear();
        choiceRegion.getItems().clear();
        choiceRegion.getItems().addAll(resultados.getRegionProvinces().keySet());
        choiceProvincia.getItems().addAll(resultados.getProvinces().keySet());
    
    
    
    }
    public void resultadosEleccionesLanzadera(int year) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                bar.setVisible(true);
                resultados = DataAccessLayer.getElectionResults(year);
                bar.setVisible(false);
                
               
                return null;
                
        }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
    resultadosElecciones(year);
    slider.disableProperty().bind(task.runningProperty());
    choiceAño.disableProperty().bind(task.runningProperty());
    choiceRegion.disableProperty().bind(task.runningProperty());
    choiceProvincia.disableProperty().bind(task.runningProperty());
    cAutonoma.disableProperty().bind(task.runningProperty());
    bar.progressProperty().bind(task.progressProperty());


}
    public void resultadosEleccionesProvincia(String provincia) {
        if(provincia != null) {
            choiceRegion.getSelectionModel().clearSelection();
            barChart1.getData().clear();
            ObservableList<XYChart.Data<String, Number>> barData = FXCollections.observableArrayList();
            pieChartData = FXCollections.observableArrayList();
            regionResults = resultados.getProvinceResults(provincia);
            for(PartyResults partyResults : regionResults.getPartyResultsSorted()) {
                if(partyResults.getPercentage() >= (int)slider.getValue()) {
                    barData = FXCollections.observableArrayList();
                    barData.add(new XYChart.Data(String.valueOf(resultados.getYear()), partyResults.getVotes()));
                    Series <String, Number> data = new Series<>(barData);
                    data.setName(partyResults.getParty());
                    pieChartData.add(new PieChart.Data(partyResults.getParty() + " (" + partyResults.getSeats() + ")", partyResults.getSeats()));
                    chart.setData(pieChartData);
                    barChart1.getData().add(data);
                 }
            }
            chart.setTitle("Distribución de escaños para " + provincia);
            barChart1.setTitle("Votos en " + provincia);
        }
    }
    
   
    
    
    
    public void resultadosEleccionesRegion(String region) {
       
            choiceProvincia.getSelectionModel().clearSelection();
            barChart1.getData().clear();
            chart.getData().clear();
            ObservableList<XYChart.Data<String, Number>> barData = FXCollections.observableArrayList();
            regionResults = resultados.getRegionResults(region);
            for(PartyResults partyResults : regionResults.getPartyResultsSorted()) {
                if(partyResults.getPercentage() >= (int)slider.getValue()) {
                     if(region != null) {
                    barData = FXCollections.observableArrayList();
                    barData.add(new XYChart.Data(String.valueOf(resultados.getYear()), partyResults.getVotes()));
                    Series<String, Number> data = new Series<>(barData);
                     data.setName(partyResults.getParty());
                    barChart1.getData().add(data);
                }
            }
            barChart1.setTitle("Votos en " + region);
            chart.setTitle("Las comarcas no son circunscripciones electorales, \npor lo tanto, \nno les corresponde ningún escaño");

        }
    }
    
    
  
      @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
                
     stackedBar.setTitle("Escaños");
      ObservableList<XYChart.Data<String, Number>> stackData = FXCollections.observableArrayList();
        barChart2.setTitle("Participación (%)");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        series1.setName("Comunitat Valenciana");
        series2.setName("Valencia");
        series3.setName("Alicante");
        series4.setName("Castellón");
        for(int i = 1995; i <= 2015; i+= 4) {
            resultados = DataAccessLayer.getElectionResults(i);
            double votos = resultados.getGlobalResults().getPollData().getVotes();
            double censo = resultados.getGlobalResults().getPollData().getCensus();
            double valencia = (resultados.getProvinceResults("Valencia").getPollData().getVotes() * 100)
                    / (resultados.getProvinceResults("Valencia").getPollData().getCensus());
            double alicante = (resultados.getProvinceResults("Alicante").getPollData().getVotes() * 100)
                    / (resultados.getProvinceResults("Alicante").getPollData().getCensus());
            double castellon = (resultados.getProvinceResults("Castellón").getPollData().getVotes() * 100)
                    / (resultados.getProvinceResults("Castellón").getPollData().getCensus());
            series1.getData().add(new XYChart.Data(String.valueOf(i), (votos * 100) / censo));
            series2.getData().add(new XYChart.Data(String.valueOf(i), valencia));
            series3.getData().add(new XYChart.Data(String.valueOf(i), alicante));
            series4.getData().add(new XYChart.Data(String.valueOf(i), castellon));
             regionResults = resultados.getGlobalResults();
        for(PartyResults partyResults : regionResults.getPartyResultsSorted()) {
            if(partyResults.getPercentage() > 5) {
                stackData = FXCollections.observableArrayList();
                stackData.add(new XYChart.Data(String.valueOf(resultados.getYear()), partyResults.getSeats()));      
                Series<String, Number> data = new Series<>(stackData);
                    data.setName(partyResults.getParty());
                
                
                stackedBar.getData().add(data);
            
    }
    }
        }
       barChart2.getData().addAll(series1, series2, series3, series4);
       DecimalFormat df = new DecimalFormat("#.00");
       slider.setBlockIncrement(0.5);
       slider.setValue(0);
       slider.setMax(5);
       slider.valueProperty().addListener((observable, oldVal, newVal) ->
       { label.setText(newVal + "");
       label.setText(df.format(newVal));});
       choiceAño.getItems().clear();
       choiceAño.getItems().addAll(1995, 1999, 2003, 2007, 2011, 2015);
       choiceAño.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> resultadosEleccionesLanzadera(newValue));
       choiceProvincia.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> resultadosEleccionesProvincia(newValue)
        );
       choiceRegion.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> resultadosEleccionesRegion(newValue)
        );
       choiceAño.getSelectionModel().selectFirst();
       
       

    } 
    
    
    @FXML
    private void eleccionesCValenciana(ActionEvent event) {
        barChart1.getData().clear();
        pieChartData = FXCollections.observableArrayList();  
        ObservableList<XYChart.Data<String, Number>> barData = FXCollections.observableArrayList();
        regionResults = resultados.getGlobalResults();
        for(PartyResults partyResults : regionResults.getPartyResultsSorted()) {
            if(partyResults.getPercentage() >= (int)slider.getValue()) {
                barData = FXCollections.observableArrayList();
                barData.add(new XYChart.Data(String.valueOf(resultados.getYear()), partyResults.getVotes()));      
                Series<String, Number> data = new Series<>(barData);
                data.setName(partyResults.getParty());
                barChart1.getData().add(data);
                pieChartData.add(new PieChart.Data(partyResults.getParty() + " (" + partyResults.getSeats() + ")", partyResults.getSeats()));
                chart.setData(pieChartData);
                 }
        }
        chart.setTitle("Distribución de escaños en la Comunidad Valenciana");
        barChart1.setTitle("Votos en la Comunidad Valenciana");
    }
}

        
   