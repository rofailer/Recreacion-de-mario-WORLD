package gamestates;

import audio.AudioPlayer;
import entidades.ManagerEnemigos;
import entidades.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import levels.LevelManager;
import main.Juego;
import objects.ObjectManager;
import ui.*;
import utilz.LoadSave;
import static utilz.constantes.Ambiente.*;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private ManagerEnemigos managerEnemigos;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletadoOverlay levelCompletadoOverlay;
    private boolean paused = false;

    private int xLvlOffset, yLvlOffset;
    //BORDES MAPA ARRIBA Y ABAJO
    private int topBorder = (int) (0.60 * Juego.GAME_HEIGHT);
    private int bottomBorder = (int) (0.60 * Juego.GAME_HEIGHT);
    //BORDES MAPA IZQUIERDA Y DERECHA
    private int leftBorder = (int) (0.48 * Juego.GAME_WIDTH);
    private int rightBorder = (int) (0.48 * Juego.GAME_WIDTH);
    //CALCULAR OFFSET MAPA X
    private int maxLvlOffsetX;
    //CALCULAR OFFSET MAPA Y
    private int maxLvlOffsetY;

    private BufferedImage backgroundImg, mountains;

    private boolean gameOver;
    private boolean lvlCompletado = false;
    private boolean playerDying = false;

    public Playing(Juego juego) {
        super(juego);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        mountains = LoadSave.GetSpriteAtlas(LoadSave.MOUNTAINS);

        calcLvlOffsetX();
        calcLvlOffsetY();
        loadStartLevel();
    }

    public void loadNextLevel() {
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    private void loadStartLevel() {
        managerEnemigos.loadEnemigos(levelManager.getCurrentLevel());
    }

    private void calcLvlOffsetX() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffsetX();
    }

    private void calcLvlOffsetY() {
        maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffsetY();
    }

    private void initClasses() {
        levelManager = new LevelManager(juego);
        managerEnemigos = new ManagerEnemigos(this);
        objectManager = new ObjectManager(this);

        player = new Player(10, 2951, (int) (32 * Juego.SCALE), (int) (32 * Juego.SCALE), this);
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        player.cargarLvlDatos(levelManager.getCurrentLevel().getLevelData());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletadoOverlay = new LevelCompletadoOverlay(this);
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompletado) {
            levelCompletadoOverlay.update();
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelManager.update();
            //objectManager.update();
            player.update();
            managerEnemigos.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
            checkCloseToTop();
        }
    }

    @Override
    public void draw(Graphics g
    ) {
        g.drawImage(backgroundImg, 0, 0, Juego.GAME_WIDTH, Juego.GAME_HEIGHT, null);

        drawMountains(g);

        levelManager.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);
        managerEnemigos.draw(g, xLvlOffset, yLvlOffset);
        //objectManager.draw(g, xLvlOffset, yLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Juego.GAME_WIDTH, Juego.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompletado) {
            levelCompletadoOverlay.draw(g);
        }

    }
    //MOVIMIENTO DE MAPA X

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    //MOVIMIENTO DE MAPA Y
    private void checkCloseToTop() {
        int playerY = (int) player.getHitbox().y;
        int diffY = playerY - yLvlOffset;

        if (diffY > topBorder) {
            yLvlOffset += diffY - topBorder;
        } else if (diffY < bottomBorder) {
            yLvlOffset += diffY - bottomBorder;
        }

        if (yLvlOffset > maxLvlOffsetY) {
            yLvlOffset = maxLvlOffsetY;
        } else if (yLvlOffset < 0) {
            yLvlOffset = 0;
        }

    }

    private void drawMountains(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(mountains, i * (int) (MOUNTAINS_WIDTH * Juego.SCALE) - (int) (xLvlOffset * 0.3), (int) (-yLvlOffset * 0.3) - 130, (int) (MOUNTAINS_WIDTH * Juego.SCALE), (int) (MOUNTAINS_HEIGHT * Juego.SCALE), null);
        }
    }

    public void resetAll() {
        //resetea todo
        gameOver = false;
        paused = false;
        lvlCompletado = false;
        playerDying = false;
        player.resetTodo();
        managerEnemigos.resetTodosEnemigos();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        managerEnemigos.hitEnemigo(attackBox);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (gameOver) {
            gameOverOverlay.keyPressed(ke);
        } else {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_SHIFT:
                    player.setRun(true);
                    break;
                case KeyEvent.VK_J:
                    player.setAtaque(true);
                    break;
                case KeyEvent.VK_K:
                    player.setSpin(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    juego.getAudioPlayer().playEffect(AudioPlayer.PAUSE);
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (!gameOver) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
                case KeyEvent.VK_SHIFT:
                    player.setRun(false);
                    break;
                case KeyEvent.VK_J:
                    player.setAtaque(false);
                    break;
                case KeyEvent.VK_K:
                    player.setSpin(false);
                    break;
            }
        }
    }

    public void mouseDragged(MouseEvent me) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseDragged(me);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        /*if (me.getButton() == MouseEvent.BUTTON1) {
            player.setAtaque(true);
        }*/
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed(me);
            } else if (lvlCompletado) {
                levelCompletadoOverlay.mousePressed(me);
            }
        } else {
            gameOverOverlay.mousePressed(me);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(me);
            } else if (lvlCompletado) {
                levelCompletadoOverlay.mouseReleased(me);
            }
        } else {
            gameOverOverlay.mouseReleased(me);
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(me);
            } else if (lvlCompletado) {
                levelCompletadoOverlay.mouseMoved(me);
            }
        } else {
            gameOverOverlay.mouseMoved(me);
        }
    }

    public void setLevelCompletado(boolean levelCompletado) {
        this.lvlCompletado = levelCompletado;
        if (levelCompletado) {
            juego.getAudioPlayer().lvlCompleted();
        }
    }

    public void setMaxLvlOffsetX(int lvlOffsetX) {
        this.maxLvlOffsetX = lvlOffsetX;
    }

    public void setMaxLvlOffsetY(int lvlOffsetY) {
        this.maxLvlOffsetX = lvlOffsetY;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public ManagerEnemigos getManagerEnemigos() {
        return managerEnemigos;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

}
