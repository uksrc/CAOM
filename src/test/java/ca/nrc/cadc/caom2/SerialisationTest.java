package ca.nrc.cadc.caom2;



import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 * Created on 06/02/2025 by Paul Harrison (paul.harrison@manchester.ac.uk).
 */

import org.ivoa.dm.caom2.Algorithm;
import org.ivoa.dm.caom2.Caom2Model;
import org.ivoa.dm.caom2.ObservationIntentType;
import org.ivoa.dm.caom2.SimpleObservation;
import org.ivoa.vodml.testing.AutoDBRoundTripTest;

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


      ;

      Caom2Model caom2Model = new Caom2Model();
      caom2Model.addContent(simpleObs);

      return caom2Model;
   }

   @Override
   public void testModel(Caom2Model caom2Model) {
      //should do a test!
   }
}
