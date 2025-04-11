package levels;

import entidades.Koopa;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static utilz.MetodosDeAyuda.GetLevelData;
import static utilz.MetodosDeAyuda.GetEnemigos;
import static utilz.MetodosDeAyuda.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Koopa> koopa;

    private int lvlTilesWide;
    private int maxTilesOffsetx;
    private int maxLvlOffsetX;

    private int lvlTilesHeight;
    private int maxTilesOffsety;
    private int maxLvlOffsetY;

    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemigos();
        calcLvlOffsets();
        calcLvlSpawn();
    }

    private void calcLvlSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    private void createEnemigos() {
        koopa = GetEnemigos(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffsetx = lvlTilesWide - Juego.TILES_IN_WIDTH;
        maxLvlOffsetX = Juego.TILES_SIZE * maxTilesOffsetx;

        lvlTilesHeight = img.getHeight();
        maxTilesOffsety = lvlTilesHeight - Juego.TILES_IN_HEIGHT;
        maxLvlOffsetY = Juego.TILES_SIZE * maxTilesOffsety;

    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffsetX() {
        return maxLvlOffsetX;
    }

    public int getLvlOffsetY() {
        return maxLvlOffsetY;
    }

    public ArrayList<Koopa> getEnemigos() {
        return koopa;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

}
