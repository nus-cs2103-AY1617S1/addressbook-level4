package seedu.cmdo.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import seedu.cmdo.commons.util.FxViewUtil;
import seedu.cmdo.logic.commands.AddCommand;
import seedu.cmdo.logic.commands.BlockCommand;
import seedu.cmdo.logic.commands.ClearCommand;
import seedu.cmdo.logic.commands.DeleteCommand;
import seedu.cmdo.logic.commands.DoneCommand;
import seedu.cmdo.logic.commands.EditCommand;
import seedu.cmdo.logic.commands.ExitCommand;
import seedu.cmdo.logic.commands.FindCommand;
import seedu.cmdo.logic.commands.HelpCommand;
import seedu.cmdo.logic.commands.ListCommand;
import seedu.cmdo.logic.commands.SelectCommand;
import seedu.cmdo.logic.commands.StorageCommand;

/**
 * Help Card collects information from the different commands 
 * and adds them to the help screen.
 * 
 * @@author A0141006B
 *
 */

public class HelpCard extends UiPart{

	private static final String FXML = "HelpCard.fxml";
	
	public String commandName;
	public String commandDescription;
	public String commandSyntax;
	
	@FXML
	private ScrollPane helpCardScrollPane;
	@FXML
	private VBox helpCardPane;
	@FXML
	private AnchorPane testPane;
	
	@FXML
	private VBox addCard;
	@FXML
	private VBox blockCard;
	@FXML
	private VBox clearCard;
	@FXML
	private VBox deleteCard;
	@FXML
	private VBox doneCard;
	@FXML
	private VBox editCard;
	@FXML
	private VBox exitCard;
	@FXML
	private VBox findCard;
	@FXML
	private VBox helpCard;
	@FXML
	private VBox listCard;
	@FXML
	private VBox selectCard;
	@FXML
	private VBox storageCard;
	
	@FXML
	private Label addNameLabel;
	@FXML
	private Label addDescriptionLabel;
	@FXML
	private Label blockNameLabel;
	@FXML
	private Label blockDescriptionLabel;
	@FXML
	private Label clearNameLabel;
	@FXML
	private Label clearDescriptionLabel;
	@FXML
	private Label deleteNameLabel;
	@FXML
	private Label deleteDescriptionLabel;
	@FXML
	private Label doneNameLabel;
	@FXML
	private Label doneDescriptionLabel;
	@FXML
	private Label editNameLabel;
	@FXML
	private Label editDescriptionLabel;
	@FXML
	private Label exitNameLabel;
	@FXML
	private Label exitDescriptionLabel;
	@FXML
	private Label findNameLabel;
	@FXML
	private Label findDescriptionLabel;
	@FXML
	private Label helpNameLabel;
	@FXML
	private Label helpDescriptionLabel;
	@FXML
	private Label listNameLabel;
	@FXML
	private Label listDescriptionLabel;
	@FXML
	private Label selectNameLabel;
	@FXML
	private Label selectDescriptionLabel;
	@FXML
	private Label storageNameLabel;
	@FXML
	private Label storageDescriptionLabel;
	
	@Override
	public void setNode(Node node) {
		helpCardScrollPane = (ScrollPane) node;	
	}
	
	@Override
	public String getFxmlPath() {		
		return FXML;
	}
	
	public ScrollPane getHelpCardPane() {
		ScrollPane helpCardScrollPane = new ScrollPane();
		AnchorPane testPane = new AnchorPane();
		VBox helpCardPane = new VBox();			
		FxViewUtil.applyFullWidth(helpCardScrollPane);		
		helpCardScrollPane.setPrefViewportWidth(600);
		helpCardScrollPane.setPrefViewportHeight(600);
		setHelpCards();		
		helpCardPane.getChildren().addAll(
				addCard, blockCard, clearCard, deleteCard, doneCard, editCard, 
				findCard, helpCard, listCard, selectCard, storageCard, exitCard);
		testPane.getChildren().add(helpCardPane);
		helpCardScrollPane.setContent(testPane);
		return helpCardScrollPane;
	}

	private void setHelpCards() {
		setAddInfo();
		setBlockInfo();
		setClearInfo();
		setDeleteInfo();
		setDoneInfo();
		setEditInfo();
		setFindInfo();
		setHelpInfo();
		setListInfo();
		setSelectInfo();
		setStorageInfo();
		setExitInfo();
	}
	
	private void setExitInfo() {
		exitCard = new VBox();
		setPadding(exitCard);
		exitNameLabel = new Label();
		exitNameLabel.setText("12. " + ExitCommand.COMMAND_WORD);
		exitDescriptionLabel = new Label();
		exitDescriptionLabel.setText(ExitCommand.MESSAGE_USAGE);
		exitCard.getChildren().addAll(exitNameLabel, exitDescriptionLabel);
	}
	
