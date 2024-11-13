The UML diagram will only show the strongest relation


Get-ChildItem -Recurse -Filter *.java | ForEach-Object { javac -d out -sourcepath src $_.FullName }
java -cp out app.HMSApp