# A0139430L JingRui
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    public DeleteCommand(ArrayList<String> targetIndexes) throws IllegalValueException {
        pass = targetIndexes;
        for(int i= 0; i < targetIndexes.size(); i++){
            String temp = targetIndexes.get(i);
            String stringIdx = temp.substring(1);
            Integer intIdx = Integer.valueOf(stringIdx);
            if(temp.charAt(0)=='E'){
                targetIndexesE.add(intIdx);
            }
            else if(temp.charAt(0)=='D'){
                targetIndexesD.add(intIdx);
            }
            else if(temp.charAt(0)=='T'){
                targetIndexesT.add(intIdx);
            }
            else{
                throw new IllegalValueException(MESSAGE_CATEGORY_CONSTRAINTS);
            }
        }
    }

```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();

        if(targetIndexesE.size()>0){
            Collections.sort(targetIndexesE);
            Collections.reverse(targetIndexesE);
            model.addToUndoStack();
            for(int i=0; i<targetIndexesE.size();i++){               
                Integer idx = targetIndexesE.get(i); 
                if (lastShownEventList.size() < idx) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownEventList.get(idx-1);             
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }

        if(targetIndexesD.size()>0){
            Collections.sort(targetIndexesD);
            Collections.reverse(targetIndexesD);
            model.addToUndoStack();
            for(int i=0; i<targetIndexesD.size();i++){                 
                Integer idx = targetIndexesD.get(i);               
                if (lastShownDeadlineList.size() < idx) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownDeadlineList.get(idx-1);             
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }

        if(targetIndexesT.size()>0){
            Collections.sort(targetIndexesT);
            Collections.reverse(targetIndexesT);
            model.addToUndoStack();
            for(int i=0; i<targetIndexesT.size();i++){               
                Integer idx = targetIndexesT.get(i);                
                if (lastShownTodoList.size() < idx) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownTodoList.get(idx-1);       
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    model.getUndoStack().pop();
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, pass));
    }

}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
 */


public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String DESCRIPTION_WORD = "des";
    public static final String DATE_WORD = "date";
    public static final String START_WORD = "start";
    public static final String END_WORD = "end";
    public static final String TAG_WORD = "tag";
    public static final String ADD_WORD = "add";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task in Simply. "
            +"Parameters: INDEX <section to delete> <edited information>\n" 
            +"Example: " + COMMAND_WORD + " 1 " + DESCRIPTION_WORD + " beach party\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + DATE_WORD + " 120516\n"
            +"Example: " + COMMAND_WORD + " 1 " + START_WORD + " 1600\t\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + END_WORD + " 2300\n"
    		+"Example: " + COMMAND_WORD + " 1 " + TAG_WORD + " sentosa";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s%2$s       Changes:  %3$s";
    
    public final Integer targetIndex;
    public final String editArgs;
    public final char category;

    public EditCommand(Integer index, String args, char category) {
        // TODO Auto-generated constructor stub
        this.targetIndex = index;
        this.editArgs = args;
        this.category = category;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        if(category == 'E'){
            if (lastShownEventList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask eventToEdit = lastShownEventList.get(targetIndex - 1);
            
            try {
                model.addToUndoStack();
                model.getCommandHistory().add("edit");
                Task edited = model.editTask(eventToEdit, editArgs, category);
                lastShownEventList = model.getFilteredEventList();
               EventsCenter.getInstance().post(new JumpToListRequestEvent(lastShownEventList.indexOf(edited), category));
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }
            model.changeTaskCategory();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        
        else if(category == 'D'){
            if (lastShownDeadlineList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask deadlineToEdit = lastShownDeadlineList.get(targetIndex - 1);

            try {
                model.addToUndoStack();
                model.getCommandHistory().add("edit");
                Task edited = model.editTask(deadlineToEdit, editArgs, category);
                lastShownDeadlineList = model.getFilteredDeadlineList();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(lastShownDeadlineList.indexOf(edited), category));
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }
            model.changeTaskCategory();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        
        else if(category == 'T'){
            if (lastShownTodoList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask todoToEdit = lastShownTodoList.get(targetIndex - 1);

            try {
                model.addToUndoStack();
                model.getCommandHistory().add("edit");
                Task edited = model.editTask(todoToEdit, editArgs, category);
                lastShownTodoList = model.getFilteredTodoList();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(lastShownTodoList.indexOf(edited), category));
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }
            model.changeTaskCategory();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(keywords);
        model.updateFilteredDeadlineList(keywords);
        model.updateFilteredTodoList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredEventList().size(), model.getFilteredDeadlineList().size(), model.getFilteredTodoList().size()));
    }

}
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private Command prepareAddTags(String args) {
        final Matcher matcher = ARGS_FORMAT_ADD_TAGS.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        args = args.trim();
        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(Character.toString(args.charAt(1)));
        args = args.substring(args.indexOf(' ') + 1);
        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        Integer pass = index.get();
        args = "add ".concat(args);

        //uses Edit command to add tags
        return new EditCommand(pass, args, category);
    }

    /* @author Ronald
     * @param number of times to undo, args
     * @return the prepared command
     */

```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private Command prepareDelete(String args){
        final Matcher matcher = ARGS_FORMAT_DELETE.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }       
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(",")));       
        if(args.contains("-")){        
            char cat = args.charAt(1);
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            //check format of start and end if it is integer
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            indexes = rangeToMultiple(start, end , cat);
        }
        //check if index is valid 1st number check
        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDelete = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDelete);      
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }          
        //the rest of the number check
        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDelete = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDelete);
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));             
            }           
        }
        try {
            return new DeleteCommand(indexes);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private ArrayList<String> rangeToMultiple(int start, int end, char cat){
        //making format of String: T(start), T2, T3.....T(end)
        String newArgs = Character.toString(cat).concat(Integer.toString(start));
        for(int i = start+1; i<= end; i++){
            newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
            newArgs = newArgs.concat(Integer.toString(i));
        }
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")));
        return indexes;
    }
    /**
     * Parses arguments in the context of the Edit command.
     *
     * @param args full command args string
     * @return the prepared command
     */
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private Command prepareEdit(String args) {
        final Matcher matcher = ARGS_FORMAT_EDIT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }
        /*final Collection<String> indexes = Arrays.asList(args.trim().replaceAll(" ",  ""));
        Iterator<String> itr = indexes.iterator();
        ArrayList<Integer> pass = new ArrayList<Integer>(); //by right arraylist is redundant cause 1 value only, leave here first in case next time want use 
        Optional<Integer> index = parseIndex(itr.next()); */

        args = args.trim();  
        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(Character.toString(args.charAt(1)));
        args = args.substring(args.indexOf(' ') + 1);

        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Integer pass = index.get();
        return new EditCommand(pass, args, category);
    }

