/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesnake2;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;



/**
 *
 * @author johanwendt
 */
public class MainBoard extends Application {
    
    private Pane pane = new Pane();
    private static int BLOCK_SIZE = 4;
    private static int GRIDS_PER_PLAYER_WIDTH = 4;
    private static int GRID_WIDTH = 200 * GRIDS_PER_PLAYER_WIDTH;
    private static int GRID_HEIGTH = 160 * GRIDS_PER_PLAYER_WIDTH;
    private static int PLAYER_START_LENGTH = 8 * GRIDS_PER_PLAYER_WIDTH; 
    private static int MULIPLIER_X = 1000;
    private static long GAME_SPEED = 7;
    private boolean isAlive = true;
    private boolean isRunning = false;
    //Current location is given by the upper left buildingblock in the moving direction of the player
    private int currentLocation;
    private int playerLength;
    private String currentDirection = "Right";
    private String turnDirection = "Right";
    private boolean isHorizontal = true;
    private GameGrid gameGrid;
    private Player player;
    
    public MainBoard () {
        
    }
    
    @Override
    public void start(Stage BattleStage) throws InterruptedException {
        Scene mainScene = new Scene(pane, GRID_WIDTH, GRID_HEIGTH);
        gameGrid = new GameGrid(GRID_HEIGTH, GRID_WIDTH, pane, MULIPLIER_X, Color.AQUA, BLOCK_SIZE);
        player = new Player(1000, PLAYER_START_LENGTH, GRIDS_PER_PLAYER_WIDTH, MULIPLIER_X, Color.RED, gameGrid);
        
        currentLocation = player.getCurrentLocation();
        
        BattleStage.setScene(mainScene);
        BattleStage.show();
        
        new Thread(() -> {
            try {
                while (isAlive) {
                    if(currentDirection != null && isRunning) {
                        movePlayer();
                        System.out.println(player.getLength()*player.getWidth());

                    }
                    Thread.sleep(100/GAME_SPEED);
                }
            }
            catch (InterruptedException ex) {
            }
        }).start();
        
               
        
        mainScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                
                case RIGHT: setTurnDirection("Right"); break;
                case LEFT: setTurnDirection("Left"); break;
                case UP: setTurnDirection("Up"); break;
                case DOWN: setTurnDirection("Down"); break;
                case ENTER: isRunning = true; break;
                case L: player.setLength(player.getLength() + player.getWidth()); break;
                case F: GAME_SPEED ++; break;
                //default only for testing
                default:movePlayer();
            }
        });
    }
    public void movePlayer() {
        if ((currentDirection != turnDirection) && ((currentLocation + MULIPLIER_X) % (GRIDS_PER_PLAYER_WIDTH*MULIPLIER_X) < MULIPLIER_X) && ((currentLocation + MULIPLIER_X) % GRIDS_PER_PLAYER_WIDTH ==0)) {
            currentDirection = turnDirection;
            player.turnSwap(turnDirection);
        }
        if (currentDirection.equals("Right") || currentDirection.equals("Left")) {
            isHorizontal = true;
        }
        else {
            isHorizontal = false;
        }
        switch(currentDirection) {
            case "Right": moveRight(); break;
            case "Left": moveLeft(); break;
            case "Up": moveUp(); break;
            case "Down": moveDown(); break;
        }
    }
    // Set the controlls for the player
    
    public void moveRight() {
        for(int i = 0 ; i < GRIDS_PER_PLAYER_WIDTH; i++) {
            player.movePlayer(gameGrid.getBlock(currentLocation + MULIPLIER_X + i)); 
            
        }
        currentLocation += MULIPLIER_X;
    }
        
    public void moveLeft() {       
        for(int i = 0 ; i < GRIDS_PER_PLAYER_WIDTH; i++) {
            player.movePlayer(gameGrid.getBlock(currentLocation - (GRIDS_PER_PLAYER_WIDTH * MULIPLIER_X) + i));
        }
        currentLocation -= MULIPLIER_X;
    }
    public void moveUp() {       
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1; i >= 0; i--) {
            player.movePlayer(gameGrid.getBlock(currentLocation - 1 - (i * MULIPLIER_X)));
        }
        currentLocation --;
    }
    public void moveDown() {       
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1; i >= 0; i--) {
            player.movePlayer(gameGrid.getBlock(currentLocation + GRIDS_PER_PLAYER_WIDTH - (i * MULIPLIER_X)));
        }
        currentLocation ++;
    }
    //Set methods
    public void setTurnDirection(String direction) {
        if(isHorizontal) {
            switch (direction) {
                case "Up": turnDirection = "Up"; break;    
                case "Down": turnDirection = "Down"; break;
            }
        }
        else {
            switch (direction) {
                case "Right": turnDirection = "Right"; break;
                case "Left": turnDirection = "Left"; break;
            }
        }
    }

}
