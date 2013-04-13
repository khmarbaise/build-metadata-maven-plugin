Build MetaData Maven Plugin
===========================

[![Build Status](https://buildhive.cloudbees.com/job/khmarbaise/job/build-metadata-maven-plugin/badge/icon)](https://buildhive.cloudbees.com/job/khmarbaise/job/build-metadata-maven-plugin/)

Overview
--------


License
-------
MIT ...

Homepage
--------
[http://khmarbaise.github.com/build-metadata-maven-plugin/](http://khmarbaise.github.com/build-metadata-maven-plugin/)

Description
-----------

Also take the following goals of the build-helper-maven-plugin:

released-version       Resolve the latest released 
                       version of this project.
parse-version          Set properties containing the parsed 
                       components of a version string.
timestamp-property     Sets a property based on the current
                       date and time.


maven                  Provide information about maven-version,
                       called goals and active profiles 
                       during the build and the command line 
                       (security?).
java                   Provide information about the java 
                       runtime. JAVA_OPTS? (Security?)
os                     Provide information about the 
                       operation system (name, family, 
                       architecture, version).
hostname               Provide the build hostname.
username               Provide the user und which the build
                       has been run.

metadata               Combine the above goals for convenience
                       (maven, java, os, hostname, username).
                       May be released-version and parse-version
                       can used as well.

metadata-report        Create a report of what has been
                       combined in metadata goal.




Status
------


TODOs
-----

Usage
-----

see homepage.
