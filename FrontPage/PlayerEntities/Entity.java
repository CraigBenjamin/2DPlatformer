package PlayerEntities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import MainFiles.Game;

public abstract class Entity {
   
    protected float x, y; 
    protected int width, height;
    protected Rectangle2D.Float hitbox; //for hitbox
    //if protected, only classes that extend this class can use these variables.
    
    public Entity(float x, float y, int width, int height){
        // this.x = x*Game.Scale;
        // this.y = y*Game.Scale;
        // this.width = width;
        // this.height = height;
        
        this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

        //initHitbox(x, y, (20*Game.Scale), (28*Game.Scale));
        
    }
    protected void drawHitbox(Graphics g){
        //For debugging the hitbox
        g.setColor(Color.pink);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);

    }
    protected void initHitbox(float x, float y, int width, int height) {
        //creating hitbox out of entire sprite image (first)
        hitbox = new Rectangle2D.Float(x,y,width,height);
    }
  /*  protected void updateHitbox(){
        //only player can update it..no other class
        //takes the new x and y and puts it to the hitbox
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }*/
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }


}
 /* cannot create an object of this class...
    we CANNOT do this: Entity enemyOne = new Entity(120, ... , bigman);
    instead, we will EXTEND this class to our player and enemy classes to use
    its variables and properties
    for ex. public class enemy{ Enemy enemyOne = new Enemy(...); }
    and public class player { Player playerOne = new Player(...); }
    Both classes will extend the Entity class and use its properties as their own
    */