# A0147944Ureused
###### \java\seedu\task\logic\LogicManager.java
``` java
        if (command instanceof IncorrectCommand) {
            return command.execute(false);
        }
```
###### \java\seedu\task\logic\parser\CommandParser.java
``` java
        case DirectoryCommand.COMMAND_WORD:
            return prepareDirectory(arguments);

        case DirectoryCommand.COMMAND_WORD_ALT:
            return prepareDirectory(arguments);

        case BackupCommand.COMMAND_WORD:
            return prepareBackup(arguments);

        case BackupCommand.COMMAND_WORD_ALT:
            return prepareBackup(arguments);

        case SortCommand.COMMAND_WORD:
            return prepareSort(arguments);
```
