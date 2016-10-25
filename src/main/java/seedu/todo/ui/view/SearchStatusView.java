package seedu.todo.ui.view;

import com.google.common.base.Joiner;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.atteo.evo.inflector.English;
import seedu.todo.model.property.SearchStatus;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.UiPartLoaderUtil;

//@author A0135817B
public class SearchStatusView extends UiPart {
    private static final String FXML = "SearchStatusView.fxml";
    private static final String TASK_FOUND_FORMAT = "%d %s found";
    
    @FXML private Text searchCount;
    @FXML private Text searchTerm;
    
    private StackPane node;
    
    public static SearchStatusView load(Stage stage, AnchorPane placeholder, ObservableValue<SearchStatus> searchStatus) {
        SearchStatusView view = UiPartLoaderUtil.loadUiPart(stage, placeholder, new SearchStatusView());
        view.configureLayOut();
        view.bindListeners(searchStatus);
        return view;
    }
    
    private void configureLayOut() {
        FxViewUtil.applyAnchorBoundaryParameters(node, 0.0, 0.0, 0.0, 0.0);
    }
    
    private void bindListeners(ObservableValue<SearchStatus> searchStatus) {
        searchStatus.addListener((observable, oldValue, newValue) -> updateStatus(newValue));
        updateStatus(searchStatus.getValue());
    }
    
    private void updateStatus(SearchStatus status) {
        if (status == null) {
            FxViewUtil.setCollapsed(node, true);
        } else {
            FxViewUtil.setCollapsed(node, false);
            updateSearchTerm(status);
        }
    }
    
    private void updateSearchTerm(SearchStatus status) {
        String terms = Joiner.on(", ").join(status.terms);
        searchTerm.setText(terms);
        
        String taskFound = String.format(TASK_FOUND_FORMAT, status.tasksFound, English.plural("task", status.tasksFound));
        searchCount.setText(taskFound);
    }
    
    @Override
    public void setNode(Node node) {
        this.node = (StackPane) node;
    }

    @Override
    public String getFxmlPath() {
        return SearchStatusView.FXML;
    }
}
