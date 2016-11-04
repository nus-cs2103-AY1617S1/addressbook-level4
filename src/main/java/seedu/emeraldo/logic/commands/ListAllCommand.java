package seedu.emeraldo.logic.commands;

import seedu.emeraldo.logic.commands.ListCommand.TimePeriod;

//@@author A0139749L
/**
* Lists all tasks in Emeraldo to the user.
*/
public class ListAllCommand extends Command {

	public static final String COMMAND_WORD = "listall";

	public static final String MESSAGE_LIST_ALL = "All tasks are successfully listed!\n";
  
	public static final String MESSAGE_LIST_KEYWORD = "All tasks with tag '%s' successfully listed!\n";
  
	public static final String MESSAGE_LIST_TIMEPERIOD = "All tasks happening %s successfully listed!\n";
  
	public static final String MESSAGE_USAGE = "(1) " + COMMAND_WORD + " :  Lists all tasks\n"
			+ "(2) " + COMMAND_WORD + " [PRE-DEFINED KEYWORDS] :  Lists all tasks in the period specified\n"
			+ "(3) " + COMMAND_WORD + " [KEYWORD] :  Lists all tasks with tags containing the specified keyword\n"
			+ "Example:  " + COMMAND_WORD + "  |  " + COMMAND_WORD + " today  |  " + COMMAND_WORD + " tomorrow"
			+ "  |  " + COMMAND_WORD + " homework";

	private String keyword;
	private String successMessage;
	private TimePeriod timePeriod;
  
	public ListAllCommand(String keyword){
		this.keyword = keyword;
	}
  
	@Override
	public CommandResult execute() {
		if(keyword.isEmpty()){
			model.updateFilteredListToShowAll();
			this.successMessage = MESSAGE_LIST_ALL;
			
      	}else if(keywordSatifiesTimePeriod()){
      		model.updateFilteredTaskListWithCompleted(timePeriod);
      		this.successMessage = String.format(MESSAGE_LIST_TIMEPERIOD, keyword);
      	
      	}else{
      		model.updateFilteredTaskListWithCompleted(keyword);
      		this.successMessage = String.format(MESSAGE_LIST_KEYWORD, keyword.toLowerCase())
      				+ getMessageForTaskListShownSummary(model.getFilteredTaskList().size());
      	}
		return new CommandResult(successMessage);
	}
  
	private boolean keywordSatifiesTimePeriod(){
		boolean result;
		switch(this.keyword.toLowerCase()){
  			case "today":
  				this.timePeriod = TimePeriod.today;    			
  				result = true;
  				break;
  			case "tomorrow":
  				this.timePeriod = TimePeriod.tomorrow;
  				result = true;
  				break;
  			case "thisweek":
  				this.timePeriod = TimePeriod.thisWeek;
  				this.keyword = "this week";
  				result = true;
  				break;
  			case "nextweek":
  				this.timePeriod = TimePeriod.nextWeek;
  				this.keyword = "next week";
  				result = true;
  				break;
  			case "thismonth":
  				this.timePeriod = TimePeriod.thisMonth;
  				this.keyword = "this month";
  				result = true;
  				break;
  			case "nextmonth":
  				this.timePeriod = TimePeriod.nextMonth;
  				this.keyword = "next month";
  				result = true;
  				break;
  			default:
  				result = false;
		}
		return result;
	}
  
}
