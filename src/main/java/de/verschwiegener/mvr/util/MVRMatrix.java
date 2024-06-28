package de.verschwiegener.mvr.util;

import java.lang.Math;

public class MVRMatrix {

	float m00, m01, m02;
	float m10, m11, m12;
	float m20, m21, m22;
	float m30, m31, m32;

	/**
	 * Returns Identity Matrix
	 */
	public MVRMatrix() {
		identity();
	}

	/**
	 * Tries to Parse MVR Matrix, returns Identity Matrix if Matrix Data is missing
	 * 
	 * @param matrix
	 */
	public MVRMatrix(String matrix) {
		if (matrix == null) {
			identity();
			return;
		}

		String[] substrings = everySubstringBetween(matrix, "{", "}");

		String[] uValues = substrings[0].split(",");
		String[] vValues = substrings[1].split(",");
		String[] wValues = substrings[2].split(",");
		String[] oValues = substrings[3].split(",");

		m00 = Float.valueOf(uValues[0]);
		m01 = Float.valueOf(uValues[1]);
		m02 = Float.valueOf(uValues[2]);

		m10 = Float.valueOf(vValues[0]);
		m11 = Float.valueOf(vValues[1]);
		m12 = Float.valueOf(vValues[2]);

		m20 = Float.valueOf(wValues[0]);
		m21 = Float.valueOf(wValues[1]);
		m22 = Float.valueOf(wValues[2]);

		m30 = Float.valueOf(oValues[0]);
		m31 = Float.valueOf(oValues[1]);
		m32 = Float.valueOf(oValues[2]);

	}

	public void identity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;

		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;

		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;

		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
	}

	/**
	 * 
	 * @return
	 */
	public float[] getScale() {
		float[] scale = new float[3];
		scale[0] = (float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
		scale[1] = (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
		scale[2] = (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
		return scale;
	}

	public float[] getTranslation() {
		float[] translation = new float[3];
		translation[0] = m30;
		translation[1] = m31;
		translation[2] = m32;
		return translation;
	}

	public float[] getRotation() {
		float[] rotation = new float[4];

		float nm00 = m00, nm01 = m01, nm02 = m02;
		float nm10 = m10, nm11 = m11, nm12 = m12;
		float nm20 = m20, nm21 = m21, nm22 = m22;
		float lenX = 1.0f / (float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
		float lenY = 1.0f / (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
		float lenZ = 1.0f / (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
		nm00 *= lenX;
		nm01 *= lenX;
		nm02 *= lenX;
		nm10 *= lenY;
		nm11 *= lenY;
		nm12 *= lenY;
		nm20 *= lenZ;
		nm21 *= lenZ;
		nm22 *= lenZ;
		float epsilon = 1E-4f, epsilon2 = 1E-3f;
		if (Math.abs(nm10 - nm01) < epsilon && Math.abs(nm20 - nm02) < epsilon && Math.abs(nm21 - nm12) < epsilon) {
			if (Math.abs(nm10 + nm01) < epsilon2 && Math.abs(nm20 + nm02) < epsilon2 && Math.abs(nm21 + nm12) < epsilon2
					&& Math.abs(nm00 + nm11 + nm22 - 3) < epsilon2) {
				rotation[0] = 0f;
				rotation[1] = 0f;
				rotation[2] = 1f;
				rotation[3] = 0f;
				return rotation;
			}
			rotation[3] = (float) Math.PI;
			float xx = (nm00 + 1) / 2;
			float yy = (nm11 + 1) / 2;
			float zz = (nm22 + 1) / 2;
			float xy = (nm10 + nm01) / 4;
			float xz = (nm20 + nm02) / 4;
			float yz = (nm21 + nm12) / 4;
			if ((xx > yy) && (xx > zz)) {
				rotation[0] = (float) Math.sqrt(xx);
				rotation[1] = xy / rotation[0];
				rotation[2] = xz / rotation[0];
			} else if (yy > zz) {
				rotation[1] = (float) Math.sqrt(yy);
				rotation[0] = xy / rotation[1];
				rotation[2] = yz / rotation[1];
			} else {
				rotation[2] = (float) Math.sqrt(zz);
				rotation[0] = xz / rotation[2];
				rotation[1] = yz / rotation[2];
			}
			return rotation;
		}
		float s = (float) Math
				.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
		rotation[3] = safeAcos((nm00 + nm11 + nm22 - 1) / 2);
		rotation[0] = (nm12 - nm21) / s;
		rotation[1] = (nm20 - nm02) / s;
		rotation[2] = (nm01 - nm10) / s;
		return rotation;
	}

	public float m00() {
		return m00;
	}

	public float m01() {
		return m01;
	}

	public float m02() {
		return m02;
	}

	public float m10() {
		return m10;
	}

	public float m11() {
		return m11;
	}

	public float m12() {
		return m12;
	}

	public float m20() {
		return m20;
	}

	public float m21() {
		return m21;
	}

	public float m22() {
		return m22;
	}

	public float m30() {
		return m30;
	}

	public float m31() {
		return m31;
	}

	public float m32() {
		return m32;
	}

	private float safeAcos(float v) {
		if (v < -1.0f)
			return (float) Math.PI;
		else if (v > +1.0f)
			return 0.0f;
		else
			return (float) Math.acos(v);
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
