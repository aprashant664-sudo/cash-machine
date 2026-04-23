import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame implements ActionListener {

    private JTextField display;
    private JButton[] numberButtons = new JButton[10];
    private JButton enterBtn, clearBtn;
    private JButton balanceBtn, withdrawBtn, receiptBtn, logoutBtn;

    private String enteredPin = "";

    private AccountManager manager;
    private Account currentAccount;
    private Transaction lastTransaction;

    public ATMGUI() {

        manager = new AccountManager();

        setTitle("Electronic Cash Machine");
        setSize(400, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setEditable(false);
        display.setText("Enter PIN");

        add(display, BorderLayout.NORTH);

        JPanel keypad = new JPanel(new GridLayout(4, 3));

        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            keypad.add(numberButtons[i]);
        }

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        keypad.add(clearBtn);

        numberButtons[0] = new JButton("0");
        numberButtons[0].addActionListener(this);
        keypad.add(numberButtons[0]);

        enterBtn = new JButton("Enter");
        enterBtn.addActionListener(this);
        keypad.add(enterBtn);

        add(keypad, BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(4, 1));

        balanceBtn = new JButton("Balance");
        withdrawBtn = new JButton("Withdraw");
        receiptBtn = new JButton("Receipt");
        logoutBtn = new JButton("Logout");

        balanceBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        receiptBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        actions.add(balanceBtn);
        actions.add(withdrawBtn);
        actions.add(receiptBtn);
        actions.add(logoutBtn);

        add(actions, BorderLayout.EAST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i <= 9; i++) {
            if (e.getSource() == numberButtons[i]) {
                enteredPin += i;
                display.setText("*".repeat(enteredPin.length()));
            }
        }

        if (e.getSource() == clearBtn) {
            enteredPin = "";
            display.setText("");
        }

        if (e.getSource() == enterBtn) {
            try {
                int pin = Integer.parseInt(enteredPin);
                currentAccount = manager.validatePin(pin);

                if (currentAccount != null) {
                    display.setText("Login Successful");
                } else {
                    display.setText("Invalid PIN");
                }

                enteredPin = "";

            } catch (Exception ex) {
                display.setText("Error");
            }
        }

        if (e.getSource() == balanceBtn) {
            if (currentAccount != null) {
                display.setText("Balance: $" + currentAccount.getBalance());
            }
        }

        if (e.getSource() == withdrawBtn) {
            if (currentAccount != null) {

                String input = JOptionPane.showInputDialog("Enter amount:");

                try {
                    double amount = Double.parseDouble(input);

                    if (currentAccount.withdraw(amount)) {
                        display.setText("Withdraw Successful");
                        lastTransaction = new Transaction("Withdraw", amount);
                    } else {
                        display.setText("Insufficient Funds");
                    }

                } catch (Exception ex) {
                    display.setText("Invalid Input");
                }
            }
        }

        if (e.getSource() == receiptBtn) {
            if (lastTransaction != null) {
                JOptionPane.showMessageDialog(this, lastTransaction.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No Transaction Yet");
            }
        }

        if (e.getSource() == logoutBtn) {
            currentAccount = null;
            display.setText("Enter PIN");
        }
    }
}
