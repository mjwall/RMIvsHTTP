#!/bin/bash

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

if [ $# -ne 1 ]; then
  echo "You may pass in the number of executions per run, defaulting"
  num=10
else
  num=$1
fi

echo "Running HTTPS test with ${num} executions per run"
CMD="java -jar $(_script_dir)/../target/rmi-vs-http-https-test-jar-with-dependencies.jar ${num}"
echo "${CMD}"
$CMD

