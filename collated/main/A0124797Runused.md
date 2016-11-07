# A0124797Runused
###### /java/harmony/mastermind/model/ModelManager.java
``` java
    //remove the use of importing txt file
    @Override
    public synchronized BufferedReader importFile(String fileToImport) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileToImport));
        
        return br;
    }
    
```
###### /java/harmony/mastermind/logic/LogicManager.java
``` java
    // disable the use of importing txt file
    /**
     * parse the result of commands and handle ImportCommand separately
     */
    /*
    private CommandResult parseResult(Command cmd) {
        CommandResult result = cmd.execute();
        if (result.feedbackToUser.equals(ImportCommand.MESSAGE_READ_SUCCESS)) {
            result = handleImport((ImportCommand) cmd);
        }

        return result;
    }
    
    /**
     * handle the inputs from the reading of file from ImportCommand
     */
    /*
    public CommandResult handleImport(ImportCommand command) {
        ArrayList<String> lstOfCmd = command.getTaskToAdd();
        String errLines = "";
        int errCount = 0;
        int lineCounter = 0;
        
        for (String cmd: lstOfCmd) {
            lineCounter += 1;
            Command cmdResult = parser.parseCommand(cmd);
            cmdResult.setData(model, storage);
            CommandResult addResult = cmdResult.execute();
            boolean isFailure = isAddFailure(addResult);
            
            if (isFailure) {
                errCount += 1;
                errLines += Integer.toString(lineCounter) + ",";
            }
        }
        
        int addCount = lineCounter - errCount;
        if (errLines.isEmpty()) {
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(ImportCommand.MESSAGE_IMPORT_TXT_SUCCESS, addCount));
        } else {
            errLines = errLines.substring(0,errLines.length()-1);
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(ImportCommand.MESSAGE_IMPORT_TXT_FAILURE, addCount, errLines));
        }
        
    }
    */
    
    
    /**
     * Checks if adding of tasks from ImportCommand is valid
     */
    /*
    private boolean isAddFailure(CommandResult cmdResult) {
        if (cmdResult.toString().substring(MESSAGE_START_INDEX, MESSAGE_END_INDEX).equals(AddCommand.MESSAGE_SUCCESS.substring(MESSAGE_START_INDEX, MESSAGE_END_INDEX))) {
            return false;
        }
        
        return true;
    }
    */
}
```
###### /java/harmony/mastermind/logic/commands/ImportCommand.java
``` java
    // find that it does not make sense to import a txt file of commands,
    // instead user could have 
    /*
    private CommandResult importTxtFile() {
        try {
            BufferedReader br = model.importFile(fileToImport);
            
            String line;
            
            while ((line = br.readLine()) != null) {
                lstOfCmd.add(line);
            }
            
        } catch (IOException e) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_READ_FAILURE, fileToImport));
        }
        
        return new CommandResult(COMMAND_WORD, MESSAGE_READ_SUCCESS);
    }*/


```
