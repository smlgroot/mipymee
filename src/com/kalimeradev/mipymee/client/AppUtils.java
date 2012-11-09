package com.kalimeradev.mipymee.client;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class AppUtils {

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);


	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>Float</code>.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>Float</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static Float createFloat(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		return Float.valueOf(str);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>Double</code>.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>Double</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static Double createDouble(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		return Double.valueOf(str);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>Integer</code>, handling hex and octal notations.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>Integer</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static Integer createInteger(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		// decode() handles 0xAABD and 0777 (hex and octal) as well.
		return Integer.decode(str);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>Long</code>; since 3.1 it handles hex and octal notations.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>Long</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static Long createLong(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		return Long.decode(str);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>BigInteger</code>.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>BigInteger</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static BigInteger createBigInteger(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		return new BigInteger(str);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to a <code>BigDecimal</code>.
	 * </p>
	 * 
	 * <p>
	 * Returns <code>null</code> if the string is <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            a <code>String</code> to convert, may be null
	 * @return converted <code>BigDecimal</code>
	 * @throws NumberFormatException
	 *             if the value cannot be converted
	 */
	public static BigDecimal createBigDecimal(String str) {
		boolean isNumber = isNumber(str);
		if (str == null || !isNumber) {
			return null;
		}
		// handle JDK1.3.1 bug where "" throws IndexOutOfBoundsException
		if (str.matches("")) {
			throw new NumberFormatException("A blank string is not a valid number");
		}
		return new BigDecimal(str);
	}

	/**
	 * <p>
	 * Checks whether the String a valid Java number.
	 * </p>
	 * 
	 * <p>
	 * Valid numbers include hexadecimal marked with the <code>0x</code> qualifier, scientific notation and numbers marked with a type qualifier (e.g. 123L).
	 * </p>
	 * 
	 * <p>
	 * <code>Null</code> and empty String will return <code>false</code>.
	 * </p>
	 * 
	 * @param str
	 *            the <code>String</code> to check
	 * @return <code>true</code> if the string is a correctly formatted number
	 */
	public static boolean isNumber(String str) {
		if (str == null || str.matches("")) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		// deal with any possible sign up front
		int start = (chars[0] == '-') ? 1 : 0;
		if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
			int i = start + 2;
			if (i == sz) {
				return false; // str == "0x"
			}
			// checking hex (it can't be anything else)
			for (; i < chars.length; i++) {
				if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
					return false;
				}
			}
			return true;
		}
		sz--; // don't want to loop to the last char, check it afterwords
				// for type qualifiers
		int i = start;
		// loop to the next to last char or to the last char if we need another digit to
		// make a valid number (e.g. chars[0..5] = "1234E")
		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					// two decimal points or dec in exponent
					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {
				// we've already taken care of hex.
				if (hasExp) {
					// two E's
					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false; // we need a digit after the E
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				// no type qualifier, OK
				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {
				// can't have an E at the last byte
				return false;
			}
			if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					// two decimal points or dec in exponent
					return false;
				}
				// single trailing decimal point after non-exponent is ok
				return foundDigit;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {
				// not allowing L with an exponent or decimal point
				return foundDigit && !hasExp && !hasDecPoint;
			}
			// last character is illegal
			return false;
		}
		// allowSigns is true iff the val ends in 'E'
		// found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
		return !allowSigns && foundDigit;
	}
}