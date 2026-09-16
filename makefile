build: GroupInterface.class

GroupInterface.class: GroupInterface.java
	javac GroupInterface.java

run: GroupInterface.class
	java -Dfile.encoding=UTF-8 -cp .:mssql-jdbc-11.2.0.jre11.jar GroupInterface

clean:
	rm GroupInterface.class
	rm MyDatabase.class
