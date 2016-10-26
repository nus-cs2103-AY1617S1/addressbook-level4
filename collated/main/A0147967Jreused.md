# A0147967Jreused
###### \java\seedu\address\ui\AutoCompleteTextField.java
``` java
/**
 * This class extends javafx text field for auto complete
 * implementation for Happy Jim Task Master. 
 * Reference: https://gist.github.com/floralvikings/10290131
 */
public class AutoCompleteTextField extends TextField
{	
	/** Stores command, syntax and utility words.*/
	private final SortedSet<String> dictionary;
	
	/** Pops up the dictionary words. */
	private ContextMenu dictionaryPopup;
	
	/** Determines whether to turn on the auto-complete function. */
	public boolean turnOn = true;
	
	/** Constructor */
	public AutoCompleteTextField() {
		
		super();
		
		dictionary = new TreeSet<>();
		setDictionary();
		dictionaryPopup = new ContextMenu();
		
		
		textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {				
				if (getText().length() == 0 || getCurrentWord() == null){
					dictionaryPopup.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(dictionary.subSet(getCurrentWord(), getCurrentWord() + Character.MAX_VALUE));
					if (dictionary.size() > 0){
						popup(searchResult);
						if (!dictionaryPopup.isShowing() && turnOn){
							dictionaryPopup.show(AutoCompleteTextField.this, Side.BOTTOM, getText().length()*8, 0);
						}
					} else {
						dictionaryPopup.hide();
					}
				}
			}
		});

		focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
				dictionaryPopup.hide();
			}
		});
	}

	/**
	 * Pop out the entry set with the given search results.
	 */
	private void popup(List<String> searchResult) {
		
		List<CustomMenuItem> menuItems = new LinkedList<>();

		for (String result: searchResult){
			
			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent actionEvent) {
					setText(getPreviousWords() + result + " ");
					dictionaryPopup.hide();
					positionCaret(getText().length());
				}
			});
			
			menuItems.add(item);
		}
		dictionaryPopup.getItems().clear();
		dictionaryPopup.getItems().addAll(menuItems);
	}
	
	/**
	 * Sets the data of the dictionary used. Naive method. 
	 */
	private void setDictionary(){
		
		//syntax words
		dictionary.add("from");
		dictionary.add("to");
		dictionary.add("by");
		dictionary.add("t/");
		//command word
		String[] commandWords = {"add","block","cd","clear","delete","done","edit","help","u","r","find","list","select","exit"};
		for(String s: commandWords) dictionary.add(s);
		//date
		String[] dateWords = {"jan","feb","mar","apr","may","jun","jul",
							  "aug","sep","oct","nov","dec","today","tomorrow","yesterday",
							  "monday","tuesday","wednesday","thursday","friday","saturday","sunday",
							  "daily", "weekly", "monthly", "yearly","next",
							  "day", "week", "year"};
		for(String s: dateWords) dictionary.add(s);
	}
	
	/**
	 * Returns current word that should be provided suggestions.
	 */
	private String getCurrentWord(){
		if(getText().endsWith(" ")) return null;
		String[] enteredWords = getText().split(" ");
		return enteredWords[enteredWords.length - 1];
	}
	
	/**
	 * Returns existing text for future text modification. 
	 */
	private String getPreviousWords(){
		return getText().substring(0, getText().length() - getCurrentWord().length());
	}
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
/**
 * The Browser Panel of the App modified to display the agenda.
 */
public class BrowserPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(BrowserPanel.class);
    private static final String FXML = "BrowserPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private MyAgenda agenda;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a BrowserPanel.
     */
    @Override
    public void setNode(Node node) {
    	panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
		return FXML;
       //not applicable
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
    	this.placeHolderPane = pane;
    }

    /**
     * Factory method for creating a Browser Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public static BrowserPanel load(Stage primaryStage, AnchorPane browserPanelPlaceholder,
            ObservableList<TaskComponent> taskList){
        logger.info("Initializing Agenda");       
        BrowserPanel browserPanel =
                UiPartLoader.loadUiPart(primaryStage, browserPanelPlaceholder, new BrowserPanel());
        browserPanel.initialize(taskList);
        FxViewUtil.applyAnchorBoundaryParameters(browserPanel.agenda, 0.0, 0.0, 0.0, 0.0);
        browserPanel.placeHolderPane.getChildren().add(browserPanel.panel);
        return browserPanel;
    }
    
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public void handleNavigationChanged(String command) {
        
        previousCommandTest = command;

        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }
    
    public void turnOffAutoComplete(){
    	commandTextField.turnOn = false;
    }

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.getStyleClass().remove("fail");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }
    
```
###### \resources\view\DarkTheme.css
``` css
.background {
    -fx-background-color: derive(#6b7a8f, 20%);
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.auto-complete-text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.split-pane:horizontal .split-pane-divider {
    -fx-border-color: transparent #6b7a8f transparent #6b7a8f;
    -fx-background-color: transparent, derive(#6b7a8f, 10%);
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#6b7a8f, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell .label {
    -fx-text-fill: #010504;
}

#navbarView .list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 10;
    -fx-padding: 0 0 0 0;
    -fx-background-color : derive(#6b7a8f, 20%);
}
#navbarView .list-cell .label {
    -fx-font-size: 20px;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: derive(#F0F0F0, 80%);
    -fx-opacity: 1;
    -fx-text-alignment: center;
}

#navbarView .list-cell:filled:selected:focused, #navbarView .list-cell:filled:selected {
    -fx-background-color: derive(#30415d, 50%);
    -fx-text-fill: white;
}

#navbarView .list-cell:even:filled {
    -fx-background-color: derive(#f7882f, 20%);
}

#navbarView .list-cell:odd:filled {
    -fx-background-color: derive(#f7c331, 50%);
}
#navbarView .list-cell:odd:filled .image-view{
    -fx-image:url('/images/ddl_icon.png');
}
#navbarView .list-cell:even:filled .image-view{
    -fx-image:url('/images/task_icon.png');
    -fx-alignment:left;
}


.cell_big_label {
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-size: 11px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#a7b3a5, 20%);
}

.anchor-pane-with-border {
     -fx-background-color: derive(#6b7a8f, 20%);
     -fx-border-color: derive(#6b7a8f, 10%);
     -fx-border-top-width: 1px;
     -fx-text-alignment: center;
}

.status-bar {
    -fx-background-color: derive(#6b7a8f, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #F0F0F0;
}

.result-display .label {
    -fx-text-fill: black !important;
    -fx-text-alignment: center;
}

.status-bar .label {
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#6b7a8f, 30%);
    -fx-border-color: derive(#6b7a8f, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#6b7a8f, 30%);
    -fx-border-color: derive(#6b7a8f, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#6b7a8f, 30%);
}

.context-menu {
    -fx-background-color: derive(#6b7a8f, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#6b7a8f, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #6b7a8f;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #6b7a8f;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #6b7a8f;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #6b7a8f;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #6b7a8f;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#6b7a8f, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#6b7a8f, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-color: #d6d6d6;
    -fx-border-width: 1 1 1 1;
}

#cardPaneNav {
    -fx-background-color: transparent;
    -fx-border-color: derive(#6b7a8f, 10%);
    -fx-border-width: 0.5 0.5 0.5 0.5;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}
```
###### \resources\view\Extensions.css
``` css
.error {
    -fx-background-color: red;
}

.fail {
    -fx-background-color: orange;
}

.tag-selector {
    -fx-border-width: 1;
    -fx-border-color: white;
    -fx-border-radius: 3;
    -fx-background-radius: 3;
}

.tooltip-text {
    -fx-text-fill: white;
}

```
###### \resources\view\MyAgenda.css
``` css
.MyAgenda .Week {
	-fx-background-color: #efefef90;
}

.MyAgenda .HourLabel {
	-fx-fill: #25242c;
	-fx-font-family: "Segue UI Semibold";
	-fx-alignment: center;
	-fx-stroke: transparent;
}

.MyAgenda .HourLine {
	-fx-fill: transparent;
	-fx-stroke: LIGHTGRAY;
}

.MyAgenda .HalfHourLine {
	-fx-fill: transparent;
	-fx-stroke: LIGHTGRAY;
	-fx-stroke-dash-array: 4 4 4 4;
}

.MyAgenda .DayHeader {
	-fx-border-color:LIGHTGRAY;
	-fx-text-fill: #25242c;
	-fx-font-family:"Segue UI Semibold";
	-fx-border-width: 0px 0px 0px 1px;
	-fx-background-color: #efefef90;
}


.MyAgenda .DayHeader .weekend { 
	-fx-text-fill: #25242c;
	-fx-font-family:"Segue UI Semibold";
	-fx-font-weight: bold;
	
}


.MyAgenda .Day {
	-fx-fill: transparent;
	-fx-stroke: transparent;
	-fx-border-color:LIGHTGRAY;
	-fx-border-width: 0px 0px 0px 1px;
}

.MyAgenda .weekend {
	-fx-background-color: rgba(221, 221, 214, 0.7);
}

.MyAgenda .today {
	-fx-background-color: rgba(247, 155, 46, 0.3);
	-fx-border-color: RED;
	-fx-border-width: 1px;
	-fx-background-insets: -1.4, 0, 1, 2;
	-fx-background-radius: 6.4, 5, 4, 3;
}

.MyAgenda .Appointment {
	-fx-border-color: WHITE;
	-fx-border-width: 1px;
}

.MyAgenda .AppointmentTimeLabel {
	-fx-fill: #32313b;
	-fx-font-fmaily:"Segue UI Light";
	-fx-stroke: transparent;
	-fx-font-size: 0.8em;
}

.MyAgenda .AppointmentLabel {
	-fx-fill: #152737;
	-fx-font-fmaily:"Segue UI Light";
	-fx-stroke: transparent;
}

.group0 { -fx-background-color: #AC725E; -fx-fill: #AC725E; }
.archive { -fx-background-color: rgba(102,171,140,0.8); -fx-fill: rgba(102,171,140,0.8); }
.group2 { -fx-background-color: #F83A22; -fx-fill: #F83A22; }
.group3 { -fx-background-color: #FA573C; -fx-fill: #FA573C; }
.group4 { -fx-background-color: #FF7537; -fx-fill: #FF7537; }
.group5 { -fx-background-color: #FFAD46; -fx-fill: #FFAD46; }
.block { -fx-background-color: rgba(148, 93, 96, 0.8); -fx-fill: rgba(148, 93, 96, 0.8); }
.group7 { -fx-background-color: #16A765; -fx-fill: #16A765; }
.group8 { -fx-background-color: #7BD148; -fx-fill: #7BD148; }
.normal { -fx-background-color: rgba(110, 196, 219, 0.8); -fx-fill: rgba(110, 196, 219, 0.8); }
.group10 { -fx-background-color: #FBE983; -fx-fill: #FBE983; }
.group11 { -fx-background-color: #FAD165; -fx-fill: #FAD165; }
.group12 { -fx-background-color: #92E1C0; -fx-fill: #92E1C0; }
.group13 { -fx-background-color: #9FE1E7; -fx-fill: #9FE1E7; }
.group14 { -fx-background-color: #9FC6E7; -fx-fill: #9FC6E7; }
.group15 { -fx-background-color: #4986E7; -fx-fill: #4986E7; }
.group16 { -fx-background-color: #9A9CFF; -fx-fill: #9A9CFF; }
.group17 { -fx-background-color: #B99AFF; -fx-fill: #B99AFF; }
.group18 { -fx-background-color: #C2C2C2; -fx-fill: #C2C2C2; }
.group19 { -fx-background-color: #CABDBF; -fx-fill: #CABDBF; }
.group20 { -fx-background-color: #CCA6AC; -fx-fill: #CCA6AC; }
.group21 { -fx-background-color: #F691B2; -fx-fill: #F691B2; }
.group22 { -fx-background-color: #CD74E6; -fx-fill: #CD74E6; }
.group23 { -fx-background-color: #A47AE2; -fx-fill: #A47AE2; }
 
.MyAgenda .GhostRectangle {
	-fx-fill: rgba(247, 155, 46, 0.3);
	-fx-stroke: #00000080;
    -fx-stroke-width: 1;
    -fx-stroke-dash-array: 4 4 4 4;
}

.MyAgenda .Selected { 
	-xfx-color: -fx-focused-base;
	-fx-background-color: -fx-focus-color, -fx-outer-border, -fx-inner-border, -fx-body-color;
	-fx-background-insets: -1.4, 0, 1, 2;
	-fx-background-radius: 6.4, 5, 4, 3;
}

.MyAgenda .Now {
	-fx-fill: #FF000088;
	-fx-stroke: RED;
	-fx-stroke-width: 2;
}
.MyAgenda .History {
	-fx-stroke: transparent;
	-fx-fill: #EFEFEF70;
}
```
