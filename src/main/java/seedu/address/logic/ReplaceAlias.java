package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.model.Alias;
import seedu.address.model.task.InMemoryTaskList;

/**
 * Replaces the shortcut of an alias with its sentence.
 */
public class ReplaceAlias {
	private InMemoryTaskList model;
	
	public ReplaceAlias(InMemoryTaskList model){
		this.model = model;
	}
	
    /** Returns the sentence of an alias shortcut */ 
	public String getAliasCommandText(String commandText)
	{
		String s = commandText;
		ObservableList<Alias> aliasList = model.getAlias();
        if(!aliasList.isEmpty()){
        	for(int i=0; i<aliasList.size(); i++){
        		if(aliasList.get(i).getShortcut().equals(commandText.substring(0, commandText.indexOf(" ")))){
        			s = new String(aliasList.get(i).getSentence());
        			s = s.concat(commandText.substring(commandText.indexOf(" ")));
        			break;
        		}
        	}
        }
        return s;
	}
}
