@echo off
setlocal
set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper
set WRAPPER_JAR=%MAVEN_WRAPPER_DIR%\maven-wrapper.jar
set WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
if not exist "%WRAPPER_JAR%" (
  echo Downloading Maven Wrapper...
  mkdir "%MAVEN_WRAPPER_DIR%" 2>NUL
  powershell -Command "& {Invoke-WebRequest -OutFile '%WRAPPER_JAR%' '%WRAPPER_URL%'}" || (
    echo Error: could not download Maven Wrapper
    exit /b 1
  )
)
if "%JAVA_HOME%"=="" (
  set JAVA_EXE=java
) else (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
)
"%JAVA_EXE%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal
