# Yildiz-Engine module-network.

This is the official repository of the Network Module, part of the Yildiz-Engine project.
The network module is an abstract component meant to connect different system together.
It requires an implementation module to materialize it.

## Features

* Server capabilities.
* Authentication.
* Message parsing.
* Client capabilities.
* ...

## Requirements

To build this module, you will need the latest java JDK, and Maven.

## Coding Style and other information

Project website:
https://engine.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarqube.com/overview?id=be.yildiz-games:module-network

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.

## Build instructions
Go to your root directory, where you POM file is located.

Then invoke maven

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file.

## Usage

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>module-network</artifactId>
    <version>LATEST</version>
</dependency>
```
## Contact
Owner of this repository: Grégory Van den Borre