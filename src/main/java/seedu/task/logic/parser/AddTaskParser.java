package seedu.task.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ModelManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.DateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.Priority;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.Venue;

public class AddTaskParser {

	public static final String SPLIT_STRING_BY_WHITESPACE = "\\s+";
	public static final String PREFIX_HASHTAG = "#";
	public static final String PREFIX_AT = "@";
	public static final String WORD_AT = "at ";
	public static final String RESERVED_WORD_FROM = "FROM ";
	public static final String RESERVED_WORD_BY = "BY ";

	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
	private Task task;
	private String input;

	public AddTaskParser(String input) {
		this.input = input;
		this.task = new Task();
	};

	public Task parseInput() throws IllegalValueException {
		input = tagIdentification(input);
		input = processDateNLP(input);
		processTaskName(input);
		
		logger.info("name: " + task.getName() + ", startDate: " + task.getStartDate() + ", endDate: " + task.getEndDate() + " priority: " + task.getPriority());
		task.getTags().forEach(tag -> logger.info("Tag: " + tag.toString()));
		
		return task;
	}

	private String tagIdentification(String str) throws DuplicateTagException, IllegalValueException {
		String[] parts = str.split(SPLIT_STRING_BY_WHITESPACE);
		String strWithNoTags = "";
		for (String part : parts) {
			if (part.startsWith(PREFIX_HASHTAG))
				matchTag(part.substring(1).trim());
			else if (part.startsWith(PREFIX_AT)) {
				//task.setVenue(new Venue(String.join(" ", task.getVenue().toString(), part.substring(1))));
				task.setVenue(new Venue(part.substring(1).trim()));
				strWithNoTags = String.join(" ", strWithNoTags, WORD_AT + part.substring(1).trim());
			} else
				strWithNoTags = String.join(" ", strWithNoTags, part.trim());
		}
		return strWithNoTags;
	}

	private void matchTag(String str) throws DuplicateTagException, IllegalValueException {
		if (EnumUtils.isValidEnum(Priority.class, str.toUpperCase()))
			task.setPriority(Priority.valueOf(str.toUpperCase()));
		else
			task.addTag(new Tag(str));
	}

	private String processDateNLP(String str) throws IllegalValueException {
		Parser parser = new Parser();
		List<DateGroup> groups = new ArrayList<>();
		List<Date> dates = new ArrayList<>();

		groups = parser.parse(str);
		for (DateGroup group : groups) {
			dates.addAll(group.getDates());
			String matchingValue = group.getText();
			str = removeDateWords(str.toUpperCase(), matchingValue.toUpperCase());
			//str = str.replace(matchingValue, "");
			logger.info(" Str: " + str + " dates " + Arrays.toString(dates.toArray()) + " matchingValue: " + matchingValue);
		}

		Collections.sort(dates);
		setTaskDates(dates);
		return str;
	}
	
	private String removeDateWords(String str, String matchingValue) {
		str = str.replace(RESERVED_WORD_FROM + matchingValue, "");
		str = str.replace(RESERVED_WORD_BY + matchingValue, "");
		str = str.replace(matchingValue, "");
		return StringUtils.capitalize(StringUtils.lowerCase(str)).trim();
	}
	

	// Need to Redo
	/*private ArrayList<Date> dateValidation(List<Date> dates, List<Date> groupDates) {
		// if groupDates > 2 return exception invalid Date Command
		switch (dates.size()) {
		case 0:
			dates.addAll(groupDates);
			break;
		case 1:
			dates.addAll(groupDates.stream().map(s -> new Date(dates.get(0).getTime() + s.getTime()))
			        .collect(Collectors.toList()));
			break;
		case 2:
			// if groupDates > 1 return exception invalid Date Command
			dates.addAll(dates.stream().map(s -> new Date(groupDates.get(0).getTime() + s.getTime()))
			        .collect(Collectors.toList()));
			break;
		default:
		}
		return (ArrayList<Date>) dates;
	}*/

	private void setTaskDates(List<Date> dates) throws IllegalValueException {
		if (dates.size() == 1) {
			task.setEndDate(new DateTime(dates.get(0)));
		} else if (dates.size() >= 2) {
			task.setStartDate(new DateTime(dates.get(0)));
			task.setEndDate(new DateTime(dates.get(1)));
		}
	}

	private void processTaskName(String str) throws IllegalValueException {
		// If have time, do some NLG
		task.setName(new Name(str));
	}
}
