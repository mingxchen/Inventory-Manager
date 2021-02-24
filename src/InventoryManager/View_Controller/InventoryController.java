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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class InventoryController{

    @FXML
    private ComboBox<String> styleCombo, colorCombo, sizeCombo;

    @FXML
    private Button searchBtn, exportBtn;

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


    //Preset dropdown list items
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
            tableList.clear();
            styleColorSizeSearch(); //Search when all three fields are entered
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() == 0){
            tableList.clear();
            styleColorSearch();     //Search when style and color are entered
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() != 0){
            tableList.clear();
            styleSizeSearch();      //Search when style and size are entered
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() != 0){
            tableList.clear();
            colorSizeSearch();      //Search when color and size are entered
        }
        else if(styleCombo.getEditor().getText().length() != 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() == 0){
            tableList.clear();
            styleSearch();          //Search when style is entered
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() != 0 && sizeCombo.getEditor().getText().length() == 0){
            tableList.clear();
            colorSearch();          //Search when color is entered
        }
        else if(styleCombo.getEditor().getText().length() == 0 && colorCombo.getEditor().getText().length() == 0 && sizeCombo.getEditor().getText().length() != 0){
            tableList.clear();
            sizeSearch();           //Search when size is entered
        }
        else{
            tableList.clear();
            allSearch();            //Search when nothing is entered
        }
    }

    void styleColorSizeSearch() throws SQLException{
        Connection con = DBConnector.getConnection();
        String query = "SELECT * FROM Stock WHERE Style = ? AND Color = ? AND Size = ?";
        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, styleCombo.getEditor().getText().strip());
        prepStmt.setString(2, colorCombo.getEditor().getText().strip());
        prepStmt.setString(3, sizeCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, styleCombo.getEditor().getText().strip());
        prepStmt.setString(2, colorCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, styleCombo.getEditor().getText().strip());
        prepStmt.setString(2, sizeCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, colorCombo.getEditor().getText().strip());
        prepStmt.setString(2, sizeCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, styleCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, colorCombo.getEditor().getText().strip());
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
        prepStmt.setString(1, sizeCombo.getEditor().getText().strip());
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


    //Export data from tableview to .csv file
    @FXML
    void exportBtnClick() throws Exception{
        ObservableList<Stock> stocks = searchTable.getItems();
        Writer writer = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Stocks");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".csv", ".csv"));

        try{
            File file = fileChooser.showSaveDialog(new Stage());
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("ID, Style, Color, Size, Quantity, \n");

            for(Stock s : stocks){
                String text = s.getId() + "," + s.getStyle() + "," + s.getColor() + "," +
                        s.getSize()  + "," + s.getQuantity() + "\n";
                writer.write(text);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Data successfully exported!");
            alert.showAndWait();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }


    //Edit quantity cell when double clicked
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


    //Add Stock by descriptions to the database
    @FXML
    void addBtnClick(ActionEvent event) throws SQLException{
        if(manageStyleCombo.getEditor().getText().length() != 0 && manageColorCombo.getEditor().getText().length() != 0 &&
                manageSizeCombo.getEditor().getText().length() != 0 && manageQuantityCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "INSERT INTO Stock (Style, Color, Size, Quantity) " +
                            "VALUES (?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, manageStyleCombo.getEditor().getText().strip());
            prepStmt.setString(2, manageColorCombo.getEditor().getText().strip());
            prepStmt.setString(3, manageSizeCombo.getEditor().getText().strip());
            prepStmt.setString(4, manageQuantityCombo.getEditor().getText().strip()); //Int

            try {
                prepStmt.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Add Successful!");
                alert.showAndWait();
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


    //Edit Stock by descriptions to the database
    @FXML
    void editBtnClick() throws SQLException{
        if(manageStyleCombo.getEditor().getText().length() != 0 && manageColorCombo.getEditor().getText().length() != 0 &&
                manageSizeCombo.getEditor().getText().length() != 0 && manageQuantityCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "UPDATE Stock SET Quantity = ? " +
                            "WHERE Style = ? AND Color = ? AND Size = ? ";
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, manageQuantityCombo.getEditor().getText().strip()); //Int
            prepStmt.setString(2, manageStyleCombo.getEditor().getText().strip());
            prepStmt.setString(3, manageColorCombo.getEditor().getText().strip());
            prepStmt.setString(4, manageSizeCombo.getEditor().getText().strip());

            try {
                if((!(prepStmt.executeUpdate() >= 1))){     //If less than 1 row affected
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Edit, Item not in Stock!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Edit Successful!");
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


    //Delete Stock by descriptions from the database
    @FXML
    void deleteBtnClick(ActionEvent event) throws SQLException{
        if(delStyleCombo.getEditor().getText().length() != 0 && delColorCombo.getEditor().getText().length() != 0
                && delSizeCombo.getEditor().getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "DELETE FROM Stock WHERE Id > 0 " +
                            "AND Style = ? AND Color = ? AND Size = ? ";

            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, delStyleCombo.getEditor().getText().strip());
            prepStmt.setString(2, delColorCombo.getEditor().getText().strip());
            prepStmt.setString(3, delSizeCombo.getEditor().getText().strip());

            if ((!(prepStmt.executeUpdate() >= 1))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Delete, Item not in Stock!");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Delete Successful!");
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


    //Delete Stock by ID from the database
    @FXML
    void deleteByIdClick(ActionEvent event) throws SQLException{
        if(delIdField.getText().length() != 0){
            Connection con = DBConnector.getConnection();

            String query = "DELETE FROM Stock WHERE Id = ?";

            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, delIdField.getText().strip());

            if ((!(prepStmt.executeUpdate() >= 1))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Delete, No such ID!");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Delete Successful!");
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