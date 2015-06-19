package es.fjtorres.jasperReport.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author fjtorres
 *
 */
public class Report<T> implements Serializable {

   /**
    * SerivalVersionUID.
    */
   private static final long serialVersionUID = 6749473507699860696L;

   private String outputName;

   private Format format;

   private Map<String, Object> parameters;

   private String template;

   private List<T> data;

   public boolean validate() {
      boolean isInValid = StringUtils.isBlank(outputName) || StringUtils.isBlank(template)
            || format == null;

      return !isInValid;
   }

   public String getOutputName() {
      return outputName;
   }

   public void setOutputName(String pOutputName) {
      outputName = pOutputName;
   }

   public Format getFormat() {
      return format;
   }

   public void setFormat(Format pFormat) {
      format = pFormat;
   }

   public Map<String, Object> getParameters() {
      return parameters;
   }

   public void setParameters(Map<String, Object> pParameters) {
      parameters = pParameters;
   }

   public String getTemplate() {
      return template;
   }

   public void setTemplate(String pTemplate) {
      template = pTemplate;
   }

   public List<T> getData() {
      return data;
   }

   public void setData(List<T> pData) {
      data = pData;
   }
}
