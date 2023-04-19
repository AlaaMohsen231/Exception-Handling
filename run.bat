@echo off
javac -d  Container.java 
echo **Testing Normal file**
java Container.java Normal.arxml
echo **Testing Empty file**
java Container.java Empty.arxml
echo **Testing wrong extension file**
java Container.java test.txt
echo **Testing non exist file**
java Container.java Empt.arxml

pause