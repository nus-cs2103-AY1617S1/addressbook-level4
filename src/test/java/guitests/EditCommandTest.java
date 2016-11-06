package guitests;

import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.Reminder;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.testutil.*;

//@@author A0125097A
public class EditCommandTest extends AddressBookGuiTest {
    
    private static final boolean REMINDER_NOT_RECURRING = false;
    private static final boolean REMINDER_IS_RECURRING = true;

    @Test
    public void edit_activityParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditActivityResult(index, currentList);
    }

    @Test
    public void edit_taskParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 3;
        assertEditTaskResult(index, currentList);
    }

    @Test
    public void edit_activityWithTaskParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditTaskResult(index, currentList);
    }

    @Test
    public void edit_taskWithActivityParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 4;
        assertEditActivityResult(index, currentList);
    }

    @Test
    public void edit_eventParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 5;
        assertEditEventResult(index, currentList);
    }

    @Test
    public void edit_activityWithEventParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditEventResult(index, currentList);
    }

    @Test
    public void edit_eventWithActivityParameters() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 5;
        assertEditActivityResult(index, currentList);
    }

    @Test
    public void edit_activityWithRecurringReminder() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditRecurReminderResult(index, currentList);
    }
    
    @Test
    public void edit_eventWithRecurringStartEndTimes() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 5;
        assertEditRecurEventResult(index, currentList);
    }
    
    @Test
    public void edit_activity_addTags() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertAddTagsResult(index, false, currentList);
    }
    
    @Test
    public void edit_activity_addTags_withDuplicates() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertAddTagsResult(index, true, currentList);
    }

    private void assertEditActivityResult(int index, TestActivity... currentList) {
        assertEditResult(index, "activity", REMINDER_NOT_RECURRING, currentList);
    }

    private void assertEditTaskResult(int index, TestActivity[] currentList) {
        assertEditResult(index, "task", REMINDER_NOT_RECURRING, currentList);
    }

    private void assertEditEventResult(int index, TestActivity[] currentList) {
        assertEditResult(index, "event", REMINDER_NOT_RECURRING, currentList);
    }
    
    private void assertEditRecurReminderResult(int index, TestActivity[] currentList) {
        assertEditResult(index, "activity", REMINDER_IS_RECURRING, currentList);
    }
    
    private void assertEditRecurEventResult(int index, TestActivity[] currentList) {
        assertEditResult(index, "recur-event", REMINDER_NOT_RECURRING, currentList);
    }

    private void assertEditResult(int index, String type, boolean isReminderRecurring, TestActivity... currentList) {
        String newName = "Totally New Name";
        String newReminder = "1-01-2020 1111";
        String newDuedate = "10-10-2020 1010";
        String newPriority = "3";
        String newStartTime = "2-02-2020 1212";
        String newEndTime = "10-10-2020 1010";
        String newRecurReminder = "every thu 1300";
        String newRecurStartTime = "every fri 1400";
        String newRecurEndTime = "every fri 1600";
        String editCommand = "edit " + index;

        TestActivity activityBeforeEdit;
        activityBeforeEdit = produceNewActivityObject(currentList[index - 1]);

        TestActivity activityAfterEdit = null;

        editCommand += " n/" + newName;
        switch (type) {
        case "task": {
            activityAfterEdit = new TestTask(currentList[index - 1]);
            ((TestTask) activityAfterEdit).setDueDate(newDuedate);
            ((TestTask) activityAfterEdit).setPriority(newPriority);
            editCommand += " d/" + newDuedate + " p/" + newPriority;
        }
            break;

        case "event": {
            activityAfterEdit = new TestEvent(currentList[index - 1]);
            ((TestEvent) activityAfterEdit).setStartTime(newStartTime);
            ((TestEvent) activityAfterEdit).setEndTime(newEndTime);
            editCommand += " s/" + newStartTime + " e/" + newEndTime;
        }
            break;
            
        case "recur-event": {
            activityAfterEdit = new TestEvent(currentList[index - 1]);
            ((TestEvent) activityAfterEdit).setStartTime(newRecurStartTime);
            ((TestEvent) activityAfterEdit).setEndTime(newRecurEndTime);
            editCommand += " s/" + newRecurStartTime + " e/" + newRecurEndTime;
        }
            break;

        case "activity":
            activityAfterEdit = currentList[index - 1];
        }
        

        activityAfterEdit.setName(newName);
        
        if (isReminderRecurring) {
            activityAfterEdit.setReminder(newRecurReminder);
            editCommand += " r/" + newRecurReminder;
        } else {
            activityAfterEdit.setReminder(newReminder);
            editCommand += " r/" + newReminder;
        }
        
        commandBox.runCommand(editCommand);

        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s", activityBeforeEdit.getAsText(),
                activityAfterEdit.getAsText()));

        assertTrue(activityListPanel.isListMatching(currentList));

    }
    
    private void assertAddTagsResult(int index, boolean isAddingDuplicateTags, TestActivity... currentList) {
        String editCommand = "edit " + index;
        String newTag1 = "newtag1";
        String newTag2 = "newtag2";
        
        TestActivity activityBeforeEdit = produceNewActivityObject(currentList[index - 1]);
        TestActivity activityAfterEdit = currentList[index - 1];
        
        try {
            if (isAddingDuplicateTags) {
                editCommand += " t/" + newTag1 + " t/" + newTag1;
                activityAfterEdit.addTags(new Tag(newTag1));
            } else {
                editCommand += " t/" + newTag1 + " t/" + newTag2;
                HashSet<Tag> newTags = new HashSet<>(Arrays.asList(new Tag(newTag1), new Tag(newTag2)));
                
                for (Tag newTag : newTags) {
                    activityAfterEdit.addTags(newTag);
                }
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
        
        commandBox.runCommand(editCommand);

        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s", activityBeforeEdit.getAsText(),
                activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
    }

    private TestActivity produceNewActivityObject(TestActivity original) {
        String type = original.getClass().getSimpleName().toLowerCase();

        switch (type) {
        case "testactivity":
            return new TestActivity(original);
        case "testtask":
            return new TestTask((TestTask) original);
        default: // case "event":
            return new TestEvent((TestEvent) original);
        }
    }

}
