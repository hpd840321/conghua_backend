@echo off
set JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
set CATALINA_OPTS=-Xms512m -Xmx1024m -XX:MaxPermSize=256m
call "D:\apache-tomcat-11.0.6\bin\catalina.bat" jpda start 