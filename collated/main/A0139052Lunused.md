# A0139052Lunused
###### \java\seedu\taskitty\model\ModelManager.java
``` java
// swap over to different undo function
//
// * returns true is there is a previous valid command input by user
// * and false otherwise
// */
// private boolean hasPreviousValidCommand() {
// return !historyCommands.isEmpty();
// }
//
//
// /**
// * returns the Task Manager from the previous state
// */
// private ReadOnlyTaskManager getPreviousTaskManager() {
// return historyTaskManagers.pop();
// }
//
// /**
// * returns the Predicate from the previous state
// */
// private Predicate getPreviousPredicate() {
// return historyPredicates.pop();
// }
//
// /**
// * returns the previous valid command input by the user
// */
// private String getPreviousValidCommand() {
// return historyCommands.pop();
// }
//
// public synchronized void saveState(String command) {
// historyTaskManagers.push(new TaskManager(taskManager));
// historyCommands.push(command);
// historyPredicates.push(filteredTodos.getPredicate());
// }
//
// public synchronized void removeUnchangedState() {
// historyTaskManagers.pop();
// historyCommands.pop();
// historyPredicates.pop();
// }
//
// public synchronized String undo() throws NoPreviousValidCommandException {
// if (!hasPreviousValidCommand()) {
// throw new NoPreviousValidCommandException(null);
// }
// assert !historyPredicates.isEmpty() && !historyTaskManagers.isEmpty();
// resetData(getPreviousTaskManager());
// updateFilteredTaskList(getPreviousPredicate());
// return getPreviousValidCommand();
// }
```
