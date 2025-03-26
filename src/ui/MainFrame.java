package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import rs.RS6;
import java.awt.*;

public class MainFrame extends JFrame {
    private HexSpinner[] messageSpinners = new HexSpinner[12];
    private HexSpinner[] paritySpinners = new HexSpinner[8];
    private JTextField messageTextField;
    private JLabel pxLabel;
    private JLabel gxLabel;
    private JLabel pxgxLabel;
    private JLabel mxLabel;
    private JLabel msgLabel;
    private JLabel syndLabel;
    private JLabel exLabel;
    private JLabel ePosLabel;
    private JLabel errLabel;
    private JLabel corrLabel;
    private final String pxEmpty = "<html>P(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String gxEmpty = "<html>G(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String pxgxEmpty = "<html>P(x)/G(x)&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String mxEmpty = "<html>M(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String syndEmpty = "Syndromes =";
    private final String exEmpty = "<html>e(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&thinsp;=</html>";
    private final String ePosEmpty = "<html>ePos&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String errEmpty = "<html>err&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=</html>";
    private final String msgEmpty = "Message     =";
    private final String corrEmpty = "<html>Corrected&nbsp;&nbsp;&nbsp;=</html>";
    private RS6 rs;
    private int[] msgIn;
    private int[] msgOut1;
    private int[] msgOut2;
    private int[] synd;
    private int[] rem;

    public MainFrame() {
        super("Reed-Solomon Code");

        rs = new RS6();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 0, 50, 0));
        add(mainPanel);

        JLabel titleLabel = new JLabel("Enter a message (Up to 12 characters)");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        messageTextField = new JTextField(12);
        messageTextField.setDocument(new JTextFieldLimit(12));
        messageTextField.setMaximumSize(messageTextField.getPreferredSize());
        messageTextField.setAlignmentY(Component.TOP_ALIGNMENT);
        mainPanel.add(messageTextField);

        JPanel buttonPanel = new JPanel();
        mainPanel.add(buttonPanel);

        JButton encodeButton = new JButton("Encode");
        encodeButton.addActionListener(_ -> handleEncodePressed());
        buttonPanel.add(encodeButton);

        JButton decodeButton = new JButton("Decode");
        decodeButton.addActionListener(_ -> handleDecodePressed());
        buttonPanel.add(decodeButton);

        JButton resetButton = new JButton("  Reset  ");
        resetButton.addActionListener(_ -> handleResetPressed());
        buttonPanel.add(resetButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel spinnersPanel1 = new JPanel();
        spinnersPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        spinnersPanel1.setBorder(new EmptyBorder(0, 50, 0, 50));
        mainPanel.add(spinnersPanel1);

        JPanel spinnersPanel2 = new JPanel();
        spinnersPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        mainPanel.add(spinnersPanel2);

        for (int i = 0; i < 10; i++) {
            messageSpinners[i] = new HexSpinner();
            spinnersPanel1.add(messageSpinners[i]);
            // spinnersPanel1.add(new JLabel("x"));
            // if (i < 9)
            // spinnersPanel1.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        for (int i = 10; i < 12; i++) {
            messageSpinners[i] = new HexSpinner();
            spinnersPanel2.add(messageSpinners[i]);
            // spinnersPanel2.add(new JLabel("x"));
            // spinnersPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        for (int i = 0; i < 8; i++) {
            paritySpinners[i] = new HexSpinner();
            paritySpinners[i].setInitialColor(new Color(225, 255, 255));
            spinnersPanel2.add(paritySpinners[i]);
            // spinnersPanel2.add(new JLabel("x"));
            // if (i < 7)
            // spinnersPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 28)));
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 28)));

        // Wrapper panel to force left alignment
        JPanel bottomWrapper = new JPanel(new BorderLayout());
        // bottomWrapper.setBackground(Color.BLACK);
        bottomWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        bottomWrapper.setBorder(new EmptyBorder(0, 50, 0, 50));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add bottomPanel to the left of the wrapper
        bottomWrapper.add(bottomPanel, BorderLayout.WEST);
        mainPanel.add(bottomWrapper);

        // JPanel bottomPanel = new JPanel();
        // bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        // bottomPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        // bottomPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        // bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // bottomPanel.setBackground(Color.BLACK);
        // mainPanel.add(bottomPanel);


