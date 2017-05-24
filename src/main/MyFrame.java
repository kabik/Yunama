package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import game.Game;

public class MyFrame extends JFrame implements KeyListener {
	private Thread mainThread, paintThread;
	private MyCanvas canvas;
	private Game game;
	private int fps = 40;

	public MyFrame() {
		canvas = new MyCanvas(this);
		game = new Game(canvas);

		mainThread = new UpdateThread(this);
		paintThread = new PaintThread(this);

		mainThread.start();
		paintThread.start();

		setAlwaysOnTop(false);
		setLocation(0, 0);
        getContentPane().setPreferredSize(new Dimension(1100, 650));
		getContentPane().add(canvas, BorderLayout.CENTER);
        pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		}

		game.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	public MyCanvas getCanvas() {
		return this.canvas;
	}

	public Game getGame() {
		return game;
	}

	public int getFps() {
		return fps;
	}
}
