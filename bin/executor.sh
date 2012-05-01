#!/bin/bash

# shell script to execute all the tests and collect results.  You must modify
# the TestHarness numbers and run mvn clean install jboss:hard-deploy before
# each run of this script

CODE_DIR="/Users/mwall/src/Personal/RMIvsHTTP/"
BASE_URL="http://localhost:8080/rmi-same-jvm/"
DATA_DIR="/Users/mwall/Desktop/rmi-vs-http-data/"

local_same() {
  curl "${BASE_URL}local" > "${DATA_DIR}$(number_of_runs)-local-same.txt"
}

remote_same() {
  curl "${BASE_URL}remote" > "${DATA_DIR}$(number_of_runs)-remote-same.txt"
}

remote_external() {
  cd "${CODE_DIR}pref-tests/rmi-external-jvm"
  ./bin/run.sh | tee "${DATA_DIR}$(number_of_runs)-remote-external.txt"
}

http() {
  cd "${CODE_DIR}pref-tests/http"
  ./bin/run.sh | tee "${DATA_DIR}$(number_of_runs)-http.txt"
}

https() {
  cd "${CODE_DIR}pref-tests/https"
  ./bin/run.sh | tee "${DATA_DIR}$(number_of_runs)-https.txt"
}

number_of_runs() {
  if [ -z $NUMBER ]; then
     NUMBER=$(grep "final int numberOfRunsPerTest" ${CODE_DIR}pref-tests/test-harness/src/main/java/mjwall/testHarness/TestHarness.java | perl -lne 'print $1 if /(\d+)/')
  fi
  echo $NUMBER
}


echo "Executing tests with $(number_of_runs) runs and dumping results to ${DATA_DIR}"
local_same
remote_same
remote_external
http
https
cd "${CODE_DIR}"
