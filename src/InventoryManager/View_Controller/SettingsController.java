package InventoryManager.View_Controller;

import InventoryManager.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class SettingsController {

    @FXML
    private Button resetBtn;

    @FXML
    void resetBtnClick(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure? All Data Will Be Lost!");
        Optional<ButtonType> options = alert.showAndWait();
        if(options.get() == ButtonType.CANCEL){
            return;
        }
        else if(options.get() == ButtonType.OK){
            Connection con = DBConnector.getConnection();

            String query = "TRUNCATE TABLE Stock";
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.execute();

            con.close();
        }
    }

}
