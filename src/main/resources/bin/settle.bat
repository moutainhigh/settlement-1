@echo off & setlocal enabledelayedexpansion

cd %cd%
set BIN_DIR=%cd%
cd ..
set DEPLOY_DIR=%cd%
set CONF_DIR=%DEPLOY_DIR%\conf\
set LIB_DIR=%DEPLOY_DIR%\lib\
set LIB_JARS=
cd %LIB_DIR%
for %%i in (*) do set LIB_JARS=!LIB_JARS!%LIB_DIR%%%i;
cd %DEPLOY_DIR%
set CLASSPATH=%DEPLOY_DIR%;%CONF_DIR%;%LIB_JARS%
@echo on
java -classpath %CLASSPATH% com.yuanheng100.settlement.startup.SettlementBootstrap
pause
