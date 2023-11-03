package school.redrover;

import java.util.UUID;

public class AdditionalUtils {

    public static String generateRandomName() {
        String randomName = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        return randomName;
    }

}
