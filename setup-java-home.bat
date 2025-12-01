@echo off
REM Script para ejecutar PowerShell como Administrador y configurar JAVA_HOME
REM Guarda este archivo como .bat y ejecuta (doble click o desde PowerShell)

echo Solicitando permisos de Administrador...
powershell -Command "Start-Process powershell -ArgumentList '-NoProfile -ExecutionPolicy Bypass -Command &{&''C:\Users\julia\Documents\GitHub\Parche-Lector\configure-java-home.ps1''}' -Verb RunAs"
