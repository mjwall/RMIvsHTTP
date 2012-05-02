#!/bin/bash

BASE_URL="http://localhost:8080/rmi-same-jvm/"

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

if [ $# -ne 2 ]; then
  echo "You may pass in 2 args, the number of executions per run and local or remote, defaulting"
  location=local
  num=10
else
  num=$1
  location=$2
fi

echo "Running SAME JVM test against the ${location} interface with ${num} executions per run"
CMD="curl ${BASE_URL}${location}?runsPerTest=${num}"
echo "${CMD}"
$CMD


