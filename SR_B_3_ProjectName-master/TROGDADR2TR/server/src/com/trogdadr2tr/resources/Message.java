package com.trogdadr2tr.resources;

import java.io.Serializable;

import com.trogdadr2tr.game.Player;

/**
 * Metadata for messages needed for chat functionality.
 * @author Colt Rogness
 *
 */
public class Message implements Serializable
{

	/** Message MK 0 */
	private static final long serialVersionUID = 0;

	/**
	 * Author of the message.
	 */
	Player author;

	/**
	 * Contents of the message.
	 */
	String text;

	/**
	 * Default message constructor.
	 * @param author of the message
	 * @param messageText text in the message
	 */
	public Message(Player author, String messageText)
	{
		this.author = author;
		text = messageText;
	}

}
