import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class OrderedListFifthAdditionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCompareRawObjectsAsIllegalComparison() {
        OrderedListFifthAddition<Object> orderedList = new OrderedListFifthAddition<>(true);

        orderedList.compare(new Object(), new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareCharsAsIllegalComparison() {
        OrderedListFifthAddition<Object> orderedList = new OrderedListFifthAddition<>(true);

        orderedList.compare('a', 'a');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareNullsAsIllegalComparison() {
        OrderedListFifthAddition<Object> orderedList = new OrderedListFifthAddition<>(true);

        orderedList.compare(null, null);
    }

    @Test
    public void testComparePositiveNumbersWithLessthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(-1, orderedList.compare(1, 2));
    }

    @Test
    public void testComparePositiveNumbersWithGreaterthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare(100, 20));
    }

    @Test
    public void testComparePositiveNumbersWithEqualResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(0, orderedList.compare(99, 99));
    }

    @Test
    public void testCompareNegativeNumbersWithLessthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(-1, orderedList.compare(-100, -20));
    }

    @Test
    public void testCompareNegativeNumbersWithGreaterthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare(-1, -10));
    }

    @Test
    public void testCompareNegativeNumbersWithEqualResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(0, orderedList.compare(-99, -99));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithLessthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(-1, orderedList.compare(-100, 0));
    }

    @Test
    public void testCompareNegativeNumberAndZeroWithGreaterthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare(0, -10));
    }

    @Test
    public void testCompareZerosWithEqualResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(0, orderedList.compare(0, 0));
    }

    @Test
    public void testCompareFloatNumberAndZeroWithGreaterthanResult() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare(0.5, -10));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithLessthanResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(-1, orderedList.compare("abc", "abccc"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithGreaterthanResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare("ABC", "AAA"));
    }

    @Test
    public void testCompareStringsWithoutExtraSpacesWithEqualResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(0, orderedList.compare("@cattac!", "@cattac!"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithLessthanResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(-1, orderedList.compare("aa   aa", "aaaa"));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithGreaterthanResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(1, orderedList.compare("  ABC   ", "  AAA    "));
    }

    @Test
    public void testCompareStringsWithExtraSpacesWithEqualResult() {
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

        assertEquals(0, orderedList.compare(" @cat tac!  ", "   @cat tac! "));
    }

    @Test
    public void testAddOneValueWithAscOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<String> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

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
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

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

    @Test
    public void testMergeListSameListsWithAscOrder() {
        OrderedListFifthAddition<Number> lst1 = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> lst2 = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> merger = new OrderedListFifthAddition<>(true);

        lst1.add(1);
        lst1.add(1);
        lst1.add(3);
        lst1.add(3);

        lst2.add(2);
        lst2.add(2);
        lst2.add(4);
        lst2.add(4);

        List<Number> lst1Values = lst1.getAll()
                .stream()
                .map(node -> node.value)
                .toList();
        List<Number> lst2Values = lst2.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 3, 3), lst1Values);
        assertEquals(List.of(2, 2, 4, 4), lst2Values);

        OrderedListFifthAddition<Number> result = merger.mergeList(lst1, lst2);
        List<Number> resultValues = result.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 3, 4, 4), resultValues);
        assertNull(result.tail.next);
        assertNull(result.head.prev);
    }

    @Test
    public void testMergeListSameListsWithDescOrder() {
        OrderedListFifthAddition<Number> lst1 = new OrderedListFifthAddition<>(false);
        OrderedListFifthAddition<Number> lst2 = new OrderedListFifthAddition<>(false);
        OrderedListFifthAddition<Number> merger = new OrderedListFifthAddition<>(true);

        lst1.add(1);
        lst1.add(1);
        lst1.add(3);
        lst1.add(3);

        lst2.add(2);
        lst2.add(2);
        lst2.add(4);
        lst2.add(4);

        List<Number> lst1Values = lst1.getAll()
                .stream()
                .map(node -> node.value)
                .toList();
        List<Number> lst2Values = lst2.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(3, 3, 1, 1), lst1Values);
        assertEquals(List.of(4, 4, 2, 2), lst2Values);

        OrderedListFifthAddition<Number> result = merger.mergeList(lst1, lst2);
        List<Number> resultValues = result.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 3, 4, 4), resultValues);
        assertNull(result.tail.next);
        assertNull(result.head.prev);
    }

    @Test
    public void testMergeListSameListsWithMixOrder() {
        OrderedListFifthAddition<Number> lst1 = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> lst2 = new OrderedListFifthAddition<>(false);
        OrderedListFifthAddition<Number> merger = new OrderedListFifthAddition<>(true);

        lst1.add(1);
        lst1.add(1);
        lst1.add(3);
        lst1.add(3);
        lst1.add(5);
        lst1.add(5);

        lst2.add(2);
        lst2.add(2);
        lst2.add(4);
        lst2.add(4);

        List<Number> lst1Values = lst1.getAll()
                .stream()
                .map(node -> node.value)
                .toList();
        List<Number> lst2Values = lst2.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 3, 3, 5, 5), lst1Values);
        assertEquals(List.of(4, 4, 2, 2), lst2Values);

        OrderedListFifthAddition<Number> result = merger.mergeList(lst1, lst2);
        List<Number> resultValues = result.getAll()
                .stream()
                .map(node -> node.value)
                .toList();

        assertEquals(List.of(1, 1, 2, 2, 3, 3, 4, 4, 5, 5), resultValues);
        assertNull(result.tail.next);
        assertNull(result.head.prev);
    }


    @Test
    public void testIsSublistFewValuesWithAscOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> sublist = new OrderedListFifthAddition<>(true);

        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);

        sublist.add(2);
        sublist.add(2);
        sublist.add(3);
        sublist.add(4);

        boolean result = orderedList.isSublist(sublist);

        assertTrue(result);
        assertEquals(1, orderedList.head.value);
        assertEquals(4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testIsSublistFewValuesWithDescOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);
        OrderedListFifthAddition<Number> sublist = new OrderedListFifthAddition<>(true);

        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);

        sublist.add(2);
        sublist.add(2);
        sublist.add(3);
        sublist.add(4);

        boolean result = orderedList.isSublist(sublist);

        assertTrue(result);
        assertEquals(4, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testIsSublistNonExistingWithAscOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> sublist = new OrderedListFifthAddition<>(true);

        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);

        sublist.add(2);
        sublist.add(2);
        sublist.add(3);
        sublist.add(4);
        sublist.add(4);

        boolean result = orderedList.isSublist(sublist);

        assertFalse(result);
        assertEquals(1, orderedList.head.value);
        assertEquals(4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testIsSublistSoloValueExistingWithAscOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);
        OrderedListFifthAddition<Number> sublist = new OrderedListFifthAddition<>(true);

        orderedList.add(2);

        sublist.add(2);

        boolean result = orderedList.isSublist(sublist);

        assertTrue(result);
        assertEquals(2, orderedList.head.value);
        assertEquals(2, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testGetMostFrequentValueInComplexListWithAscOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(true);

        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(4);
        orderedList.add(4);
        orderedList.add(4);


        Number result = orderedList.getMostFrequentValue();

        assertEquals(4, result);
        assertEquals(1, orderedList.head.value);
        assertEquals(4, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testGetMostFrequentValueInComplexListWithDescOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(1);
        orderedList.add(2);
        orderedList.add(2);
        orderedList.add(3);
        orderedList.add(4);
        orderedList.add(4);
        orderedList.add(4);
        orderedList.add(4);


        Number result = orderedList.getMostFrequentValue();

        assertEquals(4, result);
        assertEquals(4, orderedList.head.value);
        assertEquals(1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }

    @Test
    public void testGetMostFrequentValueInSmallListWithDescOrder() {
        OrderedListFifthAddition<Number> orderedList = new OrderedListFifthAddition<>(false);

        orderedList.add(1);
        orderedList.add(-1);

        Number result = orderedList.getMostFrequentValue();

        assertEquals(-1, result);
        assertEquals(1, orderedList.head.value);
        assertEquals(-1, orderedList.tail.value);
        assertNull(orderedList.tail.next);
        assertNull(orderedList.head.prev);
    }
}

