import org.junit.Test;

import static org.junit.Assert.*;

public class NativeCacheTest {

    @Test
    public void testGetOrLoadWithSingleValues() {
        NativeCache<String> nativeCache = new NativeCache<>(String.class);

        assertEquals(0, nativeCache.size());

        assertEquals("mock_for___Opp", nativeCache.getOrLoad("Opp"));
        assertEquals(1, nativeCache.size());

        assertEquals("mock_for___Opp", nativeCache.getOrLoad("Opp"));
        assertEquals(1, nativeCache.size());
    }

    @Test
    public void testGetOrLoadWithFewValues() {
        NativeCache<String> nativeCache = new NativeCache<>(String.class);

        assertEquals(0, nativeCache.size());

        nativeCache.getOrLoad("Opp");
        assertEquals(1, nativeCache.size());

        nativeCache.getOrLoad("OtY");
        assertEquals(2, nativeCache.size());

        nativeCache.getOrLoad("OtX");
        assertEquals(3, nativeCache.size());
    }

    @Test
    public void testGetOrLoadWithFullCache() {
        NativeCache<String> nativeCache = new NativeCache<>(String.class);

        assertEquals(0, nativeCache.size());

        nativeCache.getOrLoad("Opp");
        nativeCache.getOrLoad("Ofc");
        nativeCache.getOrLoad("lki");
        nativeCache.getOrLoad("nice");
        nativeCache.getOrLoad("vault");
        nativeCache.getOrLoad("crator");
        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("invader");
        nativeCache.getOrLoad("kidney");
        nativeCache.getOrLoad("spoon");

        assertEquals(10, nativeCache.size());

        nativeCache.getOrLoad("bender");
        assertEquals(10, nativeCache.size());
    }


    @Test
    public void testGetOrLoadWithFullCacheWithDifferentHits() {
        NativeCache<String> nativeCache = new NativeCache<>(String.class);

        assertEquals(0, nativeCache.size());

        nativeCache.getOrLoad("Opp");
        nativeCache.getOrLoad("Ofc");
        nativeCache.getOrLoad("lki");
        nativeCache.getOrLoad("nice");
        nativeCache.getOrLoad("vault");
        nativeCache.getOrLoad("crator");
        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("invader");
        nativeCache.getOrLoad("kidney");
        nativeCache.getOrLoad("spoon");

        nativeCache.getOrLoad("Opp");
        nativeCache.getOrLoad("Ofc");
        nativeCache.getOrLoad("lki");
        nativeCache.getOrLoad("nice");
        nativeCache.getOrLoad("vault");
        nativeCache.getOrLoad("crator");
        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("invader");
        nativeCache.getOrLoad("kidney");

        assertEquals(10, nativeCache.size());
        nativeCache.getOrLoad("KONGROO");

        assertNull(nativeCache.get("spoon"));
        assertEquals("mock_for___KONGROO", nativeCache.get("KONGROO"));
        assertEquals("mock_for___KONGROO", nativeCache.getOrLoad("KONGROO"));
        assertEquals(10, nativeCache.size());
    }


    @Test
    public void testGetOrLoadWithFewElementsOfDifferentHits() {
        NativeCache<String> nativeCache = new NativeCache<>(String.class);

        assertEquals(0, nativeCache.size());

        nativeCache.getOrLoad("crator");
        nativeCache.getOrLoad("crator");
        nativeCache.getOrLoad("crator");

        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("alien");
        nativeCache.getOrLoad("alien");

        nativeCache.getOrLoad("invader");
        nativeCache.getOrLoad("invader");


        assertEquals(3, nativeCache.size());
        assertEquals("mock_for___crator", nativeCache.get("crator"));
        assertEquals("mock_for___alien", nativeCache.get("alien"));
        assertEquals("mock_for___invader", nativeCache.get("invader"));


        nativeCache.getOrLoad("KONGROO");
        assertEquals(4, nativeCache.size());
        assertEquals("mock_for___crator", nativeCache.get("crator"));
        assertEquals("mock_for___alien", nativeCache.get("alien"));
        assertEquals("mock_for___invader", nativeCache.get("invader"));
        assertEquals("mock_for___KONGROO", nativeCache.get("KONGROO"));
    }

}

