package PTIT.IOT.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FirebaseService {

    private static final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("windSpeed");

    public static void pushDataToFirebase(double data) {
        Map<String, Object> windData = new HashMap<>();
        windData.put("speed", data);
        windData.put("timestamp", System.currentTimeMillis());

        dbRef.push().setValueAsync(windData);
        System.out.println("Data pushed to Firebase: " + windData);
    }
}