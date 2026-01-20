import java.lang.reflect.Array;
import java.util.Arrays;


// Task number: 3.7
// Short description: Implement Multidimensional Dynamic Array
// Reflection:
// For me, the practice with a multidimensional dynamic array turned out to be more like a study of how this can be done,
// unfortunately, I might like to focus a little more at the moment on the implementation itself with already a lot of
// limitations, perhaps even a boiler plate of the code. The idea of deploying multiple nested dimensions in one straigh
// t line looks very strong, it's not in my solution, but I can well admit that it is stronger and more effective than
// just nested arrays (they have much more problems with expansion), and there is a lot of code, looking at the code
// again, I can say what exactly can be done better, shorter and flatter.
public class DynArraySecondAddition<T> {

    private static class DynArray<E> {
        private E[] array;
        private int count;
        private int capacity;

        private static final int MIN_CAPACITY = 16;
        private static final int GROW_COEFFICIENT = 2;

        @SuppressWarnings("unchecked")
        DynArray(Class<E> clazz) {
            this.capacity = MIN_CAPACITY;
            this.count = 0;
            this.array = (E[]) Array.newInstance(clazz, capacity);
        }

        E get(int index) {
            if (index < 0 || index >= count)
                throw new IndexOutOfBoundsException();
            return array[index];
        }

        void set(int index, E value) {
            ensureCapacity(index + 1);
            while (index >= count) {
                array[count++] = null;
            }
            array[index] = value;
        }

        void append(E value) {
            ensureCapacity(count + 1);
            array[count++] = value;
        }

        private void ensureCapacity(int required) {
            if (required <= capacity) return;
            resize(capacity * GROW_COEFFICIENT);
        }

        private void resize(int newCapacity) {
            array = Arrays.copyOf(array, newCapacity);
            capacity = newCapacity;
        }
    }


    private final int dimensions;
    private final DynArray<Object> root;

    public DynArraySecondAddition(Class<T> clazz, int... sizes) {
        if (sizes.length == 0)
            throw new IllegalArgumentException("At least 1 dimension required");

        this.dimensions = sizes.length;
        this.root = createLevel(0, sizes);
    }

    private DynArray<Object> createLevel(int level, int[] sizes) {
        DynArray<Object> arr = new DynArray<>(Object.class);

        for (int i = 0; i < sizes[level]; i++) {
            if (level == sizes.length - 1) {
                arr.append(null);
            } else {
                arr.append(createLevel(level + 1, sizes));
            }
        }
        return arr;
    }

    @SuppressWarnings("unchecked")
    public T get(int... indices) {
        if (indices.length != dimensions)
            throw new IllegalArgumentException("Wrong amount of indices");

        Object current = root;

        for (int index : indices) {
            DynArray<Object> arr = (DynArray<Object>) current;
            current = arr.get(index);
        }
        return (T) current;
    }

    @SuppressWarnings("unchecked")
    public void set(T value, int... indices) {
        if (indices.length != dimensions)
            throw new IllegalArgumentException("Wrong number of indices");

        DynArray<Object> current = root;

        for (int i = 0; i < indices.length - 1; i++) {
            int idx = indices[i];

            Object next = null;
            if (idx < current.count) {
                next = current.get(idx);
            }

            if (next == null) {
                next = new DynArray<>(Object.class);
                current.set(idx, next);
            }

            current = (DynArray<Object>) next;
        }

        current.set(indices[indices.length - 1], value);
    }
}

