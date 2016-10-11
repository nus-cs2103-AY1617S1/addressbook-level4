package seedu.todo.controllers;

import seedu.todo.models.TodoListDB;

public class DestroyController implements Controller {

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("delete") || input.startsWith("destroy")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Get index.
        int index = Integer.decode(args.replaceFirst("(delete|destroy)", "").trim());
        
        // TODO: Get record.
        TodoListDB db = TodoListDB.getInstance();
        db.destroyTask(db.getAllTasks().get(index));
    }

}
