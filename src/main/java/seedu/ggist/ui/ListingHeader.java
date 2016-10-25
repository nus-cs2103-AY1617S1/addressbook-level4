package seedu.ggist.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.events.ui.ChangeListingEvent;
import seedu.ggist.commons.util.FxViewUtil;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class ListingHeader extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(ListingHeader.class);
    
    private static final String FXML = "ListingHeader.fxml";
    

    private VBox mainPane;
    
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
        if (listing.equals("all")) {
            this.listing.setText("ALL TASKS");
        } else if (listing.equals("done")) {
            this.listing.setText("ALL COMPLETED TASKS");
        } else if (listing.equals("")) {
            this.listing.setText("TASKS NOT COMPLETED");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMMM yyyy");
            this.listing.setText(sdf.format(new PrettyTimeParser().parse(listing).get(0)).toString());
        }
       
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
        mainPane = (VBox)node;
    }

    
    @Subscribe
    public void handleChangeListingEvent(ChangeListingEvent abce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last listing status to " + lastListing));
        setListing(abce.listing);
    }


}