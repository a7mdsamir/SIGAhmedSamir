/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sales_model;

import java.util.ArrayList;

/**
 *
 * @author Ahmed.Samir
 */
public class InvoiceDetails {
 private int id;
    private String date;
    private String cstName;
    private ArrayList<Item> items;
    
    public InvoiceDetails() {
    }

    public InvoiceDetails(int num, String date, String customer) {
        this.id = num;
        this.date = date;
        this.cstName = customer;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        for (Item line : getItems()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public ArrayList<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + id + ", date=" + date + ", customer=" + cstName + '}';
    }
    
    public String getAsCSV() {
        return id + "," + date + "," + cstName;
    }   
}
