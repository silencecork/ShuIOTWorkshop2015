#include <SPI.h>
#include <WiFi.h>

char ssid[] = ""; // TODO fill it with your Wifi information
char pass[] = ""; // TODO fill it with your Wifi information
int status = WL_IDLE_STATUS;
WiFiClient client;
char server[] = ""; // TODO Fill it with your server
int tempaturePin = 0;

void setup() {
  Serial.begin(9600); 
  while (!Serial) {
    ; 
  }
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present"); 
    while(true);
  } 
  while (status != WL_CONNECTED) { 
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid); 
    status = WiFi.begin(ssid, pass);
    delay(10000);
  } 
  printWifiStatus();
}

void loop() {
  if (client.connect(server, 80)) {
    int tempature = analogRead(tempaturePin);
    tempature = tempature * 0.48828125;
    Serial.print("TEMPRATURE = ");
    Serial.print(tempature);
    Serial.println("*C");
    Serial.println("connecting...");
    String request = "GET /api/createDataPoint?value=";
    request += tempature;
    request += " HTTP/1.1";
    client.println(request);
    client.println("Host: example-tempature-api-server.herokuapp.com");
    client.println("User-Agent: Arduino Sensor");
    client.println("Connection: close");
    client.println();
    delay(1000);
  } else {
    Serial.println("connection failed");
  }
  Serial.println("disconnecting.");
  client.stop();
  delay(10000);
}

void printWifiStatus() {
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
