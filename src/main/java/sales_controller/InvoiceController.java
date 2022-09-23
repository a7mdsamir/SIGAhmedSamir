/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sales_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sales_model.InvoiceDetails;
import sales_model.InvoicesTableDetails;
import sales_model.Item;
import sales_model.ItemTableDetails;
import sales_view.InvoiceComponents;
import sales_view.InvoiceGUI;
import sales_view.ItemComponents;

/**
 *
 * @author Ahmed.Samir
 */
public class InvoiceController implements ActionListener, ListSelectionListener {


    private InvoiceGUI frameInvoice;
    private InvoiceComponents invComponents;
    private ItemComponents itemComponents;

    public InvoiceController(InvoiceGUI frame) {
        this.frameInvoice = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frameInvoice.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceDetails currentInvoice = frameInvoice.getInvoices().get(selectedIndex);
            frameInvoice.getInvoiceNumLabel().setText("" + currentInvoice.getId());
            frameInvoice.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frameInvoice.getCustomerNameLabel().setText(currentInvoice.getCstName());
            frameInvoice.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            ItemTableDetails linesTableModel = new ItemTableDetails(currentInvoice.getItems());
            frameInvoice.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(frameInvoice);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                
                ArrayList<InvoiceDetails> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        InvoiceDetails invoice = new InvoiceDetails(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frameInvoice, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                result = fc.showOpenDialog(frameInvoice);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Items have been read");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoiceDetails inv = null;
                            for (InvoiceDetails invoice : invoicesArray) {
                                if (invoice.getId() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            Item line = new Item(itemName, itemPrice, count, inv);
                            inv.getItems().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frameInvoice, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                frameInvoice.setInvoices(invoicesArray);
                InvoicesTableDetails invoicesTableModel = new InvoicesTableDetails(invoicesArray);
                frameInvoice.setInvoicesTableModel(invoicesTableModel);
                frameInvoice.getInvoiceTable().setModel(invoicesTableModel);
                frameInvoice.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frameInvoice, "Failure in reading file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<InvoiceDetails> invoices = frameInvoice.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceDetails invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (Item line : invoice.getItems()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frameInvoice);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frameInvoice);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        invComponents = new InvoiceComponents(frameInvoice);
        invComponents.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frameInvoice.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frameInvoice.getInvoices().remove(selectedRow);
            frameInvoice.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        itemComponents = new ItemComponents(frameInvoice);
        itemComponents.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = frameInvoice.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            ItemTableDetails linesTableModel = (ItemTableDetails) frameInvoice.getLineTable().getModel();
            linesTableModel.getItems().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frameInvoice.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invComponents.setVisible(false);
        invComponents.dispose();
        invComponents = null;
    }

    private void createInvoiceOK() {
        String date = invComponents.getInvDate().getText();
        String customer = invComponents.getCstName().getText();
        int num = frameInvoice.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frameInvoice, "Failure in date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frameInvoice, "Failure in date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceDetails invoice = new InvoiceDetails(num, date, customer);
                    frameInvoice.getInvoices().add(invoice);
                    frameInvoice.getInvoicesTableModel().fireTableDataChanged();
                    invComponents.setVisible(false);
                    invComponents.dispose();
                    invComponents = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frameInvoice, "Failure in date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = itemComponents.getItemName().getText();
        String countStr = itemComponents.getItemCount().getText();
        String priceStr = itemComponents.getItemPrice().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frameInvoice.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceDetails invoice = frameInvoice.getInvoices().get(selectedInvoice);
            Item line = new Item(item, price, count, invoice);
            invoice.getItems().add(line);
            ItemTableDetails linesTableModel = (ItemTableDetails) frameInvoice.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            linesTableModel.fireTableDataChanged();
            frameInvoice.getInvoicesTableModel().fireTableDataChanged();
        }
        itemComponents.setVisible(false);
        itemComponents.dispose();
        itemComponents = null;
    }

    private void createLineCancel() {
        itemComponents.setVisible(false);
        itemComponents.dispose();
        itemComponents = null;
    }

}
