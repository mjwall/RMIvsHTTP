#!/bin/bash

# You must pass in a number of times to run per test
if [ $# -ne 1 ]; then
  echo "You must pass in a number of times to run per test"
else
  java -jar target/rmi-vs-http-http-test-jar-with-dependencies.jar $1
fi

