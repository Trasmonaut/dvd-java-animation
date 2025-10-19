import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GamePanel: minimal, self-contained game surface for the template.
 * - Owns a PlayerEntity and renders it to an offscreen buffer.
 * - Provides a simple game loop thread with start/stop flags.
 * - Exposes simple actions: updateGameEntities(direction), swung(), shield().
 *
 * Keep it simple so you can copy this into new projects and customize.
 */
public class GamePanel extends JPanel {

    private PlayerEntity player;
    private final int screenwidth = GameWindow.screenWidth;
    private final int screenheight = GameWindow.screenHeight;

    private Timer gameTimer;
    private boolean isStarted;
    private boolean isRunning;

    public GamePanel() {
        setBackground(Color.black);
        setDoubleBuffered(true); // rely on Swing's built-in double buffering
    }

    /** Create initial entities for a new game. */
    public void createGameEntities() {
        // Start player roughly at center
        player = new PlayerEntity(this, screenwidth/2 - PlayerEntity.diameter/2, screenheight/2 - PlayerEntity.diameter/2);
    
    }

    /** Update entities based on a direction code (1..8). */
    public void updateGameEntities() {
        if (!isRunning || player == null) return;
        player.move();
    }


    // ============================ LOOP ============================ //
    /** Draw current state to the panel from the back buffer. */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // Render player entity
        if (player != null) {
            player.draw(g2);
        }
        g2.dispose();
        // Reduce tearing on some systems
        java.awt.Toolkit.getDefaultToolkit().sync();
    }

    /** Start the game loop once. */
    public void startGame() {
        if (isStarted || isRunning) {
            System.out.println("Game already started. Reopen application to start again.");
            return;
        }
        isStarted = true;
        isRunning = true;
        // ~60 FPS timer driving update + render
        gameTimer = new Timer(16, e -> {
            // reference event so lambda parameter is used (keeps it simple)
            if (e.getSource() == null) return;
            if (!isRunning) return;
            updateGameEntities();
            repaint();
        });
        gameTimer.start();
        createGameEntities();
        System.out.println("Number of threads: " + Thread.activeCount());
    }

    public void pauseGame() {
        isRunning = false;
        repaint();
    }

    public void startStopGame() {
        if (!isStarted) {
            startGame();
        } else {
            isRunning = !isRunning;
        }
    }
}