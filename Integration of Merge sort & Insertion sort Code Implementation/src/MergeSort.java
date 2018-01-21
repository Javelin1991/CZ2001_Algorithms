
public class MergeSort {
	
	private long numberOfComparison = 0;
	
	
	public void mergeSort(int first, int last, int[] slot){
	
		int mid = (first + last) / 2;        
	
		if (last-first <= 0) {
			return;
		}
		
		else if (last-first>1) {
			mergeSort(first,mid,slot);
			mergeSort(mid+1,last,slot);
		}
		merge(first,last, mid, slot);

    }
	
	
	
	public void merge(int first, int last, int mid, int[] slot) {
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
