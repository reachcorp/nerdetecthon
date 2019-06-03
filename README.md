# nerdetecthon

## Prérequis
1. Install java 11 & maven
    
       $ ./java_maven_install.sh

2. Add JAVA_HOME

    2.a. éditer le fichier /etc/environment

       $ sudo nano /etc/environment
    2.b. ajouter la ligne suivante:
      
       JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/"
       
    2.c. "sourcer" le fichier /etc/environment
      
       source /etc/environment
       
3. Check versions

       $ java -version
       openjdk version "1.8.0_212"
       OpenJDK Runtime Environment (build 1.8.0_212-8u212-b03-0ubuntu1.18.04.1-b03)
       OpenJDK 64-Bit Server VM (build 25.212-b03, mixed mode)

-
       $ mvn -version
       Apache Maven 3.6.0
       Maven home: /usr/share/maven
       Java version: 1.8.0_212, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
       Default locale: fr_FR, platform encoding: UTF-8
       OS name: "linux", version: "4.18.0-20-generic", arch: "amd64", family: "unix"
       
       
   ## Build
    ./build.sh