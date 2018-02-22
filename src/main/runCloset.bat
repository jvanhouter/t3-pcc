echo off

javac -d classes -classpath classes; common\*.java database\*.java event\*.java exception\*.java impresario\*.java model\*.java userinterface\*.java Utilities\*.java
javac -classpath classes;. Closet.java
java -cp mysql-connector-java-5.1.7-bin.jar;classes;. Closet