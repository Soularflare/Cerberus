#!/bin/bash
sudo modprobe bcm2835-v4l2
cd django-app
sudo -s source venv/bin/activate
cd Cerberus
sudo python3 manage.py runserver 0.0.0.0:8000



