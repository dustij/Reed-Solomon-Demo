package ui;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import java.awt.event.*;
import java.text.ParseException;

public class HexSpinner extends JSpinner {
  public HexSpinner() {
    // Wrap around spinner model
    super(new SpinnerNumberModel(0, 0, 0xff, 0x1) {
      @Override
      public Object getNextValue() {
        int currentValue = ((Number) getValue()).intValue();
        int maxValue = ((Number) getMaximum()).intValue();
        int stepSize = ((Number) getStepSize()).intValue();
        return (currentValue + stepSize > maxValue) ? getMinimum() : currentValue + stepSize;
      }

      @Override
      public Object getPreviousValue() {
        int currentValue = ((Number) getValue()).intValue();
        int minValue = ((Number) getMinimum()).intValue();
        int stepSize = ((Number) getStepSize()).intValue();
        return (currentValue - stepSize < minValue) ? getMaximum() : currentValue - stepSize;
      }
    });

    var textField = getEditorTextField();
    configureTextFieldForHexInput(textField);

    // Select text on focus
    textField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        SwingUtilities.invokeLater(textField::selectAll);
      }
    });

    // Allow hex values
    textField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String input = textField.getText().trim();
        try {
          int newValue = parseHexByte(input);
          setValue(newValue);
        } catch (NumberFormatException ex) {
          resetTextField();
        }
      }
    });
  }

  protected JFormattedTextField getEditorTextField() {
    return ((JSpinner.DefaultEditor) getEditor()).getTextField();
  }

  protected int parseHexByte(String hexString) throws NumberFormatException {
    int value = Integer.parseInt(hexString, 16);
    if (value < 0 || value > 0xFF) {
      throw new NumberFormatException("Out of range");
    }
    return value;
  }

  /**
   * Resets the text field to the last valid spinner value.
   */
  protected int resetTextField() {
    var textField = getEditorTextField();
    try {
      textField.setText(Integer.toHexString((int) getValue()).toUpperCase());
    } catch (Exception ignored) {
    }
    return (int) getValue();
  }

  /**
   * Configures the JFormattedTextField to allow hex input.
   */
  private void configureTextFieldForHexInput(JFormattedTextField textField) {
    AbstractDocument doc = (AbstractDocument) textField.getDocument();
    doc.setDocumentFilter(new DocumentFilter() {
      @Override
      public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
          throws BadLocationException {
        if (text.matches("[0-9A-Fa-f]*")) { // Allow only hex characters
          super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
      }
    });

    // Ensure displayed text is in uppercase hex format
    textField.setFormatterFactory(new DefaultFormatterFactory(
        new JFormattedTextField.AbstractFormatter() {
          @Override
          public Object stringToValue(String text) throws ParseException {
            try {
              return parseHexByte(text);
            } catch (NumberFormatException e) {
              throw new ParseException("Invalid hex", 0);
            }
          }

          @Override
          public String valueToString(Object value) {
            if (value instanceof Number) {
              return Integer.toHexString(((Number) value).intValue()).toUpperCase();
            }
            return "0";
          }
        }));
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Hex Spinner Test");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(300, 200);
      frame.setLayout(new java.awt.FlowLayout());

      HexSpinner hexSpinner1 = new HexSpinner();
      HexSpinner hexSpinner2 = new HexSpinner();
      HexSpinner hexSpinner3 = new HexSpinner();
      frame.add(hexSpinner1);
      frame.add(hexSpinner2);
      frame.add(hexSpinner3);

      frame.setVisible(true);
    });
  }
}
