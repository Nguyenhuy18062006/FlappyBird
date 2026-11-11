@echo off
REM =====================================================
REM  Build FlappyBird thanh file .exe (kem Java, khong can cai Java)
REM  Ket qua: dist\FlappyBird\FlappyBird.exe
REM  Can: JDK co jpackage (JDK 14+), Maven
REM =====================================================
setlocal
cd /d "%~dp0"

set APP_NAME=FlappyBird
set INPUT=target\jpackage-input

echo [1/4] Bien dich va dong goi .jar bang Maven...
call mvn -q clean package -DskipTests
if errorlevel 1 goto :error

echo [2/4] Gom file .jar cua game va cac thu vien (MySQL, BCrypt)...
if exist "%INPUT%" rmdir /s /q "%INPUT%"
mkdir "%INPUT%"
copy /y target\flappybird-1.0-SNAPSHOT.jar "%INPUT%\" >nul
call mvn -q dependency:copy-dependencies -DoutputDirectory="%INPUT%" -DincludeScope=runtime
if errorlevel 1 goto :error

echo [3/4] Tao FlappyBird.exe bang jpackage...
if exist "dist\%APP_NAME%" rmdir /s /q "dist\%APP_NAME%"
jpackage --type app-image ^
  --name %APP_NAME% ^
  --input "%INPUT%" ^
  --main-jar flappybird-1.0-SNAPSHOT.jar ^
  --main-class flappybird.Main ^
  --dest dist
if errorlevel 1 goto :error

echo [4/4] Copy thu muc resources (anh, am thanh) canh file .exe...
xcopy /e /i /y /q resources "dist\%APP_NAME%\resources" >nul
if errorlevel 1 goto :error

echo.
echo XONG! Chay game tai: dist\%APP_NAME%\%APP_NAME%.exe
goto :eof

:error
echo.
echo BUILD THAT BAI - xem loi o tren.
exit /b 1
