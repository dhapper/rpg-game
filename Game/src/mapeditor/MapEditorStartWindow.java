package mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.*;

import locations.Area;

public class MapEditorStartWindow extends JFrame {

    private JPanel inputPanel, loadMapPanel;
    private JTextField widthField, heightField;
    private JLabel widthLabel, heightLabel;
    private JButton submitButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapEditorStartWindow::new);
    }

    public MapEditorStartWindow() {
        setTitle("Map Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 500);
        setLayout(new BorderLayout());

        // Create and set up the input panel
        inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        widthLabel = new JLabel("Width:");
        heightLabel = new JLabel("Height:");
        widthField = new JTextField();
        heightField = new JTextField();
        submitButton = new JButton("Submit");

        inputPanel.add(widthLabel);
        inputPanel.add(widthField);
        inputPanel.add(heightLabel);
        inputPanel.add(heightField);
        inputPanel.add(submitButton);

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput();
            }
        });

        // Create and set up the load map panel
        loadMapPanel = new JPanel(new GridLayout(5, 1, 5, 5)); // Align buttons in the center
        loadMapPanel.setPreferredSize(new Dimension(200, 5 * 100));
        addButtonsToLoadMapPanel();

        JScrollPane scrollPane = new JScrollPane(loadMapPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add the panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set the frame properties
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void handleInput() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());

            if (width >= 32 && height >= 16) {
                loadMapPanel.removeAll(); // Clear previous buttons
                addButtonsToLoadMapPanel();
                loadMapPanel.revalidate(); // Refresh the panel
                loadMapPanel.repaint();
                
                new MapEditor(null, width, height);
                
            } else {
                JOptionPane.showMessageDialog(this, "Width must be at least 32 and height must be at least 16.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for width and height.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addButtonsToLoadMapPanel() {
    	
    	ArrayList<String> fileNames = getMapFiles("res/");
    	
        for (String fileName : fileNames) {
            JButton button = new JButton(fileName);
            button.addActionListener(e -> handleButtonAction(fileName));
            loadMapPanel.add(button);
        }
    }
    
    private void handleButtonAction(String fileName) {
        System.out.println("Button clicked: " + fileName);
        
        ArrayList<int[][]> map = Area.ReadMapDataFromFile("res/"+fileName);
        int width = map.get(0)[0].length;
        int height = map.get(0).length;
        
        new MapEditor(map, width, height);
        
        // call map editorm, add maps to constructor
    }
    
    public static ArrayList<String> getMapFiles(String resFolderPath) {
        ArrayList<String> mapFiles = new ArrayList<String>();

        try {
            Files.walk(Paths.get(resFolderPath))
                 .filter(Files::isRegularFile) // Only regular files (not directories)
                 .filter(path -> path.getFileName().toString().contains("MAP_DATA")) // Filenames containing "MAP"
                 .forEach(path -> mapFiles.add(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapFiles;
    }
}
