package linearDataStructures;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
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
			newNode.setPrev(i, lastNodes.get(i));
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
	
		addNodeToMain(newNode);
	}
	
	public void addContent(Collection<I> indices, T data) {
		Node<T, I> newNode = new Node<T, I>(data);
		newNode.SubListIndices = indices;
		
		for (I i : indices) {
			checkNull(i);
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
		addNodeToMain(newNode);
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
	
	public void addNodeToMain(Node<T, I> newNode) {
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
		
		while(currentNodes.get(i) != lastNodes.get(i)) {
			System.out.println(currentNodes.get(i).getContent());
			
			remove(i);
			 
		}
		
	}
	
	public void removeFromHere() {
		if (hasNext()) {
			while(hasNext()) {
				next();
				if (hasNext()) remove();
			}
		}
	}
	
	public void remove(I i) {
		checkNull(i);
	
		if (currentNodes.get(i).getNext(i) == null) {
			if (currentNodes.get(i).getPrev(i) != null) {
				currentNodes.get(i).getPrev(i).setNext(i, null);
				
				lastNodes.put(i, currentNodes.get(i).getPrev(i));
				currentNodes.put(i, currentNodes.get(i).getPrev(i));
			}
			else {
				currentNodes.remove(i);
				lastNodes.remove(i);
				startNodes.remove(i);
			}
		}
		else {
			if (currentNodes.get(i).getPrev(i) != null) {
				currentNodes.get(i).getPrev(i).setNext(i, currentNodes.get(i).getNext(i));
				
				currentNodes.get(i).getNext(i).setPrev(i, currentNodes.get(i).getPrev(i));
				
				currentNodes.put(i, currentNodes.get(i).getNext(i));
			}
			else {
				
				currentNodes.get(i).getNext(i).setPrev(i, null);
				
				
				startNodes.put(i, currentNodes.get(i).getNext(i));
				currentNodes.put(i, currentNodes.get(i).getNext(i));
			}
		}
		
	}
	
	public void removeDeep(I i) {
		if (currentNodes.get(i).SubListIndices == null) {
			remove(i);
		}
		else {
			for (I i1 : currentNodes.get(i).SubListIndices) {
				if (i1 == i) i1 = null; 
				System.out.println(i1);
				Node<T, I> prev = currentNodes.get(i).getPrev(i1);
				Node<T, I> next = currentNodes.get(i).getNext(i1);
				
				if (next == null) {
					if (prev != null) {
						System.out.println("a");
						prev.setNext(i1, null);
						
						lastNodes.put(i1, prev);
						currentNodes.put(i1, prev);
					}
					else {
						System.out.println("b");
						currentNodes.remove(i1);
						lastNodes.remove(i1);
						startNodes.remove(i1);
					}
				}
				else {
					if (prev != null) {
						System.out.println("c");
						prev.setNext(i1, next);
						
						next.setPrev(i, prev);
						
						currentNodes.put(i1, next);
					}
					else {
						System.out.println("d");
						next.setPrev(i1, null);

						startNodes.put(i1, next);
						currentNodes.put(i1, next);
					}
				}
			}
			
			remove(i);
		}
		
		/*currentNodes.get(i).getPrev(i).setNext(i, currentNodes.get(i).getNext(i));
		
		currentNodes.get(i).getNext(i).setPrev(i, currentNodes.get(i).getPrev(i));
		
		currentNodes.put(i, currentNodes.get(i).getNext(i));*/
	}
	
	public void remove() {
		if (currentNodes.get(null).getNext(null) == null) {
			if (currentNodes.get(null).getPrev(null) != null) {
				currentNodes.get(null).getPrev(null).setNext(null, null);
				
				lastNodes.put(null, currentNodes.get(null).getPrev(null));
				currentNodes.put(null, currentNodes.get(null).getPrev(null));
			}
			else {
				currentNodes.remove(null);
				lastNodes.remove(null);
				startNodes.remove(null);
			}
		}
		else {
			if (currentNodes.get(null).getPrev(null) != null) {
				currentNodes.get(null).getPrev(null).setNext(null, currentNodes.get(null).getNext(null));
				
				currentNodes.get(null).getNext(null).setPrev(null, currentNodes.get(null).getPrev(null));
				
				currentNodes.put(null, currentNodes.get(null).getNext(null));
			}
			else {
				
				currentNodes.get(null).getNext(null).setPrev(null, null);
				
				startNodes.put(null, currentNodes.get(null).getNext(null));
				currentNodes.put(null, currentNodes.get(null).getNext(null));
			}
		}
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
		
		private Collection<I> SubListIndices;
		
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
		
		HashMap<I, Node<T, I>> getNextForAll() {
			return nextNodes;
		}
		
		void setNextForAll(HashMap<I, Node<T, I>> h) {
			nextNodes.putAll(h);
		}
		
		HashMap<I, Node<T, I>> getPrevForAll() {
			return prevNodes;
		}
		
		void setPrevForAll(HashMap<I, Node<T, I>> h) {
			prevNodes.putAll(h);
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
