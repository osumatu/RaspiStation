package Games.SpaceInvader.Models;

import Games.SpaceInvader.Proxy.ProxyImage;

import java.awt.Rectangle;


public class Bullet extends GameObject {

    private int x;
    private int y;
    private static ProxyImage imageProxy;
    private static final int SCREEN_WIDTH = 500;
    private static final int VELOCITY = 3;

    public Bullet() {
        if (imageProxy == null) {
            imageProxy = new ProxyImage("/SpaceInvader/bullet.png");
        }
        this.setImage(imageProxy.loadImage().getImage());
        this.setHeight(getImage().getHeight(null));
        this.setWidth(getImage().getWidth(null));

        this.setVisibility(true);
    }

    public void mixer() {
        this.x += VELOCITY;
        if (this.x > SCREEN_WIDTH) {
            setVisibility(false);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

}
