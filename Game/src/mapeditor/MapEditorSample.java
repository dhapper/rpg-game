package mapeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MapEditorSample extends JFrame {
    private int tileSize = 32;
    private JPanel tilePanel;
    private JPanel mapPanel;
    private BufferedImage[] sprites;
    private BufferedImage currentTile;
    private TilePanel[][] mapTiles;
    private Point selectionStart;
    private Point selectionEnd;
    private BufferedImage[][] copiedTiles;

    public MapEditorSample() {
        setTitle("Map Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);  // Initial size for the input window
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);
        JButton createButton = new JButton("Create");

        inputPanel.add(new JLabel("Width:"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Height:"));
        inputPanel.add(heightField);
        inputPanel.add(createButton);

        add(inputPanel, BorderLayout.CENTER);

        createButton.addActionListener(e -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            if (width >= 10 && height >= 10) {
                createMapEditorWindow(width, height);
            } else {
                JOptionPane.showMessageDialog(this, "Width and Height must be at least 10.");
            }
        });

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void createMapEditorWindow(int width, int height) {
        // Create a new JFrame for the map editor
        JFrame editorFrame = new JFrame("Map Editor");
        editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editorFrame.setSize(500, 500);
        editorFrame.setLayout(new BorderLayout());

        createTilePanel();
        createMapPanel(width, height);
        loadSpritesheet();

        editorFrame.add(new JScrollPane(tilePanel), BorderLayout.WEST);
        editorFrame.add(new JScrollPane(mapPanel), BorderLayout.CENTER);

        editorFrame.setLocationRelativeTo(null);
        editorFrame.setResizable(false);
        editorFrame.setVisible(true);

        // Close the initial input window
        this.dispose();
    }

    private void createTilePanel() {
        tilePanel = new JPanel();
        tilePanel.setLayout(new GridLayout(5, 5));
        tilePanel.setPreferredSize(new Dimension(160, 160));
    }

    private void createMapPanel(int width, int height) {
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(height, width));
        mapPanel.setPreferredSize(new Dimension(width * tileSize, height * tileSize));
        mapTiles = new TilePanel[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TilePanel tile = new TilePanel();
                tile.setPreferredSize(new Dimension(tileSize, tileSize));
                tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mapTiles[y][x] = tile;
                mapPanel.add(tile);
            }
        }

        mapPanel.addMouseListener(new MapMouseListener());
        mapPanel.addMouseMotionListener(new MapMouseMotionListener());
    }

    private void loadSpritesheet() {
        try {
            BufferedImage spritesheet = ImageIO.read(new File("res/SPRITESHEET_W_S_La1.png"));
            sprites = new BufferedImage[25];
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    sprites[y * 5 + x] = spritesheet.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
                    JLabel label = new JLabel(new ImageIcon(sprites[y * 5 + x]));
                    label.addMouseListener(new TileMouseListener(y * 5 + x));
                    tilePanel.add(label);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TilePanel extends JPanel {
        private BufferedImage tileImage;

        public void setTileImage(BufferedImage tileImage) {
            this.tileImage = tileImage;
            repaint();
        }

        public BufferedImage getTileImage() {
            return tileImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (tileImage != null) {
                g.drawImage(tileImage, 0, 0, tileSize, tileSize, null);
            }
        }
    }

    private class TileMouseListener extends MouseAdapter {
        private int index;

        public TileMouseListener(int index) {
            this.index = index;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            currentTile = sprites[index];
        }
    }

    private class MapMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX() / tileSize;
            int y = e.getY() / tileSize;

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (currentTile != null) {
                    mapTiles[y][x].setTileImage(currentTile);
                } else {
                    // Select the tile already placed on the map
                    currentTile = mapTiles[y][x].getTileImage();
                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                selectionStart = new Point(x, y);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e) && selectionStart != null) {
                selectionEnd = new Point(e.getX() / tileSize, e.getY() / tileSize);
                copySelection();
                selectionStart = null;
                selectionEnd = null;
            }
        }
    }

    private class MapMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e) && selectionStart != null) {
                selectionEnd = new Point(e.getX() / tileSize, e.getY() / tileSize);
                mapPanel.repaint();
            }
        }
    }

    private void copySelection() {
        if (selectionStart == null || selectionEnd == null) return;

        int startX = Math.min(selectionStart.x, selectionEnd.x);
        int startY = Math.min(selectionStart.y, selectionEnd.y);
        int endX = Math.max(selectionStart.x, selectionEnd.x);
        int endY = Math.max(selectionStart.y, selectionEnd.y);

        copiedTiles = new BufferedImage[endY - startY + 1][endX - startX + 1];
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                copiedTiles[y - startY][x - startX] = mapTiles[y][x].getTileImage();
            }
        }

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = e.getY() / tileSize;

                for (int j = 0; j < copiedTiles.length; j++) {
                    for (int i = 0; i < copiedTiles[j].length; i++) {
                        if (y + j < mapTiles.length && x + i < mapTiles[0].length) {
                            mapTiles[y + j][x + i].setTileImage(copiedTiles[j][i]);
                        }
                    }
                }
                mapPanel.removeMouseListener(this);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapEditorSample::new);
    }
}
