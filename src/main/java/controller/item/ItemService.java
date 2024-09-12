package controller.item;

import javafx.collections.ObservableList;
import model.Customer;
import model.Item;

public interface ItemService {
    boolean addItem(Item item);
    boolean deleteItem(String id);
    ObservableList<Item> getAll();
    boolean updateUpdate(Item item);
    Item searchItem(String itemCode);
}
