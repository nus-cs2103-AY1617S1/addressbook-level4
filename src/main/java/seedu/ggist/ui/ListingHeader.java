package seedu.ggist.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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

import java.util.Date;
import java.util.logging.Logger;

public class ListingHeader extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(ListingHeader.class);
    
    private static final String FXML = "ListingHeader.fxml";
    
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
        setListing(listing);
        registerAsAnEventHandler(this);
    }
    
    public void setListing(String listing) {
        lastListing = listing;
    }
    
    @FXML
    public void initialize() {
        listing.setText(lastListing);
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setNode(Node node) {
        listing = (Label)node;
    }
    
    @Subscribe
    public void handleChangeListingEvent(ChangeListingEvent abce) {
        String lastListing = abce.toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last listing status to " + lastListing));
        this.lastListing = lastListing;
    }


}
