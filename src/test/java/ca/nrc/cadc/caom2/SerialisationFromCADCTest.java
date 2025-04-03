package ca.nrc.cadc.caom2;


import jakarta.xml.bind.*;
import org.ivoa.dm.caom2.*;
import org.ivoa.vodml.testing.AutoDBRoundTripTest;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


//IMP - this test is very minimal - not really testing the model much - just to make sure that the JPA mapping will work.
public class SerialisationFromCADCTest extends AutoDBRoundTripTest<Caom2Model, String, DerivedObservation > {
   private DerivedObservation derivedObservation;


   @Override
   public DerivedObservation entityForDb() {
      return derivedObservation;
   }

   @Override
   public void testEntity(DerivedObservation derivedObservation) {
      //should do a test!
   }

   @Override
   public Caom2Model createModel() {

      try {
         createObs();
      } catch (JAXBException e) {
         throw new RuntimeException(e);
      }

      Caom2Model caom2Model = new Caom2Model();
      caom2Model.addContent(derivedObservation);

      return caom2Model;
   }

   private void createObs() throws JAXBException {
      JAXBContext jc = Caom2Model.contextFactory();
      Unmarshaller um = jc.createUnmarshaller();
      Observation o = um.unmarshal(new javax.xml.transform.stream.StreamSource(this.getClass().getResourceAsStream("/SampleDerived-CAOM-2.5.xml")),Observation.class).getValue();
      assertNotNull(o);
      derivedObservation = (DerivedObservation) o;
         }

   @Override
   public void testModel(Caom2Model caom2Model) {
      //should do a test!
   }

   @Override
   protected String setDbDumpFile() {
      return "CAOM_dump.sql";
   }


}
