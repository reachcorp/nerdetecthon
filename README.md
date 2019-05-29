# NERdetecthon

## Prérequis
1. Install java 11 & maven
    
       $ ./java_maven_install.sh

2. Add JAVA_HOME

       $ sudo nano /etc/environment
      *ajouter la ligne suivante:*
      
       JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/bin/"
       

2. Check versions

       $ java -version
       openjdk version "11.0.3" 2019-04-16
       OpenJDK Runtime Environment (build 11.0.3+7-Ubuntu-1ubuntu218.04.1)
       OpenJDK 64-Bit Server VM (build 11.0.3+7-Ubuntu-1ubuntu218.04.1, mixed mode, sharing)
-
       $ mvn -version
       Apache Maven 3.6.0
       Maven home: /usr/share/maven
       Java version: 11.0.3, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-11-openjdk-amd64
       Default locale: fr_FR, platform encoding: UTF-8
       OS name: "linux", version: "4.18.0-20-generic", arch: "amd64", family: "unix"
