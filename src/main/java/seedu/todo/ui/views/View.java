package seedu.todo.ui.views;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.ui.components.Component;

public abstract class View extends Component {
    
    public static View load(Stage primaryStage, Pane placeholder) {
        return null;
    }
    
}
