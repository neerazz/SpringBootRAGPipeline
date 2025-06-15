@echo off
set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if not exist "%JAVA_EXE%" set JAVA_EXE=java
"%JAVA_EXE%" -jar "%MAVEN_WRAPPER_DIR%\maven-wrapper.jar" %*
