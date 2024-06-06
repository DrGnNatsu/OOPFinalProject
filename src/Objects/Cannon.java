package Objects;

import Game.Game;

import java.util.ArrayList;

public class Cannon extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private int tileY;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        createHitbox(40, 32);
        this.doAnimation = true;
        tileY = y / 36;
        this.xDrawOffset = 0;
        this.yDrawOffset = 6;
        hitbox.x -= 4 * Game.TILE_SCALE;
        hitbox.y += yDrawOffset - 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load Cannon
    public static ArrayList<Cannon> loadCannon (int[][] levelData){
        ArrayList<Cannon> cannons = new ArrayList<>();
        for (int i = 0; i < levelData.length; i++){
            for (int j = 0; j < levelData[i].length ; j++){
                if (levelData[i][j] == 8 || levelData[i][j] == 9){
                    cannons.add(new Cannon(j * 36, i * 36 - 23, levelData[i][j] - 3));
                }

            }
        }
        return cannons;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        if(doAnimation) updateAnimationTick();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public int getTileY() {
        return tileY;
    }
}
