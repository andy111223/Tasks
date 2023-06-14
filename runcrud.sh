#!/usr/bin/env bash

export CATALINA_HOME=/Applications/apache-tomcat-10.1.10

stop_tomcat() {
  $CATALINA_HOME/bin/catalina.sh stop
  end
}

start_tomcat() {
  $CATALINA_HOME/bin/catalina.sh start
}

rename() {
  if [ -f build/libs/crud.war ]; then
    rm build/libs/crud.war
  fi
  if [ -f build/libs/tasks-0.0.1-SNAPSHOT.war ] && mv build/libs/tasks-0.0.1-SNAPSHOT.war build/libs/crud.war;
  then
    echo "Successfully renamed file"
    else
      echo "Cannot rename file"
      fail
  fi
}
copy_file() {
  if [ -f build/libs/crud.war ] && cp build/libs/crud.war $CATALINA_HOME/webapps/crud.war;
  then
    echo "Filed copied to webapps"
    start_tomcat
    echo "Runcrud: Tomcat started"
    else
      fail
  fi
}


fail() {
  echo "There were errors"
  exit 0
}
end() {
  echo "Work is finished"
}

if ./gradlew build;
then
  rename
  copy_file
  else
    stop_tomcat
    fail
fi
