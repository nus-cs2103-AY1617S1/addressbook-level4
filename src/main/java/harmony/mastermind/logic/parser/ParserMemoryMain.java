package harmony.mastermind.logic.parser;

import java.util.Calendar;

public class ParserMemoryMain { 
    
    private static final String STRING_AND = "and";
    private static final String STRING_ON = "on";
    private static final String STRING_FROM = "from";
    private static final String STRING_TO = "to";
    private static final String STRING_BETWEEN = "between";
    private static final String STRING_BEFORE = "before";
    private static final String STRING_TILL = "till";
    private static final String STRING_UNTIL = "until";
    private static final String STRING_BY = "by";
    private static final String STRING_DEADLINE = "deadline";
    
    //Commands for start date
    protected static final int BETWEEN = 13;
    protected static final int ON = 12;
    protected static final int FROM = 11;
    
    //Commands for end date
    protected static final int AND = 7;
    protected static final int TILL = 6;
    protected static final int TO = 5;
    protected static final int BEFORE = 4;
    protected static final int UNTIL = 3;
    protected static final int BY = 2;
    protected static final int DEADLINE = 1;
    
    public static String command; 
    protected static String taskName; 
    protected static String description; 
    
    protected static final int INVALID_INT = -1;
    
    protected static int type;
    protected static int length; 
    
    public static boolean containsDescription; 
    public static boolean setProper;
    
    private static int day;
    private static int month; 
    private static int year;
    
    private static final int INT_INVALID = -1;
    private static final int INVALID_STRING = 0;

    private static final String INVALID_DATE_TIME = "Invalid date/time: ";
    private static final String INVALID_COMMAND = "Invalid command, please try again";
    
    //@@author A0143378Y
    /*
     * General getters and setters
     */
    public static String getCommand() { 
        return command;
    }
    
    //@@author A0143378Y
    public static void setCommand(String newCommand) { 
        command = newCommand;
    }
    
    //@@author A0143378Y
    public static void setTaskName(String newName) { 
        taskName = newName;
    }
    
    //@@author A0143378Y
    public static String getTaskName() { 
        return taskName;
    }
    
    //@@author A0143378Y
    public static void setDescription(String newDescription) { 
        description = newDescription;
    }
    
    //@@author A0143378Y
    public static String getDescription() { 
        return description;
    }
    
    //@@author A0143378Y 
    public static void setLength(int newLength) { 
        length = newLength;
    }
    
    //@@author A0143378Y
    public static int getLength() { 
        return length;
    }
    
    //@@author A0143378Y
    public static void setType(int newType) {
        type = newType;
    }
    
    //@@author A0143378Y 
    public static int getType() { 
        return type;
    }
    
    //@@author A0143378Y
    public static void setContainsDescription(boolean cd) { 
        containsDescription = cd;
    }
    
    //@@author A0143378Y
    public static void setProper(boolean sp) { 
        setProper = sp;
    }
    
    //@@author A0143378Y
    /*
     * Set date to a calendar object setEvent
     * If set, return true.
     */
    public static boolean setDate(String date, Calendar setEvent) { 
        boolean isValid = false;
        
        initialiseDate();
        getDate(date);
        
        isValid = setDateIfContainDDMMYY(day, month, year, setEvent);
        
        return isValid;
    }
    
    //@@author A0143378Y
    public static boolean setTime(String time, Calendar setEvent) { 
        int newTime = INT_INVALID;
        int hour = 23, minute = 59; 
        boolean isValid = false;
        
        String timeReduced = reduceToInt(time);
        newTime = convertToInt(timeReduced);
        
        //Checks that time string has exactly 4 digit.
        if(timeReduced.length() == 4 && newTime != INVALID_INT){
            minute = newTime%100;
            hour = newTime/100;
        }
        
        //Checks that the time set is valid
        if(!(invalidMinute(minute)||invalidHour(hour)||timeReduced.length()!=4)){
            isValid = setTimeIfHHMMSS(hour, minute, setEvent);
        }
        
        return isValid;
    }
    
    //@@author A0143378Y
    /*
     * Check the format the date is in
     * dd/mm/yy or dd-mm-yy and parse accordingly
     */
    protected static void getDate(String date){
        
        if(date.contains("/")){
            getInt("/", date);
            
        }else if(date.contains("-")){
            getInt("-", date);
            
        }
    }
    
    //@@author A0143378Y
    /*
     * Parse the date string with the symbol "/" or "-"
     */
    protected static void getInt(String symbol, String date){
        
        String[] details = date.split(symbol);
        
        boolean dateIsNumeric = true;
        
        //Check that date has all 3 component: day, month and year
        if(details.length == 3){
            dateIsNumeric = checkIfDateIsNumeric(details);
        }
            
        if(dateIsNumeric){
            setDDMMYY(details);
        }
    }
    
    //@@author A0143378Y
    /*
     * Returns true if user command is an empty string or contains symbols only
     */
    public static Boolean isUselessCommand(String input){

        if(reduceToIntAndChar(input).length()==0){
            return true;
        }else{
            return false;
        }
    }
    
    //@@author A0143378Y
    /*
     * Returns true if end date and time is before start date and time
     */
    protected static Boolean endIsBeforeStart(Calendar start, Calendar end){
        return end.before(start);
    }
    
