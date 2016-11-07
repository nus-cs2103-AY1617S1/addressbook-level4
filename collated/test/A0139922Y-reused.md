# A0139922Y-reused
###### /java/seedu/todo/guitests/FindCommandTest.java
``` java
    @Before
    public void initFixtures() {
        console.runCommand("clear");
        assertTaskVisibleAfterCmd(commandAdd1, task1);
        assertTaskVisibleAfterCmd(commandAdd2, task2);
        assertEventVisibleAfterCmd(commandAdd3, event3);
        assertEventVisibleAfterCmd(commandAdd4, event4);
    }
    
```
###### /java/seedu/todo/guitests/FindCommandTest.java
``` java
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskNotVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventNotVisibleAfterCmd("list", event4);
    }
```
###### /java/seedu/todo/guitests/ListCommandTest.java
``` java
    @Before
    public void initFixtures() {
        console.runCommand("clear");
        assertTaskVisibleAfterCmd(commandAdd1, task1);
        assertTaskVisibleAfterCmd(commandAdd2, task2);
        assertEventVisibleAfterCmd(commandAdd3, event3);
        assertEventVisibleAfterCmd(commandAdd4, event4);
    }
    
```
###### /java/seedu/todo/guitests/ListCommandTest.java
``` java
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskNotVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventNotVisibleAfterCmd("list", event4);
    }
```
###### /java/seedu/todo/guitests/TagControllerTest.java
``` java
    @Before
    public void initFixtures() {
        console.runCommand("clear");
        assertTaskVisibleAfterCmd(commandAdd, task);
    }
    
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisibleAfterCmd("list", task);
    }
```
###### /java/seedu/todo/guitests/UntagControllerTest.java
``` java
    @Before
    public void initFixtures() {
        console.runCommand("clear");
        assertTaskVisibleAfterCmd(commandAdd, task);
    }
    
```
###### /java/seedu/todo/guitests/UntagControllerTest.java
``` java
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisibleAfterCmd("list", task);
    }
```
