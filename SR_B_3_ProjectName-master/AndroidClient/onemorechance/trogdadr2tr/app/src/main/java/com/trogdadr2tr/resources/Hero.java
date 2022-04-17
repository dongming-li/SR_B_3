package com.trogdadr2tr.resources;

import java.io.Serializable;

/**
 * Created by Danny on 11/8/2017.
 */

public class Hero implements Serializable
{

    /** Hero */
    private static final long serialVersionUID = 1L;

    /** The x position of this Hero */
    private double xPos;

    /** The y position of this Hero */
    private double yPos;

    /** The width of this Hero */
    private double width;

    /** The height of this Hero */
    private double height;

    /** The current health of this Hero */
    private double health;

    /** The maximum health this Hero can have */
    private double maxHealth;

    /** How fast the Hero can move around the map */
    private double speed;

    /** The amount of health this Hero takes away from the
     * defending object when this Hero attacks */
    private double damage;

    /** How far away this Hero can deal damage */
    private double range;

    /**
     * This is the current health of the Hero
     */
    private double currentHealth;

    /** What this Hero is called */
    private String name;

    /** Creates a new Hero based on the String passed in
     *
     * @param x The x position this object will be created at
     * @param y The y position this object will be created at
     * @param n The name of this object. This determines what type
     *            of Hero is created */
    public Hero(double x, double y, String n)
    {
        switch (n)
        {
            case "Knight":
                health = 400;
                speed = 4;
                damage = 25;
                range = 2;
                width = 20;
                height = 20;
                break;
            case "Wizard":
                health = 100;
                speed = 5;
                damage = 30;
                range = 30;
                width = 20;
                height = 20;
                break;
            case "Archer":
                health = 175;
                speed = 7;
                damage = 20;
                range = 40;
                width = 20;
                height = 20;
                break;
        }
        maxHealth = health;
        xPos = x;
        yPos = y;
        name = n;
    }

    /** Subtracts the parameter amount from the Hero health.
     * SHOULD NOT BE USED IF AMOUNT IS ENOUGH TO KILL THIS HERO
     *
     * @param amount The amount of health this Hero loses */
    public void takeDamage(double amount)
    {
        health -= amount;
    }

    /** Acts like moving the Hero, but it sort of jumps to the
     * indicated x and y coords
     *
     * @param x The x coordinate the Hero is moved to
     * @param y The y coordinate the Hero is moved to */
    public void setPosition(double x, double y)
    {
        xPos = x;
        yPos = y;
    }

    /** Returns the maxHealth variable
     *
     * @return The maximum amount of health this Hero can have */
    public double getMaxHealth()
    {
        return maxHealth;
    }

    /** Returns the xPos variable
     *
     * @return The x position of this Hero */
    public double getX()
    {
        return xPos;
    }

    /** Returns the yPos variable
     *
     * @return The y position of this Hero */
    public double getY()
    {
        return yPos;
    }

    /** Returns the width variable
     *
     * @return The width of this Hero */
    public double getWidth()
    {
        return width;
    }

    /** Returns the height variable
     *
     * @return The height of this Hero */
    public double getHeight()
    {
        return height;
    }

    /** Returns the health variable
     *
     * @return The current health of this Hero */
    public double getHealth()
    {
        return health;
    }

    /** Returns the speed variable
     *
     * @return How fast this Hero can move around the map */
    public double getSpeed()
    {
        return speed;
    }

    /** Returns the damage variable
     *
     * @return The amount of damage this Hero can inflict on other
     *         objects */
    public double getDamage()
    {
        return damage;
    }

    /** Returns the range variable
     *
     * @return The maximum distance this Hero can attack */
    public double getRange()
    {
        return range;
    }

    /** Returns the name variable
     *
     * @return The name of this Hero. This determines the other
     *         stats of this Hero */
    public String getName()
    {
        return name;
    }

    /**
     * Sends the string of the input from the array
     * @return string of the input from the array
     */
    public String toString()
    {
        return name + ", (" + xPos + ", " + yPos + "),  HP:" + currentHealth + "/" + health + "\n";
    }
}
