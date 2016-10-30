package harmony.mastermind.logic.commands;

import java.util.ArrayList;

import harmony.mastermind.memory.GenericMemory;

public class Sort { 
    private static ArrayList<GenericMemory> task;
    private static ArrayList<GenericMemory> deadline;
    private static ArrayList<GenericMemory> event; 
    
    //@@author A0143378Y
    /* Sort all items in memory by urgency.
     * Items arranged by Event, Deadline, Task
     * Event sorted by earliest start date followed by end date
     * Deadline sorted by earliest due date
     * Task sorted by alphabet 
     */
    public static void sort(ArrayList<GenericMemory> list) { 
        task = new ArrayList<GenericMemory>();
        deadline = new ArrayList<GenericMemory>();
        event = new ArrayList<GenericMemory>();
        splitMemory(list);
    }
    
    //@@author A0143378Y
    //Split all items into their own list for sorting
    private static void splitMemory(ArrayList<GenericMemory> list) { 
        for (int i = 0; i < list.size(); i++) { 
            switch (list.get(i).getType()) { 
            case "Task":
                task.add(list.get(i));
                break;
                
            case "Deadline":
                deadline.add(list.get(i));
                break;
                
            case "Event":
                event.add(list.get(i));
                break;
            }
        }
    }
}