package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.GuiSettings;
import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.task.logic.Logic;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.parser.Parser;
import seedu.task.model.UserPrefs;
import seedu.task.model.task.ReadOnlyTask;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter; 
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs; 


    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String addressBookName;

    @FXML
    private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;
    //@@author A0153751H
    @FXML
    private Button aButton;
    
    @FXML
    private Button eButton;
    
    @FXML
    private TextField eIndex, eTitle, eDes, eStart, eDue,
    	eColor, eTags;
    
    @FXML
    private TextField aTitle, aDes, aStart, aDue, aInterval,
    	aTimeInterval, aColor, aTags;
    //@@author
    public MainWindow() {
        super();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String addressBookName, Config config, UserPrefs prefs,
                           Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.addressBookName = addressBookName;
        this.config = config;
        this.userPrefs = prefs;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        //@@author A0153751H
        aButton.setOnAction((ActionEvent e) -> {
        	String aHead = "add";
        	aHead = processAddParams(aHead);
        	try {
				logic.execute(aHead);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        eButton.setOnAction((ActionEvent e) -> {
        	String eHead = "edit";
        	eHead = processEditParams(eHead);
        	try {
				logic.execute(eHead);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        //@@author
        setAccelerators();
    }
    //@@author A0153751H
	private String processEditParams(String eHead) {
		if (!eIndex.getText().isEmpty()) {
			eHead = eHead + " " + eIndex.getText();
		}
		if (!eTitle.getText().isEmpty()) {
			eHead = eHead + " t/" + eTitle.getText();
		}
		if (!eDes.getText().isEmpty()) {
			eHead = eHead + " d/" + eDes.getText();
		}
		if (!eStart.getText().isEmpty()) {
			eHead = eHead + " sd/" + eStart.getText();
		}
		if (!eDue.getText().isEmpty()) {
			eHead = eHead + " dd/" + eDue.getText();
		}
		if (!eColor.getText().isEmpty()) {
			eHead = eHead + " c/" + eColor.getText();
		}
		if (!eTags.getText().isEmpty()) {
			String[] temp = eTags.getText().split(" ");
			for (String tag : temp) {
				eHead = eHead + " ts/" + tag;
			}
		}
		return eHead;
	}

	private String processAddParams(String aHead) {
		if (!aTitle.getText().isEmpty()) {
			aHead = aHead + " " + aTitle.getText();
		}
		if (!aDes.getText().isEmpty()) {
			aHead = aHead + " d/" + aDes.getText();
		}
		if (!aStart.getText().isEmpty()) {
			aHead = aHead + " sd/" + aStart.getText();
		}
		if (!aDue.getText().isEmpty()) {
			aHead = aHead + " dd/" + aDue.getText();
		}
		if (!aInterval.getText().isEmpty()) {
			aHead = aHead + " i/" + aInterval.getText();
		}
		if (!aTimeInterval.getText().isEmpty()) {
			aHead = aHead + " ti/" + aTimeInterval.getText();
		}
		if (!aColor.getText().isEmpty()) {
			aHead = aHead + " c/" + aColor.getText();
		}
		if (!aTags.getText().isEmpty()) {
			String[] temp = aTags.getText().split(" ");
			for (String tag : temp) {
				aHead = aHead + " t/" + tag;
			}
		}
		return aHead;
	}
	//@@author
    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }
}
