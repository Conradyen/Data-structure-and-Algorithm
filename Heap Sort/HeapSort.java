/**
 * Implement the heap sort.
 * Given a random list of numbers in an array (index zero stores the number of nodes).
 * Sort them in ascending order.
 * 1. You must first make a heap an then sort it.
 * Initialize an array of random numbers (at least 20 )
 * Print the list.
 * Make a heap
 * Print the list
 * Sort it
 * print the list again.
 * Submit code
 * submit screen shot showing each printout.
 */

public class HeapSort {

    int[] heap;

    public void Heap(int ... nums){
        /**
         * take array as input and make heap
         */
        heap = new int[nums.length+1];
        for(int i = 0;i < nums.length;i++ ){
            heap[i+1] = nums[i];
        }
        heap[0] = nums.length;
    }

    public int getSize(){
        return heap[0];
    }


    private int swapmin(int idx_p,int idx_leftchild,int idx_rightchild){
        /**
         * swap parent with smallest value child
         */
        int swap_idx = (heap[idx_leftchild] > heap[idx_rightchild])? idx_rightchild:idx_leftchild;
        int tmp = heap[idx_p];
        heap[idx_p] = heap[swap_idx];
        heap[swap_idx] = tmp;
        return swap_idx;
    }

    private int swapmax(int idx_p,int idx_leftchild,int idx_rightchild){
        /**
         * swap parent with largest value child
         */
        int swap_idx = (heap[idx_leftchild] > heap[idx_rightchild])? idx_leftchild:idx_rightchild;
        int tmp = heap[idx_p];
        heap[idx_p] = heap[swap_idx];
        heap[swap_idx] = tmp;
        return swap_idx;
    }

    private void swap(int a,int b){
        /**
         * swap value of two indexes
         */
        int tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    private int leftChild(int parentidx){
        /**
         * return idx of left child ,if no left child return 0
         */
        if(parentidx*2 <= heap[0]){
            return parentidx*2;
        }
        return 0;
    }

    private int rightChild(int parentidx){
        /**
         * return index of right chile, if no right child return 0
         */
        if(parentidx*2 +1 <= heap[0]){
            return parentidx*2 + 1;
        }
        return 0;
    }

    public void minHeapify(int idx){
        /**
         * percolate down which value of parent is larger than child
         */

        if(leftChild(idx) > 0 && rightChild(idx) > 0) {
            if (heap[idx] > heap[leftChild(idx)] || heap[idx] > heap[rightChild(idx)]) {
                int swap_idx = swapmin(idx,leftChild(idx),rightChild(idx));
                minHeapify(swap_idx);
            }
        }else if(leftChild(idx) > 0 && rightChild(idx) == 0){
            if(heap[idx] > heap[leftChild(idx)]){
                swap(idx,leftChild(idx));
            }
        }else{
            return;
        }

    }
    public void maxHeapify(int idx){
        /**
         * percolate down which value of parent is smaller than child
         */

        if(leftChild(idx) > 0 && rightChild(idx) > 0) {
            if (heap[idx] < heap[leftChild(idx)] || heap[idx] < heap[rightChild(idx)]) {
                int swap_idx = swapmax(idx,leftChild(idx),rightChild(idx));
                maxHeapify(swap_idx);
            }
        }else if(leftChild(idx) > 0 && rightChild(idx) == 0){
            if(heap[idx] < heap[leftChild(idx)]){
                swap(idx,leftChild(idx));
            }
        }else{
            return;
        }
    }

    public void makeminHeap(){
        /**
         * make array to min heap
         */
        for(int i = heap[0]/2;i > 0;i--){
            minHeapify(i);
        }
    }

    public void makemaxHeap(){
        /**
         * array to max heap
         */
        for(int i = heap[0]/2;i > 0;i--){
            maxHeapify(i);
        }
    }

    public void heapSortdes(){
        /**
         * Sort to descending order with min heap
         */
        if(heap[0] <= 1){
            return;
        }
        swap(1,heap[0]);
        heap[0]--;
        minHeapify(1);
        heapSortdes();
    }

    public void heapSortasc(){
        /**
         * Sort to ascending order with max heap
         */
        if(heap[0] <= 1){
            return;
        }
        swap(1,heap[0]);
        heap[0]--;
        maxHeapify(1);
        heapSortasc();
    }

    public void printHeap(){
        /**
         * print out the heap
         */
        for(int i = 1;i<heap.length;i++){
            System.out.print(heap[i]+" ");
        }
        System.out.print("\n");
    }

    public static void main(String arg[]){
        HeapSort minheap = new HeapSort();
        minheap.Heap(3,2,51,5,43,6,1,31,42,34,23,4,27,12,14,37,35,13,54,32,17);
        System.out.println("Size of list : " + minheap.getSize());
        minheap.printHeap();
        System.out.println("min heap : ");
        minheap.makeminHeap();
        minheap.printHeap();
        System.out.println("Sorted in descending: ");
        minheap.heapSortdes();
        minheap.printHeap();
        HeapSort maxheap = new HeapSort();
        maxheap.Heap(3,2,51,5,43,6,1,31,42,34,23,4,27,12,14,37,35,13,54,32,17);
        System.out.println("Same list : ");
        maxheap.printHeap();
        System.out.println("max heap : ");
        maxheap.makemaxHeap();
        maxheap.printHeap();
        System.out.println("Sorted in ascending order: ");
        maxheap.heapSortasc();
        maxheap.printHeap();
    }
}
