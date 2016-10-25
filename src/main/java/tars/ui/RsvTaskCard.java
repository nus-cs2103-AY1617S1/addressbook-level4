package tars.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tars.model.task.rsv.RsvTask;

public class RsvTaskCard extends UiPart{

    private static final String FXML = "RsvTaskListCard.fxml";
    private static final String DATETIMELIST_ID = "dateTimeList";

    private TextArea dateTimeListArea;
    private AnchorPane dateTimeListPane;

    private StringProperty dateTimeListdisplayed = new SimpleStringProperty("");

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    private RsvTask rsvTask;
    private int displayedIndex;

    public RsvTaskCard(){

    }

    public static RsvTaskCard load(RsvTask rsvTask, int displayedIndex){
        RsvTaskCard card = new RsvTaskCard();
        card.cardPane = new HBox();
        card.dateTimeListPane = new AnchorPane();

        card.rsvTask = rsvTask;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(rsvTask.getName().taskName);
        id.setText(displayedIndex + ". ");
        setDateTimeList();
        configure();
    }

    public void configure() {
        dateTimeListArea = new TextArea();
        dateTimeListArea.setEditable(false);
        dateTimeListArea.setId(DATETIMELIST_ID);

        dateTimeListArea.getStyleClass().removeAll();
        dateTimeListArea.setWrapText(true);
        dateTimeListArea.setPrefSize(200, 75);
        dateTimeListArea.textProperty().bind(dateTimeListdisplayed);
        dateTimeListArea.autosize();
        
        dateTimeListPane.getChildren().add(dateTimeListArea);
        cardPane.getChildren().add(dateTimeListPane);
    }

    private void setDateTimeList() {
        String toSet = Formatter.formatDateTimeList(rsvTask);
        dateTimeListdisplayed.setValue(toSet);
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
        dateTimeListPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
