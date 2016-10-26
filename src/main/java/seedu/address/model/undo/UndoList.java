package seedu.address.model.undo;

import seedu.address.model.task.ReadOnlyTask;

//@@author A0139145E
/*
 * Implements a circular linked list to store the UndoTasks (up to 3 actions)
 * using Last-In-First-Out (LIFO)
 */
public class UndoList {

    public UndoNode head;
    public UndoNode tail;
    private int size;

    public UndoList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /*
     * Adds a Undo action to the front of the list.
     */
    public void addToFront(String cmd, ReadOnlyTask postData, ReadOnlyTask preData){
        if (size == 0){ //currently empty
            head = new UndoNode(cmd, postData, preData, head, tail);
            tail = head;
            size++;
        }
        else if (size < 3){
            tail.setNext(new UndoNode(cmd, postData, preData, head, tail));
            tail = tail.getNext();
            size++;
        }
        else if (size == 3){
            head = head.getNext();
            tail.setNext(new UndoNode(cmd, postData, preData, head, tail));
            tail = tail.getNext();
        }
    }

    /**
     * Removes a Undo action to the front of the list.
     * @return UndoTask, or null if no actions to undo
     * 
     **/
    public UndoTask removeFromFront(){
        if (size == 0) {
            return null;
        }
        UndoNode toRm = tail;
        if (size == 1){
            head = null;
            tail = null;
            size--;
            return toRm.getUndoData();
        }
        else {
            tail = tail.getPrev();
            tail.setNext(head);
            size--;
            return toRm.getUndoData();
        }
    }

    @Override
    public String toString(){
        StringBuffer value = new StringBuffer();
        UndoNode temp = tail;
        if (head == null){
            value.append("\n");
        }
        else {
            while (temp != head){
                value.append("--> ").append(temp.getUndoData().toString()).append("\n");
                temp = temp.getPrev();
            }
            value.append("--> ").append(temp.getUndoData().toString()).append("\n");
        }
        return value.toString();
    }

    /*
     * List Node class for the UndoList circular linked list
     */
    class UndoNode {

        public UndoTask data;
        public UndoNode next, prev;

        /*
         * Initialises a UndoNode
         * cmd. postData cannot be null
         */
        public UndoNode(String cmd, ReadOnlyTask postData, ReadOnlyTask preData, UndoNode next, UndoNode prev){
            this.data = new UndoTask(cmd, postData, preData);
            this.next = next;
            this.prev = prev;
        }

        UndoNode getNext(){
            return this.next;
        }

        UndoNode getPrev(){
            return this.prev;
        }

        UndoTask getUndoData(){
            return this.data;
        }

        void setNext(UndoNode next){
            this.next = next;
        }

        void setPrev(UndoNode prev){
            this.prev = prev;
        }
    }
}
//@@author
