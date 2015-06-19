package es.fjtorres.jasperReport.service;

public final class Objects {

   private Objects() {
      // Empty
   }

   public static void checkNull(final Object obj, final String message)
         throws IllegalArgumentException {
      checkExpression(obj == null, message);
   }

   public static void checkExpression(boolean expression, final String message) {
      if (expression) {
         throw new IllegalArgumentException(message);
      }
   }
}
