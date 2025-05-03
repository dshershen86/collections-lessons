import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayList<T> implements List<T>, Iterable<T> {
    private Object[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROW_UP = 1.5;
    private int size;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        this(null, initialCapacity);
    }

    public ArrayList(T[] newElements, int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        elements= new Object[initialCapacity>0?initialCapacity:DEFAULT_CAPACITY];
        if(newElements!=null){
            elements=Arrays.copyOf(newElements,newElements.length);
            size = newElements.length;
        }
    }
    private Object[] grow() {
        if(elements.length>0){
            size=(int)(elements.length*GROW_UP);
            return Arrays.copyOf(elements,size);
        }else {
             return new Object[DEFAULT_CAPACITY];
        }

    }
    @Override
    public void add(Object value) {
        add(value,size);
    }

    @Override
    public void add(Object value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (elements.length == size) {
            elements=grow();
        }
        System.arraycopy(elements, index,elements,index+1,size-index);
        elements[index]=value;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T result = (T)elements[index];
        System.arraycopy(elements, index+1,elements,index,size-index-1);
        size--;
        elements[size]=null;
        return result;
    }

    @Override
    public T get(int index) {
        if (index < size && index >= 0) {
            return (T)elements[index];
        }
        return null;
    }

    @Override
    public T set(Object value, int index) {
        if (index < size && index >= 0) {
            Object result = elements[index];
            elements[index] = value;
            return (T)result;
        }
        return null;
    }

    @Override
    public void clear() {
        for(Object element : elements) {
            element = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value)>=0;
    }

    @Override
    public int indexOf(T value) {
        for(int i=0;i<size;i++){
            if (elements[i]!=null) {
                if (elements[i].equals(value)) {
                    return i;
                }
            }
            else if (elements[i]==null&&value==null){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        int result = 0;
        boolean found = false;
        for(int i=0;i<size;i++){
            if (elements[i]!=null) {
                if (elements[i].equals(value)) {
                    found = true;
                    result=i;
                }
            }
            else if (elements[i]==null&&value==null){
                found = true;
                result=i;
            }

        }
        return found?result:-1;
    }

    @Override
    public String toString() {
        return "["+Arrays.stream(elements).limit(size).map(Object::toString).collect(Collectors.joining(","))+"]";

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor=0;
            private boolean checkRemoved = true;

            @Override
            public boolean hasNext() {
                return cursor!=size;
            }

            @Override
            public T next() {
                if (cursor >= size)
                    throw new NoSuchElementException();
                T result = (T) elements[cursor];
                cursor++;
                checkRemoved = false;
                return result;
            }

            @Override
            public void remove() {
                if (checkRemoved) throw new IllegalStateException();
                checkRemoved = true;
                cursor--;
            }
        };
    }
}
