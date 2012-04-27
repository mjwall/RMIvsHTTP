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
the HTTP client is located on a different server.

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
1.  You may have to modify your settings.xml.  This is used to do deployments via JMX.  I didn't test it, but here is what I have in mine.

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

Hit the following url http://localhost:8080/rmi-vs-http-local/localtest.html.  At the bottom you should see the time it took.  Refresh a couple of times to see the time change

#### RMI via a remote interface on the same JVM

Hit the following url http://localhost:8080/rmi-vs-http-local/remotetest.htm.  Again, see the time at the bottom

#### RMI via a remote interface on a different JVM

Change to the remote directory and execute the following

    ./bin/run.sh
    
The output will look similar to the test above and is in fact running the same code.  Rerun the commmand a couple of times 
to see changes

## Results

Average times.  See http://mjwall.github.com/RMIvsHTTP/ for a pretty graph

* 1000 runs
    1. local - same JVM       = 120.8
    2. remote - same JVM      = 237.1
    3. remote - different JVM = 2513
    
* 10000 runs
    1. local - same JVM       = 1156.8
    2. remote - same JVM      = 2299.9
    3. remote - different JVM = 17646.8


## Data

Running each test 10 times at each level.  All numbers in milliseconds

### Execute addOne 1000 times

* local - same JVM: 147, 141, 126, 108, 120, 119, 120, 108, 111, 108
* remote - same JVM: 253, 231, 250, 237, 232, 240, 229, 227, 243, 229
* remote - different JVM: 2561, 2525, 2492, 2549, 2513, 2492, 2515, 2480, 2528, 2505

### Execute addOne 10000 times

* local - same JVM:  1097, 1243, 1160, 1137, 1150, 1159, 1181, 1154, 1137, 1150
* remote - same JVM: 2311, 2337, 2305, 2306, 2270, 2294, 2302, 2292, 2304, 2278
* remote - different JVM: 20669, 22084, 17530, 16500, 16544, 16632, 16684, 16674, 16293, 16858

### Execute addOne 100000 times

* local - same JVM:
* remote - same JVM:
* remote - different JVM:

### Execute addOne 1000000 times

* local - same JVM:
* remote - same JVM:
* remote - different JVM:
