import java.awt.event.KeyEvent;

public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    static Room game;
    private boolean pause = false;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPause() {
        return pause;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public void createMouse() {
        //Координаты созданной мыши
        int sWidth;
        int sHeight;

        //Выбираем свободное место для спавна мыши
        do {
            sWidth = (int) (Math.random() * width);
            sHeight = (int) (Math.random() * height);
        } while (snake.getSections().contains(new SnakeSection(sWidth, sHeight)));

            mouse = new Mouse(sWidth, sHeight);
    }

    public void eatMouse() {
        createMouse();
    }

    public void sleep() {
        // Делаем паузу, длинна которой зависит от длинны змеи
        try { Thread.sleep(snake.getSpeed()); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        Window keyboard = new Window();
        Thread gameThread = new Thread(keyboard);
        gameThread.start();

        while (true) {
            //Пока змея жива
            while (snake.isAlive()) {
                //"Наблюдатель" содержит события о нажатии клавиш?
                if (keyboard.hasKeyEvents()) {
                    KeyEvent event = keyboard.getEventFromTop();

                    //Если равно ESCAPE - поставить игру на паузу.
                    if (event.getKeyChar() == KeyEvent.VK_ESCAPE) {
                        pause = true;
                        continue;
                    }
                    else pause = false;

                    //Если "стрелка влево" - сдвинуть змейку влево
                    if (event.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != SnakeDirection.RIGHT)
                        snake.setDirection(SnakeDirection.LEFT);
                        //Если "стрелка вправо" - сдвинуть змейку вправо
                    else if (event.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != SnakeDirection.LEFT)
                        snake.setDirection(SnakeDirection.RIGHT);
                        //Если "стрелка вверх" - сдвинуть змейку вверх
                    else if (event.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != SnakeDirection.DOWN)
                        snake.setDirection(SnakeDirection.UP);
                        //Если "стрелка вниз" - сдвинуть змейку вниз
                    else if (event.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != SnakeDirection.UP)
                        snake.setDirection(SnakeDirection.DOWN);
                }

                if (pause) continue; //Проверяем игру на паузу
                snake.move();   //Двигаем змею
                sleep();        //Пауза между ходами
            }

            //Если змея погибает, то создаем новую змейку нажатием ENTER
            if (keyboard.hasKeyEvents()) {
                KeyEvent event = keyboard.getEventFromTop();

                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    game.snake = new Snake(((width % 2 == 0 ? width : width - 1) / 2), ((height % 2 == 0 ? height : height - 1) / 2));
                    game.snake.setDirection(SnakeDirection.DOWN);
                    game.createMouse();
                }
            }
        }
    }

    public static void main(String[] args) {
        int x = 21;
        int y = 21;
        game = new Room(x, y, new Snake(((x%2==0 ? x : x-1)/2), ((y%2==0 ? y : y-1)/2)));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }
}
























