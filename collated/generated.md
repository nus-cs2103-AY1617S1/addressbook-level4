# generated
###### \build\resources\main\view\CommandErrorView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox maxHeight="-Infinity" minWidth="-Infinity" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandErrorView">
   <children>
      <VBox fx:id="nonFieldErrorBox" prefWidth="100.0">
         <children>
            <Label styleClass="text2" text="Non-Field Problems" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="17.0" />
               </font>
            </Label>
            <GridPane fx:id="nonFieldErrorGrid" alignment="CENTER_LEFT">
               <columnConstraints>
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="1.7976931348623157E308" minWidth="-Infinity" />
               </columnConstraints>
               <rowConstraints>
                  <RowConstraints vgrow="SOMETIMES" />
               </rowConstraints>
               <styleClass>
                  <String fx:value="gridPanel" />
                  <String fx:value="text4" />
               </styleClass>
            </GridPane>
         </children>
      </VBox>
      <VBox fx:id="fieldErrorBox" prefWidth="100.0">
         <children>
            <Label styleClass="text2" text="Field Problems" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="17.0" />
               </font>
            </Label>
            <GridPane fx:id="fieldErrorGrid" alignment="CENTER_LEFT">
               <columnConstraints>
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="1.7976931348623157E308" minWidth="-Infinity" />
               </columnConstraints>
               <rowConstraints>
                  <RowConstraints vgrow="SOMETIMES" />
               </rowConstraints>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
               <styleClass>
                  <String fx:value="gridPanel" />
                  <String fx:value="text4" />
               </styleClass>
            </GridPane>
         </children>
      </VBox>
   </children>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \build\resources\main\view\CommandFeedbackView.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.TextFlow?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.92" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandFeedbackView">
   <children>
      <TextFlow fx:id="commandFeedbackTextFlow" layoutX="10.0" layoutY="10.0" lineSpacing="2.0" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" styleClass="commandFeedback" />
      <Label fx:id="commandFeedbackLabel" styleClass="commandFeedback" />
   </children>
</AnchorPane>
```
###### \build\resources\main\view\CommandInputView.fxml
``` fxml

<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>

<AnchorPane xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandInputView">
   <TextArea fx:id="commandTextField" minHeight="-Infinity" minWidth="-Infinity" prefHeight="40.0" prefWidth="480.0" promptText="Your wish is my command. Enter your command." style="-fx-background-radius: 0;" styleClass="commandInput" stylesheets="@../style/DefaultStyle.css" wrapText="true">
      <font>
         <Font name="Segoe UI" size="20.0" />
      </font>
   </TextArea>
</AnchorPane>
```
###### \build\resources\main\view\CommandPreviewView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandPreviewView">
  <GridPane fx:id="previewGrid" styleClass="gridPanel">
     <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
        <ColumnConstraints hgrow="SOMETIMES" minWidth="-Infinity" />
     </columnConstraints>
     <rowConstraints>
        <RowConstraints vgrow="SOMETIMES" />
     </rowConstraints>
     <VBox.margin>
        <Insets />
     </VBox.margin>
  </GridPane>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \build\resources\main\view\FilterBarView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.FlowPane?>

<FlowPane alignment="CENTER_LEFT" hgap="16.0" maxWidth="-Infinity" minWidth="-Infinity" styleClass="viewFilter" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.FilterBarView">
   <padding>
      <Insets left="4.0" right="4.0" top="-8.0" />
   </padding>
</FlowPane>
```
###### \build\resources\main\view\HelpView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.HelpView">
   <children>
      <HBox alignment="CENTER_LEFT" styleClass="spacing">
         <children>
            <Label styleClass="text2" text="Help" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="22.0" />
               </font>
            </Label>
            <Label styleClass="text4" text="Need help? Try these commands out to get started." />
         </children>
      </HBox>
      <GridPane fx:id="helpGrid" styleClass="gridPanel">
         <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="-Infinity" />
         </columnConstraints>
         <rowConstraints>
            <RowConstraints vgrow="SOMETIMES" />
         </rowConstraints>
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </GridPane>
      <Label alignment="CENTER_RIGHT" contentDisplay="TEXT_ONLY" styleClass="text4" text="Start typing in the command to dismiss." />
   </children>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \build\resources\main\view\MainWindow.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="Infinity" maxWidth="Infinity" minHeight="-Infinity" minWidth="-Infinity" spacing="8.0" styleClass="main" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.MainWindow">
   <children>
      <VBox>
         <children>
            <AnchorPane fx:id="filterBarViewPlaceholder" />
            <AnchorPane fx:id="searchStatusViewPlaceholder" maxWidth="Infinity"/>
            <AnchorPane fx:id="todoListViewPlaceholder" VBox.vgrow="ALWAYS">
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </AnchorPane>
         </children>
      </VBox>
      <AnchorPane fx:id="commandErrorViewPlaceholder" />
      <AnchorPane fx:id="helpViewPlaceholder" />
      <AnchorPane fx:id="commandPreviewViewPlaceholder" />
       <AnchorPane fx:id="commandFeedbackViewPlaceholder" prefHeight="16.0" VBox.vgrow="NEVER">
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets left="2.0" right="2.0" />
         </padding>
       </AnchorPane>
      <AnchorPane fx:id="commandInputViewPlaceholder" VBox.vgrow="NEVER">
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </AnchorPane>
   </children>