    //@@author A0143378Y
    /*
     * Checks if string contains number only
     * Returns true if it does
     */
    protected static boolean isNumeric(String temp){
        try{
            Integer.parseInt(temp);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    //@@author A0143378Y
    /*
     * Prints error message and set setProper as false
     */
    protected static void generalError(){
        System.out.println(INVALID_COMMAND);
        setProper(false);
    }
    
    //@@author A0143378Y
    protected static String removeAllInt(String name){
        return name.replaceAll("[0-9 ]", "");
    }
    
    //@@author A0143378Y
    public static String reduceToInt(String name){
        return name.replaceAll("[^0-9]", "");
    }
    
    //@@author A0143378Y
    public static String reduceToIntAndChar(String name){
        return name.replaceAll("[^a-zA-Z0-9]", "");
    }   
    
    //@@author A0143378Y
    protected static String reduceToChar(String name){
        return name.replaceAll("[^a-zA-Z]", "");
    }   
    
    //@@author A0143378Y
    /*
     * set dates
     * If day and month are appropriate, returns true
     */
    private static boolean setDateIfContainDDMMYY(int day, int mth, int yr, Calendar setEvent){
        int year = 2000 + yr;
        int month = mth -1;
        if(!(invalidMonth(month)||invalidDay(day))){
            setEvent.set(Calendar.DATE, day);
            setEvent.set(Calendar.MONTH, month);
            setEvent.set(Calendar.YEAR, year);
            return true;
        }else{
            return false;
        }
    }
    
    //@@author A0143378Y
    /*
     * Converts string to integer
     * If string is empty or contains non-number, print error message.
     */
    private static int convertToInt(String value){
        int i = INVALID_INT;
        try{
            i= Integer.parseInt(value);
        }catch(NumberFormatException e){
            System.err.println(INVALID_DATE_TIME+ e.getMessage());     
        }
        return i;
    }
    
    //@@author A0143378Y
    /*
     * Month is invalid if
     * Month is less than 0
     * Month is greater than 11
     */
    private static boolean invalidMonth(int month){
        return (month<0||month>11);
    }
    
    //@@author A0143378Y
    /*
     * returns true is day is 0 or greater than 31 
     */
    private static boolean invalidDay(int day){
        return (day<=0||day>31);
    }
    
    //@@author A0143378Y
    /*
     * returns true if time is set properly
     */
    private static boolean setTimeIfHHMMSS(int hour, int minute, Calendar setEvent){
        setEvent.set(Calendar.HOUR_OF_DAY, hour);
        setEvent.set(Calendar.MINUTE, minute);
        if(hour == 23 && minute == 59){
            setEvent.set(Calendar.SECOND, 59);
        }else{
            setEvent.set(Calendar.SECOND, 0);
        }
        return true;
    }
    
    //@@author A0143378Y
    /*
     * returns true if minute is negative or more than 59
     */
    private static boolean invalidMinute(int minute){
        return (minute<0||minute>=60);
    }
    
    //@@author A0143378Y
    /*
     * returns true if hour is negative or more than 23
     */
    private static boolean invalidHour(int hour){
        return (hour<0||hour>=24);
    }
    
    //@@author A0143378Y
    /*
     * Check that the word is a command word for date
     */
    protected static int isCommandWord(String word){
        switch(word){
        case STRING_DEADLINE:
            return DEADLINE;
        case STRING_BY:
            return BY;
        case STRING_UNTIL:
            return UNTIL;
        case STRING_TILL:
            return TILL;
        case STRING_BEFORE:
            return BEFORE;
        case STRING_BETWEEN:
            return BETWEEN;
        case STRING_TO:
            return TO;
        case STRING_FROM:
            return FROM;
        case STRING_ON:
            return ON;
        case STRING_AND:
            return AND;
            default:
                return INVALID_STRING;   
        }
    }
    
    //@@author A0143378Y
    /*
     * remove additional space between each word in case of typo
     */
    protected static void removeAdditionalSpacesInCommand(){
        String[] temp = command.split(" ");
        String newCommand = "";
        for (int i = 0; i < temp.length; i++){
            if(temp[i].length() != 0){
                newCommand = newCommand + temp[i] + " ";
            }
        }
        setCommand(newCommand.trim());
    }
    
    //@@author A0143378Y
    /*
     * Returns true if the command word is a command for start date
     */
    protected static boolean isStartCommand(String word){
        return (isCommandWord(word)>= FROM &&
                isCommandWord(word)<= BETWEEN);
    }
    
    //@@author A0143378Y
    /*
     * Returns true if the command word is a command for end date
     */
    protected static boolean isEndCommand(String word){
        return (isCommandWord(word)>= BY &&
                isCommandWord(word)<= AND);
    }
    
    //author A0143378Y
    private static void setDDMMYY(String[] details){
        day = Integer.parseInt(details[0]);
        month = Integer.parseInt(details[1]);
        year = Integer.parseInt(details[2]);
    }
    
    //@@author A0143378Y
    private static void initialiseDate(){
        day = INT_INVALID;
        month = INT_INVALID;
        year = INT_INVALID;
    }
    
    //@@author A0143378Y
    private static boolean checkIfDateIsNumeric(String[] details){
        for(int i = 0; i < 3; i++ ){
            if(!isNumeric(details[i])){
                return false;
            }
        }
        
        return true;
    }
}