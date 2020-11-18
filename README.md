[![Build Status](https://travis-ci.org/simonNozaki/rest-api-framework.svg?branch=master)](https://travis-ci.org/simonNozaki/rest-api-framework)
# rest-api-framework
## Overview
Perhaps, on building REST API with Java or Kotlin, we always do the same things like  
checking JSON inputs and building a response object.  
This framework offers convenient methods for those situations.

## Preface
This framework works well with an architecture of DDD.   
You can handle the problem well on using DDD layers(controllers, services, and so on).  

## Feature
We can feature mainly 3 functions:  
- Input checking
- Logging with `Logback`, `slf4j`
- Response Builder
- a Web API client

## Requirements  
- Java ... 11

## Installing
Add the dependencies.
#### Gradle
Add the dependency.  
build.gradle...
```groovy
implementation group: 'net.framework.api.rest', name: 'rest-api-framework', version: '1.1.0'
```
build.gradle.kts...
```kotlin
implementation("net.framework.api.rest:rest-api-framework:1.1.0")
```
#### Maven
Add the dependency on `pom.xml`.
```xml
<dependency>
  <groupId>net.framework.api.rest</groupId>
  <artifactId>rest-api-framework</artifactId>
  <version>1.1.0</version>
</dependency>
```
See more: https://github.com/simonNozaki/rest-api-framework/packages
## How it works
This library has convenient classes for handling common problems.
See usages in tests.   
### Helpers
- `RestControllerHelper`
- `ServiceHelper`
### Client
- `SimpleApiClient` ... an API client singleton. Easily can build client.
### Extension
- `StdioExtensions` ... extensions for standard I/O
- `ObjectInspector` ... Error building singleton. This class test a subject and build errors

#Contributing
If you are interested in this library, first of all, Thanks a lot!  
Anytime waiting for your idea or contributing! 