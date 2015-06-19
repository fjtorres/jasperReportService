package es.fjtorres.jasperReport.service.impl;

import java.io.File;
import java.nio.file.Paths;

import net.sf.jasperreports.engine.JasperReport;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import es.fjtorres.jasperReport.service.Format;
import es.fjtorres.jasperReport.service.IReportLoader;
import es.fjtorres.jasperReport.service.Report;
import es.fjtorres.jasperReport.service.impl.DefaultReportServiceImplTest.Model;

public class DefaultReportLoaderTest {

   private String reportLocation;
   private IReportLoader reportLoader;

   @BeforeTest
   public void beforeTest() throws Exception {
      reportLocation = Paths.get(ClassLoader.getSystemResource("").toURI()).toString();
      reportLoader = new DefaultReportLoader();
   }

   @Test
   public void loadReport() throws Exception {
      final Report<Model> report = new Report<Model>();
      report.setOutputName("TEST");
      report.setFormat(Format.PDF);
      report.setTemplate(reportLocation + File.separator + "test.jrxml");

      final JasperReport result = reportLoader.load(report);

      Assert.assertNotNull(result, "JasperReport is null");
   }

   @Test
   public void loadReportWithTemplatePath() throws Exception {
      final Report<Model> report = new Report<Model>();
      report.setOutputName("TEST");
      report.setFormat(Format.PDF);
      report.setTemplate("test.jrxml");

      final JasperReport result = reportLoader.load(report, reportLocation);

      Assert.assertNotNull(result, "JasperReport is null");
   }

}
