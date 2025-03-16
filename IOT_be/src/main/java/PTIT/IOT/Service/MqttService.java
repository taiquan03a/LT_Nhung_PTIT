package PTIT.IOT.Service;

import PTIT.IOT.Model.Device;
import PTIT.IOT.Repository.DeviceRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class MqttService {
    private MqttClient client;
    final private DeviceRepository deviceRepository;
    private double latestWindSpeed = 0.0;

    public MqttService(DeviceRepository deviceRepository) throws MqttException {
        this.deviceRepository = deviceRepository;

        String broker = "tcp://172.20.10.3:2003";
        String clientId = "springBootClient";
        String username = "taiquan";
        String password = "b21dccn614";


        // Tạo MQTT Connect Options
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());

        // Khởi tạo MqttClient và kết nối
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        client.connect(options);

        // Đăng ký để nhận thông điệp
        client.subscribe("wind/speed", (topic, message) -> {
            String payload = new String(message.getPayload());
            System.out.println("Received message: " + payload);
            try {
                // Chuyển chuỗi JSON sang JSONObject
                JSONObject jsonObject = new JSONObject(payload);
                // Lấy giá trị từ JSONObject
                double windSpeed = jsonObject.getDouble("wind_speed");
                System.out.println(windSpeed);
                // Đẩy dữ liệu lên Firebase
                FirebaseService.pushDataToFirebase(windSpeed);
                latestWindSpeed = windSpeed;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    @Scheduled(fixedRate = 300000) // 300.000ms = 5 phút
    public void saveLatestWindSpeedToDatabase() {
        Device windSpeedEntity = new Device();
        windSpeedEntity.setSpeed(String.valueOf(latestWindSpeed));
        windSpeedEntity.setTime(LocalDateTime.now());
        deviceRepository.save(windSpeedEntity);
        System.out.println("Saved latest wind speed: " + latestWindSpeed + " m/s to MySQL.");
    }
}

