
/**
 * Implement Hash table.
 *
 * Pick 20 random words.  Each word must be of different lengths,
 * maximum length 8 and minimum length 3.
 *
 * The words will be of letters a-zA-Z and the space character.
 * Insert them into a hash table.
 *
 * You can use a library for only the hash function.
 * The collision resolution scheme should be open addressing - quadratic.
 * Initially the table size is 31.  The program should increase the table size and rehash at load factor of .5
 * At the end print the total number of collisions you get.
 */

public class HashTable {


    private String[] table_array;
    private String[] aux_array;
    int collision_count;
    int element_count = 0;
    int table_size = 31;

    HashTable(){
        this.table_array = new String[31];
        this.collision_count = 0;
    }

    private int hash_func(String s){
        //Jenkins One At A Time Hash
        int i = 0;
        int length = s.length();
        int hash = 0;
        while (i != length) {
            hash += s.charAt(i++);
            hash += hash << 10;
            hash ^= hash >> 6;
        }
        hash += hash << 3;
        hash ^= hash >> 11;
        hash += hash << 15;
        return Math.abs(hash);
    }

    private int new_tableSize(){
        /**
         * return next prime number greater than two times current table size
         */
        int[] prim_list = {31,67,137,277,557};
        for(int i = 0;i < 4;i++){
            if(prim_list[i] > table_size){
                return prim_list[i];
            }
        }
        return -1;
    }

    private void resize_table(){

        int newSize = new_tableSize();
        String[] temp = table_array;
        this.aux_array = new String[newSize];
        this.table_array = this.aux_array;
        int old_size = table_size;
        this.collision_count = 0;
        System.out.println("resize hash table from size "+ old_size + " to " + newSize);
        for(int i = 0;i < old_size;i++){
            if(temp[i] != null) {
                insert_table(temp[i]);
                element_count++;
            }
        }
        this.table_size = newSize;
    }

    private boolean check_input(String s){
        if(s.length() > 8 || s.length() < 3){
            return false;
        }
        return true;
    }

    private int open_addressing(int addr){
        int count = 1;
        while(table_array[addr] != null){
            addr += count * count;
            addr = addr % table_size;
            count++;
            collision_count++;
        }
        return addr;
    }

    private int compute_idx(String s){
        //compute index for hash table

        int idx;
        idx = hash_func(s);
        idx = idx % table_size;
        return idx;
    }

    private void insert_table(String s){

        if(!check_input(s)){
            System.out.println("wrong input");
        }
        if(table_array[compute_idx(s)] != null){
            table_array[open_addressing(compute_idx(s))] = s;
        }else {
            table_array[compute_idx(s)] = s;
        }

    }

    public void insert(String s){
        //for normal insert
        if(++element_count > table_size/2){
            System.out.println("table size :" + element_count);

            this.element_count = 0;
            resize_table();
            insert_table(s);
        }else {
            insert_table(s);
        }

    }

    public int findString(String s){
        /**
         * return index of s in the hash table
         */
        int idx = hash_func(s) % table_size;
        if(table_array[idx] == s){
            return idx;
        }else if(table_array[idx] == null){
            return -1;
        }else{
            int count = 1;
            //int idx = hash_func(s);
            while(table_array[idx] != s){
                idx += count * count;
                idx %= table_size;
                count++;
            }
            return idx;
        }
    }

    public void print_array(){
        int count = 0;
        for(int i = 0;i<table_size;i++){
            System.out.print(i + " :" + table_array[i] + " ");
            if((i % 8) == 0 && (i != 0)){
                System.out.println();
            }
            if(table_array[i] != null){
                count ++;
            }
        }
        System.out.println("\nNumber of elements :"+count);
    }

    public void print_numCollision(){
        System.out.println("number of collision : "+collision_count);
    }

    public static void main(String[] argv){

        HashTable table = new HashTable();

        table.insert("program");table.insert("dog");table.insert("own");
        table.insert("few");table.insert("nonstop");table.insert("windy");
        table.insert("man");table.insert("dazzling");table.insert("wire");
        table.insert("pizzas");table.insert("bob");table.insert("place");
        table.insert("crib");table.insert("tine");table.insert("object");
        table.insert("fine");table.insert("nifty");table.insert("judge");
        table.insert("trip");table.insert("pail");
        table.print_numCollision();
        String find = "fine";
        System.out.println("find string : "+ find +", at index : "+table.findString(find));
        table.print_array();
    }
}
