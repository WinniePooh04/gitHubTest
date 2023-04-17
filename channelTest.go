package main

import (
	"fmt"
	"time"
	"sync"
)

func main() {
	// Channel is used to communicate betweeen each go routine
	// Channel with buffered limit is for Asynchronous 
	// Channel with no buffered limit is for Synchronous
	// For synchronous go routine is block until receiver receive the message then send new again
	// For Asynchronous go rountine can send 1 more as per defiend limit
	//Test1()
	//Test2()
	//Test3()
	Test4()
}
/**
* Test for for select channel
*/
func Test1() {
	charChannel := make(chan string, 3)
	chars := []string{"a", "b", "c"}

	for _, s := range chars {
		select {
		case charChannel <- s:
		}
	}
	close(charChannel)
	for result := range charChannel {
		fmt.Println(result)
	}
}
/**
* Test for infinite loop with channel
* Stop the loop from parent function
**/
func Test2() {
	done := make(chan bool)
    go doWork(done)
	time.Sleep(time.Second * 3)
	// when close the channel it will stop the loop
	close(done)
}

func doWork(done <- chan bool) {
	for {
		select {
		case <- done:
			return
		default:
			fmt.Println("DOING WORK")
		}
	}
}
/**
** sample of working pipeline process
** divide each stage to works as channel with goroutine
*/
func Test3() {
// input
nums := []int{1,2,3,4}
// each stage will be asynchronus because use buffer channel with unlimited
//stage 1
dataChannel1 := sliceToChannel(nums)
// stage 2
finalChannel := sq(dataChannel1)
// stage 3
for n := range finalChannel {
	fmt.Println(n)
}
}
func sliceToChannel(nums []int) <-chan int {
	out := make(chan int)
	go func(){
		for _, n := range nums {
			out <- n // would block the channel until the value is work on another function
		}
		close(out)
	}()
	return out
}
func sq(in <-chan int) <-chan int {
	out := make(chan int)
	go func() {
		for  n := range in {
		out <- n * n
		}
		close(out)
	}()
	return out
}

/**
* limit the goroutine with channel buffer
*/
func Test4() {
   var num int
   limiter := make(chan int, 8)
   wg := new(sync.WaitGroup)
   for num = 1; num < 100; num++ {
	limiter <- 1
	wg.Add(1)
     go func(num int) {
	  defer wg.Done()
      fmt.Println(num)
	  <- limiter
	 }(num)
   }
   close(limiter) 
   wg.Wait()
}