</VBox>
```
###### \build\resources\main\view\SearchStatusView.fxml
``` fxml

<?import javafx.scene.layout.HBox?>
<?import javafx.scene.text.Text?>
<?import javafx.scene.layout.StackPane?>

<StackPane maxWidth="Infinity" prefHeight="24" styleClass="searchStatus"
           xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.SearchStatusView">
    <HBox minWidth="0" maxWidth="Infinity" spacing="2.0" StackPane.alignment="CENTER_LEFT">
        <Text styleClass="searchLabel">Searching for:</Text>
        <Text fx:id="searchTerm" styleClass="searchTerm" text="search terms" />
    </HBox>
    <Text fx:id="searchCount" styleClass="searchCount" text="0 results found" StackPane.alignment="CENTER_RIGHT" />
</StackPane>
```
###### \build\resources\main\view\TaskCardView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox fx:id="taskCard" alignment="CENTER_LEFT" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefWidth="500.0" spacing="2.0" styleClass="taskCard" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.60" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <StackPane>
         <children>
            <HBox alignment="CENTER_LEFT" spacing="8.0" styleClass="spacing">
               <children>
                  <Label fx:id="titleLabel" maxWidth="1.7976931348623157E308" styleClass="titleLabel" text="1. $Tasking Title$">
                     <font>
                        <Font name="Segoe UI Semibold" size="20.0" />
                     </font>
                  </Label>
                  <Label fx:id="typeLabel" text="Event" textFill="WHITE">
                     <font>
                        <Font name="Segoe UI Semilight" size="16.0" />
                     </font>
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                     <styleClass>
                        <String fx:value="highlightedBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
                  <Label fx:id="moreInfoLabel" text="•••" textFill="WHITE">
                     <font>
                        <Font name="Segoe UI Semilight" size="16.0" />
                     </font>
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <styleClass>
                        <String fx:value="lightBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
               </children>
            </HBox>
            <ImageView fx:id="pinImage" fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true" styleClass="pinImage" StackPane.alignment="CENTER_RIGHT" />
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </StackPane>
      <FlowPane fx:id="tagsBox" hgap="4.0">
         <opaqueInsets>
            <Insets />
         </opaqueInsets>
         <VBox.margin>
            <Insets />
         </VBox.margin></FlowPane>
      <HBox fx:id="descriptionBox" spacing="8.0">
         <children>
            <Label fx:id="descriptionDecoration" maxHeight="1.7976931348623157E308" minWidth="4.0">
               <styleClass>
                  <String fx:value="highlightedBackground" />
                  <String fx:value="roundLabel" />
               </styleClass></Label>
            <Label fx:id="descriptionLabel" lineSpacing="1.0" styleClass="descriptionLabel" text="Task description. $Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam id iaculis arcu. Curabitur at dapibus magna, at molestie diam. Integer posuere.$" wrapText="true">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
               <font>
                  <Font name="Segoe UI" size="18.0" />
               </font>
            </Label>
         </children>
         <VBox.margin>
            <Insets bottom="2.0" />
         </VBox.margin>
         <styleClass>
            <String fx:value="collapsible" />
            <String fx:value="spacing" />
            <String fx:value="subheadingPadding" />
         </styleClass>
         <padding>
            <Insets left="2.0" />
         </padding>
      </HBox>
      <FlowPane hgap="16.0" styleClass="spacing">
         <children>
            <HBox fx:id="dateBox" alignment="CENTER" maxWidth="-Infinity" minWidth="-Infinity" styleClass="spacingSmall">
               <children>
                  <ImageView fx:id="dateImage" fitHeight="2420.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" styleClass="dateImage">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </ImageView>
                  <Label fx:id="dateLabel" maxHeight="1.7976931348623157E308" styleClass="footnoteLabel" text="from $date missing$">
                     <font>
                        <Font name="Segoe UI Italic" size="16.0" />
                     </font>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </Label>
               </children>
               <FlowPane.margin>
                  <Insets />
               </FlowPane.margin>
            </HBox>
            <HBox fx:id="locationBox" alignment="CENTER_LEFT" maxWidth="-Infinity" styleClass="spacingSmall">
               <children>
                  <ImageView fx:id="locationImage" fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" styleClass="locationImage">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </ImageView>
                  <Label fx:id="locationLabel" maxHeight="1.7976931348623157E308" maxWidth="-Infinity" styleClass="footnoteLabel" text="at $location missing$">
                     <font>
                        <Font name="Segoe UI Italic" size="16.0" />
                     </font>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </Label>
               </children>
            </HBox>
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <opaqueInsets>
            <Insets />
         </opaqueInsets>
      </FlowPane>
   </children>
   <opaqueInsets>
      <Insets />
   </opaqueInsets>
   <padding>
      <Insets bottom="6.0" left="12.0" right="12.0" top="6.0" />
   </padding>
</VBox>
```
###### \build\resources\main\view\TodoListView.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox prefHeight="5000.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.TodoListView">
    <children>
        <ListView fx:id="todoListView" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
