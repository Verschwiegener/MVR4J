//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//


package de.verschwiegener.mvr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import de.verschwiegener.mvr.auxData.AddressType;
import de.verschwiegener.mvr.auxData.AddressesType;
import de.verschwiegener.mvr.auxData.FixtureType;
import de.verschwiegener.mvr.auxData.GeometriesType;
import de.verschwiegener.mvr.auxData.LayerType;
import de.verschwiegener.mvr.auxData.LayersType;
import de.verschwiegener.mvr.auxData.SceneObjectType;
import de.verschwiegener.mvr.auxData.SceneType;
import de.verschwiegener.mvr.auxData.SymbolType;
import de.verschwiegener.mvr.layer.AUXDataType;
import de.verschwiegener.mvr.layer.ChildListType;
import de.verschwiegener.mvr.layer.ClassType;
import de.verschwiegener.mvr.layer.Geometry3DType;
import de.verschwiegener.mvr.layer.SymdefType;


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

    private final static QName _GeneralSceneDescription_QNAME = new QName("", "GeneralSceneDescription");
    private final static QName _Geometry3DTypeMatrix_QNAME = new QName("", "Matrix");
    private final static QName _ChildListTypeSceneObject_QNAME = new QName("", "SceneObject");
    private final static QName _ChildListTypeFixture_QNAME = new QName("", "Fixture");
    private final static QName _ChildListTypeGeometry3D_QNAME = new QName("", "Geometry3D");
    private final static QName _FixtureTypeGDTFSpec_QNAME = new QName("", "GDTFSpec");
    private final static QName _FixtureTypeGDTFMode_QNAME = new QName("", "GDTFMode");
    private final static QName _FixtureTypeAddresses_QNAME = new QName("", "Addresses");
    private final static QName _FixtureTypeFixtureID_QNAME = new QName("", "FixtureID");
    private final static QName _FixtureTypeUnitNumber_QNAME = new QName("", "UnitNumber");
    private final static QName _FixtureTypeFixtureTypeId_QNAME = new QName("", "FixtureTypeId");
    private final static QName _FixtureTypeCustomId_QNAME = new QName("", "CustomId");
    private final static QName _FixtureTypeColor_QNAME = new QName("", "Color");
    private final static QName _FixtureTypeCastShadow_QNAME = new QName("", "CastShadow");
    private final static QName _FixtureTypeMappings_QNAME = new QName("", "Mappings");
    private final static QName _FixtureTypeGeometries_QNAME = new QName("", "Geometries");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.verschwiegener.mvr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GeneralSceneDescriptionType }
     * 
     */
    public GeneralSceneDescriptionType createGeneralSceneDescriptionType() {
        return new GeneralSceneDescriptionType();
    }

    /**
     * Create an instance of {@link SceneObjectType }
     * 
     */
    public SceneObjectType createSceneObjectType() {
        return new SceneObjectType();
    }

    /**
     * Create an instance of {@link SymbolType }
     * 
     */
    public SymbolType createSymbolType() {
        return new SymbolType();
    }

    /**
     * Create an instance of {@link GeometriesType }
     * 
     */
    public GeometriesType createGeometriesType() {
        return new GeometriesType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link AddressesType }
     * 
     */
    public AddressesType createAddressesType() {
        return new AddressesType();
    }

    /**
     * Create an instance of {@link FixtureType }
     * 
     */
    public FixtureType createFixtureType() {
        return new FixtureType();
    }

    /**
     * Create an instance of {@link ChildListType }
     * 
     */
    public ChildListType createChildListType() {
        return new ChildListType();
    }

    /**
     * Create an instance of {@link LayerType }
     * 
     */
    public LayerType createLayerType() {
        return new LayerType();
    }

    /**
     * Create an instance of {@link LayersType }
     * 
     */
    public LayersType createLayersType() {
        return new LayersType();
    }

    /**
     * Create an instance of {@link ClassType }
     * 
     */
    public ClassType createClassType() {
        return new ClassType();
    }

    /**
     * Create an instance of {@link Geometry3DType }
     * 
     */
    public Geometry3DType createGeometry3DType() {
        return new Geometry3DType();
    }

    /**
     * Create an instance of {@link SymdefType }
     * 
     */
    public SymdefType createSymdefType() {
        return new SymdefType();
    }

    /**
     * Create an instance of {@link AUXDataType }
     * 
     */
    public AUXDataType createAUXDataType() {
        return new AUXDataType();
    }

    /**
     * Create an instance of {@link SceneType }
     * 
     */
    public SceneType createSceneType() {
        return new SceneType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneralSceneDescriptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GeneralSceneDescriptionType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "GeneralSceneDescription")
    public JAXBElement<GeneralSceneDescriptionType> createGeneralSceneDescription(GeneralSceneDescriptionType value) {
        return new JAXBElement<GeneralSceneDescriptionType>(_GeneralSceneDescription_QNAME, GeneralSceneDescriptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Matrix", scope = Geometry3DType.class)
    public JAXBElement<String> createGeometry3DTypeMatrix(String value) {
        return new JAXBElement<String>(_Geometry3DTypeMatrix_QNAME, String.class, Geometry3DType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SceneObjectType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SceneObjectType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "SceneObject", scope = ChildListType.class)
    public JAXBElement<SceneObjectType> createChildListTypeSceneObject(SceneObjectType value) {
        return new JAXBElement<SceneObjectType>(_ChildListTypeSceneObject_QNAME, SceneObjectType.class, ChildListType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FixtureType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FixtureType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Fixture", scope = ChildListType.class)
    public JAXBElement<FixtureType> createChildListTypeFixture(FixtureType value) {
        return new JAXBElement<FixtureType>(_ChildListTypeFixture_QNAME, FixtureType.class, ChildListType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Geometry3DType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Geometry3DType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Geometry3D", scope = ChildListType.class)
    public JAXBElement<Geometry3DType> createChildListTypeGeometry3D(Geometry3DType value) {
        return new JAXBElement<Geometry3DType>(_ChildListTypeGeometry3D_QNAME, Geometry3DType.class, ChildListType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Matrix", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeMatrix(String value) {
        return new JAXBElement<String>(_Geometry3DTypeMatrix_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "GDTFSpec", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeGDTFSpec(String value) {
        return new JAXBElement<String>(_FixtureTypeGDTFSpec_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "GDTFMode", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeGDTFMode(String value) {
        return new JAXBElement<String>(_FixtureTypeGDTFMode_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddressesType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Addresses", scope = FixtureType.class)
    public JAXBElement<AddressesType> createFixtureTypeAddresses(AddressesType value) {
        return new JAXBElement<AddressesType>(_FixtureTypeAddresses_QNAME, AddressesType.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "FixtureID", scope = FixtureType.class)
    public JAXBElement<Byte> createFixtureTypeFixtureID(Byte value) {
        return new JAXBElement<Byte>(_FixtureTypeFixtureID_QNAME, Byte.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "UnitNumber", scope = FixtureType.class)
    public JAXBElement<Byte> createFixtureTypeUnitNumber(Byte value) {
        return new JAXBElement<Byte>(_FixtureTypeUnitNumber_QNAME, Byte.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "FixtureTypeId", scope = FixtureType.class)
    public JAXBElement<Byte> createFixtureTypeFixtureTypeId(Byte value) {
        return new JAXBElement<Byte>(_FixtureTypeFixtureTypeId_QNAME, Byte.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "CustomId", scope = FixtureType.class)
    public JAXBElement<Byte> createFixtureTypeCustomId(Byte value) {
        return new JAXBElement<Byte>(_FixtureTypeCustomId_QNAME, Byte.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Color", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeColor(String value) {
        return new JAXBElement<String>(_FixtureTypeColor_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "CastShadow", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeCastShadow(String value) {
        return new JAXBElement<String>(_FixtureTypeCastShadow_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Mappings", scope = FixtureType.class)
    public JAXBElement<String> createFixtureTypeMappings(String value) {
        return new JAXBElement<String>(_FixtureTypeMappings_QNAME, String.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeometriesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GeometriesType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Geometries", scope = FixtureType.class)
    public JAXBElement<GeometriesType> createFixtureTypeGeometries(GeometriesType value) {
        return new JAXBElement<GeometriesType>(_FixtureTypeGeometries_QNAME, GeometriesType.class, FixtureType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Matrix", scope = SceneObjectType.class)
    public JAXBElement<String> createSceneObjectTypeMatrix(String value) {
        return new JAXBElement<String>(_Geometry3DTypeMatrix_QNAME, String.class, SceneObjectType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeometriesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GeometriesType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "Geometries", scope = SceneObjectType.class)
    public JAXBElement<GeometriesType> createSceneObjectTypeGeometries(GeometriesType value) {
        return new JAXBElement<GeometriesType>(_FixtureTypeGeometries_QNAME, GeometriesType.class, SceneObjectType.class, value);
    }

}
