# Cerberus
Security system mobile app implemented using Raspberry Pi and Django

## About
Cerberus is a security system running on a Raspberry Pi with a Python-based Django backend and user accessibility from an Android mobile app. The system is capable of detecting motion and sending push notifications to the user upon detection, as well as displaying video and 2-way audio. The purpose of this project was to deepen my understanding of Django and Android app development by creating a security system similar to those already on the market, such as Nest and Ring. The scope of this project covers setting up a Raspberry Pi to run a Django and Mumble server and other peripherals, as well as the source code for the mobile app. A large portion of the app was made using the source code of the Plumble app found here https://github.com/acomminos/Plumble and here https://github.com/acomminos/Jumble and was heavily editted to suit the needs of this project. 

## Required Libraries and Dependencies
- OpenCV 4
- Pyfcm
- Django
- Virtualenv
- Localtunnel

## Getting Started
These instructions will allow you to get a copy of the project up and running on your own Raspberry Pi as well as running code for the mobile app for your own personal use.

### Installing prerequesites
The software listed above must be installed onto your Raspberry Pi for the project to function. OpenCV4 may require additional software depending on how it is installed on your system. The simplest method would be to run  
`pip3 install opencv-python`

However this command requires additional dependencies   
`sudo apt-get install libcblas-dev`  
`sudo apt-get install libhdf5-dev`  
`sudo apt-get install libhdf5-serial-dev`  
`sudo apt-get install libatlas-base-dev`  
`sudo apt-get install libjasper-dev`   
`sudo apt-get install libqtgui4`  
`sudo apt-get install libqt4-test`  

### **Update!** Localtunnel deprecation
As of January 2020, Localtunnel servers have seemed to become unresponsive. Localtunnel was used with this project to provide remote access to the Django server. Thus if you desire to access the server from outside the network, a new tunneling service must be used, such as Ngrok or Pagekite. However these services are fremium and not open-source, requiring payment for extra features such as a static domain, which is necessary for this project. As such these services are outside the scope of this project, but can be implemented if desired by replacing the lt_bootup file with whatever script is needed.

### Installing Django app and bootup files