###### \src\main\resources\view\CommandErrorView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox maxHeight="-Infinity" minWidth="-Infinity" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandErrorView">
   <children>
      <VBox fx:id="nonFieldErrorBox" prefWidth="100.0">
         <children>
            <Label styleClass="text2" text="Non-Field Problems" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="17.0" />
               </font>
            </Label>
            <GridPane fx:id="nonFieldErrorGrid" alignment="CENTER_LEFT">
               <columnConstraints>
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="1.7976931348623157E308" minWidth="-Infinity" />
               </columnConstraints>
               <rowConstraints>
                  <RowConstraints vgrow="SOMETIMES" />
               </rowConstraints>
               <styleClass>
                  <String fx:value="gridPanel" />
                  <String fx:value="text4" />
               </styleClass>
            </GridPane>
         </children>
      </VBox>
      <VBox fx:id="fieldErrorBox" prefWidth="100.0">
         <children>
            <Label styleClass="text2" text="Field Problems" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="17.0" />
               </font>
            </Label>
            <GridPane fx:id="fieldErrorGrid" alignment="CENTER_LEFT">
               <columnConstraints>
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="1.7976931348623157E308" minWidth="-Infinity" />
               </columnConstraints>
               <rowConstraints>
                  <RowConstraints vgrow="SOMETIMES" />
               </rowConstraints>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
               <styleClass>
                  <String fx:value="gridPanel" />
                  <String fx:value="text4" />
               </styleClass>
            </GridPane>
         </children>
      </VBox>
   </children>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \src\main\resources\view\CommandFeedbackView.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.TextFlow?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.92" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandFeedbackView">
   <children>
      <TextFlow fx:id="commandFeedbackTextFlow" layoutX="10.0" layoutY="10.0" lineSpacing="2.0" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" styleClass="commandFeedback" />
      <Label fx:id="commandFeedbackLabel" styleClass="commandFeedback" />
   </children>
</AnchorPane>
```
###### \src\main\resources\view\CommandInputView.fxml
``` fxml

<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>

