package seedu.address.logic.parser;

public class CommandParserList {
	public static Class<?>[] getList(){
		return new Class[]{AddCommandParser.class, DeleteCommandParser.class, TaskCommandsParser.class};
	}
}
