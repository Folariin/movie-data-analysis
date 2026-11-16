JDBC_JAR = mssql-jdbc-11.2.0.jre11.jar

# Java classpath separator is ';' on Windows and ':' elsewhere.
# On Windows, make runs recipes with sh if Git Bash/MSYS is on PATH, otherwise with cmd.exe.
ifeq ($(OS),Windows_NT)
	CP_SEP = ;
	ifeq ($(shell echo %OS%),Windows_NT)
		CLEAN_CMD = del /Q *.class 2>NUL & exit /b 0
	endif
else
	CP_SEP = :
endif
CLEAN_CMD ?= rm -f *.class

build: GroupInterface.class

GroupInterface.class: GroupInterface.java
	javac GroupInterface.java

run: GroupInterface.class
	java -Dfile.encoding=UTF-8 -cp ".$(CP_SEP)$(JDBC_JAR)" GroupInterface

clean:
	$(CLEAN_CMD)
