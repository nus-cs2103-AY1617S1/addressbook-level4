package seedu.tasklist.logic.parser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//@@author A0146107M
public class ArgumentParser {
	private HashMap<String, String> subArgs;
	
	
	public ArgumentParser(String input, String[] keywords){
		subArgs = new HashMap<String, String>();
		HashMap<String, Integer> locales = new HashMap<String, Integer>();
		String sequence = input;
		subArgs.put("default", "");
		locales.put("default", 0);
		for(String keyword: keywords){
			subArgs.put(keyword.trim(), "");
			locales.put(keyword, Integer.MAX_VALUE);
		}
		parse(locales, sequence);
	}
	
	public String getField(String arg){
		return (subArgs.get(arg)=="")?null:subArgs.get(arg);
	}
	
	public String getField(){
		return getDefault();
	}
	
	public String getEitherField(String...args){
		String result = null;
		for(String arg: args){
			if(getField(arg)!=null){
				result = getField(arg);
			}
		}
		return result;
	}
	
	public String getDefault(){
		return (subArgs.get("default")=="")?null:subArgs.get("default");
	}
	
	public static Integer extractIndex(String input){
		try{
			return Integer.valueOf(input.split(" ")[0]);
		}
		catch(NumberFormatException nfe){
			return null;
		}
	}
	
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
	
	public static String cutIndex(String input){
		String result = "";
		String[] tokens = input.split(" ");
		for(int i=1; i<tokens.length; i++){
			result += tokens[i] + " ";
		}
		return result.trim();
	}
	
	private void parse(HashMap<String, Integer> locales, String input){
		for(int i=0; i<input.length(); i++){
			for(String arg: locales.keySet()){
				if(isArg(i, arg, input)){
					locales.put(arg, i);
					input = input.replaceFirst(arg, "");
				}
			}
		}
		getArgsFromLocales(locales, input);
	}
	
	private void getArgsFromLocales(HashMap<String, Integer> locales, String input){
		ArrayList<Map.Entry<String, Integer>> locs = new ArrayList<Map.Entry<String, Integer>>();
		for(Map.Entry<String, Integer> locale: locales.entrySet()){
			if(locale.getValue()!=Integer.MAX_VALUE){
				locs.add(locale);
			}
		}
		Collections.sort(locs, (a,b)->{return a.getValue()-b.getValue();});
		Iterator<Map.Entry<String, Integer>> iter = locs.iterator();
		
		Map.Entry<String, Integer> next = iter.next();
		int start, end;
		String arg;

		do{
			start = next.getValue();
			arg = next.getKey();
			if(iter.hasNext()){
				next = iter.next();
				end = next.getValue();
			}
			else{
				end = input.length();
			}
			subArgs.put(arg.trim(), input.substring(start, end));
		} while(end != input.length());
		
	}
	
	private boolean isArg(int index, String arg, String input){
		if(index<0 || index>=input.length()){
			return false;
		}
		else{
			return input.substring(index).startsWith(arg);
		}
	}
}
