package InventoryManager.View_Controller;

import InventoryManager.DBConnector;
import InventoryManager.Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class OverviewController {

    @FXML
    private TableView<Stock> overviewTable;

    @FXML
    private TableColumn<Stock, Integer> idColumn;

    @FXML
    private TableColumn<Stock, String> styleColumn, colorColumn, sizeColumn;

    @FXML
    private TableColumn<Stock, Integer> quantityColumn;

    private ObservableList<Stock> tableList = FXCollections.observableArrayList();


    //FXMLLoader automatically calls no-arg initialize() method.
    public void initialize() {
        //Connect DB and store data in observable list
        try {
            Connection con = DBConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Stock " +
                                                                    "WHERE Quantity >= 1");
            while (rs.next()) {
                tableList.add(new Stock(rs.getInt("Id"), rs.getString("Style"),
                            rs.getString("Color"), rs.getString("Size"),
                            rs.getInt("Quantity")));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        populateTable();
    }

    //Edit quantity cell when double clicked
    @FXML
    void EditQuantityCell(TableColumn.CellEditEvent event) throws SQLException{
        Stock stockSelected = overviewTable.getSelectionModel().getSelectedItem();
        Connection con = DBConnector.getConnection();

        String query = "UPDATE Stock SET Quantity = ? " +
                        "WHERE Style = ? AND Color = ? AND Size = ? ";

        PreparedStatement prepStmt = con.prepareStatement(query);
        prepStmt.setString(1, event.getNewValue().toString());
        prepStmt.setString(2, stockSelected.getStyle());
        prepStmt.setString(3, stockSelected.getColor());
        prepStmt.setString(4, stockSelected.getSize());

        prepStmt.executeUpdate();
        con.close();
    }


    //Populate tableview from observable list
    void populateTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<>("Style"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("Color"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("Size"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        overviewTable.setItems(tableList);

        //Make Quantity column editable
        overviewTable.setEditable(true);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        //Allow selection of multiple rows
        overviewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}
