# RMI vs HTTP

## Purpose
The purpose of this project is to test the performance of EJB communication vs
exposing the EJB as a service and communication over HTTP/HTTPS

## Design
I will test 4 scenarios:

1.  RMI using a local EJB interface, obviously the same JVM
2.  RMI using a remote EJB interface on the same JVM
3.  RMI using a remote EJB interface on a different JVM
3.  HTTP exposing the EJB as a REST service
4.  HTTPS exposting the EJB as a REST service

These tests will all use the same EJB.  They will be run on the same machine.
to remove any network latency.  In practice, the network will decrease the
performance if the remote interface is called from a different machine or if
the HTTP client is located on a different server. For more information the test design, 
see http://mjwall.github.com/RMIvsHTTP/ or read the code

## Running these for yourself

### Getting everything installed and running

1.  Install JBoss 6.1.0.Final, download from http://download.jboss.org/jbossas/6.1/jboss-as-distribution-6.1.0.Final.zip.
If you install it in ~/opt/jboss, you will not need to modify the project pom.  A symlink works too.
1.  Install my patched version of the jboss maven plugin by cloning https://github.com/mjwall/maven-jboss-plugin.  Run the following and it should be be available for your use.  Read the README in the project to find out more 
 
        mvn clean install

1.  Clone this project and cd into the top level directory
1.  Open the top level pom and modify the jbossHome property to point to your install if you didn't install jboss in 
~/opt/jboss.
1.  Set the JBOSS_HOME environment variable to point the same install directory
1.  Configure JBoss to run with SSL.  Copy the pref-test/https/src/main/resources/server.keystore file to 
$JBOSS_HOME/server/default/conf.  You will also need to modify $JBOSS_HOME/server/default/deploy/jbossweb.sar/server.xml
file as show below.  In case you need it to know, the server.keystore was generated with the following command

        keytool -genkey -alias rmi-vs-http -keyalg RSA -validity 1000 -keystore server.keystore -storetype JKS

 
        <!-- This is what goes in the server.xml file -->
        <Connector protocol="HTTP/1.1" SSLEnabled="true"
            port="${jboss.web.https.port}" address="${jboss.bind.address}"
            scheme="https" secure="true" clientAuth="false"
            keystoreFile="${jboss.server.home.dir}/conf/server.keystore"
            keystorePass="changeit" sslProtocol = "TLS" />

1.  You may have to modify your ~/.m2/settings.xml.  This is used to do deployments via JMX.  I didn't test it, but here is what I have in mine.

        <servers>
          <server>
            <id>jboss-local</id>
            <username>admin</username>
            <password>admin</password>
          </server>
        </servers>
    
1. Compile the project by running the following, which will build all the artifacts for you.

        mvn clean package
    
1. Deploy the artifacts to jboss by running the following.  This will copy jars and wars in JBOSS_HOME/server/default/deploy.

        mvn jboss:hard-deploy
    
1. Start JBoss by running the following and then browse to http://localhost:8080 

        mvn jboss:start

### Executing the tests

Assuming you have everything running correctly, the following instructions explain to reproduce each of the tests.

#### RMI via a local interface

Hit the following url http://localhost:8080/rmi-same-jvm/local.  At the bottom you should see the time it took.  Refresh a couple of times to see the time change

#### RMI via a remote interface on the same JVM

Hit the following url http://localhost:8080/rmi-same-jvm/remote.  Again, see the time at the bottom

#### RMI via a remote interface on a different JVM

Change to the pref-test/rmi-external-jvm directory and execute the following

    ./bin/run.sh
    
#### HTTP test

The REST endpoint is available at http://localhost:8080/rmi-vs-http-rest/rest/add/one?to=122323.  To run this test, 
change to the pref-test/http directory and execute the following

    ./bin/run.sh   

#### HTTPS test

The REST endpoing is available at https://localhost:8443/rmi-vs-http-rest/rest/add/one?to=122323.  To run this test, 
change to the prefs-test/http directory and execute the following

    ./bin/run.sh   

## Results

See http://mjwall.github.com/RMIvsHTTP/ for results information