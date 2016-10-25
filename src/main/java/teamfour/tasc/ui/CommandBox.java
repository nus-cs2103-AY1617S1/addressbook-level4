package teamfour.tasc.ui;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.events.ui.IncorrectCommandAttemptedEvent;
import teamfour.tasc.commons.util.FxViewUtil;
import teamfour.tasc.logic.Logic;
import teamfour.tasc.logic.commands.CommandResult;

import java.util.logging.Logger;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;

    @FXML
    private TextField commandTextField;
    
    @FXML
    private ListView<String> wordList = new ListView<String>();
    
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        wordList.setTranslateX(80);
        placeHolderPane.getChildren().add(wordList);
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkCurrentWord(newValue);
        });
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN)) {
                    if (wordList.getItems().size() > 0) {
                        wordList.getSelectionModel().select(0);
                        wordList.requestFocus();
                    }
                }
            }
        });
        wordList.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    autoComplete(wordList.getSelectionModel().getSelectedItem());
                    commandTextField.requestFocus();
                    commandTextField.selectPositionCaret(0);
                    commandTextField.end();
                }
            }
        });
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }
    
    private void autoComplete(String word) {
        String[] words = commandTextField.getText().split(" ");
        words[words.length-1] = word + " ";
        commandTextField.setText(StringUtils.join(words, " "));
    }
    
    private void setWordChoices(String word) {
        if (word.trim().equals("")) {
            wordList.setItems(null);
            wordList.setPrefHeight(0);
            return;
        }
        String[] keywords = {"add", "by", "clear", "complete", "delete", "exit", "find", "from", "help", "hide", "list", "relocate", "repeat", "redo", "select", "show", "to", "undo", "update"};
        ObservableList<String> words = FXCollections.observableArrayList();
        int endIndex = word.length();
        for(String keyword: keywords) {
            if (keyword.length() >= endIndex) {
                if (keyword.substring(0, endIndex).equalsIgnoreCase(word)) {
                    words.add(keyword);
                }
            }
        }
        wordList.setItems(null);
        wordList.setItems(words);
        wordList.setPrefHeight(words.size() * 16 + 2);
    }
    
    private void checkCurrentWord(String command) {
        String[] words = command.split(" ");
        String lastWord = words[words.length-1];
        setWordChoices(lastWord);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }


    @FXML
    private void handleCommandInputChanged() {
        
        //Take a copy of the command text
        
        previousCommandTest = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }


    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

}
