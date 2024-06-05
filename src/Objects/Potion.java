package Objects;

import Game.Game;

public class Potion extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        this.doAnimation = true;
        createHitbox(7, 14);
        this.xDrawOffset = (int) (3 * Game.TILE_SCALE);
        this.yDrawOffset = (int) (2 * Game.TILE_SCALE);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        super.updateAnimationTick();
    }
}
