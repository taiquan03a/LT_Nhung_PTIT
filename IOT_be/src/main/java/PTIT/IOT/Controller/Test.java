package PTIT.IOT.Controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Test {
    private static MqttClient mqttClient;
    public static void main(String[] args) {

        try {
            mqttClient.subscribe("sensor/data", (topic, msg) -> {
                String payload = new String(msg.getPayload());
                System.out.println("Received Data: " + payload);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
