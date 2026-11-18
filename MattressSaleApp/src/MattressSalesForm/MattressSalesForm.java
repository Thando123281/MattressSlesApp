
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MattressSalesForm extends JFrame {

    private JTextField txtCustomerName;
    private JTextField txtPrice;
    private JComboBox<String> cmbProduct;
    private JButton btnProcess;

    public MattressSalesForm() {

        setTitle("Mattress Sales App");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        createForm();

    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        exitItem.addActionListener(e -> System.exit(0));

        processItem.addActionListener(e -> processSale());

        clearItem.addActionListener(e -> clearForm());

        fileMenu.add(exitItem);
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);
    }

    private void createForm() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel lblCustomer = new JLabel("Customer Name:");
        txtCustomerName = new JTextField();

        JLabel lblProduct = new JLabel("Select Product:");
        cmbProduct = new JComboBox<>(new String[]{
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        });

        JLabel lblPrice = new JLabel("Enter Price:");
        txtPrice = new JTextField();

        btnProcess = new JButton("PROCESS");
        btnProcess.addActionListener(e -> processSale());

        panel.add(lblCustomer);
        panel.add(txtCustomerName);

        panel.add(lblProduct);
        panel.add(cmbProduct);

        panel.add(lblPrice);
        panel.add(txtPrice);

        panel.add(new JLabel(""));
        panel.add(btnProcess);

        add(panel);
    }

    private void processSale() {

        String customerName = txtCustomerName.getText().trim();
        String product = (String) cmbProduct.getSelectedItem();
        String priceText = txtPrice.getText().trim();

        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter the customer name.");
            return;
        }

        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter the product price.");
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);

            if (price <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Price must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number for the price.");
            return;
        }

        ProductCalculationsImpl calculations =
                new ProductCalculationsImpl(price);

        double vat = calculations.GetVat();
        double commission = calculations.GetCommission();
        double total = calculations.GetTotal();

        String message = "Customer Name: " + customerName
                + "\nProduct: " + product
                + "\nVAT: R " + vat
                + "\nCOMMISSION: R " + commission
                + "\nTOTAL: R " + total;

        JOptionPane.showMessageDialog(this,
                message,
                "Sale Information",
                JOptionPane.INFORMATION_MESSAGE);

        saveToFile(customerName, product, vat, commission, total);
    }

    private void saveToFile(String customerName,
            String product,
            double vat,
            double commission,
            double total) {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter("data.txt", true))) {

            writer.println("DATA LOG");
            writer.println("****************************");
            writer.println("Customer Name: " + customerName);
            writer.println("Product: " + product);
            writer.println("VAT: R " + vat);
            writer.println("COMMISSION: R " + commission);
            writer.println("TOTAL: R " + total);
            writer.println("****************************");
            writer.println();

            JOptionPane.showMessageDialog(this,
                    "Sale saved successfully to data.txt");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Error saving file: " + e.getMessage());
        }
    }

    private void clearForm() {

        txtCustomerName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);

        txtCustomerName.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MattressSalesForm().setVisible(true);
        });
    }
}
