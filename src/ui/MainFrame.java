package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

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

        JTextField messageTextField = new JTextField(12);
        messageTextField.setMaximumSize(getPreferredSize());
        topPanel.add(messageTextField);

        JPanel buttonPanel = new JPanel();
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton encodeButton = new JButton("Encode");
        buttonPanel.add(encodeButton);

        // I feel the decode button belongs after the user introduces errors (below hex spinners)
        // JButton decodeButton = new JButton("Decode");
        // buttonPanel.add(decodeButton);

        JButton resetButton = new JButton("Reset");
        buttonPanel.add(resetButton);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(centerPanel);

        JPanel polynomialPanel = new JPanel();
        polynomialPanel.setLayout(new BoxLayout(polynomialPanel, BoxLayout.Y_AXIS));
        centerPanel.add(polynomialPanel);

        JLabel polyLabel1 = new JLabel("""
                <html>
                P(x) =
                1x<sup>19</sup>+
                1x<sup>18</sup>+
                1x<sup>17</sup>+
                1x<sup>16</sup>+
                1x<sup>15</sup>+
                1x<sup>14</sup>+
                1x<sup>13</sup>+
                1x<sup>12</sup>+
                1x<sup>11</sup>+
                1x<sup>10</sup>+
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
        polynomialPanel.add(polyLabel1);

        JLabel remainderLabel = new JLabel("""
                <html>
                P(x)/G(x) =
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
        polynomialPanel.add(remainderLabel);

        JLabel fullPolyLabel = new JLabel("""
                <html>
                P(x) =
                1x<sup>19</sup>+
                1x<sup>18</sup>+
                1x<sup>17</sup>+
                1x<sup>16</sup>+
                1x<sup>15</sup>+
                1x<sup>14</sup>+
                1x<sup>13</sup>+
                1x<sup>12</sup>+
                1x<sup>11</sup>+
                1x<sup>10</sup>+
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
        polynomialPanel.add(fullPolyLabel);

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
            // JSpinner spinner = new JSpinner();
            JSpinner spinner = new HexSpinner();
            msgSpinnersPanel.add(spinner);
        }

        JPanel ptySpinnersPanel = new JPanel();
        ptySpinnersPanel.setLayout(new GridLayout(1, 12));
        spinnersWidgetPanel.add(ptySpinnersPanel);

        for (int i = 0; i < 8; i++) {
            // JSpinner spinner = new JSpinner();
            JSpinner spinner = new HexSpinner();
            ptySpinnersPanel.add(spinner);
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

}
