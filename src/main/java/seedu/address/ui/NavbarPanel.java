package seedu.address.ui;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavbarPanel extends UiPart {
	// private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "NavbarPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    private final String NAVBAR_TASKS = "Tasks";
    private final String NAVBAR_DEADLINES = "Deadlines";
    private final String NAVBAR_INCOMING_DEADLINES = "Incoming Deadlines";
    private final String NAVBAR_FLOATING_TASKS = "Floating Tasks";
    
    private final ObservableList<String> navbarElement = FXCollections.observableArrayList(NAVBAR_TASKS, NAVBAR_DEADLINES,
			  																					 NAVBAR_INCOMING_DEADLINES, NAVBAR_FLOATING_TASKS);
    
    @FXML
    private ListView<String> navbarView;

    public NavbarPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public static NavbarPanel load(Stage primaryStage, AnchorPane navbarPlaceholder) {
    	NavbarPanel navbarPanel = UiPartLoader.loadUiPart(primaryStage, navbarPlaceholder, new NavbarPanel());
    	navbarPanel.configure();
    	return navbarPanel;
    }
    
    private void configure() {
    	setConnection();
    	addToPlaceHolder();
    }
    
    private void addToPlaceHolder() {
    	SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
    
    private void setConnection() {
    	navbarView.setItems(navbarElement);
    	navbarView.setCellFactory(listView -> new NavbarViewCell());
    	setEventHandlerForSelectionChangeEvent();
    }
    
    private void setEventHandlerForSelectionChangeEvent() {
        navbarView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                logger.fine("Selection in task navbar changed to : '" + newValue + "'");
//                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }
    
    class NavbarViewCell extends ListCell<String> {
    	
    	public NavbarViewCell() {
    	}
    	
    	@Override
        protected void updateItem(String li, boolean empty) {
            super.updateItem(li, empty);

            if (empty || li == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(NavbarCard.load(li).getLayout());
            }
        }
    }
}
