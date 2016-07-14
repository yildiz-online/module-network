# Yildiz-Engine module-network.

This is the official repository of The Yildiz-Engine Network Module, part of the Yildiz-Engine project.
The network module is an abstract component meant to connect different system together.
It requires an implementation module to materialize it.

## Status [![Quality Gate](https://www.sonarqube.com/api/badges/gate?key=be.yildiz-games:module-network)](https://sonarqube.com/overview?id=be.yildiz-games:module-network)

## Features

* Server capabilities.
* Authentication.
* Message parsing.
* Client capabilities.
* ...

## Requirements

To build this module, you will need a java 8 JDK, and Maven 3.

## Coding Style and other information

Project website:
http://www.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarqube.com/overview?id=be.yildiz-games:module-network

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.
## Build instructions for module-graphic using maven.

Go to your root directory, where you POM file is located.

Then invoke maven

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file.

## Usage

To use the snapshot versions, please add the following repository
https://oss.sonatype.org/content/repositories/snapshots/

Released version are retrieved from maven central.

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>module-network</artifactId>
    <version>1.0.0-0-SNAPSHOT</version>
</dependency>
```
## Contact
Owner of this repository: Grégory Van den Borre