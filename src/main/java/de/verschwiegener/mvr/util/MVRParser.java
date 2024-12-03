package de.verschwiegener.mvr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.verschwiegener.mvr.GeneralSceneDescription;
import de.verschwiegener.mvr.auxData.Symdef;
import de.verschwiegener.mvr.layer.Classing;
import de.verschwiegener.mvr.layer.Layer;

public class MVRParser {

	/**
	 * Parent Folder into which all MVR Files get extracted
	 */
	public static File mvrExtractFolder;

	private GeneralSceneDescription description;
	/**
	 * Path of the extracted MVR File
	 */
	private File mvrOutputFolder;
	/**
	 * Path of the Compressed MVR File
	 */
	private File mvrFile;

	private static SchemaFactory schemaFactory;
	private static Schema schema;

	static {
		try {
			schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			schema = schemaFactory.newSchema(new StreamSource(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("xsd/mvr.xsd")));
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}


	public MVRParser(File mvrFile) {
		this.mvrFile = mvrFile;
		mvrOutputFolder = new File(mvrExtractFolder,
				mvrFile.getName().substring(0, mvrFile.getName().lastIndexOf(".")));
	}
	
	/**
	 * Parses the MVR File
	 * 
	 * @throws JAXBException
	 * @throws IOException 
	 */
	public void parse() throws JAXBException, IOException {
		mvrOutputFolder.mkdirs();
		//unzipFile(mvrFile, mvrOutputFolder);

		// https://bugs.openjdk.org/browse/JDK-8204933
		Locale.setDefault(Locale.ENGLISH);
		JAXBContext context = JAXBContext.newInstance("de.verschwiegener.mvr");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(schema);

		description = (GeneralSceneDescription) unmarshaller
				.unmarshal(new InputSource(new FileReader(new File(mvrOutputFolder, "GeneralSceneDescription.xml"))));
	}

	/**
	 * Returns all Layers in the MVR File
	 * 
	 * @return
	 */
	public List<Layer> getLayers() {
		return description.getScene().getLayers().getLayer();
	}

	/**
	 * Returns Layer with given Name, null if no layer with this name exists
	 * 
	 * @param name
	 * @return
	 */
	public Layer getLayerByUUID(UUID uuid) {
		return description.getScene().getLayers().getLayer().stream().filter(layer -> layer.uuid().equals(uuid))
				.findFirst().orElse(null);
	}

	/**
	 * Returns Layer with given Name, null if no layer with this name exists
	 * 
	 * @param name
	 * @return
	 */
	public Layer getLayerByName(String name) {
		return description.getScene().getLayers().getLayer().stream().filter(layer -> layer.getName().equals(name))
				.findFirst().orElse(null);
	}

	/**
	 * This node contains the graphics so the scene can refer to this, thus
	 * optimizing repetition of the geometry.
	 * 
	 * @param uuid
	 * @return
	 */
	public Symdef getSymdefByUUID(UUID uuid) {
		return description.getScene().getAUXData().getSymdef().stream().filter(symdef -> symdef.uuid().equals(uuid))
				.findFirst().orElse(null);
	}

	/**
	 * This node defines a logical grouping across different layers. Primarily used
	 * for controlling object visibility of objects across multiple Layers.
	 * 
	 * @param uuid
	 * @return
	 */
	public Classing getClassingByUUID(UUID uuid) {
		return description.getScene().getAUXData().getClazz().stream().filter(classing -> classing.uuid().equals(uuid))
				.findFirst().orElse(null);
	}

	public int getVersionMajor() {
		return description.getVerMajor();
	}

	public int getVersionMinor() {
		return description.getVerMinor();
	}

	/**
	 * Returns File out of MVR Folder
	 * 
	 * @param name
	 * @return
	 */
	public File getFile(String name) {
		return new File(mvrOutputFolder, name);
	}

	/**
	 * Returns Underlying MVR Code Structure
	 * 
	 * @return
	 */
	public GeneralSceneDescription getDescription() {
		return description;
	}

	private void unzipFile(File mvrFile, File mvrFolder) throws IOException {
		byte[] buffer = new byte[1024];
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(mvrFile))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				File newFile = newFile(mvrFolder, zipEntry);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					// fix for Windows-created archives
					File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent);
					}

					// write file content
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		}
	}

	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

}
