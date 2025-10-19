import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class PlayerEntity {
   
    private final JPanel panel;
    public int x;
    public int dx = 0; // Change in x
    public int dy = 0; // Change in y
    public int y;
    public static int length = 225;
    public static int diameter = 50; // Diameter of the player circle
    public static int height = 104;
  

    private final int speed = 4;
 

    private Dimension dimension;
    private final Image logo;

   

    public PlayerEntity(JPanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();

        x = xPos;
        y = yPos;

        int randomSideDirection = (int) (Math.random() * 2) + 1; // Random direction 1-2
        int randomUpDirection = (int) (Math.random() * 2) + 1; // Random up direction 1-2

        switch (randomSideDirection) {
            case 1 -> dx = 1 * speed; // Move right
            case 2 -> dx = -1 * speed; // Move left
        }

        switch (randomUpDirection) {
            case 1 -> dy = 1 * speed; // Move down
            case 2 -> dy = -1 * speed; // Move up
        }

        logo = ImageManager.loadImage("logo.png");

    }
       

    // Draw onto a provided Graphics2D (called from GamePanel.paintComponent)
    public void draw(Graphics2D g2) {

        g2.drawImage(logo, x, y, length, height, panel);
    }

    // Erase no longer needed; painting is handled by Swing's repaint cycle

    
   
    public void move() {
        if (!panel.isVisible()) return;

        dimension = panel.getSize();

        if(x + dx < 0 || x + length + dx > dimension.width) {
            dx = -dx; // Reverse horizontal direction
        }
        if(y + dy < 0 || y + height + dy > dimension.height) {
            dy = -dy; // Reverse vertical direction
        }

        x += dx;
        y += dy;

        // Position updated; actual drawing occurs in GamePanel.paintComponent via repaint()
    }

    
    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, diameter, diameter);
     }
  
    
}
