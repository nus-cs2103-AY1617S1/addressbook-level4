package guitests;

import org.junit.Test;

import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.SelectCommand;
import seedu.taskmanager.model.item.ReadOnlyItem;

import static org.junit.Assert.assertEquals;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;


public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {

        assertSelectionInvalid(11); //invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(SelectCommand.COMMAND_WORD, 1); //first person in the list
        assertSelectionSuccess(SelectCommand.SHORT_COMMAND_WORD, 1); //first person in the list
        int personCount = td.getTypicalItems().length;
        assertSelectionSuccess(SelectCommand.COMMAND_WORD, personCount); //last person in the list
        assertSelectionSuccess(SelectCommand.SHORT_COMMAND_WORD, personCount); //last person in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(SelectCommand.COMMAND_WORD, middleIndex); //a person in the middle of the list
        assertSelectionSuccess(SelectCommand.SHORT_COMMAND_WORD, middleIndex); //a person in the middle of the list

        assertSelectionInvalid(personCount + 1); //invalid index
        assertPersonSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList(){
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand(SelectCommand.COMMAND_WORD + " " + index);
        assertResultMessage(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    private void assertSelectionSuccess(String commandWord, int index) {
        commandBox.runCommand(commandWord + " " + index);
        assertResultMessage("Selected " + index);
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(shortItemListPanel.getSelectedPersons().size(), 1);
        ReadOnlyItem selectedPerson = shortItemListPanel.getSelectedPersons().get(0);
        assertEquals(shortItemListPanel.getPerson(index-1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(shortItemListPanel.getSelectedPersons().size(), 0);
    }

}
