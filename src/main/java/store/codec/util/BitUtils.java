package store.codec.util;

/**
 * @author Kyle Friz
 * @since Feb 11, 2016
 */
public class BitUtils {

	private static final int[] MASKS = new int[32];

	static {
		for (int i = 0; i < 32; i++)
			MASKS[i] = (1 << i) - 1;
	}

	public static final int getMask(int i) {
		return MASKS[i];
	}
	
	public static final int nextPowerOfTwo(int n) {
        if (n == 0) return 1;

        // If n is already a power of two, return it:
        if ((n & (n - 1)) == 0) return n;

        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;

        return n + 1;
    }

}
