#!/bin/sh
set -e
MAVEN_PROJECTBASEDIR="$(dirname "$0")"
MAVEN_WRAPPER_DIR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
WRAPPER_JAR="$MAVEN_WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper..."
  mkdir -p "$MAVEN_WRAPPER_DIR"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "$WRAPPER_JAR" "$WRAPPER_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$WRAPPER_JAR" "$WRAPPER_URL"
  else
    echo "Error: curl or wget is required to download Maven Wrapper" >&2
    exit 1
  fi
fi
JAVA_EXEC="${JAVA_HOME:+$JAVA_HOME/bin/java}"
if [ -z "$JAVA_EXEC" ]; then
  JAVA_EXEC="$(command -v java)"
fi
exec "$JAVA_EXEC" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