<AnchorPane xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandInputView">
   <TextArea fx:id="commandTextField" minHeight="-Infinity" minWidth="-Infinity" prefHeight="40.0" prefWidth="480.0" promptText="Your wish is my command. Enter your command." style="-fx-background-radius: 0;" styleClass="commandInput" stylesheets="@../style/DefaultStyle.css" wrapText="true">
      <font>
         <Font name="Segoe UI" size="20.0" />
      </font>
   </TextArea>
</AnchorPane>
```
###### \src\main\resources\view\CommandPreviewView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandPreviewView">
  <GridPane fx:id="previewGrid" styleClass="gridPanel">
     <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
        <ColumnConstraints hgrow="SOMETIMES" minWidth="-Infinity" />
     </columnConstraints>
     <rowConstraints>
        <RowConstraints vgrow="SOMETIMES" />
     </rowConstraints>
     <VBox.margin>
        <Insets />
     </VBox.margin>
  </GridPane>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \src\main\resources\view\FilterBarView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.FlowPane?>

<FlowPane alignment="CENTER_LEFT" hgap="16.0" maxWidth="-Infinity" minWidth="-Infinity" styleClass="viewFilter" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.FilterBarView">
   <padding>
      <Insets left="4.0" right="4.0" top="-8.0" />
   </padding>
</FlowPane>
```
###### \src\main\resources\view\HelpView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" styleClass="spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.HelpView">
   <children>
      <HBox alignment="CENTER_LEFT" styleClass="spacing">
         <children>
            <Label styleClass="text2" text="Help" textFill="WHITE">
               <font>
                  <Font name="Segoe UI Semibold" size="22.0" />
               </font>
            </Label>
            <Label styleClass="text4" text="Need help? Try these commands out to get started." />
         </children>
      </HBox>
      <GridPane fx:id="helpGrid" styleClass="gridPanel">
         <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="-Infinity" />
         </columnConstraints>
         <rowConstraints>
            <RowConstraints vgrow="SOMETIMES" />
         </rowConstraints>
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </GridPane>
      <Label alignment="CENTER_RIGHT" contentDisplay="TEXT_ONLY" styleClass="text4" text="Start typing in the command to dismiss." />
   </children>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \src\main\resources\view\MainWindow.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="Infinity" maxWidth="Infinity" minHeight="-Infinity" minWidth="-Infinity" spacing="8.0" styleClass="main" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.MainWindow">
   <children>
      <VBox>
         <children>
            <AnchorPane fx:id="filterBarViewPlaceholder" />
            <AnchorPane fx:id="searchStatusViewPlaceholder" maxWidth="Infinity"/>
            <AnchorPane fx:id="todoListViewPlaceholder" VBox.vgrow="ALWAYS">
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </AnchorPane>
         </children>
      </VBox>
      <AnchorPane fx:id="commandErrorViewPlaceholder" />
      <AnchorPane fx:id="helpViewPlaceholder" />
      <AnchorPane fx:id="commandPreviewViewPlaceholder" />
       <AnchorPane fx:id="commandFeedbackViewPlaceholder" prefHeight="16.0" VBox.vgrow="NEVER">
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets left="2.0" right="2.0" />
         </padding>
       </AnchorPane>
      <AnchorPane fx:id="commandInputViewPlaceholder" VBox.vgrow="NEVER">
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </AnchorPane>
   </children>
</VBox>
```
###### \src\main\resources\view\SearchStatusView.fxml
``` fxml

<?import javafx.scene.layout.HBox?>
<?import javafx.scene.text.Text?>
<?import javafx.scene.layout.StackPane?>

<StackPane maxWidth="Infinity" prefHeight="24" styleClass="searchStatus"
           xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.SearchStatusView">
    <HBox minWidth="0" maxWidth="Infinity" spacing="2.0" StackPane.alignment="CENTER_LEFT">
        <Text styleClass="searchLabel">Searching for:</Text>
        <Text fx:id="searchTerm" styleClass="searchTerm" text="search terms" />
    </HBox>
    <Text fx:id="searchCount" styleClass="searchCount" text="0 results found" StackPane.alignment="CENTER_RIGHT" />
