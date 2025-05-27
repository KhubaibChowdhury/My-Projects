import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FrameIntro extends JFrame {
    JTextField nameField, ageField, weightField, heightField, waterField;
    JButton bmiBtn, dietBtn, waterBtn, tipsBtn, saveBtn, exitBtn, viewHistoryBtn, deleteHistoryBtn;
    JTextArea resultArea;

    public FrameIntro() {
        super("ACE Health Control Panel");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // Background Image
        ImageIcon bgImg = new ImageIcon("C:/Users/user/Desktop/Project/12.jpg");
        JLabel bgLabel = new JLabel(bgImg);
        bgLabel.setBounds(0, 0, 1920, 1080);
        bgLabel.setLayout(null); // Important for adding components on top
		// Title Label
        JLabel titleLabel = new JLabel("ACE Health Control Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 20, 1920, 50); // Full width, adjust Y if needed
        bgLabel.add(titleLabel);

        // Labels
        JLabel nameLabel = createLabel("Name:", 50, 100);
        JLabel ageLabel = createLabel("Age:", 50, 150);
        JLabel weightLabel = createLabel("Weight (kg):", 50, 200);
        JLabel heightLabel = createLabel("Height (m):", 50, 250);
        JLabel waterLabel = createLabel("Water Intake (L):", 50, 300);

        // Text Fields
        nameField = createField(200, 100);
        ageField = createField(200, 150);
        weightField = createField(200, 200);
        heightField = createField(200, 250);
        waterField = createField(200, 300);

        /* Result Area
        resultArea = new JTextArea();
        resultArea.setBounds(700, 100, 800, 500);
        resultArea.setBackground(Color.WHITE);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 18));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
		*/
		// Result Area with Scroll
        resultArea = new JTextArea();
        resultArea.setBackground(Color.WHITE);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 18));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(700, 100, 800, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        // Buttons - First row
        bmiBtn = createButton("BMI", 100, 400, Color.GREEN);
        dietBtn = createButton("Diet Chart", 260, 400, Color.ORANGE);
        waterBtn = createButton("Hydration Check", 440, 400, Color.CYAN);

        // Buttons - Second row
        tipsBtn = createButton("Health Tips", 100, 460, Color.YELLOW);
        saveBtn = createButton("Save Info", 260, 460, Color.MAGENTA);
        exitBtn = createButton("Exit", 440, 460, Color.RED);

        // History buttons below result box
        viewHistoryBtn = createButton("View History", 700, 620, Color.LIGHT_GRAY);
        deleteHistoryBtn = createButton("Delete History", 880, 620, Color.DARK_GRAY);
        deleteHistoryBtn.setForeground(Color.WHITE);

        // Add hover effect
        addHoverEffect(bmiBtn);
        addHoverEffect(dietBtn);
        addHoverEffect(waterBtn);
        addHoverEffect(tipsBtn);
        addHoverEffect(saveBtn);
        addHoverEffect(exitBtn);
        addHoverEffect(viewHistoryBtn);
        addHoverEffect(deleteHistoryBtn);

        // Add everything to bgLabel
        bgLabel.add(nameLabel);
        bgLabel.add(ageLabel);
        bgLabel.add(weightLabel);
        bgLabel.add(heightLabel);
        bgLabel.add(waterLabel);

        bgLabel.add(nameField);
        bgLabel.add(ageField);
        bgLabel.add(weightField);
        bgLabel.add(heightField);
        bgLabel.add(waterField);

        //bgLabel.add(resultArea);
		bgLabel.add(scrollPane);


        bgLabel.add(bmiBtn);
        bgLabel.add(dietBtn);
        bgLabel.add(waterBtn);
        bgLabel.add(tipsBtn);
        bgLabel.add(saveBtn);
        bgLabel.add(exitBtn);
        bgLabel.add(viewHistoryBtn);
        bgLabel.add(deleteHistoryBtn);

        // Action listeners
        bmiBtn.addActionListener(e -> showBMI());
        dietBtn.addActionListener(e -> showDiet());
        waterBtn.addActionListener(e -> showWaterAdvice());
        tipsBtn.addActionListener(e -> showTips());
        saveBtn.addActionListener(e -> saveData());
        viewHistoryBtn.addActionListener(e -> viewHistory());
        deleteHistoryBtn.addActionListener(e -> deleteHistory());
        exitBtn.addActionListener(e -> System.exit(0));

        // Add to frame
        this.setContentPane(bgLabel);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 30);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 200, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        return field;
    }

    private JButton createButton(String text, int x, int y, Color bg) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 140, 40);
        button.setBackground(bg);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void addHoverEffect(JButton button) {
        Color original = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(original.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(original);
            }
        });
    }

    private void showBMI() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            double bmi = weight / (height * height);
            String status = (bmi < 18.5) ? "Underweight" :
                            (bmi < 24.9) ? "Normal" :
                            (bmi < 29.9) ? "Overweight" : "Obese";

            String result = String.format("BMI: %.2f\nStatus: %s", bmi, status);
            resultArea.setText(result);
            appendToHistory(result);
        } catch (Exception ex) {
            resultArea.setText("Please enter valid weight and height.");
        }
    }

    private void showDiet() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            double bmi = weight / (height * height);
            String diet;

            if (bmi < 18.5)
                diet = "Breakfast: Eggs, Toast\nLunch: Chicken & Rice\nDinner: Fish & Veggies";
            else if (bmi < 24.9)
                diet = "Breakfast: Oats, Banana\nLunch: Veggie Wrap\nDinner: Soup & Salad";
            else if (bmi < 29.9)
                diet = "Breakfast: Smoothie\nLunch: Grilled Chicken\nDinner: Stir-fry Veg";
            else
                diet = "Breakfast: Low-carb cereal\nLunch: Salad with lean meat\nDinner: Steamed Veggies";

            resultArea.setText("Recommended Diet:\n" + diet);
            appendToHistory("Recommended Diet:\n" + diet);
        } catch (Exception ex) {
            resultArea.setText("Please enter valid weight and height.");
        }
    }
	
	private void showWaterAdvice() {
    try {
        int age = Integer.parseInt(ageField.getText());
        double water = Double.parseDouble(waterField.getText());
        double required;

        if (age < 8)
            required = 1.2;
        else if (age <= 13)
            required = 1.6;
        else if (age <= 18)
            required = 1.9;
        else if (age <= 50)
            required = 2.5;
        else
            required = 2.0;

        String advice = String.format(
            "Your age: %d\nYou drank: %.1f L\nRecommended: %.1f L/day\n",
            age, water, required
        );

        advice += (water >= required) ?
            "Great! You're drinking enough water." :
            "You need to drink more water.";

        resultArea.setText("Water Intake Result:\n" + advice);
        appendToHistory("Water Intake Result:\n" + advice);

    } catch (Exception ex) {
        resultArea.setText("Please enter valid age and water amount.");
    }
}


    /*private void showTips() {
        String tips = "Health Tips:\n" +
                      "- Sleep at least 7–8 hours a day\n" +
                      "- Eat vegetables & drink water\n" +
                      "- Avoid junk food\n" +
                      "- Do light exercise or walking";
        resultArea.setText(tips);
        appendToHistory(tips);
    }*/
	
	private void showTips() {
    try {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        double weight = Double.parseDouble(weightField.getText());
        double height = Double.parseDouble(heightField.getText());
        double bmi = weight / (height * height);
        double water = Double.parseDouble(waterField.getText());

        StringBuilder tips = new StringBuilder("Health Tips for " + name + ":\n");

        // Age-based
        if (age < 18)
            tips.append("- You're still growing! Eat protein-rich and balanced meals.\n");
        else if (age <= 40)
            tips.append("- Stay active, avoid junk food, and manage stress.\n");
        else
            tips.append("- Monitor blood pressure and sugar levels regularly.\n");

        // BMI-based
        if (bmi < 18.5)
            tips.append("- Your BMI is low. Add more calories and nutrients to your meals.\n");
        else if (bmi < 24.9)
            tips.append("- Your BMI is normal. Maintain your healthy lifestyle!\n");
        else if (bmi < 29.9)
            tips.append("- Slightly overweight. Try walking 30 mins daily.\n");
        else
            tips.append("- Obese. Consider a diet plan and regular exercise.\n");

        // Water intake check
        double recommendedWater = (age < 8) ? 1.2 : (age <= 13) ? 1.6 : (age <= 18) ? 1.9 :
                                   (age <= 50) ? 2.5 : 2.0;
        if (water < recommendedWater)
            tips.append("- Drink more water! Try to reach at least " + recommendedWater + " L daily.\n");
        else
            tips.append("- Good job staying hydrated!\n");

        // General tips
        tips.append("- Sleep 7–8 hours per night.\n");
        tips.append("- Add fruits and green veggies to your meals.\n");

        resultArea.setText(tips.toString());
        appendToHistory(tips.toString());

    } catch (Exception e) {
        resultArea.setText("Please enter valid information before requesting tips.");
    }
}


    private void saveData() {
        try (FileWriter fw = new FileWriter("userdata.txt", true)) {
            fw.write("Name: " + nameField.getText() + "\n");
            fw.write("Age: " + ageField.getText() + "\n");
            fw.write("Weight: " + weightField.getText() + "\n");
            fw.write("Height: " + heightField.getText() + "\n");
            fw.write("Water: " + waterField.getText() + "\n");
            fw.write("-----------------------------\n");
            resultArea.setText("Information saved.");
        } catch (IOException e) {
            resultArea.setText("Error saving information.");
        }
    }

    private void appendToHistory(String data) {
        try (FileWriter fw = new FileWriter("userdata.txt", true)) {
            fw.write(data + "\n-----------------------------\n");
        } catch (IOException e) {
            resultArea.setText("Error writing to history.");
        }
    }

    private void viewHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader("userdata.txt"))) {
            StringBuilder history = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                history.append(line).append("\n");
            }
            resultArea.setText(history.toString());
        } catch (IOException e) {
            resultArea.setText("No history found.");
        }
    }

    private void deleteHistory() {
        try {
            File f = new File("userdata.txt");
            if (f.exists()) {
                if (f.delete())
                    resultArea.setText("History deleted.");
                else
                    resultArea.setText("Error deleting history.");
            } else {
                resultArea.setText("No history to delete.");
            }
        } catch (Exception e) {
            resultArea.setText("Error occurred while deleting.");
        }
    }

    public static void main(String[] args) {
        new FrameIntro().setVisible(true);
    }
}
