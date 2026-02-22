import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.*;
import javax.swing.*;

public class AlphabetWordGameNew extends JFrame implements ActionListener {

    JButton[] buttons = new JButton[26];
    JTextField display;
    JLabel questionLabel, levelLabel;
    JButton clearBtn;

    String userName;
    int level = 1;
    int index = 0;
    int score = 0;

    String[] level1 = {"APPLE","MANGO","GRAPES","BANANA","PEAR","PLUM","KIWI","FIG","DATE","LEMON"};
    String[] level2 = {"ORANGE","PAPAYA","CHERRY","PEACH","LYCHEE","GUAVA","COCONUT","MELON","AVOCADO","APRICOT"};

    ArrayList<String> words;
    String currentWord = "";
    String userWord = "";

    Color buttonColor = new Color(144, 238, 144);

    // ✅ NEW: TRACK LETTER COUNTS (FOR BANANA FIX)
    HashMap<Character, Integer> letterCount = new HashMap<>();

    public AlphabetWordGameNew(String name) {
        this.userName = name;

        setTitle("Word Game - Player: " + userName);
        setSize(900, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Arrange The Letters", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        levelLabel = new JLabel("LEVEL 1", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 30));

        topPanel.add(title);
        topPanel.add(levelLabel);
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        // ---------- CENTER PANEL ----------
        JPanel centerPanel = new JPanel(new GridLayout(4, 7, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));

        char ch = 'A';
        for(int i = 0; i < 26; i++){
            buttons[i] = new JButton(String.valueOf(ch));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setPreferredSize(new Dimension(45, 40)); // ✅ SMALL BUT VISIBLE
            buttons[i].setBackground(buttonColor);
            buttons[i].addActionListener(this);
            centerPanel.add(buttons[i]);
            ch++;
        }

        add(centerPanel, BorderLayout.CENTER);

        // ---------- BOTTOM PANEL ----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 18));

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setPreferredSize(new Dimension(420, 62));

        clearBtn = new JButton("CLEAR");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 30));
        clearBtn.setBackground(Color.RED);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setPreferredSize(new Dimension(180, 60));
        clearBtn.addActionListener(this);

        bottomPanel.add(display);
        bottomPanel.add(clearBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        startLevel1();
        setVisible(true);
    }

    void startLevel1(){
        level = 1;
        levelLabel.setText("LEVEL 1");
        loadWords(level1);
    }

    void startLevel2(){
        level = 2;
        levelLabel.setText("LEVEL 2");
        loadWords(level2);
    }

    void loadWords(String[] data){
        words = new ArrayList<>(Arrays.asList(data));
        Collections.shuffle(words);
        index = 0;
        score = 0;
        loadNext();
    }

    void loadNext(){
        if(index == 10){
            showResult();
            return;
        }

        currentWord = words.get(index);
        userWord = "";
        display.setText("");

        //  RESET BUTTONS
        for(JButton b : buttons){
            b.setEnabled(true);
            b.setBackground(buttonColor);
        }

        //  BUILD LETTER COUNT (CRITICAL BANANA FIX)
        letterCount.clear();
        for(char c : currentWord.toCharArray()){
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }

        questionLabel.setText("Arrange: " + shuffle(currentWord));
    }

    String shuffle(String word){
        ArrayList<Character> list = new ArrayList<>();
        for(char c : word.toCharArray()) list.add(c);
        Collections.shuffle(list);

        StringBuilder sb = new StringBuilder();
        for(char c : list) sb.append(c).append(" ");
        return sb.toString();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clearBtn){
            userWord = "";
            display.setText("");

            for(JButton b : buttons){
                b.setEnabled(true);
                b.setBackground(buttonColor);
            }

            // RESET COUNTS AGAIN
            letterCount.clear();
            for(char c : currentWord.toCharArray()){
                letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
            }
        }
        else{
            JButton btn = (JButton)e.getSource();
            char selected = btn.getText().charAt(0);

            if(letterCount.containsKey(selected) && letterCount.get(selected) > 0){
                userWord += selected;
                display.setText(userWord);

                letterCount.put(selected, letterCount.get(selected) - 1);

                // ✅ DISABLE ONLY WHEN COUNT BECOMES ZERO
                if(letterCount.get(selected) == 0){
                    btn.setEnabled(false);
                    btn.setBackground(Color.GRAY);
                }
            }

            if(userWord.length() == currentWord.length()){
                if(userWord.equalsIgnoreCase(currentWord)){
                    score++;
                }

                JOptionPane.showMessageDialog(this,"NEXT");
                index++;
                loadNext();
            }
        }
    }

    void showResult(){
        String result = score >= 8 ? "PASS" : "FAIL";

        try{
            FileWriter fw = new FileWriter("NewGameRecords.txt", true);
            fw.write(userName + " - Level " + level + " - " + score + "/10 - " + result + "\n");
            fw.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"File Error");
        }

        if(result.equals("PASS") && level == 1){
            JOptionPane.showMessageDialog(this,"Level 1 Passed! Level 2 Start");
            startLevel2();
        }
        else if(result.equals("PASS")){
            JOptionPane.showMessageDialog(this,"🎉 YOU WON, CONGRATULATIONS 🎉");
            System.exit(0);
        }
        else{
            JOptionPane.showMessageDialog(this,"❌ GAME OVER");
            System.exit(0);
        }
    }
}
