# Java8CompletableFutureVsStream
Code of blog post of Fahd Shariff - [Java 8: CompletableFuture vs Parallel Stream](http://fahdshariff.blogspot.de/2016/06/java-8-completablefuture-vs-parallel.html)

This project uses IntelliJ and Gradle.

## Runtime Environment
* 2,6 GHz Intel Core i7
* 4 cores with HyperThreading
* Java 1.8 
    ````
    java version "1.8.0_144"
    Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
    Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
    ````

## Results for 10 Threads and sleep(1000ms)
### .stream()
* everything on the main-Thread (as expected)
* took approx. 10s (as expected)

````
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Thread.currentThread().getName() = main
Running sequentially
Processed 10 tasks in 10053 milliseconds
````

### .parallelStream()
* uses ForkJoinPool and main-Thread (as expected)
* took approx. 2s (better than expected)
````
Thread.currentThread().getName() = main
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-4
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-1
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-5
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-2
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-3
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-6
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-7
Thread.currentThread().getName() = main
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-5
Using parallelStream()
Processed 10 tasks in 2010 milliseconds
````

### ComparableFuture()
* uses ForkJoinPool only (as expected)
* took approx 2s (as expected after .parallelSteam() because both use ForkJoinPool)
````
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-5
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-6
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-1
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-4
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-2
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-3
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-7
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-6
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-3
Thread.currentThread().getName() = ForkJoinPool.commonPool-worker-2

Using CompletableFuture()
Processed 10 tasks in 2007 milliseconds
````

### CompletableFuture with Executor
* uses it's own pool (as expected)
* took approx. 1s (better than expected. Because the runtime environment has 8 'cores' and the test has 10 work items,
  I expected something around 1300ms. But after rethinking the test it is the expected result because VM and CPU can
  optimize the sleep() statement)
  
````
Thread.currentThread().getName() = pool-1-thread-1
Thread.currentThread().getName() = pool-1-thread-2
Thread.currentThread().getName() = pool-1-thread-3
Thread.currentThread().getName() = pool-1-thread-4
Thread.currentThread().getName() = pool-1-thread-5
Thread.currentThread().getName() = pool-1-thread-6
Thread.currentThread().getName() = pool-1-thread-7
Thread.currentThread().getName() = pool-1-thread-8
Thread.currentThread().getName() = pool-1-thread-9
Thread.currentThread().getName() = pool-1-thread-10

Using CompletableFuture with Executor
Processed 10 tasks in 1010 milliseconds

Process finished with exit code 0
```