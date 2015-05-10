#include <SoftwareSerial.h>

SoftwareSerial BTSerial(8, 6); // RX | TX

void setup()
{
  Serial.begin(9600);
  BTSerial.begin(38400);
}

void loop()
{
  // Keep reading from HC-05 and send to Arduino Serial Monitor
  if (BTSerial.available())
    Serial.write(BTSerial.read());
}
