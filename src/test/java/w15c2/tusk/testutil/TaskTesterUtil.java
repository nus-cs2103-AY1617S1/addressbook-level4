package w15c2.tusk.testutil;

import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
/*
 * Utility functions that help with tests that use Tasks
 */
public class TaskTesterUtil {
    public static String getAddCommandFromTask(Task task) {
        // Add description
        StringBuilder command = new StringBuilder("add " + task.getDescription().toString());
        
        if (task instanceof DeadlineTask) {
            command.append(" by " + ((DeadlineTask)task).getDeadline());
        } else if (task instanceof EventTask) {
            command.append(" from " + ((EventTask)task).getStartDate() + " - " + ((EventTask)task).getEndDate());
        }
        
        return command.toString();
    }
}
