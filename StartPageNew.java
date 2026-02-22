

import java.awt.*;
import javax.swing.*;

public class StartPageNew extends JFrame {

    public StartPageNew() {
        setTitle("Word Game - Start");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("ALPHABET WORD QUIZ GAME");
        title.setFont(new Font("Arial", Font.BOLD, 35));

        JButton startBtn = new JButton("START GAME");
        startBtn.setFont(new Font("Arial", Font.BOLD, 28));
        startBtn.setBackground(new Color(144, 238, 144));
        startBtn.setPreferredSize(new Dimension(220, 55));

        gbc.gridy = 0;
        mainPanel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 0, 0);
        mainPanel.add(startBtn, gbc);

        add(mainPanel);

        startBtn.addActionListener(e -> {
            new NamePageNew();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StartPageNew();
    }
}
