
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A hand-coded version of SimpleCalculator for learners.
 * Includes digit buttons as requested.
 */
public class ManualCalculator extends JFrame {

    // Components
    private JLabel lblDisplay;
    private JTextField txtFInput, txtSInput;
    private JButton[] digitButtons = new JButton[10];
    private JButton btnAddition, btnSubstraction, btnMultiplication, btnDivision, btnClear;

    // To track which input field was last focused for digit button input
    private JTextField lastFocusedField;

    private double firstNumber, secondNumber, result;

    public ManualCalculator() {
        // Basic Frame Setup
        setTitle("Simple Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // 1. Display Panel (Result)
        lblDisplay = new JLabel("", SwingConstants.RIGHT);
        lblDisplay.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblDisplay.setForeground(new Color(0, 0, 102));
        lblDisplay.setPreferredSize(new Dimension(380, 60));
        lblDisplay.setBorder(BorderFactory.createTitledBorder("Result"));
        mainPanel.add(lblDisplay, BorderLayout.NORTH);

        // 2. Center Panel (Inputs)
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        centerPanel.setBackground(Color.WHITE);

        JLabel lblF = new JLabel("First Input:");
        lblF.setFont(new Font("Segoe UI", Font.BOLD, 18));

        txtFInput = new JTextField();
        txtFInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                lastFocusedField = txtFInput;
            }
        });

        JLabel lblS = new JLabel("Second Input:");
        lblS.setFont(new Font("Segoe UI", Font.BOLD, 18));

        txtSInput = new JTextField();
        txtSInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                lastFocusedField = txtSInput;
            }
        });

        centerPanel.add(lblF);
        centerPanel.add(txtFInput);
        centerPanel.add(lblS);
        centerPanel.add(txtSInput);

        // Default focus
        lastFocusedField = txtFInput;

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 3. Bottom Panel (Buttons)
        JPanel bottomPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        bottomPanel.setBackground(Color.WHITE);

        // Digit Buttons 1-9
        for (int i = 1; i <= 9; i++) {
            digitButtons[i] = createDigitButton(String.valueOf(i));
            bottomPanel.add(digitButtons[i]);
        }

        // 0 Button
        digitButtons[0] = createDigitButton("0");

        // Operators
        btnAddition = createOpButton("+", new Color(153, 153, 255));
        btnSubstraction = createOpButton("-", new Color(204, 102, 255));
        btnMultiplication = createOpButton("X", new Color(51, 255, 255));
        btnDivision = createOpButton("/", new Color(153, 255, 153));
        btnClear = createOpButton("CLR", new Color(255, 0, 102));

        // Layout the buttons
        bottomPanel.add(btnAddition);
        bottomPanel.add(digitButtons[0]);
        bottomPanel.add(btnSubstraction);
        bottomPanel.add(btnMultiplication);
        bottomPanel.add(btnDivision);
        bottomPanel.add(btnClear);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Action Listeners for Operations
        btnAddition.addActionListener(e -> calculate("+"));
        btnSubstraction.addActionListener(e -> calculate("-"));
        btnMultiplication.addActionListener(e -> calculate("X"));
        btnDivision.addActionListener(e -> calculate("/"));
        btnClear.addActionListener(e -> clearAll());
    }

    private JButton createDigitButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.addActionListener(e -> {
            if (lastFocusedField != null) {
                lastFocusedField.setText(lastFocusedField.getText() + text);
            }
        });
        return btn;
    }

    private JButton createOpButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void calculate(String operator) {
        if (!validateInputs())
            return;

        if (operator.equals("+")) {
            result = firstNumber + secondNumber;
        } else if (operator.equals("-")) {
            result = firstNumber - secondNumber;
        } else if (operator.equals("X")) {
            result = firstNumber * secondNumber;
        } else if (operator.equals("/")) {
            if (secondNumber == 0) {
                JOptionPane.showMessageDialog(this, "Cannot divide by zero!", "Math Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = firstNumber / secondNumber;
        }

        lblDisplay.setText(String.valueOf(result));
    }

    private boolean validateInputs() {
        try {
            firstNumber = Double.parseDouble(txtFInput.getText());
            secondNumber = Double.parseDouble(txtSInput.getText());

            if (firstNumber < 0 || secondNumber < 0) {
                JOptionPane.showMessageDialog(this, "Negative values are not allowed", "Input error!",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers only!", "Input error!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void clearAll() {
        txtFInput.setText("");
        txtSInput.setText("");
        lblDisplay.setText("");
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManualCalculator().setVisible(true);
        });
    }
}
