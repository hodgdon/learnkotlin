import org.junit.Assert.*
import org.junit.Test
import kotlin.ranges.downTo

class LinkedListTest {
    @Test
    fun prependTest() {
        var listItem : list.ListItem<Int>? = null
        for(i in 100 downTo 1) {
            listItem = list.prepend(listItem, i)
        }

        val listStart = listItem

        listItem = listStart
        for(i in 1..100) {
            assertNotNull(listItem)
            assertEquals(i, listItem!!.value);
            listItem = listItem.next
        }
    }

    @Test
    fun appendTest() {
        var listItem : list.ListItem<Int>? = null
        for(i in 1..100) {
            listItem = list.append(listItem,i)
        }

        val listStart = listItem

        listItem = listStart
        for(i in 1..100) {
            assertNotNull(listItem)
            assertEquals(i, listItem!!.value)
            listItem = listItem.next
        }
    }

    @Test
    fun lengthTest() {
        var listItem : list.ListItem<Int>? = null
        assertEquals(0, list.length(listItem))
        for(i in 1..100) {
            listItem = list.append(listItem,i)
            assertEquals(i,list.length(listItem))
        }
    }

    @Test
    fun compareTest() {
        var listAItem : list.ListItem<Int>? = null
        var listBItem : list.ListItem<Int>? = null
        for(i in 1..100) {
            listAItem = list.append(listAItem,i)
            listBItem = list.append(listBItem,i)
        }
        val areEqual = list.compare(listAItem, listBItem, {a:Int, b:Int -> a==b})
        assertTrue(areEqual)
    }

    @Test
    fun copyTest() {

        var listAItem : list.ListItem<Int>? = null
        for(i in 1..100) {
            listAItem = list.append(listAItem,i)
        }
        var listBItem = list.copy(listAItem)
        assertEquals(list.length(listAItem), list.length(listBItem))
        assertTrue(list.compare(listAItem, listBItem, {a:Int, b:Int -> a==b}))
    }

    @Test
    fun valueAtTest() {
        var listA : list.ListItem<Int>? = null

        assertNull(list.valueAt(listA,0));
        assertNull(list.valueAt(listA,4));

        for(i in 1..10) {
            listA = list.append(listA,i)
            for(j in 0..(i-1)) {
                val v = list.valueAt(listA,j)
                assertNotNull(v)
                assertEquals(j+1, v)
            }
            assertNull(list.valueAt(listA,i))
        }
    }

    @Test
    fun containsTest() {
        val integers = list.listify(Array(10, { i -> i + 1}))
        assertEquals(10, list.length(integers))

        for(i in 1..10) {
            assertTrue(list.contains(integers,i))
        }
        assertFalse(list.contains<Int>(integers, {x -> x > 10 || x <= 0}))
    }

    @Test
    fun transformTest() {
        val integers = list.listify(Array(10, { i -> i + 1}))
        assertEquals(10, list.length(integers))

        val integersAsStrings = list.transform(integers, {x : Int-> x.toString()})
        val expectedResults = list.listify(arrayOf("1","2","3","4","5","6","7","8","9","10"))
        assertTrue(list.compare(expectedResults,integersAsStrings, {a:String, b:String -> a.equals(b) }))
    }

    @Test
    fun removeFirstTest() {
        val originalList = list.listify(arrayOf(1,2,3,4,5))
        assertEquals(5, list.length(originalList))

        val removed3 = list.removeFirst(originalList, 3)
        assertTrue(removed3.success)
        assertEquals(4, list.length(removed3.list))

        val removed1 = list.removeFirst(removed3.list, 1)
        assertTrue(removed1.success)
        assertEquals(3, list.length(removed1.list))

        val removing1Again = list.removeFirst(removed1.list, 1)
        assertFalse(removing1Again.success)
    }

}