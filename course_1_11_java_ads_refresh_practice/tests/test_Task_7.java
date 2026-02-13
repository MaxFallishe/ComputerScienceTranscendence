import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


// Task number: 7.7
// Short description: Implement tests for OrderedList class.
public class OrderedListTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCompareRawObjectsAsIllegalComparison() {
        OrderedList<Object> orderedList = new OrderedList<>(true);

        orderedList.compare(new Object(), new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareCharsAsIllegalComparison() {
        OrderedList<Object> orderedList = new OrderedList<>(true);

        orderedList.compare('a', 'a');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareNullsAsIllegalComparison() {
        OrderedList<Object> orderedList = new OrderedList<>(true);

        orderedList.compare(null, null);
    }

    @Test
    public void testComparePositiveNumbersWithLessthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(-1, orderedList.compare(1, 2));
    }

    @Test
    public void testComparePositiveNumbersWithGreaterthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare(100, 20));
    }

    @Test
    public void testComparePositiveNumbersWithEqualResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(0, orderedList.compare(99, 99));
    }

    @Test
    public void testCompareNegativeNumbersWithLessthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(-1, orderedList.compare(-100, -20));
    }

    @Test
    public void testCompareNegativeNumbersWithGreaterthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare(-1, -10));
    }

    @Test
    public void testCompareNegativeNumbersWithEqualResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(0, orderedList.compare(-99, -99));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithLessthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(-1, orderedList.compare(-100, 0));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithGreaterthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare(0, -10));
    }

    @Test
    public void testCompareZerosWithEqualResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(0, orderedList.compare(0, 0));
    }

    @Test
    public void testCompareFloatNumberAndZeroWithGreaterthanResult() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare(0.5, -10));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithLessthanResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(-1, orderedList.compare("abc", "abccc"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithGreaterthanResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare("ABC", "AAA"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithEqualResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(0, orderedList.compare("@cattac!", "@cattac!"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithLessthanResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(-1, orderedList.compare("aa   aa", "aaaa"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithGreaterthanResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(1, orderedList.compare("  ABC   ", "  AAA    "));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithEqualResult() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        assertEquals(0, orderedList.compare(" @cat tac!  ", "   @cat tac! "));
    }

    @Test
    public void testAddOneValueWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);

    }

    @Test
    public void testAddFewValuesAsAscSequenceWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(4);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 2, 2, 3, 4, 4), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsDescSequenceWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(4);
        orderedList.add(3);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(1);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 4), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsRandomSequenceWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(1);
        orderedList.add(5);
        orderedList.add(4);
        orderedList.add(5);
        orderedList.add(5);
        orderedList.add(4);
        orderedList.add(2);
        orderedList.add(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 2, 3, 4, 4, 5, 5, 5), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(5, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsComplexRandomSequenceWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(8);
        orderedList.add(6);
        orderedList.add(4);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(7);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 4, 5, 6, 7, 8), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(8, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddOneValueWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsAscSequenceWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(4);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(4, 4, 3, 2, 2, 1), values);
        assertEquals(4, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsDescSequenceWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(4);
        orderedList.add(3);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(1);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(4, 3, 2, 2, 1, 1), values);
        assertEquals(4, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsRandomSequenceWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(1);
        orderedList.add(5);
        orderedList.add(4);
        orderedList.add(5);
        orderedList.add(5);
        orderedList.add(4);
        orderedList.add(2);
        orderedList.add(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(5, 5, 5, 4, 4, 3, 2, 1), values);
        assertEquals(5, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddFewValuesAsComplexRandomSequenceWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(8);
        orderedList.add(6);
        orderedList.add(4);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(7);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(8, 7, 6, 5, 4, 3, 2, 2, 1, 1), values);
        assertEquals(8, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddManyValuesAsComplexRandomSequenceWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(7);
        orderedList.add(6);
        orderedList.add(1);
        orderedList.add(10);
        orderedList.add(1);
        orderedList.add(5);
        orderedList.add(10);
        orderedList.add(-2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(-2, 1, 1, 3, 5, 5, 6, 7, 10, 10), values);
        assertEquals(-2, orderedList.head.value);
        assertEquals(10, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddManyValuesAsComplexRandomSequenceWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(5);
        orderedList.add(7);
        orderedList.add(3);
        orderedList.add(6);
        orderedList.add(10);
        orderedList.add(1);
        orderedList.add(10);
        orderedList.add(6);
        orderedList.add(1);
        orderedList.add(-4);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(10, 10, 7, 6, 6, 5, 3, 1, 1, -4), values);
        assertEquals(10, orderedList.head.value);
        assertEquals(-4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testAddDuplicatesManyValuesWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(-1);
        orderedList.add(-1);
        orderedList.add(2);
        orderedList.add(-2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(2, 1, 1, 0, 0, 0, -1, -1, -2), values);
        assertEquals(2, orderedList.head.value);
        assertEquals(-2, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteExistSoloValueWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        orderedList.delete(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(), values);
        assertNull(orderedList.tail);
        assertNull(orderedList.head);
    }

    @Test
    public void testDeleteNonExistSoloValueWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        orderedList.delete(2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteExistSoloValueWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        orderedList.delete(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(), values);
        assertNull(orderedList.tail);
        assertNull(orderedList.head);
    }

    @Test
    public void testDeleteNonExistSoloValueWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        orderedList.delete(2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteFewSameValuesAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(0);
        orderedList.delete(0);
        orderedList.delete(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteFewSameValuesDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(0);
        orderedList.delete(0);
        orderedList.delete(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteMiddleValuesAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(0);
        orderedList.delete(0);
        orderedList.delete(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 2, 3, 4, 5), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(5, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteMiddleValuesDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(0);
        orderedList.delete(0);
        orderedList.delete(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(5, 4, 3, 2, 1), values);
        assertEquals(5, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithComplexPatternValuesAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(5);
        orderedList.delete(3);
        orderedList.delete(4);
        orderedList.delete(2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 3), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(3, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithComplexPatternValuesDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(5);
        orderedList.delete(3);
        orderedList.delete(4);
        orderedList.delete(2);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(3, 1), values);
        assertEquals(3, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithComplexPatternStringValuesAscOrder() {
        OrderedList<String> orderedList = new OrderedList<>(true);

        orderedList.add("aaa");
        orderedList.add("abb");
        orderedList.add("abb");
        orderedList.add("aaa");
        orderedList.add("bbb");
        orderedList.add("aab");

        orderedList.delete("aaa");
        orderedList.delete("abb");
        orderedList.delete("abb");

        List<String> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of("aaa", "aab", "bbb"), values);
        assertEquals("aaa", orderedList.head.value);
        assertEquals("bbb", orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithComplexPatternStringValuesDescOrder() {
        OrderedList<String> orderedList = new OrderedList<>(false);

        orderedList.add("aaa");
        orderedList.add("abb");
        orderedList.add("abb");
        orderedList.add("aaa");
        orderedList.add("bbb");
        orderedList.add("aab");

        orderedList.delete("aaa");
        orderedList.delete("abb");
        orderedList.delete("abb");

        List<String> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of("bbb", "aab", "aaa"), values);
        assertEquals("bbb", orderedList.head.value);
        assertEquals("aaa", orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithListRecreateAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(5);
        orderedList.delete(3);
        orderedList.delete(4);
        orderedList.delete(2);
        orderedList.delete(1);
        orderedList.delete(3);
        assertNull(orderedList.tail);
        assertNull(orderedList.head);

        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 3), values);
        assertEquals(1, orderedList.head.value);
        assertEquals(3, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testDeleteWithListRecreateDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(1);
        orderedList.add(3);
        orderedList.add(5);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(2);

        orderedList.delete(5);
        orderedList.delete(3);
        orderedList.delete(4);
        orderedList.delete(2);
        orderedList.delete(1);
        orderedList.delete(3);
        assertNull(orderedList.tail);
        assertNull(orderedList.head);

        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(3);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(3, 3, 2, 2, 1, 1), values);
        assertEquals(3, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindExistSoloValueWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        Node<Number> findedNode = orderedList.find(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, findedNode.value);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindExistSoloValueWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        Node<Number> findedNode = orderedList.find(0);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertEquals(0, findedNode.value);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindNonExistSoloValueWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        Node<Number> findedNode = orderedList.find(99);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertNull(findedNode);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindNonExistSoloValueWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        Node<Number> findedNode = orderedList.find(99);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0), values);
        assertNull(findedNode);
        assertEquals(0, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindWithFewValuesWithAscOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(true);

        orderedList.add(0);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(5);

        Node<Number> findedNode = orderedList.find(3);
        assertEquals(3, findedNode.value);

        findedNode = orderedList.find(1);
        assertEquals(1, findedNode.value);

        findedNode = orderedList.find(3);
        assertEquals(3, findedNode.value);

        findedNode = orderedList.find(5);
        assertEquals(5, findedNode.value);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(0, 1, 2, 3, 4, 5), values);
        assertEquals(0, orderedList.head.value);
        assertEquals(5, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testFindWithFewValuesWithDescOrder() {
        OrderedList<Number> orderedList = new OrderedList<>(false);

        orderedList.add(0);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(5);

        Node<Number> findedNode = orderedList.find(3);
        assertEquals(3, findedNode.value);

        findedNode = orderedList.find(1);
        assertEquals(1, findedNode.value);

        findedNode = orderedList.find(3);
        assertEquals(3, findedNode.value);

        findedNode = orderedList.find(5);
        assertEquals(5, findedNode.value);

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(5, 4, 3, 2, 1, 0), values);
        assertEquals(5, orderedList.head.value);
        assertEquals(0, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

}

