package seedu.todo.controllers;

public interface Controller {
    
    public float inputConfidence(String input);
    public void process(String input);

}
