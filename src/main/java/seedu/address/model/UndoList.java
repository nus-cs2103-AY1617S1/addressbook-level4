package seedu.address.model;

public class UndoList {
    
    public UndoNode head;
    public UndoNode tail;
    private int size;
    
    public UndoList(){
        head = null;
        tail = head;
        size = 0;
    }
    
    public void addToList(String text){
        if (head == null){ //currently empty
            head = new UndoNode(text, head);
            tail = head;
            size++;
        }
        else if (size < 3){
            tail.setNext(new UndoNode(text, head));
            tail = tail.getNext();
        }
    }
    
}
