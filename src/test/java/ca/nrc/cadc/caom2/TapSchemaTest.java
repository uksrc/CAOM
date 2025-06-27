package ca.nrc.cadc.caom2;


import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.ivoa.dm.caom2.Caom2Model;
import org.ivoa.dm.tapschema.Schema;
import org.ivoa.dm.tapschema.TapschemaModel;
import org.ivoa.vodml.validation.AbstractBaseValidation;
import org.junit.jupiter.api.Test;

import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Tests the TAP Schema
 * Created on 25/06/2025 by Paul Harrison (paul.harrison@manchester.ac.uk).
 */

public class TapSchemaTest  extends AbstractBaseValidation {

   @Test
   public void testSave() throws JAXBException {
      TapschemaModel tapschemaModel = new TapschemaModel();
      InputStream is = Caom2Model.TAPSchema();
//       String ss = new BufferedReader(new InputStreamReader(Caom2Model.TAPSchema())).lines().collect(Collectors.joining("\n"));
//      System.out.println(ss);
      JAXBContext jc = tapschemaModel.management().contextFactory();
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      JAXBElement<TapschemaModel> el = unmarshaller.unmarshal(new StreamSource(is), TapschemaModel.class);
      TapschemaModel model_in = el.getValue();
      org.ivoa.dm.tapschema.ColNameKeys.normalize(model_in); // note that this step is necessary before saving to the database to set up the table_name foreign keys
      EntityManager em =setupH2Db(TapschemaModel.pu_name(),TapschemaModel.modelDescription.allClassNames());
      em.getTransaction().begin();
      model_in.management().persistRefs(em);
      em.persist(model_in.getContent(Schema.class).get(0));
      em.getTransaction().commit();
      dumpDbData(em, "CAOM_dump_tapschema.sql");
   }

}
