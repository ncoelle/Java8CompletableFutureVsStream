package model;

public class MyTask {
	private final int duration;

	public MyTask(int duration) {
		this.duration = duration * 10;
		System.out.println("duration = " + this.duration);
	}

	public int calculate() {
		System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return duration;
	}
}
