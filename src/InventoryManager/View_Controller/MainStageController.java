package InventoryManager.View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("InventoryPage.fxml").openStream());
        borderPane.setCenter(fxmlLoader.getRoot());
    }


    //Switch to OverviewPage when clicked
    @FXML
    void OverviewBtnOnClick(ActionEvent event) throws Exception{
        overviewActive();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("OverviewPage.fxml").openStream());
        borderPane.setCenter(fxmlLoader.getRoot());
    }

    //Switch to SettingsPage when clicked
    @FXML
    void settingsBtnOnClick(ActionEvent event) throws Exception{
        settingsActive();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("SettingsPage.fxml").openStream());
        borderPane.setCenter(fxmlLoader.getRoot());
    }

    //Switch to AboutPage when clicked
    @FXML
    void aboutBtnOnClick(ActionEvent event) throws Exception{
        aboutActive();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("AboutPage.fxml").openStream());
        borderPane.setCenter(fxmlLoader.getRoot());
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
