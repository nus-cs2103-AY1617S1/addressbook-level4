package seedu.todo.ui.views;

import java.util.function.Function;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

public abstract class View extends UiPart {
    protected Pane placeHolderPane;
    protected VBox mainNode;
    
    private Function<View, View> setPropsCallback;
    
    public void passInProps(Function<View, View> setPropsCallback) {
    	this.setPropsCallback = setPropsCallback;
    }
	
    /**
     * This method renders the View as specified in the FXML file, in the placeholder pane.
     * After loading the FXML file, the execution is then passed onto setNode, 
     * which will replace the placeholder contents accordingly.
     * 
     * @param primaryStage   The primary stage that contains the main application window.
     * @param placeholder    The placeholder pane where this View should reside.
     */
	public View render(Stage primaryStage, Pane placeholder) {
		// Load FXML.
		if (setPropsCallback != null)
			return UiPartLoader.loadUiPart(primaryStage, placeholder, this, setPropsCallback);
		else
			return UiPartLoader.loadUiPart(primaryStage, placeholder, this);
	}
	
	// Overloaded methods for render.
	public View render(Stage primaryStage) {
		return render(primaryStage, null);
	}
	
	public Node getNode() {
		return mainNode;
	}
	
	// Lifecycle hooks
	public void componentDidMount() {
		// To be overwritten by View or Component definitions.
	}

    @Override
    public void setPlaceholder(Pane pane) {
        this.placeHolderPane = pane;
    }

    @Override
    public void setNode(Node node) {
        mainNode = (VBox) node;
        
        if (placeHolderPane != null) {
    		// Replace placeholder children with node.
            placeHolderPane.getChildren().setAll(mainNode);
        }
		
		// Callback once view is loaded.
		componentDidMount();
    }
	
}
