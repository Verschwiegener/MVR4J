package de.verschwiegener.mvr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

public class MVRUtil {
	
	/**
	 * General Values
	 */
	public static String MVR_MATRIX = "Matrix";
	public static String MVR_CLASSING = "Classing";
	public static String MVR_SCENEOBJECT = "SceneObject";
	public static String MVR_FIXTURE = "Fixture";
	public static String MVR_GROUPOBJECT = "GroupObject";
	public static String MVR_FOCUSPOINT = "FocusPoint";
	public static String MVR_GEOMETRY3D = "Geometry3D";
	
	/**
	 * Fixture Values
	 */
	public static String MVR_GDTFSPEC = "GDTFSpec";
	public static String MVR_GDTFMODE = "GDTFMode";
	public static String MVR_FOCUS = "Focus";
	public static String MVR_POSITION = "Position";
	public static String MVR_DMXINVERT_PAN = "DMXInvertPan";
	public static String MVR_DMXINVERT_TILT = "DMXInvertTilt";
	public static String MVR_FUNCTION = "Function";
	public static String MVR_FIXTUREID = "FixtureID";
	public static String MVR_FIXTUREID_NUMERIC = "FixtureIDNumeric";
	public static String MVR_UNITNUMBER = "UnitNumber";
	public static String MVR_CHILDPOSITION = "ChildPosition";
	public static String MVR_CUSTOMID = "CustomID";
	public static String MVR_CUSTOMIDTYPE = "CustomIDType";
	public static String MVR_COLOR = "Color";
	public static String MVR_CASTSHADOW = "CastShadow";
	public static String MVR_MAPPINGS = "Mappings";
	public static String MVR_GOBO = "Gobo";
	public static String MVR_CHILDLIST = "ChildList";
	public static String MVR_ADDRESSES = "Addresses";
	public static String MVR_PROTOCOLS = "Protocols";
	public static String MVR_ALIGNMENTS = "Alignments";
	public static String MVR_CUSTOMCOMMANDS = "CustomCommands";
	public static String MVR_OVERWRITES = "Overwrites";
	public static String MVR_CONNECTIONS = "Connections";
	
	public static String MVR_GEOMETRIES = "Geometries";
	
	
	/**
	 * Returns List containing all Objects with given objectName
	 * 
	 * @param <T>
	 * @param objectName
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObject(String objectName, List<Serializable> content) {
    	List<T> sceneObjects = new ArrayList<>();
    	
    	content.forEach(s -> {
    		if(s instanceof JAXBElement<?> element) {
    			if(element.getName().getLocalPart().equals(objectName))
    				sceneObjects.add((T) element.getValue());
    		}
    	});
    	return sceneObjects;
    }
	
}
