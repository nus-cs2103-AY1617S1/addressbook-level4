package seedu.address.logic.parser;

import java.lang.reflect.Field;

public class ParserType {
		
	private static final Class<?>[] parserTypes = new Class[]{AddCommandParser.class, DeleteCommandParser.class};
	
	public static CommandParser get(String commandWord){
		for(int i=0; i<parserTypes.length; i++){
			try {
				Field type = parserTypes[i].getField("COMMAND_WORD");
				if(type.get(null).equals(commandWord)){
					return (CommandParser)parserTypes[i].newInstance();
				}
			} 
			catch (NoSuchFieldException e) {
				return new IncorrectCommandParser();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new IncorrectCommandParser();
		
	}
}
