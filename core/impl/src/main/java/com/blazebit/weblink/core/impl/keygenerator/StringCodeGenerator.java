package com.blazebit.weblink.core.impl.keygenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating short strings from numbers by means of a bijection.
 * This is a cleaned up implementation of http://hashids.org v1.0.2 version.
 * 
 * @author Christian Beikov
 */
public class StringCodeGenerator {
	
	private static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final String DEFAULT_SEPARATORS = "cfhistuCFHISTU";
	private static final int MINIMUM_ALPHABET_LENGTH = 16;
	
	private static final double SEP_DIV = 3.5;
	private static final int GUARD_DIV = 12;

	private final String salt;
	private final String alphabet;
	private final String seps;
	private final int minHashLength;
	private final String guards;
	
	private final String bufferTemplate;
	private final int bufferStart;
	private final int alphabetCopyCount;

	public StringCodeGenerator() {
		this("");
	}

	public StringCodeGenerator(String salt) {
		this(salt, 0);
	}

	public StringCodeGenerator(String salt, int minHashLength) {
		this(salt, minHashLength, DEFAULT_ALPHABET);
	}

	public StringCodeGenerator(String salt, int minHashLength, String alphabet) {
		this.salt = salt;
		
		if (minHashLength < 0) {
			this.minHashLength = 0;
		} else {
			this.minHashLength = minHashLength;
		}
		
		StringBuilder alphabetBuilder = new StringBuilder(alphabet.length());
		
		for (int i = 0; i < alphabet.length(); i++) {
			if (indexOf(alphabetBuilder, alphabet.charAt(i)) == -1) {
				alphabetBuilder.append(alphabet.charAt(i));
			}
		}

		if (alphabetBuilder.length() < MINIMUM_ALPHABET_LENGTH) {
			throw new IllegalArgumentException("alphabet must contain at least " + MINIMUM_ALPHABET_LENGTH + " unique characters");
		}

		if (alphabetBuilder.indexOf(" ") != -1) {
			throw new IllegalArgumentException("alphabet cannot contains spaces");
		}
		
		StringBuilder sepsBuilder = new StringBuilder(DEFAULT_SEPARATORS);

		// seps should contain only characters present in alphabet; alphabet should not contains seps
		int sepsOffset = 0;
		int sepsLength = sepsBuilder.length();
		for (int i = 0; i < sepsLength; i++) {
			int j = indexOf(alphabetBuilder, sepsBuilder.charAt(i - sepsOffset));
			if (j == -1) {
				sepsBuilder.deleteCharAt(i - sepsOffset);
				sepsOffset++;
			} else {
				alphabetBuilder.deleteCharAt(j);
			}
		}

		consistentShuffle(sepsBuilder, this.salt);
		sepsLength = sepsBuilder.length();

		if (sepsLength == 0 || (alphabetBuilder.length() / sepsLength) > SEP_DIV) {
			int anticipatedSepsLength = (int) Math.ceil(alphabetBuilder.length() / SEP_DIV);

			if (anticipatedSepsLength == 1) {
				anticipatedSepsLength++;
			}

			if (anticipatedSepsLength > sepsLength) {
				int diff = anticipatedSepsLength - sepsLength;
				sepsBuilder.append(alphabetBuilder, 0, diff);
				alphabetBuilder.delete(0, diff);
			} else {
				sepsBuilder.delete(anticipatedSepsLength, sepsLength);
			}
		}

		consistentShuffle(alphabetBuilder, this.salt);
		
		// use double to round up
		int guardCount = (int) Math.ceil((double) alphabetBuilder.length() / GUARD_DIV);

		if (alphabetBuilder.length() < 3) {
			this.guards = sepsBuilder.substring(0, guardCount);
			this.seps = sepsBuilder.substring(guardCount);
			this.alphabet = alphabetBuilder.toString();
		} else {
			this.guards = alphabetBuilder.substring(0, guardCount);
			this.seps = sepsBuilder.toString();
			this.alphabet = alphabetBuilder.substring(guardCount);
		}
		
		/*
		 * Prepare a buffer template for salt generation
		 */
		
		this.bufferStart = 1 + this.salt.length();
		this.alphabetCopyCount = this.alphabet.length() - (1 + this.salt.length());

		final StringBuilder buffer = new StringBuilder(this.alphabet.length());
		buffer.append(' ');
		
		// alphabetCopyCount is negative if salt.length() + 1 is bigger than the alphabet 
		if (alphabetCopyCount < 0) {
			// only copy up to alphabet.length() - 1 from salt to buffer
			buffer.append(this.salt, 0, this.alphabet.length() - 1);
		} else {
			// copy full salt and fill the rest up with spaces
			buffer.append(this.salt);
			for (int i = 0; i < alphabetCopyCount; i++) {
				buffer.append(' ');
			}
		}
		
		this.bufferTemplate = buffer.toString();
	}

