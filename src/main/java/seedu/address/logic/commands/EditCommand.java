package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

//@@author A0135812L
/**
 * Adds a task to the SmartyDo.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task to the SmartyDo. "
            + "Parameters: INDEX [PREFIX INPUT]..."
            + "Example: " + COMMAND_WORD
            + " CS2103 t;10-12-2016 10:00AM 11:00AM d;description a;Nus Computing t/sadLife";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";


    public final int targetIndex;
    private ReadOnlyTask taskToEdit;
    private HashMap<String, List<String>> field_and_newValue_pair;

    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, HashMap<String, List<String>> field_and_newValue_pair){
		this.targetIndex = targetIndex;
    	this.field_and_newValue_pair = field_and_newValue_pair;
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
        ReadOnlyTask editedTask = null;
        try {
            for(Entry<String, List<String>> entry : field_and_newValue_pair.entrySet()){
                
                String fieldString = entry.getKey();
                List<String> valueString = entry.getValue();
                Class<?>[] argTypes = new Class[valueString.size()];
                for(int i=0; i<valueString.size(); i++){
                    argTypes[i] = valueString.get(0).getClass();
                }
                
                Field field = taskClazz.getDeclaredField(fieldString);
                Object new_value = getObject(valueString, field);
                assert new_value !=null;
                changesToBeMade.put(field, new_value);
            }
            editedTask = model.editTask(taskToEdit, changesToBeMade);
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

 // Modified from http://stackoverflow.com/a/13872171/7068957
    private Object getObject(List<String> valueString, Field field)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class type;
        if(field.getType()==Optional.class){
            assert field.getName() == "time";
            type = Time.class;
        }else{
            type= field.getType();
        }
        for (Constructor<?> ctor : type.getConstructors()) {
            Class<?>[] paramTypes = ctor.getParameterTypes();
            // If the arity matches, let's use it.
            if (valueString.size() == paramTypes.length) {
                return newInstance(valueString, ctor);
            }
        }
        return null;
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

