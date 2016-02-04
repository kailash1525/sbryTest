
STEPS TO RUN / TEST JAVA PROGRAM VIA CONSOLE
============================================

All dependencies of the program are included in the commands. So, we just simply have to run the below commands.

Built using Java Version - 1.8


Get the repo :
==============

Clone the repository from github, issue the below command

```
git clone https://github.com/kailash1525/sbryTest.git
```

Once cloning completed, navigate to project source file root directory (all command should be executed from this directory):

```
cd sbryTest/src
```


Compile Java program :
=======================

```
javac  -cp ".:com/sbry/lib/gson-2.3.1.jar:com/sbry/lib/jsoup-1.8.3.jar:com/sbry/lib/commons-lang3-3.4.jar" com/sbry/controller/WebScrapperController.java
```

Run Java Program :
==================

```
java  -cp ".:com/sbry/lib/gson-2.3.1.jar:com/sbry/lib/jsoup-1.8.3.jar:com/sbry/lib/commons-lang3-3.4.jar" com/sbry/controller/WebScrapperController
```

The above command will display the JSON String result (as per requirement) in the console.

you can copy the JSON string from the console to 'http://jsonviewer.stack.hu/' to view it in more readable format.


Compile Test Program :
======================

```
javac -cp ".:com/sbry/lib/junit-4.11.jar:com/sbry/lib/hamcrest-core-1.3.jar:com/sbry/lib/gson-2.3.1.jar:com/sbry/lib/jsoup-1.8.3.jar:com/sbry/lib/commons-lang3-3.4.jar" com/sbry/testsrc/TestRunner.java
```

Run JUNIT Test :
================

```
java -cp ".:com/sbry/lib/junit-4.11.jar:com/sbry/lib/hamcrest-core-1.3.jar:com/sbry/lib/gson-2.3.1.jar:com/sbry/lib/jsoup-1.8.3.jar:com/sbry/lib/commons-lang3-3.4.jar" com/sbry/testsrc/TestRunner
```

"ALL TEST CASES PASSED" message will get displayed once the test is passed.