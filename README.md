# Overview

This project aims to providing a) a base for initial project setup and 
b) a software development process definition. So for creatiung a new project,
you may just fork (or copy) this project. Build, Deployment and IDE-Setup can be
 handled using the provided SBT build tool.


# IDE-Setup (Eclipse)

## Get Scala-IDE

```
http://scala-ide.org/
```


## Generating project files

Just generate the Eclipse project files using. Pleas enote this command will 
also fetch all necessary sources from third-party libraries.

```
$ ./sbt eclipse
```

