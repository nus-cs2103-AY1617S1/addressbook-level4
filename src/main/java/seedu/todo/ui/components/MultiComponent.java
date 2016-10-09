package seedu.todo.ui.components;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class MultiComponent extends Component {

	@Override
    public void setNode(Node node) {
        mainNode = (VBox) node;
        
        if (placeHolderPane != null) {
    		// Replace placeholder children with node.
            placeHolderPane.getChildren().add(mainNode);
        }
		
		// Callback once view is loaded.
		componentDidMount();
    }

}
