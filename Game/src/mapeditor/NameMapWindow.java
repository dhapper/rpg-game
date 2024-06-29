package mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class NameMapWindow extends JFrame{

	private JPanel inputPanel;// loadMapPanel;
    private JTextField mapNameField;
    private JLabel mapNameLabel;
    private JButton saveButton;

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(NameMapWindow::new);
//    }

    public NameMapWindow() {
        setTitle("Map Editor: Save");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 150);
        setLayout(new BorderLayout());

        // Create and set up the input panel
        inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        mapNameLabel = new JLabel("Map Name:");
        mapNameField = new JTextField();
        mapNameField.setText("MAP_DATA_0");
        saveButton = new JButton("Submit");

        inputPanel.add(mapNameLabel);
        inputPanel.add(mapNameField);
        inputPanel.add(saveButton);

        // Add action listener to the submit button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput();
            }

        });

        // Add the panels to the frame
        add(inputPanel, BorderLayout.NORTH);

        // Set the frame properties
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    private void handleInput() {
		String fileName = mapNameField.getText();
		SaveMap.WriteToFile(MapEditor.TILE_LAYERS, fileName);
		System.exit(EXIT_ON_CLOSE);
	}
	
}
