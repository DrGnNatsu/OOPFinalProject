package Utilization;

public class ConstantVariables {
    public static class PlayerConstant {
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Animation
        public static final int PLAYER_IDLE_ANIMATION = 6;
        public static final int PLAYER_ATTACK_ANIMATION = 8;
        public static final int PLAYER_RUN_ANIMATION = 8;
        public static final int PLAYER_JUMP_ANIMATION = 8;
        public static final int PLAYER_LAND_ANIMATION = 8;
        public static final int PLAYER_DAMAGED_ANIMATION = 4;
        public static final int PLAYER_DEATH_ANIMATION = 12;
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
        public static final int DAMAGED = 5;
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
                case DAMAGED -> PLAYER_DAMAGED_ANIMATION;
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

    }
}
