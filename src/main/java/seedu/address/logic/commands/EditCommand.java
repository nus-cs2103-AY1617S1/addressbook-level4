package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

//@@author A0135812L
/**
 * Adds a task to the SmartyDo.
 */
public class EditCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task to the SmartyDo. "
            + "Parameters: INDEX [PREFIX INPUT]..."
            + "Example: " + COMMAND_WORD
            + " CS2103 t;10-12-2016 10:00AM 11:00AM d;description a;Nus Computing t/sadLife";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";


    public final int targetIndex;
    private ReadOnlyTask taskToEdit;
    private ReadOnlyTask editedTask;
    private HashMap<String, List<String>> field_and_newValue_pair;
    private boolean isExecutedBefore;

    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, HashMap<String, List<String>> field_and_newValue_pair){
		this.targetIndex = targetIndex;
    	this.field_and_newValue_pair = field_and_newValue_pair;
    	isExecutedBefore = false;
    }

    @Override
    public CommandResult execute() {

        assert model != null;
        assert undoRedoManager != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(targetIndex - 1);
        Class<? extends ReadOnlyTask> taskClazz = Task.class;
        HashMap<Field, Object> changesToBeMade = new HashMap<>();
        editedTask = null;
        try {
            for(Entry<String, List<String>> entry : field_and_newValue_pair.entrySet()){

                String fieldString = entry.getKey();
                List<String> valueString = entry.getValue();

                Field field = taskClazz.getDeclaredField(fieldString);
                Object new_value = getObject(valueString, field);
                assert new_value !=null;
                changesToBeMade.put(field, new_value);
            }
            editedTask = model.editTask(taskToEdit, changesToBeMade);
            isExecutedBefore = pushCmdToUndo(isExecutedBefore);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (NoSuchFieldException e){
            e.printStackTrace();
            assert false : "Checking of inputs'validity should be done in parser.";
        } catch (Exception e) {
            e.printStackTrace();
            assert false : e.getMessage();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
    }

    //@@author A0121261Y
    /**
     * Updates the task back to its original form by deleting the edited task
     * and restoring the original state.
     */
    public CommandResult unexecute() {
        int toRemove;
        toRemove = model.getToDo().getTaskList().indexOf(editedTask);
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ReadOnlyTask taskToDelete = lastShownList.get(toRemove);

        try {
            model.deleteTask(taskToDelete);
            model.addTask((Task) taskToEdit);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false: "impossible for task to be missing";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
    }

    @Override
    public boolean pushCmdToUndo(boolean isExecuted) {
        if (!isExecuted) {
            undoRedoManager.addToUndo(this);
        }
        return true;
    }
    //@@author

 // Modified from http://stackoverflow.com/a/13872171/7068957
    private Object getObject(List<String> valueString, Field field) throws InstantiationException,
        IllegalAccessException, InvocationTargetException, IllegalValueException, NoSuchMethodException, SecurityException {
        Class type;
        if(field.getName() == "time"){
            type = Time.class;
        }else if(field.getName()== "tags"){
            HashSet<Tag> tags = new HashSet<>();
            for(String str : valueString){
                tags.add(new Tag(str));
            }
            return new UniqueTagList(tags);
        }else{
            type = field.getType();
        }
        
        Class<?>[] argTypes = new Class[valueString.size()];
        for(int i=0; i<valueString.size(); i++){
            argTypes[i] = valueString.get(i).getClass();
        }
        
        Constructor suitableConstructor = type.getDeclaredConstructor(argTypes);
        
        return newInstance(valueString, suitableConstructor);
    }

    private Object newInstance(List<String> valueString, Constructor<?> ctor)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        try{
            return ctor.newInstance(valueString);
        }catch(IllegalArgumentException e){
            if(valueString.size()==1){
                return ctor.newInstance(valueString.get(0));
            }else if(valueString.size()==2){
                return ctor.newInstance(valueString.get(0), valueString.get(1));
            }else{
                return ctor.newInstance(valueString.get(0), valueString.get(1), valueString.get(2));
            }
        }

    }

}

