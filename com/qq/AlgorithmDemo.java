package com.qq;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Iterator;

public class AlgorithmDemo {

	public static void main(String[] args) {

		try {
			//输入[(])
			CustomizedArrayStack<String> list = new CustomizedArrayStack<String>();
			list.push("[");
			list.push("(");
			list.push("]");
			list.push(")");
			
			if (list.size() > 0) {
				System.out.println(false);
			} else {
				System.out.println(true);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// for (Integer str : list) {
		// System.out.println("str: " + str);
		// }
		//
		// LIFONode<String> node = new LIFONode<String>();
		// node.push("1");
		// node.push("2");
		// node.push("3");
		// node.push("4");
		// node.push("5");
		// int size = node.size;
		//
		// for (int index = 0; index < size; index++) {
		// for (String str : node) {
		// System.out.println(str + " - " + node.size);
		// }
		// node.poll();
		// }
		//
		// System.out.println("===========================");
		//
		// FIFONode<String> mNode = new FIFONode<String>();
		// mNode.push("1");
		// mNode.push("2");
		// mNode.push("3");
		// mNode.push("4");
		// mNode.push("5");
		// size = mNode.size;
		// for (int num = 0; num < size; num++) {
		// for (String str : mNode) {
		// System.out.println(str + " - " + mNode.size);
		// }
		// System.out.println(mNode.poll());
		// }

	}

}

class CustomizedArrayStack<Item> implements Iterable<Item> {
	// 泛性数组，java不支持直接创建泛性数组
	// 但支持类型强转，不过还是建议直接用
	// Object数组替代泛性数组
	private Item[] items = (Item[]) new Object[8];
	// 用于记录实际元素数目
	private int size = 0;

	// 入栈操作
	public void push(Item item) throws Exception {
		if (size == items.length) {
			// 当栈大小无法再添加其它元素时，扩充栈空间大小
			Item[] mItems = (Item[]) new Object[items.length * 2];
			for (int index = 0; index < items.length; index++) {
				mItems[index] = items[index];
			}
			items = mItems;
		}
		//每次入栈判断是否有"}","]",")"中的其中一个
		switch (item.toString()) {
		case "}":
			match("{");
			break;
		case "]":
			match("[");
			break;
		case ")":
			match("(");
			break;
		default:
			items[size++] = item;
			break;
		}
	}

	// 出栈操作
	public Item poll() {
		if (size == 0) {
			throw new UnsupportedOperationException();
		}
		Item item = items[--size];
		items[size] = null;
		return item;
	}

	// 获取角标index的元素
	public Item get(int index) {
		if (size == 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return items[index];
	}

	public int size() {
		return size;
	}
	
	//对符号进行匹对
	public void match(String symbol) throws Exception {
		if (size == 0 || !poll().toString().equals(symbol)) {
			throw new Exception("false");
		}
	}

	@Override
	public Iterator<Item> iterator() {
		return new CustomizedIterator();
	}

	// 自定义栈迭代器，支持遍历元素
	private class CustomizedIterator implements Iterator<Item> {
		// 每次遍历从角标0开始
		private int index = size;

		@Override
		public boolean hasNext() {
			return index > 0;
		}

		@Override
		public Item next() {
			return items[--index];
		}
	}
}

class CustomizedArrayQueue<Item> implements Iterable<Item> {
	private Item[] items = (Item[]) new Object[4];
	private int size = 0;

	public void add(Item item) {
		items[size++] = item;
	}

	public void push(Item item) {
		if (size == items.length) {
			Item[] mItems = (Item[]) new Object[items.length * 2];
			for (int index = 0; index < items.length; index++) {
				mItems[index] = items[index];
			}
			items = mItems;
		}
		items[size++] = item;
	}

	public Item Poll() {
		if (size == 0) {
			throw new UnsupportedOperationException();
		}
		Item item = items[0];
		for (int index = 0; index < size - 1; index++) {
			items[index] = items[index + 1];
		}
		items[--size] = null;
		return item;
	}

	// 获取角标index的元素
	public Item get(int index) {
		if (size == 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return items[index];
	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<Item> iterator() {
		return new CustomizedIterator();
	}

	private class CustomizedIterator implements Iterator<Item> {

		private int mSize = 0;

		@Override
		public boolean hasNext() {
			return mSize < size;
		}

		@Override
		public Item next() {
			return items[mSize++];
		}

	}

}

// 栈 后进先出链表
class CustomizedLinkedStack<Item> extends Object implements Iterable<Item> {
	Node<Item> nextNode;
	int size = 0;

	public void push(Item item) {
		Node<Item> oldNode = nextNode;
		Node<Item> itemNode = new Node<Item>();
		itemNode.item = item;
		nextNode = itemNode;
		nextNode.nextNode = oldNode;
		size++;
	}

	public Item poll() {
		if (nextNode == null) {
			return null;
		}
		Node<Item> oldNode = nextNode;
		nextNode = nextNode.nextNode;
		size--;
		return oldNode.item;
	}

	// 获取角标index的元素
	public Item get(int index) {
		if (size == 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<Item> oldNode = nextNode;
		for (int num = 1; num <= index; num++) {
			oldNode = oldNode.nextNode;
		}
		return oldNode.item;
	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<Item> iterator() {
		return new CustomizedIterator();
	}

	private class Node<Item> {
		Item item;
		Node nextNode;
	}

	private class CustomizedIterator implements Iterator<Item> {

		private Node<Item> mNode = nextNode;

		@Override
		public boolean hasNext() {
			return mNode != null;
		}

		@Override
		public Item next() {
			Item item = mNode.item;
			mNode = mNode.nextNode;
			return item;
		}

	}

}

// 先进先出链表
class CustomizedLinkedQueue<Item> implements Iterable<Item> {
	// 记录链表头，用于删除元素
	Node<Item> nextNode;
	// 记录链表尾，用于添加元素
	Node<Item> headNode;
	// 记录元素数目
	int size = 0;

	// 往链表尾添加元素
	public void push(Item item) {
		Node<Item> itemNode = new Node<Item>();
		itemNode.item = item;
		if (nextNode != null) {
			nextNode.nextNode = itemNode;
		} else {
			headNode = itemNode;
		}
		nextNode = itemNode;
		size++;
	}

	// 往链表头删除元素
	public Item poll() {
		if (headNode == null) {
			throw new UnsupportedOperationException();
		}
		Node<Item> oldNode = headNode;
		headNode = headNode.nextNode;
		if (--size == 0) {
			nextNode = null;
		}
		return oldNode.item;
	}

	// 获取角标index的元素
	public Item get(int index) {
		if (size == 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<Item> oldNode = headNode;
		for (int num = 1; num <= index; num++) {
			oldNode = oldNode.nextNode;
		}
		return oldNode.item;
	}

	public int size() {
		throw new NullPointerException();
	}

	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}

	private class Node<Item> {
		Item item;
		Node nextNode;
	}

	// 队列迭代器
	private class QueueIterator implements Iterator<Item> {

		private Node<Item> mNode = headNode;

		@Override
		public boolean hasNext() {
			return mNode != null;
		}

		@Override
		public Item next() {
			Item item = mNode.item;
			mNode = mNode.nextNode;
			return item;
		}

	}

}

// 环形双向链表实现的队列
class LoopQueue<Item> implements Iterable<Item> {
	// 记录开始的节点
	private Node nodeLink;
	// 记录节点的数量
	private int size;

	public LoopQueue() {
		// 当只有一个节点时，该节点的上个节点和上个节点都是它本身
		// 这里新建一个节点，用来做哨兵用
		nodeLink = new Node();
		nodeLink.previous = nodeLink;
		nodeLink.next = nodeLink;
	}

	// 入队
	public void push(Item item) {
		Node oldNode = nodeLink.previous;
		Node newNode = new Node(oldNode, nodeLink, item);
		oldNode.next = newNode;
		nodeLink.previous = newNode;
		size++;
	}

	// 出队
	public Item poll() {
		if (size == 0) {
			throw new UnsupportedOperationException();
		}
		Node oldNode = nodeLink.next;
		nodeLink.next = oldNode.next;
		oldNode.next.previous = nodeLink;
		size--;
		return oldNode.item;
	}

	public int size() {
		return size;
	}

	private class Node {
		Node previous;
		Node next;
		Item item;

		public Node() {

		}

		public Node(Node previous, Node next, Item item) {
			this.previous = previous;
			this.next = next;
			this.item = item;
		}
	}

	// 队列迭代器
	private class QueueIterator implements Iterator<Item> {

		private Node iteratorNode = nodeLink;

		@Override
		public boolean hasNext() {
			return (iteratorNode = iteratorNode.next) != nodeLink;
		}

		@Override
		public Item next() {
			return iteratorNode.item;
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}
}
