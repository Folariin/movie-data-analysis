JDBC_JAR = mssql-jdbc-11.2.0.jre11.jar

# Java classpath separator is ';' on Windows and ':' elsewhere.
ifeq ($(OS),Windows_NT)
	CP_SEP = ;
else
	CP_SEP = :
endif

build: GroupInterface.class

GroupInterface.class: GroupInterface.java
	javac GroupInterface.java

run: GroupInterface.class
	java -Dfile.encoding=UTF-8 -cp ".$(CP_SEP)$(JDBC_JAR)" GroupInterface

clean:
	rm -f *.class
