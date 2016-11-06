package w15c2.tusk.logic.commands.taskcommands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;

import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.model.HelpGuide;

//@@author A0139708W
public class TaskCommandList {
    private static final Logger logger = LogsCenter.getLogger(TaskCommandList.class);
    
    public static Class<?>[] getList(){
        
        return new Class[]{
                AddAliasCommand.class, 
                AddTaskCommand.class,
                ClearTaskCommand.class, 
                CompleteTaskCommand.class,
                DeleteAliasCommand.class,
                DeleteTaskCommand.class,
                ExitCommand.class,
                FindTaskCommand.class,
                HelpTaskCommand.class,
                ListTaskCommand.class,
                PinTaskCommand.class,
                RedoTaskCommand.class,
                SetStorageCommand.class, 
                UncompleteTaskCommand.class,
                UndoTaskCommand.class,
                UnpinTaskCommand.class,
                UpdateTaskCommand.class};
    }
    
    public static ArrayList<HelpGuide> getHelpList() {
        ArrayList<HelpGuide> helpGuideList = new ArrayList<HelpGuide>();
        for(Class<?> taskCommand : getList()) {
            try {
                Field argumentsField = taskCommand.getField("COMMAND_FORMAT");
                String arguments = (String) argumentsField.get(null);
                Field commandDescriptionField = taskCommand.getField("COMMAND_DESCRIPTION");
                String commandDescription = (String) commandDescriptionField.get(null);
                helpGuideList.add(new HelpGuide(commandDescription, arguments));

            } catch (NoSuchFieldException e) {
                logger.severe("Error: Non-command class placed into list");
                e.printStackTrace();
            } catch (Exception e) {
                logger.severe("Exception encountered");
                e.printStackTrace();
            }
        }
        return helpGuideList;
        
    }

}
