# A0135817Breused
###### \DeveloperGuide.html
``` html
<div class="no-page-break"><h4 id="importing-the-project-into-eclipse"><span>2.1.1 </span>Importing the project into Eclipse<a class="headerlink" href="#importing-the-project-into-eclipse" title="Permanent link">#</a></h4><div class="admonition note">
<p class="admonition-title">Note</p>
<p>Ensure that you have installed the <strong>e(fx)clipse</strong> and <strong>buildship</strong> plugins as listed in the prerequisites above.</p>
</div></div>

<ol>
<li>Click <code>File</code> &gt; <code>Import</code></li>
<li>Click <code>Gradle</code> &gt; <code>Gradle Project</code> &gt; <code>Next</code> &gt; <code>Next</code></li>
<li>Click <code>Browse</code>, then locate the project's directory</li>
<li>Click <code>Finish</code></li>
</ol>
<div class="admonition note">
<p class="admonition-title">Note</p>
<ul>
<li>If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.</li>
<li>Depending on your connection speed and server load, this step may take up to 30 minutes to finish
  (This is because Gradle downloads library files from servers during the project set up process)</li>
<li>If Eclipse has changed any settings files during the import process, you can discard those changes.</li>
</ul>
</div>
<div class="no-page-break"><h3 id="contributing"><span>2.2 </span>Contributing<a class="headerlink" href="#contributing" title="Permanent link">#</a></h3><p>We use the <a href="https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow/">feature branch git workflow</a>. Thus when you are working on a task, please remember to assign the relevant issue to yourself <a href="https://github.com/CS2103AUG2016-W10-C4/main/issues">on the issue tracker</a> and branch off from <code>master</code>. When the task is completed, do remember to push the branch to GitHub and <a href="https://github.com/CS2103AUG2016-W10-C4/main/compare">create a new pull request</a> so that the integrator can review the code. For large features that impact multiple parts of the code it is best to open a new issue on the issue tracker so that the design of the code can be discussed first.</p></div>

<p><a href="https://en.wikipedia.org/wiki/Test-driven_development">Test driven development</a> is encouraged but not required. If possible, all of your incoming code should have 100% accompanying tests - Coveralls will fail any incoming pull request which causes coverage to fall.</p>
<div class="no-page-break"><h3 id="coding-style"><span>2.3 </span>Coding Style<a class="headerlink" href="#coding-style" title="Permanent link">#</a></h3><p>We use the Java coding standard found at <a href="https://oss-generic.github.io/process/codingstandards/coding-standards-java.html">https://oss-generic.github.io/process/codingstandards/coding-standards-java.html</a>.</p></div>

```
###### \DeveloperGuide.html
``` html
<div class="no-page-break"><h3 id="logging"><span>4.3 </span>Logging<a class="headerlink" href="#logging" title="Permanent link">#</a></h3><p>We are using the <a href="https://docs.oracle.com/javase/8/docs/api/java/util/logging/package-summary.html"><code>java.util.logging</code></a> package for logging. The <code>LogsCenter</code> class is used to manage the logging levels and logging destinations.</p></div>

<ul>
<li>The logging level can be controlled using the <code>logLevel</code> setting in the configuration file (See <a href="#configuration">Configuration</a>)</li>
<li>The <code>Logger</code> for a class can be obtained using <code>LogsCenter.getLogger(Class)</code> which will log messages according to the specified logging level</li>
<li>Currently log messages are output through: Console and to a log file.</li>
<li>The logs roll over at 5MB such that every log file is smaller than 5MB. Five log files are kept, after which the oldest will be deleted. </li>
</ul>
```
###### \DeveloperGuide.html
``` html
<div class="no-page-break"><h3 id="configuration"><span>4.4 </span>Configuration<a class="headerlink" href="#configuration" title="Permanent link">#</a></h3><p>Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file (default: <code>config.json</code>)</p></div>

<div class="no-page-break"><h2 id="testing"><span>5 </span>Testing<a class="headerlink" href="#testing" title="Permanent link">#</a></h2><p>Tests can be found in the <code>./src/test/java</code> folder.</p></div>

<div class="no-page-break"><h3 id="in-eclipse"><span>5.1 </span>In Eclipse<a class="headerlink" href="#in-eclipse" title="Permanent link">#</a></h3><ul>
<li>To run all tests, right-click on the <code>src/test/java</code> folder and choose
  <code>Run as</code> &gt; <code>JUnit Test</code></li>
<li>To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.</li>
</ul></div>

<div class="admonition note">
<p class="admonition-title">Note</p>
<p>If you are not using a recent Eclipse version (Neon or later), enable assertions in JUnit tests
as described <a href="http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option">in this Stack Overflow question</a> (url: http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).</p>
</div>
<div class="no-page-break"><h3 id="using-gradle"><span>5.2 </span>Using Gradle<a class="headerlink" href="#using-gradle" title="Permanent link">#</a></h3><p>See <a href="#appendix-f-using-gradle">UsingGradle.md</a> for how to run tests using Gradle.</p></div>

<p>We have two types of tests:</p>
<ol>
<li>
<p><strong><abbr title="Graphical User Interface">GUI</abbr> Tests</strong> - These are <em>System Tests</em> that test the entire App by simulating user actions on the <abbr title="Graphical User Interface">GUI</abbr>. These are in the <code>guitests</code> package.</p>
</li>
<li>
<p><strong>Non-<abbr title="Graphical User Interface">GUI</abbr> Tests</strong> - These are tests not involving the <abbr title="Graphical User Interface">GUI</abbr>. They include,</p>
<ol>
<li><strong>Unit tests</strong> - targeting the lowest level methods/classes.<br/>
  e.g. <code>seedu.todo.commons.UrlUtilTest</code></li>
<li><strong>Integration tests</strong> - that are checking the integration of multiple code units (those code units are assumed to be working).<br/>
  e.g. <code>seedu.todo.model.TodoModelTest</code></li>
<li><strong>Hybrids of unit and integration tests.</strong> These tests are checking multiple code units as well as how the are connected together.  <br/>
  e.g. <code>seedu.todo.logic.BaseCommandTest</code></li>
</ol>
</li>
</ol>
```
###### \DeveloperGuide.html
``` html
<div class="no-page-break"><h2 id="dev-ops"><span>6 </span>Dev Ops<a class="headerlink" href="#dev-ops" title="Permanent link">#</a></h2><h3 id="build-automation"><span>6.1 </span>Build Automation<a class="headerlink" href="#build-automation" title="Permanent link">#</a></h3></div>

<p>We use <a href="https://gradle.org/">Gradle</a> for build automation. Gradle handles project dependencies, build tasks and testing. If you have configured Eclipse by importing the project as shown in the <a href="#setting-up">setting up</a> section Gradle should already be properly configured and can be executing from within Eclipse to build, test and package the project from the Run menu.</p>
<p>See the appendix <a href="#appendix-f-using-gradle">Using Gradle</a> for all of the details and Gradle commands. </p>
<div class="no-page-break"><h3 id="continuous-integration"><span>6.2 </span>Continuous Integration<a class="headerlink" href="#continuous-integration" title="Permanent link">#</a></h3><p>We use <a href="https://travis-ci.org/CS2103AUG2016-W10-C4/main">Travis CI</a> to perform Continuous Integration on our projects. See <a href="UsingTravis.md">UsingTravis.md</a> for more details.</p></div>

```
###### \DeveloperGuide.html
``` html
<div class="no-page-break"><h2 id="appendix-a-user-stories"><span>8 </span>Appendix A : User Stories<a class="headerlink" href="#appendix-a-user-stories" title="Permanent link">#</a></h2><p>Priorities: High (must have) - <strong>* , Medium (nice to have) - </strong> ,  Low (unlikely to have) - *</p></div>

<table>
<thead>
<tr>
<th>Priority</th>
<th align="left">As a ...</th>
<th align="left">I want to ...</th>
<th align="left">So that I can...</th>
</tr>
</thead>
<tbody>
<tr>
<td>***</td>
<td align="left">new user</td>
<td align="left">see usage instructions</td>
<td align="left">refer to instructions when I forget how to use the app</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">add a new task</td>
<td align="left"></td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">mark a task as complete</td>
<td align="left">so I know which are the tasks are not complete.</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">delete a task</td>
<td align="left">remove entries that I no longer need</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">edit a task</td>
<td align="left">change or update details for the task</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">set a deadline for a task</td>
<td align="left">track down deadlines of my tasks</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">set events with start and end dates</td>
<td align="left">keep track of events that will happen</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">view tasks</td>
<td align="left">see all the tasks I have</td>
</tr>
<tr>
<td>***</td>
<td align="left">user</td>
<td align="left">view incomplete tasks only</td>
<td align="left">to know what are the tasks I have left to do.</td>
</tr>
<tr>
<td>***</td>
<td align="left">user with multiple computers</td>
<td align="left">save the todo list file to a specific location</td>
<td align="left">move the list to other computers</td>
</tr>
<tr>
<td>**</td>
<td align="left">user with a lot tasks</td>
<td align="left">add tags to my tasks</td>
<td align="left">organize my tasks</td>
</tr>
<tr>
<td>**</td>
<td align="left">user</td>
<td align="left">set recurring tasks</td>
<td align="left">do not need to repeatedly add tasks</td>
</tr>
<tr>
<td>**</td>
<td align="left">user</td>
<td align="left">sort tasks by various parameters</td>
<td align="left">organize my tasks and locate a task easily</td>
</tr>
<tr>
<td>**</td>
<td align="left">user</td>
<td align="left">set reminders for a task</td>
<td align="left">do not need to mentally track deadlines</td>
</tr>
<tr>
<td>*</td>
<td align="left">user</td>
<td align="left">know the number of tasks I have left</td>
<td align="left">gauge how many tasks I have left to do.</td>
</tr>
<tr>
<td>*</td>
<td align="left">user</td>
<td align="left">be notified about upcoming deadlines without opening the app</td>
<td align="left">so that I can receive timely reminders</td>
</tr>
</tbody>
</table>
<div class="no-page-break"><h2 id="appendix-b-use-cases"><span>9 </span>Appendix B : Use Cases<a class="headerlink" href="#appendix-b-use-cases" title="Permanent link">#</a></h2><p>(For all use cases below, the <strong>System</strong> is the <code>TodoApp</code> and the <strong>Actor</strong> is the <code>user</code>, unless specified otherwise)</p></div>

<div class="no-page-break"><h3 id="adding-an-event"><span>9.1 </span>Adding an event<a class="headerlink" href="#adding-an-event" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User types out an event with start time, end time and location</li>
<li>TodoApp adds event with specified fields and saves it to disk</li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. The task has no title</p>
<blockquote>
<p>1a1. TodoApp shows an error message<br/>
  Use case resumes at step 1 </p>
</blockquote>
<p>1b. The task's date field is empty</p>
<blockquote>
<p>1b1. TodoApp creates a task with no start and end date<br/>
  Use case resumes at step 2</p>
</blockquote>
<p>1c. The task has a start time later than end time</p>
<blockquote>
<p>1c1. TodoApp assumes the dates are inverted  <br/>
  Use case resumes at step 2</p>
</blockquote>
<p>1d. The event's timing overlaps with an existing event's timing </p>
<blockquote>
<p>1d1. TodoApp displays a warning to the user that he has another event at the same time<br/>
  Use case resumes at step 2</p>
</blockquote>
<div class="no-page-break"><h3 id="adding-a-task-with-deadline"><span>9.2 </span>Adding a task with deadline<a class="headerlink" href="#adding-a-task-with-deadline" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User enters a task while specifying a deadline for the task.</li>
<li>TodoApp creates new todo item with deadline specified and saves it to disk</li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. The task has no title</p>
<blockquote>
<p>1a1. TodoApp shows an error message<br/>
  Use case resumes at step 1 </p>
</blockquote>
<p>1b. The task's date field is empty</p>
<blockquote>
<p>1b1. TodoApp creates a task with no deadline<br/>
  Use case ends</p>
</blockquote>
<div class="no-page-break"><h3 id="adding-a-recurring-task"><span>9.3 </span>Adding a recurring task<a class="headerlink" href="#adding-a-recurring-task" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User enters a task with a recurring time period </li>
<li>TodoApp creates a new recurring todo item with the specified time period </li>
<li>At the start of the specified time period (eg. every week, month) TodoApp creates a copy of the original task for the user </li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>2a. The given recurring time period is invalid </p>
<blockquote>
<p>2a1. TodoApp shows an error message<br/>
  Use case resumes at step 1</p>
</blockquote>
<div class="no-page-break"><h3 id="marking-a-task-complete"><span>9.4 </span>Marking a task complete<a class="headerlink" href="#marking-a-task-complete" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User requests to see a list of uncompleted tasks.</li>
<li>TodoApp shows a list of uncompleted tasks.</li>
<li>User marks complete a specific task in the list.</li>
<li>TodoApp marks the task as complete by striking through the task and saving its new state to disk</li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. User uses another method to list tasks (e.g. search)</p>
<blockquote>
<p>1a1. TodoApp shows the list of tasks requested<br/>
  Use case resumes at step 2</p>
</blockquote>
<p>2a. The list is empty</p>
<blockquote>
<p>1a1. TodoApp informs the user the list is empty<br/>
  Use case ends</p>
</blockquote>
<p>3a. The given index is invalid</p>
<blockquote>
<p>3a1. TodoApp shows an error message<br/>
  Use case resumes at step 2</p>
</blockquote>
<p>3b. The given index is a task which has already been completed</p>
<blockquote>
<p>3b1. TodoApp informs the user the task has already been completed 
  Use case ends</p>
</blockquote>
<div class="no-page-break"><h3 id="delete-task"><span>9.5 </span>Delete task<a class="headerlink" href="#delete-task" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User requests to delete a specific task from the list</li>
<li>TodoApp deletes the person  </li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. The given index is invalid</p>
<blockquote>
<p>1a1. TodoApp shows an error message<br/>
  Use case resumes at step 1</p>
</blockquote>
<div class="no-page-break"><h3 id="viewing-a-specific-tab-ie-intelligent-views"><span>9.6 </span>Viewing a specific tab (i.e. intelligent views)<a class="headerlink" href="#viewing-a-specific-tab-ie-intelligent-views" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User requests to view specific tab</li>
<li>TodoApp shows a list of tasks under specific tab</li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. User enters invalid view (eg. a view that doesn't exist )</p>
<blockquote>
<p>1a1. TodoApp shows an error message<br/>
  Use case ends</p>
</blockquote>
<div class="no-page-break"><h3 id="finding-for-a-task"><span>9.7 </span>Finding for a task<a class="headerlink" href="#finding-for-a-task" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User searches for task with specific tag or fragmented title</li>
<li>TodoApp returns a list of tasks matching search fragment</li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>1a. User enters an invalid tag/search fragment</p>
<blockquote>
<p>1a1. TodoApp returns an empty list<br/>
  Use case ends</p>
</blockquote>
<div class="no-page-break"><h3 id="editing-a-task"><span>9.8 </span>Editing a task<a class="headerlink" href="#editing-a-task" title="Permanent link">#</a></h3><ol>
<li>User searches for specific task to edit</li>
<li>TodoApp returns list of tasks matching search query</li>
<li>User edits specific task on the list, changing any of its fields</li>
<li>TodoApp accepts changes, reflects them on the task and </li>
</ol></div>

<p><strong>Extensions</strong></p>
<p>2a. List returned is empty  </p>
<blockquote>
<p>Use case ends</p>
</blockquote>
<p>3a. User enters invalid task index</p>
<blockquote>
<p>3a1. TodoApp shows error message indicating invalid index <br/>
Use case resumes at Step 2</p>
</blockquote>
<p>3b. User enters invalid arguments to edit fields</p>
<blockquote>
<p>3b1. TodoApp shows error message indicating invalid fields<br/>
Use case resumes at Step 2</p>
</blockquote>
<div class="no-page-break"><h3 id="pinning-a-task"><span>9.9 </span>Pinning a task<a class="headerlink" href="#pinning-a-task" title="Permanent link">#</a></h3><p><strong>MSS</strong></p></div>

<ol>
<li>User searches for specific task to pin using the find command</li>
<li>TodoApp returns a list of tasks matching the search query</li>
<li>User selects a specific task to pin</li>
<li>TodoApp pins selected task and updates the storage file on disk </li>
</ol>
<p>Use case ends.</p>
<p><strong>Extensions</strong></p>
<p>2a. List returned by TodoApp is empty</p>
<blockquote>
<p>Use case ends</p>
</blockquote>
<p>3a. Selected task is already pinned</p>
<blockquote>
<p>3a1. TodoApp unpins selected task
 Use case ends</p>
</blockquote>
<p>3b. User provides an invalid index</p>
<blockquote>
<p>3b1. TodoApp shows an error message<br/>
Use case resumes at Step 3</p>
</blockquote>
<div class="no-page-break"><h3 id="undoing-an-action"><span>9.10 </span>Undoing an action<a class="headerlink" href="#undoing-an-action" title="Permanent link">#</a></h3><ol>
<li>User carries out a mutating command (see <a href="#appendix-d-glossary">glossary</a>)</li>
<li>User finds they have made a mistake and instructs TodoApp to undo last action</li>
<li>TodoApp rolls back the todolist to the previous state and updates the stored todolist on disk</li>
</ol></div>

<p><strong>Extensions</strong></p>
<p>2a1. The user calls the undo command without having made any changes </p>
<blockquote>
<p>2a1. TodoApp shows an error message<br/>
Use case ends </p>
</blockquote>
<div class="no-page-break"><h2 id="appendix-c-non-functional-requirements"><span>10 </span>Appendix C : Non Functional Requirements<a class="headerlink" href="#appendix-c-non-functional-requirements" title="Permanent link">#</a></h2><p>The project should -</p></div>

<ol>
<li>work on any mainstream OS as long as it has Java 8 or higher installed.</li>
<li>use a command line interface as the primary input mode</li>
<li>have a customizable colour scheme.</li>
<li>be able to hold up to 100 todos, events and deadlines. </li>
<li>come with automated unit tests.</li>
<li>have competitive performance with commands being executed within 5 seconds of typing into the CLI</li>
<li>be open source. </li>
</ol>
<div class="no-page-break"><h2 id="appendix-d-glossary"><span>11 </span>Appendix D : Glossary<a class="headerlink" href="#appendix-d-glossary" title="Permanent link">#</a></h2><dl>
<dt>Mainstream OS</dt>
<dd>
<p>Windows, OS X</p>
</dd>
<dt>Task </dt>
<dd>
<p>A single todo task, deadline or item</p>
</dd>
<dt>Pinning</dt>
<dd>
<p>Marking a task with higher importance/priority than others. Pinned tasks will always appear first in any view. </p>
</dd>
<dt>Mutating Command</dt>
<dd>
<p>Any command which causes a change in the state of the the TodoApp (E.g. add, delete, edit, pin, complete)</p>
</dd>
</dl></div>

<div class="no-page-break"><h2 id="appendix-e-product-survey"><span>12 </span>Appendix E : Product Survey<a class="headerlink" href="#appendix-e-product-survey" title="Permanent link">#</a></h2><p><strong>Basic Todo Lists</strong> e.g. Sticky Notes, Notepad</p></div>

<p>Very flexible and easy to use, but hard to organise tasks on them. Also, data can only be saved locally. </p>
<p><strong>Online/Cloud Based Todo lists</strong></p>
<p>Apps such as Google Calendar, Asana and Trello offer a wide range of effective features that help manage your To-do lists. However, most of them require heavy mouse usage and the constant context switching might break user concentration. Our target audience are users who prefer not use the mouse at all, and that makes some of these applications almost unusable. Also, it is hard to sync without a constant internet connection.</p>
<div class="no-page-break"><h2 id="appendix-f-using-gradle"><span>13 </span>Appendix F: Using Gradle<a class="headerlink" href="#appendix-f-using-gradle" title="Permanent link">#</a></h2><p><a href="https://gradle.org/">Gradle</a> is a build automation tool for Java projects. It can automate build-related tasks such as</p></div>

<ul>
<li>Running tests</li>
<li>Managing library dependencies</li>
<li>Analyzing code for style compliance</li>
<li>Packaging code for release</li>
</ul>
<p>The gradle configuration for this project is defined in the build script <a href="../build.gradle"><code>build.gradle</code></a>.</p>
<div class="admonition note">
<p class="admonition-title">Note</p>
<p>To learn more about gradle build scripts refer to <a href="https://docs.gradle.org/current/userguide/tutorial_using_tasks.html">Build Scripts Basics</a>.</p>
</div>
<div class="no-page-break"><h3 id="running-gradle-commands"><span>13.1 </span>Running Gradle Commands<a class="headerlink" href="#running-gradle-commands" title="Permanent link">#</a></h3><p>To run a Gradle command, open a command window on the project folder and enter the Gradle command. Gradle commands look like this:</p></div>

<ul>
<li>On Windows :<code>gradlew &lt;task1&gt; &lt;task2&gt; ...</code> e.g. <code>gradlew clean allTests</code></li>
<li>On Mac/Linux: <code>./gradlew &lt;task1&gt; &lt;task2&gt;...</code>  e.g. <code>./gradlew clean allTests</code></li>
</ul>
<div class="admonition note">
<p class="admonition-title">Note</p>
<p>If you do not specify any tasks, Gradlew will run the default tasks <code>clean</code> <code>headless</code> <code>allTests</code> <code>coverage</code></p>
</div>
<div class="no-page-break"><h3 id="cleaning-the-project"><span>13.2 </span>Cleaning the Project<a class="headerlink" href="#cleaning-the-project" title="Permanent link">#</a></h3><p><strong><code>clean</code></strong> - Deletes the files created during the previous build tasks (e.g. files in the <code>build</code> folder).<br>
e.g. <code>./gradlew clean</code></br></p></div>

<div class="admonition note">
<p class="admonition-title"><code>clean</code> to force Gradle to execute a task</p>
<p>When running a Gradle task, Gradle will try to figure out if the task needs running at all.  If Gradle determines that the output of the task will be same as the previous time, it will not run the task. For example, it will not build the JAR file again if the relevant source files have not changed since the last time the JAR file was built. If we want to force Gradle to run a task, we can combine that task with <code>clean</code>. Once the build files have been <code>clean</code>ed, Gradle has no way to determine if the output will be same as before, so it will be forced to execute the task.</p>
</div>
<div class="no-page-break"><h3 id="creating-the-jar-file"><span>13.3 </span>Creating the JAR file<a class="headerlink" href="#creating-the-jar-file" title="Permanent link">#</a></h3><p><strong><code>shadowJar</code></strong> - Creates the <code>addressbook.jar</code> file in the <code>build/jar</code> folder, if the current file is outdated.<br/>
e.g. <code>./gradlew shadowJar</code></p></div>

<p>To force Gradle to create the JAR file even if the current one is up-to-date, you can '<code>clean</code>' first.<br/>
e.g. <code>./gradlew clean shadowJar</code> </p>
<div class="admonition note">
<p class="admonition-title">Why do we create a fat JAR?</p>
<p>If we package only our own class files into the JAR file, it will not work properly unless the user has all the other JAR files (i.e. third party libraries) our classes depend on, which is rather inconvenient. Therefore, we package all dependencies into a single JAR files, creating what is also known as a <em>fat</em> JAR file. To create a fat JAR file, we use the <a href="https://github.com/johnrengelman/shadow">shadow jar</a> Gradle plugin.</p>
</div>
<div class="no-page-break"><h3 id="running-tests"><span>13.4 </span>Running Tests<a class="headerlink" href="#running-tests" title="Permanent link">#</a></h3><p><strong><code>allTests</code></strong> - Runs all tests.</p></div>

<p><strong><code>guiTests</code></strong> - Runs all tests in the <code>guitests</code> package</p>
<p><strong><code>nonGuiTests</code></strong> - Runs all non-<abbr title="Graphical User Interface">GUI</abbr> tests in the <code>seedu.address</code> package</p>
<p><strong><code>headless</code></strong> - Sets the test mode as <em>headless</em>. The mode is effective for that Gradle run only so it should be combined with other test tasks.</p>
<p>Here are some examples:</p>
<ul>
<li><code>./gradlew headless allTests</code> -- Runs all tests in headless mode</li>
<li><code>./gradlew clean nonGuiTests</code> -- Cleans the project and runs non-<abbr title="Graphical User Interface">GUI</abbr> tests</li>
</ul>
<div class="no-page-break"><h3 id="updating-dependencies"><span>13.5 </span>Updating Dependencies<a class="headerlink" href="#updating-dependencies" title="Permanent link">#</a></h3><p>There is no need to run these Gradle tasks manually as they are called automatically by other relevant Gradle tasks.</p></div>

<p><strong><code>compileJava</code></strong> - Checks whether the project has the required dependencies to compile and run the main program, and download any missing dependencies before compiling the classes.  </p>
<p>See <code>build.gradle</code> &gt; <code>allprojects</code> &gt; <code>dependencies</code> &gt; <code>compile</code> for the list of dependencies required.</p>
<p><strong><code>compileTestJava</code></strong> - Checks whether the project has the required dependencies to perform testing, and download any missing dependencies before compiling the test classes.</p>
<p>See <code>build.gradle</code> &gt; <code>allprojects</code> &gt; <code>dependencies</code> &gt; <code>testCompile</code> for the list of dependencies required.</p>
</div>
</body>
</html>
```
###### \DeveloperGuide.md
``` md

#### Importing the project into Eclipse

0. Fork this repository, and clone the fork to your computer with Git.
1. Open Eclipse
!!! note
    
    Ensure that you have installed the **e(fx)clipse** and **buildship** plugins as listed in the prerequisites above.

2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

!!! note
    
    * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
    * Depending on your connection speed and server load, this step may take up to 30 minutes to finish
      (This is because Gradle downloads library files from servers during the project set up process)
    * If Eclipse has changed any settings files during the import process, you can discard those changes.
  
### Contributing 

We use the [feature branch git workflow][workflow]. Thus when you are working on a task, please remember to assign the relevant issue to yourself [on the issue tracker][issues] and branch off from `master`. When the task is completed, do remember to push the branch to GitHub and [create a new pull request][pr] so that the integrator can review the code. For large features that impact multiple parts of the code it is best to open a new issue on the issue tracker so that the design of the code can be discussed first.

[Test driven development][tdd] is encouraged but not required. If possible, all of your incoming code should have 100% accompanying tests - Coveralls will fail any incoming pull request which causes coverage to fall.

### Coding Style

We use the Java coding standard found at <https://oss-generic.github.io/process/codingstandards/coding-standards-java.html>.
 

```
###### \DeveloperGuide.md
``` md

### Logging

We are using the [`java.util.logging`][jul] package for logging. The `LogsCenter` class is used to manage the logging levels and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level
* Currently log messages are output through: Console and to a log file.
* The logs roll over at 5MB such that every log file is smaller than 5MB. Five log files are kept, after which the oldest will be deleted. 

```
###### \DeveloperGuide.md
``` md

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file (default: `config.json`)


## Testing

Tests can be found in the `./src/test/java` folder.

### In Eclipse

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.

!!! note
    If you are not using a recent Eclipse version (Neon or later), enable assertions in JUnit tests
    as described [in this Stack Overflow question](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option) (url: http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).


### Using Gradle

See [UsingGradle.md](#appendix-f-using-gradle) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
    1. **Unit tests** - targeting the lowest level methods/classes.  
      e.g. `seedu.todo.commons.UrlUtilTest`
    2. **Integration tests** - that are checking the integration of multiple code units (those code units are assumed to be working).  
      e.g. `seedu.todo.model.TodoModelTest`
    3. **Hybrids of unit and integration tests.** These tests are checking multiple code units as well as how the are connected together.    
      e.g. `seedu.todo.logic.BaseCommandTest`

```
###### \DeveloperGuide.md
``` md

## Dev Ops

### Build Automation

We use [Gradle][gradle] for build automation. Gradle handles project dependencies, build tasks and testing. If you have configured Eclipse by importing the project as shown in the [setting up](#setting-up) section Gradle should already be properly configured and can be executing from within Eclipse to build, test and package the project from the Run menu.

See the appendix [Using Gradle](#appendix-f-using-gradle) for all of the details and Gradle commands. 

### Continuous Integration

We use [Travis CI][travis] to perform Continuous Integration on our projects. See [UsingTravis.md](UsingTravis.md) for more details.

```
###### \DeveloperGuide.md
``` md

## Appendix A : User Stories

Priorities: High (must have) - *** , Medium (nice to have) - ** ,  Low (unlikely to have) - *


Priority | As a ...  | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
***      | new user  | see usage instructions | refer to instructions when I forget how to use the app
***      | user      | add a new task |
***      | user      | mark a task as complete | so I know which are the tasks are not complete.
***      | user      | delete a task | remove entries that I no longer need
***      | user      | edit a task | change or update details for the task
***      | user      | set a deadline for a task | track down deadlines of my tasks
***      | user      | set events with start and end dates | keep track of events that will happen
***      | user      | view tasks | see all the tasks I have
***      | user      | view incomplete tasks only | to know what are the tasks I have left to do.
***      | user with multiple computers | save the todo list file to a specific location | move the list to other computers
**       | user with a lot tasks | add tags to my tasks | organize my tasks 
**       | user      | set recurring tasks | do not need to repeatedly add tasks
**       | user      | sort tasks by various parameters| organize my tasks and locate a task easily
**       | user      | set reminders for a task | do not need to mentally track deadlines
*        | user      | know the number of tasks I have left | gauge how many tasks I have left to do.
*        | user      | be notified about upcoming deadlines without opening the app | so that I can receive timely reminders  


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TodoApp` and the **Actor** is the `user`, unless specified otherwise)

