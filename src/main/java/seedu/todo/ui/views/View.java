package seedu.todo.ui.views;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

public abstract class View extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
	
    private AnchorPane placeHolderPane;
    private VBox mainNode;
	
    /**
     * This method renders the View as specified in the FXML file, in the placeholder pane.
     * After loading the FXML file, the execution is then passed onto setNode, 
     * which will replace the placeholder contents accordingly.
     * 
     * @param primaryStage   The primary stage that contains the main application window.
     * @param placeholder    The placeholder pane where this View should reside.
     */
	public void render(Stage primaryStage, AnchorPane placeholder) {
		// Load FXML.
		UiPartLoader.loadUiPart(primaryStage, placeholder, this);
	}
	
	// Overloaded method for render.
	public void render(Stage primaryStage) {
		render(primaryStage, null);
	}
	
	public void onViewMount() {
		// To be overwritten by View or Component definitions.
	}

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    @Override
    public void setNode(Node node) {
        mainNode = (VBox) node;
        
        if (placeHolderPane == null) {
        	logger.severe("View cannot setNode: placeHolderPane is null.");
        	return;
        }
        
        assert placeHolderPane != null;
		
		// Replace placeholder children with node.
        placeHolderPane.getChildren().setAll(mainNode);
		
		// Callback once view is loaded.
		onViewMount();
    }
	
}
