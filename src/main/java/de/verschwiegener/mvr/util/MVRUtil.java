package de.verschwiegener.mvr.util;


import java.io.File;
import java.util.List;
import java.util.UUID;

import de.verschwiegener.mvr.GeneralSceneDescription;
import de.verschwiegener.mvr.MVRParser;
import de.verschwiegener.mvr.Scene;
import de.verschwiegener.mvr.auxData.Symdef;
import de.verschwiegener.mvr.layer.Classing;
import de.verschwiegener.mvr.layer.Layer;
import de.verschwiegener.mvr.layer.type.Fixture;
import de.verschwiegener.mvr.layer.type.FocusPoint;
import de.verschwiegener.mvr.nodes.MappingDefinition;
import de.verschwiegener.mvr.nodes.Position;

public class MVRUtil {
	
	private static File mvrExportFolder;
	
	private GeneralSceneDescription description;
	private File mvrOutputFolder;
	
	public void load(GeneralSceneDescription description) {
		this.description = description;
	}
	
	public MVRUtil(GeneralSceneDescription description) {
		this.description = description;
	}
	
	public MVRUtil(File mvrFile) throws Exception {
		mvrOutputFolder = new File(mvrExportFolder, mvrFile.getName().substring(0, mvrFile.getName().lastIndexOf(".")));
		MVRParser parser = new MVRParser();
		description = parser.parseMVR(mvrFile, mvrOutputFolder);
	}
	
	/**
	 * Returns all Layers in the MVR File
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
	public Layer getLayerByName(String name) {
		return description.getScene().getLayers().getLayer().stream().filter(layer -> layer.getName().equals(name)).findFirst().orElse(null);
	}
	
	/**
	 * This node contains the graphics so the scene can refer to this, thus optimizing repetition of the geometry.
	 * 
	 * @param uuid
	 * @return
	 */
	public Symdef getSymdefByUUID(UUID uuid) {
		return description.getScene().getAUXData().getSymdef().stream().filter(symdef -> symdef.uuid().equals(uuid)).findFirst().orElse(null);
	}
	
	/**
	 * Returns Layer with given Name, null if no layer with this name exists
	 * 
	 * @param name
	 * @return
	 */
	public Layer getLayerByUUID(UUID uuid) {
		return description.getScene().getLayers().getLayer().stream().filter(layer -> layer.uuid().equals(uuid)).findFirst().orElse(null);
	}
	
	/**
	 * This node defines a logical grouping across different layers. Primarily used for controlling object visibility of objects across multiple Layers.
	 * @param uuid
	 * @return
	 */
	public Classing getClassingByUUID(UUID uuid) {
		return description.getScene().getAUXData().getClazz().stream().filter(classing -> classing.getUUID().equals(uuid)).findFirst().orElse(null);
	}
	
	public int getVersionMajor() {
		return description.getVerMajor();
	}
	
	public int getVersionMinor() {
		return description.getVerMinor();
	}
	/**
	 * Returns File out of MVR File
	 * @param name
	 * @return
	 */
	public File getFile(String name) {
		return new File(mvrOutputFolder, name);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This node defines a logical grouping across different layers. Primarily used for controlling object visibility of objects across multiple Layers.
	 * @param uuid
	 * @return
	 */
	public Classing getClassingByName(UUID uuid) {
		return description.getScene().getAUXData().getClazz().stream().filter(classing -> classing.getUUID().equals(uuid)).findFirst().orElse(null);
	}
	
	/**
	 * This node defines a logical grouping of lighting devices and trusses.
	 * @param uuid
	 * @return
	 */
	public Position getPositionByUUID(UUID uuid) {
		return description.getScene().getAUXData().getPosition().stream().filter(position -> position.getUUID().equals(uuid)).findFirst().orElse(null);
	}
	/**
	 * This node specifies an input source for fixture color mapping applications. 
	 * 
	 * @param uuid
	 * @return
	 */
	public MappingDefinition getMappingDefinitionByUUID(UUID uuid) {
		return description.getScene().getAUXData().getMappingDefinition().stream().filter(definition -> definition.getUUID().equals(uuid)).findFirst().orElse(null);
	}
	
	
	
	
	
	public List<Fixture> getFixturesFromLayer(Layer layer) {
		return layer.getChildList().getFixture();
	}
	
	public Fixture getFixtureFromLayerByUUID(Layer layer, UUID UUID) {
		return getFixturesFromLayer(layer).stream().filter(fixture -> fixture.uuid().equals(UUID)).findFirst().orElse(null);
	}
	
	public List<FocusPoint> getFocusPointsFromLayer(Layer layer) {
		return layer.getChildList().getFocusPoint();
	}
	
}
