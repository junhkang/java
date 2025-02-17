import java.util.Random;


public class Project2problem3 {

	private static int[] arr;
	private static int[] arrCopy;
        private static int size;
	private static Random randomGenerator;

	private static void printArray(int[] arr) {
		System.out.print(" [" + arr[0]);
		for (int i = 1; i < 25; i++) {
			System.out.print(", " + arr[i]);
		}
		System.out.println("]");
	}

	private static void printData(int totalTrials, int range, int size, long duration, long comparisons) {
		System.out.println("Trial " + totalTrials + " Completed: Range(" + range + ") " + "Size(" + size
				+ ") = Duration(" + duration + ") Comparisons(" + comparisons + ")");
	}
        
 public static void maxheapify(int i, int n) { 
	   // Pre: the left and right subtrees of node i are max heaps.
	   // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;
        
        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;
        
        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }
    
    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t; 
   }
    
    public static int heapsort(){
    	int comparisons = 0;
// Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);
         comparisons ++;
        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            comparisons ++;
            maxheapify(0, i);     // adjust heap
        
        }
        return comparisons;
    }
 public static int insertSort(int left, int right) {
		int comparisons = 0;
		// insertSort the subarray arr[left, right]
		int i, j;
		for (i = left + 1; i <= right; i++) {
			int temp = arr[i]; // store a[i] in temp
			j = i; // start shifts at i
			// until one is smaller,
			while (j > left && arr[j - 1] >= temp) {
				comparisons++;
                                
				arr[j] = arr[j - 1]; // shift item to right
				--j; // go left one position
			}
			arr[j] = temp; // insert stored item
		} // end for

		return comparisons;
	} // end insertSort()
private static int mergesort(int low, int high, boolean smartSort, boolean insertSort) {
		int comparisons = 0;

		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;

			// Sort the left side of the array
			if ((smartSort && !isSorted(low, middle)) || !smartSort) {
				// If subarray is 100 elements or greater call merge sort
				if ((insertSort && middle - low >= 100) || !insertSort) {
					comparisons += mergesort(low, middle, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, middle);
				}
			}

			// Sort the right side of the array
			if ((smartSort && !isSorted(middle + 1, high)) || !smartSort) {
				// If subarray is 100 elements or greater call merge sort
				if ((insertSort && high - middle + 1 >= 100) || !insertSort) {
					comparisons += mergesort(middle + 1, high, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, middle);
				}
			}

			// Combine them both
			comparisons += merge(low, middle, high);
		}

		return comparisons;
	}
