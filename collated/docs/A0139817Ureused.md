# A0139817Ureused
###### /DeveloperGuide.md
``` md
## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above)
3. Click `File` > `Import`
4. Click `Gradle` > `Gradle Project` > `Next`
5. Click `Browse`, then locate the project's directory
6. Click `Next` > `Finish`

> * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
> * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
> * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

<br>
## Design

### Architecture
<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/w15c2/tusk/MainApp.java). It is responsible for,
* At app launch: Initializing the components in the correct sequence, and connecting them up with each other.
* At shut down: Shutting down the components and invoking cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Three of those classes play important roles at the architecture level.
* `EventsCentre` : Used by components to communicate with other components using events (i.e. a form of _Event Driven_ design) (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
* `LogsCenter` : Used by many classes to write log messages to the App's log file.
* `UniqueItemCollection<T>` : Used to store unique lists of Tasks and Aliases.

The rest of the App consists of four components, where each components defines its API in an interface and implements its functionality in one main class.

Component Name | Purpose | Interface | Implementation |
-------- | :----------- | :----------- |:-----------
[**`UI`**](#ui-component) | Handles the <i>Tusk</i> UI | [`Ui.java`](../src/main/java/w15c2/tusk/ui/Ui.java) | [`UIManager.java`](../src/main/java/w15c2/tusk/ui/UiManager.java)
[**`Logic`**](#logic-component) | Executes commands from the UI | [`Logic.java`](../src/main/java/w15c2/tusk/logic/Logic.java) | [`LogicManager.java`](../src/main/java/w15c2/tusk/logic/LogicManager.java)
[**`Model`**](#model-component) | Holds all required data in-memory | [`Model.java`](../src/main/java/w15c2/tusk/model/Model.java) | [`ModelManager.java`](../src/main/java/w15c2/tusk/model/ModelManager.java)
[**`Storage`**](#storage-component) | Reads data from, and writes data to, the hard disk. | [`TaskStorage.java`](../src/main/java/w15c2/tusk/storage/task/TaskStorage.java) <br> [`AliasStorage.java`](../src/main/java/w15c2/tusk/storage/task/TaskStorage.java) | [`StorageManager.java`](../src/main/java/w15c2/tusk/storage/StorageManager.java)
```
