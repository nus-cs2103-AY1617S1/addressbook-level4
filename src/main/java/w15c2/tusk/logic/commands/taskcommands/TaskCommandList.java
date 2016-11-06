package w15c2.tusk.logic.commands.taskcommands;

import java.lang.reflect.Field;
import java.util.ArrayList;

import w15c2.tusk.model.HelpGuide;

public class TaskCommandList {
    
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return helpGuideList;
        
    }

}
