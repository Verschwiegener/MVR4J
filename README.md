# MVR4J
Java implementation of [MVR and XChange](https://gdtf-share.com/), currently can only load and not create/save MVR File

## Usage

###  Decode MVR File
```
  //The Folder into which all MVR Files get decompressed, needs to be set only once
  MVRUtil.mvrExtractFolder = new File("MVRExtractFolder");

  //Loads MVR File
  MVRUtil util = new MVRUtil("MVRFile.mvr");

  //Parses the MVR File
  util.parse();

  //Goes through each Layer
  for(Layer layer : util.getLayers()) {
      if(layer.hasChildren()) {
          layer.getFixtures(); //Returns all Fixtures of this layer
      }
  }
```
### Encode MVR File
  Soon

### XChange
```
  //Creates XChange Station
  XChange xchange = new XChange(ProtocolMode.TCP, "XChangeStationName", XChangeMVRFilesDirectory);

  //Starts XChange Station
  xchange.start(new XChangeListener() {

    @Override
    public void stationLeave(Station station) {
    }

    @Override
    public void stationAdded(Station station) {
    }

    @Override
    public void newMVRFile(MVRFile file) {
    }

});
```
