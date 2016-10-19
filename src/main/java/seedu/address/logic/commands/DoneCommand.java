package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the specified task or event as done \n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " horror night";
    public static final String MARK_DONE_SUCCESS="Marked as done: %1$s";
    public static final String MULTIPLE_TASK_SATISFY_KEYWORD="Please select the Task identified "
    		+ "by the index number.\n"+"Parameters: INDEX(must be a positive integer)\n"
    		+"Example: "+COMMAND_WORD+" 1";
    public static final String TASK_NOT_FOUND="Task not found: %1$s";

    private final Set<String> keywords;
    public final int targetIndex;

    public DoneCommand(Set<String> keywords) {
        this.keywords = keywords;
        targetIndex=-1;
    }
    
    public DoneCommand(int indexToMark){
    	keywords=null;
    	targetIndex=indexToMark;
    }

    @Override
    public CommandResult execute() {
    	if(keywords!=null){
        model.updateFilteredTaskList(keywords);
        if(model.getFilteredTaskList().size()==0)
        	return new CommandResult(TASK_NOT_FOUND);
        else if(model.getFilteredTaskList().size()>1)
        	return new CommandResult(MULTIPLE_TASK_SATISFY_KEYWORD);
        else{
        	 UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        	 ReadOnlyTask TaskToMark=null;
        	 for(ReadOnlyTask e:lastShownList){
        		 if(keywords.stream().filter(keyword -> StringUtil.containsIgnoreCase(e.getName().taskName, keyword))
                    .findAny().isPresent()){
        			 TaskToMark=e;
        			 TaskToMark.markAsDone();
        		 break;
        		 }
        	 }
        	 return new CommandResult(String.format(MARK_DONE_SUCCESS, TaskToMark));
        }
    	}else{
    		ReadOnlyTask TaskToMark = null;
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
                if (lastShownList.size() < targetIndex) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                TaskToMark = lastShownList.get(targetIndex - 1);
                TaskToMark.markAsDone();
                return new CommandResult(String.format(MARK_DONE_SUCCESS, TaskToMark));
    	}
    }

}
