#!/bin/bash

# assumes JBOSS_HOME is set
java -classpath $JBOSS_HOME/client/*:$JBOSS_HOME/lib/*:../ejb/target/rmi-vs-http-ejb.jar:target/rmi-vs-http-remote.jar mjwall.RemoteClient