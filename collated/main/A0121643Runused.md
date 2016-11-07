# A0121643Runused
###### /java/seedu/todo/logic/parser/ParserFormats.java
``` java
    public static final int FIRST_INDEX = 0;
    public static final String PRIORITY_FORMAT = " priority (?<priority>[^;]+)";
    public static final String ON_DATE_FORMAT = " (on|from) (?<onDateTime>[^;]+)";
    public static final String BY_DATE_FORMAT = " (by|to) (?<byDateTime>[^;]+)";
    public static final String DETAIL_FORMAT = "(?: ?; ?(?<detail>.+))?";
    public static final String NAME_FORMAT = "(?<name>[^/;]+)";
    public static final String RECUR_FORMAT = " every (?<rec>[^;]+)";
            
    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)?");

    public static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    //one or more keywords separated by whitespace
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 
    
    
```
###### /java/seedu/todo/logic/parser/ParserFormats.java
``` java
    public static final Pattern ADD_PRIORITY_FT = Pattern
            .compile(NAME_FORMAT + ON_DATE_FORMAT + BY_DATE_FORMAT 
                    + PRIORITY_FORMAT + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_PRIORITY_ON = Pattern
            .compile(NAME_FORMAT + ON_DATE_FORMAT + PRIORITY_FORMAT 
                    + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_PRIORITY_BY = Pattern
            .compile(NAME_FORMAT + BY_DATE_FORMAT + PRIORITY_FORMAT 
                    + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);
    
    public static final Pattern ADD_PRIORITY_FL = Pattern
            .compile("(?<name>[a-zA-Z_0-9 ]+)" + PRIORITY_FORMAT 
                    + "(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);
    
```
###### /java/seedu/todo/model/task/Priority.java
``` java
    /**
     * higher priority is smaller so that can be shown in front
     */

	public int compareTo(Priority other) {
		if(! this.priorityLevel.equals(other.priorityLevel)) {
			if (this.priorityLevel.equals(HIGH)) {
				return -1;
			} else if (this.priorityLevel.equals(LOW)) {
				return 1;
			} else if (this.priorityLevel.equals(MID) && other.priorityLevel.equals(HIGH)) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}
```
###### /java/seedu/todo/model/task/Task.java
``` java
    /**
     * compares first based on bydate, then on priority, then on name
     * @return dateTimeComparator
     */
    public static Comparator getTaskComparator(){
        return new Comparator<Task>(){
            public int compare (Task t1, Task t2){
                    if (t1.getByDate().compareTo(t2.getByDate()) == 0) {
                    	if (t1.getPriority().compareTo(t2.getPriority()) == 0) {
                    		return t1.getName().toString().compareTo(t2.getName().toString());
                    	} else {
                    		return t1.getPriority().compareTo(t2.getPriority());
                    	}
                    } else {
                    	return t1.getByDate().compareTo(t2.getByDate());
                    }
            }
        };
    }
```
###### /java/seedu/todo/model/task/TaskDate.java
``` java
    /**
     * earlier date is smaller so that it can be shown before task with later date
     */
	public int compareTo(TaskDate other) {
		if (this.getDate() == null && other.getDate() == null) {
			return 0;
		} else if (this.getDate() == null) {
			return 1;
		} else if (other.getDate() == null) {
			return -1;
		} else if (this.getDate().equals(other.getDate())) {
			return this.getTime().compareTo(other.getTime());			
		} else {
			return this.getDate().compareTo(other.getDate());
		}
	}
	
```
