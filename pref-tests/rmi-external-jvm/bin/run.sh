#!/bin/bash

# assumes JBOSS_HOME is set.  Getting all the dependencies in the executable jar is too bloated
#java -classpath $JBOSS_HOME/client/*:$JBOSS_HOME/lib/*:target/rmi-external-jvm-test-jar-with-dependencies.jar mjwall.rmiExternalJVM.RemoteClient
java -classpath ./target/dependency/*:target/rmi-external-jvm-test.jar mjwall.rmiExternalJVM.RemoteClient