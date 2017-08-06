package app;

import model.MyTask;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {
	public static void main(String[] args) {
		final List<MyTask> tasks = IntStream.range(0, 10)
				.mapToObj(i -> new MyTask(1))
				.collect(Collectors.toList());
		runSequentially(tasks);
		useParallelStream(tasks);
		useCompletableFuture(tasks);
		useCompletableFutureWithExecutor(tasks);
	}

	private static void runSequentially(List<MyTask> tasks) {
		long start = System.nanoTime();
		final List<Integer> result = tasks.stream()
				.map(MyTask::calculate)
				.collect(Collectors.toList());
		long duration = (System.nanoTime() - start);
		System.out.println("Running sequentially");
		System.out.printf("Processed %d tasks in %d milliseconds\n", tasks.size(), TimeUnit.NANOSECONDS.toMillis(duration));
		System.out.println("result = " + result);
	}

	private static void useParallelStream(List<MyTask> tasks) {
		long start = System.nanoTime();
		final List<Integer> result = tasks.parallelStream()
				.map(MyTask::calculate)
				.collect(Collectors.toList());
		long duration = (System.nanoTime() - start);
		System.out.println("\nUsing parallelStream()");
		System.out.printf("Processed %d tasks in %d milliseconds\n", tasks.size(), TimeUnit.NANOSECONDS.toMillis(duration));
		System.out.println("result = " + result);
	}

	private static void useCompletableFuture(List<MyTask> tasks) {
		long start = System.nanoTime();
		final List<CompletableFuture<Integer>> futures = tasks.stream()
				.map(t -> CompletableFuture.supplyAsync(t::calculate))
				.collect(Collectors.toList());
		final List<Integer> result = futures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
		long duration = (System.nanoTime() - start);
		System.out.println("\nUsing CompletableFuture()");
		System.out.printf("Processed %d tasks in %d milliseconds\n", tasks.size(), TimeUnit.NANOSECONDS.toMillis(duration));
		System.out.println("result = " + result);
	}

	private static void useCompletableFutureWithExecutor(List<MyTask> tasks) {
		final long start = System.nanoTime();
		final ExecutorService executor = Executors.newFixedThreadPool(Math.min(tasks.size(), 10));
		final List<CompletableFuture<Integer>> futures = tasks.stream()
				.map(t -> CompletableFuture.supplyAsync(t::calculate, executor))
				.collect(Collectors.toList());
		final List<Integer> result = futures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
		long duration = (System.nanoTime() - start);
		System.out.println("\nUsing CompletableFuture with Executor");
		System.out.printf("Processed %d tasks in %d milliseconds\n", tasks.size(), TimeUnit.NANOSECONDS.toMillis(duration));
		System.out.println("result = " + result);
		executor.shutdown();
	}
}
