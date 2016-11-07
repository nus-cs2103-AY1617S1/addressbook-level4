package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.IncorrectCommand;


//@@author A0144702N
/**
 * Parser to prepare FindCommand
 * @author xuchen
 *
 */
public class FindParser implements Parser {
	/**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command prepare(String args) {
		if(args.isEmpty()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
				FindCommand.MESSAGE_USAGE));
		}
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(powerSearchPrefix, findKeywordPrefix);
		argsTokenizer.tokenize(args.trim());
		
		Optional<String> keyword = Optional.empty();
		Optional<List<String>> moreKeywords = Optional.empty();
		boolean isPowerSearch = false;
		
		moreKeywords = argsTokenizer.getAllValues(findKeywordPrefix);
		isPowerSearch = argsTokenizer.hasPrefix(powerSearchPrefix);
		keyword = argsTokenizer.getPreambleAllowEmpty();
		
		final Set<String> keywordSet = combineKeywords(keyword, moreKeywords);
		return new FindCommand(keywordSet, isPowerSearch);
	}

	private Set<String> combineKeywords(Optional<String> keyword, Optional<List<String>> moreKeywords) {
		List<String> keywordList = moreKeywords.orElse(new ArrayList<String>());
		
		if(keyword.isPresent()) {
			keywordList.add(keyword.get());
		}
		return new HashSet<>(keywordList);
	}
}
