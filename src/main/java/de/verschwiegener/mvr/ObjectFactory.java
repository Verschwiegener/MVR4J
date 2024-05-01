//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr;

import javax.xml.bind.annotation.XmlRegistry;

import de.verschwiegener.mvr.auxData.Address;
import de.verschwiegener.mvr.auxData.Addresses;
import de.verschwiegener.mvr.auxData.Symdef;
import de.verschwiegener.mvr.layer.AUXData;
import de.verschwiegener.mvr.layer.ChildList;
import de.verschwiegener.mvr.layer.Classing;
import de.verschwiegener.mvr.layer.Layer;
import de.verschwiegener.mvr.layer.Layers;
import de.verschwiegener.mvr.layer.type.Fixture;
import de.verschwiegener.mvr.layer.type.FocusPoint;
import de.verschwiegener.mvr.layer.type.GroupObject;
import de.verschwiegener.mvr.layer.type.Projector;
import de.verschwiegener.mvr.layer.type.SceneObject;
import de.verschwiegener.mvr.layer.type.Support;
import de.verschwiegener.mvr.layer.type.Truss;
import de.verschwiegener.mvr.layer.type.VideoScreen;
import de.verschwiegener.mvr.nodes.Alignment;
import de.verschwiegener.mvr.nodes.Alignments;
import de.verschwiegener.mvr.nodes.Connection;
import de.verschwiegener.mvr.nodes.Connections;
import de.verschwiegener.mvr.nodes.CustomCommands;
import de.verschwiegener.mvr.nodes.Data;
import de.verschwiegener.mvr.nodes.Geometries;
import de.verschwiegener.mvr.nodes.Geometry3D;
import de.verschwiegener.mvr.nodes.Gobo;
import de.verschwiegener.mvr.nodes.Mapping;
import de.verschwiegener.mvr.nodes.MappingDefinition;
import de.verschwiegener.mvr.nodes.Mappings;
import de.verschwiegener.mvr.nodes.Network;
import de.verschwiegener.mvr.nodes.Overwrite;
import de.verschwiegener.mvr.nodes.Overwrites;
import de.verschwiegener.mvr.nodes.Position;
import de.verschwiegener.mvr.nodes.Projection;
import de.verschwiegener.mvr.nodes.Projections;
import de.verschwiegener.mvr.nodes.Protocol;
import de.verschwiegener.mvr.nodes.Protocols;
import de.verschwiegener.mvr.nodes.ScaleHandeling;
import de.verschwiegener.mvr.nodes.Source;
import de.verschwiegener.mvr.nodes.Sources;
import de.verschwiegener.mvr.nodes.Symbol;
import de.verschwiegener.mvr.nodes.UserData;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.verschwiegener.mvr package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.verschwiegener.mvr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GeneralSceneDescription }
     * 
     */
    public GeneralSceneDescription createGeneralSceneDescription() {
        return new GeneralSceneDescription();
    }

    /**
     * Create an instance of {@link UserData }
     * 
     */
    public UserData createUserData() {
        return new UserData();
    }

    /**
     * Create an instance of {@link Scene }
     * 
     */
    public Scene createScene() {
        return new Scene();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link AUXData }
     * 
     */
    public AUXData createAUXData() {
        return new AUXData();
    }

    /**
     * Create an instance of {@link Classing }
     * 
     */
    public Classing createClassing() {
        return new Classing();
    }

    /**
     * Create an instance of {@link Symdef }
     * 
     */
    public Symdef createSymdef() {
        return new Symdef();
    }

    /**
     * Create an instance of {@link Position }
     * 
     */
    public Position createPosition() {
        return new Position();
    }

    /**
     * Create an instance of {@link MappingDefinition }
     * 
     */
    public MappingDefinition createMappingDefinition() {
        return new MappingDefinition();
    }

    /**
     * Create an instance of {@link Layers }
     * 
     */
    public Layers createLayers() {
        return new Layers();
    }

    /**
     * Create an instance of {@link Layer }
     * 
     */
    public Layer createLayer() {
        return new Layer();
    }

    /**
     * Create an instance of {@link SceneObject }
     * 
     */
    public SceneObject createSceneObject() {
        return new SceneObject();
    }

    /**
     * Create an instance of {@link GroupObject }
     * 
     */
    public GroupObject createGroupObject() {
        return new GroupObject();
    }

    /**
     * Create an instance of {@link FocusPoint }
     * 
     */
    public FocusPoint createFocusPoint() {
        return new FocusPoint();
    }

    /**
     * Create an instance of {@link Fixture }
     * 
     */
    public Fixture createFixture() {
        return new Fixture();
    }

    /**
     * Create an instance of {@link Gobo }
     * 
     */
    public Gobo createGobo() {
        return new Gobo();
    }

    /**
     * Create an instance of {@link Addresses }
     * 
     */
    public Addresses createAddresses() {
        return new Addresses();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Alignments }
     * 
     */
    public Alignments createAlignments() {
        return new Alignments();
    }

    /**
     * Create an instance of {@link Alignment }
     * 
     */
    public Alignment createAlignment() {
        return new Alignment();
    }

    /**
     * Create an instance of {@link CustomCommands }
     * 
     */
    public CustomCommands createCustomCommands() {
        return new CustomCommands();
    }

    /**
     * Create an instance of {@link Overwrites }
     * 
     */
    public Overwrites createOverwrites() {
        return new Overwrites();
    }

    /**
     * Create an instance of {@link Overwrite }
     * 
     */
    public Overwrite createOverwrite() {
        return new Overwrite();
    }

    /**
     * Create an instance of {@link Connections }
     * 
     */
    public Connections createConnections() {
        return new Connections();
    }

    /**
     * Create an instance of {@link Connection }
     * 
     */
    public Connection createConnection() {
        return new Connection();
    }

    /**
     * Create an instance of {@link Mappings }
     * 
     */
    public Mappings createMappings() {
        return new Mappings();
    }

    /**
     * Create an instance of {@link Mapping }
     * 
     */
    public Mapping createMapping() {
        return new Mapping();
    }

    /**
     * Create an instance of {@link Truss }
     * 
     */
    public Truss createTruss() {
        return new Truss();
    }

    /**
     * Create an instance of {@link Support }
     * 
     */
    public Support createSupport() {
        return new Support();
    }

    /**
     * Create an instance of {@link VideoScreen }
     * 
     */
    public VideoScreen createVideoScreen() {
        return new VideoScreen();
    }

    /**
     * Create an instance of {@link Projector }
     * 
     */
    public Projector createProjector() {
        return new Projector();
    }

    /**
     * Create an instance of {@link Projections }
     * 
     */
    public Projections createProjections() {
        return new Projections();
    }

    /**
     * Create an instance of {@link Projection }
     * 
     */
    public Projection createProjection() {
        return new Projection();
    }

    /**
     * Create an instance of {@link Sources }
     * 
     */
    public Sources createSources() {
        return new Sources();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link ScaleHandeling }
     * 
     */
    public ScaleHandeling createScaleHandeling() {
        return new ScaleHandeling();
    }

    /**
     * Create an instance of {@link Geometries }
     * 
     */
    public Geometries createGeometries() {
        return new Geometries();
    }

    /**
     * Create an instance of {@link Symbol }
     * 
     */
    public Symbol createSymbol() {
        return new Symbol();
    }

    /**
     * Create an instance of {@link Geometry3D }
     * 
     */
    public Geometry3D createGeometry3D() {
        return new Geometry3D();
    }

    /**
     * Create an instance of {@link ChildList }
     * 
     */
    public ChildList createChildList() {
        return new ChildList();
    }

    /**
     * Create an instance of {@link Network }
     * 
     */
    public Network createNetwork() {
        return new Network();
    }

    /**
     * Create an instance of {@link Protocols }
     * 
     */
    public Protocols createProtocols() {
        return new Protocols();
    }

    /**
     * Create an instance of {@link Protocol }
     * 
     */
    public Protocol createProtocol() {
        return new Protocol();
    }

}
