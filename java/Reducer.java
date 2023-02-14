import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reducer {

    static String currentKey = null;
    static int currentValue = 0;

    static void reduce(String key, int value) {
        if (currentKey.equals(key)) {
            currentValue += value;
        } else {
            System.out.println(String.format("%s\t%s", currentKey, currentValue));

            currentKey = key;
            currentValue = value;
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            String[] tokens;

            line = reader.readLine();
            tokens = line.split("\t");
            currentKey = tokens[0];
            currentValue = Integer.parseInt(tokens[1]);

            while ((line = reader.readLine()) != null) {
                tokens = line.split("\t");
                try {
                    reduce(tokens[0], Integer.parseInt(tokens[1]));
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    System.out.println("Line: " + line);
                    System.exit(1);
                }
            }

            System.out.println(String.format("%s\t%s", currentKey, currentValue));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
