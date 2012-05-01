#!/bin/bash

# all extra jars for jboss-as-client are in the target/dependency directory
# You must pass in a number of times to run per test
if [ $# -ne 1 ]; then
  echo "You must pass in a number of times to run per test"
else
  java -classpath ./target/dependency/*:target/rmi-external-jvm-test.jar mjwall.rmiExternalJVM.RemoteClient $1
fi