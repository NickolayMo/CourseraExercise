import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
    private Node first = null;
    private Node last = null;
    private int size = 0;
    private class Node{
        public Item item = null;
        public Node prev = null;
        public Node next = null;
    }

    public Deque()                           // construct an empty deque
    {
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return first == null;
    }
    public int size()                        // return the number of items on the deque
    {
        return size;

    }
    public void addFirst(Item item)          // add the item to the front
    {
        if(item == null){
            throw new NullPointerException("Item should not be null");
        }

        Node newNode = new Node();
        newNode.item = item;
        if(isEmpty()){
            first = last = newNode;
        }else{
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if(item == null){
            throw new NullPointerException("Item should not be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        if(isEmpty()){
            first = last = newNode;
        }else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        if(first.next == null){
            first = last = null;
        }else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        if(last.prev == null){
            first = last = null;
        }else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NoSuchElementException("Deque is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");

        }


    }
    public static void main(String[] args)   // unit testing
    {
        Deque<String> d = new Deque<>();
        d.addFirst("aaaaa");
        d.addFirst("bbbbb");
        d.addLast("ccccc");
        d.addLast("ddddd");
        for (String s : d){
            System.out.println(s);
        }
        System.out.println("size == 4 "+ (d.size() == 4));
        d.removeLast();
        d.removeFirst();
        System.out.println("size == 2 "+ (d.size() == 2));
        for (String s : d){
            System.out.println(s);
        }
        d.removeFirst();
        d.removeFirst();
        try {
            d.removeFirst();
        }catch (NoSuchElementException e){
            System.out.println("Empty deque exception");
        }

        try {
            d.addFirst(null);
        }catch (NullPointerException e){
            System.out.println("null value exception");
        }

        System.out.println("size == 0 "+ (d.size() == 0));




    }


}
