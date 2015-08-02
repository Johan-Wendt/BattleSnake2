/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesnake2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johanwendt
 */
public class Mover implements Runnable {
    private MainBoard mainBoard;
    private boolean alive;
    
    public Mover (MainBoard mainBoard) {
        this.mainBoard = mainBoard;
        alive = true;
    }
    @Override
    public void run() {
        mainBoard.moveRight();
    }
}
