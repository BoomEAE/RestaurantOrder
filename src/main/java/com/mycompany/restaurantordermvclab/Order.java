/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantordermvclab;

/**
 *
 * @author 2539556
 */
public class Order {
    
    private String entreeSelection = "";
    private String drinkSelection = "";
    private boolean freeWaterRequest = false;
    private String specialRequest = "";
    private double selectedEntreePrice = 0.0;
    private double selectedDrinkPrice = 0.0;
    private String menuItems[] = {"", "Chicken Salad", "Turkey Sandwich", "Vegetable Wrap", "Grilled Cheese", "Coffee", "Tea", "Soda", "Juice"};
    private double prices[] = {0.0, 6.50, 6.75, 6.00, 5.00, 1.50, 1.50, 2.00, 1.75};
    private double totalPrice;
    
    public void setEntreeSelection(String entreeSelection){
        this.entreeSelection = entreeSelection;
    }
    
    public String getEntreeSelection(){
        return entreeSelection;
    }
    
    public void setDrinkSelection(String drinkSelection){
        this.drinkSelection = drinkSelection;
    }
    
    public String getDrinkSelection(){
        return drinkSelection;
    }
    
    public void setFreeWaterRequest(Boolean freeWaterRequest){
        this.freeWaterRequest = freeWaterRequest;
    }
    
    public Boolean getFreeWaterRequest(){
        return freeWaterRequest;
    }
    
    public void setSpecialRequest(String specialRequest){
        this.specialRequest = specialRequest;
    }
    
    public String getSpecialRequest(){
        return specialRequest;
    }
    
    public double getSelectedEntreePrice(){
        
        //Iterate through the menuItems and find the index where the selected entree lies in menuItems
        //then using the index, get the price from the prices
        for(int i = 0; i < menuItems.length; i++){
            if(entreeSelection.equals(menuItems[i])){
                selectedEntreePrice = prices[i];
            }
        }
        
        return selectedEntreePrice;
    }
    
    public double getSelectedDrinkPrice(){
        
        //Iterate through the menuItems and find the index where the selected drink lies in menuItems
        //then using the index, get the price from the prices
        for(int i = 0; i < menuItems.length; i++){
            if(drinkSelection.equals(menuItems[i])){
                selectedDrinkPrice = prices[i];
            }
        }
        
        return selectedDrinkPrice;
    }
    
    public double getTotalPrice(){
        
        //Calculate price by including taxes
        totalPrice = 1.15*(getSelectedEntreePrice() + getSelectedDrinkPrice());
        
        return totalPrice;
    }
}
