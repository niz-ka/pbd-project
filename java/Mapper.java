import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

// key: (street, zipCode, victimType, injuriesType)
// value: victimsNumber
// nullable: zipCode, street
// year > 2012

public class Mapper {
    final static class Record {
        static final int DATE = 0;
        static final int ZIP_CODE = 2;
        static final int FIRST_STREET = 6;
        static final int SECOND_STREET = 7;
        static final int THIRD_STREET = 8;
        static final int PEDESTRIANS_INJURED = 11;
        static final int PEDESTRIANS_KILLED = 12;
        static final int CYCLIST_INJURED = 13;
        static final int CYCLIST_KILLED = 14;
        static final int MOTORIST_INJURED = 15;
        static final int MOTORIST_KILLED = 16;
    }

    public static String getVictimType(int index) throws Exception {
        switch (index) {
            case Record.PEDESTRIANS_INJURED:
            case Record.PEDESTRIANS_KILLED:
                return "PEDESTRIAN";
            case Record.CYCLIST_INJURED:
            case Record.CYCLIST_KILLED:
                return "CYCLIST";
            case Record.MOTORIST_INJURED:
            case Record.MOTORIST_KILLED:
                return "MOTORIST";
            default:
                throw new Exception("Victim type unknown: " + index);
        }
    }

    public static String getInjuriesType(int index) throws Exception {
        switch (index) {
            case Record.PEDESTRIANS_INJURED:
            case Record.CYCLIST_INJURED:
            case Record.MOTORIST_INJURED:
                return "INJURED";
            case Record.PEDESTRIANS_KILLED:
            case Record.CYCLIST_KILLED:
            case Record.MOTORIST_KILLED:
                return "KILLED";
            default:
                throw new Exception("Injuries type unknown: " + index);
        }
    }

    public static void map(String[] row) throws Exception {
        String[] date = row[Record.DATE].split("/");
        if (date.length != 3) {
            return;
        }
        int year = Integer.parseInt(date[2]);
        String zipCode = row[Record.ZIP_CODE];
        if (year <= 2012 || Objects.equals(zipCode, "")) {
            return;
        }

        for (int streetIndex = Record.FIRST_STREET; streetIndex <= Record.THIRD_STREET; streetIndex++) {
            String street = row[streetIndex];
            if (Objects.equals(street, "")) {
                continue;
            }
            for (int numberIndex = Record.PEDESTRIANS_INJURED; numberIndex <= Record.MOTORIST_KILLED; numberIndex++) {
                String victimType = getVictimType(numberIndex);
                String injuriesType = getInjuriesType(numberIndex);
                String number = row[numberIndex];
                String result = String.format("%s|%s|%s|%s\t%s", street, zipCode, victimType, injuriesType, number);
                System.out.println(result);
            }
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                try {
                    map(tokens);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Line: " + line);
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