        pxLabel = new JLabel(pxEmpty);
        gxLabel = new JLabel(gxEmpty);
        pxgxLabel = new JLabel(pxgxEmpty);
        mxLabel = new JLabel(mxEmpty);
        syndLabel = new JLabel(syndEmpty);
        exLabel = new JLabel(exEmpty);
        ePosLabel = new JLabel(ePosEmpty);
        errLabel = new JLabel(errEmpty);
        msgLabel = new JLabel(msgEmpty);
        corrLabel = new JLabel(corrEmpty);

        pxLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        gxLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        pxgxLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        mxLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        syndLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        exLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        ePosLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        errLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        msgLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        corrLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        Dimension labelSize = new Dimension(1000, 28);

        pxLabel.setPreferredSize(labelSize);
        pxLabel.setMaximumSize(labelSize);
        pxLabel.setMinimumSize(labelSize);

        gxLabel.setPreferredSize(labelSize);
        gxLabel.setMaximumSize(labelSize);
        gxLabel.setMinimumSize(labelSize);

        pxgxLabel.setPreferredSize(labelSize);
        pxgxLabel.setMaximumSize(labelSize);
        pxgxLabel.setMinimumSize(labelSize);

        mxLabel.setPreferredSize(labelSize);
        mxLabel.setMaximumSize(labelSize);
        mxLabel.setMinimumSize(labelSize);

        syndLabel.setMinimumSize(labelSize);
        syndLabel.setPreferredSize(labelSize);
        syndLabel.setMaximumSize(labelSize);

        exLabel.setPreferredSize(labelSize);
        exLabel.setMaximumSize(labelSize);
        exLabel.setMinimumSize(labelSize);

        ePosLabel.setPreferredSize(labelSize);
        ePosLabel.setMaximumSize(labelSize);
        ePosLabel.setMinimumSize(labelSize);

        errLabel.setPreferredSize(labelSize);
        errLabel.setMaximumSize(labelSize);
        errLabel.setMinimumSize(labelSize);

        msgLabel.setMinimumSize(labelSize);
        msgLabel.setPreferredSize(labelSize);
        msgLabel.setMaximumSize(labelSize);

        corrLabel.setPreferredSize(labelSize);
        corrLabel.setMaximumSize(labelSize);
        corrLabel.setMinimumSize(labelSize);

