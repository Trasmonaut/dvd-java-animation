import java.awt.*;              // GUI objects
import java.awt.event.*;        // AWT events
import javax.swing.*;           // Swing widgets

/**
 * GameWindow: a simple, readable template window.
 * - Top info bar shows health and points
 * - Center is the GamePanel
 * - Bottom has Start/Exit buttons
 *
 * Keep this minimal so it's easy to reuse for new games.
 */
public class GameWindow extends JFrame {

    // Icons for info labels
   

    // Info bar
   
    public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    // Buttons
    
    // Layout
    private final Container c;
    private JPanel mainPanel;
    private GamePanel gamePanel;

    public GameWindow() {
        // Window basics
       

        setTitle("dvd-logo");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Defer visibility until after content/layout setup
      
       

        // Main panel layout - use BorderLayout to avoid gaps
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(null);

        // Game area
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Fullscreen size
        gamePanel.createGameEntities();

       
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.setFocusable(true);
        // Use true fullscreen (undecorated + exclusive full screen when supported)
        setUndecorated(true);

        

        // Input listeners
        // Key input: handle Escape (exit) and Space (start)
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0); // Exit on Escape key
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    gamePanel.startStopGame(); // S gamePanel.pauseGame();
                }
            }
        });

        // Show
        c = getContentPane();
        c.add(mainPanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Attempt exclusive fullscreen where available, after the frame is realized
        SwingUtilities.invokeLater(() -> {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(this);
            } else {
                // Fallback to maximized window
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setVisible(true);
            }
        });

        // Ensure keyboard focus is granted to the main panel once the window is visible
        SwingUtilities.invokeLater(() -> {
            if (!mainPanel.requestFocusInWindow()) {
                mainPanel.requestFocus();
            }
        });
    }
    // No additional listener methods needed; we use adapters above.
}