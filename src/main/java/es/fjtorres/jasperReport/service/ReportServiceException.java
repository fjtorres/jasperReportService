package es.fjtorres.jasperReport.service;

public class ReportServiceException extends Exception {

   /**
    * 
    */
   private static final long serialVersionUID = 5978290129110590170L;

   /**
    * @param pMessage
    * @param pCause
    */
   public ReportServiceException(String pMessage, Throwable pCause) {
      super(pMessage, pCause);
   }

}
