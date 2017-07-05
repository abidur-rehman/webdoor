To start this App.

1. Start hsqldb server via command:- java -cp hsqldb.jar org.hsqldb.Server -database.0 file:Webdoor -dbname.0 Webdoor
2. Connect to this db via any client or via hsqldb command:-  java -cp hsqldb.jar org.hsqldb.util.DatabaseManager
and create tables. SQL script is included in the project.
3. Start your app