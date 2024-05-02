package de.verschwiegener.mvr.util;

import java.util.Arrays;

public class MVRMatrix {

	private String[] substrings;

	public MVRMatrix(String matrix) {
		substrings = everySubstringBetween(matrix, "{", "}");
	}

	public float[] getU() {
		String[] uValues = substrings[0].split(",");
		return new float[] { Float.valueOf(uValues[0]), Float.valueOf(uValues[1]), Float.valueOf(uValues[2]) };
	}

	public float[] getV() {
		String[] vValues = substrings[1].split(",");
		return new float[] { Float.valueOf(vValues[0]), Float.valueOf(vValues[1]), Float.valueOf(vValues[2]) };
	}

	public float[] getW() {
		String[] wValues = substrings[2].split(",");
		return new float[] { Float.valueOf(wValues[0]), Float.valueOf(wValues[1]), Float.valueOf(wValues[2]) };
	}

	public float[] getO() {
		String[] oValues = substrings[3].split(",");
		return new float[] { Float.valueOf(oValues[0]), Float.valueOf(oValues[1]), Float.valueOf(oValues[2]) };
	}

	/**
	 * Returns Every instance of String between open and close
	 * 
	 * @param str
	 * @param open
	 * @param close
	 * @return
	 */
	private String[] everySubstringBetween(final String str, final String open, final String close) {
		if (str == null || open == null || close == null)
			return null;

		final int start = str.indexOf(open);
		if (start != -1) {
			// Open Char exists
			final int end = str.indexOf(close, start + open.length());

			if (end != -1) {
				// Close Char exist
				String between = str.substring(start + open.length(), end);

				if (end < (str.length() - 1)) {
					// Contains multiple

					// Get substrings from rest of string
					String[] multiple = everySubstringBetween(str.substring(end + close.length()), open, close);

					String[] every = new String[multiple.length + 1];
					every[0] = between;

					// Copy to bigger array
					System.arraycopy(multiple, 0, every, 1, multiple.length);

					return every;
				}
				// Return if contains only one
				return new String[] { between };
			}
		}
		return null;
	}

}
