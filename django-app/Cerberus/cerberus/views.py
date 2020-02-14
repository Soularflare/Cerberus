from django.shortcuts import render
from django.http import HttpResponse
from django.http import StreamingHttpResponse
from .models import fcm_info
from .models import stream_flag
from django.views.decorators import gzip
import io
import random
import picamera
import cv2
import threading
import time



from pyfcm import FCMNotification
# Create your views here.

#this was the original home page, it declares the list of all the fcmid's in database
def homePageView(request): 
    prize = "<h1>Hello There</h1>"
    b = stream_flag(id = 1, flag = 0)
    b.save()

    tokens = fcm_info.objects.filter(id=1).first()
    prize += tokens.fcm_token
    prize += '<br>'
    
    return HttpResponse(prize)

#inserting the token into database, after receiving it from Volley
def fcm_insert(request): 
    token = request.GET.get("fcm_token", '')
    print(token)
    a = fcm_info(id=1, fcm_token=token)
    a.save()
    return HttpResponse(token)

#the method which sends the notification
def send_notifications(request): 
    path_to_fcm = "https://fcm.googleapis.com"
    server_key = 'insert your server keys here'
    reg_id = fcm_info.objects.get(id=1).fcm_token #quick and dirty way to get that ONE fcmId from table
    message_title = "ALERT"
    message_body = "Motion detected at entrance!"
    data_message = {
        "Nick" : "ALERT",
        "body" : "Motion detected at entrance!",
        
    }
    push_service = FCMNotification(api_key=server_key)
    result = push_service.single_device_data_message(registration_id=reg_id, data_message=data_message)
    #result = FCMNotification(api_key=server_key).notify_single_device(registration_id=reg_id, message_title=message_title, message_body=message_body)
    return HttpResponse(result)

def delete_item(request):
    response = "<h1>Items Deleted</h1>"
    
    tokens = fcm_info.objects.all()
    tokens.delete()
    tokens = stream_flag.objects.all()
    tokens.delete()
    return HttpResponse(response)
#in case you wanna send notifications to multitple ids in just replace the argument registration_id in the notify_single_device
#function with registration_ids and provide it with the list of ids you wanna send notifications to.

class VideoCamera(object):
    def __init__(self):
        self.video = cv2.VideoCapture(0)
        self.video.set(cv2.CAP_PROP_FPS, 30)


    def __del__(self):
        self.video.release()

    def get_frame(self):
        ret,image = self.video.read()
        ret, jpeg = cv2.imencode('.jpg', image)
        return jpeg.tobytes()









def gen(camera):
    start = time.time()
    end = time.time()

    while (end-start)<150:
        end = time.time()
        frame = camera.get_frame()
        yield(b'--frame\r\n'
              b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
    flag = stream_flag.objects.get(id = 1)
    flag.flag= 0
    flag.save()
        


@gzip.gzip_page
def stream(request):
    flag = stream_flag.objects.get(id = 1)
    if flag.flag == 0:
        flag.flag = 1
        flag.save()
        try:
            return StreamingHttpResponse(gen(VideoCamera()), content_type="multipart/x-mixed-replace;boundary=frame")
        except HttpResponseServerError as e:
            print("aborted")
    else:
        prize = "<h1>Stream is currently busy. Please try again in 2.5 minutes</h1>"
        return HttpResponse(prize)
