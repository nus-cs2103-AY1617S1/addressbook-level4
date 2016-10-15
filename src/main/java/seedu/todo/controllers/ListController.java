package seedu.todo.controllers;

import java.util.HashMap;
import java.util.Map;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class ListController implements Controller {
    
    private static String NAME = "List";
    private static String DESCRIPTION = "Lists all tasks and events.";
    private static String COMMAND_SYNTAX = "list";
    
    private static final String MESSAGE_LISTING_SUCCESS = "Listing a total of %d %s and %d %s.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.startsWith("list")) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"list"});
        tokenDefinitions.put("eventType", new String[] { "event", "task"});
        tokenDefinitions.put("status", new String[] { "complete", "uncomplete"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) {
        
        Map<String, String[]> parsedResult;
        try {
            parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        } catch (UnmatchedQuotesException e) {
            System.out.println("Unmatched quote!");
            return ;
        }
        
        // Task or event?
        boolean isTask = true;
        boolean isEvent = true;
        boolean listAll = true;
        boolean isCompleted = true;
        
        for(int i = 0; i <parsedResult.get("eventType").length; i ++){
            System.out.println(parsedResult.get("eventType")[i]);
            //System.out.println(parsedResult.get("status")[0]);
        }
        
        //check if required to list all or just task or event
        if (parsedResult.get("eventType") != null) {
            if (parsedResult.get("eventType")[0].equals("event")) {
                isTask = false;
            } else {
                isEvent = false;
            }
        }
        
        //check if required to list only completed or uncomplete
        if (parsedResult.get("stauts") != null) {
            listAll = false;
            
        } 

        
        // Name - Disambiguate if null.
//        String name = null;
//        if (parsedResult.get("default") != null && parsedResult.get("default")[1] != null)
//            name = parsedResult.get("default")[1];
//        if (parsedResult.get("eventType") != null && parsedResult.get("eventType")[1] != null)
//            name = parsedResult.get("eventType")[1];
//        if (name == null) {
//            renderDisambiguation(parsedResult);
//            return;
//        }
        
        // old process
//        TodoListDB db = TodoListDB.getInstance();
//        
//        // Render
//        IndexView view = UiManager.loadView(IndexView.class);
//        view.tasks = db.getAllTasks();
//        view.events = db.getAllEvents();
//        UiManager.renderView(view);
//
//        // Update console message
//        int numTasks = db.getAllTasks().size();
//        int numEvents = db.getAllEvents().size();
//        String consoleMessage = String.format(MESSAGE_LISTING_SUCCESS,
//                numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"),
//                numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
//        UiManager.updateConsoleMessage(consoleMessage);
    }

}
