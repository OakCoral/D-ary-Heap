import java.util.*;
import java.io.*;

public class Heap {
	
	public static int D, heapSize = 0;
	static int[] Heap = new int[1000];
	
	public static int checkValid() {
		Scanner scan = new Scanner(System.in);
		try {
			int d = scan.nextInt();
			if( d < 2)
				System.out.println("Invalid input - d must be an integer and bigger than 0, please try again.");
			if(d >= 2) {
				D = d;
				return D;
			}
		} catch (Exception e) {
			System.out.println("Invalid input - d must be an integer and bigger than 0, please try again.");
		}
		return 0;
	}
	
	public static int parent(int i) { 
		if(i == 0) {		
		System.out.println("Error : There is no parent for this index. Returning 0 as a default value.");
		return 0;
		} else {
			return (int)Math.ceil((double)i/(double)D)-1;
		}
	}

	public static void maxHeapify(int[] arr, int i) {
		
		if(D*i + 1 >= Math.ceil(Heap.length-1/D)) {
			return;
		}
		
		int largest = i;
		int[] indecies = new int[D];
		for(int k = 0; k < D ; k++) {
			if(D*i + k + 1 < Heap.length) {
				indecies[k] = D*i + k + 1 ;
			}
		}
		for(int l = 0; l < D; l++) {
			if(Heap[indecies[l]] > Heap[largest]) {
				largest = indecies[l];
			}
		}
		if(largest != i) {
			int tmp = Heap[i];
			Heap[i] = Heap[largest];
			Heap[largest] = tmp;
			maxHeapify(arr, largest);
		}
	}
	
	public static int extractMax(int[] arr){
		if(arr.length < 1) {
			System.out.println("Error : Heap underflow.");
			return 0;
		}
		else {
			int max = arr[0];
			arr[0] = arr[heapSize-1];
			arr[heapSize-1] = 0;	
			maxHeapify(arr, 0);
			heapSize-=1;
			System.out.println("The function extract-max() has been executed successfuly.");
			return max;
		}
	}

	public static void increaseKey(int[] arr, int i, int key) {
		int index = i;
		if(key < arr[i]) {
			System.out.println("Error : new key is smaller than current key");
		} else {
			arr[i] = key;
			while((i > 0) && (arr[parent(i)] < arr[i])) {
				int tmp = arr[i];
				arr[i] = arr[parent(i)];
				arr[parent(i)] = tmp;
				i = parent(i);
			}
			System.out.println("The function increase-key(" + index + ", " + key + ") has been executed successfuly.");
		}
	}
	
	public static void insert(int[] arr, int key) {
		if(arr[arr.length-1] != 0)
		{
			System.out.println("Error : Couldn't insert key because heap is already full.");
		} else {
			increaseKey(arr, heapSize, key);
			heapSize += 1;
			System.out.println("The function insert-key(" + key + ") has been executed successfuly.");
		}
	}
	
	public static void delete(int[] arr, int i) {
		increaseKey(arr, i, 1 + arr[0]);
		extractMax(arr);
		System.out.println("Key has been deleted successfully.");
	}
	
	public static void print(int[] arr, int level) {
		if(heapSize == 0) 
		{
			System.out.println("Heap is empty.");
		} 
		else 
		{
			int endOfLevel = (((int)Math.pow(D, level+1) - 1)/(D-1)) - 1 ; 
			int startOfLevel = endOfLevel - (int)Math.pow(D, level) + 1;
			int levelSum = 0;
			int length = arr.length;
			if(startOfLevel >= arr.length || startOfLevel >= heapSize)
				return;
			if(endOfLevel >= heapSize)
				endOfLevel = heapSize-1;
			for(int k = startOfLevel; k < endOfLevel+1; k++) {
				levelSum += arr[k];
			}
			if(levelSum == 0) {
				return;
			}
			for(int k = startOfLevel; k < endOfLevel + 1; k++) {
				if(k==endOfLevel) {
					System.out.print(arr[k] + "\n");
				} else {
					System.out.print(arr[k] + "|");
				}
			}
			print(arr, level + 1);
		}
	}

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to 'Build a D-th Heap' Program!");
		System.out.println("Please enter the value of D");
		
		while(checkValid() == 0) {
			checkValid();
		}
		
		System.out.println("The value of d is : " + D);
		
		System.out.println("Please enter all your commands");
		while(scan.hasNextLine()) {
			String line = scan.nextLine(), command = "unknown command";
			if(line.length() >= 5) {
				command = line.substring(0, 5);
			}
			int key;
			switch ( command ) {
				case "inser" : 
					key = Integer.valueOf(line.substring(7, line.length()));
					insert(Heap, key);
					break;
				case "extra" : 
					extractMax(Heap);
					break;
				case "incre" : 
					String[] str = new String[3];
					int s = 0, indexNum = 0;
					boolean increaseIndex = false;
					for(int i = 0; i < line.length(); i++) {
						String currentChar =  line.substring(i, i+1);
						if(s==0 && currentChar.equals(" ")) {
							str[s] = line.substring(0, i);
							indexNum = i+1;
							increaseIndex = true;
						}
						if(s==1 && currentChar.equals(" ")) {
							str[s] = line.substring(indexNum, i);
							increaseIndex = true;
							str[2] = line.substring(i+1, line.length());
						}
						if(increaseIndex == true) {
							s++;
						}
						increaseIndex = false;
					}
					int index = Integer.valueOf(str[1]);
					key = Integer.valueOf(str[2]);
					increaseKey(Heap, index, key);
					break;
				case "print" : 
					print(Heap, 0);
					break;
				case "delet" : 
					index = Integer.valueOf(line.substring(7, line.length()));
					delete(Heap, index);
					break;				
				default : 
					System.out.println("unknown command.");
			}
		}
		
	}
		
}
