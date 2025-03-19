package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private HexSpinner[] messageSpinners = new HexSpinner[12];
    private HexSpinner[] paritySpinners = new HexSpinner[8];
    private JTextField messageTextField;
    private JLabel initialPolyLabel;
    private JLabel remainderPolyLabel;
    private JLabel messagePolyLabel;

    public MainFrame() {
        super("Reed-Solomon Code");


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Enter message
        JPanel titlePanel = new JPanel();
        mainPanel.add(titlePanel);

        titlePanel.add(new JLabel("Enter a message, then click Encode. (Up to 12 characters)"));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topPanel);

        JPanel messagePanel = new JPanel();
        topPanel.add(messagePanel);

        messageTextField = new JTextField(12);
        messageTextField.setMaximumSize(getPreferredSize());
        topPanel.add(messageTextField);

        JPanel buttonPanel = new JPanel();
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton encodeButton = new JButton("Encode");
        encodeButton.addActionListener(_ -> handleEncodePressed());
        buttonPanel.add(encodeButton);

        // I feel the decode button belongs after the user introduces errors (below hex spinners)
        // JButton decodeButton = new JButton("Decode");
        // buttonPanel.add(decodeButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(_ -> handleResetPressed());
        buttonPanel.add(resetButton);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(centerPanel);

        JPanel polynomialPanel = new JPanel();
        polynomialPanel.setLayout(new BoxLayout(polynomialPanel, BoxLayout.Y_AXIS));
        centerPanel.add(polynomialPanel);

        initialPolyLabel = new JLabel("<html>P(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =</html>");
        polynomialPanel.add(initialPolyLabel);

        remainderPolyLabel = new JLabel("P(x)/g(x) =");
        polynomialPanel.add(remainderPolyLabel);

        messagePolyLabel = new JLabel("<html>M(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =</html>");
        polynomialPanel.add(messagePolyLabel);

        JPanel introErrorsPanel = new JPanel();
        introErrorsPanel.setLayout(new BoxLayout(introErrorsPanel, BoxLayout.Y_AXIS));
        centerPanel.add(introErrorsPanel);

        JPanel instructPanel = new JPanel();
        introErrorsPanel.add(instructPanel);

        JLabel instructLabel = new JLabel("Introduce errors, then click Decode. (Up to 4 errors)");
        instructPanel.add(instructLabel);

        JPanel spinnersPanel = new JPanel();
        spinnersPanel.setLayout(new BoxLayout(spinnersPanel, BoxLayout.X_AXIS));
        introErrorsPanel.add(spinnersPanel);

        JPanel spinnersLabelPanel = new JPanel();
        spinnersLabelPanel.setLayout(new BoxLayout(spinnersLabelPanel, BoxLayout.Y_AXIS));
        spinnersPanel.add(spinnersLabelPanel);

        JPanel spinnersWidgetPanel = new JPanel();
        spinnersWidgetPanel.setLayout(new BoxLayout(spinnersWidgetPanel, BoxLayout.Y_AXIS));
        spinnersPanel.add(spinnersWidgetPanel);

        JLabel msgSymbolsLabel = new JLabel("Message symbols:");
        spinnersLabelPanel.add(msgSymbolsLabel);

        JLabel ptySymbolsLabel = new JLabel("Parity symbols:");
        spinnersLabelPanel.add(ptySymbolsLabel);

        JPanel msgSpinnersPanel = new JPanel();
        msgSpinnersPanel.setLayout(new GridLayout(1, 12));
        spinnersWidgetPanel.add(msgSpinnersPanel);

        for (int i = 0; i < 12; i++) {
            messageSpinners[i] = new HexSpinner();
            msgSpinnersPanel.add(messageSpinners[i]);
        }

        JPanel ptySpinnersPanel = new JPanel();
        ptySpinnersPanel.setLayout(new GridLayout(1, 12));
        spinnersWidgetPanel.add(ptySpinnersPanel);

        for (int i = 0; i < 8; i++) {
            paritySpinners[i] = new HexSpinner();
            ptySpinnersPanel.add(paritySpinners[i]);
        }

        JPanel decodeButtonPanel = new JPanel();
        introErrorsPanel.add(decodeButtonPanel);

        JButton decodeButton = new JButton("Decode");
        decodeButtonPanel.add(decodeButton);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // TODO: Show the decoding stuff

        JPanel bottomPanel = new JPanel();
        mainPanel.add(bottomPanel);


        // mainPanel.add(Box.createVerticalGlue());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setSize(850, 600);
        pack();
        setVisible(true);
    }

    private void handleEncodePressed() {
        initialPolyLabel.setText("""
                <html>
                P(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =
                1x<sup>13</sup>+
                1x<sup>12</sup>+
                1x<sup>11</sup>+
                1x<sup>10</sup>+
                1x<sup>F</sup>+
                1x<sup>E</sup>+
                1x<sup>D</sup>+
                1x<sup>C</sup>+
                1x<sup>B</sup>+
                1x<sup>A</sup>+
                1x<sup>9</sup>+
                1x<sup>8</sup>+
                0+
                0+
                0+
                0+
                0+
                0+
                0+
                0
                </html>""");

        remainderPolyLabel.setText("""
                <html>
                P(x)/g(x) =
                1x<sup>7</sup>+
                1x<sup>6</sup>+
                1x<sup>5</sup>+
                1x<sup>4</sup>+
                1x<sup>3</sup>+
                1x<sup>2</sup>+
                1x+
                1
                </html>
                """);

        messagePolyLabel.setText("""
                <html>
                M(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =
                1x<sup>13</sup>+
                1x<sup>12</sup>+
                1x<sup>11</sup>+
                1x<sup>10</sup>+
                1x<sup>F</sup>+
                1x<sup>E</sup>+
                1x<sup>D</sup>+
                1x<sup>C</sup>+
                1x<sup>B</sup>+
                1x<sup>A</sup>+
                1x<sup>9</sup>+
                1x<sup>8</sup>+
                1x<sup>7</sup>+
                1x<sup>6</sup>+
                1x<sup>5</sup>+
                1x<sup>4</sup>+
                1x<sup>3</sup>+
                1x<sup>2</sup>+
                1x+
                1
                </html>""");
    }

    private void handleResetPressed() {
        messageTextField.setText("");

        for (HexSpinner spinner : messageSpinners) {
            spinner.setValue(0);
        }

        for (HexSpinner spinner : paritySpinners) {
            spinner.setValue(0);
        }

        initialPolyLabel.setText("<html>P(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =</html>");
        remainderPolyLabel.setText("P(x)/g(x) =");
        messagePolyLabel.setText("<html>M(x) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; =</html>");
    }
}
