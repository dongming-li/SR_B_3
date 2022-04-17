package com.trogdadr2tr.resources;

/**
 * Created by Danny on 12/1/2017.
 */
import java.io.Serializable;
import com.trogdadr2tr.game.Player;

public class Message implements Serializable
{

    /** Message MK 0 */
    private static final long serialVersionUID = 0;

    /** The author that made the message */
    Player author;

    /** The message text */
    String text;

    /**
     * Messaging between players
     * @param author The value for the player received from the caller
     * @param messageText The value for the message received from the caller
     */
    public Message(Player author, String messageText)
    {
        this.author = author;
        text = messageText;
    }

}
