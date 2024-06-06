package Objects;

import java.awt.geom.Rectangle2D;

import static Utilization.ConstantVariables.Projectiles.*;

public class Projectile {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Rectangle2D.Float hitbox;
    private int direction;
    private boolean active = true;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Projectile(int x, int y, int direction){
        int xOffset = -5;
        int yOffset = 8;
        if (direction == 1) xOffset = 43;
        hitbox = new Rectangle2D.Float(x, y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
        this.direction = direction;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void updatePosition(){
        hitbox.x += direction * CANNON_BALL_SPEED;
//        if (direction == 1){
//            hitbox.x -= CANNON_BALL_SPEED;
//        } else {
//            hitbox.x += CANNON_BALL_SPEED;
//        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Set position
    public void setPosition(int x, int y){
        hitbox.x = x;
        hitbox.y = y;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
