package electioncharts;

import electionresults.model.ElectionResults;
import electionresults.model.Party;
import electionresults.model.PartyResults;
import electionresults.model.PollData;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author mario
 */
public class MainController implements Initializable {

    @FXML
    private ChoiceBox<Integer> yearChoice;

    @FXML
    private TextArea provinceInfo;

    @FXML
    private ChoiceBox<String> regionChoice;

    @FXML
    private ChoiceBox<String> provinceChoice;

    @FXML
    private TextArea regionInfo;

    @FXML
    private ListView<String> regions;

    @FXML
    private ListView<String> parties;

    @FXML
    private VBox mainParties;

    @FXML
    private Label partyFoundLabel;

    @FXML
    private TextField partySearchField;

    @FXML
    private TextArea cvInfo;

    private ElectionResults electionResults;
    

    private void showElectionResultsInfo(int year) {
        // Tab getting basic data
        electionResults = DataAccessLayer.getElectionResults(year);
        cvInfo.appendText(regionResultsToString(electionResults.getGlobalResults()));
        regionChoice.getItems().clear();
        provinceChoice.getItems().clear();
        regionChoice.getItems().addAll(electionResults.getRegionProvinces().keySet());
        provinceChoice.getItems().addAll(electionResults.getProvinces().keySet());
        regionChoice.setDisable(false);
        provinceChoice.setDisable(false);
        // Tab other data
        regions.getItems().clear();
        parties.getItems().clear();
        for (Map.Entry<String, String> entry : electionResults.getRegionProvinces().entrySet()) {
            regions.getItems().add(entry.getKey() + " (" + entry.getValue() + ")");
        }
        for (Map.Entry<String, String> entry : electionResults.getPartyNames().entrySet()) {
            parties.getItems().add(entry.getKey() + ": " + entry.getValue());
        }

    }

    private void showProvinceResults(String province) {
        if (province != null) {
            provinceInfo.setText(regionResultsToString(electionResults.getProvinceResults(province)));
        } else {
            provinceInfo.clear();
        }
    }

    private void showRegionResults(String region) {
        if (region != null) {
            regionInfo.setText(regionResultsToString(electionResults.getRegionResults(region)));
        } else {
            regionInfo.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yearChoice.getItems().addAll(1995, 1999, 2003, 2007, 2011, 2015);
        yearChoice.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showElectionResultsInfo(newValue));
        provinceChoice.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProvinceResults(newValue)
        );
        regionChoice.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRegionResults(newValue)
        );
        for (Party party : Party.values()) {
            Label name = new Label(party.getName());
            name.setPrefWidth(100);
            ImageView logo = new ImageView(party.getLogo());
            logo.setFitWidth(35);
            logo.setFitHeight(35);
            Region region = new Region();
            region.setPrefSize(35, 35);
            region.setBackground(new Background(new BackgroundFill(Paint.valueOf(party.getColor().toString()), null, null)));
            Label acronyms = new Label("Acronyms = " + party.getAcronyms().toString());
            HBox hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(name, logo, region, acronyms);
            mainParties.getChildren().add(hBox);
        }
        yearChoice.getSelectionModel().selectFirst();

        partySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            Party party = Party.getPartyByName(newValue);
            if (party != null) {
                partyFoundLabel.setText("Party found: " + party.getName());
            } else {
                partyFoundLabel.setText("No party found using that acronym!");
            }
        });
    }

    private String regionResultsToString(RegionResults regionResults) {
        StringBuilder sb = new StringBuilder();
        sb.append(regionResults.getRegion()).append('\n');
        sb.append(pollDataToString(regionResults.getPollData()));
        for (PartyResults results : regionResults.getPartyResultsSorted()) {
            sb.append("   ").append(results.getParty()).append(": ").append(results.getVotes());
            sb.append('(').append(String.format("%.2f", results.getPercentage())).append(" %)").append('\n');
        }
        return sb.toString();
    }

    private String pollDataToString(PollData pollData) {
        StringBuilder sb = new StringBuilder();
        sb.append("Censo electoral: ").append(pollData.getCensus()).append('\n');
        sb.append("Votos emitidos: ").append(pollData.getVotes()).append('\n');
        sb.append("Abstenciones: ").append(pollData.getAbstentions()).append('\n');
        sb.append("Votos nulos: ").append(pollData.getNullVotes()).append('\n');
        sb.append("Votos validos: ").append(pollData.getValidVotes()).append('\n');
        sb.append("Votos en blanco: ").append(pollData.getBlankVotes()).append('\n');
        sb.append("Votos con candidato: ").append(pollData.getCandidateVotes()).append('\n');
        sb.append("Reparto de votos:\n");
        return sb.toString();
    }
}
