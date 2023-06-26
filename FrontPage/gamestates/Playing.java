package gamestates;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import GameLevels.LevelManager;
import MainFiles.Game;
import PlayerEntities.Player;
import Utils.LoadNSave;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadNSave.getLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    public Playing(Game game) {
        super(game);
        initializeClasses();
    }

    private void initializeClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)(64 * Game.SCALE), (int)(40 * Game.SCALE));
		//x: 200 * Scale
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    public void windowFocusLost() {
        player.resetBooleans();
        //points to resetBooleans method in player to reset the boolean values
        //have to point here since Game is the only class with a player obj
    }

    public Player getPlayer(){
            return player;
    }

    @Override
    public void update() {
        if(!paused) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }
        else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if(diff > rightBorder)
            xLvlOffset += diff - rightBorder; 
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0 )
            xLvlOffset = 0;
            
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if(paused){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }
    public void mouseDragged(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseDragged(e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {//1 = left, 2 = middle, 3 = right; (on mouse)
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if(paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        if(paused)
            pauseOverlay.mouseMoved(e);
    }

    public void unpauseGame(){
        paused = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){

            case KeyEvent.VK_A:
                player.setLeft(true);
                break;

            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;

            case KeyEvent.VK_D:
                player.setRight(true);
                break;

            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;

            case KeyEvent.VK_SPACE:
                player.setJumping(true);
                break;

            case KeyEvent.VK_BACK_SPACE:
                paused = !paused;
                break;
                //if backspace is pressed we go to menu

            // case KeyEvent.VK_W:
            //     player.setUp(true);
            //     break;

            // case KeyEvent.VK_UP:
            //     player.setUp(true);
            //     break;

            // case KeyEvent.VK_S:
            //     player.setDown(true);
            //     break;

            // case KeyEvent.VK_DOWN:
            //     player.setDown(true);
            //     break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){

            case KeyEvent.VK_A:
                player.setLeft(false);
                break;

            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;

            case KeyEvent.VK_D:
                player.setRight(false);
                break;

            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;

            case KeyEvent.VK_SPACE:
                player.setJumping(false);
                break;


            // case KeyEvent.VK_W:
            //     player.setUp(false);
            //     break;

            // case KeyEvent.VK_UP:
            //     player.setUp(false);
            //     break;

            // case KeyEvent.VK_S:
            //     player.setDown(false);
            //     break;

            // case KeyEvent.VK_DOWN:
            //     player.setDown(false);
            //     break;
        }
    }

}


