package seedu.dailyplanner.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.util.FxViewUtil;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL =
            "/view/HelpDoc.html";
    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }


    private void configure(){
           Scene scene = new Scene(mainPane);
           //Null passed as the parent stage to make it non-modal.
           dialogStage = createDialogStage(TITLE, null, scene);
           dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
           setIcon(dialogStage, ICON);
           
           WebView browser = new WebView();
         
			/*BufferedReader textFile;
			try {
				textFile = new BufferedReader(new FileReader("/images/HelpDoc.html"));
				try {
					String contentLine = textFile.readLine();
					System.out.println(contentLine);
					
					while(!(contentLine == null)){
						browser.getEngine().loadContent(contentLine);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  */      
           
          
           browser.getEngine().loadContent("<h1>Help</h1><p style=\"color:blue;\"><b>add </b></br></p><p>add [TASKNAME] s/[START] e/[END]</p><p><sub>Except <mark>TASKNAME</mark>, all the fields above are optional</sub></p><em>Examples: </em><p>add Math Assignment s/today </p><p>add Music Lesson s/11112016 1500 e/5pm</p><p>add Sleepover s/today 9pm e/tomorrow 9am </p><p>add CS1020 Revision </p><p style=\"color:blue;\"><br><b>delete </b></br></p><p>delete [INDEX]</p><em>Examples: </em><p>delete 1 </p><p style=\"color:blue;\"><br><b>edit </b></br></p><p>edit [TASKNAME] s/[START] e/[END]</p><p><sub>Except <mark>TASKNAME</mark>, all the fields above are optional</sub></p><em>Examples: </em><p>edit 1 Math Assignment</p><p>edit 5 Music Lesson s/1800 e/7pm</p><p>edit 9 Freshmen Camp s/15112016 1pm e/17112016 9am </p><p>edit 11 CS1020 Revision s/tomorrow </p><p style=\"color:blue;\"><br><b>find </b></br></p><p>find [INDEX]</p><em>Examples: </em><p>find 1 </p><p style=\"color:blue;\"><br><b>show </b></br></p><p>show [DATE]/[COMPLETED]</p><em>Examples: </em><p>show complete </p><p>show all </p><p>show today </p><p style=\"color:blue;\"><br><b>undo </b></br></p><p>undo</p><em>Examples: </em><p>undo</p><p style=\"color:blue;\"><br><b>help </b></br></p><p>help</p><em>Examples: </em><p>help </p>" );
           FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
           mainPane.getChildren().add(browser);
       }

    public void show() {
        dialogStage.showAndWait();
    }
}