</StackPane>
```
###### \src\main\resources\view\TaskCardView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox fx:id="taskCard" alignment="CENTER_LEFT" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefWidth="500.0" spacing="2.0" styleClass="taskCard" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.60" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <StackPane>
         <children>
            <HBox alignment="CENTER_LEFT" spacing="8.0" styleClass="spacing">
               <children>
                  <Label fx:id="titleLabel" maxWidth="1.7976931348623157E308" styleClass="titleLabel" text="1. $Tasking Title$">
                     <font>
                        <Font name="Segoe UI Semibold" size="20.0" />
                     </font>
                  </Label>
                  <Label fx:id="typeLabel" text="Event" textFill="WHITE">
                     <font>
                        <Font name="Segoe UI Semilight" size="16.0" />
                     </font>
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                     <styleClass>
                        <String fx:value="highlightedBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
                  <Label fx:id="moreInfoLabel" text="•••" textFill="WHITE">
                     <font>
                        <Font name="Segoe UI Semilight" size="16.0" />
                     </font>
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <styleClass>
                        <String fx:value="lightBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
               </children>
            </HBox>
            <ImageView fx:id="pinImage" fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true" styleClass="pinImage" StackPane.alignment="CENTER_RIGHT" />
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
      </StackPane>
      <FlowPane fx:id="tagsBox" hgap="4.0">
         <opaqueInsets>
            <Insets />
         </opaqueInsets>
         <VBox.margin>
            <Insets />
         </VBox.margin></FlowPane>
      <HBox fx:id="descriptionBox" spacing="8.0">
         <children>
            <Label fx:id="descriptionDecoration" maxHeight="1.7976931348623157E308" minWidth="4.0">
               <styleClass>
                  <String fx:value="highlightedBackground" />
                  <String fx:value="roundLabel" />
               </styleClass></Label>
            <Label fx:id="descriptionLabel" lineSpacing="1.0" styleClass="descriptionLabel" text="Task description. $Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam id iaculis arcu. Curabitur at dapibus magna, at molestie diam. Integer posuere.$" wrapText="true">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
               <font>
                  <Font name="Segoe UI" size="18.0" />
               </font>
            </Label>
         </children>
         <VBox.margin>
            <Insets bottom="2.0" />
         </VBox.margin>
         <styleClass>
            <String fx:value="collapsible" />
            <String fx:value="spacing" />
            <String fx:value="subheadingPadding" />
         </styleClass>
         <padding>
            <Insets left="2.0" />
         </padding>
      </HBox>
      <FlowPane hgap="16.0" styleClass="spacing">
         <children>
            <HBox fx:id="dateBox" alignment="CENTER" maxWidth="-Infinity" minWidth="-Infinity" styleClass="spacingSmall">
               <children>
                  <ImageView fx:id="dateImage" fitHeight="2420.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" styleClass="dateImage">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </ImageView>
                  <Label fx:id="dateLabel" maxHeight="1.7976931348623157E308" styleClass="footnoteLabel" text="from $date missing$">
                     <font>
                        <Font name="Segoe UI Italic" size="16.0" />
                     </font>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </Label>
               </children>
               <FlowPane.margin>
                  <Insets />
               </FlowPane.margin>
            </HBox>
            <HBox fx:id="locationBox" alignment="CENTER_LEFT" maxWidth="-Infinity" styleClass="spacingSmall">
               <children>
                  <ImageView fx:id="locationImage" fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" styleClass="locationImage">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </ImageView>
                  <Label fx:id="locationLabel" maxHeight="1.7976931348623157E308" maxWidth="-Infinity" styleClass="footnoteLabel" text="at $location missing$">
                     <font>
                        <Font name="Segoe UI Italic" size="16.0" />
                     </font>
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </Label>
               </children>
            </HBox>
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <opaqueInsets>
            <Insets />
         </opaqueInsets>
      </FlowPane>
   </children>
   <opaqueInsets>
      <Insets />
   </opaqueInsets>
   <padding>
      <Insets bottom="6.0" left="12.0" right="12.0" top="6.0" />
   </padding>
</VBox>
```
###### \src\main\resources\view\TodoListView.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox prefHeight="5000.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.TodoListView">
    <children>
        <ListView fx:id="todoListView" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
