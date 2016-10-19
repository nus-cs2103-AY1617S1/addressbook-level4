package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;

/**
 *
 */
public class TypicalTestItems {

    public static TestItem always, bags, cs2103, dover, eating, frolick, grass, help, indeed;

    public TypicalTestItems() {
        try {
            always  = new ItemBuilder().withDescription("Always brush teeth").build();
            bags    = new ItemBuilder().withDescription("Pack bag with the thing that I always need to bring").build();
            cs2103  = new ItemBuilder().withDescription("Finish my CS2103 homework").build();
            dover   = new ItemBuilder().withDescription("Dover Road").build();
            eating  = new ItemBuilder().withDescription("eat 1 child").build();
            frolick = new ItemBuilder().withDescription("frolick in the grass").build();
            grass   = new ItemBuilder().withDescription("You are allergic to grass").build();
            //Manually added
            help    = new ItemBuilder().withDescription("Read help instructions").build();
            indeed  = new ItemBuilder().withDescription("Indeed this is a test item").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook tb) {

        try {
            tb.addItem(new Item(always));
            tb.addItem(new Item(bags));
            tb.addItem(new Item(cs2103));
            tb.addItem(new Item(dover));
            tb.addItem(new Item(eating));
            tb.addItem(new Item(frolick));
            tb.addItem(new Item(grass));
        } catch (UniqueItemList.DuplicateItemException e) {
            assert false : "not possible";
        }
    }

    public TestItem[] getTypicalItems() {
        return new TestItem[]{always, bags, cs2103, dover, eating, frolick, grass};
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook tb = new TaskBook();
        loadTaskBookWithSampleData(tb);
        return tb;
    }
}
