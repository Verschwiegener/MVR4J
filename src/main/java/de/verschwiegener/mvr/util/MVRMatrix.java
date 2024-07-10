package de.verschwiegener.mvr.util;

import java.lang.Math;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import org.joml.Options;
import org.joml.Runtime;

public class MVRMatrix {

	double m00, m01, m02;
	double m10, m11, m12;
	double m20, m21, m22;
	double m30, m31, m32;

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

		m00 = Double.valueOf(uValues[0]);
		m01 = Double.valueOf(uValues[1]);
		m02 = Double.valueOf(uValues[2]);

		m10 = Double.valueOf(vValues[0]);
		m11 = Double.valueOf(vValues[1]);
		m12 = Double.valueOf(vValues[2]);

		m20 = Double.valueOf(wValues[0]);
		m21 = Double.valueOf(wValues[1]);
		m22 = Double.valueOf(wValues[2]);

		m30 = Double.valueOf(oValues[0]);
		m31 = Double.valueOf(oValues[1]);
		m32 = Double.valueOf(oValues[2]);

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
	public double[] getScale() {
		double[] scale = new double[3];
		scale[0] = Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
		scale[1] = Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
		scale[2] = Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
		return scale;
	}
	
	/**
	 * Sets Matrix Scale
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setScale(double x, double y, double z) {
		m00 = x;
        m11 = y;
        m22 = z;
	}

	public double[] getTranslation() {
		double[] translation = new double[3];
		translation[0] = m30;
		translation[1] = m31;
		translation[2] = m32;
		return translation;
	}
	
	/**
	 * 
	 * @param scaleFactor Scales the Position to whatever Unit is wanted
	 * scaleFactor 1 = Millimeter
	 * @return
	 */
	public double[] getTranslation(double scaleFactor) {
		double[] translation = new double[3];
		translation[0] = m30 / 100;
		translation[1] = m31 / 100;
		translation[2] = m32 / 100;
		return translation;
	}
	
	public void translate(double[] offset) {
        m30 = m30 + offset[0];
        m31 = m31 + offset[1];
        m32 = m32 + offset[2];
	}
	
	public void setRotationXYZ(double angleX, double angleY, double angleZ) {		
		double sinX = Math.sin(angleX);
        double cosX = Math.sin(angleX + (Math.PI * 0.5));
        double sinY = Math.sin(angleY);
        double cosY = Math.sin(angleY + (Math.PI * 0.5));
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.sin(angleZ + (Math.PI * 0.5));
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm11 = cosX;
        double nm12 = sinX;
        double nm21 = m_sinX;
        double nm22 = cosX;
        // rotateY
        double nm00 = cosY;
        double nm01 = nm21 * m_sinY;
        double nm02 = nm22 * m_sinY;
        m20 = sinY;
        m21 = nm21 * cosY;
        m22 = nm22 * cosY;
        // rotateZ
        m00 = nm00 * cosZ;
        m01 = nm01 * cosZ + nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ;
        m11 = nm01 * m_sinZ + nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
	}

	public double[] getRotation() {
		double[] rotation = new double[4];

		double nm00 = m00, nm01 = m01, nm02 = m02;
		double nm10 = m10, nm11 = m11, nm12 = m12;
		double nm20 = m20, nm21 = m21, nm22 = m22;
		double lenX = 1.0f / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
		double lenY = 1.0f / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
		double lenZ = 1.0f / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
		nm00 *= lenX;
		nm01 *= lenX;
		nm02 *= lenX;
		nm10 *= lenY;
		nm11 *= lenY;
		nm12 *= lenY;
		nm20 *= lenZ;
		nm21 *= lenZ;
		nm22 *= lenZ;
		double epsilon = 1E-4f, epsilon2 = 1E-3f;
		if (Math.abs(nm10 - nm01) < epsilon && Math.abs(nm20 - nm02) < epsilon && Math.abs(nm21 - nm12) < epsilon) {
			if (Math.abs(nm10 + nm01) < epsilon2 && Math.abs(nm20 + nm02) < epsilon2 && Math.abs(nm21 + nm12) < epsilon2
					&& Math.abs(nm00 + nm11 + nm22 - 3) < epsilon2) {
				rotation[0] = 0f;
				rotation[1] = 0f;
				rotation[2] = 1f;
				rotation[3] = 0f;
				return rotation;
			}
			rotation[3] = Math.PI;
			double xx = (nm00 + 1) / 2;
			double yy = (nm11 + 1) / 2;
			double zz = (nm22 + 1) / 2;
			double xy = (nm10 + nm01) / 4;
			double xz = (nm20 + nm02) / 4;
			double yz = (nm21 + nm12) / 4;
			if ((xx > yy) && (xx > zz)) {
				rotation[0] = Math.sqrt(xx);
				rotation[1] = xy / rotation[0];
				rotation[2] = xz / rotation[0];
			} else if (yy > zz) {
				rotation[1] = Math.sqrt(yy);
				rotation[0] = xy / rotation[1];
				rotation[2] = yz / rotation[1];
			} else {
				rotation[2] = Math.sqrt(zz);
				rotation[0] = xz / rotation[2];
				rotation[1] = yz / rotation[2];
			}
			return rotation;
		}
		double s = Math
				.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
		rotation[3] = safeAcos((nm00 + nm11 + nm22 - 1) / 2);
		rotation[0] = (nm12 - nm21) / s;
		rotation[1] = (nm20 - nm02) / s;
		rotation[2] = (nm01 - nm10) / s;
		return rotation;
	}
	
