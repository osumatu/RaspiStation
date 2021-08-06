package Games.SpaceInvader.Models;

import Games.SpaceInvader.Controllers.Controller;
import Games.SpaceInvader.Proxy.ProxyImage;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    private int x, y;
    private int dx, dy;
    private boolean isDead;
    private static ProxyImage imageProxy;
    private final Bullet missile;

    private Controller control;

    private final List<Bullet> bullets;

    public Player() {
        if (imageProxy == null)
            imageProxy = new ProxyImage("/SpaceInvader/ship.gif");

        this.setImage(imageProxy.loadImage().getImage());

        this.setHeight(getImage().getHeight(null));
        this.setWidth(getImage().getWidth(null));

        bullets = new ArrayList<>();
        missile = new Bullet();
    }

    public void mixer() {
        x += dx * 2; // 1 e 462
        y += dy * 2; // 1 e 340

        collisionWindow();
    }

    public void collisionWindow() {

        if (this.x < 1) {
            x = 1;
        }

        if (this.x > 462) {
            x = 462;
        }

        if (this.y < 1) {
            y = 1;
        }

        if (this.y > 340) {
            y = 340;
        }
    }

    public List<Bullet> getBullet() {
        return bullets;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDx(int d) {
        this.dx = d;
    }

    public void setDy(int d) {
        this.dy = d;
    }

    public int getY() {
        return y;
    }

    public void setY(int num) {

        this.y = num;
    }

    public boolean getDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    public void fire() {
        Bullet mis = (Bullet) missile.clone();
        mis.setX(x + getWidth());
        mis.setY(y + getHeight() / 2);

        this.bullets.add(mis);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public Controller getControl() {
        return control;
    }

    public void setControl(Controller control) {
        this.control = control;
    }

}
