import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;


public class LinkedList<E> implements List<E>, Iterable<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;
    public LinkedList() {
    }

    @Override
    public void add(Object value) {
        final Node<E> newNode = new Node<>(value);
        linkedLast(newNode);
        size++;
    }

    @Override
    public void add(E value, int index) {
        validateIndex(index);
        if (isLast(index)) {
            add(value);
            return;
        }
        final Node<E> currentNode = findByIndex(index);
        if (currentNode != null) {
            linkedBefore(currentNode, value);
            size++;
        }
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        if (isLast(index)) {
            size--;
            return unLinkLast();
        } else if (isFirst(index)) {
            size--;
            return unLinkFirst();
        }else{
            final Node<E> currentNode=findByIndex(index);
            size--;
            return unLinkMiddle(Objects.requireNonNull(currentNode));
        }
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        if (isLast(index)) {
            return (E)last.value;
        } else if (isFirst(index)) {
            return (E)first.value;
        }else {
            return((E) Objects.requireNonNull(findByIndex(index)).value);
        }

    }

    @Override
    public E set(Object value, int index) {
        validateIndex(index);
        E result;
        if (isLast(index)) {
            result=(E)last.value;
            last.value = value;
        }else if (isFirst(index)) {
            result=(E)first.value;
            first.value = value;
        }else{
            final Node<E> currentNode=Objects.requireNonNull(findByIndex(index));
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
    public int lastIndexOf(Object value) {
        int indexResult = -1;
        int index = 0;
        if (value == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.value == null) {
                    indexResult = index;
                }
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (value.equals(x.value)) {
                    indexResult = index;
                }
                index++;
            }
        }
        return indexResult;
    }

    @Override
    public int indexOf(Object value) {

        int index = 0;
        if (value == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.value == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (value.equals(x.value)) {
                    return index;
                }
                index++;
            }
        }
        return -1;

    }

    private Node<E> findByIndex(int index) {
        int resultIndex = -1;
        if (size / 2 > index >> 1) {
            for (Node<E> node = first; node != null; node = node.next) {
                resultIndex++;
                if (resultIndex == index) {
                    return node;
                }
            }
            return null;
        } else {
            for (Node<E> node = first; node != null; node = node.prev) {
                resultIndex++;
                if (resultIndex == index) {
                    return node;
                }
            }
            return new Node<>(null);
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
    }


    private E unLinkLast() {
        E result = (E) last.value;
        last = last.prev;
        last.next = null;
        size--;
        return result;
    }

    private E unLinkFirst() {
        E result = (E) first.value;
        first = first.next;
        first.prev = null;
        size--;
        return result;
    }

    private E unLinkMiddle(Node<E> currentNode) {

        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        return (E) currentNode.value;
    }

    private boolean isLast(int index) {
        return index == size - 1;
    }

    private boolean isFirst(int index) {
        return index == 0;
    }

    private void linkedBefore(Node<E> currentNode, E valueNode) {
        final Node<E> newNode = new Node<>(valueNode);

        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        if (currentNode != first) {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;

        if (newNode.prev == null) {
            first = newNode;
        }
    }

    private void linkedLast(Node<E> newNode) {
        if (isFirst(size)) {
            first = last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
    }
    private static class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        private Object value;

        public Node(Object value) {
            this.value = value;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> currentNode = first;
            private boolean checkRemoved = true;

            @Override
            public boolean hasNext() {
                return currentNode.next != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E result = (E) currentNode.value;
                currentNode = currentNode.next;
                checkRemoved = false;
                return result;
            }

            @Override
            public void remove() {
                if (checkRemoved) throw new IllegalStateException();
                Node<E> nodeBefore = currentNode.prev;
                currentNode.prev = currentNode.next;
                currentNode.next = nodeBefore;

            }
        };
    }
    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(",", "[", "]");
        for (E e : this) {
            result.add(String.valueOf(e));
        }
        return result.toString();
    }
}
