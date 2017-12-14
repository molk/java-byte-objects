package com.mgmtp.bytearraystorage;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;

import static java.lang.System.out;

final class BenchUtils {

	private BenchUtils() {
	}

	// Memory Helpers --------------------------------------------------------------------------------------------------------------

	static long heapUsed() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
	}

	static void printMemoryStatus() {
		ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

		println("================================================================================================================================");
		println("Memory Beans -------------------------------------------------------------------------------------------------------------------");
		println("Heap: " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
		println("NonHeap" + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage());


		println("\nMemoryPool Beans ---------------------------------------------------------------------------------------------------------------");
		for (MemoryPoolMXBean bean : ManagementFactory.getMemoryPoolMXBeans()) {
			println(bean.getName() + ": " + bean.getUsage());
		}

		println("\nGC Beans -----------------------------------------------------------------------------------------------------------------------");
		for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
			println(bean.getName() + ": " + bean.getCollectionCount() + ", " + bean.getCollectionTime());
		}
		println("================================================================================================================================");
	}

	// Time Helpers --------------------------------------------------------------------------------------------------------------

	static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	// Misc Helpers --------------------------------------------------------------------------------------------------------------

	static void println(Object message) {
		out.println(message);
	}

	static String formatSize(long bytes) {
		return getFormattedSize(bytes) + " " + getFormattedUnit(bytes);
	}

	static String getFormattedSize(long bytes) {
		int unit = 1024;
		if (bytes < unit) return String.valueOf(bytes);
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		return String.format("%.1f", bytes / Math.pow(unit, exp));
	}

	static String getFormattedUnit(long bytes) {
		int unit = 1024;
		if (bytes < unit) return " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = "kMGTPE".charAt(exp - 1) + "";
		return pre + "B";
	}

}
