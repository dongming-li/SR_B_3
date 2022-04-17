package com.trogdadr2tr.socket;

public class ThreadQueue
{

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

	private TBQNode head;

	private int size;

	public ThreadQueue()
	{
		head = null;
		size = 0;
	}

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

	public Thread peek()
	{
		return (head == null) ? head.data : null;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public void clear()
	{
		while (!isEmpty())
		{
			pop();
		}
	}

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
