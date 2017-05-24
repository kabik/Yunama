package main;

public class UpdateThread extends Thread {
	private MyFrame frame;
	private int fps;
	public UpdateThread(MyFrame frame) {
		this.frame = frame;
		this.fps = frame.getFps();
	}

	public void run() {
		try {
			while(true) {
				long start = System.currentTimeMillis();

				frame.getGame().update();
				//frame.getCanvas().repaint();

				long end = System.currentTimeMillis();
				long delay = (end - start <= fps) ? end-start : fps;
				sleep(fps - delay);
				//System.out.println("UpdateTime:" + (end - start));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
