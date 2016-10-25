package seedu.ggist.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.events.ui.ChangeListingEvent;
import seedu.ggist.commons.util.FxViewUtil;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

public class ListingHeader extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(ListingHeader.class);
    
    private static final String FXML = "ListingHeader.fxml";
    

    private AnchorPane mainPane;
    
    private AnchorPane placeHolder;
    @FXML
    private AnchorPane listHeader;
    @FXML
    private Label listing;
    
    private String lastListing;
    
    public ListingHeader() {
        super();
    }
    
    public static ListingHeader load(Stage stage, AnchorPane placeHolder, String listing) {
        ListingHeader listingHeader = UiPartLoader.loadUiPart(stage, placeHolder, new ListingHeader());
        listingHeader.configure(listing);
        return listingHeader;
    }

    public void configure(String listing) {
        addMainPane();
        addListing();
        setListing(listing);
        registerAsAnEventHandler(this);
    }
    
    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }
    
    public void setListing(String listing) {
        this.listing.setText(listing);
    }
   
    private void addListing() {
        FxViewUtil.applyAnchorBoundaryParameters(listHeader, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(this.listing);
    }


    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane)node;
    }

    
    @Subscribe
    public void handleChangeListingEvent(ChangeListingEvent abce) {
        this.lastListing = abce.listing;
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last listing status to " + lastListing));
        setListing(lastListing);
    }


}