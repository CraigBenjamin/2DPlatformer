package Utils;

import MainFiles.Game;

public class Constants {
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT= (int) (B_HEIGHT_DEFAULT * Game.SCALE);

        }
        
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
        }

        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;
            public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);

        }
    }
    public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

    public static class PlayerConstants{
        public static final int Idle = 0; //idle row (when j = 0)
        public static final int Running = 1; //running row
        public static final int Jumping = 2; //...etc
        public static final int Falling = 3;
        public static final int TouchingGround = 4;
        public static final int Hit = 5;
        public static final int AttackOne =  6;
        public static final int AttackJumpOne = 7;
        public static final int AttackJumpTwo = 8;
        
        public static int GetSpriteAmount(int playerAction) {
            
            switch(playerAction){
                //is used in the updateAnimation() method to determine amt of ticks;
                case Running:
                    return 6;
                
                case Idle:
                    return 5;
                
                case Hit:
                    return 4;
    
                case Jumping:
                case AttackOne:
                case AttackJumpOne:
                case AttackJumpTwo:
                    return 3;
                
                case TouchingGround:
                    return 2;
                
                case Falling:
                default:
                    return 1;
            }

        }
    }
    
}
