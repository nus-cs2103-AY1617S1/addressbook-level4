# A0143641Mreused
###### \java\guitests\DeleteCommandTest.java
``` java
    
    /**
     * Runs the delete command to delete the item at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first item in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of items (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestItem[] currentList) {
        TestItem itemToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestItem[] expectedRemainder = TestUtil.removeItemFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);
        
        //confirm the list now contains all previous items except the deleted item
        assertTrue(shortItemListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete));
    }

}
```
