package controller.item;

import controller.cusromer.CustomerController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;
import model.Item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML
    private TableColumn<?, ?> Qty;

    @FXML
    private Button btnAddCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private TableView<CartTM> cartTable;

    @FXML
    private ComboBox<String> comboBoxItemCode;

    @FXML
    private ComboBox<String> comboboxCusID;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> itemCode;

    @FXML
    private TableColumn<?, ?> total;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCusName;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private Label txtOrderID;

    @FXML
    private TextField txtQTY;

    @FXML
    private TextField txtStock;

    @FXML
    private Label txtTime;

    @FXML
    private TextField txtUnit;

    @FXML
    private Label txtTotallbl;

    @FXML
    private TableColumn<?, ?> unitPrize;


    @FXML
    void btnPlaceOrder(ActionEvent event) {

    }

    @FXML
    void comboBoxItemCode(ActionEvent event) {

    }

    @FXML
    void comboboxCusID(ActionEvent event) {

    }
    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();
        comboboxCusID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchCustomer(newVal);
            }
        });

        comboBoxItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchItem(newVal);
            }
        });
    }

    private void searchItem(String newVal) {
        Item item = ItemController.getInstance().searchItem(newVal);
        txtDescription.setText(item.getDescription());
        txtStock.setText(item.getQty().toString());
        txtUnit.setText(item.getUnitPrice().toString());
    }

    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);

        txtDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            txtDate.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerIds(){
        comboboxCusID.setItems(CustomerController.getInstance().getCustomerIds());
    }
    private void loadItemCodes(){
        comboBoxItemCode.setItems(ItemController.getInstance().getItemCodes());
    }

    private void searchCustomer(String customerID){
        Customer customer = CustomerController.getInstance().searchCustomer(customerID);
        txtCusName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        System.out.println(customerID);
    }

    public void btnAddCart(ActionEvent actionEvent) {
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemCode = comboBoxItemCode.getValue();
        String description = txtDescription.getText();
        Integer qty = Integer.parseInt(txtQTY.getText());
        Double unitPrice = Double.parseDouble(txtUnit.getText());
        Double total = unitPrice*qty;

        cartTMS.add(new CartTM(itemCode,description,qty,unitPrice,total));
        if (Integer.parseInt(txtStock.getText())<qty){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
        }else{
            cartTMS.add(new CartTM(itemCode,description,qty,unitPrice,total));
            calcNetTotal();
        }

        cartTable.setItems(cartTMS);
    }

    private void calcNetTotal(){
        Double total=0.0;

        for (CartTM cartTM: cartTMS){
            total+=cartTM.getTotal();
        }

        txtTotallbl.setText(total.toString()+"/=");

    }
}
