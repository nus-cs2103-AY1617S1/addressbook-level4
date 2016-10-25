package seedu.address.model.undo;

import seedu.address.model.task.ReadOnlyTask;

public class UndoList {

    public UndoNode head;
    public UndoNode tail;
    private int size;

    public UndoList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addToList(String cmd, ReadOnlyTask initData, ReadOnlyTask finalData){
        if (head == null){ //currently empty
            head = new UndoNode(cmd, initData, finalData, null, null);
            tail = head;
            size++;
        }
        else if (size < 3){
            tail.setNext(new UndoNode(cmd, initData, finalData, null, null));
            tail = tail.getNext();
            size++;
        }
        else if (size == 3){
            head = head.getNext();
            tail.setNext(new UndoNode(cmd, initData, finalData, head, tail));
            tail = tail.getNext();
        }
    }

    public UndoTask removeLast(){
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

}

class UndoNode {
    
    public UndoTask data;
    public UndoNode next, prev;
    
    public UndoNode(String cmd, ReadOnlyTask initData, ReadOnlyTask finalData, UndoNode next, UndoNode prev){
        this.data = new UndoTask(cmd, initData, finalData);
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