	private void setStorageInfo() {
		storageCard = new VBox();
		setPadding(storageCard);
		storageNameLabel = new Label();
		storageNameLabel.setText("11. " + StorageCommand.COMMAND_WORD);
		storageDescriptionLabel = new Label();
		storageDescriptionLabel.setText(StorageCommand.MESSAGE_USAGE);
		storageCard.getChildren().addAll(storageNameLabel, storageDescriptionLabel);
	}
	private void setSelectInfo() {
		selectCard = new VBox();
		setPadding(selectCard);
		selectNameLabel = new Label();
		selectNameLabel.setText("10. " + SelectCommand.COMMAND_WORD);
		selectDescriptionLabel = new Label();
		selectDescriptionLabel.setText(SelectCommand.MESSAGE_USAGE);
		selectCard.getChildren().addAll(selectNameLabel, selectDescriptionLabel);
	}	
	private void setListInfo() {
		listCard = new VBox();
		setPadding(listCard);
		listNameLabel = new Label();
		listNameLabel.setText("9. " + ListCommand.COMMAND_WORD);
		listDescriptionLabel = new Label();
		listDescriptionLabel.setText(ListCommand.MESSAGE_USAGE);
		listCard.getChildren().addAll(listNameLabel, listDescriptionLabel);	
	}
	private void setHelpInfo() {
		helpCard = new VBox();
		setPadding(helpCard);
		helpNameLabel = new Label();
		helpNameLabel.setText("8. " + HelpCommand.COMMAND_WORD);
		helpDescriptionLabel = new Label();
		helpDescriptionLabel.setText(HelpCommand.MESSAGE_USAGE);
		helpCard.getChildren().addAll(helpNameLabel, helpDescriptionLabel);
	}
	private void setFindInfo() {
		findCard = new VBox();
		setPadding(findCard);
		findNameLabel = new Label();
		findNameLabel.setText("7. " + FindCommand.COMMAND_WORD);
		findDescriptionLabel = new Label();
		findDescriptionLabel.setText(FindCommand.MESSAGE_USAGE);
		findCard.getChildren().addAll(findNameLabel, findDescriptionLabel);
	}
	private void setEditInfo() {
		editCard = new VBox();
		setPadding(editCard);
		editNameLabel = new Label();
		editNameLabel.setText("6. " + EditCommand.COMMAND_WORD);
		editDescriptionLabel = new Label();
		editDescriptionLabel.setText(EditCommand.MESSAGE_USAGE);
		editCard.getChildren().addAll(editNameLabel, editDescriptionLabel);		
	}
	private void setDoneInfo() {
		doneCard = new VBox();
		setPadding(doneCard);
		doneNameLabel = new Label();
		doneNameLabel.setText("5. " + DoneCommand.COMMAND_WORD);
		doneDescriptionLabel = new Label();
		doneDescriptionLabel.setText(DoneCommand.MESSAGE_USAGE);
		doneCard.getChildren().addAll(doneNameLabel, doneDescriptionLabel);		
	}
	private void setDeleteInfo() {
		deleteCard = new VBox();
		setPadding(deleteCard);
		deleteNameLabel = new Label();
		deleteNameLabel.setText("4. " + DeleteCommand.COMMAND_WORD);
		deleteDescriptionLabel = new Label();
		deleteDescriptionLabel.setText(DeleteCommand.MESSAGE_USAGE);
		deleteCard.getChildren().addAll(deleteNameLabel, deleteDescriptionLabel);	
	}
	private void setClearInfo() {
		clearCard = new VBox();
		setPadding(clearCard);
		clearNameLabel = new Label();
		clearNameLabel.setText("3. " + ClearCommand.COMMAND_WORD);
		clearDescriptionLabel = new Label();
		clearDescriptionLabel.setText(ClearCommand.MESSAGE_USAGE);
		clearCard.getChildren().addAll(clearNameLabel, clearDescriptionLabel);		
	}
	private void setBlockInfo() {
		blockCard = new VBox();
		setPadding(blockCard);
		blockNameLabel = new Label();
		blockNameLabel.setText("2. " + BlockCommand.COMMAND_WORD);
		blockDescriptionLabel = new Label();
		blockDescriptionLabel.setText(BlockCommand.MESSAGE_USAGE);
		blockCard.getChildren().addAll(blockNameLabel, blockDescriptionLabel);	
	}
	private void setAddInfo() {
		addCard = new VBox();
		setPadding(addCard);
		addNameLabel = new Label();
		addNameLabel.setText("1. " + AddCommand.COMMAND_WORD);
		addDescriptionLabel = new Label();
		addDescriptionLabel.setText(AddCommand.MESSAGE_USAGE);
		addCard.getChildren().addAll(addNameLabel, addDescriptionLabel);
	}
	
	private void setPadding(VBox node){
		node.setPadding(new Insets(10,10,10,10));
	}
}
