package seedu.todo.model.property;

import java.util.List;

//@author A0135817B
public class SearchStatus {
    final public List<String> terms; 
    final public int tasksFound;
    final public int tasksTotal;

    public SearchStatus(List<String> terms, int tasksFound, int tasksTotal) {
        this.terms = terms;
        this.tasksFound = tasksFound;
        this.tasksTotal = tasksTotal;
    }
}
