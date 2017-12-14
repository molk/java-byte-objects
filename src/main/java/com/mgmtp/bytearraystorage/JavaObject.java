package com.mgmtp.bytearraystorage;

import static java.lang.String.format;

class JavaObject implements MyObject {

	public int		getIntValue() 	 { return this.intValue;    }
	public long		getLongValue() 	 { return this.longValue;   }
	public String	getStringValue() { return this.stringValue; }

	final int intValue;
	final long longValue;
	final String stringValue;

	JavaObject(
		final int intValue,
		final long longValue,
		final String stringValue) {

		this.intValue = intValue;
		this.longValue = longValue;
		this.stringValue = stringValue;
	}

	@Override
	public String toString() {
		return format("[int: %d, long: %d, string: %s]", getIntValue(), getLongValue(), getStringValue());
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof JavaObject)) return false;

		final JavaObject that = (JavaObject) object;

		return this.intValue == that.intValue
			&& this.longValue == that.longValue
			&& this.stringValue == null ? that.stringValue == null : this.stringValue.equals(that.stringValue);
	}

}
