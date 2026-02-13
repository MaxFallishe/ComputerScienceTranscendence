import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class OrderedListFirstAdditionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCompareRawObjectsAsIllegalComparison() {
        OrderedListFirstAddition<Object> orderedList = new OrderedListFirstAddition<>(true);

        orderedList.compare(new Object(), new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareCharsAsIllegalComparison() {
        OrderedListFirstAddition<Object> orderedList = new OrderedListFirstAddition<>(true);

        orderedList.compare('a', 'a');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareNullsAsIllegalComparison() {
        OrderedListFirstAddition<Object> orderedList = new OrderedListFirstAddition<>(true);

        orderedList.compare(null, null);
    }

    @Test
    public void testComparePositiveNumbersWithLessthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(-1, orderedList.compare(1, 2));
    }

    @Test
    public void testComparePositiveNumbersWithGreaterthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare(100, 20));
    }

    @Test
    public void testComparePositiveNumbersWithEqualResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(0, orderedList.compare(99, 99));
    }

    @Test
    public void testCompareNegativeNumbersWithLessthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(-1, orderedList.compare(-100, -20));
    }

    @Test
    public void testCompareNegativeNumbersWithGreaterthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare(-1, -10));
    }

    @Test
    public void testCompareNegativeNumbersWithEqualResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(0, orderedList.compare(-99, -99));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithLessthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(-1, orderedList.compare(-100, 0));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithGreaterthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare(0, -10));
    }

    @Test
    public void testCompareZerosWithEqualResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(0, orderedList.compare(0, 0));
    }

    @Test
    public void testCompareFloatNumberAndZeroWithGreaterthanResult() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare(0.5, -10));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithLessthanResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(-1, orderedList.compare("abc", "abccc"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithGreaterthanResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare("ABC", "AAA"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithEqualResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(0, orderedList.compare("@cattac!", "@cattac!"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithLessthanResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(-1, orderedList.compare("aa   aa", "aaaa"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithGreaterthanResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(1, orderedList.compare("  ABC   ", "  AAA    "));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithEqualResult() {
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

        assertEquals(0, orderedList.compare(" @cat tac!  ", "   @cat tac! "));
    }

    @Test
    public void testAddOneValueWithAscOrder() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<String> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

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
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

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

    @Test
    public void testRemoveDublicatesOneValueTypeWithAscOrder() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(0);
        orderedList.removeDublicates();

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
    public void testRemoveDublicatesOneValueTypeWithDescOrder() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

        orderedList.add(0);
        orderedList.add(0);
        orderedList.add(0);
        orderedList.removeDublicates();

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
    public void testRemoveDublicatesFewValuesTypeWithAscOrder() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(true);

        orderedList.add(5);
        orderedList.add(5);
        orderedList.add(0);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(-3);
        orderedList.add(-3);
        orderedList.add(-3);
        orderedList.removeDublicates();

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(-3, 0, 1, 5), values);
        assertEquals(-3, orderedList.head.value);
        assertEquals(5, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testRemoveDublicatesFewValuesTypeWithDescOrder() {
        OrderedListFirstAddition<Number> orderedList = new OrderedListFirstAddition<>(false);

        orderedList.add(5);
        orderedList.add(5);
        orderedList.add(0);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(-3);
        orderedList.add(-3);
        orderedList.add(-3);
        orderedList.removeDublicates();

        List<Number> values = orderedList.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(5, 1, 0, -3), values);
        assertEquals(5, orderedList.head.value);
        assertEquals(-3, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }
}

