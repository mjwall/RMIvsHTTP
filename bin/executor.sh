#!/bin/bash

# shell script to execute all the tests and collect results.  You must modify
# the TestHarness numbers and run mvn clean install jboss:hard-deploy before
# each run of this script

CODE_DIR="/Users/mwall/src/Personal/RMIvsHTTP/"
BASE_URL="http://localhost:8080/rmi-same-jvm/"
DATA_DIR="/Users/mwall/Desktop/rmi-vs-http-data/"

local_same() {
  num=$1
  curl "${BASE_URL}local?runsPerTest=${num}" > "${DATA_DIR}${num}-local-same.txt"
}

remote_same() {
  num=$1
  curl "${BASE_URL}remote?runsPerTest=${num}" > "${DATA_DIR}${num}-remote-same.txt"
}

remote_external() {
  num=$1
  cd "${CODE_DIR}pref-tests/rmi-external-jvm"
  ./bin/run.sh "${num}" | tee "${DATA_DIR}${num}-remote-external.txt"
}

http() {
  num=$1
  cd "${CODE_DIR}pref-tests/http"
  ./bin/run.sh "${num}" | tee "${DATA_DIR}${num}-http.txt"
}

https() {
  num=$1
  cd "${CODE_DIR}pref-tests/https"
  ./bin/run.sh "${num}" | tee "${DATA_DIR}${num}-https.txt"
}

run_all() {
  num=$1
  echo "Executing tests with ${num} runs and dumping results to ${DATA_DIR}"
  local_same "${num}"
  remote_same "${num}"
  remote_external "${num}"
  http "${num}"
  https "${num}"
}

run_all 10
run_all 100
run_all 1000
run_all 10000
#run_all 100000 # my laptop falls over here
cd "${CODE_DIR}"
