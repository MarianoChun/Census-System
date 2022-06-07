package interfaz;

import java.util.concurrent.TimeUnit;

public class ThreadTime extends Thread {
	private long tiempoActualNano;

	@Override
	public void run() {
		while (true) {
			tiempoActualNano = System.nanoTime();
		}
	}

	public long getTiempoActualMs() {
		return TimeUnit.NANOSECONDS.toMillis(tiempoActualNano);
	}
}
