package entidades;

import audio.AudioPlayer;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import main.Juego;
import utilz.LoadSave;
import static utilz.constantes.PlayerConstants.*;
import static utilz.MetodosDeAyuda.*;
import static utilz.constantes.GRAVEDAD;

public class Player extends Entidad {

    private Playing playing;
    private BufferedImage[][] animaciones;
    private int aniVelocidad = 15;
    private boolean moving = false, atacando = false, derecha = true, running = false, spin = false, ataqueSpin = false;
    private boolean left, up, right, down, jump, run;
    //ESTADISTICAS PLAYER
    private float velocidadPlayer, velocidadPlayerCorriendo, velocidadPlayerMax = 1.35f * Juego.SCALE, velocidadPlayerCorriendoMax = 1.45f * Juego.SCALE;
    private float aumentoVelocidad = 0.008f * Juego.SCALE, velocidadReinicio = 0.008f * Juego.SCALE;
    //OFFSET 
    private int[][] lvlData;
    private float xDrawOffSet = 22 * Juego.SCALE;
    private float yDrawOffSet = 35 * Juego.SCALE;
    //GRAVEDAD Y SALTO
    private float velocidadSalto = -3.50f * Juego.SCALE;
    private float velocidadSaltoSpin = -2.75f * Juego.SCALE;
    private float velocidadCaidaColision = 0.5f * Juego.SCALE;
    //HUD
    private BufferedImage hudImg;
    private int hudWidth = (int) (256 * Juego.SCALE * 2);
    private int hudHeight = (int) (224 * Juego.SCALE * 2);
    private int hudX = 330;
    private int hudY = 1;
    //ATAQUE
    private boolean attackChecked;
    protected Rectangle2D.Float attackBoxJump;
    //FRICCION
    private float FRICCION, FRICCIONDEFAULT = 0.02f * Juego.SCALE;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.vidaMax = 30;
        this.vidaActual = vidaMax;
        cargarAnimations();
        initHitbox(x, y, (int) (20 * Juego.SCALE), (int) (28 * Juego.SCALE));
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (10 * Juego.SCALE), (int) (20 * Juego.SCALE));
        attackBoxJump = new Rectangle2D.Float(x, y, (int) (20 * Juego.SCALE), (int) (10 * Juego.SCALE));
        resetAttackBox();
    }

    public void update() {
        if (vidaActual <= 0) {
            if (state != DEATH) {
                state = DEATH;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getJuego().getAudioPlayer().stopSong();
                playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.DEATH);
            } else if (aniIndex == GetSpriteAmount(DEATH) - 1 && aniTick >= aniVelocidad - 1) {
                playing.setGameOver(true);
                playing.getJuego().getAudioPlayer().stopSong();
                playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateTicksAnimacion();
            }
            return;
        }

        updateAttackBox();
        updateAttackBoxJump();

        updatePos();
        //ATAQUE PATADA
        if (atacando) {
            checkAttack();
        }
        //ATAQUE CON SALTO
        if (velocidadEnAire > 0) {
            checkAttackJump();
        }
        //FRICCION
        //System.out.println(velocidadPlayer);
        //System.out.println(velocidadPlayerCorriendo);
        //System.out.println(FRICCION);
        //Friccion();

        updateTicksAnimacion();
        setAnimaciones();
        System.out.println("Vida: " + vidaActual);

    }

    private void Friccion() {
        if (!jump && !enElAire && !left && !right && velocidadPlayerCorriendo >= velocidadPlayerCorriendoMax) {
            if (velocidadPlayerCorriendo > 0 && !derecha) {
                hitbox.x -= FRICCION;
                FRICCION += 0.5f * Juego.SCALE;
                if (FRICCION >= 2f) {
                    FRICCION = FRICCIONDEFAULT;
                }
            }
            if (velocidadPlayerCorriendo > 0 && derecha) {
                hitbox.x += FRICCION;
                FRICCION += 0.5f * Juego.SCALE;
                if (FRICCION >= 2f) {
                    FRICCION = FRICCIONDEFAULT;
                }
            }
        }
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.KICK);
    }

    private void checkAttackJump() {
        if (attackChecked) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBoxJump);
    }

    private void updateAttackBox() {
        if (right && left) {
            if (derecha) {
                attackBox.x = hitbox.x + hitbox.width + (int) (1 * Juego.SCALE);
            } else {
                attackBox.x = hitbox.x - hitbox.width + (int) (8 * Juego.SCALE);
            }
        } else if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * Juego.SCALE);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width + (int) (8 * Juego.SCALE);
        }
        attackBox.y = hitbox.y + (10 * Juego.SCALE);
    }

    private void updateAttackBoxJump() {
        if (right && left) {
            if (derecha) {
                attackBoxJump.x = hitbox.x + hitbox.width - (int) (20 * Juego.SCALE);
            } else {
                attackBoxJump.x = hitbox.x - hitbox.width + (int) (20 * Juego.SCALE);
            }
        } else if (right) {
            attackBoxJump.x = hitbox.x + hitbox.width - (int) (20 * Juego.SCALE);
        } else if (left) {
            attackBoxJump.x = hitbox.x - hitbox.width + (int) (20 * Juego.SCALE);
        }
        attackBoxJump.y = hitbox.y + (20 * Juego.SCALE);
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.drawImage(animaciones[state][aniIndex], (int) (hitbox.x - xDrawOffSet) - xLvlOffset, (int) (hitbox.y - yDrawOffSet) - yLvlOffset, (int) (width * Juego.SCALE), (int) (height * Juego.SCALE), null);
        //drawHitbox(g, xLvlOffset, yLvlOffset);
        drawAttackBox(g, xLvlOffset, yLvlOffset);
        //drawAttackBoxJump(g, xLvlOffset, yLvlOffset);
        drawUI(g);
    }

    //HITBOX PATADA
    private void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }

    //HITBOX PATAS
    private void drawAttackBoxJump(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.BLUE);
        g.drawRect((int) attackBoxJump.x - xLvlOffset, (int) attackBoxJump.y - yLvlOffset, (int) attackBoxJump.width, (int) attackBoxJump.height);
    }

    //HUD
    private void drawUI(Graphics g) {
        g.drawImage(hudImg, hudX, hudY, hudWidth, hudHeight, null);
    }

    public void updateTicksAnimacion() {
        aniTick++;
        if (aniTick >= aniVelocidad) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                atacando = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimaciones() {
        int startAni = state;
        //MOVIMIENTO DERECHA-IZQUIERDA
        if (moving) {
            if (derecha) {
                state = WALK;
            } else {
                state = WALK_IZQUIERDA;
            }
        }
        //CORRIENDO
        if (moving && run && (velocidadPlayerCorriendo >= velocidadPlayerCorriendoMax)) {
            if (derecha) {
                state = RUN;
            } else {
                state = RUN_IZQUIERDA;
            }
        }
        if (velocidadPlayerCorriendo >= velocidadPlayerCorriendoMax / 2) {
            aniTick++;
        }
        //IDLE
        if (!moving) {
            if (derecha) {
                state = IDLE;
            } else {
                state = IDLE_IZQUIERDA;
            }
        }
        //SALTANDO-CAYENDO
        if (enElAire && !ataqueSpin) {
            if (velocidadEnAire < 0) {
                if (derecha) {
                    state = JUMP;
                } else {
                    state = JUMP_IZQUIERDA;
                }
            } else {
                if (derecha) {
                    state = FALL;
                } else {
                    state = FALL_IZQUIERDA;
                }
            }
        }
        //SALTANDO-CAYENDO CORRIENDO
        if (enElAire && run && velocidadPlayerCorriendo >= velocidadPlayerCorriendoMax) {
            if (velocidadEnAire < 0) {
                if (derecha) {
                    state = RUNJUMP;
                } else {
                    state = RUNJUMP_IZQUIERDA;
                }
            }
        }
        //MIRANDO ARRIBA
        if (up && !moving) {
            if (derecha) {
                state = LOOKUP;
            } else {
                state = LOOKUP_IZQUIERDA;
            }
        }
        //AGACHARSE
        if (down & !moving) {
            if (derecha) {
                state = GODOWN;
            } else {
                state = GODOWN_IZQUIERDA;
            }
        }
        //ATAQUE
        if (atacando) {
            if (derecha) {
                state = ATTACK;
                if (startAni != ATTACK) {
                    aniIndex = 0;
                    aniTick = 1;
                    return;
                }
            } else {
                state = ATTACK_IZQUIERDA;
                if (startAni != ATTACK_IZQUIERDA) {
                    aniIndex = 0;
                    aniTick = 1;
                    return;
                }
            }
        }
        //ATAQUE SPIN
        if (ataqueSpin) {
            if (derecha) {
                state = SPIN;
            } else {
                state = SPIN_IZQUIERDA;
            }
        }
        if (startAni != state) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;
        //SALTANDO
        if (jump) {
            jump();
        }
        //ATAQUE SPIN
        if (spin) {
            spin();
        }
        //IDLE
        if (!enElAire) {
            if ((!left && !right) || (left && right)) {
                if (velocidadPlayer > 0) {
                    velocidadPlayer -= velocidadReinicio;
                    velocidadPlayer = Math.max(0, velocidadPlayer);
                }
                return;
            }
        }
        float xVelocidad = 0;
        //CAMINANDO
        if (left) {
            derecha = false;
            xVelocidad -= velocidadPlayer;
            velocidadPlayer += aumentoVelocidad;
            velocidadPlayer = Math.min(velocidadPlayer, velocidadPlayerMax); // Limita la velocidad máxima
        } else if (right) {
            derecha = true;
            xVelocidad += velocidadPlayer;
            velocidadPlayer += aumentoVelocidad;
            velocidadPlayer = Math.min(velocidadPlayer, velocidadPlayerMax); // Limita la velocidad máxima
        }
        //CORRIENDO
        if (run) {
            if (left) {
                derecha = false;
                xVelocidad -= velocidadPlayerCorriendo;
                velocidadPlayerCorriendo += aumentoVelocidad;
                velocidadPlayerCorriendo = Math.min(velocidadPlayerCorriendo, velocidadPlayerCorriendoMax); // Limita la velocidad máxima
            }
            if (right) {
                derecha = true;
                xVelocidad += velocidadPlayerCorriendo;
                velocidadPlayerCorriendo += aumentoVelocidad;
                velocidadPlayerCorriendo = Math.min(velocidadPlayerCorriendo, velocidadPlayerCorriendoMax); // Limita la velocidad máxima
            }
        } else {
            if (velocidadPlayerCorriendo > 0) {
                velocidadPlayerCorriendo -= velocidadReinicio;
                velocidadPlayerCorriendo = Math.max(0, velocidadPlayerCorriendo);
            }
        }
        //GRAVEDAD
        if (!enElAire) {
            if (!EntidadEnElSuelo(hitbox, lvlData)) {
                enElAire = true;
            }
        }
        if (enElAire) {
            if (puedeMoverseAca(hitbox.x, hitbox.y + velocidadEnAire, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += velocidadEnAire;
                velocidadEnAire += GRAVEDAD;
                updateXPos(xVelocidad);
            } else {
                ataqueSpin = false;
                hitbox.y = GetEntidadYPosUnderRoofOrAboveFloor(hitbox, velocidadEnAire);
                if (velocidadEnAire > 0) {
                    resetEnAire();
                } else {
                    velocidadEnAire = velocidadCaidaColision;
                }
                updateXPos(xVelocidad);
            }
        } else {
            updateXPos(xVelocidad);
        }
        moving = true;
    }

    //SALTO
    private void jump() {
        if (enElAire) {
            return;
        }
        playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        enElAire = true;
        velocidadEnAire = velocidadSalto;
    }

    private void resetEnAire() {
        enElAire = false;
        velocidadEnAire = 0;
    }

    //SPIN
    private void spin() {
        if (enElAire) {
            return;
        }
        playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.SPINJUMP);
        ataqueSpin = true;
        enElAire = true;
        velocidadEnAire = velocidadSaltoSpin;
    }

    private void updateXPos(float xVelocidad) {
        if (puedeMoverseAca(hitbox.x + xVelocidad, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xVelocidad;
        } else {
            hitbox.x = GetEntidadXPosNextToWall(hitbox, xVelocidad);
        }
    }

    public void cambiarVida(int value) {
        vidaActual += value;

        if (vidaActual <= 0) {
            //gameOver();
            System.out.println("Moriste!!");
        } else if (vidaActual >= vidaMax) {
            vidaActual = vidaMax;
        }
    }

    private void cargarAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animaciones = new BufferedImage[25][44];
        for (int j = 0; j < animaciones.length; j++) {
            for (int i = 0; i < animaciones[j].length; i++) {
                animaciones[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }

        hudImg = LoadSave.GetSpriteAtlas(LoadSave.HUD);
    }

    public void cargarLvlDatos(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!EntidadEnElSuelo(hitbox, lvlData)) {
            enElAire = true;
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAtaque(boolean ataque) {
        this.atacando = ataque;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void setSpin(boolean spin) {
        this.spin = spin;
    }

    public void resetTodo() {
        resetDirBooleans();
        enElAire = false;
        atacando = false;
        moving = false;
        derecha = true;
        velocidadEnAire = 0f;
        state = IDLE;
        vidaActual = vidaMax;

        hitbox.x = x;
        hitbox.y = y;
        resetAttackBox();

        if (!EntidadEnElSuelo(hitbox, lvlData)) {
            enElAire = true;
        }
    }

    private void resetAttackBox() {
        //ATTACKBOX
        if (derecha) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * Juego.SCALE);
        } else {
            attackBox.x = hitbox.x - hitbox.width + (int) (8 * Juego.SCALE);
        }
        //ATTACKBOXJUMP
        if (derecha) {
            attackBoxJump.x = hitbox.x + hitbox.width - (int) (20 * Juego.SCALE);
        } else {
            attackBoxJump.x = hitbox.x - hitbox.width + (int) (20 * Juego.SCALE);
        }
    }
}
