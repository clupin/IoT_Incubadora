#include <math.h>
#include <Servo.h>
#include <Wire.h>
#include "rgb_lcd.h"

rgb_lcd lcd;

const int colorR = 255;
const int colorG = 0;
const int colorB = 0;

int pos = 0;    // variable to store the servo position

Servo myservo;  // create servo object to control a servo
const int B = 4275;               // TEMPERATURE DEF - B value of the thermistor
const int R0 = 100000;            // TEMPERATURE DEF - R0 = 100k
const int pinTempSensor = A0;     // TEMPERATURE DEF - Grove - Temperature Sensor connect to A0
const int pinPotenciometro = A1;  // POTENCIOMETRO DEF - Grove - connect to A1
const int pinLed = 2;             // LED DEF - Grove mod - connect to D2
const int pinServo = 8;           // SERVO DEF - Grove - connect to D8

void setup(){
    Serial.begin(9600);
    pinsInit();
}

void loop(){
  //TEMPERATURA - INICIO
  int a = analogRead(pinTempSensor);
  float R = 1023.0/a-1.0;
  R = R0*R;
  float temperature = 1.0/(log(R/R0)/B+1/298.15)-273.15; // convert to temperature via datasheet
  Serial.print("temperature = ");
  Serial.println(temperature);
  //TEMPERATURA - FIN
    
  //SERVO - INICIO
  for (pos = 0; pos <= 180; pos += 1){ // goes from 0 degrees to 180 degrees
    // in steps of 1 degree
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }
  for (pos = 180; pos >= 0; pos -= 1){ // goes from 180 degrees to 0 degrees
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }
  //SERVO - FIN

  //LED - INICIO
  Serial.println("Led encendido");
  digitalWrite(pinLed, HIGH);
  delay(1000);
  Serial.println("Led apagado");
  digitalWrite(pinLed, LOW);
  delay(1000);
  //LED - FIN

  //POTENCIOMETRO - INICIO
  int degrees = getDegree(5, 300, 5);
  Serial.println("The angle between the mark and the starting position: ");
  Serial.println(degrees);
  //POTENCIOMETRO - FIN

  //LCD - INICIO
  lcd.setCursor(0, 0);
  lcd.write("Holaaa\n");
  delay(1000);
  lcd.write("Holaaa\n");
  //LCD - FIN
}
  
  void pinsInit(){
    pinMode(pinPotenciometro, INPUT);
    pinMode(pinLed,OUTPUT);
    myservo.attach(pinServo);
    lcd.begin(16, 2);
    lcd.setRGB(colorR, colorG, colorB);
  }

/************************************************************************/
/*Function: Get the angle between the mark and the starting position    */
/*Parameter:-void                                                       */
/*Return:   -int,the range of degrees is 0~300                          */
int getDegree(int adc_ref, int full_angle, int grove_vcc){
  int sensor_value = analogRead(pinPotenciometro);
  float voltage;
  voltage = (float)sensor_value*adc_ref/1023;
  float degrees = (voltage*full_angle)/grove_vcc;
  return degrees;
}
