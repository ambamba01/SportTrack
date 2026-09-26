package ulb.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A listener for ComboBoxes that adds auto-completion functionality, allowing users to type and filter items.
 *
 * @param <T> The type of items in the ComboBox.
 */
public class AutoCompleteComboBoxListener<T> {

    private final ComboBox<T> comboBox;
    private String filter = "";
    private final ObservableList<T> originalItems;
    /**
     * Attaches the auto-complete functionality to the specified ComboBox.
     *
     * @param comboBox The ComboBox to attach the listener to.
     */
    public AutoCompleteComboBoxListener(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        originalItems = FXCollections.observableArrayList(comboBox.getItems());

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(this::handleOnKeyReleased);
    }

    /**
     * Handles key release events on the ComboBox, filtering items based on user input.
     *
     * @param event The key event that triggered the handler.
     */
    private void handleOnKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        // Append/remove characters to/from filter based on key pressed.
        if (code.isLetterKey() || code.isDigitKey() || code == KeyCode.SPACE) {
            filter += event.getText();
        } else if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
            filter = filter.substring(0, filter.length() - 1);
        }
        // Show the filtered list if filter is not empty, else reset.
        if (code == KeyCode.ENTER || code == KeyCode.TAB) {
            filterComboBoxItems();
            comboBox.show();
        }

        if (filter.length() == 0) {
            resetComboBox();
        } else {
            filterComboBoxItems();
            comboBox.show();
        }
    }

    /**
     * Filters the ComboBox items based on the current filter text.
     */
    private void filterComboBoxItems() {
        if (filter.length() == 0) {
            resetComboBox();
            return;
        }

        ObservableList<T> filteredList = FXCollections.observableArrayList();
        for (T item : originalItems) {
            if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
                filteredList.add(item);
            }
        }
        comboBox.setItems(filteredList);
    }

    /**
     * Resets the ComboBox to show all original items.
     */
    private void resetComboBox() {
        comboBox.setItems(originalItems);
        comboBox.hide();
    }
}