### Adding an event

**MSS**

1. User types out an event with start time, end time and location
2. TodoApp adds event with specified fields and saves it to disk

Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1 
  
1b. The task's date field is empty

> 1b1. TodoApp creates a task with no start and end date  
  Use case resumes at step 2

1c. The task has a start time later than end time

> 1c1. TodoApp assumes the dates are inverted    
  Use case resumes at step 2
 
1d. The event's timing overlaps with an existing event's timing 

> 1d1. TodoApp displays a warning to the user that he has another event at the same time  
  Use case resumes at step 2
  
### Adding a task with deadline

**MSS**

1. User enters a task while specifying a deadline for the task.
2. TodoApp creates new todo item with deadline specified and saves it to disk

Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1 

1b. The task's date field is empty

> 1b1. TodoApp creates a task with no deadline  
  Use case ends

### Adding a recurring task

**MSS**

1. User enters a task with a recurring time period 
2. TodoApp creates a new recurring todo item with the specified time period 
3. At the start of the specified time period (eg. every week, month) TodoApp creates a copy of the original task for the user 

Use case ends.

**Extensions**


2a. The given recurring time period is invalid 

> 2a1. TodoApp shows an error message  
  Use case resumes at step 1

### Marking a task complete

**MSS**

1. User requests to see a list of uncompleted tasks.
2. TodoApp shows a list of uncompleted tasks.
3. User marks complete a specific task in the list.
4. TodoApp marks the task as complete by striking through the task and saving its new state to disk

