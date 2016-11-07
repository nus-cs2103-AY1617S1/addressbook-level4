# generated
###### \resources\view\CommandErrorView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox minHeight="-Infinity" minWidth="-Infinity" styleClass="grey, spacing" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandErrorView">
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
   <VBox fx:id="nonFieldErrorBox" prefWidth="100.0">
      <Label styleClass="text2" text="Non-Field Problems" />
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
   </VBox>
   <VBox fx:id="fieldErrorBox" prefWidth="100.0">
      <Label styleClass="text2" text="Field Problems" />
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
   </VBox>
</VBox>
```
###### \resources\view\CommandFeedbackView.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.TextFlow?>

<AnchorPane stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandFeedbackView">
   <children>
      <TextFlow fx:id="commandFeedbackTextFlow" layoutX="10.0" layoutY="10.0" lineSpacing="2.0" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" styleClass="commandFeedback" />
      <Label fx:id="commandFeedbackLabel" styleClass="commandFeedback" wrapText="true" />
   </children>
</AnchorPane>
```
###### \resources\view\CommandInputView.fxml
``` fxml

<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandInputView">
   <TextArea fx:id="commandTextField" minHeight="-Infinity" minWidth="-Infinity" prefHeight="40.0" prefWidth="480.0"
             promptText="Your wish is my command. Enter your command." styleClass="commandInput" wrapText="true"/>
</AnchorPane>
```
###### \resources\view\CommandPreviewView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox minHeight="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.CommandPreviewView">
  <GridPane fx:id="previewGrid" styleClass="gridPanel">
     <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" maxWidth="-Infinity" minWidth="-Infinity" />
        <ColumnConstraints hgrow="SOMETIMES" minWidth="-Infinity" />
     </columnConstraints>
     <rowConstraints>
        <RowConstraints vgrow="SOMETIMES" />
     </rowConstraints>
     <VBox.margin>
        <Insets top="8.0" />
     </VBox.margin>
  </GridPane>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \resources\view\EmptyListView.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="emptyListView" alignment="CENTER" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.EmptyListView">
   <children>
      <ImageView fx:id="emptyListImage" fitHeight="300.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true">
         <image>
            <Image url="@../images/emoji-default.png" />
         </image>
         <VBox.margin>
            <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
         </VBox.margin>
      </ImageView>
      <Label fx:id="emptyListLabel" style="-fx-text-fill: #00A1FF;" styleClass="text1" text="No Tasks in View" textAlignment="CENTER" wrapText="true" />
   </children>
</VBox>
```
###### \resources\view\FilterBarView.fxml
``` fxml

<?import javafx.scene.layout.FlowPane?>


<FlowPane fx:id="filterBarView" alignment="CENTER_LEFT" maxWidth="-Infinity" minWidth="-Infinity" styleClass="viewFilter" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.FilterBarView">
</FlowPane>
```
###### \resources\view\GlobalTagView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="globalTagViewPanel" minHeight="-Infinity" minWidth="-Infinity" style="-fx-background-color: #2D2D2D;" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.GlobalTagView">
   <HBox alignment="CENTER_LEFT" minHeight="-Infinity" minWidth="-Infinity" styleClass="spacing">
      <Label alignment="TOP_LEFT" styleClass="text2" text="Tags" />
      <Label styleClass="text4" text="All your tags in one place." />
      <VBox.margin>
         <Insets />
      </VBox.margin>
   </HBox>
   <FlowPane fx:id="tagFlowPane" minWidth="-Infinity">
      <VBox.margin>
         <Insets bottom="4.0" left="4.0" right="4.0" top="4.0" />
      </VBox.margin>
      <styleClass>
         <String fx:value="spacing" />
         <String fx:value="gridPanel" />
      </styleClass></FlowPane>
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
</VBox>
```
###### \resources\view\HelpView.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="helpPanelView" minHeight="-Infinity" minWidth="-Infinity" styleClass="spacing, grey" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.HelpView">
   <padding>
      <Insets bottom="8.0" left="8.0" right="8.0" top="8.0" />
   </padding>
   <HBox alignment="CENTER_LEFT" styleClass="spacing">
      <Label styleClass="text2" text="Help" />
      <Label styleClass="text4" text="Need help? Try these commands out to get started." />
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
</VBox>
```
###### \resources\view\SearchStatusView.fxml
``` fxml

