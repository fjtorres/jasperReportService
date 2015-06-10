package es.fjtorres.jasperReport.service.impl;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import es.fjtorres.jasperReport.service.Format;
import es.fjtorres.jasperReport.service.IReportService;

public class DefaultReportServiceImplTest {

   private IReportService reportService;

   private String reportLocation;

   @BeforeTest
   private void beforeTest() throws URISyntaxException {
      reportLocation = Paths.get(ClassLoader.getSystemResource("").toURI()).toString();
      reportService = new DefaultReportServiceImpl(reportLocation);
   }

   @Test
   public void generatePdfTest() throws Exception {
      generateTest(Format.PDF);
   }

   @Test
   public void generateXlsTest() throws Exception {
      generateTest(Format.XLS);
   }

   private void generateTest(final Format testFormat) throws Exception {
      final List<Model> dataSourceCollection = new ArrayList<Model>();
      dataSourceCollection.add(new Model(1111, "STRING", new Date()));
      dataSourceCollection.add(new Model(Integer.MAX_VALUE, "STRING", new Date()));
      dataSourceCollection.add(new Model(Integer.MIN_VALUE, "STRING", new Date()));

      byte[] report = reportService.generateReport(testFormat, "test.jrxml",
            new JRBeanCollectionDataSource(dataSourceCollection));

      Assert.assertNotNull(report, "report");
      String outputStr = "OUTPUT_REPORT";
      switch (testFormat) {
      case PDF:
         outputStr += ".pdf";
         break;
      case XLS:
         outputStr += ".xls";
         break;
      }
      
      final Path outputReport = Paths.get(reportLocation).resolve(outputStr);
      Files.deleteIfExists(outputReport);
      Files.write(outputReport, report);
      Assert.assertTrue(Files.exists(outputReport), "outputReport");
   }

   public class Model implements Serializable {
      private static final long serialVersionUID = 1L;
      private final Integer field1;
      private final String field2;
      private final Date field3;

      /**
       * @param pField1
       * @param pField2
       * @param pField3
       */
      public Model(Integer pField1, String pField2, Date pField3) {
         field1 = pField1;
         field2 = pField2;
         field3 = pField3;
      }

      public Integer getField1() {
         return field1;
      }

      public String getField2() {
         return field2;
      }

      public Date getField3() {
         return field3;
      }
   }
}
