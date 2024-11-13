package de.verschwiegener.mvr.util;

import java.io.File;

/**
 * MVRModel holding File, FileType and Transformation Matrix
 * 
 * @author julius
 *
 */
public class MVRModel {

	private final File modelFile;
	private FileType fileType;

	private final MVRMatrix matrix;

	public MVRModel(File modelFile, MVRMatrix transformationMatrix) {
		this.modelFile = modelFile;
		this.matrix = transformationMatrix;
		if(modelFile != null) {
			this.fileType = FileType.fromExtension(
					modelFile.getName().substring(modelFile.getName().lastIndexOf(".") + 1, modelFile.getName().length()));
		}
	}

	public MVRMatrix getMatrix() {
		return matrix;
	}

	public File getModelFile() {
		return modelFile;
	}

	public FileType getFileType() {
		return fileType;
	}

	public enum FileType {

		TYPE_3DS(new String[] { "3ds" }), TYPE_GLTF(new String[] { "gltf", "glb" });

		private final String[] extension;

		FileType(String[] extension) {
			this.extension = extension;
		}

		public String[] getFileExtension() {
			return extension;
		}

		public static FileType fromExtension(String extension) {
			for (FileType type : FileType.values()) {
				for (String fExtension : type.getFileExtension()) {
					if (fExtension.equals(extension))
						return type;
				}
			}
			return null;
		}

	}
}
