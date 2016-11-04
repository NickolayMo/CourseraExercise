import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] rq;
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        rq = (Item[])(new Object[1]);
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return size;
    }

    public void enqueue(Item item)           // add the item
    {
        if(item == null){
            throw new NullPointerException("Item should not be null");
        }
        if(size == rq.length){
            resize(rq.length * 2);
        }
        rq[size++] = item;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        int rndIndex = StdRandom.uniform(size);
        Item item =  rq[rndIndex];
        rq[rndIndex] = rq[size-1];
        rq[size-1] = null;
        size--;
        if(size > 0 && size == rq.length/4 ){
            resize(rq.length/2);
        }
        return item;

    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        int rndIndex = StdRandom.uniform(size);
        return rq[rndIndex];
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return  new RandomizedQueueIterator();

    }

    private class RandomizedQueueIterator implements Iterator<Item>{

        private int n = size;
        private int[] inds;
        public RandomizedQueueIterator(){
            inds = new int[size];
            for (int i=0; i < size; i++){
                inds[i] = i;
            }
            StdRandom.shuffle(inds);
        }

        @Override
        public boolean hasNext() {
            return n > 0;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NoSuchElementException("Deque is empty");
            }
            return rq[inds[--n]];
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException("remove");
        }
    }

    private void resize(int length){
        Item[] newRq = (Item[])(new Object[length]);
        for (int i = 0; i < size; i++){
            newRq[i] = rq[i];
        }
        rq = newRq;
    }
    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> d = new RandomizedQueue<>();
        System.out.println("enqueue");
        d.enqueue("aaaaa");
        d.enqueue("bbbbb");
        d.enqueue("ccccc");
        d.enqueue("ddddd");
        System.out.println("size == 4 "+ (d.size() == 4));
        for (String s : d){
            System.out.println(s);
        }
        System.out.println("dequeue");
        for (int i = 0,  n = d.size(); i < n; i++){
            System.out.println(d.dequeue());
        }
        System.out.println("size == 0 "+ (d.size() == 0));

    }

}