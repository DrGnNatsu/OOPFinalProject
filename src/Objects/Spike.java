package Objects;

import java.util.ArrayList;
import Game.Game;

public class Spike extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);
        this.doAnimation = true;
        createHitbox(32, 16);
        this.xDrawOffset = 0;
        this.yDrawOffset = 16;
        hitbox.y += yDrawOffset - 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load Spikes
    public static ArrayList<Spike> loadSpikes (int[][] levelData){
        ArrayList<Spike> spikes = new ArrayList<>();
        for (int i = 0; i < levelData.length; i++){
            for (int j = 0; j < levelData[i].length ; j++){
                if (levelData[i][j] == 7){
                    spikes.add(new Spike(j * 36, i * 36 , levelData[i][j] - 3));
                }

            }
        }
        return spikes;
    }
}
