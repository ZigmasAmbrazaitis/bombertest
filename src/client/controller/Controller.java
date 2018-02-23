package client.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.sun.glass.ui.Cursor.setVisible;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Controller extends JFrame {
    private static final double CELL_WIDTH = 64;
    private static final double CELL_HEIGHT = 64;
    private String _failoVardas = "map.txt";
    private static final boolean isRunning = true;
    private static final int fps = 60;
    private int[][] map = new int[10][10];
    private static final int windowWidth = 1200;
    private static final int windowHeight = 700;
    private int positionX = windowHeight / 2;
    private int positionY = windowWidth / 2;

    public Pane mapContainer;

// drawMap vienas veikia. Run vienas paleidzia ne ta app kuri reikia
    @FXML
    public void initialize() {

        drawMap();
        run();
    }

    private void drawMap() {
        getData();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                ImageView cell = new ImageView();
                mapContainer.getChildren().add(cell);
                cell.setFitHeight(CELL_HEIGHT);
                cell.setFitWidth(CELL_WIDTH);
                cell.setTranslateX(CELL_WIDTH * j);
                cell.setTranslateY(CELL_HEIGHT * i);
                File sprite;
                Image image;
                switch (map[i][j]) {
                    case 0:
                        sprite = new File("ground.jpg");
                        image = new Image(sprite.toURI().toString());
                        cell.setImage(image);
                        break;
                    case 1:
                        sprite = new File("boulder.jpg");
                        image = new Image(sprite.toURI().toString());
                        cell.setImage(image);
                        break;
                    case 2:
                        sprite = new File("boulder.jpg");
                        image = new Image(sprite.toURI().toString());
                        cell.setImage(image);
                        break;
                }
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    positionY += 20;
                    break;
                case KeyEvent.VK_UP:
                    positionY -= 20;
                    break;
                case KeyEvent.VK_LEFT:
                    positionX -= 20;
                    break;
                case KeyEvent.VK_RIGHT:
                    positionX += 20;
                    break;
            }
        }
    }

    private void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new AL());
        showData(map);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);
        while (isRunning) {
            long time = System.currentTimeMillis();
            //update();
            //draw();
            time = (1000 / fps) - (System.currentTimeMillis() - time);
            if (time > 0) {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {
                }
            }
        }
        //setVisible(false);
    }

    void update() {

    }

    void draw() {
        Graphics g = getGraphics(); // map graphics
        Graphics pg = getGraphics(); // player graphics

        g.fillRect(0,0,1000,1000);

        BufferedImage bomber;
        try {
            bomber = ImageIO.read(new File("bomber.jpg"));
            pg.drawImage(bomber, positionX, positionY, 150, 150, Color.BLACK, null);
        } catch (IOException klaida) {
            klaida.printStackTrace();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void getData() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(_failoVardas));

            final int rows = 10;
            final int cols = 10;
            map = new int[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    map[row][col] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showData(int[][] a) {
        final int rows = a.length;
        final int cols = a[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("  " + a[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
