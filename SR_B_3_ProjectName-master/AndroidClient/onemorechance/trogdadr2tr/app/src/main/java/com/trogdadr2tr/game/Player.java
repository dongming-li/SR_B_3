package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */
import java.io.Serializable;
import com.trogdadr2tr.resources.MovementDirection;

public class Player implements Serializable
{
    /** Player MK 0 */
    private static final long serialVersionUID = 0;

    /** The direction they are moving */
    public MovementDirection direction;

    /** The x position */
    public double x;

    /** The y position */
    public double y;

    /** The amount of health you have */
    private int hp;

    /** If you are ready or not */
    private Boolean ready;

    /** The player type, like RTS or Hack-n'-Slash */
    public final PlayerType playerType;

    /**
     * The player and the player type
     * @param playerType The value for the player type received from the caller
     */
    public Player(PlayerType playerType)
    {
        direction = MovementDirection.STATIONARY;
        ready = false;
        this.playerType = playerType;
    }

    /**
     * Checks to see if you are ready or not
     * @return ready
     */
    public Boolean isReady()
    {
        return ready;
    }
    /**
     * Sets the movement direction
     * @param move The value for the direction needed to move received from the caller
     */
    public void setDirection(MovementDirection move)
    {
        direction = move;
    }

    /**
     * The movement direction that you are going
     * @return the direction you are going
     */
    public MovementDirection getDiretion()
    {
        return direction;
    }

    /**
     * Sets if you are ready or not
     * @param ready The boolean value if you are ready received from the caller
     */
    public void setReadiness(Boolean ready)
    {
        this.ready = ready;
    }

    /**
     * Checks to see if you are hosting the game or not
     * @return if you are the host which needs to be true, then true, else its false
     */
    public Boolean isHost()
    {
        return playerType == PlayerType.RTS;
    }

    /**
     * Moves the player with collision detection
     * @param milisecondsPast The value for the the time that passes received from the caller
     */
    public void doMove(long milisecondsPast)
    {
        // TODO - collision detection
        int speed = 10;
        x += direction.x / speed * milisecondsPast;
        y += direction.y / speed * milisecondsPast;
    }

    /**
    * Takes damage and updates the value with your current health
    * @param damage
    */
    public void takeDamage(int damage)
    {
        hp -= damage;
        if (hp <= 0)
        {
            die();
        }
    }

    /**
     * Kills the player on the map
     */
    private void die()
    {
        // fuckin dead m8
    }
}

