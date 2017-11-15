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
const int pinLed = 9;             // LED DEF - Grove mod - connect to D2
const int pinServo = 8;           // SERVO DEF - Grove - connect to D8
int values[] = {0,0,0,0};

void setup(){
    Serial.begin(9600);
    pinsInit();
}

void loop(){
  Serial.println(values[0]);
  if (Serial.available() > 0) {
    String instruction = Serial.readString();
    char insPin = instruction.charAt(0);
    char insPin2 = instruction.charAt(1);
    switch(insPin){
      case '0':  //all
        switch(insPin2){
          case '0':{ //read all
            float temp = readTemperature();
            Serial.println(temp);
            break;
          }
          case '1':{ //write all
            //cambiar temperatura xD
            break; 
          }          
        }
        break;
      case '1': //heat_lamp
        switch(insPin2){
          case '0': {//read heat_lamp
            int lamp = readLamp();
            //int lamp = digitalRead(pinLed);
            Serial.print("lamp: ");
            Serial.println(lamp);
            break;
          }
          case '1': {//write heat_lamp
            //writeLamp(instruction.substring(2,5));
            writeLamp(instruction.charAt(2));
            /*if(instruction.substring(2,4) == "00"){
              writeLamp(instruction.substring(2,4) == "00");
            } else {
              writeLamp(HIGH);
            }*/
            break;
          }
        }
      
        
    }
    
      
  }
}

int readLamp(){
  /*int val = digitalRead(pinLed);*/
  /*int value = map(val, 17, 1010, 0, 255);
  Serial.print("val: ");
  Serial.print(val);
  Serial.print(" value: ");
  Serial.println(value);*/
  return values[0];
}

void writeLamp(char value){
  //int valor = value.toInt();
  //Serial.println(valor);
  //analogWrite(pinLed, valor);
  if(value == '0'){
    digitalWrite(pinLed, LOW);
    values[0] = 0;
  } else {
    digitalWrite(pinLed, HIGH);
    values[0] = 1;
  }
}

float readTemperature(){
  int a = analogRead(pinTempSensor);
  float R = 1023.0/a-1.0;
  R = R0*R;
  float temperature = 1.0/(log(R/R0)/B+1/298.15)-273.15; // convert to temperature via datasheet
  return temperature;
}

int getDegree(int pin, int adc_ref, int full_angle, int grove_vcc){
  int sensor_value = analogRead(pin);
  float voltage;
  voltage = (float)sensor_value*adc_ref/1023;
  float degrees = (voltage*full_angle)/grove_vcc;
  return degrees;
}

void pinsInit(){
  pinMode(pinPotenciometro, INPUT);
  pinMode(pinLed,OUTPUT);
  myservo.attach(pinServo);
  lcd.begin(16, 2);
  lcd.setRGB(colorR, colorG, colorB);
}
