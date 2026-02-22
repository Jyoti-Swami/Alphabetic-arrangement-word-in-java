
import java.awt.*;
import javax.swing.*;

public class NamePageNew extends JFrame {

    JTextField nameField;

    public NamePageNew() {
        setTitle("Enter Your Name");
        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // ✅ LABEL
        JLabel label = new JLabel("Enter Your Name");
        label.setFont(new Font("Arial", Font.BOLD, 32));

        // ✅ BIG TEXT FIELD (WIDTH + HEIGHT INCREASED)
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 26));
        nameField.setPreferredSize(new Dimension(500, 60));  // ✅ HEIGHT & WIDTH INCREASED

        // ✅ BIG START BUTTON
        JButton startBtn = new JButton("START QUIZ");
        startBtn.setFont(new Font("Arial", Font.BOLD, 30));
        startBtn.setBackground(new Color(144, 238, 144));
        startBtn.setPreferredSize(new Dimension(400, 120));

        // ✅ ADD COMPONENTS
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(50, 0, 0, 0);
        panel.add(startBtn, gbc);

        add(panel);

        // ✅ BUTTON LOGIC
        startBtn.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Name");
            } else {
                new AlphabetWordGameNew(name);  // ✅ Opens game
                dispose();                      // ✅ Closes name page
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new NamePageNew();
    }
}
