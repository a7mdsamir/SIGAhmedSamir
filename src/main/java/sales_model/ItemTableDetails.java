/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sales_model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ahmed.Samir
 */
public class ItemTableDetails extends AbstractTableModel {

    private ArrayList<Item> items;
    private String[] attributes = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public ItemTableDetails(ArrayList<Item> lines) {
        this.items = lines;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return attributes.length;
    }

    @Override
    public String getColumnName(int x) {
        return attributes[x];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item line = items.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvDetails().getId();
            case 1: return line.getItemName();
            case 2: return line.getPrice();
            case 3: return line.getNumOfItems();
            case 4: return line.getLineTotal();
            default : return "";
        }
    }
    
}
