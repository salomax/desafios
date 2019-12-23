# Performance Analysis

## Crawler implementation comparison
### Java + JSoup vs Golang + Colly

Filtered by subreddit cats
Both using parallelism 
Environment
* Processor 2.7 GHz Dual-Core Intel Core i5
* Memory 16 GB 1867 MHz DDR3
         
| Attempt  | 1st      | 2nd      | 3rd      | Avg       |
|----------|----------|----------|----------|-----------|
| Java     | 28498    | 26806    | 26434    | 27246     |
| Golang   | 26753    | 26152    | 27084    | 26663     |

time in ms

**Conclusion -> Java is slower, on average, by 2.1%.** 

## Docker

```sh
docker build -t salomax/idwall-go-crawler:1.0 .
```

```sh
docker run --rm salomax/idwall-go-crawler:1.0
```