module Inventory.Manager {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens InventoryManager;
    opens InventoryManager.View_Controller;
    opens InventoryManager.Model;
}