import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Window extends JPanel implements Runnable, ActionListener {

    private Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<KeyEvent>(4);

    private JFrame frame;
    private static Room game = Room.game;
    private Timer timer = new Timer(100, this);

    private static List<SnakeSection> sections = game.getSnake().getSections();

    public static final int SCALE = 32;
    public static final int WIDTH = game.getWidth();
    public static final int HEIGHT = game.getHeight();

    public boolean hasKeyEvents() {
        return !keyEvents.isEmpty();
    }

    public KeyEvent getEventFromTop() {
        return keyEvents.poll();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paint(Graphics g) {
        sections = game.getSnake().getSections();

        //Создаем игровое поле
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

        //Рисуем змейку
        for (int i = 0; i < sections.size(); i++) {
            if (!game.getSnake().isAlive() && i==0) g.setColor(Color.RED);
            else g.setColor(Color.GREEN);
            g.fillRect(sections.get(i).getX()*SCALE+1, sections.get(i).getY()*SCALE+1, SCALE-1, SCALE-1);
        }

        //Рисуем мышь
        g.setColor(Color.YELLOW);
        g.fillRect(game.getMouse().getX()*SCALE, game.getMouse().getY()*SCALE, SCALE, SCALE);

        //Отображение информации
        Font coins = new Font("Bitstream Charter", Font.BOLD, 20);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(coins);
        g2.drawString("ОЧКИ: " + (sections.size()-3), 10, SCALE);

        if (!game.getSnake().isAlive()) {
            Font gameOver = new Font("Bitstream Charter", Font.BOLD, 40);
            Graphics2D g3 = (Graphics2D) g;
            g3.setFont(gameOver);
            g3.drawString("Конец игры", SCALE*7, (HEIGHT*SCALE)/2);
        }

        if (game.isPause()) {
            Font pause = new Font("Bitstream Charter", Font.BOLD, 40);
            Graphics2D g3 = (Graphics2D) g;
            g3.setFont(pause);
            g3.drawString("Пауза", SCALE*9, (HEIGHT*SCALE)/2);
        }
    }

    @Override
    public void run() {
        frame = new JFrame("Змейка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH*SCALE+14, HEIGHT*SCALE+37);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(new Window());

        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                //Ничего не делает
            }

            public void keyReleased(KeyEvent e) {
                //Ничего не делает
            }

            public void keyPressed(KeyEvent e) {
                keyEvents.add(e);
            }
        });
    }

    public Window() {
        timer.start();
    }
}





























