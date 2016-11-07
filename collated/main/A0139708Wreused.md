# A0139708Wreused
###### /java/w15c2/tusk/ui/AliasListPanel.java
``` java
    /**
     * Cell for AliasList to load AliasCard as graphic.
     */
    private class AliasListViewCell extends ListCell<Alias> {

        public AliasListViewCell() {
        }

        @Override
        protected void updateItem(Alias alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(AliasCard.load(alias, getIndex() + 1).getLayout());
            }
        }
    }


}
```
###### /java/w15c2/tusk/model/ModelManager.java
``` java
	private interface Expression {
        boolean satisfies(Task task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Task task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    private interface Qualifier {
        boolean run(Task task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(Task task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().getContent(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    @Override
    public boolean equals(Object obj){
    	if(obj == this){
    		return true;
    	}
    	if(!(obj instanceof ModelManager)){
    		return false;
    	}
    	ModelManager other = (ModelManager)obj;
    	Iterator<Task> itr = tasks.iterator();
    	boolean contains;
		while(itr.hasNext()){
			contains = false;
			Task task = itr.next();
			Iterator<Task> itr2 = other.getTasks().iterator();
			while(itr2.hasNext()){
				Task task2 = itr2.next();
				if(task2.getDescription().toString().equals(task.getDescription().toString())){
					contains = true;
					break;
				}
	    	}
			if(!contains){
				return false;
			}
		}
		
		Iterator<Alias> aliasItr = aliases.iterator();
		while(aliasItr.hasNext()){
			contains = false;
			Alias alias = aliasItr.next();
			Iterator<Alias> aliasItr2 = other.getAliasCollection().iterator();
			while(aliasItr2.hasNext()){
				Alias alias2 = aliasItr2.next();
				if(alias2.toString().equals(alias.toString())){
					contains = true;
					break;
				}
	    	}
			if(!contains){
				return false;
			}
		}
		return true;
    }
}
```
###### /resources/view/AliasWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="400.0" maxWidth="300.0" minHeight="-Infinity" minWidth="255.0" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1" fx:controller="w15c2.tusk.ui.AliasWindow">
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
   <children>
      <VBox fx:id="aliasList" style="-fx-background-color: #633914;">
          <padding>
              <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
          </padding>
         <children>
            <AnchorPane fx:id="aliasListPanelPlaceholder" VBox.vgrow="ALWAYS" />
         </children>
         <stylesheets>
            <URL value="@DarkTheme.css" />
            <URL value="@Extensions.css" />
         </stylesheets>
      </VBox>
   </children>
</VBox>
```
###### /resources/view/AliasListPanel.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1" fx:controller="w15c2.tusk.ui.AliasListPanel">
    <stylesheets>
        <URL value="@DarkTheme.css" />
        <URL value="@Extensions.css" />
    </stylesheets>
    <children>
        <ListView fx:id="aliasListView" styleClass="anchor-pane" style="-fx-border-color: #633914;" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
