package utilz;

import main.Juego;

public class constantes {

    public static final float GRAVEDAD = 0.045f * Juego.SCALE;

    public static class ObjectConstants {

        public static final int HONGO = 0;
        public static final int ESTRELLA = 1;
        public static final int BLOCK1 = 2;
        public static final int BLOCK2 = 3;

        public static final int HONGO_VALUE = 15;
        public static final int ESTRELLA_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 128;
        public static final int CONTAINER_HEIGHT_DEFAULT = 60;
        public static final int CONTAINER_WIDTH = (int) (Juego.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Juego.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POWERUP_WIDTH_DEFAULT = 16;
        public static final int POWERUP_HEIGHT_DEFAULT = 32;
        public static final int POWERUP_WIDTH = (int) (Juego.SCALE * POWERUP_WIDTH_DEFAULT);
        public static final int POWERUP_HEIGHT = (int) (Juego.SCALE * POWERUP_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case HONGO:
                case ESTRELLA:
                    return 7;
                case BLOCK1:
                case BLOCK2:
                    return 8;
            }
            return 1;
        }
    }

    public static class EnemigoConstants {

        public static final int KOOPA = 0;

        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int HIT = 2;
        public static final int DEATH = 3;
        public static final int ATTACK = 4;
        public static final int SKID = 5;

        public static final int KOOPA_WIDTH_DEFAULT = 32;
        public static final int KOOPA_HEIGHT_DEFAULT = 32;

        public static final int KOOPA_WIDTH = (int) (KOOPA_WIDTH_DEFAULT * Juego.SCALE);
        public static final int KOOPA_HEIGHT = (int) (KOOPA_HEIGHT_DEFAULT * Juego.SCALE);

        public static final int KOOPA_DRAWOFFSET_X = (int) (20 * Juego.SCALE);
        public static final int KOOPA_DRAWOFFSET_Y = (int) (32 * Juego.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case KOOPA:
                    switch (enemy_state) {
                        case IDLE:
                        case SKID:
                            return 1;
                        case WALK:
                            return 2;
                        case HIT:
                            return 3;
                        case DEATH:
                            return 4;
                        case ATTACK:
                            return 5;

                    }
            }

            return 0;
        }

        public static int GetMaxHealth(int tipo_enemigo) {
            switch (tipo_enemigo) {
                case KOOPA:
                    return 20;
                default:
                    return 1;
            }
        }

        public static int GetDañoEnemigo(int tipo_enemigo) {
            switch (tipo_enemigo) {
                case KOOPA:
                    return 10;
                default:
                    return 0;
            }
        }
    }

    public static class Ambiente {

        public static final int MOUNTAINS_WIDTH_DEFAULT = 512;
        public static final int MOUNTAINS_HEIGHT_DEFAULT = 432;

        public static final int MOUNTAINS_WIDTH = (int) (MOUNTAINS_WIDTH_DEFAULT * Juego.SCALE);
        public static final int MOUNTAINS_HEIGHT = (int) (MOUNTAINS_HEIGHT_DEFAULT * Juego.SCALE);
    }

    public static class UI {

        public static class Botones {

            public static final int B_WIDTH_DEFAULT = 128;
            public static final int B_HEIGHT_DEFAULT = 16;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Juego.SCALE * 2);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Juego.SCALE * 2);
        }

        public static class BotonesPausa {

            public static final int SOUND_SIZE_DEFAULT = 16;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Juego.SCALE);
        }

        public static class BotonURM {

            public static final int URM_DEFAULT_SIZE = 16;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Juego.SCALE * 2);
        }

        public static class BotonVolumen {

            public static final int VOLUME_DEFAULT_WIDTH = 16;
            public static final int VOLUME_DEFAULT_HEIGHT = 16;
            public static final int SLIDER_DEFAULT_WIDTH = 112;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Juego.SCALE * 2);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Juego.SCALE * 2);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Juego.SCALE * 2);
        }

    }

    public static class Direcciones {

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        //DERECHA
        public static final int IDLE = 0;
        public static final int LOOKUP = 1;
        public static final int GODOWN = 2;
        public static final int JUMP = 3;
        public static final int RUNJUMP = 4;
        public static final int FALL = 5;
        public static final int DEATH = 6;
        public static final int ATTACK = 7;
        public static final int WALK = 8;
        public static final int RUN = 9;
        public static final int GROW = 10;
        //IZQUIERDA
        public static final int IDLE_IZQUIERDA = 12;
        public static final int LOOKUP_IZQUIERDA = 13;
        public static final int GODOWN_IZQUIERDA = 14;
        public static final int JUMP_IZQUIERDA = 15;
        public static final int RUNJUMP_IZQUIERDA = 16;
        public static final int FALL_IZQUIERDA = 17;
        public static final int ATTACK_IZQUIERDA = 19;
        public static final int WALK_IZQUIERDA = 20;
        public static final int RUN_IZQUIERDA = 21;
        public static final int GROW_IZQUIERDA = 22;
        //SPIN
        public static final int SPIN = 23;
        public static final int SPIN_IZQUIERDA = 24;

        //FRAMES DE LAS ANIMACIONES
        public static int GetSpriteAmount(int player_action) {

            switch (player_action) {
                case DEATH:
                    return 44;
                case SPIN:
                case SPIN_IZQUIERDA:
                    return 4;
                case WALK:
                case RUN:
                case GROW:
                case WALK_IZQUIERDA:
                case RUN_IZQUIERDA:
                case GROW_IZQUIERDA:
                    return 3;
                case ATTACK:
                case ATTACK_IZQUIERDA:
                    return 2;
                case IDLE:
                case LOOKUP:
                case GODOWN:
                case JUMP:
                case RUNJUMP:
                case FALL:
                case IDLE_IZQUIERDA:
                case LOOKUP_IZQUIERDA:
                case GODOWN_IZQUIERDA:
                case JUMP_IZQUIERDA:
                case RUNJUMP_IZQUIERDA:
                case FALL_IZQUIERDA:
                default:
                    return 1;
            }
        }
    }
}
