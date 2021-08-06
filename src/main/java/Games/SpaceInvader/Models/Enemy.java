package Games.SpaceInvader.Models;

import java.awt.Rectangle;

public class Enemy extends GameObject {

    private int x;
    private int y;

    private static final int VELOCITY = 2;

    public Enemy() {
    }

    public static int GeneratePosX() {
        return GeneratePosX(100);
    }

    public static int GeneratePosX(int nb) {
        return 456 + (int) (Math.random() * 16 * nb);
    }

    public static int GeneratePosY() {
        return 10 + (int) (Math.random() * 320);
    }

    public void move(int nbEnemy) {
        if (this.x < 0) {
            this.x = GeneratePosX(nbEnemy);
            this.y = GeneratePosY();
        } else {
            this.x -= VELOCITY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

}
