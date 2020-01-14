from django.db import models

# Create your models here.
class fcm_info(models.Model):
    fcm_token = models.CharField(max_length=400)

    
    def __str__(self):
        return self.fcm_token

class stream_flag(models.Model):
    flag = models.IntegerField()
    