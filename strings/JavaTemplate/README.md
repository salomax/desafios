
# Challenge #1 - String formatting

Build an application that is going to receive 3 parameters:
* Input text
* Length limit per line (optional, default 40)
* Is text justified? (optional, default true)


## Implementation

In a few words, the class `IdwallFormatter` is the implementation for String formatting.

However, the classes `Text` and `Line` encapsulate the logic to handle the input, either break the lines and justify whole text.

Also, to keep it **cohesive**, there's a style class `LineAlignment` for justifying the text, which the implementation is `JustifyLineAlignment`.

### Build Application

Go to application folder and run the command (unix-based os example):

```sh
./mvnw clean install
```

Ensure Docker daemon is running on your machine.

### Run Application

After you build the application, you are able to run it  just execution the command below:

###### Default values
```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar
```

###### Alternative text
```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)"
```

###### Alternative text and alternative line length
```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 50
```

###### Alternative text, alternative line length and skip justified
```sh
java -jar target/StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 60 false
```


### Run Docker

You are also able to run this application by Docker. During the deploy, the image is setup on Docker machine.

###### Check the image:

```sh
docker image ls
```

###### You have to find the image *salomax/idwall-string-formatter*

```sh
REPOSITORY                                         TAG                     IMAGE ID            CREATED             SIZE
salomax/idwall-string-formatter                    1.0-SNAPSHOT            76af4b6ceea2        21 seconds ago      84.9MB
```

###### Now just run the examples following, in order to run the application by Docker:

```sh
docker run --rm -w /usr/bin/StringFormatter salomax/idwall-string-formatter:1.0-SNAPSHOT java -jar StringFormatter-1.0-SNAPSHOT.jar
```

```sh
docker run --rm -w /usr/bin/StringFormatter salomax/idwall-string-formatter:1.0-SNAPSHOT java -jar StringFormatter-1.0-SNAPSHOT.jar "$(cat src/test/resources/bible-creation.txt)" 40 true
```