
/**
 There should be at least 15 nodes,. The list should not be sorted.
Traverse the list.
Now sort the list.
The list should be sorted such that your program unlinks the nodes and relinks them so that they are sorted.
 (DO NOT SWAP THE VALUES IN THE NODES).
Traverse the list again.
 */

public class LinkedList {

    ListNode head = null;
    int size = 0;

    public class ListNode {

        int val;
        ListNode next;
        ListNode(int x){
            val = x;
            next = null;
        }

    }

    public void addToHead(int val){
        /**
        add new node with value val to head of list
         */

        ListNode newhead = new ListNode(val);
        newhead.next = head;
        head = newhead;
        size++;

    }
    public void addToTail(int val){
        /**
        add new node to tail
         */
        ListNode newtail = new ListNode(val);
        if(head == null){
            head = newtail;
        }
        ListNode temphead = head;
        while(temphead.next != null){
            temphead = temphead.next;
        }
        temphead.next = newtail;
        size++;
    }

    public void printList(){
        /**
        traverse to print all nodes of the list, start from head
         */
        ListNode tmphead = head;
        while (tmphead != null){
            System.out.print(tmphead.val+" ");
            tmphead = tmphead.next;
        }
        System.out.print("\n");
    }
    public int getSize(){
        /**
        return length of linked list
         */
        return size;
    }

    public ListNode selectionSort(ListNode head) {
        /**
         * Do selection sort on list with list head is head
         * find min -> swap to head
         * next node is selectionSort(next node)
         */

        if (head.next == null){
            return head;
        }
        ListNode pre_min = null;
        ListNode tmp_min = head;
        ListNode current = head;

        while(current .next != null){
            if (current.next.val < tmp_min.val) {
                tmp_min = current.next;
                pre_min = current;
            }
            current = current.next;
        }

        if (tmp_min != head) {
            head = swap(head,tmp_min,pre_min);
        }
        head.next = selectionSort(head.next);

        return head;
    }

    public ListNode swap( ListNode node_1, ListNode node_2, ListNode pre_node2){
        /**
         * swap two nodes
         * node_1 : original head
         * node_2 : min
         * pre_node2 : node before node_2
         */

        ListNode newhead = node_2;
        pre_node2.next = node_1;

        ListNode temp = node_2.next;
        node_2.next = node_1.next;
        node_1.next = temp;

        return newhead;

    }

    public static void main(String[] args){

        LinkedList list = new LinkedList();
        //linked 16 nodes
        list.addToHead(3);
        list.addToHead(18);
        list.addToHead(34);
        list.addToHead(29);
        list.addToHead(20);
        list.addToHead(30);
        list.addToHead(45);
        list.addToHead(33);
        list.addToHead(15);
        list.addToHead(27);
        list.addToHead(39);
        list.addToHead(51);
        list.addToHead(60);
        list.addToHead(23);
        list.addToHead(55);
        list.addToTail(2);
        list.addToTail(23);
        list.addToTail(52);
        list.addToTail(40);
        list.addToTail(36);

        //print list size
        System.out.println("List length :"+list.getSize());
        System.out.println("Before sort :");
        list.printList();
        //do selection sort
        System.out.println("After sort :");
        list.head = list.selectionSort(list.head);
        list.printList();
    }
}
