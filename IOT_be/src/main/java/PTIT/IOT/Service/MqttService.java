package PTIT.IOT.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MqttService {
    private static final String BROKER_URL = "tcp:172.11.46.241:2003";
    private static final String CLIENT_ID = "springBootClient";
    private static final String USERNAME = "taiquan";
    private static final String PASSWORD = "b21dccn614";
    private static final String TOPIC = "wind/speed";

    private MqttClient client;
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

//    public MqttService() {
//        connectToBroker();
//    }
//
//    private void connectToBroker() {
//        try {
//            client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
//            MqttConnectOptions options = new MqttConnectOptions();
//            options.setUserName(USERNAME);
//            options.setPassword(PASSWORD.toCharArray());
//            options.setCleanSession(true);
//            options.setAutomaticReconnect(true);
//
//            client.connect(options);
//            System.out.println("Connected to MQTT Broker!");
//
//            subscribeToTopic();
//        } catch (MqttException e) {
//            System.err.println("MQTT Connection Failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

//    private void subscribeToTopic() {
//        try {
//            client.subscribe(TOPIC, (topic, message) -> {
//                String payload = new String(message.getPayload());
//                System.out.println("Received message: " + payload);
//                try {
//                    JSONObject jsonObject = new JSONObject(payload);
//                    double windSpeed = jsonObject.getDouble("wind_speed");
//                    System.out.println("Wind Speed Received: " + windSpeed);
//                    FirebaseService.pushDataToFirebase(windSpeed);
//                } catch (Exception e) {
//                    System.err.println("Error processing received message: " + e.getMessage());
//                    e.printStackTrace();
//                }
//            });
//        } catch (MqttException e) {
//            System.err.println("Error subscribing to topic: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
@Scheduled(fixedRate = 1000) // 5 giây tạo một giá trị mới
public void generateWindSpeed() {
    double windSpeed = 5 + (random.nextDouble() * 45); // 5 - 50 m/s
    windSpeed = Math.round(windSpeed * 100.0) / 100.0; // Làm tròn đến 2 chữ số thập phân

    System.out.println("Wind Speed Generated: " + windSpeed);
    FirebaseService.pushDataToFirebase(windSpeed);
}

//    @Scheduled(fixedRate = 2000)
//    public void publishRandomWindSpeed() {
//        try {
//            if (client.isConnected()) {
//                double windSpeed = Math.round((random.nextDouble() * 20) * 100.0) / 100.0;
//                Map<String, Object> payload = new HashMap<>();
//                payload.put("wind_speed", windSpeed);
//                String messageString = objectMapper.writeValueAsString(payload);
//
//                MqttMessage message = new MqttMessage(messageString.getBytes());
//                message.setQos(1);
//                client.publish(TOPIC, message);
//
//                System.out.println("Sent: " + messageString);
//            } else {
//                System.err.println("MQTT Client is not connected! Reconnecting...");
//                connectToBroker();
//            }
//        } catch (Exception e) {
//            System.err.println("Error publishing message: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
