#!/bin/sh
set -e
MAVEN_PROJECTBASEDIR="$(dirname "$0")"
MAVEN_WRAPPER_DIR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
JAVA_HOME="${JAVA_HOME:-}"
if [ -z "$JAVA_HOME" ]; then
  javaExe="$(command -v java)"
else
  javaExe="$JAVA_HOME/bin/java"
fi
exec "$javaExe" -jar "$MAVEN_WRAPPER_DIR/maven-wrapper.jar" "$@"
