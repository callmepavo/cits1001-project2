public class Run {

    public static void main(String[] args) {
      int dimensions = 9;
  
      if (args.length >= 1) {
        try {
          int n = java.lang.Integer.parseInt(args[0]);
          if (n < 16) dimensions = n;
        } catch (NumberFormatException ex) {
          ex.printStackTrace();
        }
      }
      AquariumViewer av = new AquariumViewer(dimensions);
    }
  }
  