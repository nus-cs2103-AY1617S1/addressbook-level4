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
            for(int i=0; i<targetIndexesE.size();i++){               
                Integer idx = targetIndexesE.get(i); 
                if (lastShownEventList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownEventList.get(idx-1);             
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
/*<<<<<<< HEAD
=======
           else if(Character.toUpperCase(targetIndexes.get(i).charAt(0))=='D'){
               if (lastShownDeadlineList.size() < Character.getNumericValue(targetIndexes.get(i).charAt(1))) {
                   indicateAttemptToExecuteIncorrectCommand();
                   return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
               }

               ReadOnlyTask taskToDelete = lastShownDeadlineList.get(Character.getNumericValue(targetIndexes.get(i).charAt(1)) - 1);

               try {
                   model.deleteTask(taskToDelete);
               } catch (TaskNotFoundException pnfe) {
                   assert false : "The target Deadline cannot be missing";
               }
           }
            
           else if(Character.toUpperCase(targetIndexes.get(i).charAt(0))=='T'){
               if (lastShownTodoList.size() < Character.getNumericValue(targetIndexes.get(i).charAt(1))) {
                   indicateAttemptToExecuteIncorrectCommand();
                   return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
               }

               ReadOnlyTask taskToDelete = lastShownTodoList.get(Character.getNumericValue(targetIndexes.get(i).charAt(1)) - 1);

               try {
                   model.addToUndoStack();
                   model.deleteTask(taskToDelete);
               } catch (TaskNotFoundException pnfe) {
                   assert false : "The target Deadline cannot be missing";
               }
           }
            
>>>>>>> undo_redo */
        }

        if(targetIndexesD.size()>0){
            Collections.sort(targetIndexesD);
            Collections.reverse(targetIndexesD);
            for(int i=0; i<targetIndexesD.size();i++){                 
                Integer idx = targetIndexesD.get(i);               
                if (lastShownDeadlineList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownDeadlineList.get(idx-1);             
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        if(targetIndexesT.size()>0){
            Collections.sort(targetIndexesT);
            Collections.reverse(targetIndexesT);
            for(int i=0; i<targetIndexesT.size();i++){               
                Integer idx = targetIndexesT.get(i);                
                if (lastShownTodoList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                ReadOnlyTask taskToDelete = lastShownTodoList.get(idx-1);       
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
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
                model.editTask(eventToEdit, editArgs, category);
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
                model.editTask(deadlineToEdit, editArgs, category);
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
                model.editTask(todoToEdit, editArgs, category);
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
        final Matcher matcher = ADD_TAGS_ARGS_FORMAT.matcher(args.trim());
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
        final Matcher matcher = DELETE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }
        
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(","))); //might need to change split regex to ; instead of ,
        
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
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            //making format of String: T(start), T2, T3.....T(end)
            String newArgs = Character.toString(cat).concat(Integer.toString(start));
            for(int i = start+1; i<= end; i++){
                newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
                newArgs = newArgs.concat(Integer.toString(i));
            }
            indexes = new ArrayList<String> (Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")));
        }

        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDelete = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDelete);      
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }          
        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDelete = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDelete);
            // System.out.println(index.isPresent() + args + indexes.size());
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
    private Command prepareEdit(String args) {
    	final Matcher matcher = EDIT_ARGS_FORMAT.matcher(args.trim());
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
        
        //pass.add(index.get());
        
        Integer pass = index.get();
        System.out.println(pass+" "+ args +" "+ category);
        return new EditCommand(pass, args, category);
    }
    
    private Command prepareComplete(String args) {
        final Matcher matcher = COMPLETE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoneCommand.MESSAGE_USAGE));
        }

        char cat = args.charAt(1);
        Collection<String> indexes = Arrays.asList(args.trim().replaceAll(" ", "").split(",")); //might need to change split regex to ; instead of ,
              
        if(args.contains("-")){          
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
            String newArgs = Character.toString(cat).concat(Integer.toString(start));
            for(int i = start+1; i<= end; i++){
                newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
                newArgs = newArgs.concat(Integer.toString(i));
            }
            indexes = Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")); //might need to change split regex to ; instead of ,
        }

        Iterator<String> itr = indexes.iterator();
        ArrayList<String> pass = new ArrayList<String>();
        pass.addAll(indexes);
        Optional<Integer> index = parseIndex(Character.toString(itr.next().charAt(1)));
        //System.out.println(index.isPresent() + args);
        
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }     
        
        while(itr.hasNext()){
            index = parseIndex(Character.toString(itr.next().charAt(1)));
            // System.out.println(index.isPresent() + args + indexes.size());
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));             
            }           
        }
       
        System.out.println(pass);
        return new DoneCommand(pass);
        
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        
        final Collection<String> indexes = Arrays.asList(args.trim().replaceAll(" ", "").split(","));
        Iterator<String> itr = indexes.iterator();
        ArrayList<Integer> pass = new ArrayList<Integer>();
        
        Optional<Integer> index = parseIndex(itr.next());
        
        //System.out.println(index.isPresent() + args);
        
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
        
        pass.add(index.get());

        while(itr.hasNext()){
            index = parseIndex(itr.next());
           // System.out.println(index.isPresent() + args);
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));             
            }           
            pass.add(index.get());
        }
        
        return new SelectCommand(pass);

    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        
       // System.out.println(matcher.matches());

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
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
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }
    
    @Override 
    public synchronized void editTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        addressBook.changeTask(target, args, category);
        //updateFilteredListToShowAll(); // why was this line commented out?
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }
    
    public synchronized void markDone(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.completeTask(target);
        //updateFilteredListToShowAll();
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void changeTaskCategory() {
        try {
            addressBook.changeTaskCategory();
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
    
    //public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompleted() {
    //    return new UnmodifiableObservableList<>(filteredCompleted);
    //}

    @Override
    public void updateFilteredListToShowAll() {
        filteredEvents.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredTodos.setPredicate(null);
        //filteredCompleted.setPredicate(null);
    }
    
```
###### \java\seedu\address\model\ModelManager.java
``` java
        @Override
        public boolean run(ReadOnlyTask Task) {
            return nameKeyWords.stream()
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
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    

}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
	public boolean edit(ReadOnlyTask key, String args) throws IllegalValueException {
        // TODO Auto-generated method stub
        String keyword = args.substring(0, args.indexOf(' '));
        args = args.substring(args.indexOf(' ') + 1);
        
        int editIndex = internalList.indexOf(key);
        //System.out.println(key + " " + args);
        Task toEdit = new Task(internalList.get(editIndex));
        if (keyword.equals(EditCommand.DESCRIPTION_WORD)) {
            toEdit.setName(new Name(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.DATE_WORD)) {
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
            return true;
        } else if (keyword.equals(EditCommand.START_WORD)) {
            if(args.compareTo("no start") == 0 & toEdit.getTaskCategory()==1){ //event to deadline
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
            return true;
        } else if (keyword.equals(EditCommand.END_WORD)) {
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
            return true;

        } else if (keyword.equals(EditCommand.TAG_WORD)) {
            //internalList.get(editIndex).setTags(new UniqueTagList(new Tag(args)));
            //Task toEdit = new Task(internalList.get(editIndex));

            if (args.contains(">")){
                String[] beforeAndAfter = args.replaceAll(" ","").split(">");              
                toEdit.setTags(beforeAndAfter[0], beforeAndAfter[beforeAndAfter.length-1]);
            }
            else{
                toEdit.setTags(new UniqueTagList(new Tag(args)));;
            }

            internalList.set(editIndex, toEdit);
            return true;

        } else if (keyword.equals(EditCommand.ADD_WORD)) {            
            String[] newTag = args.replaceAll(" ", "").replaceFirst("#", "").split("#");          
            final Set<Tag> tagSet = new HashSet<>();
            for (int i = 0; i < newTag.length; i++) {
                tagSet.add(new Tag(newTag[i]));
            }
            UniqueTagList addTagList = new UniqueTagList(tagSet);            
            toEdit.addTags(addTagList);          
            internalList.set(editIndex, toEdit);
            return true;
        }

        else {
            return false;
        }
    }
    
    public boolean completed(ReadOnlyTask target) {
        int completeIndex = internalList.indexOf(target);
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
    
    public boolean completeTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        int category = target.getTaskCategory();
        if(category == 1){
            if (events.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 2){
            if (deadlines.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else{
            if (todo.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
    }

    public void overdueTask() {
    	for (Task task: events) {
    		events.markOverdue(task);
        	//System.out.println("events:" + task.getOverdue());
    	}
    	for (Task task: deadlines) {
        	deadlines.markOverdue(task);
        	//System.out.println("deadlines:" + task.getOverdue());	
    	}
    }

/*    public boolean removeDeadline(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
=======
    public boolean removeDeadline(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
>>>>>>> code_cleanup
        if (deadlines.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeTodo(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (todo.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }*/
```
###### \java\seedu\address\model\TaskBook.java
``` java
    public boolean changeTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if(category == 'E'){
            if (events.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 'D'){
            if (deadlines.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 'T'){
            if (todo.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        return false;
    }
```
###### \java\seedu\address\model\TaskBook.java
``` java
    public boolean changeDeadline(ReadOnlyTask target, String args) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (deadlines.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }        
    }
    
    public boolean changeTodo(ReadOnlyTask target, String args) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (todo.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }        
    }
```
###### \java\seedu\address\model\TaskBook.java
``` java
    public void changeTaskCategory() throws TaskNotFoundException, DuplicateTaskException {
        for (Task task: events) {
            System.out.println("events:" + task.toString());
            if(task.getTaskCategory()!=1){
                events.remove(task);
                System.out.println("events:" + task.toString());
                if(task.getTaskCategory()==2){
                    deadlines.add(task);
                }
                else if(task.getTaskCategory()==3){
                    todo.add(task);
                }    
            }           
            System.out.println("events:" + task.toString());
        }
        for (Task task: deadlines) {
            if(task.getTaskCategory()!=2){
                deadlines.remove(task);
                if(task.getTaskCategory()==1){
                    events.add(task);
                }
                else if(task.getTaskCategory()==3){
                    todo.add(task);
                }    
            }   
            System.out.println("events:" + task.toString());
        }
        for (Task task: todo) {
            if(task.getTaskCategory()!=3){
                todo.remove(task);
                if(task.getTaskCategory()==1){
                    events.add(task);
                }
                else if(task.getTaskCategory()==2){
                    deadlines.add(task);
                }    
            }    
            System.out.println("events:" + task.toString());
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
