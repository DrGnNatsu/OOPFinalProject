package Objects;

import java.awt.geom.Rectangle2D;

import static Utilization.ConstantVariables.ANIMATION_SPEED;
import static Utilization.ConstantVariables.ObjectConstants.*;

public class Object {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the variables for the entities
    //Position
    protected int x, y;

    protected int xDrawOffset, yDrawOffset;
    //Hitbox
    protected Rectangle2D.Float hitbox;
    //Type
    protected int objectType;
    //Animation
    protected boolean doAnimation, active = true;
    protected int animationIndex, animationTick;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Object(int x, int y, int objectType){
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(objectType)){
                animationIndex = 0;
                if (objectType == BARREL || objectType == BOX){
                    doAnimation = false;
                    active = false;
                }

                if (objectType == CANNON_LEFT || objectType == CANNON_RIGHT){
                    doAnimation = false;

                }

            }

        }

    }

    //Reset the animation
    protected void reset(){
        animationTick = 0;
        animationIndex = 0;
        active = true;

        doAnimation = (objectType != BARREL && objectType != BOX
                && objectType != CANNON_LEFT && objectType != CANNON_RIGHT);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the hitbox
    protected void createHitbox(int width, int height){
        hitbox = new Rectangle2D.Float(x, y, width * 2, height *  2);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public void setXDrawOffset(int xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public void setYDrawOffset(int yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isDoAnimation() {
        return doAnimation;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getObjectType() {
        return objectType;
    }

    public int getAnimationTick() {
        return animationTick;
    }
}
