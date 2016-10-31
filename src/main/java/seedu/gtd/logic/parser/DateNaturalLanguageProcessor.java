package seedu.gtd.logic.parser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

//@@author A0146130W
public class DateNaturalLanguageProcessor implements NaturalLanguageProcessor {
	
	private static final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
	
	@Override
	public String formatString(String naturalLanguageDate) {
		List<DateGroup> dateGroups = parser.parse(naturalLanguageDate);
		return refineDateGroupList(dateGroups);
	}
	
	/** Does not tolerate alternative dates */
	private String refineDateGroupList(List<DateGroup> groups) {
	  if(groups.size() == 1) {
		  return formatDateToString(groups.get(0).getDates().get(0));
	  }
	  else return "";
	}
	
	private String formatDateToString(Date date) {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(date);
	}
}