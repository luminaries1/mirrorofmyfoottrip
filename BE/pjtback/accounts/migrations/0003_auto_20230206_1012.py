# Generated by Django 3.2.13 on 2023-02-06 10:12

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0002_alter_user_naver'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='google',
            field=models.EmailField(blank=True, max_length=254, null=True, verbose_name='google_email'),
        ),
        migrations.AlterField(
            model_name='user',
            name='kakao',
            field=models.EmailField(blank=True, max_length=254, null=True, verbose_name='kakao_email'),
        ),
    ]
