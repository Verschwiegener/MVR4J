package de.verschwiegener.mvr.util;

import java.lang.Math;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
/**
 * Matrix Class for simple Matrix Math
 * 
 * @author julius
 *
 */
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
	/**
	 * Resets the Matrix to an Identity matrix
	 */
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
	 * Returns Scale of the Matrix
	 * 
	 * @return double[] {scaleX, scaleY, scaleZ}
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
	
	/**
	 * Returns the Translation of the Matrix 
	 * 
	 * @return double[] {posX, posY, posZ}
	 */
	public double[] getTranslation() {
		double[] translation = new double[3];
		translation[0] = m30;
		translation[1] = m31;
		translation[2] = m32;
		return translation;
	}
	
	/**
	 * Returns the Translation of the Matrix in GDTF Scale
	 */
	public double[] getGDTFScaleTranslation() {
		double[] translation = new double[3];
		translation[0] = m30 / 1000;
		translation[1] = m31 / 1000;
		translation[2] = m32 / 1000;
		return translation;
	}
	
	public void addTranslation(double[] translation) {
		if (translation.length != 3)
			return;
		m30 += translation[0];
		m31 += translation[1];
		m32 += translation[2];
	}
	/**
	 * Sets the Rotation of this matrix 
	 * 
	 * @param radianX
	 * @param radianY
	 * @param radianZ
	 */
	public void setRotationXYZ(double radianX, double radianY, double radianZ) {		
		double sinX = Math.sin(radianX);
        double cosX = Math.sin(radianX + (Math.PI * 0.5));
        double sinY = Math.sin(radianY);
        double cosY = Math.sin(radianY + (Math.PI * 0.5));
        double sinZ = Math.sin(radianZ);
        double cosZ = Math.sin(radianZ + (Math.PI * 0.5));
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
		double[] scale = getScale();

		// Normalize Rotational Component
		double m00 = this.m00 / scale[0];
		double m01 = this.m01 / scale[0];
		double m02 = this.m02 / scale[0];
		double m10 = this.m10 / scale[1];
		double m11 = this.m11 / scale[1];
		double m12 = this.m12 / scale[1];
		double m20 = this.m20 / scale[2];
		double m21 = this.m21 / scale[2];
		double m22 = this.m22 / scale[2];

		// Calculate Quaternion
		double trace = m00 + m11 + m22;
		double[] quat = new double[4];

		if (trace > 0) {
			double s = Math.sqrt(trace + 1.0f) * 2; // s = 4 * w
			quat[3] = 0.25f * s;
			quat[0] = (m21 - m12) / s;
			quat[1] = (m02 - m20) / s;
			quat[2] = (m10 - m01) / s;
		} else if ((m00 > m11) && (m00 > m22)) {
			double s = Math.sqrt(1.0f + m00 - m11 - m22) * 2; // s = 4 * x
			quat[3] = (m21 - m12) / s;
			quat[0] = 0.25f * s;
			quat[1] = (m01 + m10) / s;
			quat[2] = (m02 + m20) / s;
		} else if (m11 > m22) {
			double s = Math.sqrt(1.0f + m11 - m00 - m22) * 2; // s = 4 * y
			quat[3] = (m02 - m20) / s;
			quat[0] = (m01 + m10) / s;
			quat[1] = 0.25f * s;
			quat[2] = (m12 + m21) / s;
		} else {
			double s = Math.sqrt(1.0f + m22 - m00 - m11) * 2; // s = 4 * z
			quat[3] = (m10 - m01) / s;
			quat[0] = (m02 + m20) / s;
			quat[1] = (m12 + m21) / s;
			quat[2] = 0.25f * s;
		}

		return quat;
	}
	
	/**
	 * Returns the Matrix Rotation as Radian
	 * 
	 * @return
	 */
	public double[] getRotationRadians() {
		//https://stackoverflow.com/a/15029416
		double x = Math.atan2(m21,m22);
		double y = Math.atan2(-m20, Math.sqrt(Math.pow(m21, 2) + Math.pow(m22, 2)));
		double z = Math.atan2(m10, m00);
		return new double[] {x, y, z};
	}
	/**
	 * Returns the Rotation as Angle
	 * 
	 * @return
	 */
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
       String str = toString(decimalFormat());
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
       return format(m00, formatter) + " " + format(m10, formatter) + " " + format(m20, formatter) + " " + format(m30, formatter) + "\n"
            + format(m01, formatter) + " " + format(m11, formatter) + " " + format(m21, formatter) + " " + format(m31, formatter) + "\n"
            + format(m02, formatter) + " " + format(m12, formatter) + " " + format(m22, formatter) + " " + format(m32, formatter) + "\n";
   }
   
   public static String format(double number, NumberFormat format) {
       if (Double.isNaN(number)) {
           return padLeft(format, " NaN");
       } else if (Double.isInfinite(number)) {
           return padLeft(format, number > 0.0 ? " +Inf" : " -Inf");
       }
       return format.format(number);
   }
   
   private static String padLeft(NumberFormat format, String str) {
       int len = format.format(0.0).length();
       StringBuffer sb = new StringBuffer();
       for (int i = 0; i < len - str.length() + 1; i++) {
           sb.append(" ");
       }
       return sb.append(str).toString();
   }
   
   private static NumberFormat decimalFormat() {
       NumberFormat df;
       char[] prec = new char[3];
       Arrays.fill(prec, '0');
       df = new DecimalFormat(" 0." + new String(prec) + "E0;-");
       return df;
   }

}
