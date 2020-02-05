
import time
import requests

import RPi.GPIO as GPIO

URL = "Localtunnel_link"        //change to your specific Localtunnel url




pin = 11







GPIO.setmode(GPIO.BOARD)
    
GPIO.setup(pin, GPIO.IN)





while(1):
    if GPIO.input(pin):
        print("1")
        r = requests.get(url=URL)
        
    time.sleep(1)
        

        

 
      
   
