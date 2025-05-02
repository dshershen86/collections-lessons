import java.util.Iterator;


public class LinkedList<E> implements List<E>, Iterator<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;
    private Node<E> cursor;
    public LinkedList() {

    }


    @Override
    public void add(Object value) {
        Node<E> newNode = new Node<>(value);
        if (first == null) {
            cursor=first = last = newNode;
        }
        else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;

    }

    @Override
    public void add(Object value, int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) {
            add(value);
            return;
        }
        Node<E> newNode = new Node<>(value);

        Node<E> currentNode=findByIndex(index);
        newNode.next=currentNode;
        newNode.prev=currentNode.prev;
        if (currentNode!=first) {
            currentNode.prev.next=newNode;
        }
        currentNode.prev=newNode;

        if (newNode.prev==null) {
            first = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        E result = null;
        if (index == size-1) {
            result=(E)last.value;
            last = last.prev;
            last.next = null;
        }else if(index==0){
            result=(E)first.value;
            first=first.next;
            first.prev=null;
        }else{
            Node<E> currentNode=findByIndex(index);
            currentNode.prev.next=currentNode.next;
            currentNode.next.prev=currentNode.prev;
            result=(E)currentNode.value;
        }
        size--;
        return result;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) {
            return (E)last.value;
        }else if (index==0){
            return (E)first.value;
        }else {
            return((E)findByIndex(index).value);
        }

    }

    @Override
    public E set(Object value, int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        E result = null;
        if (index == size) {
            result=(E)last.value;
            last.value = value;
        }else if (index==0){
            result=(E)first.value;
            first.value = value;
        }else{
            Node<E> currentNode=findByIndex(index);
            result=(E)currentNode.value;
            currentNode.value = value;
        }
        return result;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
        while (hasNext()) {
            E current=next();
            current=null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value)!=-1;
    }

    @Override
    public int indexOf(Object value) {
        int indexResult = 0;
        while (hasNext()) {
            E current=next();
            if (current.equals(value)) {
                return indexResult;
            }
            indexResult++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        int indexResult = -1;
        int index = 0;
        while (hasNext()) {
            E current=next();
            if (current.equals(value)) {
                indexResult=index;
            }
            index++;
        }
        return indexResult;
    }
    @Override
    public boolean hasNext() {
        return cursor!=null;
    }

    @Override
    public E next() {
        if (cursor==null) cursor = first;
        Node<E> prevNode=cursor;
        cursor = cursor.next;
        return (E)prevNode.value;
    }
    private static class Node<E>{
        private Node<E> prev;
        private Node<E> next;
        private Object value;

        private Node() {
        }

        public Node(Object value) {
            this.value = value;
        }

    }
    private Node<E> findByIndexRecursion(int index,Node<E> node,int current) {
        Node<E>result = null;
        boolean isUp = ((double)size/2)>index;
        if ( (isUp && (current == index)) || ((!isUp && current == (size - index - 1)))) {
            if (node==null) {
                return isUp?first:last;
            }
            return node;
        }
        current++;
        if (node==null) {
            result=findByIndexRecursion(index,(isUp)?first.next:last.prev,current);
        }else{
            result=findByIndexRecursion(index,isUp?node.next:node.prev,current);
        }
        return result;
    }
    private Node<E> findByIndex(int index){
        return findByIndexRecursion(index,null,0);
    }
    @Override
    public String toString() {
        String result="[";
        cursor = first;
        while (hasNext()) {
            result += next() + ",";
        }
        result=result.substring(0,result.length()-1);
        result += "]";
        return result;
    }
}
