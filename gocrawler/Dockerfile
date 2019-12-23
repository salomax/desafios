FROM golang:latest 
COPY ./src/idwall/Main.go .
RUN go get github.com/gocolly/colly/...
RUN go build -o idwall .
CMD ["./idwall"]