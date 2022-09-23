/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sales_model;

/**
 *
 * @author Ahmed.Samir
 */
public class Item {
    private String itemName;
    private double price;
    private int numOfItems;
    private InvoiceDetails invDetails;

    public Item() {
    }

    public Item(String item, double price, int count, InvoiceDetails invoice) {
        this.itemName = item;
        this.price = price;
        this.numOfItems = count;
        this.invDetails = invoice;
    }

    public double getLineTotal() {
        return price * numOfItems;
    }
    
    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invDetails.getId() + ", item=" + itemName + ", price=" + price + ", count=" + numOfItems + '}';
    }

    public InvoiceDetails getInvDetails() {
        return invDetails;
    }
    
    public String getAsCSV() {
        return invDetails.getId() + "," + itemName + "," + price + "," + numOfItems;
    }
    
}
