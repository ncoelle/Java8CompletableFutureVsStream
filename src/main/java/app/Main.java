package app;

import model.MyTask;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {
	public static void main(String[] args) {
		final List<MyTask> tasks = IntStream.range(0, 10)
				.mapToObj(MyTask::new)
				.collect(Collectors.toList());
		runSequentially(tasks);
		useParallelStream(tasks);
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
}
