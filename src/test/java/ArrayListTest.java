import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {

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
                add(null);

            }
        };
    }
    @DisplayName("Test add without insert place and get")
    @Test
    void add() {
        list.add( "M");
        assertEquals( "M",list.get(7));
        list.add("nn");
        assertEquals("nn",list.get(8));
        list.add( "OP");
        assertEquals("OP",list.get(9));
    }
    @DisplayName("Test add with insert place and get")
    @Test
    void testAdd() {
        list.add("NO",2);
        assertEquals("NO",list.get(2));
        assertEquals(8,list.size());
        list.add("YES",6);
        assertEquals("YES",list.get(6));
        assertEquals(9,list.size());
    }

    @Test
    void removeByIndex() {
        Object b=list.remove(1);
        assertEquals("B",b);
        assertEquals("C",list.get(1));
        assertEquals(6,list.size());
    }

    @Test
    void get() {
        assertEquals("A",list.get(0));
        assertEquals("C",list.get(2));
        assertEquals("E",list.get(5));
        assertNull(list.get(6));
    }

    @Test
    void set() {
        Object result = list.set("NO",1);
        assertEquals("B",result);
        assertEquals("NO",list.get(1));

        result = list.set("YES",4);
        assertEquals("A",result);
        assertEquals("YES",list.get(4));

        result = list.set(null,2);
        assertEquals("C",result);
        assertNull(list.get(2));

    }

    @Test
    void clear() {
        list.clear();
        assertEquals(0,list.size());

    }

    @Test
    void size() {
        assertEquals(7,list.size());
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
        assertTrue(list.contains(null));
        assertFalse(list.contains("F"));
    }

    @Test
    void indexOf() {
        assertEquals(0,list.indexOf("A"));

        assertEquals(6,list.lastIndexOf(null));
    }

    @Test
    void lastIndexOf() {
        assertEquals(4,list.lastIndexOf("A"));

        assertEquals(6,list.lastIndexOf(null));
    }


}