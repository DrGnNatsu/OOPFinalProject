package Utilization;

import Game.Game;

public class ConstantVariables {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static final float GRAVITY = 0.005f * Game.PLAYER_SCALE;
    public static final int ANIMATION_SPEED = 28;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Projectiles {
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
        public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
        public static final int CANNON_BALL_WIDTH = CANNON_BALL_DEFAULT_WIDTH * 2;
        public static final int CANNON_BALL_HEIGHT = CANNON_BALL_DEFAULT_HEIGHT * 2;
        public static final float CANNON_BALL_SPEED = 1f;
        public static final int CANNON_BALL_DAMAGE = 15;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class ObjectConstants{
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;
        //Width and height of container
        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = CONTAINER_WIDTH_DEFAULT * 5 / 2;
        public static final int CONTAINER_HEIGHT = CONTAINER_HEIGHT_DEFAULT * 5 / 2;
        //Width and height of potion
        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = POTION_WIDTH_DEFAULT * 5 / 2;
        public static final int POTION_HEIGHT = POTION_HEIGHT_DEFAULT * 5 / 2;
        //Width and height of spike
        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * 0.8);
        public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * 0.8);
        //Width and height of cannon
        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = CANNON_WIDTH_DEFAULT * 5 / 2;
        public static final int CANNON_HEIGHT = CANNON_HEIGHT_DEFAULT * 5 / 2;
        //Get sprite amount
        public static int getSpriteAmount(int objectType) {
            return switch (objectType) {
                case RED_POTION, BLUE_POTION, CANNON_LEFT, CANNON_RIGHT -> 7;
                case BARREL, BOX ->8;
                case SPIKE -> 1;
                default -> 1;
            };
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class EnemyConstant {
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        public static final int CRABBY = 0;
        //No of Animation
        public static final int IDLE_C = 0;
        public static final int RUNNING_C = 1;
        public static final int ATTACK_C = 2;
        public static final int HIT_C = 3;
        public static final int DEATH_C = 4;
        //Size of crab enemy
        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = CRABBY_WIDTH_DEFAULT * 5 / 2;
        public static final int CRABBY_HEIGHT = CRABBY_HEIGHT_DEFAULT * 5 / 2;
        //Offset of the hitbox
        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.TILE_SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9* Game.TILE_SCALE);
        //Get amount of sprite
        public static int getSpriteAmountEnemy(int enemyType, int enemyState) {
            return switch (enemyType) {
                case CRABBY -> switch (enemyState) {
                    case IDLE_C -> 9;
                    case RUNNING_C -> 6;
                    case ATTACK_C -> 7;
                    case HIT_C -> 4;
                    case DEATH_C -> 5;
                    default -> 0;
                };
                default -> 0;
            };

        }

        public static int getMaxHealth (int enemyType){
            return switch (enemyType) {
                case CRABBY -> 100;
                default -> 1;
            };
        }

        public static int getEnemyDamage(int enemyType){
            return switch (enemyType) {
                case CRABBY -> 15;
                default -> 0;
            };
        }

    }
    public static class PlayerConstant {
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Direction
        public static final int LEFT = 0;
        public static final int RIGHT = 1;

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Animation
        public static final int PLAYER_IDLE_ANIMATION = 6;
        public static final int PLAYER_ATTACK_ANIMATION = 8;
        public static final int PLAYER_RUN_ANIMATION = 8;
        public static final int PLAYER_JUMP_ANIMATION = 8;
        public static final int PLAYER_LAND_ANIMATION = 8;
        public static final int PLAYER_DAMAGED_ANIMATION = 4;
        public static final int PLAYER_DEATH_ANIMATION = 8; // 12
        public static final int PLAYER_SPELL_CAST_ANIMATION = 8;
        public static final int PLAYER_CROUCH_ANIMATION = 3;
        public static final int PLAYER_DEFENSE_ANIMATION = 3;
        public static final int PLAYER_WALK_ANIMATION = 10;
        public static final int PLAYER_SLIDING_ANIMATION = 8;
        public static final int PLAYER_WALL_SLIDING_ANIMATION = 8;
        public static final int PLAYER_CRITICAL_ANIMATION = 8;
        public static final int PLAYER_CLIMBING_ANIMATION = 8;

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //NO of animation
        public static final int IDLE = 0;
        public static final int ATTACK = 1;
        public static final int RUN = 2;
        public static final int JUMP = 3;
        public static final int LAND = 4;
        public static final int HIT = 5;
        public static final int DEATH = 6;
        public static final int SPELL_CAST = 7;
        public static final int CROUCH = 8;
        public static final int DEFENSE = 9;
        public static final int WALK = 10;
        public static final int SLIDING = 11;
        public static final int WALL_SLIDING = 12;
        public static final int CRITICAL = 13;
        public static final int CLIMBING = 14;

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Get sprite amount
        public static int getSpriteAmount(int playerAction) {
            return switch (playerAction) {
                case IDLE -> PLAYER_IDLE_ANIMATION;
                case ATTACK -> PLAYER_ATTACK_ANIMATION;
                case RUN -> PLAYER_RUN_ANIMATION;
                case JUMP -> PLAYER_JUMP_ANIMATION;
                case LAND -> PLAYER_LAND_ANIMATION;
                case HIT -> PLAYER_DAMAGED_ANIMATION;
                case DEATH -> PLAYER_DEATH_ANIMATION;
                case SPELL_CAST -> PLAYER_SPELL_CAST_ANIMATION;
                case CROUCH -> PLAYER_CROUCH_ANIMATION;
                case DEFENSE -> PLAYER_DEFENSE_ANIMATION;
                case WALK -> PLAYER_WALK_ANIMATION;
                case SLIDING -> PLAYER_SLIDING_ANIMATION;
                case WALL_SLIDING -> PLAYER_WALL_SLIDING_ANIMATION;
                case CRITICAL -> PLAYER_CRITICAL_ANIMATION;
                case CLIMBING -> PLAYER_CLIMBING_ANIMATION;
                default -> IDLE;
            };
        }

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //UI
    public static class GUI{
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Menu Button
        public static class Buttons{
            public static final int BUTTON_WIDTH_DEFAULT = 64;
            public static final int BUTTON_HEIGHT_DEFAULT = 16;
            public static final int BUTTON_WIDTH = BUTTON_WIDTH_DEFAULT * 5;
            public static final int BUTTON_HEIGHT = BUTTON_HEIGHT_DEFAULT * 5;
        }

