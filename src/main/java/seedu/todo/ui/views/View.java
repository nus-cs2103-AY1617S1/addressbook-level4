package seedu.todo.ui.views;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

public abstract class View extends UiPart {
	
    private AnchorPane placeHolderPane;
    private VBox mainNode;
	
	public void render(Stage primaryStage, AnchorPane placeholder) {
		// Load FXML.
		UiPartLoader.loadUiPart(primaryStage, placeholder, this);
		
		// The execution is then passed onto setNode, which will replace the placeholder contents.
	}

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    @Override
    public void setNode(Node node) {
        mainNode = (VBox) node;
		
		// Replace placeholder children with node.
        placeHolderPane.getChildren().setAll(mainNode);
    }
	
}
