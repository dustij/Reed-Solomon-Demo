# Reed-Solomon Demo

A visual and interactive Java application to explore the fundamentals of Reed-Solomon (RS) error correction codes. This project was created for presentation at the Washburn University Apeiron — a professional forum for original student scholarship and research.

---

## 📘 Abstract

Error correction is essential for ensuring reliable digital communication and data storage. Reed-Solomon (RS) codes are a widely used error correction technique found in QR codes, CDs/DVDs, Blu-ray discs, and deep-space transmissions, where data corruption from noise or physical damage can render information unreadable.

This project explores the mathematical foundation of RS codes, specifically their ability to detect and correct burst errors using polynomial division over Galois Fields of order 2⁸ (GF(2⁸)). By encoding data as polynomials and performing arithmetic within GF(2⁸), RS codes enable fast and efficient error detection and correction at the hardware level.

Through a Java-based graphical application, this tool visualizes encoding, error injection, and decoding steps, revealing how syndrome calculation and polynomial division identify and correct errors.

---

## 🎯 Purpose

This application was developed as an original scholarly project for the Washburn University Apeiron, an academic event that allows students to showcase research, creativity, and community engagement. Each presentation is mentored by faculty and is expected to reflect work beyond a typical class project, meeting the standards of a professional forum.

The purpose of this project is to provide a hands-on, educational demonstration of Reed-Solomon encoding and error correction, making a mathematically intensive process approachable through an interactive user interface.

---

## 📁 Project Structure

```
src/
├── Main.java               # Application entry point
├── rs/
│   ├── RS5.java            # Core RS implementation (version 5)
│   └── RS6.java            # Core RS implementation (version 6)
└── ui/
├── MainFrame.java       # GUI logic
├── HexSpinner.java      # Spinner for byte input
└── JTextFieldLimit.java # Text field limiter for user input
```

## ⚙️ How to Compile and Run

Ensure you have Java (JDK 8 or higher) installed. This project was built with OpenJDK 23.0.2.

### Compile:

```bash
javac -d target -sourcepath src src/Main.java
```

### Run:

```bash
java -cp target Main
```

## 🧪 How to Use the Program

This application demonstrates how Reed-Solomon (RS) encoding and decoding works using polynomial arithmetic over GF(2⁸). Here's a step-by-step guide:

---

### 1. **Enter a Message**

-   In the text field at the top, type a message up to **12 characters long**.
-   Click **Encode** to start the process.
-   The application will convert each character into an 8-bit symbol and pad the message to 20 bytes total (12 message + 8 parity symbols).

---

### 2. **View the Generated Polynomials**

After encoding, three key polynomials will be displayed:

-   **P(x):** The original message polynomial, padded with eight zeros for parity.
-   **G(x):** The generator polynomial, which has roots at \( x = 2^i \) for \( i = 0 \) to \( 7 \). These roots (01, 02, 04, 08, 10, 20, 40, 80 in hex) are chosen for their mathematical properties in GF(2⁸).
-   **M(x):** The final encoded message. It is computed by dividing P(x) by G(x), taking the remainder (parity), and subtracting (adding in GF) that remainder from P(x). This ensures that all **syndromes** will be 0 if the message is uncorrupted.

---

### 3. **Check the Syndromes**

-   The **Syndromes** label shows the result of evaluating M(x) at the generator roots.
-   A correct message will produce **all-zero syndromes**.
-   If any syndrome is non-zero, it indicates that an error has occurred in the message.

---

### 4. **Inject Errors**

-   Use the 20 hex spinners to manually inject errors into the message:
    -   The first 12 spinners represent the message symbols.
    -   The last 8 spinners (in blue) represent the parity symbols.
-   Each spinner allows you to modify the value of a byte (RS symbol).
-   When you change a value, it turns **yellow** to indicate a simulated transmission error.
-   You can introduce up to **4 errors**, which is the maximum this RS(20,12) implementation can correct.

---

### 5. **Click Decode**

-   After injecting errors, click **Decode** to begin the error correction process.
-   The following outputs will be populated:

-   **Error Locator Polynomial (e(x))**: Used to find the positions of the errors in the message.

-   **Error Positions**: Each value represents the location of an error, determined by the exponent \( i \), where \( x^i \) is the position in the message polynomial. The corresponding **coefficient** is the corrupted symbol at that position.

-   **Error Magnitudes**: The calculated correction values (deltas), which will be added (XORed) to the corrupted symbols to recover the original message.

-   **Received Message**: The corrupted version, showing the current spinner values.

-   **Corrected Message**: The final result after applying error correction.

---

> 🧠 Tip: Reed-Solomon codes operate on symbols (1 byte each), not bits, and their strength lies in correcting **burst errors**, which affect multiple bits in consecutive bytes.
