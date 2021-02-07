package InventoryManager.View_Controller;

import InventoryManager.DBConnector;
import InventoryManager.Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class InventoryController{

    @FXML
    private ComboBox<String> styleCombo, colorCombo, sizeCombo;

    @FXML
    private Button searchBtn, clearBtn;

    @FXML
    private TableView<Stock> searchTable;

    @FXML
    private TableColumn<Stock, Integer> idColumn, quantityColumn;

    @FXML
    private TableColumn<Stock, String> styleColumn, colorColumn, sizeColumn;

    @FXML
    private ComboBox<String> manageStyleCombo, manageColorCombo, manageSizeCombo;

    @FXML
    private ComboBox<Integer> manageQuantityCombo;

    @FXML
    private Button addStockBtn, editQuantityBtn;

    @FXML
    private ComboBox<String> delStyleCombo, delColorCombo, delSizeCombo;

    @FXML
    private TextField delIdField;

    @FXML
    private Button delStockBtn, delByIDBtn;

    @FXML
    private Label statusLabel;

    private ObservableList<Stock> tableList = FXCollections.observableArrayList();

    private ObservableList<String> styleList = FXCollections.observableArrayList(
            "Men", "Women", "Youth", "Toddler", "Baby T-Shirt", "Baby Onesie");
    private ObservableList<String> colorList = FXCollections.observableArrayList(
            "White", "Black", "Red", "Heather Grey", "Light Pink", "Light Blue");
    private ObservableList<String> sizeList = FXCollections.observableArrayList(
            "XS", "S", "M", "L", "XL", "2XL", "3XL", "4XL", "2T", "3T", "4T", "5T", "6T",
            "6M", "12M", "18M", "24M", "NB");
    private ObservableList<Integer> numList = FXCollections.observableArrayList(1, 2, 3, 4,
            5, 6, 7, 8, 9, 10);


    public void initialize(){
        styleCombo.setItems(styleList);
        colorCombo.setItems(colorList);
        sizeCombo.setItems(sizeList);
        manageStyleCombo.setItems(styleList);
        manageColorCombo.setItems(colorList);
        manageSizeCombo.setItems(sizeList);
        manageQuantityCombo.setItems(numList);
        delStyleCombo.setItems(styleList);
        delColorCombo.setItems(colorList);
        delSizeCombo.setItems(sizeList);
    }


    @FXML
    void searchBtnClick(ActionEvent event) throws SQLException{
        if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() != 0){
            styleColorSizeSearch();
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() == 0){
            styleColorSearch();
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() != 0){
            styleSizeSearch();
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() != 0){
            colorSizeSearch();
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() == 0){
            styleSearch();
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() == 0){
            colorSearch();
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() != 0){
            sizeSearch();
        }
        else{
            allSearch();
        }
    }

    void styleColorSizeSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Style = ? AND Color = ? AND Size = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, styleCombo.getEditor().getText());
        prepStmt.setString(2, colorCombo.getEditor().getText());
        prepStmt.setString(3, sizeCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }
        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void styleColorSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Style = ? AND Color = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, styleCombo.getEditor().getText());
        prepStmt.setString(2, colorCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void styleSizeSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Style = ? AND Size = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, styleCombo.getEditor().getText());
        prepStmt.setString(2, sizeCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void colorSizeSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Color = ? AND Size = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, colorCombo.getEditor().getText());
        prepStmt.setString(2, sizeCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void styleSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Style = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, styleCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void colorSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Color = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, colorCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void sizeSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Size = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, sizeCombo.getEditor().getText());
        ResultSet rs = prepStmt.executeQuery();
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }

    void allSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Stock");
        if(!rs.isBeforeFirst()){
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Stock Found!");
            alert.showAndWait();
            return;
        }

        while(rs.next()){
            tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                    rs.getString("Color"), rs.getString("Size"),
                    rs.getInt("Quantity")));
        }
        populateTable();
        con.close();
    }


    //Clear text fields and search history
    @FXML
    void clearBtnClick(ActionEvent event) {
        tableList.clear();
    }


    @FXML
    void EditQuantityCell(TableColumn.CellEditEvent event) throws SQLException{
        Stock stockSelected = searchTable.getSelectionModel().getSelectedItem();
        Connection con = DBConnector.getConnection();

        String query = "UPDATE Stock SET Quantity = ? " +
                "WHERE Style = ? AND Color = ? AND Size = ? ";

        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, event.getNewValue().toString()); //Int
        prepStmt.setString(2, stockSelected.getStyle());
        prepStmt.setString(3, stockSelected.getColor());
        prepStmt.setString(4, stockSelected.getSize());

        prepStmt.executeUpdate();
        con.close();
    }


    @FXML
    void addBtnClick(ActionEvent event) throws SQLException{
        if(manageStyleCombo.getEditor().getText().length() != 0 && manageColorCombo.getEditor().getText().length() != 0 &&
                manageSizeCombo.getEditor().getText().length() != 0 && manageQuantityCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "INSERT INTO Stock (Style, Color, Size, Quantity) " +
                            "VALUES (?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, manageStyleCombo.getEditor().getText());
            prepStmt.setString(2, manageColorCombo.getEditor().getText());
            prepStmt.setString(3, manageSizeCombo.getEditor().getText());
            prepStmt.setString(4, manageQuantityCombo.getEditor().getText()); //Int

            try {
                prepStmt.executeUpdate();
            } catch (SQLException e){
                if(e != null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Entry, Quantity Must be A Number!");
                    alert.showAndWait();
                }
            }
            con.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the required Fields!");
            alert.showAndWait();
        }
    }


    @FXML
    void editBtnClick() throws SQLException{
        if(manageStyleCombo.getEditor().getText().length() != 0 && manageColorCombo.getEditor().getText().length() != 0 &&
                manageSizeCombo.getEditor().getText().length() != 0 && manageQuantityCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "UPDATE Stock SET Quantity = ? " +
                            "WHERE Style = ? AND Color = ? AND Size = ? ";
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, manageQuantityCombo.getEditor().getText()); //Int
            prepStmt.setString(2, manageStyleCombo.getEditor().getText());
            prepStmt.setString(3, manageColorCombo.getEditor().getText());
            prepStmt.setString(4, manageSizeCombo.getEditor().getText());

            try {
                if((!(prepStmt.executeUpdate() >= 1))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Edit, Item not in Stock!");
                    alert.showAndWait();
                }
            } catch (SQLException e){
                if(e != null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Edit, Quantity Must Be A Number!");
                    alert.showAndWait();
                }
            }
            con.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the required Fields!");
            alert.showAndWait();
        }
    }


    @FXML
    void deleteBtnClick(ActionEvent event) throws SQLException{
        if(delStyleCombo.getEditor().getText().length() != 0 && delColorCombo.getEditor().getText().length() != 0
                && delSizeCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "DELETE FROM Stock WHERE Id > 0 " +
                            "AND Style = ? AND Color = ? AND Size = ? ";

            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, delStyleCombo.getEditor().getText());
            prepStmt.setString(2, delColorCombo.getEditor().getText());
            prepStmt.setString(3, delSizeCombo.getEditor().getText());

            if ((!(prepStmt.executeUpdate() >= 1))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Delete, Double Check Descriptions!");
                alert.showAndWait();
            }
            con.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the required Fields!");
            alert.showAndWait();
        }
    }


    @FXML
    void deleteByIdClick(ActionEvent event) throws SQLException{
        if(delIdField.getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "DELETE FROM Stock WHERE Id = ?";

            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, delIdField.getText());

            if ((!(prepStmt.executeUpdate() >= 1))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Delete, Double Check ID!");
                alert.showAndWait();
            }
            con.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please enter an ID!");
            alert.showAndWait();
        }
    }


    //Populate tableview from observable list
    void populateTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<>("Style"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("Color"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("Size"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        searchTable.setItems(tableList);

        //Make Quantity column editable
        searchTable.setEditable(true);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        //Allow selection of multiple rows
        searchTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}