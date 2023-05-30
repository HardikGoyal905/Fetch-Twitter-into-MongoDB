# Fetch-Twitter-into-MongoDB

Put the Twitter Developer Credentials in the Cred.txt File in the Resources directory and Run the following Command:

mvn compile exec:java -Dexec.mainClass="org.example.Main" -Dmongodb.uri="CONNECTIONSTRING" -Dprofile="USERNAME"

mvn compile exec:java -Dexec.mainClass="org.example.Tweets" -Dmongodb.uri="mongodb+srv://myAtlasDBUser:myatlas-001@myatlasclusteredu.ixicyba.mongodb.net/?retryWrites=true&w=majority" -Dprofile="ABdeVilliers17" -DfavGt="100000"
