package es.fjtorres.jasperReport.service.impl;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import es.fjtorres.jasperReport.service.Format;
import es.fjtorres.jasperReport.service.IReportLoader;
import es.fjtorres.jasperReport.service.IReportService;
import es.fjtorres.jasperReport.service.Report;

public class DefaultReportServiceImplTest {

   private IReportService reportService;

   private String reportLocation;

   @Mock
   private IReportLoader mockReportLoader;

   @BeforeTest
   private void beforeTest() throws Exception {
      MockitoAnnotations.initMocks(this);
      reportLocation = Paths.get(ClassLoader.getSystemResource("").toURI()).toString();
      reportService = new DefaultReportServiceImpl(reportLocation, mockReportLoader);
   }

   @Test
   public void generatePdfTest() throws Exception {
      generateTest2(Format.PDF);
   }

   @Test
   public void generateXlsTest() throws Exception {
      generateTest2(Format.XLS);
   }

   @Test
   public void generateXlsxTest() throws Exception {
      generateTest2(Format.XLSX);
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void generateNullTest() throws Exception {
      reportService.generate(null, true);
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void generateInvalidTest() throws Exception {
      reportService.generate(new Report<Model>(), true);
   }

   private void generateTest2(final Format testFormat) throws Exception {
      final List<Model> dataSourceCollection = new ArrayList<Model>();
      dataSourceCollection.add(new Model(1111, "STRING", new Date()));
      dataSourceCollection.add(new Model(Integer.MAX_VALUE, "STRING", new Date()));
      dataSourceCollection.add(new Model(Integer.MIN_VALUE, "STRING", new Date()));

      String outputStr = "OUTPUT_REPORT.";
      switch (testFormat) {
      case PDF:
         outputStr += Format.PDF.getExtension();
         break;
      case XLS:
         outputStr += Format.XLS.getExtension();
         break;
      case XLSX:
         outputStr += Format.XLSX.getExtension();
         break;
      }

      final Report<Model> report = new Report<Model>();
      report.setOutputName(outputStr);
      report.setFormat(testFormat);
      report.setData(dataSourceCollection);
      report.setTemplate("test.jrxml");

      Mockito.when(mockReportLoader.load(report, reportLocation)).thenReturn(
            JasperCompileManager.compileReport(reportLocation + File.separator
                  + report.getTemplate()));

      byte[] reportBytes = reportService.generate(report, true);

      Assert.assertNotNull(reportBytes, "report");

      final Path outputReport = Paths.get(reportLocation).resolve(outputStr);
      Files.deleteIfExists(outputReport);
      Files.write(outputReport, reportBytes);
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
