@echo off
REM This file generated by AspectJ installer
REM Created on Mon Sep 07 17:28:12 PDT 2020 by A00006472

if "%JAVA_HOME%" == "" set JAVA_HOME=C:\Program Files\Java\jdk-11.0.8
if "%ASPECTJ_HOME%" == "" set ASPECTJ_HOME=C:\temp\aop\aspectj

if exist "%JAVA_HOME%\bin\java.exe" goto haveJava
if exist "%JAVA_HOME%\bin\java.bat" goto haveJava
if exist "%JAVA_HOME%\bin\java" goto haveJava
echo java does not exist as %JAVA_HOME%\bin\java
echo please fix the JAVA_HOME environment variable
:haveJava
"%JAVA_HOME%\bin\java" -classpath "%ASPECTJ_HOME%\lib\aspectjweaver.jar;%CLASSPATH%" "-javaagent:%ASPECTJ_HOME%\lib\aspectjweaver.jar" %1 %2 %3 %4 %5 %6 %7 %8 %9
