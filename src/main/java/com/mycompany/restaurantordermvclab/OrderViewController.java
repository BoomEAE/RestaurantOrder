/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurantordermvclab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
/**
 * FXML Controller class
 *
 * @author 2539556
 */
public class OrderViewController implements Initializable {


    @FXML
    private ListView<String> entreeListView;
    @FXML
    private RadioButton coffeeRadioBtn;
    @FXML
    private RadioButton teaRadioBtn;
    @FXML
    private RadioButton sodaRadioBtn;
    @FXML
    private RadioButton juiceRadioBtn;
    @FXML
    private CheckBox requestFreeWaterBox;
    @FXML
    private TextField specialRequestField;
    @FXML
    private Label totalLabel;
    
    private ToggleGroup tg = new ToggleGroup();
    private static final Order order = new Order();
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Set the entrees in the listview
        String entrees[] = {"Chicken Salad", "Turkey Sandwich", "Vegetable Wrap", "Grilled Cheese"}; 
        entreeListView.setItems(FXCollections.observableArrayList(entrees));
        
        //ToggleGroup for RadioButtons
        coffeeRadioBtn.setToggleGroup(tg);
        teaRadioBtn.setToggleGroup(tg);
        sodaRadioBtn.setToggleGroup(tg);
        juiceRadioBtn.setToggleGroup(tg);
        
        //Listener to update the entree selection within the order, and calculate the price of the order
        entreeListView.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            try{
                if(!newValue.equals("")) order.setEntreeSelection(newValue);
            }catch(Exception e){
                order.setEntreeSelection("");
            }
            totalLabel.setText("Total : $" + String.format("%.2f", order.getTotalPrice()));
        });
        
        //Listener to update the drink selection within the order, and calculate the price of the order
        tg.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            if(newToggle != null) order.setDrinkSelection(((ToggleButton)newToggle).getText());
            else order.setDrinkSelection("");
            totalLabel.setText("Total : $" + String.format("%.2f", order.getTotalPrice()));
        });
        
        //Update the special request order
        specialRequestField.setOnKeyPressed(event -> order.setSpecialRequest(specialRequestField.getText()));
    }   
    
    @FXML
    private void onPlaceOrder(ActionEvent event) throws IOException{
        
        //Make sure that an entree and a drink have been selected to show the order summary
        if(!order.getDrinkSelection().equals("") && !order.getEntreeSelection().equals("")){
            Stage orderSummaryWindow = new Stage();
            Parent root = (new FXMLLoader(App.class.getResource("OrderSummaryView.fxml")).load());
            Label label = (Label)root.lookup("#entreeSelectedLabel");
            label.setText(order.getEntreeSelection());
            label = (Label)root.lookup("#entreePriceLabel");
            label.setText("$" + String.format("%.2f", order.getSelectedEntreePrice()));
            label = (Label)root.lookup("#drinkSelectedLabel");
            label.setText(order.getDrinkSelection());
            label = (Label)root.lookup("#drinkPriceLabel");
            label.setText("$" + String.format("%.2f", order.getSelectedDrinkPrice()));
            label = (Label)root.lookup("#specialRequestsLabel");
            if(specialRequestField.getText().length() >0)label.setText(specialRequestField.getText());
            else label.setText("None");
            label.setWrapText(true);
            label = (Label)root.lookup("#waterRequestLabel");
            if(requestFreeWaterBox.isSelected())label.setText("You've requested free water!");
            else label.setText("You've not requested free water.");
            label = (Label)root.lookup("#totalPriceLabel");
            label.setText("$" + String.format("%.2f", order.getTotalPrice()));
            orderSummaryWindow.setScene(new Scene(root));
            orderSummaryWindow.show();
        }else{
            
            //An alert shows if drink and/or entree have not been chosen
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Proceed With Order");
            if(order.getDrinkSelection().equals("")){
                errorAlert.setTitle("Drink Not Selected!!");
                errorAlert.setContentText("Drink not chosen");
            }else if(order.getEntreeSelection().equals("")){
                errorAlert.setTitle("Drink Not Selected!!");
                errorAlert.setContentText("Entree not chosen");
            }else{
                errorAlert.setTitle("Drink&Entree Not Selected!!");
                errorAlert.setContentText("Dinrk and entree not chosen");
            }
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void onClearOrder(ActionEvent event){
        
        //Clear stuff
        order.setEntreeSelection("");
        order.setDrinkSelection("");
        order.setFreeWaterRequest(false);
        order.setSpecialRequest("");
        entreeListView.getSelectionModel().clearSelection();
        if(tg.getSelectedToggle() != null)tg.getSelectedToggle().setSelected(false);
        requestFreeWaterBox.setSelected(false);
        specialRequestField.clear();
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onClearEntree(ActionEvent event) {
        entreeListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void onClearDrink(ActionEvent event) {
        tg.getSelectedToggle().setSelected(false);
    }

    @FXML
    private void onClearSpecialRequest(ActionEvent event) {
        specialRequestField.clear();
    }

    @FXML
    private void onAbout(ActionEvent event) {
        
        //Information alert setup
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About Application");
        about.setHeaderText("Information: ");
        about.setContentText("Application Name: Restaurant Ordering Application \n Application Version: v1.0\n Developer Name: Eric\n Developer Age: 18\n Developer College Program: Computer Science");
        about.showAndWait();
    }

    @FXML
    private void onRequestFreeWater(ActionEvent event) {
        
        //Check if user wanted free water previously, and change request state
        if(order.getFreeWaterRequest() == false) order.setFreeWaterRequest(true);
        else order.setFreeWaterRequest(false);
    }
}
