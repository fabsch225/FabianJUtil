package linearDataStructures;

import java.util.ArrayList;
import java.util.Iterator;

public class mlall_demo {
	public static void main(String[] args) {
		MultiLayerLinkedList<String, Integer> x = new MultiLayerLinkedList<String, Integer>();

		x.addContent(0, "Hello");
		x.addContent("World");
		x.addContent(0, "!");
		x.addContent(1, "This");
		
		ArrayList<Integer> multipleSublistIndices = new ArrayList<Integer>();
		multipleSublistIndices.add(1);
		multipleSublistIndices.add(2);
		
		x.addContent(multipleSublistIndices, "MultiLayeredLinkedList");
		
		x.addContent(multipleSublistIndices, "is");
		
		x.addContent(multipleSublistIndices, "a");
		x.addContent(1, "Demonstration");
		x.addContent(1, "for a");
		

		x.addContent(multipleSublistIndices, "linked list, in which there are multiple Sublists.");
		
		System.out.println(x.toString());
		
		System.out.println();
		
		Iterator<String> iter = x.iterator();		
		
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		System.out.println();
		
		x.toStart(1);
		x.next(1);
		
		
		x.removeDeep(1);
		
		Iterator<String> iter2 = x.iterator(1);	
		
		x.toStart(1);
		while(iter2.hasNext()) {
			System.out.println(iter2.next());
		}
		
		System.out.println();
		
		x.toStart();
		x.remove();
		x.remove();
		x.remove();
		
		System.out.println(x.toString());
	}
}
