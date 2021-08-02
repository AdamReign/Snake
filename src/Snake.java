import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;
    private int speed;

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public int getSpeed() {
        return speed;
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) isAlive = false;
    }

    public void move() {
        if (!isAlive) return;

        //Скорость змейки змейки
        speed = sections.size() < 15 ? 500-(20*sections.size()) : 200;

        //Стороны движения змейки
        if (direction == SnakeDirection.UP) move(0, -1);
        if (direction == SnakeDirection.RIGHT) move(1, 0);
        if (direction == SnakeDirection.DOWN) move(0, 1);
        if (direction == SnakeDirection.LEFT) move(-1, 0);
    }

    public void move(int dx, int dy) {
        SnakeSection newHead = new SnakeSection(sections.get(0).getX()+dx, sections.get(0).getY()+dy);

        //Пересекание змеи границ поля
        if (newHead.getX() < 0) newHead = new SnakeSection(Room.game.getWidth()-1, newHead.getY());
        else if (newHead.getX() == Room.game.getWidth()) newHead = new SnakeSection(0, newHead.getY());
        else if (newHead.getY() < 0) newHead = new SnakeSection(newHead.getX(),Room.game.getHeight()-1);
        else if (newHead.getY() == Room.game.getHeight()) newHead = new SnakeSection(newHead.getX(),0);

        //Проверка змеи на смерть
        checkBody(newHead);

        //Создание новой головы змеи
        if (isAlive) sections.add(0, newHead);
        else return;

        //Поедание мыши
        if (Room.game.getMouse().getX() == sections.get(0).getX() && Room.game.getMouse().getY() == sections.get(0).getY())
            Room.game.eatMouse();
        else sections.remove(sections.size()-1);
    }

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(0, new SnakeSection(x, y-2));
        sections.add(0, new SnakeSection(x, y-1));
        sections.add(0, new SnakeSection(x, y));
        isAlive = true;
    }
}
