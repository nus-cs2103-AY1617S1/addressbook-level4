package harmony.mastermind.memory;
import harmony.mastermind.ui.MainWindow;

import java.util.ArrayList;

import harmony.mastermind.storage.Storage;


public class Memory{
    public ArrayList<GenericMemory> memory = new ArrayList<GenericMemory>();

    //@@author A0143378Y
    // Adds a given GenericMemory item into the memory ArrayList
    // Updates quick view and saves to file
    public void add(GenericMemory item){
        assert item != null;

        String type = item.getType();

        memory.add(item);
        Storage.saveToStorage(this);
    }

    //@@author A0143378Y
    // Returns GenericMemory object at index
    public GenericMemory get(int index){
        return memory.get(index);
    }

    //@@author A0143378Y
    // Returns ArrayList of GenericMemory used by memory
    public ArrayList<GenericMemory> getList(){
        return memory;
    }

    //@@author A0143378Y
    // Returns number of GenericMemory in memory (size)
    public int getSize(){
        return memory.size();
    }

    //@@author A0143378Y
    // Loads GenericMemory from save file into memory
    public void loadFromFile(Memory memory){
        assert memory != null;
        Storage.checkForFileExists(memory);
    }

    //@@author A0143378Y
    // Removes given item from memory
    public void remove(GenericMemory item){
        assert item != null;

        String type = item.getType();

        memory.remove(item);
        Storage.saveToStorage(this);
    }

    //@@author A0143378Y
    // Swap out ArrayList used by memory with new given list
    public void setList(ArrayList<GenericMemory> list){
        memory = list;
    }

}