<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Text?>

<StackPane fx:id="searchStatusView" maxWidth="Infinity" prefHeight="24" styleClass="searchStatus" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.102" fx:controller="seedu.todo.ui.view.SearchStatusView">
    <HBox maxWidth="Infinity" minWidth="0" spacing="2.0" StackPane.alignment="CENTER_LEFT">
        <Text styleClass="searchLabel">Searching for:</Text>
        <Text fx:id="searchTerm" styleClass="searchTerm" text="search terms" />
    </HBox>
    <Text fx:id="searchCount" styleClass="searchCount" text="0 results found" StackPane.alignment="CENTER_RIGHT" />
</StackPane>
```
###### \resources\view\TaskCardView.fxml
``` fxml

<?import java.lang.String?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="taskCard" alignment="CENTER_LEFT" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefWidth="500.0" spacing="2.0" styleClass="taskCard" stylesheets="@../style/DefaultStyle.css" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <BorderPane>
         <right>
            <ImageView fx:id="pinImage" fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true" styleClass="pinImage" BorderPane.alignment="CENTER" />
         </right>
         <center>
            <FlowPane fx:id="titleFlowPane" alignment="CENTER_LEFT" hgap="8.0" maxHeight="-Infinity" minHeight="-Infinity" prefWrapLength="0.0" vgap="2.0" BorderPane.alignment="CENTER">
               <children>
                  <Label fx:id="titleLabel" maxWidth="-Infinity" minHeight="-Infinity" styleClass="titleLabel" text="1. $Tasking Title$">
                  </Label>
                  <Label fx:id="typeLabel" text="Event" textFill="WHITE">
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <styleClass>
                        <String fx:value="highlightedBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
                  <Label fx:id="moreInfoLabel" text="•••" textFill="WHITE">
                     <padding>
                        <Insets left="8.0" right="8.0" />
                     </padding>
                     <styleClass>
                        <String fx:value="lightBackground" />
                        <String fx:value="roundLabel" />
                     </styleClass>
                  </Label>
               </children>
            </FlowPane>
         </center>
      </BorderPane>
      <HBox fx:id="descriptionBox" spacing="8.0">
         <children>
            <Label fx:id="descriptionDecoration" maxHeight="1.7976931348623157E308" maxWidth="4.0" minWidth="4.0">
               <styleClass>
                  <String fx:value="highlightedBackground" />
                  <String fx:value="roundLabel" />
               </styleClass></Label>
            <Label fx:id="descriptionLabel" lineSpacing="1.0" styleClass="descriptionLabel" text="Task description. $Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam id iaculis arcu. Curabitur at dapibus magna, at molestie diam. Integer posuere.$" wrapText="true">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
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
      <FlowPane hgap="8.0" styleClass="spacing">
         <children>
            <HBox fx:id="dateBox" alignment="CENTER" maxWidth="-Infinity" minWidth="-Infinity" styleClass="spacingSmall">
               <children>
                  <ImageView fx:id="dateImage" fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" styleClass="dateImage">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </ImageView>
                  <Label fx:id="dateLabel" maxHeight="1.7976931348623157E308" styleClass="footnoteLabel" text="from $date missing$">
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
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </Label>
               </children>
            </HBox>
         </children>
         <VBox.margin>
            <Insets bottom="2.0" />
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
###### \resources\view\TodoListView.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox stylesheets="@../style/DefaultStyle.css" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.todo.ui.view.TodoListView">
    <children>
        <ListView fx:id="todoListView" prefHeight="5000.0" prefWidth="640.0" />
    </children>
</VBox>
```
