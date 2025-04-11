package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/MarioMovement-Sheet.png";
    public static final String LEVEL_ATLAS = "res/Ground-Sheet.png";
    public static final String MENU_BUTTONS = "res/buttonMario_atlas1.png";
    public static final String MENU_BACKGROUND = "res/BackgroundMario_Menu.png";
    public static final String MENU_BACKGROUND_IMG = "res/BackgroundImgBack.gif";
    public static final String PAUSE_BACKGROUND = "res/pause_menu.png";
    public static final String SOUND_BUTTONS = "res/sound_button.png";
    public static final String URM_BUTTONS = "res/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "res/volume_buttons.png";
    public static final String PLAYING_BG_IMG = "res/MarioLevel_Background.png";
    public static final String MOUNTAINS = "res/MarioLevel_Background_Mountains.png";
    public static final String ENEMY_KOOPA = "res/Custom_koopaSprite-sheet.png";
    public static final String HUD = "res/MarioHUD.png";
    public static final String COMPLETED_IMG = "res/completed_sprite.png";
    public static final String BLOCKS_SPRITES = "res/powerBlock_sprite.png";
    public static final String POWERUPS = "res/powerUps_sprites.png";
    public static final String DEATH_SCREEN = "res/gameOver_sprite.png";
    public static final String OPTIONS_MENU = "res/options_menu.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/res/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals("" + (i + 1 + ".png"))) {
                    filesSorted[i] = files[j];
                }

            }
        }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }

}
