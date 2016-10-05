# User Cases

`Software System:` KeyboardWarrior <br>
`Use case:` UC01 - Adding a calendar task <br>
`Actor:` User <br>
`MSS:` <br>
1.  User chooses to add a calendar task. <br>
2. User types in command and required parameters. <br>			
3. KeyboardWarrior displays task in Calendar pane. <br>
`Use case ends.` <br>
`Extensions:` <br>
2a. KeyboardWarrior detects an error in the entered parameters. <br>
2a1. KeyboardWarrior requests for the correct parameters. <br>
2a2. User enters new parameters. <br>
Steps 2a1 - 2a2 are repeated until the parameters entered are correct. <br>
Use case resumes from step 3. <br>
`Use case ends.` <br>

`Software System:` KeyboardWarrior <br>
`Use case:` UC02 - Finding a task <br>
`Actor:` User <br>
`MSS:`  <br>
1.  User chooses to find a task. <br>
2. User types in command and required parameters. <br>
3. KeyboardWarrior displays task on Calendar pane. <br>
`Use case ends.` <br>
`Extensions:` <br>
2a. KeyboardWarrior does not find searched task. <br>
2a1. KeyboardWarrior notifies user that there is no such task. <br>
`Use case ends.` <br>

`Software System:` KeyboardWarrior <br>
`Use case:` UC03 - Complete todo task <br>
`Actor:` User <br>
`MSS:` <br>
1.  User chooses to complete a Keep in View or Deadline. <br>
2. User types in command and required parameters. <br>
3. KeyboardWarrior removes task from Todo pane or Deadline pane. <br>
`Use case ends.` <br>
`Extensions:` <br>
2a. KeyboardWarrior does not find searched task. <br>
2a1. KeyboardWarrior notifies user that there is no such task. <br>
`Use case ends.` <br>
