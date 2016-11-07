# A0135722L Zhiyuan
###### \java\seedu\simply\logic\commands\DoneCommand.java
``` java
public class DoneCommand extends Command {
    
    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as done. \n"
            + "Parameters: INDEX (must be positive integer) \n"
            + "Example: " + COMMAND_WORD + " T1"
            + "      "
            + "Example: " + COMMAND_WORD + " T1, E2, D3"
            + "      "
            + "Example: " + COMMAND_WORD + " T1-T10";
    
    public static final String MESSAGE_MARK_DONE_SUCCESS = "Marked task as done: %1$s";
    
    public ArrayList<String> targetIndexes = new ArrayList<String>();
    
    public DoneCommand(ArrayList<String> targetIndexes) throws IllegalValueException {
        this.targetIndexes = targetIndexes;
    }
    
    
    @Override
    public CommandResult execute() {
        
        ArrayList<String> pass = new ArrayList<String>(targetIndexes);
//        Collections.sort(targetIndexes);
//        Collections.reverse(targetIndexes);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        
        
        ArrayList<Integer> intIndex = new ArrayList<Integer>();
        for(int i = 0; i < targetIndexes.size(); i++) {
            Integer index = Integer.valueOf(targetIndexes.get(i).substring(1));
            intIndex.add(index);
        }
        
        Collections.sort(intIndex);
        Collections.reverse(intIndex);
        
        model.addToUndoStack();
        
        for(int i = 0; i < targetIndexes.size(); i++) {
            if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'E') {
                if(lastShownEventList.size() < intIndex.get(i)) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownEventList.get(intIndex.get(i) - 1);
                
                try {
                    //model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }

            else if (Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'D') {
                if (lastShownDeadlineList.size() < intIndex.get(i)) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownDeadlineList.get(intIndex.get(i) - 1);
                
                try {
                    //model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        
            else if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'T') {
                if (lastShownTodoList.size() < intIndex.get(i)) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownTodoList.get(intIndex.get(i) - 1);

                try {
                    //model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        return new CommandResult(String.format(MESSAGE_MARK_DONE_SUCCESS, pass));
    }

}
```
###### \java\seedu\simply\logic\parser\Parser.java
``` java
    private Command prepareComplete(String args) {
        final Matcher matcher = ARGS_FORMAT_COMPLETE.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoneCommand.MESSAGE_USAGE));
        }

        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(",")));

        if(args.contains("-")){        
            char cat = args.charAt(1);
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            //check format of start and end
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
            indexes = rangeToMultiple(start, end, cat);
        }

        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDone = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDone); 


        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }     

        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDone = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDone);

            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));             
            }           
        }


        try {
            return new DoneCommand(indexes);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
```
###### \java\seedu\simply\model\ModelManager.java
``` java
    public synchronized void markDone(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.completeTask(target);
        updateFilteredListToShowAllUncompleted();
        indicateTaskBookChanged();
    }

```
###### \java\seedu\simply\model\task\UniqueTaskList.java
``` java
    public boolean completed(ReadOnlyTask target) {
        int completeIndex = internalList.lastIndexOf(target);
        Task toComplete = new Task(internalList.get(completeIndex));
        toComplete.setCompleted(true);
        internalList.set(completeIndex, toComplete);
        return true;
    }

    public String getCurrentDate(){
        LocalDate now = LocalDate.now();
        String date = now.toString();
        String[] date_cat = date.split("-");
        String localDate = date_cat[2];
        localDate = localDate.concat(date_cat[1]);
        localDate = localDate.concat(date_cat[0].substring(2));
        return localDate;
    }
}
```
