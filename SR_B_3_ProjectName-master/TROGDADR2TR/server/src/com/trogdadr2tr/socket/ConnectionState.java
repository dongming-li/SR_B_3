package com.trogdadr2tr.socket;

/** Different states the connection between client and server can
 * be in.
 * 
 * @author Colt Rogness */
public enum ConnectionState
{
	DISCONNECTED, NEWCOMER, LOGGED_IN, HOST, GUEST, RTS_IN_GAME, HaS_IN_GAME
}
