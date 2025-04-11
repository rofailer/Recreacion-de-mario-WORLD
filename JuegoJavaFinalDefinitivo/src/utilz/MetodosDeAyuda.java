package utilz;

import entidades.Koopa;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Juego;
import static utilz.LoadSave.GetSpriteAtlas;
import static utilz.constantes.EnemigoConstants.KOOPA;

public class MetodosDeAyuda {

    //ESTABLECE LA COLISION CON EL SUELO
    public static boolean puedeMoverseAca(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Juego.TILES_SIZE;
        int maxHeight = lvlData[0].length * Juego.TILES_SIZE;

        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= maxHeight) {
            return true;
        }

        float xIndex = x / Juego.TILES_SIZE;
        float yIndex = y / Juego.TILES_SIZE;

        return TileSolida((int) xIndex, (int) yIndex, lvlData);

    }

    public static boolean TileSolida(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        //TILES DE FONDO
        if (value == 6 || value == 7 || value == 8 || value == 9 || value == 11 || value == 13 || value == 16 || value == 17 || value == 18 || value == 20 || value == 21 || value == 25 || value == 26) {
            return false;
        }
        //TILES SOLIDAS
        if (value >= 144 || value < 0 || value != 5) {
            return true;
        } else {
            return false;
        }
    }

    public static float GetEntidadXPosNextToWall(Rectangle2D.Float hitbox, float xVelocidad) {
        int currentTile = (int) (hitbox.x / Juego.TILES_SIZE);
        if (xVelocidad > 0) {
            // Derecha
            int tileXPos = currentTile * Juego.TILES_SIZE;
            int xOffset = (int) (Juego.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            // Izquierda
            return currentTile * Juego.TILES_SIZE;
        }
    }

    public static float GetEntidadYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float velocidadEnAire) {
        int currentTile = (int) (hitbox.y / Juego.TILES_SIZE);
        if (velocidadEnAire > 0) {
            // Cayendo/TocandoSuelo
            int tileYPos = currentTile * Juego.TILES_SIZE;
            int yOffset = (int) (Juego.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Saltando/TocandoTecho
            return currentTile * Juego.TILES_SIZE;
        }
    }

    public static float GetEntidadYPos(Rectangle2D.Float hitbox, float velocidadEnAire) {
        int currentTile = (int) (hitbox.y / Juego.TILES_SIZE);
        if (velocidadEnAire > 0) {
            // Cayendo/TocandoSuelo
            int tileYPos = currentTile * Juego.TILES_SIZE;
            int yOffset = (int) (Juego.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        }
        return currentTile;
    }

    public static boolean EntidadEnElSuelo(Rectangle2D.Float hitbox, int[][] lvlData) {
        //VERIFICA LOS PIXELES DE ABAJODERECHA Y ABAJOIZQUIERDA
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;

            }
        }
        return true;
    }

    public static boolean EnElSuelo(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    public static boolean TilesCaminables(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (TileSolida(xStart + i, y, lvlData)) {
                return false;
            }
            if (!TileSolida(xStart + i, y + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean viendoPlayer(int[][] lvlData, Rectangle2D.Float primerHitbox, Rectangle2D.Float segundaHitbox, int yTile) {

        int primeraXTile = (int) (primerHitbox.x / Juego.TILES_SIZE);
        int segundaXTile = (int) (segundaHitbox.x / Juego.TILES_SIZE);

        if (primeraXTile > segundaXTile) {
            return TilesCaminables(segundaXTile, primeraXTile, yTile, lvlData);

        } else {
            return TilesCaminables(primeraXTile, segundaXTile, yTile, lvlData);
        }
    }

    public static int[][] GetLevelData(BufferedImage img) {
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 144) {
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    public static ArrayList<Koopa> GetEnemigos(BufferedImage img) {
        ArrayList<Koopa> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == KOOPA) {
                    list.add(new Koopa(i * Juego.TILES_SIZE, j * Juego.TILES_SIZE));
                }
            }
        }
        return list;

    }

    public static Point GetPlayerSpawn(BufferedImage img) {

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100) {
                    return new Point(i * Juego.TILES_SIZE, j * Juego.TILES_SIZE);
                }
            }
        }
        return new Point(1 * Juego.TILES_SIZE, 1 * Juego.TILES_SIZE);

    }

}
