 mvn archetype:generate -B -DarchetypeArtifactId=maven-archetype-archetype -DartifactId=firstMavenArchetype -DgroupId=com.romanidze -Dversion=1.0

 mvn clean install

 mvn archetype:generate -DarchetypeGroupId=com.romanidze -DarchetypeArtifactId=firstMavenArchetype -DarchetypeVersion=1.0 -DgroupId=org.example -DartifactId=mavenArchetype

 mvn archetype:crawl -Dcatalog=C:\Users\Андрей\.m2\archetype-catalog.xml