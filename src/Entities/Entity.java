package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the variables for the entities
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    //Create animation variables
    protected int animationIndex, animationTick;

    //State of entity
    protected int state;

    //Gravity
    protected float airSpeed = 0f;
    protected boolean inAir = false;

    //Health
    protected int maxHealth;
    protected int currentHealth;

    //Attack box
    protected Rectangle2D.Float attackBox;

    //Speed
    protected float walkSpeed;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create hitbox for entities
    protected void createHitbox(float width, float height){
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    //Draw the hitbox
    protected void drawHitbox(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

}
