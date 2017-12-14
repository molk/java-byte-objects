package com.mgmtp.bytearraystorage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mgmtp.bytearraystorage.BenchUtils.formatSize;
import static com.mgmtp.bytearraystorage.BenchUtils.heapUsed;
import static com.mgmtp.bytearraystorage.BenchUtils.println;
import static java.lang.System.gc;
import static java.lang.Thread.sleep;

public class Main {

	static MyObject createMyObject(int intValue, long longValue, String stringValue) {
		return new JavaObjectInByteArray(intValue, longValue, stringValue);
	}

    public static void main(String[] args) throws InterruptedException {

		final int intValue = 42;
		final long longValue = 4223;
		final String stringValue = "FORTYTWO";

		final MyObject obj1 = createMyObject(intValue, longValue, stringValue);
		final MyObject obj2 = createMyObject(intValue, longValue, stringValue);

		assert obj1.getStringValue().equals(stringValue);
		assert obj1.getIntValue() == intValue;
		assert obj1.getLongValue() == longValue;

		assert obj1.equals(obj2);

		System.out.println(obj1);

		List<MyObject> objects = IntStream.range(0, 10_000_000)
			.mapToObj(n -> createMyObject(n, 42, "FORTYTWO"))
			.collect(Collectors.toList());

		while (true) {
			println(objects.size() + ": " + formatSize(heapUsed()));
			gc();
			sleep(1000);
		}
    }
}
