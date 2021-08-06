package Games.SpaceInvader.Controllers;

import Games.SpaceInvader.Interfaces.IStrategy;
import Games.SpaceInvader.Models.Player;

import java.awt.event.KeyEvent;

import static Games.SpaceInvader.Controllers.Touch.*;

public enum Controller implements IStrategy {

    PLAYER_1 {
        @Override
        public void keyPressed(Player player, KeyEvent key) {

            int keyCode = key.getKeyCode();
            if (!player.getDead()) {
                switch (keyCode) {
                    case PLAYER_ONE_FIRE -> player.fire();
                    case PLAYER_ONE_MOVE_UP -> player.setDy(-1);
                    case PLAYER_ONE_MOVE_DOWN -> player.setDy(1);
                    case PLAYER_ONE_MOVE_LEFT -> player.setDx(-1);
                    case PLAYER_ONE_MOVE_RIGHT -> player.setDx(1);
                }
            }
        }

        @Override
        public void keyReleased(Player player, KeyEvent key) {
            int keyCode = key.getKeyCode();

            if (!player.getDead()) {
                switch (keyCode) {
                    case PLAYER_ONE_MOVE_UP, PLAYER_ONE_MOVE_DOWN -> player.setDy(0);
                    case PLAYER_ONE_MOVE_LEFT, PLAYER_ONE_MOVE_RIGHT -> player.setDx(0);
                }
            }
        }
    }
}
