/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesnake2;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;

/**
 *
 * @author johanwendt
 */
public class Player {
    private int length;
    private int width;
    //The current location is the building block in the outer right corner of the snakes head
    private int currentLocation;
    private Color color;
    private GameGrid gameGrid;
    private Stack<BuildingBlock> body = new Stack<>();
    
    public Player(int startPoint, int length, int width, int mulitplierX, Color color, GameGrid gameGrid) {
        this.length = length;
        this.width = width;
        this.color = color;
        this.gameGrid = gameGrid;
        createPlayer(startPoint, length, width, mulitplierX, color);
        currentLocation = startPoint + length * mulitplierX -mulitplierX;   
    }
    public void createPlayer(int startPoint, int length, int width, int mulitplierX, Color color) {
        for(int j = startPoint; j < startPoint + mulitplierX*length; j += mulitplierX) {
            for(int i = 0; i < width ; i++) {
                BuildingBlock startSnake = gameGrid.getBlock(i + j);
                startSnake.getRectangle().setFill(color);
                body.add(0, startSnake);
            }
        }
    }
    //mutators
    /**
     * NB This method does not yet support makingthe snake longer.
     * @param block 
     */
    public void movePlayer(BuildingBlock block) {
        block.getRectangle().setFill(color);
        body.add(0, block);
        //not correct!!
        currentLocation = block.getBlockId();
        if(body.size() > length * width) {
            body.pop().getRectangle().setFill(gameGrid.getColor());  
        }
    }
    
    //Getters
    public BuildingBlock getBlock(int blockId) {
        for(BuildingBlock block: body) {
            if(block.getBlockId() == blockId) {
                return block;
            }
        }
        return body.get(0);
    }
    public int getCurrentLocation() {
        return currentLocation;
    }
    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
    
    //Setters
    public void setLength(int newLength) {
        length = newLength;
    }

    
    
    //Mutators
    public void turnSwap(String turnDirection) {
        Stack<BuildingBlock> turnList = new Stack<>();
        if(turnDirection.equals("Right") || turnDirection.equals("Down")) {
            for(int i = 0; i < width; i++) {
                for(int j = 0; j < width * width; j += width) {
                    turnList.add(body.elementAt(i + j));
                }
            }
        }
        else {
            for(int i = width - 1; i >= 0; i--) {
                for(int j = 0; j < width * width; j += width) {
                    turnList.add(body.elementAt(i + j));
                    for(BuildingBlock block: turnList) {
                    }
                }
            }
        }
        for(int i = 0; i < width * width; i++) {
            body.remove(0);            
        }
        for(int i = 0; i < width * width; i++) {
            body.add(0, turnList.pop());
        }
    }
}