        bottomPanel.add(pxLabel);
        bottomPanel.add(gxLabel);
        bottomPanel.add(pxgxLabel);
        bottomPanel.add(mxLabel);
        bottomPanel.add(syndLabel);
        bottomPanel.add(exLabel);
        bottomPanel.add(ePosLabel);
        bottomPanel.add(errLabel);
        bottomPanel.add(msgLabel);
        bottomPanel.add(corrLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(1100, 0)));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 650);
        // pack();
        setMinimumSize(getPreferredSize());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleEncodePressed() {
        resetDecodingLabels();

        String text = messageTextField.getText();
        msgIn = text.chars().toArray();
        int[] zeros = new int[12];

        for (int i = 0; i < Math.min(text.length(), 12); i++) {
            zeros[i] = msgIn[i];
        }
        msgIn = zeros;

        // if (msgIn.length == 0 || msgIn.length > 12) {
        // JOptionPane.showMessageDialog(null, "Message should be greater than 0 and less than 13 characters
        // long.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        // return;
        // }

        System.out.print("msgIn = ");
        rs.printArray(msgIn);

        rem = rs.generator(8);
        System.out.print("rem = ");
        rs.printArray(rem);

        msgOut1 = rs.encodeMsg(msgIn, 8);
        System.out.print("msgOut1 = ");
        rs.printArray(msgOut1);

        pxLabel.setText("""
                <html>
                P(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
                %02Xx<sup>13</sup>+
                %02Xx<sup>12</sup>+
                %02Xx<sup>11</sup>+
                %02Xx<sup>10</sup>+
                %02Xx<sup>F</sup>+
                %02Xx<sup>E</sup>+
                %02Xx<sup>D</sup>+
                %02Xx<sup>C</sup>+
                %02Xx<sup>B</sup>+
                %02Xx<sup>A</sup>+
                %02Xx<sup>9</sup>+
                %02Xx<sup>8</sup>+
                00x<sup>7</sup>+
                00x<sup>6</sup>+
                00x<sup>5</sup>+
                00x<sup>4</sup>+
                00x<sup>3</sup>+
                00x<sup>2</sup>+
                00x+
                00
                </html>""".formatted(
                msgOut1[0],
                msgOut1[1],
                msgOut1[2],
                msgOut1[3],
                msgOut1[4],
                msgOut1[5],
                msgOut1[6],
                msgOut1[7],
                msgOut1[8],
                msgOut1[9],
                msgOut1[10],
                msgOut1[11]));

        gxLabel.setText("""
                <html>
                G(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
                %02Xx<sup>8</sup>+
                %02Xx<sup>7</sup>+
                %02Xx<sup>6</sup>+
                %02Xx<sup>5</sup>+
                %02Xx<sup>4</sup>+
                %02Xx<sup>3</sup>+
                %02Xx<sup>2</sup>+
                %02Xx+
                %02X
                </html>""".formatted(
                rem[0],
                rem[1],
                rem[2],
                rem[3],
                rem[4],
                rem[5],
                rem[6],
                rem[7],
                rem[8]));

        pxgxLabel.setText("""
                <html>
                P(x)/G(x)&nbsp;&nbsp;&nbsp;&nbsp;=
                %Xx<sup>7</sup>+
                %Xx<sup>6</sup>+
                %Xx<sup>5</sup>+
                %Xx<sup>4</sup>+
                %Xx<sup>3</sup>+
                %Xx<sup>2</sup>+
                %Xx+
                %X
                </html>
                """.formatted(
                msgOut1[12],
                msgOut1[13],
                msgOut1[14],
                msgOut1[15],
                msgOut1[16],
                msgOut1[17],
                msgOut1[18],
                msgOut1[19]));

        mxLabel.setText("""
                <html>
                M(x)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
                %02Xx<sup>13</sup>+
                %02Xx<sup>12</sup>+
                %02Xx<sup>11</sup>+
                %02Xx<sup>10</sup>+
                %02Xx<sup>F</sup>+
                %02Xx<sup>E</sup>+
                %02Xx<sup>D</sup>+
                %02Xx<sup>C</sup>+
                %02Xx<sup>B</sup>+
                %02Xx<sup>A</sup>+
                %02Xx<sup>9</sup>+
                %02Xx<sup>8</sup>+
                %02Xx<sup>7</sup>+
                %02Xx<sup>6</sup>+
                %02Xx<sup>5</sup>+
                %02Xx<sup>4</sup>+
                %02Xx<sup>3</sup>+
                %02Xx<sup>2</sup>+
                %02Xx+
                %02X
                </html>""".formatted(
                msgOut1[0],
                msgOut1[1],
                msgOut1[2],
                msgOut1[3],
                msgOut1[4],
                msgOut1[5],
                msgOut1[6],
                msgOut1[7],
                msgOut1[8],
                msgOut1[9],
                msgOut1[10],
                msgOut1[11],
                msgOut1[12],
                msgOut1[13],
                msgOut1[14],
                msgOut1[15],
                msgOut1[16],
                msgOut1[17],
                msgOut1[18],
                msgOut1[19]));

        int startingIndex = 12 - msgIn.length;

        for (int i = 0; i < msgIn.length; i++) {
            messageSpinners[startingIndex + i].setInitialValue(msgOut1[i]);
        }

        startingIndex = msgIn.length;
        for (int i = 0; i < 8; i++) {
            paritySpinners[i].setInitialValue(msgOut1[startingIndex + i]);
        }

        setSyndromes();
    }

    private void handleResetPressed() {
        messageTextField.setText("");
        msgIn = new int[0];
        msgOut1 = new int[0];
        msgOut2 = new int[0];
        synd = new int[0];
        rem = new int[0];

        for (HexSpinner spinner : messageSpinners) {
            spinner.setInitialValue(0);
        }

        for (HexSpinner spinner : paritySpinners) {
            spinner.setInitialValue(0);
        }

        pxLabel.setText(pxEmpty);
        gxLabel.setText(gxEmpty);
        pxgxLabel.setText(pxgxEmpty);
        mxLabel.setText(mxEmpty);
        syndLabel.setText(syndEmpty);

        resetDecodingLabels();
        messageTextField.requestFocusInWindow();
    }

    private void resetDecodingLabels() {
        exLabel.setText(exEmpty);
        ePosLabel.setText(ePosEmpty);
        errLabel.setText(errEmpty);
        msgLabel.setText(msgEmpty);
        corrLabel.setText(corrEmpty);
        for (HexSpinner sp : messageSpinners) {
            sp.resetColor();
        }
        for (HexSpinner sp : paritySpinners) {
            sp.resetColor();
        }
    }

    private void handleDecodePressed() {
        setSyndromes();

        int[] eLoc = rs.findErrorLocator(synd, 8);

        int errCount = 0;
        for (int i = 0; i < msgOut2.length; i++) {
            if (msgOut1[i] != msgOut2[i])
                errCount++;
        }
        if (errCount > 4) {
            JOptionPane.showMessageDialog(this, "Too many errors to correct.", "Too Many Errors",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.print("eLoc = ");
        rs.printArray(eLoc);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eLoc.length; i++) {
            sb.append("%02X".formatted(eLoc[eLoc.length - i - 1]));
            if (i < eLoc.length - 1) {
                sb.append("x");
                if (i < eLoc.length - 2) {
                    sb.append("<sup>").append(eLoc.length - i - 1).append("</sup>");
                }
                sb.append(" + ");
            }
        }

        String exText = exEmpty.substring(0, exEmpty.length() - 7) + " " + sb.toString() + "</html>";
        exLabel.setText(exText);

        int[] eLoc2 = new int[eLoc.length];
        for (int j = 0; j < eLoc.length; j++)
            eLoc2[eLoc.length - 1 - j] = eLoc[j];

        int[] ePos = rs.findErrorPositions(eLoc2, 20);
        System.out.print("ePos = ");
        rs.printArray(ePos);

        sb.delete(0, sb.length());
        for (int i = 0; i < ePos.length; i++) {
            sb.append("%02X ".formatted(ePos[i]));
        }

        String ePosText = ePosEmpty.substring(0, ePosEmpty.length() - 7) + " " + sb.toString() + "</html>";
        ePosLabel.setText(ePosText);

        int[] err = rs.findErrorValues(synd, ePos);
        System.out.print("err = ");
        rs.printArray(err);

        sb.delete(0, sb.length());
        for (int i = 0; i < err.length; i++) {
            sb.append("%02X ".formatted(err[i]));
        }

        String errText = errEmpty.substring(0, errEmpty.length() - 7) + " " + sb.toString() + "</html>";
        errLabel.setText(errText);

        sb.delete(0, sb.length());
        for (int i = 0; i < msgOut2.length; i++) {
            sb.append("%02X ".formatted(msgOut2[i]));
        }

        String text = msgEmpty + " " + sb.toString();
        msgLabel.setText(text);

        int[] corr = msgOut1;
        if (ePos.length > 0) {
            corr = rs.correctMsg(msgOut2, ePos, err);
        }

        System.out.print("corr = ");
        rs.printArray(corr);

        sb.delete(0, sb.length());
        for (int i = 0; i < corr.length; i++) {
            sb.append("%02X ".formatted(corr[i]));
        }

        String corrText = corrEmpty.substring(0, corrEmpty.length() - 7) + " " + sb.toString() + "</html>";
        corrLabel.setText(corrText);
    }

    private void setSyndromes() {
        msgOut2 = new int[20];

        for (int i = 0; i < 12; i++) {
            msgOut2[i] = (int) messageSpinners[i].getValue();
        }

        for (int i = 0; i < 8; i++) {
            msgOut2[i + 12] = (int) paritySpinners[i].getValue();;
        }

        System.out.print("msgOut2 = ");
        rs.printArray(msgOut2);
        synd = rs.calcSyndromes(msgOut2, 8);
        System.out.print("synd = ");
        rs.printArray(synd);

        syndLabel.setText("Syndromes = %02X %02X %02X %02X %02X %02X %02X %02X".formatted(
                synd[0], synd[1], synd[2], synd[3], synd[4], synd[5], synd[6], synd[7]));
    }


}