	/**
	 * Encrypt numbers to string
	 *
	 * @param numbers
	 *            the numbers to encrypt
	 * @return the encrypt string
	 */
	public String encode(long... numbers) {
		for (long number : numbers) {
			if (number > 9007199254740992L) {
				throw new IllegalArgumentException("number can not be greater than 9007199254740992L");
			}
		}

		if (numbers.length == 0) {
			return "";
		}

		return _encode(numbers);
	}

	/**
	 * Decrypt string to numbers
	 *
	 * @param hash
	 *            the encrypt string
	 * @return decryped numbers
	 */
	public long[] decode(String hash) {
		if (hash.equals("")) {
			return new long[0];
		}

		return _decode(hash);
	}

	private String _encode(long[] numbers) {
		int numberHashInt = 0;
		for (int i = 0; i < numbers.length; i++) {
			numberHashInt += (numbers[i] % (i + 100));
		}
		
		final StringBuilder alphabet = new StringBuilder(this.alphabet);
		final StringBuilder buffer = new StringBuilder(bufferTemplate);
		final char ret = alphabet.charAt(numberHashInt % alphabet.length());
		buffer.setCharAt(0, ret);
		
		StringBuilder returnSb = new StringBuilder();
		returnSb.append(ret);

		long num;
		int sepsIndex, guardIndex;
		char guard;
		
		for (int i = 0; i < numbers.length; i++) {
			num = numbers[i];

			consistentShuffle(alphabet, createSalt(buffer, alphabet));
			final StringBuilder last = hash(num, alphabet);

			returnSb.append(last);

			if (i + 1 < numbers.length) {
				num %= ((int) last.charAt(0) + i);
				sepsIndex = (int) (num % this.seps.length());
				returnSb.append(this.seps.charAt(sepsIndex));
			}
		}

		if (returnSb.length() < this.minHashLength) {
			guardIndex = (numberHashInt + (int) returnSb.charAt(0)) % this.guards.length();
			guard = this.guards.charAt(guardIndex);

			returnSb.insert(0, guard);

			if (returnSb.length() < this.minHashLength) {
				guardIndex = (numberHashInt + (int) returnSb.charAt(2)) % this.guards.length();
				guard = this.guards.charAt(guardIndex);

				returnSb.append(guard);
			}
		}

		int halfLen = alphabet.length() / 2;
		while (returnSb.length() < this.minHashLength) {
			consistentShuffle(alphabet, alphabet.toString());
			
			returnSb.ensureCapacity(returnSb.length() + alphabet.length());
			returnSb.insert(0, alphabet, halfLen, alphabet.length());
			returnSb.append(alphabet, 0, halfLen);
			
			int excess = returnSb.length() - this.minHashLength;
			if (excess > 0) {
				int startPosition = excess / 2;
				returnSb.delete(startPosition + this.minHashLength, returnSb.length());
				returnSb.delete(0, startPosition);
			}
		}

		return returnSb.toString();
	}

