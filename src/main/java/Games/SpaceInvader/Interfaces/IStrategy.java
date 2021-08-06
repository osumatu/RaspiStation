package Games.SpaceInvader.Interfaces;

import Games.SpaceInvader.Models.Player;

import java.awt.event.KeyEvent;

public interface IStrategy {

    void keyPressed(Player player, KeyEvent key);

    void keyReleased(Player player, KeyEvent key);

}
