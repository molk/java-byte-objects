package com.mgmtp.bytearraystorage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.mgmtp.bytearraystorage.ByteArrayUtils.subbytes;
import static java.lang.String.format;
import static java.lang.System.arraycopy;

class JavaObjectInByteArray implements MyObject{

	private static final Charset CHARSET = Charset.forName("UTF-8");

	public int getIntValue() {
		return ByteBuffer.wrap(fields, 0, Integer.BYTES).getInt();
	}

	public long getLongValue() {
		return ByteBuffer.wrap(fields, Integer.BYTES, Long.BYTES).getLong();
	}

	public String getStringValue() {
		final int strLen = ByteBuffer.wrap(fields, Integer.BYTES + Long.BYTES, Integer.BYTES).getInt();
		final int posBegin = Integer.BYTES + Long.BYTES + Integer.BYTES;
		final int posEnd   = posBegin + strLen;

		return new String(subbytes(fields, posBegin, posEnd), CHARSET);
	}

	private final byte[] fields;

	JavaObjectInByteArray(
		final int intValue,
		final long longValue,
		final String stringValue) {

		byte[] intBytes    = asByteArray(intValue);
		byte[] longBytes   = asByteArray(longValue);
		byte[] stringBytes = stringValue.getBytes(CHARSET);
		byte[] strLenBytes = asByteArray(stringBytes.length);

		fields = new byte[
				intBytes.length		// int bytes
			+	longBytes.length	// long bytes
			+	strLenBytes.length	// string length bytes
			+	stringBytes.length];// string bytes

		int cursor = 0;
		arraycopy(intBytes,    0, fields, cursor, intBytes.length);

		cursor += intBytes.length;
		arraycopy(longBytes,   0, fields, cursor, longBytes.length);

		cursor += longBytes.length;
		arraycopy(strLenBytes, 0, fields, cursor, strLenBytes.length);

		cursor += strLenBytes.length;
		arraycopy(stringBytes, 0, fields, cursor, stringBytes.length);
	}

	@Override
	public String toString() {
		return format("[int: %d, long: %d, string: %s] size %d", getIntValue(), getLongValue(), getStringValue(), fields.length);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(fields);
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof JavaObjectInByteArray
			&& this.hashCode() == object.hashCode();
	}

	private static byte[] asByteArray(int value) {
		return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
	}

	private static byte[] asByteArray(long value) {
		return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
	}

}