	private long[] _decode(String hash) {
		ArrayList<Long> ret = new ArrayList<Long>();
		List<String> hashList = splitByChars(hash, this.guards);

		String hashBreakdown;
		if (hashList.size() == 3 || hashList.size() == 2) {
			hashBreakdown = hashList.get(1);
		} else {
			hashBreakdown = hashList.get(0);
		}

		char lottery = hashBreakdown.charAt(0);

		hashBreakdown = hashBreakdown.substring(1);
		hashList = splitByChars(hashBreakdown, this.seps);

		final StringBuilder alphabet = new StringBuilder(this.alphabet);
		final StringBuilder buffer = new StringBuilder(bufferTemplate);
		buffer.setCharAt(0, lottery);

		for (String hashElement : hashList) {
			consistentShuffle(alphabet, createSalt(buffer, alphabet));
			ret.add(unhash(hashElement, alphabet));
		}

		// transform from List<Long> to long[]
		long[] arr = new long[ret.size()];
		for (int k = 0; k < arr.length; k++) {
			arr[k] = ret.get(k);
		}

		if (!_encode(arr).equals(hash)) {
			arr = new long[0];
		}

		return arr;
	}

	/* Private methods */
	
	private String createSalt(StringBuilder buffer, StringBuilder alphabet) {
		final String newSalt;

		if (alphabetCopyCount < 0) {
			newSalt = buffer.toString();
		} else {
			// replace last slots in buffer with alphabet characters
			for (int j = 0; j < alphabetCopyCount; j++) {
				buffer.setCharAt(bufferStart + j, alphabet.charAt(j));
			}
			newSalt = buffer.toString();
		}
		
		return newSalt;
	}
	
	private void consistentShuffle(StringBuilder alphabet, String salt) {
		int saltLength = salt.length();
		if (saltLength <= 0) {
			return;
		}

		char[] arr = salt.toCharArray();
		int asc_val, j;
		char tmp;
		for (int i = alphabet.length() - 1, v = 0, p = 0; i > 0; i--, v++) {
			v %= saltLength;
			asc_val = (int) arr[v];
			p += asc_val;
			j = (asc_val + v + p) % i;

			tmp = alphabet.charAt(i);
			alphabet.setCharAt(i, alphabet.charAt(j));
			alphabet.setCharAt(j, tmp);
		}
	}

	private StringBuilder hash(long input, StringBuilder alphabet) {
		StringBuilder hash = new StringBuilder();
		int alphabetLen = alphabet.length();
		char[] arr = new char[alphabetLen];
		alphabet.getChars(0, alphabetLen, arr, 0);

		do {
			hash.append(arr[(int) (input % alphabetLen)]);
			input /= alphabetLen;
		} while (input > 0);

		return hash.reverse();
	}

	private Long unhash(String input, StringBuilder alphabet) {
		long number = 0, pos;
		char[] input_arr = input.toCharArray();

		for (int i = 0; i < input.length(); i++) {
			pos = indexOf(alphabet, input_arr[i]);
			number += pos * Math.pow(alphabet.length(), input.length() - i - 1);
		}

		return number;
	}
	
	private int indexOf(StringBuilder sb, char c) {
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == c) {
				return i;
			}
		}
		
		return -1;
	}
	
	private List<String> splitByChars(String hash, String chars) {
		final StringBuilder sb = new StringBuilder();
		final List<String> list = new ArrayList<String>();
		final int charsLength = chars.length();
		
		OUTER: for (int i = 0; i < hash.length(); i++) {
			final char c = hash.charAt(i);
			
			for (int j = 0; j < charsLength; j++) {
				if (c == chars.charAt(j)) {
					if (sb.length() > 0) {
						list.add(sb.toString());
						sb.setLength(0);
					}
					
					continue OUTER;
				}
			}
			
			sb.append(c);
		}
		
		if (sb.length() > 0) {
			list.add(sb.toString());
		}
		
		return list;
	}
}
