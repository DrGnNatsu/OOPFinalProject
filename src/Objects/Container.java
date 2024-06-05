package Objects;

import Game.Game;

import static Utilization.ConstantVariables.ObjectConstants.*;


public class Container extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Container(int x, int y, int objectType) {
        super(x, y, objectType);
        initializeHitbox();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        if(doAnimation)
            super.updateAnimationTick();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create hitbox
    private void initializeHitbox(){
        if (objectType == BOX){
            createHitbox(25, 18);
            this.xDrawOffset = (int) (7 * Game.TILE_SCALE);
            this.yDrawOffset = (int) (12 * Game.TILE_SCALE);
        } else {
            createHitbox(23, 25);
            this.xDrawOffset = (int) (8 * Game.TILE_SCALE);
            this.yDrawOffset = (int) (5 * Game.TILE_SCALE);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


}