Use case ends.

**Extensions**

1a. User uses another method to list tasks (e.g. search)

> 1a1. TodoApp shows the list of tasks requested  
  Use case resumes at step 2

2a. The list is empty

> 1a1. TodoApp informs the user the list is empty  
  Use case ends

3a. The given index is invalid

> 3a1. TodoApp shows an error message  
  Use case resumes at step 2

3b. The given index is a task which has already been completed

> 3b1. TodoApp informs the user the task has already been completed 
  Use case ends

### Delete task

**MSS**

1. User requests to delete a specific task from the list
2. TodoApp deletes the person  

Use case ends.

**Extensions**

1a. The given index is invalid

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1

### Viewing a specific tab (i.e. intelligent views)

**MSS**

1. User requests to view specific tab
2. TodoApp shows a list of tasks under specific tab

Use case ends.

**Extensions**

1a. User enters invalid view (eg. a view that doesn't exist )

> 1a1. TodoApp shows an error message  
  Use case ends

### Finding for a task

**MSS**

1. User searches for task with specific tag or fragmented title
2. TodoApp returns a list of tasks matching search fragment

Use case ends.

**Extensions**

1a. User enters an invalid tag/search fragment

> 1a1. TodoApp returns an empty list  
  Use case ends

### Editing a task

1. User searches for specific task to edit
2. TodoApp returns list of tasks matching search query
3. User edits specific task on the list, changing any of its fields
4. TodoApp accepts changes, reflects them on the task and 

**Extensions**

2a. List returned is empty  
>  Use case ends

3a. User enters invalid task index

> 3a1. TodoApp shows error message indicating invalid index   
> Use case resumes at Step 2

3b. User enters invalid arguments to edit fields

> 3b1. TodoApp shows error message indicating invalid fields  
> Use case resumes at Step 2

### Pinning a task

**MSS**

1. User searches for specific task to pin using the find command
2. TodoApp returns a list of tasks matching the search query
3. User selects a specific task to pin
4. TodoApp pins selected task and updates the storage file on disk 

Use case ends.

**Extensions**

2a. List returned by TodoApp is empty

> Use case ends

3a. Selected task is already pinned

>  3a1. TodoApp unpins selected task
>  Use case ends

3b. User provides an invalid index

> 3b1. TodoApp shows an error message  
> Use case resumes at Step 3


### Undoing an action

1. User carries out a mutating command (see [glossary](#appendix-d-glossary))
2. User finds they have made a mistake and instructs TodoApp to undo last action
3. TodoApp rolls back the todolist to the previous state and updates the stored todolist on disk

**Extensions**

2a1. The user calls the undo command without having made any changes 

> 2a1. TodoApp shows an error message  
> Use case ends 


## Appendix C : Non Functional Requirements

The project should -

1. work on any mainstream OS as long as it has Java 8 or higher installed.
2. use a command line interface as the primary input mode
3. have a customizable colour scheme.
4. be able to hold up to 100 todos, events and deadlines. 
5. come with automated unit tests.
6. have competitive performance with commands being executed within 5 seconds of typing into the CLI
7. be open source. 

## Appendix D : Glossary

Mainstream OS

:   Windows, OS X

Task 

:   A single todo task, deadline or item

Pinning

:   Marking a task with higher importance/priority than others. Pinned tasks will always appear first in any view. 

Mutating Command

:   Any command which causes a change in the state of the the TodoApp (E.g. add, delete, edit, pin, complete)


## Appendix E : Product Survey

**Basic Todo Lists** e.g. Sticky Notes, Notepad

Very flexible and easy to use, but hard to organise tasks on them. Also, data can only be saved locally. 

**Online/Cloud Based Todo lists**

Apps such as Google Calendar, Asana and Trello offer a wide range of effective features that help manage your To-do lists. However, most of them require heavy mouse usage and the constant context switching might break user concentration. Our target audience are users who prefer not use the mouse at all, and that makes some of these applications almost unusable. Also, it is hard to sync without a constant internet connection.

## Appendix F: Using Gradle 

[Gradle][gradle] is a build automation tool for Java projects. It can automate build-related tasks such as
 
* Running tests
* Managing library dependencies
* Analyzing code for style compliance
* Packaging code for release

The gradle configuration for this project is defined in the build script [`build.gradle`](../build.gradle).
 
!!! note 
    To learn more about gradle build scripts refer to [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).

### Running Gradle Commands

To run a Gradle command, open a command window on the project folder and enter the Gradle command. Gradle commands look like this:

* On Windows :`gradlew <task1> <task2> ...` e.g. `gradlew clean allTests`
* On Mac/Linux: `./gradlew <task1> <task2>...`  e.g. `./gradlew clean allTests`

!!! note
    If you do not specify any tasks, Gradlew will run the default tasks `clean` `headless` `allTests` `coverage`

### Cleaning the Project

**`clean`** - Deletes the files created during the previous build tasks (e.g. files in the `build` folder).<br>
e.g. `./gradlew clean`
  
!!! note "`clean` to force Gradle to execute a task"
    When running a Gradle task, Gradle will try to figure out if the task needs running at all.  If Gradle determines that the output of the task will be same as the previous time, it will not run the task. For example, it will not build the JAR file again if the relevant source files have not changed since the last time the JAR file was built. If we want to force Gradle to run a task, we can combine that task with `clean`. Once the build files have been `clean`ed, Gradle has no way to determine if the output will be same as before, so it will be forced to execute the task.
    
### Creating the JAR file

**`shadowJar`** - Creates the `addressbook.jar` file in the `build/jar` folder, if the current file is outdated.  
e.g. `./gradlew shadowJar`

To force Gradle to create the JAR file even if the current one is up-to-date, you can '`clean`' first.  
e.g. `./gradlew clean shadowJar` 

!!! note "Why do we create a fat JAR?"
    If we package only our own class files into the JAR file, it will not work properly unless the user has all the other JAR files (i.e. third party libraries) our classes depend on, which is rather inconvenient. Therefore, we package all dependencies into a single JAR files, creating what is also known as a _fat_ JAR file. To create a fat JAR file, we use the [shadow jar](https://github.com/johnrengelman/shadow) Gradle plugin.

### Running Tests

**`allTests`** - Runs all tests.

**`guiTests`** - Runs all tests in the `guitests` package
  
**`nonGuiTests`** - Runs all non-GUI tests in the `seedu.address` package
  
**`headless`** - Sets the test mode as _headless_. The mode is effective for that Gradle run only so it should be combined with other test tasks.
  
Here are some examples:

* `./gradlew headless allTests` -- Runs all tests in headless mode
* `./gradlew clean nonGuiTests` -- Cleans the project and runs non-GUI tests


### Updating Dependencies

There is no need to run these Gradle tasks manually as they are called automatically by other relevant Gradle tasks.

**`compileJava`** - Checks whether the project has the required dependencies to compile and run the main program, and download any missing dependencies before compiling the classes.  

See `build.gradle` > `allprojects` > `dependencies` > `compile` for the list of dependencies required.

**`compileTestJava`** - Checks whether the project has the required dependencies to perform testing, and download any missing dependencies before compiling the test classes.
  
See `build.gradle` > `allprojects` > `dependencies` > `testCompile` for the list of dependencies required.

*[CRUD]: Create, Retrieve, Update, Delete
*[GUI]: Graphical User Interface
*[UI]: User interface
*[IDE]: Integrated Development Environment

[repo]: https://github.com/CS2103AUG2016-W10-C4/main/

[sourcetree]: https://www.sourcetreeapp.com/
[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[eclipse]: https://eclipse.org/downloads/
[travis]: https://travis-ci.org/CS2103AUG2016-W10-C4/main
[coveralls]: https://coveralls.io/github/CS2103AUG2016-W10-C4/main
[codacy]: https://www.codacy.com/app/Logical-Reminding-Apartment/main/dashboard
[gradle]: https://gradle.org/ 

[workflow]: https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow/
[issues]: https://github.com/CS2103AUG2016-W10-C4/main/issues
[pr]: https://github.com/CS2103AUG2016-W10-C4/main/compare
[tdd]: https://en.wikipedia.org/wiki/Test-driven_development

[jul]: https://docs.oracle.com/javase/8/docs/api/java/util/logging/package-summary.html
[property]: https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/Property.html

[gfm]: https://guides.github.com/features/mastering-markdown/
[py-markdown]: https://pythonhosted.org/Markdown/extensions/index.html
[figcaption]: https://developer.mozilla.org/en/docs/Web/HTML/Element/figcaption
```
###### \UserGuide.html
``` html
<div class="no-page-break"><h2 id="quick-start"><span>2 </span>Quick Start<a class="headerlink" href="#quick-start" title="Permanent link">#</a></h2><ol>
<li>
<p>Ensure you have <a class="print-url" href="https://www.java.com/en/download/"><strong>Java version 8 update 60</strong></a> or later installed on your computer.</p>
<div class="admonition warning">
<p class="admonition-title">This application will not work with earlier versions of Java 8</p>
</div>
</li>
<li>
<p>Download the latest copy of <code>Uncle_Jim.jar</code> from our <a class="print-url" href="https://github.com/CS2103AUG2016-W10-C4/main/releases">releases</a> page.</p>
</li>
<li>Save the file to the folder you want to use for this application.</li>
<li>
<p>Double-click the file to start the application. You should see something like this</p>
<p><figure><img alt="Example of UI once launched" src="images/app_empty.png" width="700"/><figcaption><strong>Figure 1. </strong>Initial launch screen of Uncle Jim</figcaption></figure> </p>
</li>
<li>
<p>Type in the command box and press <kbd>Enter</kbd> to execute it. </p>
</li>
<li>
<p>Here are some example commands you can try:</p>
<ul>
<li><strong><code>add</code></strong><code>Finish CS2103T homework /d next Friday</code> - 
   adds a new task with the deadline set for next Friday</li>
<li><strong><code>delete</code></strong><code>3</code> - deletes the 3<sup>rd</sup> task shown in the current list</li>
<li><strong><code>exit</code></strong> - exits the app</li>
</ul>
</li>
<li>
<p>Refer to the <a href="#command-reference">commands reference</a> section below for details of each command.</p>
</li>
</ol></div>

<div class="no-page-break"><h2 id="command-reference"><span>3 </span>Command Reference<a class="headerlink" href="#command-reference" title="Permanent link">#</a></h2><p>You can refer to the section below for the full list of commands that are available in Uncle Jim. For quick reference, you can also refer to the <a href="#command-summary">command summary</a> at the end of this guide or use the <code>help</code> command when using the app.   </p></div>

<div class="no-page-break"><h3 id="notes-regarding-command-format"><span>3.1 </span>Notes regarding command format<a class="headerlink" href="#notes-regarding-command-format" title="Permanent link">#</a></h3><ul>
<li>Words in <code>UPPERCASE</code> are the parameters.</li>
<li>Items in <code>[SQUARE BRACKETS]</code> are optional.</li>
<li>To specify parameters, such as the deadline for a task, use flags. Flags follow the Windows DOS command format - single dash (eg. <code>/f</code>) for short flags.</li>
<li>Items with <code>...</code> within each parameter means you can add more items within the same parameters than specified.</li>
<li>Most commands that refer to a particular task or event in the list require an <code>INDEX</code>. This is a number indicated on the left of a task or event as shown in the screenshot below:</li>
</ul></div>

<figure><img alt="Index Number Location" src="images/app_index.png" width="560"/><figcaption><strong>Figure 2. </strong>Use the number on the side to choose the task for your command</figcaption></figure>

```
###### \UserGuide.md
``` md

## Quick Start

1. Ensure you have [**Java version 8 update 60**][java]{: .print-url } or later installed on your computer.

    !!! warning "This application will not work with earlier versions of Java 8"

2. Download the latest copy of `Uncle_Jim.jar` from our [releases][releases]{: .print-url } page.
3. Save the file to the folder you want to use for this application.
4. Double-click the file to start the application. You should see something like this

    <img src="images/app_empty.png" width="700" alt="Example of UI once launched" /> <figcaption>Initial launch screen of Uncle Jim</figcaption>

5. Type in the command box and press <kbd>Enter</kbd> to execute it. 
6. Here are some example commands you can try:

     * **`add`**` Finish CS2103T homework /d next Friday` - 
       adds a new task with the deadline set for next Friday
     * **`delete`**` 3` - deletes the 3<sup>rd</sup> task shown in the current list
     * **`exit`** - exits the app
     
7. Refer to the [commands reference](#command-reference) section below for details of each command.


## Command Reference

You can refer to the section below for the full list of commands that are available in Uncle Jim. For quick reference, you can also refer to the [command summary](#command-summary) at the end of this guide or use the `help` command when using the app.   

### Notes regarding command format

* Words in `UPPERCASE` are the parameters.
* Items in `[SQUARE BRACKETS]` are optional.
* To specify parameters, such as the deadline for a task, use flags. Flags follow the Windows DOS command format - single dash (eg. `/f`) for short flags.
* Items with `...` within each parameter means you can add more items within the same parameters than specified.
* Most commands that refer to a particular task or event in the list require an `INDEX`. This is a number indicated on the left of a task or event as shown in the screenshot below:

<img src="images/app_index.png" width="560" alt="Index Number Location" />

<figcaption>Use the number on the side to choose the task for your command</figcaption>

```
