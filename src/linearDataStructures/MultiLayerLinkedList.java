package linearDataStructures;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class MultiLayerLinkedList<T, I> implements Iterable<T> {
	
	HashMap<I, Node<T, I>> startNodes;
	HashMap<I, Node<T, I>> currentNodes;
	HashMap<I, Node<T, I>> lastNodes;
	
	HashMap<I, Integer> size;
	HashSet<I> SubListKeys;
	
	public MultiLayerLinkedList () {
		SubListKeys = new HashSet<I>();
		size = new HashMap<I, Integer>();
		
		size.put(null, 0);
		
		startNodes = new HashMap<I, Node<T, I>>();
		currentNodes = new HashMap<I, Node<T, I>>();
		lastNodes = new HashMap<I, Node<T, I>>();
		
	}
	
	public T getContent(I i) {
		checkNull(i);
		if (hasNext()) return currentNodes.get(i).getContent();
		else throw new NoSuchElementException();
	}
	
	public T getContent() {
		if (hasNext()) return currentNodes.get(null).getContent();
		else throw new NoSuchElementException();
	}
	
	//TODO: addContent (Collection<I> i, T Data) 
	public void addContent(I i, T data) {
		checkNull(i);
		
		Node<T, I> newNode = new Node<T, I>(data);
		
		if (SubListKeys.contains(i)) {
			lastNodes.get(i).setNext(i, newNode);
			lastNodes.put(i, newNode);
			size.put(i, size.get(i) + 1);
		}
		else {
			SubListKeys.add(i);
			size.put(i, 1);
			
			startNodes.put(i, newNode);
			currentNodes.put(i, newNode);
			lastNodes.put(i, newNode);
		}
	
		addContent(data);
	}
	
	public void addContent(Collection<I> indices, T data) {
		for (I i : indices) {
			checkNull(i);
			
			Node<T, I> newNode = new Node<T, I>(data);
			
			if (SubListKeys.contains(i)) {
				lastNodes.get(i).setNext(i, newNode);
				newNode.setPrev(i, lastNodes.get(i));
				lastNodes.put(i, newNode);
				size.put(i, size.get(i) + 1);
			}
			else {
				SubListKeys.add(i);
				size.put(i, 1);
				newNode.setPrev(i, null);
				startNodes.put(i, newNode);
				currentNodes.put(i, newNode);
				lastNodes.put(i, newNode);
			}
		
		}
		addContent(data);
	}
	
	public void addContent(T data) {
		
		Node<T, I> newNode = new Node<T, I>(data);
		if (hasData()) {
			lastNodes.get(null).setNext(null, newNode);
			newNode.setPrev(null, lastNodes.get(null));
			lastNodes.put(null, newNode);
			size.put(null, size.get(null) + 1);
		}
		else {
			size.put(null, 1);
			newNode.setPrev(null, null);
			startNodes.put(null, newNode);
			currentNodes.put(null, newNode);
			lastNodes.put(null, newNode);
		}
	}
	
	public boolean hasNext(I i) {
		checkNull(i);
		return currentNodes.get(i) != null;
	}
	
	public boolean hasNext() {
		return currentNodes.get(null) != null;
	}
	 
	public boolean hasData() {
		return !(size.get(null) == 0);
	}
	
	public void toStart(I i) {
		checkNull(i);
		currentNodes.put(i, startNodes.get(i));
	}
	
	public void toStart() {
		currentNodes.put(null, startNodes.get(null));
	}
	
	public void replaceContent(I i, T data) {
		checkNull(i);
		currentNodes.get(i).setContent(data);
	}
	
	public void removeFromHere(I i) {
		checkNull(i);
		currentNodes.get(i).setNext(i, null);
	}
	
	public void removeFromHere() {
		currentNodes.get(null).setNext(null, null);
	}
	
	public void remove(I i) {
		currentNodes.get(i).getPrev(i).setNext(i, currentNodes.get(i).getNext(i));
		currentNodes.put(i, currentNodes.get(i).getPrev(i));
	}
	
	public void remove() {
		currentNodes.get(null).getPrev(null).setNext(null, currentNodes.get(null).getNext(null));
		currentNodes.put(null, currentNodes.get(null).getPrev(null));
	}
	
	public void remove(I i, T t) {
		toStart(i);
		while(hasNext(i)) {
			if (currentNodes.get(i).getContent() == t) {
				remove(i);
				break;
			}
			next(i);
		}
	}
	
	public T next() {
		if (currentNodes.get(null) == null) throw new NoSuchElementException();
		T content = currentNodes.get(null).getContent();
		currentNodes.put(null, currentNodes.get(null).getNext(null));
		return content;
	}
	
	public T next(I i) {
		checkNull(i);
		if (currentNodes.get(i) == null) throw new NoSuchElementException();
		T content = currentNodes.get(i).getContent();
		
		currentNodes.put(i, currentNodes.get(i).getNext(i));
		return content;
	}
	
	public void checkNull(I i) {
		if (i == null) throw new NullPointerException();
	}
	
	public String toString() {
		String res = "";
		
		res += "For Mainlist: ";
	
		toStart();
		while(hasNext()) {
			res += next().toString() + " ";;
		} 
	
		for (I i : SubListKeys) {
			res += "\n" + toString(i);
		}
		
		return res;
	}
	
	public String toString(I i) {
		String res = "";
	
		res += "For Sublist " + i.toString() + ": ";
		
		toStart(i);
		while(hasNext(i)) {
			res += next(i).toString() + " ";
		}
		
		return res;
	}
	
	static class Node<T, I> {
		private T data;
		private HashMap<I, Node<T, I>> nextNodes;
		private HashMap<I, Node<T, I>> prevNodes;
		
		Node(T data_) {
			nextNodes = new HashMap<I, Node<T, I>>();
			prevNodes = new HashMap<I, Node<T, I>>();
			data = data_;
		}
		
		T getContent() {
			return data;
		}
		
		void setContent(T newData) {
			data = newData;
		}
		
		Node<T, I> getNext(I i) {
			return nextNodes.get(i);
		}
		
		void setNext(I i, Node<T, I> newNode) {
			nextNodes.put(i, newNode);
		}
		
		Node<T, I> getPrev(I i) {
			return prevNodes.get(i);
		}
		
		void setPrev(I i, Node<T, I> newNode) {
			prevNodes.put(i, newNode);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new MultiLayerLinkedListIterator<T, I>(this);
	}

	public Iterator<T> iterator(I i) {
		return new LinkedSubListIterator<T, I>(this, i);
	}

}
