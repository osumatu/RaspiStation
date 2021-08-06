package Games.SpaceInvader.Views;

import Games.SpaceInvader.Controllers.Controller;
import Games.SpaceInvader.Models.Bullet;
import Games.SpaceInvader.Models.Enemy;
import Games.SpaceInvader.Models.Player;
import Games.SpaceInvader.Proxy.ProxyImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
    private final Image background;
    private final Player playerOne;

    private boolean playing;
    private boolean begin;
    private boolean isWon;

    private List<Enemy> enemies;

    public Game() {

        Player nave = new Player();

        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(new KeyboardAdapter());

        ImageIcon reference = new ImageIcon(getClass().getResource("/SpaceInvader/background.png"));
        background = reference.getImage();

        playerOne = (Player) nave.clone();
        playerOne.setX(100);
        playerOne.setY(100);
        playerOne.setControl(Controller.PLAYER_1);

        playing = false;
        isWon = false;
        begin = true;

        initEnemy();

        Timer timer = new Timer(5, this);
        timer.start();
    }

    private void initEnemy() {
        enemies = new ArrayList<>();
        Enemy enemy = new Enemy();

        ProxyImage enemyOneImage = new ProxyImage("/SpaceInvader/enemy_1.gif");
        ProxyImage enemyTwoImage = new ProxyImage("/SpaceInvader/enemy_2.gif");

        for (int i = 0; i < 100; i++) {
            Enemy ini = (Enemy) enemy.clone();
            ini.setX(Enemy.GeneratePosX());
            ini.setY(Enemy.GeneratePosY());

            if (i % 3 == 0) {
                ini.setImage(enemyTwoImage.loadImage().getImage());
            } else {
                ini.setImage(enemyOneImage.loadImage().getImage());
            }

            ini.setHeight(ini.getImage().getHeight(null));
            ini.setWidth(ini.getImage().getWidth(null));

            ini.setVisibility(true);
            enemies.add(ini);
        }
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(background, 0, 0, null);

        if (playing) {

            if (!playerOne.getDead()) {
                graphics.drawImage(playerOne.getImage(), playerOne.getX(), playerOne.getY(), this);
            }

            List<Bullet> bullets1 = playerOne.getBullet();

            for (Bullet bullet : bullets1) {

                graphics.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);

            }

            for (Enemy in : enemies) {

                graphics.drawImage(in.getImage(), in.getX(), in.getY(), this);

            }

            graphics.setColor(Color.WHITE);
            graphics.drawString("Enemies: " + enemies.size(), 5, 15);

        } else if (isWon) {

            ImageIcon wonImage = new ImageIcon(getClass().getResource("/SpaceInvader/game_won.png"));

            graphics.drawImage(wonImage.getImage(), 0, 0, null);

        } else if (begin) {

            ImageIcon bg_ = new ImageIcon(getClass().getResource("/SpaceInvader/main_menu.png"));
            Image beginning = bg_.getImage();
            graphics.drawImage(beginning, 0, 0, null);

        } else {
            ImageIcon fimGame = new ImageIcon(getClass().getResource("/SpaceInvader/game_over.png"));

            graphics.drawImage(fimGame.getImage(), 0, 0, null);
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (enemies.isEmpty()) {
            playing = false;
            isWon = true;
        }

        List<Bullet> bullets1 = playerOne.getBullet();

        for (int i = 0; i < bullets1.size(); i++) {

            Bullet m = bullets1.get(i);

            if (m.isVisible()) {
                m.mixer();
            } else {
                bullets1.remove(i);
            }

        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy in = enemies.get(i);

            if (in.isVisible()) {
                in.move(enemies.size());
            } else {
                enemies.remove(i);
            }

        }

        playerOne.mixer();
        findCollision();
        repaint();
    }

    public void findCollision() {

        Rectangle p1Bounds = playerOne.getBounds();
        Rectangle enemyForm;
        Rectangle missileForm;

        for (Enemy tempEnemy : enemies) {

            enemyForm = tempEnemy.getBounds();

            if (p1Bounds.intersects(enemyForm)) {
                playerOne.setVisibility(false);
                playerOne.setDead(true);
                playing = false;
            }
        }

        List<Bullet> bulletsOne = playerOne.getBullet();

        for (Bullet tempMissile : bulletsOne) {

            missileForm = tempMissile.getBounds();

            for (Enemy tempEnemy : enemies) {

                enemyForm = tempEnemy.getBounds();

                if (missileForm.intersects(enemyForm)) {

                    tempEnemy.setVisibility(false);
                    tempMissile.setVisibility(false);

                }
            }

        }
    }

    private class KeyboardAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!playing) {
                    playing = true;
                    playerOne.setDead(false);
                    isWon = false;
                    if (begin) {
                        begin = false;
                    }

                    playerOne.setX(100);
                    playerOne.setY(100);

                    initEnemy();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                playing = false;
            }

            playerOne.getControl().keyPressed(playerOne, e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            playerOne.getControl().keyReleased(playerOne, e);

        }

    }
}
