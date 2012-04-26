# RMI vs HTTP

## Purpose
The purpose of this project is to test the performance of EJB communication vs
exposing the EJB as a service and communication over HTTP/HTTPS

## Design
I will test 4 scenarios:

1. RMI using a local EJB interface
2. RMI using a remote EJB interface
3. HTTP exposing the EJB as a REST service
4. HTTPS exposting the EJB as a REST service

These tests will all use the same EJB.  They will be run on the same machine.
to remove any network latency.  In practice, the network will decrease the
performance if the remote interface is called from a different machine or if
the HTTP client is located on a different server.

## Notes

This project uses JBoss 6.1.0.Final
Also used is the jboss-maven-plugin version 1.5.1-mjwall-1, which is fork of the release plugin with a patch.  Not sure 
if it will ever be released, as most work appears to be going into JBoss 7 stuff.  You can 

   mvn clean install 

after cloning https://github.com/mjwall/maven-jboss-plugin if you want to use my fork.
