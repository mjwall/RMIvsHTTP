#!/bin/bash

# shell script to execute all the tests and collect results.

_script_dir() {
    if [ -z "${SCRIPT_DIR}" ]; then
    # even resolves symlinks, see
    # http://stackoverflow.com/questions/59895/can-a-bash-script-tell-what-directory-its-stored-in
        local SOURCE="${BASH_SOURCE[0]}"
        while [ -h "$SOURCE" ] ; do SOURCE="$(readlink "$SOURCE")"; done
        SCRIPT_DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
    fi
    echo "${SCRIPT_DIR}"
}

DATA_DIR="/Users/mwall/Desktop/rmi-vs-http-data/"

local_same() {
  num=$1
  "$(_script_dir)/../pref-tests/rmi-same-jvm/bin/run.sh" "${num}" local > "${DATA_DIR}${num}-local-same.txt"
}

remote_same() {
  num=$1
  "$(_script_dir)/../pref-tests/rmi-same-jvm/bin/run.sh" "${num}" remote > "${DATA_DIR}${num}-remote-same.txt"
}

remote_external() {
  num=$1
  "$(_script_dir)/../pref-tests/rmi-external-jvm/bin/run.sh" "${num}" | tee "${DATA_DIR}${num}-remote-external.txt"
}

http() {
  num=$1
  "$(_script_dir)/../pref-tests/http/bin/run.sh" "${num}" | tee "${DATA_DIR}${num}-http.txt"
}

https() {
  num=$1
  "$(_script_dir)/../pref-tests/https/bin/run.sh" "${num}" | tee "${DATA_DIR}${num}-https.txt"
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
