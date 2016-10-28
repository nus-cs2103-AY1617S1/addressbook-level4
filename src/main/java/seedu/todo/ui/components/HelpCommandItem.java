package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @@author A0139812A
 */
public class HelpCommandItem extends MultiComponent {

    private static final String FXML_PATH = "components/HelpCommandItem.fxml";
    
    // Props
    public String commandName;
    public String commandDescription;
    public String commandSyntax;
    
    // FXML
    @FXML
    private Text commandNameText;
    @FXML
    private Text commandDescriptionText;
    @FXML
    private Text commandSyntaxText;

    
    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }
    
    @Override
    public void componentDidMount() {
        commandNameText.setText(commandName);
        commandDescriptionText.setText(commandDescription);
        commandSyntaxText.setText(commandSyntax);
    }

}
