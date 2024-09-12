package controller.cusromer;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public TableColumn colSalary;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public DatePicker dateDob;
    public ComboBox cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customer> tblCustomers;

    CustomerService service =CustomerController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Ms");
        cmbTitle.setItems(titles);
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        loadTable();
    }

    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(), txtProvince.getText(),
                txtPostalCode.getText()
        );
        ;
        if(service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added :(").show();
        }
//INSERT INTO Customer VALUES('C001','Mr','Danapala','1981-2-6',40000,'No.20 Walana','Panadura','Western',12500);

    }


    private void loadTable() {
        ObservableList<Customer> customerObservableList =service.getAll();
        tblCustomers.setItems(customerObservableList);
// ----------------------------------------------------------------------------------


    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {

        if (service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }
}


//com.mysql.cj.jdbc.ConnectionImpl@619eb068
//com.mysql.cj.jdbc.ConnectionImpl@2b3349a2