	/**
	 * Returns the Matrix Rotation as Radian
	 * @return
	 */
	public double[] getRotationRadians() {
		//https://stackoverflow.com/a/15029416
		double x = Math.atan2(m21,m22);
		double y = Math.atan2(-m20, Math.sqrt(Math.pow(m21, 2) + Math.pow(m22, 2)));
		double z = Math.atan2(m10, m00);
		return new double[] {x, y, z};
	}
	public double[] getRotationAngles() {
		double[] radians = getRotationRadians();
		return new double[] {Math.toDegrees(radians[0]), Math.toDegrees(radians[1]), Math.toDegrees(radians[2])};
	}

	public double m00() {
		return m00;
	}

	public double m01() {
		return m01;
	}

	public double m02() {
		return m02;
	}

	public double m10() {
		return m10;
	}

	public double m11() {
		return m11;
	}

	public double m12() {
		return m12;
	}

	public double m20() {
		return m20;
	}

	public double m21() {
		return m21;
	}

	public double m22() {
		return m22;
	}

	public double m30() {
		return m30;
	}

	public double m31() {
		return m31;
	}

	public double m32() {
		return m32;
	}

	private double safeAcos(double v) {
		if (v < -1.0f)
			return Math.PI;
		else if (v > +1.0f)
			return 0.0f;
		else
			return Math.acos(v);
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
	
	public String toMVR() {
		DecimalFormat decimalFormat = new DecimalFormat("0.000000");
		return "{" + decimalFormat.format(m00) + "," + decimalFormat.format(m10) + "," + decimalFormat.format(m20) + ","
				+ decimalFormat.format(m30) + "}{" + decimalFormat.format(m01) + "," + decimalFormat.format(m11) + ","
				+ decimalFormat.format(m21) + "," + decimalFormat.format(m31) + "}{" + decimalFormat.format(m02) + ","
				+ decimalFormat.format(m12) + "," + decimalFormat.format(m22) + "," + decimalFormat.format(m32) + "}";
	}
	
	/**
    * Return a string representation of this matrix.
    * <p>
    * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
    * 
    * @return the string representation
    */
   public String toString() {
       String str = toString(Options.NUMBER_FORMAT);
       StringBuffer res = new StringBuffer();
       int eIndex = Integer.MIN_VALUE;
       for (int i = 0; i < str.length(); i++) {
           char c = str.charAt(i);
           if (c == 'E') {
               eIndex = i;
           } else if (c == ' ' && eIndex == i - 1) {
               // workaround Java 1.4 DecimalFormat bug
               res.append('+');
               continue;
           } else if (Character.isDigit(c) && eIndex == i - 1) {
               res.append('+');
           }
           res.append(c);
       }
       return res.toString();
   }
   
   /**
    * Return a string representation of this matrix by formatting the matrix elements with the given {@link NumberFormat}.
    * 
    * @param formatter
    *          the {@link NumberFormat} used to format the matrix values with
    * @return the string representation
    */
   public String toString(NumberFormat formatter) {
       return Runtime.format(m00, formatter) + " " + Runtime.format(m10, formatter) + " " + Runtime.format(m20, formatter) + " " + Runtime.format(m30, formatter) + "\n"
            + Runtime.format(m01, formatter) + " " + Runtime.format(m11, formatter) + " " + Runtime.format(m21, formatter) + " " + Runtime.format(m31, formatter) + "\n"
            + Runtime.format(m02, formatter) + " " + Runtime.format(m12, formatter) + " " + Runtime.format(m22, formatter) + " " + Runtime.format(m32, formatter) + "\n";
   }

}
