#include <Arduino.h>
#include <WiFi.h>
#include <PubSubClient.h>

// WiFi & MQTT Config
const char *ssid = "iPhone Q";
const char *password = "123456789";
const char *mqttServer = "172.20.10.3"; // Địa chỉ MQTT Broker (VD: Mosquitto, EMQX)
const int mqttPort = 2003;
const char *mqttTopic = "wind/speed";
const char *mqtt_username = "taiquan";    // MQTT username
const char *mqtt_password = "b21dccn614"; // MQTT password172.20.10.3

WiFiClient espClient;
PubSubClient client(espClient);

// Cấu hình cảm biến Hall
#define HALL_SENSOR_PIN 4
#define LED_PIN 5

volatile int pulseCount = 0;
unsigned long lastTime = 0;
float windSpeed = 0.0;

void IRAM_ATTR countPulse()
{
    static unsigned long lastInterruptTime = 0;
    unsigned long interruptTime = millis();

    if (interruptTime - lastInterruptTime > 1)
    {
        pulseCount++;
    }

    lastInterruptTime = interruptTime;
}

void setupWiFi()
{
    Serial.print("Đang kết nối WiFi...");
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED)
    {
        delay(1000);
        Serial.print(".");
    }
    Serial.println(WiFi.localIP());
    Serial.println("\nWiFi đã kết nối!");
}

void reconnectMQTT()
{
    while (!client.connected())
    {
        Serial.print("Kết nối MQTT...");
        if (client.connect("ESP32Client", mqtt_username, mqtt_password))
        {
            Serial.println("Thành công!");
        }
        else
        {
            Serial.print("Lỗi: ");
            Serial.print(client.state());
            Serial.println(" Thử lại sau 5 giây...");
            delay(5000);
        }
    }
}

void setup()
{
    Serial.begin(115200);
    setupWiFi();
    client.setServer(mqttServer, mqttPort);

    pinMode(HALL_SENSOR_PIN, INPUT_PULLUP);
    attachInterrupt(digitalPinToInterrupt(HALL_SENSOR_PIN), countPulse, FALLING);

    pinMode(LED_PIN, OUTPUT);
    digitalWrite(LED_PIN, LOW);
}

void loop()
{
    if (!client.connected())
    {
        reconnectMQTT();
    }
    client.loop();

    unsigned long currentTime = millis();

    if (currentTime - lastTime >= 1000)
    {
        int pulsePerSecond = pulseCount;
        pulseCount = 0;

        float frequency = pulsePerSecond / 2.0;
        windSpeed = 3.1416 * 3 * frequency;

        if (windSpeed > 0)
        {
            digitalWrite(LED_PIN, HIGH);
        }
        else
        {
            digitalWrite(LED_PIN, LOW);
        }

        Serial.print("Số xung đo được: ");
        Serial.print(pulsePerSecond);
        Serial.print("  |  Tốc độ gió: ");
        Serial.print(windSpeed);
        Serial.println(" m/s");

        // Gửi dữ liệu qua MQTT
        String payload = "{\"wind_speed\": " + String(windSpeed) + "}";
        client.publish(mqttTopic, payload.c_str());

        lastTime = currentTime;
    }
}
