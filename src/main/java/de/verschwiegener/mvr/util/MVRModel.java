package de.verschwiegener.mvr.util;

import java.io.File;

/**
 * MVRModel holding File, FileType and Transformatio Matrix
 * 
 * @author julius
 *
 */
public class MVRModel {
	
	private File modelFile;
	private FileType fileType;
	
	private MVRMatrix transformationMatrix;
	
	public MVRModel(File modelFile, MVRMatrix transformationMatrix) {
		this.modelFile = modelFile;
		this.transformationMatrix = transformationMatrix;
		this.fileType = FileType.fromExtension(modelFile.getName().substring(modelFile.getName().lastIndexOf(".") + 1, modelFile.getName().length()));
		if(fileType == FileType.TYPE_3DS) {
			transformationMatrix.setRotationXYZ((float) Math.toRadians(90f), 0, 0);
		}
	}

	public MVRMatrix getTransformationMatrix() {
		return transformationMatrix;
	}
	
	public File getModelFile() {
		return modelFile;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
	/**
	 * Transforms matrix Scale/Rotation/Position with given Matrix
	 * 
	 * @param other          Matrix to transform with
	 * @param ignoreScaling  Whether or not to ignore Scaling
	 * @param ignoreRotation Whether or not to ignore Rotation
	 * 
	 * 
	 *                       The Layer object should have only a elevation and
	 *                       nothing else
	 * 
	 *                       A Geometry or a Symbol do not have any restriction on
	 *                       their matrix
	 * 
	 *                       All other object (Projector, Fixture, Truss, Scene
	 *                       Object, ...) have only a rotation and offset, but no
	 *                       scale.
	 * 
	 * 
	 */
	public void transform(MVRMatrix other, boolean ignoreScaling, boolean ignoreRotation) {
		if(!ignoreScaling) {
			transformationMatrix.m00 = transformationMatrix.m00 * other.m00;
			transformationMatrix.m11 = transformationMatrix.m11 * other.m11;
			transformationMatrix.m22 = transformationMatrix.m22 * other.m22;
		}
		if(!ignoreRotation) {
		double[] otherRotation = other.getRotationAngles();
		double[] thisRotation = transformationMatrix.getRotationAngles();
		
		transformationMatrix.setRotationXYZ((float) Math.toRadians(otherRotation[0] + thisRotation[0]), (float) Math.toRadians(otherRotation[1] + thisRotation[1]),
				(float) Math.toRadians(otherRotation[2] + thisRotation[2]));
		
		}
		
		transformationMatrix.offsetPos(other.getTranslation());
	}
	
	
	
	
	public enum FileType {
		
		TYPE_3DS(new String[]{"3ds"}),
		TYPE_GLTF(new String[] {"gltf", "glb"});
		
		private final String[] extension;
		
		FileType(String[] extension) {
			this.extension = extension;
		}
		
		public String[] getFileExtension() {
			return extension;
		}
		
		public static FileType fromExtension(String extension) {
			for(FileType type : FileType.values()) {
				for(String fExtension : type.getFileExtension()) {
					if(fExtension.equals(extension))
						return type;
				}
			}
			return null;
		}
		
	}
}
