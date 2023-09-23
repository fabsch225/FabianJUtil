package linearDataStructures;

import java.util.Iterator;

public class LinkedSubListIterator<T, I> implements Iterator<T>{

	private MultiLayerLinkedList<T, I> multiLayerLinkedList; 
	private I SublistIndentifier;
	
	public LinkedSubListIterator(MultiLayerLinkedList<T, I> l, I i) {
		multiLayerLinkedList = l;
		multiLayerLinkedList.checkNull(i);
		SublistIndentifier = i;
		multiLayerLinkedList.toStart(i);
	}
	
	@Override
	public boolean hasNext() {
		return multiLayerLinkedList.hasNext(SublistIndentifier);
	}
	
	@Override
	public T next() {
		return multiLayerLinkedList.next(SublistIndentifier);
	}

	@Override
	public void remove() {
		multiLayerLinkedList.remove(SublistIndentifier);
	}
}
