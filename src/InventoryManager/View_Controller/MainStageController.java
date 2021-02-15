package InventoryManager.View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class MainStageController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button overviewBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button aboutBtn;


    //Click Inventory button at startup
    public void initialize(){
        inventoryBtn.fire();
    }


    //Switch to InventoryPage when clicked
    @FXML
    void inventoryBtnOnClick(ActionEvent event) throws Exception{
        inventoryActive();
        Parent root = FXMLLoader.load(getClass().getResource("InventoryPage.fxml"));
        borderPane.setCenter(root);
    }


    //Switch to OverviewPage when clicked
    @FXML
    void OverviewBtnOnClick(ActionEvent event) throws Exception{
        overviewActive();
        Parent root = FXMLLoader.load(getClass().getResource("OverviewPage.fxml"));
        borderPane.setCenter(root);
    }


    //Switch to SettingsPage when clicked
    @FXML
    void settingsBtnOnClick(ActionEvent event) throws Exception{
        settingsActive();
        Parent root = FXMLLoader.load(getClass().getResource("SettingsPage.fxml"));
        borderPane.setCenter(root);
    }


    //Switch to AboutPage when clicked
    @FXML
    void aboutBtnOnClick(ActionEvent event) throws Exception{
        aboutActive();
        Parent root = FXMLLoader.load(getClass().getResource("AboutPage.fxml"));
        borderPane.setCenter(root);
    }


    void inventoryActive(){
        inventoryBtn.setDisable(true);
        overviewBtn.setDisable(false);
        settingsBtn.setDisable(false);
        aboutBtn.setDisable(false);
    }

    void overviewActive(){
        inventoryBtn.setDisable(false);
        overviewBtn.setDisable(true);
        settingsBtn.setDisable(false);
        aboutBtn.setDisable(false);
    }

    void settingsActive(){
        inventoryBtn.setDisable(false);
        overviewBtn.setDisable(false);
        settingsBtn.setDisable(true);
        aboutBtn.setDisable(false);
    }

    void aboutActive(){
        inventoryBtn.setDisable(false);
        overviewBtn.setDisable(false);
        settingsBtn.setDisable(false);
        aboutBtn.setDisable(true);
    }

}
