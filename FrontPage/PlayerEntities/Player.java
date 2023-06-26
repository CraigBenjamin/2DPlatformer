package PlayerEntities;

//imports
// import static Utils.Constants.PlayerConstants.AttackOne;
// import static Utils.Constants.PlayerConstants.GetSpriteAmount;
// import static Utils.Constants.PlayerConstants.Idle;
// import static Utils.Constants.PlayerConstants.Running;
import static Utils.Constants.PlayerConstants.*;
import static Utils.HelpMethods.*;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import MainFiles.Game;
import Utils.LoadNSave;

/* Unused Imports
import static Utils.Constants.PlayerConstants.Jumping;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.tools.DocumentationTool.Location;

import java.io.IOException;
import java.io.InputStream;
*/

public class Player extends Entity {

    //initializing variables
    private BufferedImage[][] Animations;

    private int AnimationTick, AnimationIndex, AnimationSpeed = 25;
    private int playerAction = Idle;

    private boolean moving = false, attacking = false, jumping;
    private boolean left, up, right, down;

    private float playerSpeed = 1.0f * Game.SCALE;
    private float xValOffset = 21 * Game.SCALE;
    private float yValOffset = 4 * Game.SCALE;
    //private float playerHeight = 1.5f;

    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private int[][] levelData;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y,(int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
    }

    public void update(){
        updatePosition();
        //updateHitbox();
        updateAnimation();
        setNewAnimation();
    }

    //change player size here
    public void render(Graphics p, int LvlOffset){
        p.drawImage(Animations[playerAction][AnimationIndex], (int)(hitbox.x - xValOffset) - LvlOffset, (int)(hitbox.y - yValOffset), width, height,  null); // (int)(120), (80)
        //drawHitbox(p);
    }

    private void updatePosition() {
        moving = false;

        if(jumping)
            jump();
            
        // if(!left && !right && !inAir)
        //     return;
        if(!inAir) 
            if((!left && !right) || (left && right))
                return;

        float xSpeed = 0; //temporary storage of speed
        //pass them into "canMoveHere" method and apply the to actual position of player if it returns true

        if(left)
            xSpeed -= playerSpeed; //makes it so we can move ONLY if we can move in that place.. otherwise no
        if (right)
            xSpeed += playerSpeed;
        if(!inAir){
            if(!isEntityOnFloor(hitbox, levelData)){
                inAir = true;
            }
        }
        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        }
        else{
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        }
        else{
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void setNewAnimation() {
        int startNewAnimation = playerAction; //idle at first
        if(moving){
           playerAction = Running;
        }
        else{
           playerAction = Idle;
        }

        if(inAir){
            if(airSpeed < 0)
                playerAction = Jumping;
            else
                playerAction = Falling;
        }

        if(attacking){
            playerAction = AttackOne;
        }

        if(startNewAnimation != playerAction){
            resetAnimationTickAndIndex();
        }
    }

    private void resetAnimationTickAndIndex() {
        //resets the tick and index so that we get a full cycle of new animations
            AnimationTick = 0;
            AnimationIndex = 0;
    }



    private void updateAnimation() {
        AnimationTick++;

        if(AnimationTick >= AnimationSpeed){
            AnimationTick = 0;
            AnimationIndex++;
            if(AnimationIndex >= GetSpriteAmount(playerAction)) {
               AnimationIndex = 0; //resets at end of row
               attacking = false;
            }
        }
    }

    private void loadAnimations() {
        BufferedImage image = LoadNSave.GetSpriteAtlas(LoadNSave.PlayerAtlas);

        Animations = new BufferedImage[9][6];
        //y-axis indx = 9 (j); x-axis indx = 6 (i);
        for(int j = 0; j < Animations.length; j++){//checks for outer dimension
            //dependent for loop
            for(int i = 0; i < Animations[j].length; i++){
                //i is counted until 5 (0,1,2,3,4,5)
                Animations[j][i] = image.getSubimage(i * 64,j * 40, 64, 40);
            }
        }
    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public void loadLevelData(int[][] levelData){
        this.levelData = levelData;
        if(!isEntityOnFloor(hitbox, levelData))
            inAir = true;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetBooleans() {
        //resets boolean values to false when window focus is lost
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public Component setLocationRelativeTo() {
       return null;
    }

}
