#!/usr/bin/env bash

showtasks() {
  echo "------>   Starting BRAVE BROWSER   <------"
  open -a "Brave browser" http://localhost:8080/crud/v1/tasks
}

fail() {
  echo "There were errors"
  exit 0
}

if ./runcrud.sh;
  then
    showtasks
  else
    fail
fi