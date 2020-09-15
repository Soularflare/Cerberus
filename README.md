# Cerberus
Security system mobile app implemented using Raspberry Pi and Django

<img src="/Demo/cerberus_demo.gif"/>

## About
Cerberus is a security system running on a Raspberry Pi with a Python-based Django backend and user accessibility from an Android mobile app. The system is capable of detecting motion and sending push notifications to the user upon detection, as well as displaying video and 2-way audio. The purpose of this project was to deepen my understanding of Django and Android app development by creating a security system similar to those already on the market, such as Nest and Ring. The scope of this project covers setting up a Raspberry Pi to run a Django and Mumble server and other peripherals, as well as the source code for the mobile app. A large portion of the app was made using the source code of the Plumble app found here https://github.com/acomminos/Plumble and here https://github.com/acomminos/Jumble and was heavily editted to suit the needs of this project. 

## Required Libraries and Dependencies
- OpenCV 4
- Pyfcm
- Django
- Virtualenv
- Localtunnel
- Mumble client/server

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
As of January 2020, Localtunnel servers have seemed to become unresponsive. Localtunnel was used with this project to provide remote access to the Django server. Thus if you desire to access the server from outside the local network, a new tunneling service must be used, such as Ngrok or Pagekite. However these services are fremium and not open-source, requiring payment for extra features such as a static domain, which is necessary for this project. As such these services are outside the scope of this project, but can be implemented if desired by replacing the lt_bootup file with whatever script is needed.

### Installing Django app and bootup files
After the dependencies are installed, the Django app and bootup files can be downloaded into the main directory of your Raspberry Pi. Make sure all of the .sh files are made executable by using  
`sudo chmod +x <filename>`  
Next edit the autostart file using  
`sudo nano /etc/xdg/lxsession/LXDE-pi/autostart`  
and add `@/home/pi/mumble.sh` and `@lxterminal` to the end of it so that the mumble client and a terminal can be opened at bootup. The rest of the files can be run by editting the .bashrc file in the main directory using  
`sudo nano ~/.bashrc`  
and adding the command to execute the file at the bottom  
`python /path/to/python_file &`  
`sh /path/to/sh_file &`  
make sure to add & at the end to ensure that the program will run continuously in the background

### Firebase integration
This project utilizes the Firebase platform to send push notifications to the user's device. In order to incorporate this feature, you must create a project on Firebase's site. Doing so will generate a server key to use in this project's views.py file in the Django folder. With this, your Django server should be able to send out push notifications using your own Firebase app.

### Installing Mumble
For this project, the Raspberry Pi runs a Mumble client and server to provide an audio feed to the app. To do this, simply run `sudo apt install mumble` and `sudo apt install mumble-server` in the command line. Next type `sudo dpkg-reconfigure mumble-server` to setup the server. In the following screens, select yes, followed by creating a password for the superuser. Next edit the server config file by typing `sudo nano /etc/mumble-server.ini` and change the following lines:  
`registerName=HomeIntercom`  
`serverpassword=hackme`  
`sslCert=/home/pi/rootCA.crt`  
`sslKey=/home/pi/rootCA.key`  
`sslpassphrase=hackme`  
`certrequired=False`  
In order to allow other devices to connect to your Mumble server, you must port forward port# 64738 on the router that your Raspberry Pi is connected to as well. 

### Acquiring and assembling the hardware  
![IMG_20200203_010155670](https://user-images.githubusercontent.com/30604147/73686224-71f51e80-467c-11ea-97a8-bf1a53012274.jpg)

For this project you will need the following:
- Raspberry Pi
- Picamera
- PIR motion sensor
- portable speaker
- jumper wires
- button for manual shutdown of the Raspberry Pi(optional)

The pinouts selected for the components are the following:  
- **PIR Motion Sensor**
  - Pin 2(+5v)
  - Pin 11(GPIO 17)
  - Pin 6(GND)

- **Shutdown Button**
  - Pin 37(GPIO 26)
  - Pin 39(GND)

The pinout selection can be reconfigured by altering the pin selections in bootup.py and Shutdown.py. The addition of a shutdown button is a completely optional component that provides a method to safely shut down the Raspberry Pi externally and can be ignored completely if desired.
