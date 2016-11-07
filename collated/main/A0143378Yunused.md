# A0143378Yunused
###### /java/harmony/mastermind/logic/commands/History.java
``` java
    // Retrieves most recent snapshot in the forward stack and swap it with current memory. Current memory gets pushed into back stack
    public static void redo(Memory memory) {
        if (forward.isEmpty() || (forward.peek() == null)) {
            System.out.println("Nothing to redo!");
            return;
        }
        
        redoMemorySwap(memory);
        StorageMemory.saveToStorage(memory);
        System.out.println("Redo successful.");
    }
    
```
###### /java/harmony/mastermind/ui/HelpPopup.java
``` java
     * Styling for TextArea. Unused because we initially use a TextArea contained in a popup.
     * But we changed it to a TableView instead.
     */
    /*
    public void properties() { 
        //Setting up the width and height
        content.setPrefHeight(DEFAULT_HEIGHT);
        content.setPrefWidth(DEFAULT_WIDTH);
        
        //Setting up wrapping of text in the content box 
        content.setWrapText(true);
        
        //Setting up the background, font and borders
        content.setStyle("-fx-background-color: #00BFFF;"
                + "-fx-padding:10px;"
                + "-fx-text-fill: #000080;"
                + "-fx-font-family: Fantasy;"
                + "-fx-alignment: center"
                + "-fx-font-size: 20px"
                );
    }
    */

```
