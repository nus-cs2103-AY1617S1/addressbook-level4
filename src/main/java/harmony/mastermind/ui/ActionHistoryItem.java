package harmony.mastermind.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class ActionHistoryItem extends UiPart{

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
