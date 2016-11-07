# A0138862Wunused
###### /java/guitests/ImportCommandTest.java
``` java
    //removed importing of txt file
    /*
    @Test
    public void importtxt_successWithEmptyTaskList(){        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import success: 3 tasks added");
        
        // resulting task list in Mastermind should have 3 items
        this.assertListSize(3);
    }
    
    
```
###### /java/guitests/ImportCommandTest.java
``` java
    @Test
    public void importtxt_partialSuccess_containOtherCommand(){        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/containOtherCommand.txt");
        this.assertResultMessage("Import failure: 1 tasks added \nInvalid lines: 2,3");
        
        // resulting task list in Mastermind should have 3 items
        this.assertListSize(1);
    }
    
```
###### /java/guitests/ImportCommandTest.java
``` java
    //removed importing of txt file
    
    @Test
    public void importtxt_failure_importTwiceCauseDuplicateException(){

        // expect first import successful
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import success: 3 tasks added");
        this.assertListSize(3);

        // expect second import failure
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import failure: 0 tasks added \nInvalid lines: 1,2,3");
        this.assertListSize(3);
    }
     */
}
```
