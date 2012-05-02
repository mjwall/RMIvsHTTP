# RMI vs HTTP

## Purpose
The purpose of this project is to test the performance of EJB communication vs
exposing the EJB as a service and communication over HTTP/HTTPS

## Design

### Code under test

The code under test is 2 EJBs and a WAR.  The first EJB has a method that simply adds one to the input.  The REST EJB gets a reference to the first EJB and exposes it's method via RESTEasy.  The WAR file simple exposes the REST EJB and serializes the response to XML.  Typically, this setup would go into an EAR, but I choose to deploy them separately.  For more info, see http://mjwall.github.com/RMIvsHTTP/ or read the code.

### Test Scenarios

There are 5 scenarios

1.  RMI using a local interface to the REST EJB, obviously the same JVM
1.  RMI using a remote interface to the REST EJB on the same JVM
1.  RMI using a remote interface to the REST EJB on a different JVM
1.  HTTP calling the exposed REST endpoint from the REST EJB
1.  HTTPS calling the exposed REST endpoint from the REST EJB
 
The tests run on the same machine, so there is no network latency.  In practice, the network will decrease the performance if the remote interface is called from a different machine or if the HTTP client is located on a different server. For more information the test design, again see http://mjwall.github.com/RMIvsHTTP/ or read the code.

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

Assuming you have everything running correctly, the following instructions explain to reproduce each of the tests.  By default, each test will run 15 times.  If you want to change that number, you will need to modify numberOfTests in the TestHarness class and recompile and deploy.  Each test needs to know the number of times to execute per run.  Examples below show 10, so there will be 30 runs with 10 executions each time per test.  Change that number as you like.

There is also a script in bin name executor.sh.  This script will run all the tests and dump results to the configured directory.  Modify this script as you see fit.

#### RMI via a local interface

There is a servlet deployed in rmi-same-jvm.war file.  Run the following to use curl to hit http://localhost:8080/rmi-same-jvm/local?runsPerTest=10.
 
    ./pref-tests/rmi-same-jvm/bin/run.sh 10 local

#### RMI via a remote interface on the same JVM

Also in the rmi-same-jvm.war file is another servlet that uses the remote interface.  Run the following to use curl to hit http://localhost:8080/rmi-same-jvm/remote?runsPerTest=10.
 
    ./pref-tests/rmi-same-jvm/bin/run.sh 10 remote

#### RMI via a remote interface on a different JVM

Run the following to access the REST EJB using the remote interface from an external JVM.

    ./pref-tests/rmi-external-jvm/bin/run.sh 10
    
#### HTTP test

The REST endpoint is available at http://localhost:8080/rmi-vs-http-war/rest/add/one?to=122323.  Execute the following to run this test, using HTTPClient to gather and parse the response XML.

    ./pref-tests/http/bin/run.sh 10  

#### HTTPS test

The REST endpoint is also available at https://localhost:8443/rmi-vs-http-war/rest/add/one?to=122323.  Execute the following to run this test, using HTTPClient hand the SSL, gather the response then parse the XML.

    ./pref-tests/https/bin/run.sh 10  

## Results

See http://mjwall.github.com/RMIvsHTTP/ for results information