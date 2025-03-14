package ca.nrc.cadc.caom2;



import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 * Created on 06/02/2025 by Paul Harrison (paul.harrison@manchester.ac.uk).
 */

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.ivoa.dm.caom2.*;
import org.ivoa.vodml.testing.AutoDBRoundTripTest;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


//IMP - this test is very minimal - not really testing the model much - just to make sure that the JPA mapping will work.
public class SerialisationTest extends AutoDBRoundTripTest<Caom2Model, Long, SimpleObservation > {
   private SimpleObservation simpleObs;


   @Override
   public SimpleObservation entityForDb() {
      return simpleObs;
   }

   @Override
   public void testEntity(SimpleObservation simpleObservation) {
      //should do a test!
   }

   @Override
   public Caom2Model createModel() {

      simpleObs = (SimpleObservation) new SimpleObservation().withCollection("collection").withIntent(ObservationIntentType.CALIBRATION)
            .withUri("http://www.test/")
            .withAlgorithm(new Algorithm("algorithm"))
            .withUriBucket("http://www.test.uribucket/")


      ;

      Caom2Model caom2Model = new Caom2Model();
      caom2Model.addContent(simpleObs);

      return caom2Model;
   }

   @Override
   public void testModel(Caom2Model caom2Model) {
      //should do a test!
   }

   @Override
   protected String setDbDumpFile() {
      return "CAOM_dump.sql";
   }

   @Test
   public void testSerialiseObservationOnly() throws JAXBException {
      Observation dro = new DerivedObservation().withCollection("collection").withIntent(ObservationIntentType.CALIBRATION)
            .withUri("http://www.test/")
            .withAlgorithm(new Algorithm("algorithm"));

      JAXBContext jc = Caom2Model.contextFactory();
      StringWriter sw = new StringWriter();
      Marshaller m = jc.createMarshaller();

      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      JAXBElement<Observation> el = new JAXBElement<Observation>(new QName("http://www.opencadc.org/caom2/xml/v2.5","Observation"), Observation.class, dro);
      m.marshal(el, sw);
      System.out.println(sw.toString());
   }
   @Test
   public void testUnmarshall() throws JAXBException {
       String xml = "<caom2:Observation xsi:type=\"caom2:DerivedObservation\" xmlns:caom2=\"http://www.opencadc.org/caom2/xml/v2.5\" xmlns:ivoa=\"http://ivoa.net/vodml/ivoa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  caom2:id=\"ab3e2f74-9e91-4e72-9a77-dd80555d7543\">\n"
               + "    <caom2:collection>collection</caom2:collection>\n"
               + "    <caom2:uri>http://www.test/</caom2:uri>\n"
               + "    <caom2:uriBucket>e36</caom2:uriBucket>\n"
               + "    <caom2:intent>calibration</caom2:intent>\n"
               + "    <caom2:algorithm>\n"
               + "        <caom2:name>algorithm</caom2:name>\n"
               + "    </caom2:algorithm>\n"
               + "    <caom2:planes/>\n"
               + "</caom2:Observation>\n";
       JAXBContext jc = Caom2Model.contextFactory();
       Unmarshaller um = jc.createUnmarshaller();
       Observation o = um.unmarshal(new javax.xml.transform.stream.StreamSource(new StringReader(xml)),Observation.class).getValue();
       assertNotNull(o);
       DerivedObservation dobs = (DerivedObservation) o;
       assertNotNull(dobs);
       
               
   }
}
