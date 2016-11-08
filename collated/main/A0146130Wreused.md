# A0146130Wreused
###### /seedu/gtd/logic/parser/Parser.java
``` java
    private String extractDetailType(String args) {
    	String preprocessedArgs = " " + appendEnd(args.trim());
        final Matcher dueDateMatcher = DUEDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
        final Matcher addressMatcher = ADDRESS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
        final Matcher priorityMatcher = PRIORITY_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	
    	if(addressMatcher.matches()) {
    		return "address";
    	}
    	else if(dueDateMatcher.matches()) {
    		return "dueDate";
    	}
    	else if(priorityMatcher.matches()) {
    		return "priority";
    	}
    	
    	return "name";
    }
    
```
