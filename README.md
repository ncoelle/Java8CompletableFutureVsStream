# Java8CompletableFutureVsStream
Code of blog post of Fahd Shariff - [Java 8: CompletableFuture vs Parallel Stream][1]

## Get started
The IntelliJ Idea files are checked-in (which is bad practice).

## Runtime Environment
* 2,6 GHz Intel Core i7
* 4 cores with HyperThreading
* Java 1.8 
	````
	java version "1.8.0_144"
	Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
	Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
	````

|Parameters|.stream()|.parallelStream()|ComparableFuture|CompletableFuture with Executor Using 10 Threads|
|---|---:|---:|---:|---:|
|1 Work Items and sleep(1000ms)|1000|1004|1007|1010|
|10 Work Items and sleep(1000ms)|10034|2007|2011|1007|
|100 Work Items and sleep(1000ms)|100258|13047|15056|10035|

(All data in ms)

[1]:	http://fahdshariff.blogspot.de/2016/06/java-8-completablefuture-vs-parallel.html