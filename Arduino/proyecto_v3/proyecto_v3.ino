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
const int pinLed = 3;             // LED DEF - Grove mod - connect to D3
const int pinVentilador = 4;      // LED DEF - Grove mod - connect to D4
const int pinServo = 8;           // SERVO DEF - Grove - connect to D8
int values[] = {0,0,0,0,0};         //{lamp, fan, servo, temp, humidity}

void setup(){
    Serial.begin(9600);
    pinsInit();
}

void loop(){
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
            break; 
          }
        }
        break;
      case '1': //heat_lamp
        switch(insPin2){
          case '0': {//read heat_lamp
            int lamp = readLamp();
            Serial.println(lamp);
            break;
          }
          case '1': {//write heat_lamp
            writeLamp(instruction.substring(2,5));
            break;
          }
        }
        break;
      case '2': //fan_state
        switch(insPin2){
          case '0': {//read fan_state
            int fan = readFan();
            Serial.println(fan);
            break;
          }
          case '1': {//write fan_state
            writeFan(instruction.charAt(2));
            break;
          }
        }
        break;
      case '3': //servo_angle
        switch(insPin2){
          case '0': {//read servo_angle
            int fan = readServo();
            Serial.println(fan);
            break;
          }
          case '1': {//write servo_angle
            writeServo(instruction.substring(2,5));
            break;
          }
        }
        break;
      case '4': //temperature
        switch(insPin2){
          case '0': {//read temperature
            float temp = readTemperature();
            Serial.println(temp);
            break;
          }
          case '1': {//write temperature
            writeTemperature(instruction.substring(2,5));
            break;
          }
        }
        break;
      
        
    }
    
      
  }
}


int readLamp(){
  return values[0];
}

void writeLamp(String value){
  int valor = value.toInt();
  values[0] = valor;
  analogWrite(pinLed, valor);
}

int readFan(){
  values[1] = digitalRead(pinVentilador);
  return values[1];
}

void writeFan(char value){
  int valor = 0;
  if(value == '0'){
    digitalWrite(pinVentilador, LOW);
  } else {
    digitalWrite(pinVentilador, HIGH);
    valor = 1;
  }
  values[1] = valor;
}

int readServo(){
  values[2] = myservo.read();
  return values[2];
}

void writeServo(String value){
  int valor = value.toInt();
  values[2] = valor;
  myservo.write(valor);
}

float readTemperature(){
  int a = analogRead(pinTempSensor);
  float R = 1023.0/a-1.0;
  R = R0*R;
  float temperature = 1.0/(log(R/R0)/B+1/298.15)-273.15; // convert to temperature via datasheet
  return temperature;
}

void writeTemperature(String value){
  //enviar a spring temperatura maxima
}

void pinsInit(){
  pinMode(pinLed,OUTPUT);
  pinMode(pinVentilador,OUTPUT);
  myservo.attach(pinServo);
  lcd.begin(16, 2);
  lcd.setRGB(colorR, colorG, colorB);
}
