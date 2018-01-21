
public class MergeInsertionSort {
	
	private long numberOfComparison = 0;
	
	public void mergeInsertionSort(int first, int last, int[] slot, int threshold){
		
		int mid = (first + last) / 2;
		if (last-first > threshold) {
		
			mergeInsertionSort(first,mid,slot,threshold);
			mergeInsertionSort(mid+1,last,slot,threshold);
			merge(first,mid,last, slot);
		}
		else {
			insertionSort(slot, first, last);
		}
	}
	
	public void merge(int first, int mid, int last, int[] slot) {
		int a = first, b = mid + 1, i, tmp;
		if (last - first <= 0) {
			return;
		}
		while (a <= mid && b <= last) {
			if (slot[a] > slot[b]) { 
				numberOfComparison++;
				tmp = slot[b++];
				for (i = ++mid; i > a; i--) {
					slot[i] = slot[i - 1];
				}
				slot[a++] = tmp;
			} else if (slot[a] < slot[b]) {
				numberOfComparison++;
				a++;
			}
			else { 
				if (a == mid && b == last) {
					break;
				}
				numberOfComparison++;
				tmp = slot[b++];
				a++;
				for (i = ++mid; i > a; i--) {
					slot[i] = slot[i - 1];
				}
				slot[a++] = tmp;
			}
		}
	}
	
	
	public void insertionSort(int[] slot, int first, int last) {
		
		for(int i=first; i < last; i++) {
			
			for(int j=i+1; j > first; j--) {
				numberOfComparison++;
				
				if(slot[j-1] > slot[j]) {
					swap(slot, j-1, j);
				}else {
					break;
				}
			}
		}
    }
	
	public void swap(int[] slot, int x, int y){
		
		int temp = slot[x];
		slot[x] = slot[y];
		slot[y] = temp; 
	}

	
	public long getNumberOfComparison() {
		long count;
		count = numberOfComparison;
		numberOfComparison = 0;
		return count;
	}
	
	public void resetNumberOfComparison() {
		numberOfComparison = 0;
	}
	
	
}