        //Pause Button
        public static class PauseButton{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = SOUND_SIZE_DEFAULT * 3 / 2;
        }

        //URM Button
        public static class URMButton{
            public static final int URM_BUTTON_SIZE_DEFAULT = 56;
            public static final int URM_BUTTON_SIZE = URM_BUTTON_SIZE_DEFAULT * 7 / 4;
        }

        //Volume Button
        public static class VolumeButton {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int VOLUME_WIDTH = VOLUME_WIDTH_DEFAULT * 2;
            public static final int VOLUME_HEIGHT = VOLUME_HEIGHT_DEFAULT * 2;
            public static final int SLIDER_WIDTH_DEFAULT = 215;
            public static final int SLIDER_WIDTH = SLIDER_WIDTH_DEFAULT * 2;
        }

    }

    public static class Environment{
        public static final int BIG_CLOUDS_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUDS_HEIGHT_DEFAULT = 101;
        public static final int BIG_CLOUDS_WIDTH = BIG_CLOUDS_WIDTH_DEFAULT * 11 / 10;
        public static final int BIG_CLOUDS_HEIGHT = BIG_CLOUDS_HEIGHT_DEFAULT * 11 / 10;

        public static final int SMALL_CLOUDS_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUDS_HEIGHT_DEFAULT = 24;
        public static final int SMALL_CLOUDS_WIDTH = SMALL_CLOUDS_WIDTH_DEFAULT * 3 / 2;
        public static final int SMALL_CLOUDS_HEIGHT = SMALL_CLOUDS_HEIGHT_DEFAULT * 2;
    }

}
