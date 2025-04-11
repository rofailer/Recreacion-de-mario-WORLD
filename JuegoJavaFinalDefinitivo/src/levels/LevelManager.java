package levels;

import gamestates.Gamestate;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static main.Juego.TILES_SIZE;
import utilz.LoadSave;

public class LevelManager {

    private Juego juego;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;

    public LevelManager(Juego juego) {
        this.juego = juego;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("SIN NIVELES!!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        juego.getPlaying().getManagerEnemigos().loadEnemigos(newLevel);
        juego.getPlaying().getPlayer().cargarLvlDatos(newLevel.getLevelData());
        juego.getPlaying().setMaxLvlOffsetX(newLevel.getLvlOffsetX());
        juego.getPlaying().setMaxLvlOffsetY(newLevel.getLvlOffsetY());

    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[144];
        for (int j = 0; j < 16; j++) {
            for (int i = 0; i < 9; i++) {
                int index = j * 9 + i;
                levelSprite[index] = img.getSubimage(i * 16, j * 16, 16, 16);

            }
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {

        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j - yLvlOffset, TILES_SIZE, TILES_SIZE, null);

            }
        }

    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

}