```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private Command prepareFind(String args) {
        final Matcher matcher = ARGS_FORMAT_KEYWORDS.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        //  final String[] keywords = matcher.group("keywords").split("\\s+");
        final String[] keywords = {args.trim()};
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override 
    public synchronized Task editTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        Task temp = taskBook.changeTask(target, args, category);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
        return temp;
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void changeTaskCategory() {
        try {
            taskBook.changeTaskCategory();
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        System.out.println("yes");
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        System.out.println("yes1");
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList() {
        System.out.println("yes2");
        return new UnmodifiableObservableList<>(filteredTodos);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredEvents.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredTodos.setPredicate(null);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
        @Override
        public boolean run(ReadOnlyTask Task) {

            return anyKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(Task.getName().taskDetails.toLowerCase(), keyword)
                            || StringUtil.containsIgnoreCase(Task.getDate().value, keyword)
                            || StringUtil.containsIgnoreCase(Task.getStart().value, keyword)
                            || StringUtil.containsIgnoreCase(Task.getEnd().value, keyword)
                            || StringUtil.containsIgnoreCase(Task.getTags().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", anyKeyWords);
        }
    }


}
```
###### \java\seedu\address\model\task\Date.java
``` java
    @Override
    public int compareTo(Date o) { 
        if(this.toString().compareTo("no date")==0 & o.toString().compareTo("no date")==0)
            return 0;
        else if(this.toString().compareTo("no date")==0 )
            return -1;
        else if(o.toString().compareTo("no date")==0 )
            return 1;
        
        String[] temp = this.value.split("-");
        String[] temp2 = o.toString().split("-");
        
        String date = temp[2].concat(temp[1]).concat(temp[0]);
        String date2 = temp2[2].concat(temp2[1]).concat(temp2[0]);
        
        return date.compareTo(date2);
    }

}
```
###### \java\seedu\address\model\task\End.java
``` java
    @Override
    public int compareTo(End o) {
        if(this.value.compareTo("no end") == 0 & o.toString().compareTo("no end") == 0) 
            return 0;
        else if(this.value.compareTo("no end") == 0 )
            return -1;
        else if(o.toString().compareTo("no end") == 0 )
            return 1;
        
        return this.value.compareTo(o.toString());
    }

}
```
###### \java\seedu\address\model\task\Start.java
``` java
    @Override
    public int compareTo(Start o) {  
        if(this.value.compareTo("no start") == 0 & o.toString().compareTo("no start") == 0) 
            return 0;
        else if(this.value.compareTo("no start") == 0)
            return -1;
        else if(o.toString().compareTo("no start") == 0)
            return 1;
            
        return this.value.compareTo(o.toString());
    }

}
```
###### \java\seedu\address\model\task\Task.java
``` java
    public boolean setTags(String specific_tag, String replacement) throws IllegalValueException{        
        Tag tempTag = new Tag(specific_tag);
        System.out.println(tags.contains(tempTag));
        Iterator<Tag> itr = tags.iterator();
        while(itr.hasNext()){
            Tag temp = itr.next();
            if(temp.equals(tempTag)){    
                temp.setTagName(replacement);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, start, end);//, tags);
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
```
###### \java\seedu\address\model\task\Task.java
``` java
    @Override
    public int compareTo(Task other) {
        if(this.isCompleted==true & other.isCompleted == false)
            return 1;
        else if(this.isCompleted==false & other.isCompleted == true)
            return -1;
        
        if(this.date.compareTo(other.date)==0){
            return compareTime(other);
        }

        return this.date.compareTo(other.date);
    }

    private int compareTime(Task other) {
        if (this.start.compareTo(other.start)==0)
            return this.end.compareTo(other.end);
        else
            return this.start.compareTo(other.start);
    }


}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
    public boolean contains(ReadOnlyTask toCheck) {
        if(toCheck.getTaskCategory()==3){
            assert toCheck != null;
            return findUncompletedDuplicate(toCheck);
        }
        else 
            assert toCheck != null;
        return internalList.contains(toCheck);
    }
    private boolean findUncompletedDuplicate(ReadOnlyTask toCheck) {
        for(int i =0; i<internalList.size(); i++){
            Task temp = internalList.get(i);
            if(temp.getName().toString().compareTo(toCheck.getName().toString())==0){
                return !temp.getIsCompleted();
            }
        }
        return false;
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(
                                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
    public Task edit(ReadOnlyTask key, String args) throws IllegalValueException {
        // TODO Auto-generated method stub
        String keyword = args.substring(0, args.indexOf(' '));
        args = args.substring(args.indexOf(' ') + 1);

        int editIndex = internalList.indexOf(key);
        //System.out.println(key + " " + args);
        Task toEdit = new Task(internalList.get(editIndex));
        if (keyword.equals(EditCommand.DESCRIPTION_WORD)) {
            return editDescription(args, editIndex, toEdit);
        } else if (keyword.equals(EditCommand.DATE_WORD)) {
            return editDate(args, editIndex, toEdit);
        } else if (keyword.equals(EditCommand.START_WORD)) {
            return editStart(args, editIndex, toEdit);
        } else if (keyword.equals(EditCommand.END_WORD)) {
            return editEnd(args, editIndex, toEdit);
        } else if (keyword.equals(EditCommand.TAG_WORD)) {
            return editTag(args, editIndex, toEdit);
        } else if (keyword.equals(EditCommand.ADD_WORD)) {            
            return addTag(args, editIndex, toEdit);
        }
        else {
            return null;
        }
    }
    private Task addTag(String args, int editIndex, Task toEdit) throws IllegalValueException {
        String[] newTag = args.replaceAll(" ", "").replaceFirst("#", "").split("#");          
        final Set<Tag> tagSet = new HashSet<>();
        for (int i = 0; i < newTag.length; i++) {
            tagSet.add(new Tag(newTag[i]));
        }
        UniqueTagList addTagList = new UniqueTagList(tagSet);            
        toEdit.addTags(addTagList);          
        internalList.set(editIndex, toEdit);
        return toEdit;
    }
    private Task editTag(String args, int editIndex, Task toEdit) throws IllegalValueException, DuplicateTagException {
        if (args.contains(">")){
            String[] beforeAndAfter = args.replaceAll(" ","").split(">");              
            toEdit.setTags(beforeAndAfter[0], beforeAndAfter[beforeAndAfter.length-1]);
        }
        else{
            toEdit.setTags(new UniqueTagList(new Tag(args)));;
        }

        internalList.set(editIndex, toEdit);
        return toEdit;
    }
    private Task editEnd(String args, int editIndex, Task toEdit) throws IllegalValueException {
        if(this.isNotValidTime(toEdit.getStart().toString(), args)){
            throw new IllegalValueException(AddCommand.END_TIME_BEFORE_START_TIME_MESSAGE);
        }
        if(args.compareTo("no end") == 0 & toEdit.getTaskCategory()!=3){ //not todo default end time 2359
            toEdit.setEnd(new End("2359"));
        }
        else if(toEdit.getTaskCategory()==3 & args.compareTo("no end") != 0){  //todo to Deadline
            toEdit.setDate(new Date(this.getCurrentDate()));
            toEdit.setStart(new Start("no start"));
            toEdit.setEnd(new End(args));
            toEdit.setTaskCategory(2);
        }
        else
            toEdit.setEnd(new End(args));
        internalList.set(editIndex, toEdit);
        FXCollections.sort(internalList);
        return toEdit;
    }
    private Task editStart(String args, int editIndex, Task toEdit) throws IllegalValueException {
        if(this.isNotValidTime(args, toEdit.getEnd().toString())){
            throw new IllegalValueException(AddCommand.START_TIME_BEFORE_END_TIME_MESSAGE);
        }
        else if(args.compareTo("no start") == 0 & toEdit.getTaskCategory()==1){ //event to deadline
            toEdit.setStart(new Start(args));
            toEdit.setTaskCategory(2);
        }
        else if(toEdit.getTaskCategory()==2){   //deadline to event
            toEdit.setStart(new Start(args));
            toEdit.setTaskCategory(1);
        }
        else if(toEdit.getTaskCategory()==3){  //todo to Event              
            toEdit.setDate(new Date(this.getCurrentDate()));
            toEdit.setStart(new Start(args));
            toEdit.setEnd(new End("2359"));
            toEdit.setTaskCategory(1);
        }
        else
            toEdit.setStart(new Start(args));
        internalList.set(editIndex, toEdit);
        FXCollections.sort(internalList);
        return toEdit;
    }
    private boolean isNotValidTime(String start, String end) {
        if(start.compareTo(end) >= 0)
            return true;
        return false;
    }
    private Task editDate(String args, int editIndex, Task toEdit) throws IllegalValueException {
        if(args.compareTo("no date") == 0 & toEdit.getTaskCategory()!=3){ // change to Todo
            toEdit.setDate(new Date("no date"));
            toEdit.setStart(new Start("no start"));
            toEdit.setEnd(new End("no end"));
            toEdit.setTaskCategory(3);
        }           
        else if(toEdit.getTaskCategory()==3){//todo to deadline
            toEdit.setDate(new Date(args));
            toEdit.setEnd(new End("2359"));
            toEdit.setTaskCategory(2);  
        }
        else
            toEdit.setDate(new Date(args));          
        internalList.set(editIndex, toEdit);
        FXCollections.sort(internalList);
        return toEdit;
    }
    private Task editDescription(String args, int editIndex, Task toEdit) throws IllegalValueException {
        toEdit.setName(new Name(args));
        internalList.set(editIndex, toEdit);
        return toEdit;
    }

```
###### \java\seedu\address\model\TaskBook.java
``` java
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        int taskCategory = key.getTaskCategory();
        if(taskCategory == 1){
            if (events.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
        else if (taskCategory == 2){
            if (deadlines.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
        else{
            if (todo.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
    }

```
###### \java\seedu\address\model\TaskBook.java
``` java
    public Task changeTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        if(category == 'E'){
            Task temp = events.edit(target, args);
            if (temp!=null) {
                return temp;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 'D'){
            Task temp = deadlines.edit(target, args);
            if (temp!=null) {
                return temp;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 'T'){
            Task temp = todo.edit(target, args);
            if (temp!=null) {
                return temp;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        return null;
    }
    
```
###### \java\seedu\address\model\TaskBook.java
``` java
    public void changeTaskCategory() throws TaskNotFoundException, DuplicateTaskException {
        for (Task task: events) {
            if(task.getTaskCategory()!=1){
                events.remove(task);
                if(task.getTaskCategory()==2){
                    deadlines.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(deadlines.getTaskIndex(task), 'D'));
                }
                else if(task.getTaskCategory()==3){
                    todo.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(todo.getTaskIndex(task), 'T'));
                }    
            }           
        }
        for (Task task: deadlines) {
            if(task.getTaskCategory()!=2){
                deadlines.remove(task);
                if(task.getTaskCategory()==1){
                    events.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(events.getTaskIndex(task), 'E'));
                }
                else if(task.getTaskCategory()==3){
                    todo.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(todo.getTaskIndex(task), 'T'));
                }    
            }   
        }
        for (Task task: todo) {
            if(task.getTaskCategory()!=3){
                todo.remove(task);
                if(task.getTaskCategory()==1){
                    events.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(events.getTaskIndex(task), 'E'));
                }
                else if(task.getTaskCategory()==2){
                    deadlines.add(task);
                    EventsCenter.getInstance().post(new JumpToListRequestEvent(deadlines.getTaskIndex(task), 'D'));
                }    
            }    
        }
    }

    //// tag-level operations
```
###### \java\seedu\address\model\TaskBook.java
``` java
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return events.getInternalList().size() + " events, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    public String toStringDeadlines() {
        return deadlines.getInternalList().size() + " deadlines, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    public String toStringTodo() {
        return todo.getInternalList().size() + " todo, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }

    public List<ReadOnlyTask> getDeadlineList() {
        return Collections.unmodifiableList(deadlines.getInternalList());
    }

    public List<ReadOnlyTask> getTodoList() {
        return Collections.unmodifiableList(todo.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueEventList() {
        return this.events;
    }

    public UniqueTaskList getUniqueDeadlineList() {
        return this.deadlines;
    }

    public UniqueTaskList getUniqueTodoList() {
        return this.todo;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                        && this.events.equals(((TaskBook) other).events)
                        && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events, tags);
    }

}
```
