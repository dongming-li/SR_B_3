package com.trogdadr2tr.socket;

/** A very simple queue to prevent socket communication from
 * overlapping.
 * 
 * @author Colt Rogness */
public class ThreadQueue
{

	/** Nodes boi
	 * 
	 * @author Colt Rogness */
	private class TBQNode
	{

		public TBQNode next;

		public Thread data;

		public TBQNode(Thread data)
		{
			this.data = data;
			next = null;
		}
	}

	/** Top of the queue. */
	private TBQNode head;

	/** Size of the queue. */
	private int size;

	/** Init the queue. */
	public ThreadQueue()
	{
		head = null;
		size = 0;
	}

	/** Return the first thread in the queue. return null if queue
	 * empty. Remove thread.
	 * 
	 * @return */
	public Thread pop()
	{
		if (head == null)
		{
			return null;
		}
		TBQNode temp = head;
		size -= 1;
		head = temp.next;
		return temp.data;
	}

	/** Look at the top of the queue.
	 * 
	 * @return head */
	public Thread peek()
	{
		return (head == null) ? head.data : null;
	}

	/** Getter for size.
	 * 
	 * @return size */
	public int size()
	{
		return size;
	}

	/** Is the queue empty? Is batman a bad man? Do Ducks talk? Do
	 * I have to write this javadoc?
	 * 
	 * @return size == 0 */
	public boolean isEmpty()
	{
		return size == 0;
	}

	/** Purge the queue. */
	public void clear()
	{
		while (!isEmpty())
		{
			pop();
		}
	}

	/** Stick a thread on the back.
	 * 
	 * @param e thread to add
	 * @return if it was added or not */
	public boolean add(Thread e)
	{
		if (head == null)
		{
			head = new TBQNode(e);
			size++;
			return true;
		}
		TBQNode cursor = head;
		while (cursor.next != null)
		{
			cursor = cursor.next;
		}
		cursor.next = new TBQNode(e);
		size++;
		return true;
	}

}
