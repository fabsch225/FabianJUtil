package linearDataStructures;

import java.util.Iterator;

public class MultiLayerLinkedListIterator<T, I> implements Iterator<T>{

	private MultiLayerLinkedList<T, I> multiLayerLinkedList; 
	
	public MultiLayerLinkedListIterator(MultiLayerLinkedList<T, I> l) {
		multiLayerLinkedList = l;
		multiLayerLinkedList.toStart();
	}
	
	@Override
	public boolean hasNext() {
		return multiLayerLinkedList.hasNext();
	}
	
	@Override
	public T next() {
		return multiLayerLinkedList.next();
	}

}