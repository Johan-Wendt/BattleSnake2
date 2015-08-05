/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesnake2;

/**
 *
 * @author johanwendt
 */
public class TurnPoint {
    private int turnId;
    private int turnLocation;
    
    public TurnPoint(int turnLocation, int multiplierX) {
        this.turnLocation = turnLocation;
        turnId = 9 * (turnLocation - multiplierX + 1);       
    }
    //Getters
    public int getTurnId() {
    return turnId;
    }
    public int getTurnLocation() {
        return turnLocation;
    }
}
