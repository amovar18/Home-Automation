#include <FirebaseFS.h>
#include <FirebaseESP8266HTTPClient.h>
#include <FirebaseJson.h>
#include <FirebaseESP8266.h>
#include <ESP8266WiFi.h>

#define relay_no true
#define num_relays 4
#define database_secret "secret key" 
#define database_url "dburl"
#define user_id "unique userid"
#define wifi_id "wifiname"
#define wifi_password "wifipass"

String path="/devices";
int relayGPIOs[num_relays] = {5, 4, 14, 12};
// 5 fan 4 light 14 light 12 light
FirebaseData firebaseData;
void setup() {
  Serial.begin(9600);
  //setup pins for relay
  for(int i=0; i<num_relays; i++){
    pinMode(relayGPIOs[i], OUTPUT);
    if(relay_no){
      digitalWrite(relayGPIOs[i], HIGH);
    }
  }
  // Connect to wifi
    WiFi.begin(wifi_id, wifi_password);
    Serial.print("Connecting to Wi-Fi");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(300);
    }
    Serial.println();
    Serial.print("Connected with IP: ");
    Serial.println(WiFi.localIP());
    Serial.println();
    
    //Connect to Firebase
  Firebase.begin(database_url,database_secret);
  delay(100);
  Firebase.reconnectWiFi(true);
}

void loop() {
    if (Firebase.getBool(firebaseData, "/Users/Eyzp6MOK8SbUPEazCHoYCFIhkvn1/devices/hall fan 1")) {
      if (firebaseData.dataType() == "boolean") {
          if(firebaseData.boolData()==0){
            digitalWrite(relayGPIOs[0],HIGH);
        }else{
          digitalWrite(relayGPIOs[0],LOW);
          }
      }
    } else {
      Serial.println(firebaseData.errorReason());
    }
    if (Firebase.getBool(firebaseData, "/Users/Eyzp6MOK8SbUPEazCHoYCFIhkvn1/devices/hall light 1")) {
      if (firebaseData.dataType() == "boolean") {
        Serial.println(firebaseData.boolData());
        if(firebaseData.boolData()==0){
            digitalWrite(relayGPIOs[1],HIGH);
        }else{
          digitalWrite(relayGPIOs[1],LOW);
          }
      }
    } else {
      Serial.println(firebaseData.errorReason());
    }
    if (Firebase.getBool(firebaseData, "/Users/Eyzp6MOK8SbUPEazCHoYCFIhkvn1/devices/hall light 2")) {
      if (firebaseData.dataType() == "boolean") {
        Serial.println(firebaseData.boolData());
        if(firebaseData.boolData()==0){
            digitalWrite(relayGPIOs[2],HIGH);
        }else{
          digitalWrite(relayGPIOs[2],LOW);
          }
      }
    } else {
      Serial.println(firebaseData.errorReason());
    }
    if (Firebase.getBool(firebaseData, "/Users/Eyzp6MOK8SbUPEazCHoYCFIhkvn1/devices/hall light 3")) {
      if (firebaseData.dataType() == "boolean") {
        Serial.println(firebaseData.boolData());
        if(firebaseData.boolData()==0){
            digitalWrite(relayGPIOs[3],HIGH);
        }else{
          digitalWrite(relayGPIOs[3],LOW);
          }
      }
    } else {
      Serial.println(firebaseData.errorReason());
    }
}
