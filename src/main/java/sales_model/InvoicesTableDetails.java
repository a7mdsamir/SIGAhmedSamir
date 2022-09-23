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
public class InvoicesTableDetails extends AbstractTableModel {
    private ArrayList<InvoiceDetails> invoicesDetails;
    private String[] attributes = {"Number", "Date", "CstName", "Total"};
    
    public InvoicesTableDetails(ArrayList<InvoiceDetails> invoices) {
        this.invoicesDetails = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoicesDetails.size();
    }

    @Override
    public int getColumnCount() {
        return attributes.length;
    }

    @Override
    public String getColumnName(int column) {
        return attributes[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceDetails invoice = invoicesDetails.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getId();
            case 1: return invoice.getDate();
            case 2: return invoice.getCstName();
            case 3: return invoice.getInvoiceTotal();
            default : return "";
        }
    }
}
