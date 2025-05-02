import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest<E> {
    public ArrayList<String> list;
    @BeforeEach
    void setUp() {
        list = new ArrayList<>(){
            {
                add("A");
                add("B");
                add("C");
                add("D");
                add("A");
                add("E");

            }
        };

    }
    @DisplayName("Test add without insert place and get")
    @Test
    void add() {
        list.add((E) "M");
        assertEquals((E) "M",list.get(6));
        list.add((E) "nn");
        assertEquals((E) "nn",list.get(7));
        list.add((E) "OP");
        assertEquals((E) "OP",list.get(8));
    }
    @DisplayName("Test add with insert place and get")
    @Test
    void testAdd() {
        list.add("NO",2);
        assertEquals("NO",list.get(2));
        assertEquals(7,list.size());
        list.add("YES",6);
        assertEquals("YES",list.get(6));
        assertEquals(8,list.size());
    }

    @Test
    void removeByIndex() {
        Object b=list.remove(1);
        assertEquals("B",b);
        assertEquals("C",list.get(1));
        assertEquals(5,list.size());
    }

    @Test
    void get() {
        assertEquals("A",list.get(0));
        assertEquals("B",list.get(1));
        assertEquals("C",list.get(2));
    }

    @Test
    void set() {
        Object result = list.set("NO",1);
        assertEquals("B",result);
        assertEquals("NO",list.get(1));
        assertEquals(6,list.size());
        result = list.set("YES",4);
        assertEquals("A",result);
        assertEquals("YES",list.get(4));
        assertEquals(6,list.size());
    }

    @Test
    void clear() {
        list.clear();
        assertEquals(0,list.size());
        assertNull(list.get(0));
    }

    @Test
    void size() {
        assertEquals(6,list.size());
    }

    @Test
    void isEmpty() {
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(list.contains("A"));
        assertFalse(list.contains("F"));
    }

    @Test
    void indexOf() {
        assertEquals(0,list.indexOf("A"));
    }

    @Test
    void lastIndexOf() {
        assertEquals(4,list.lastIndexOf("A"));
    }

    @Test
    void hasNextAndNext() {
        assertTrue(list.hasNext());
        assertEquals("A",list.next());
        assertEquals("B",list.next());
    }

}
