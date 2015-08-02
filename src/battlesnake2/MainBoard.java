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



/**
 *
 * @author johanwendt
 */
public class MainBoard extends Application {
    
    private Pane pane = new Pane();
    private static double GRID_WIDTH = 900;
    private static double GRID_HEIGTH = 900;
    private static int GRID_SIZE = 3;
    private static int GRIDS_PER_PLAYER_WIDTH = 3;
    private static int PLAYER_START_LENGTH = 27; 
    private static int JUMPS_PER_HORIZONTAL_MOVE = 1000;
    private static long GAME_SPEED = 7;
    private HashMap<Integer, Rectangle> gridList = new HashMap<>();
    private ArrayList<Rectangle> playerOne = new ArrayList<>();
    private boolean isAlive = true;
    private boolean isRunning = false;
    //Current location is given by the upper right grid of the player
    private int currentLocation;
    private int playerLength;
    private String currentDirection = "Right";
    private String turnDirection = "Right";
    private boolean isHorizontal = true;
    
    public MainBoard () {
        
    }
    
    @Override
    public void start(Stage BattleStage) throws InterruptedException {
        Scene mainScene = new Scene(pane, GRID_WIDTH, GRID_HEIGTH);
        makeGrid();
        makePlayer();
        
        BattleStage.setScene(mainScene);
        BattleStage.show();
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (isAlive) {
                        if(currentDirection != null && isRunning) {
                           movePlayer();

                        }
                        Thread.sleep(100/GAME_SPEED);
                    }
                }
                catch (InterruptedException ex) {
            }
            }
        }).start();
        
               
        
        mainScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                
                case RIGHT: setTurnDirection("Right"); break;
                case LEFT: setTurnDirection("Left"); break;
                case UP: setTurnDirection("Up"); break;
                case DOWN: setTurnDirection("Down"); break;
                case ENTER: isRunning = true; break;
                //default only for testing
                default:movePlayer();
            }
        });
    }
    //Set up the board and the player
    
    /**
     * Creates the grid that the player is moving around on. The grid is not neceserilly 
     * the samt size as the player, but should be a square root of the size of the player.
     */
    public void makeGrid() {
        for(int i = 0; i < GRID_WIDTH; i += GRID_SIZE) {
            for(int j = 0; j < GRID_HEIGTH; j += GRID_SIZE) {
                Rectangle rectangle = new Rectangle(GRID_SIZE, GRID_SIZE, Color.RED);
                rectangle.setX(i);
                rectangle.setY(j);
                pane.getChildren().add(rectangle);
                Integer a = (j + i * JUMPS_PER_HORIZONTAL_MOVE)/3;
                gridList.put(a, rectangle);
            }
        }
    }
    /**
     * Creates the player, or players if that feature is introduced. 
     */
    public void makePlayer() {
        int startPoint = 0;
        for(int j = startPoint; j < startPoint + JUMPS_PER_HORIZONTAL_MOVE*PLAYER_START_LENGTH; j += JUMPS_PER_HORIZONTAL_MOVE) {
            for(int i = 0; i < GRIDS_PER_PLAYER_WIDTH ; i++) {
                Rectangle startSnake = gridList.get(i + j);
                startSnake.setFill(Color.BLUE);
                playerOne.add(startSnake);
                playerLength = PLAYER_START_LENGTH;
                currentLocation = j;
                System.out.println(j);
            }
        }
    }
    
    public void movePlayer() {
        if ((currentDirection != turnDirection) && ((currentLocation + JUMPS_PER_HORIZONTAL_MOVE) % (GRIDS_PER_PLAYER_WIDTH*JUMPS_PER_HORIZONTAL_MOVE) < JUMPS_PER_HORIZONTAL_MOVE) && ((currentLocation + JUMPS_PER_HORIZONTAL_MOVE) % GRIDS_PER_PLAYER_WIDTH ==0)) {
            currentDirection = turnDirection;
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
            Rectangle toMove = gridList.get(currentLocation + JUMPS_PER_HORIZONTAL_MOVE + i);
              toMove.setFill(Color.BLUE);
              Rectangle toRemove = playerOne.get(i);
              toRemove.setFill(Color.RED);
        }
        for(int i = 0 ; i < GRIDS_PER_PLAYER_WIDTH; i++) {
            Rectangle toMove = gridList.get(currentLocation + JUMPS_PER_HORIZONTAL_MOVE + i);        
            playerOne.add(toMove);
        }
        Iterator itr = playerOne.iterator();
        while(itr.hasNext()) {
            Object rec = itr.next();
            if(playerOne.size() > playerLength*GRIDS_PER_PLAYER_WIDTH) {
                    itr.remove();
            }
        }
            currentLocation += JUMPS_PER_HORIZONTAL_MOVE;
    }
    public void moveLeft() {       
        for(int i = 0 ; i < GRIDS_PER_PLAYER_WIDTH; i++) {
            Rectangle toMove = gridList.get(currentLocation -(GRIDS_PER_PLAYER_WIDTH * JUMPS_PER_HORIZONTAL_MOVE) + i);
            toMove.setFill(Color.BLUE);
            Rectangle toRemove = playerOne.get(i);
            toRemove.setFill(Color.RED);
        }
        for(int i = 0 ; i < GRIDS_PER_PLAYER_WIDTH; i++) {
            Rectangle toMove = gridList.get(currentLocation -(GRIDS_PER_PLAYER_WIDTH * JUMPS_PER_HORIZONTAL_MOVE) + i);        
            playerOne.add(toMove);
        }
        Iterator itr = playerOne.iterator();
        while(itr.hasNext()) {
            Object rec = itr.next();
            if(playerOne.size() > playerLength*GRIDS_PER_PLAYER_WIDTH) {
                    itr.remove();
            }
        }
            currentLocation -= JUMPS_PER_HORIZONTAL_MOVE;
    }
    public void moveUp() {       
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1; i >= 0; i--) {
            Rectangle toMove = gridList.get(currentLocation - 1 - (i * JUMPS_PER_HORIZONTAL_MOVE));
            toMove.setFill(Color.BLUE);
            Rectangle toRemove = playerOne.get(i);
            toRemove.setFill(Color.RED);
        }
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1 ; i >= 0; i--) {
            Rectangle toMove = gridList.get(currentLocation - 1 - (i * JUMPS_PER_HORIZONTAL_MOVE));        
            playerOne.add(toMove);
        }
        Iterator itr = playerOne.iterator();
        while(itr.hasNext()) {
            Object rec = itr.next();
            if(playerOne.size() > playerLength*GRIDS_PER_PLAYER_WIDTH) {
                    itr.remove();
            }
        }
            currentLocation --;
    }
    public void moveDown() {       
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1; i >= 0; i--) {
            Rectangle toMove = gridList.get(currentLocation + GRIDS_PER_PLAYER_WIDTH - (i * JUMPS_PER_HORIZONTAL_MOVE));
            toMove.setFill(Color.BLUE);
            Rectangle toRemove = playerOne.get(i);
            toRemove.setFill(Color.RED);
        }
        for(int i = GRIDS_PER_PLAYER_WIDTH - 1 ; i >= 0; i--) {
            Rectangle toMove = gridList.get(currentLocation + GRIDS_PER_PLAYER_WIDTH - (i * JUMPS_PER_HORIZONTAL_MOVE));        
            playerOne.add(toMove);
        }
        Iterator itr = playerOne.iterator();
        while(itr.hasNext()) {
            Object rec = itr.next();
            if(playerOne.size() > playerLength*GRIDS_PER_PLAYER_WIDTH) {
                    itr.remove();
            }
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
