package seedu.todo.ui.view;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.model.property.SearchStatus;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.UiPartLoaderUtil;

//@author A0135817B
public class SearchResultView extends UiPart {
    private static final String FXML = "SearchResultView.fxml";
    private static final String STATUS_FORMAT = "";
    
    @FXML private Text searchTerm;
    
    private HBox node;
    
    public static SearchResultView load(Stage stage, AnchorPane placeholder, ObservableValue<SearchStatus> searchStatus) {
        SearchResultView view = UiPartLoaderUtil.loadUiPart(stage, placeholder, new SearchResultView());
        view.bindListeners(searchStatus);
        return view;
    }
    
    private void bindListeners(ObservableValue<SearchStatus> searchStatus) {
        searchStatus.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                FxViewUtil.setCollapsed(node, true);
            } else {
                FxViewUtil.setCollapsed(node, false);
                updateSearchTerm(newValue);
            }
        });
    }
    
    private void updateSearchTerm(SearchStatus status) {
        
        searchTerm.setText("");
    }
    
    @Override
    public void setNode(Node node) {
        this.node = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return SearchResultView.FXML;
    }
}
