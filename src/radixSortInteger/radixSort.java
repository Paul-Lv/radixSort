package radixSortInteger;


import java.util.Arrays;
import java.util.Random;

public class radixSort {

	private static final int MAX_ARR_RANGE = 1000000;
	private static final int MAX_NUMBER = 1000000;

	public static void main(String[] args) {

		int[] data = generate();
//		System.out.println("data - " + Arrays.toString(data));

		int[] data2 = Arrays.copyOf(data, MAX_ARR_RANGE);

		long start = System.currentTimeMillis();
		RadixSort(data2);
		long stop = System.currentTimeMillis();
		System.out.println("Elapsed Radix Sort (res) = " + (stop - start));
//		System.out.println("data2 - " + Arrays.toString(data2));

		start = System.currentTimeMillis();
		Arrays.sort(data);
		stop = System.currentTimeMillis();
		System.out.println("Elapsed standart (data) = " + (stop - start));
//		System.out.println("data sort" + Arrays.toString(data));

	}

	// ================================================
	public static void RadixSort(int[] a) {
		// our helper array
		int[] helperArray = new int[a.length];

		// number of bits our group will be long
		int numberOfBit = 4; // try to set this also to 2, 8 or 16 to see if it is quicker or not

		// number of bits of a Java int
		int intSize = Integer.SIZE;

		// counting and prefix arrays
		// (note dimensions 2^r which is the number of all possible values of a r-bit
		// number)
		int[] count = new int[1 << numberOfBit];
		int[] pref = new int[1 << numberOfBit];

		// number of groups
		int groups = (int) Math.ceil(intSize / (double) numberOfBit);

		// the mask to identify groups
		int mask = (1 << numberOfBit) - 1;

		// the algorithm:
		for (int c = 0, shift = 0; c < groups; c++, shift += numberOfBit) {
			// reset count array
			for (int j = 0; j < count.length; j++) {
				count[j] = 0;
			}

			// counting elements of the c-th group
			for (int i = 0; i < a.length; i++) {
				count[(a[i] >> shift) & mask]++;
			}

			// calculating prefixes
			pref[0] = 0;
			for (int i = 1; i < count.length; i++) {
				pref[i] = pref[i - 1] + count[i - 1];
			}

			// from a[] to t[] elements ordered by c-th group
			for (int i = 0; i < a.length; i++) {
				helperArray[pref[(a[i] >> shift) & mask]++] = a[i];
			}

			// a[]=t[] and start again until the last group
			System.arraycopy(helperArray, 0, a, 0, helperArray.length);
		}
		// a is sorted
	}

	// ================================================
	private static int[] generate() {
		int[] data = new int[MAX_ARR_RANGE];

		Random random = new Random();

		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(MAX_NUMBER);
		}

		return data;
	}

}
