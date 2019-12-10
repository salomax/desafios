# Challenge #2 - Crawlers

Create a reddit crawler.

### Input
* subreddit names joined by ';'. ie: "askreddit;worldnews;cats"

### Part #1

* Print reddit thread list with rate, title, links (thread and comment).
* Show it in a CLI

##### Implementation

* CLI implemented with Spring Boot Shell. See `SearchRedditThreadCommand`.
* Command `search` does the job:

```sh
$ search askreddit;worldnews;cats
```
* I setup a thread limit (50 by subreddit). That's the case if there're a huge number of pages from Reddit
  and thus we have to prevent to keep executing it indefinitely.

* **Overdelivery** Also created a second implementation for multithread loading and parsing Reddit HTML. Just add `--multithread` option.

```sh
$ search askreddit;worldnews;cats --multithread
```

Multi thread option has a timeout set to 60s up as default.

Regardless the implementation, either Single thread or Multi thread, each subreddit runs in parallel anyway.
**Multi thread** option is applied **only** for loading and parsing Reddit HTML as mentioned above.

### Part #2

* Build a Telegram bot to send a message back with the same reply as part #1
  whenever it receives the command /NadaPraFazer [+ Lista de subrredits].

##### Implementation

No big deal, just a simple integration with telegram using a bot implementation through the class `SalomaxIdWallBot`.
To keep the generalization, I created an interface called `TelegramCommand` and hence, it makes easy to create multiple commands
and map them with Telegram by qualifiers, as the example @Qualifier("/NadaPraFazer") on class `RedditCrawlerTelegramCommand`.

**Telegram uses multi thread solution. Therefore, it has a timeout (60s) or until the crawler searches all over pages.**

#### Unit test and Mock test

As you can see, I built an unit test `SingleThreadCrawlerUnitTest` using mocks.
I've done just this one as example for how to ensure the rules and to keep the code trustful throughout new releases.

### Build Application

Go to application folder and run the command (unix-based os example):

```sh
./mvnw clean install
```

Ensure Docker daemon is running on your machine.

### Run Application

After you build the application, you are able to run it  just execution the command below:

```sh
java -jar target/idwall-reddit-crawler-1.0-SNAPSHOT.jar
```

##### Simple search

```sh
$ search askreddit;worldnews;cats --multithread
```

##### Exit from the application

```sh
$ exit
```

### Other options

You can replace Telegram bot config by the parameters:

```sh
java -jar \
    -Dtelegram.bot.username=SalomaxIdWallBot \
    -Dtelegram.bot.token=979348664:AAE_PhawwduBpoZCZBOb-UsrxGuKY2xDS4E \
    target/idwall-reddit-crawler-1.0-SNAPSHOT.jar
```


### Run Docker

You are also able to run this application by Docker. During the deploy, the image is setup on Docker machine.

###### Check the image:

```sh
docker image ls
```

###### You have to find the image *salomax/idwall-reddit-crawler*

```sh
REPOSITORY                                         TAG                     IMAGE ID            CREATED             SIZE
salomax/idwall-reddit-crawler                      1.0-SNAPSHOT            91c25253b773        30 seconds ago      142MB
```

###### Now just run the examples following, in order to run the application by Docker:

```sh
docker run --rm -it salomax/idwall-reddit-crawler:1.0-SNAPSHOT
```

Don't forget param `it`, otherwise a NullPointerException will be thrown because interactive mode is not enabled.

