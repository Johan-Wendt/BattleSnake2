/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesnake2;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 *
 * @author johanwendt
 */
public class GameGrid {
    private static int BLOCK_SIZE;
    private static double GRID_WIDTH;
    private static double GRID_HEIGTH;
    private static int MULITIPLIER_X;
    private Color color;
    private Pane pane;
    private ArrayList<BuildingBlock> gridList = new ArrayList<>();

    
    public GameGrid(int height, int width, Pane pane, int multiplierX, Color color, int blockSize) {
        GRID_HEIGTH = height;
        GRID_WIDTH = width;
        this.pane = pane;
        this.color = color;
        MULITIPLIER_X = multiplierX;
        BLOCK_SIZE = blockSize;
        
        createGrid();
    }
    public void createGrid() {
        for(int i = 0; i < GRID_WIDTH/BLOCK_SIZE; i ++) {
            for(int j = 0; j < GRID_HEIGTH/BLOCK_SIZE; j ++) {
                BuildingBlock block = new BuildingBlock(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, (j + i * MULITIPLIER_X), Color.AQUA);
                pane.getChildren().add(block.getRectangle());
                gridList.add(block);

            }
        }
    }
    /**
     * Returns the BuildingBlock with the given ID. If the ID is not in the list
     * it returns the first block that was added to the list.
     * @param blockId The ID of the BuildingBlock.
     * @return BuildingBlock with requested ID or first block added.
     */
    public BuildingBlock getBlock(int blockId) {
        for(BuildingBlock block: gridList) {
            if(block.getBlockId() == blockId) {
                return block;
            }
        }
        return gridList.get(0);
    }
    public Color getColor() {
        return color;
    }
}