private static int merge(int low, int middle, int high) {
		int comparisons = 0;

		// Copy first part into the arrCopy array
		for (int i = low; i <= middle; i++) {
			arrCopy[i] = arr[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;

		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			comparisons++;
			if (arrCopy[i] <= arr[j]) {
				arr[k] = arrCopy[i];
				i++;
			} else {
				arr[k] = arr[j];
				j++;
			}
			k++;
		}

		// Copy the rest of the left part of the array into the target array
		while (i <= middle) {
			arr[k] = arrCopy[i];
			k++;
			i++;
		}

		return comparisons;
	}
        // gives 2 conditions to turn it off/on
	private static int quicksort(int low, int high, boolean smartSort, boolean insertSort) {
		int comparisons = 0;

		int i = low, j = high;

		// Get the pivot element from the middle of the list
		int pivot = arr[(high + low) / 2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) {
				comparisons++;
				i++;
			}

			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) {
				comparisons++;
				j--;
			}

			// If we have found a value in the left list which is larger than
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) {
				exchange(i, j);
				i++;
				j--;
			} else if (i == j) {
				i++;
				j--;
			}
		}

		// Recursion
		if (low < j) {
			if ((smartSort && !isSorted(low, j)) || !smartSort) {
				// If subarray is 100 elements or greater call quick sort
				if ((insertSort && j - low >= 100) || !insertSort) {
					comparisons += quicksort(low, j, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, j);
				}
			}
		}
		if (i < high) {
			if ((smartSort && !isSorted(i, high)) || !smartSort) {
				// If subarray is 100 elements or greater call quick sort
				if ((insertSort && high - i >= 100) || !insertSort) {
					comparisons += quicksort(i, high, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(i, high);
				}
			}
		}

		return comparisons;
	}

        // check whether the array is sorted or not by using isSorted
	private static boolean isSorted(int low, int high) {
		for (int x = low; x < high - 1; x++) {
			if (arr[x] >= arr[x + 1]) {
				return false;
			}
		}

		return true;
	}

	

	public static void main(String[] args) {
		randomGenerator = new Random();

		long start;
		long finish;
		int size;
		int range;
		int numTrials = 100;
		long totalDuration = 0;
		int totalTrials = 0;
		long totalComparisons = 0;

		// Do merge sort for different ranges of integer values as instructions
		for (int currIntRange = 0; currIntRange < 2; currIntRange++) {
			if (currIntRange == 0) {
				range = 1000; // case 1
			} else {
				range = 1000000; // case 2
			}

			// Do merge sort for different sizes of integer values as instructions
			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000; // case 1
				} else if (currIntSize == 1) {
					size = 2000000; // case 2
				} else {
					size = 4000000; // case 3
				}

				// Do merge sort with current size and current range of integer with 100 trials 
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort
					int comparisons = heapsort();
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;
                                        // if we want to see detail processes in each trials, activate printData or erase it.
					// printData(totalTrials, range, size, duration, comparisons);
				}
			}
		}

		long avgDuration = totalDuration / totalTrials;
		long avgComparisons = totalComparisons / totalTrials;
		System.out.println("heapsort() Average duration(" + avgDuration + ") Average comparisons(" + avgComparisons + ")");

		totalTrials = 0;
		totalDuration = 0;
		totalComparisons = 0;   // reset for other sort

              

	
	

	

		
		// Do merge sort for different ranges of integer values as instructions
		for (int currIntRange = 0; currIntRange < 2; currIntRange++) {
			if (currIntRange == 0) {
				range = 1000; // case 1
			} else {
				range = 1000000; // case 2
			}

			
			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000;
				} else if (currIntSize == 1) {
					size = 2000000;
				} else {
					size = 4000000;
				}

				
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort with changing the bolean (turn off/ on the condition)
					int comparisons = mergesort(0, size - 1, true, true); // best case in problem 2
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;
                            System.out.println("mergesort() with best case in problem 2 Average duration(" + avgDuration + ") Average comparisons(" + avgComparisons + ")");
				}
			}
		}
               
		

		totalTrials = 0;
		totalDuration = 0;
		totalComparisons = 0;

		// Do merge sort for different ranges of integer values as instructions
		for (int currIntRange = 0; currIntRange < 2; currIntRange++) {
			if (currIntRange == 0) {
				range = 1000; // case 1
			} else {
				range = 1000000; // case 2
			}

			// Do merge sort for different sizes of integer values as instructions
			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000; // case 1
				} else if (currIntSize == 1) {
					size = 2000000; // case 2
				} else {
					size = 4000000; // case 3
				}


				// Do merge sort with current size and current range of integer
				// values 100 times
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort
					int middle = (size - 1) / 2;
                                        // changing boleans to turn on/off the condition
					int comparisons = quicksort(0, size - 1, false, true);
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;
                                        // if we want to see detail processes in each trials, activate printData or erase it.
					//printData(totalTrials, range, size, duration, comparisons);
				
			}
		}

		avgDuration = totalDuration / totalTrials;
		avgComparisons = totalComparisons / totalTrials;
		System.out.println("quicksort() with best case in problem 2 Average duration(" + avgDuration + ") Average comparisons(" + avgComparisons + ")");}
	}
}
// I brought some codes from my previous problems. 
// the best cases from problem2 is both using smartsort and insertsort.
// from the result, quicksort with both smartsort and insertsort is fastest
// 2nd is merge sort with both smartsort and insertsort
// next is heapsort and built-in array sort.