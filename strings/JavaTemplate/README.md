
# Challenge #1 - String formatting

Bla bla

### Build Application

```sh
./mvnw clean install
```


Ensure Docker daemon is running on your machine.

### Run Application


```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar
```

```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)"
```

```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 50
```

```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 60 false
```

### Run Docker

```sh
docker image ls
```

```sh
REPOSITORY                                         TAG                     IMAGE ID            CREATED             SIZE
salomax/idwall-string-formatter                    1.0-SNAPSHOT            76af4b6ceea2        21 seconds ago      84.9MB
```

```sh
docker run --rm -w /usr/bin/StringFormatter salomax/idwall-string-formatter:1.0-SNAPSHOT java -jar StringFormatter-1.0-SNAPSHOT.jar
```

```sh
docker run --rm -w /usr/bin/StringFormatter salomax/idwall-string-formatter:1.0-SNAPSHOT java -jar StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 40 true
```