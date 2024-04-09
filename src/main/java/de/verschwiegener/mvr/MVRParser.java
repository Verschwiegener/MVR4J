package de.verschwiegener.mvr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MVRParser {
	
	private SchemaFactory schemaFactory;
	private Schema schema;
	
	public MVRParser() throws SAXException, FileNotFoundException {
		schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schema = schemaFactory.newSchema(new StreamSource(getClass().getResourceAsStream("xsd/mvr.xsd")));
	}

	public GeneralSceneDescriptionType parseGDTF(File mvrFile, File mvrOutputFolder) throws Exception {
		mvrOutputFolder = new File(mvrOutputFolder, mvrFile.getName().split("\\.")[0]);
		unzipFile(mvrFile, mvrOutputFolder);

		Unmarshaller unmarshaller = JAXBContext.newInstance(GeneralSceneDescriptionType.class).createUnmarshaller();
		unmarshaller.setSchema(schema);

		GeneralSceneDescriptionType root = (GeneralSceneDescriptionType) unmarshaller
				.unmarshal(new InputSource(new FileReader(new File(mvrOutputFolder, "description.xml"))));
		return root;
	}

	private void unzipFile(File gdtfFile, File gdtfFolder) throws IOException {
		byte[] buffer = new byte[1024];
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(gdtfFile))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				File newFile = newFile(gdtfFolder, zipEntry);
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
