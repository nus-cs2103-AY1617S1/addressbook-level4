package seedu.tasklist.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//@@author A0146107M
/**
 * Parses an input string to extract arguments.
 * 
 */
public class ArgumentParser {
	private HashMap<String, String> subArgs;

	/**
     * Constructor
     * 
     * @param	input user input
     * @param	keywords array of keywords
     */
	public ArgumentParser(String input, String[] keywords){
		//Initialise data structures
		subArgs = new HashMap<String, String>();
		HashMap<String, Integer> locales = new HashMap<String, Integer>();
		subArgs.put("default", "");
		locales.put("default", 0);
		//insert keywords into hashmaps
		for(String keyword: keywords){
			subArgs.put(keyword.trim(), "");
			//max value indicates not found
			locales.put(keyword, Integer.MAX_VALUE);
		}
		//parse input
		parse(locales, input);
	}

	/**
     * Get argument with a specific keyword
     * 
     * @param	arg keyword to be checked
     * @returns argument associated with keyword, null if none exists
     */
	public String getField(String arg){
		return (subArgs.get(arg)=="")?null:subArgs.get(arg);
	}

	/**
     * Get default argument
     * 
     * @returns the default argument, null if none exists
     */
	public String getField(){
		return getDefault();
	}

	/**
     * Get argument associated with keyword group
     * 
     * @returns the argument associated with keyword group, null if none exists
     */
	public String getEitherField(String...args){
		String result = null;
		for(String arg: args){
			if(getField(arg)!=null){
				result = getField(arg);
			}
		}
		return result;
	}

	/**
     * Get default argument
     * 
     * @returns the default argument, null if none exists
     */
	public String getDefault(){
		return (subArgs.get("default")=="")?null:subArgs.get("default");
	}

	/**
     * Extract index from input string
     * 
     * @returns the integer that starts the input string, null if none exists
     */
	public static Integer extractIndex(String input){
		try{
			return Integer.valueOf(input.split(" ")[0]);
		}
		catch(NumberFormatException nfe){
			return null;
		}
	}

	/**
     * Extract index from input string
     * 
     * @returns the integer that starts the input string, null if none exists, or if string contains other arguments
     */
	public static Integer extractIndexBlocking(String input){
		if (input.split(" ").length>1){
			return null;
		}
		else{
			try{
				return Integer.valueOf(input.split(" ")[0]);
			}
			catch(NumberFormatException nfe){
				return null;
			}
		}
	}

	/**
     * Remove index from input string
     * 
     * @param input A string starting with an index
     * @returns The input string, less the index
     */
	public static String cutIndex(String input){
		String result = "";
		//split by spaces
		String[] tokens = input.split(" ");
		//remove first token (the index)
		for(int i=1; i<tokens.length; i++){
			result += tokens[i] + " ";
		}
		return result.trim();
	}

	/**
     * Retrieves arguments from input string and puts them into subArgs
     * 
     */
	private void parse(HashMap<String, Integer> locales, String input){
		//iterate by character
		for(int i=0; i<input.length(); i++){
			//check all keywords
			for(String arg: locales.keySet()){
				//check if keyword exists at character index i
				if(isArg(i, arg, input)){
					//insert into locales
					locales.put(arg, i);
					//remove keyword from string
					input = input.replaceFirst(arg, "");
				}
			}
		}
		getArgsFromLocales(locales, input);
	}

	/**
     * Retrieves arguments from input string given locales
     * 
     */
	private void getArgsFromLocales(HashMap<String, Integer> locales, String input){
		ArrayList<Map.Entry<String, Integer>> locs = new ArrayList<Map.Entry<String, Integer>>();
		addArgLocales(locales, locs);
		Collections.sort(locs, (a,b)->{return a.getValue()-b.getValue();});

		retrieveArgs(locs, input);
	}

	/**
     * Helper method for getArgsFromLocales
     * 
     */
	private void retrieveArgs(ArrayList<Map.Entry<String, Integer>> locs, String input){
		Iterator<Map.Entry<String, Integer>> iter = locs.iterator();
		//get first item
		Map.Entry<String, Integer> next = iter.next();
		int start, end;
		String arg;

		do{
			//starts at index of current keyword
			start = next.getValue();
			//get keyword
			arg = next.getKey();
			if(iter.hasNext()){
				next = iter.next();
				//ends at index of next keyword
				end = next.getValue();
			}
			else{
				//ends at end of string
				end = input.length();
			}
			//stores argument
			subArgs.put(arg.trim(), input.substring(start, end));
		} while(end != input.length());
	}

	/**
     * Adds all present keywords into locs
     * 
     */
	private void addArgLocales(HashMap<String, Integer> locales, ArrayList<Map.Entry<String, Integer>> locs){
		for(Map.Entry<String, Integer> locale: locales.entrySet()){
			//check if keyword is present
			if(locale.getValue()!=Integer.MAX_VALUE){
				locs.add(locale);
			}
		}
	}

	/**
     * Check if keyword starts the input string
     * 
     * @return true if keyword starts the input string
     */
	private boolean isArg(int index, String arg, String input){
		if(index<0 || index>=input.length()){
			return false;
		}
		else{
			return input.substring(index).startsWith(arg);
		}
	}
}
