package main

import (
	"fmt"
	"strconv"
	"time"

	"github.com/gocolly/colly"
)

type redditThread struct {
	title      string
	rate       uint16
	subReddit  string
	commentURL string
	threadURL  string
}

// NewRedditThread Create a new reddit thread
func NewRedditThread(e *colly.HTMLElement) *redditThread {

	x, err := strconv.Atoi(e.ChildAttr("div.score.unvoted", "title"))
	if err != nil {
		x = 0
	}

	r := redditThread{
		title:      e.DOM.Find("a.title").Text(),
		rate:       uint16(x),
		subReddit:  e.Attr("data-subreddit-prefixed"),
		commentURL: e.ChildAttr("a.comments", "href"),
		threadURL:  e.ChildAttr("a.comments", "href"),
	}
	return &r
}

func main() {

	t := time.Now()

	c := colly.NewCollector(
		colly.AllowedDomains("old.reddit.com"),
		colly.MaxDepth(100),
		colly.Async(true),
	)

	c.OnRequest(func(r *colly.Request) {
		fmt.Printf("Visting %v\n", r.URL.String())
	})

	c.OnHTML("div.thing", func(e *colly.HTMLElement) {
		redditThread := NewRedditThread(e)
		if redditThread.rate > 5000 {
			fmt.Printf("   %v %v\n", redditThread.rate, redditThread.title)
		}
	})

	c.OnHTML("span.next-button a", func(e *colly.HTMLElement) {
		e.Request.Visit(e.Attr("href"))
	})

	c.Visit("https://old.reddit.com/r/cats/")

	c.Wait()

	fmt.Printf("Time elapsed %v\n", time.Since(t).Milliseconds())
}
