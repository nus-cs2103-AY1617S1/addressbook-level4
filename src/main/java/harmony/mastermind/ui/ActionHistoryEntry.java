package harmony.mastermind.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

//@@author A0138862W
public class ActionHistoryEntry extends UiPart{

    private static final String FXML = "ActionHistoryItem.fxml";
    
    @FXML
    private HBox actionHistoryItem;
    
    @FXML
    private Label title;
    
    @FXML
    private Label date;
    
    @FXML
    private Label description;
    
    public void setTitle(String title){
        this.title.setText(title);
    }
    
    public void setDate(String date){
        this.date.setText(date);
    }
    
    public void setDescription(String description){
        this.description.setText(description);
    }
    
    public Node getNode(){
        return actionHistoryItem;
    }
    
    public void setTypeFail(){
        this.title.setStyle("-fx-text-fill: crimson;");
        this.date.setStyle("-fx-text-fill: crimson;");
    }
    
    public void setTypeSuccess(){
        this.title.setStyle("-fx-text-fill: deepSkyBlue;");
        this.date.setStyle("-fx-text-fill: deepSkyBlue;");
    }
    
    @Override
    public void setNode(Node node) {
        actionHistoryItem = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
      
    

}

//@@author A0138862W
class ActionHistory {
    private final String title;
    private final String description;
    private final Date dateActioned;
    
    public ActionHistory(String title, String description){
        this.title = title;
        this.description = description;
        this.dateActioned = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateActioned() {
        return dateActioned;
    }
}
