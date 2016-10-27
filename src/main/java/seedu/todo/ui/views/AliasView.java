package seedu.todo.ui.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seedu.todo.MainApp;
import seedu.todo.commons.core.AliasDefinition;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.components.AliasItem;

public class AliasView extends View {
    
    private static final String FXML_PATH = "views/AliasView.fxml";

    private static final String ICON_PATH = "/images/icon-settings.png";
    private static final String TEXT_INSTRUCTIONS = "Aliases make your life easier by allowing you to customize shortcuts!\n"
            + "To set an alias, use the following command:\n    alias <aliasKey> <aliasValue>";

    // FXML
    @FXML
    private Text aliasInstructionsText;
    @FXML
    private ImageView aliasImageView;
    @FXML
    private Pane aliasesPlaceholder;

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        // Makes the Component full width wrt parent container.
        FxViewUtil.makeFullWidth(this.mainNode);
        
        // Set instructions
        aliasInstructionsText.setText(TEXT_INSTRUCTIONS);

        // Load image
        aliasImageView.setImage(new Image(ICON_PATH));
        
        // Get definitions
        Map<String, String> aliasMap = MainApp.getConfig().getAliases();
        List<Map.Entry<String, String>> aliasDefinitions =
                new ArrayList<Map.Entry<String, String>>(aliasMap.entrySet());
        
        Comparator<Map.Entry<String, String>> aliasComparator = new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        };
        Collections.sort(aliasDefinitions, aliasComparator);

        // Clear items
        AliasItem.reset(aliasesPlaceholder);

        // Load items
        for (Map.Entry<String, String> aliasPair : aliasDefinitions) {
            AliasItem item = load(primaryStage, aliasesPlaceholder, AliasItem.class);
            item.aliasDefinition = new AliasDefinition(aliasPair.getKey(), aliasPair.getValue());
            item.render();
        }
    }
    
}
