/*

	Class: FallingKeysView
	Description: Custom Control displaying keys falling on the screen.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;
import java.util.*;

class FallingKeysView extends Canvas implements Runnable {
	private static final int DEFAULT_WIDTH = 200;	// default parameters
	private static final int DEFAULT_HEIGHT = 300;
	private static final int DEFAULT_DELAY = 80;
	private static final Color DEFAULT_GAME_COLOR = Color.red;
	private static final Font DEFAULT_GAME_FONT = new Font("TimesRoman", Font.BOLD, 12);

	private int width, height, delay;				// actual parameters
	private Color gameColor;
	private Font gameFont;

	char keys[];
	boolean caps;

	private static final int PHASES = 5;			// states of the control

	private static final int START = 0;
	private static final int FADE_ON = 1;
	private static final int MIDDLE = 2;
	private static final int FADE_OFF = 3;
	private static final int END = 4;

	private static final int INITIAL_DELAY = 10000;
	// delay for the "Get ready" screen

	private static final int START_LENGTH = 50;		// Lengths (in frames) of
	private static final int MIDDLE_LENGTH = 1500;	// the non-fading phases
	private static final int END_LENGTH = 50;

	int frame, last_frame;
	// current frame and last frame painted

	private int end_phase[] = new int[PHASES];
	// phase i starts at frame end_phase[i-1] and ends at frame end_phase[i]-1

	private Thread animatorThread;				// thread performing the animation
	private boolean paused;						// has the game been paused?

	private Vector falling_keys = new Vector();
	// keys currently falling on the screen

	private int deleted_keys;
	// number of elements among those falling which have been erased since the
	// last repaint

	Archiver archiver;
	// archiver object to notify at every keypress

	Image offImage;
	// offscreen buffer for faster graphic performance

		public FallingKeysView(
			int width, int height, int delay,
			Color gameColor,
			Font gameFont) {

		this.width = width;
		this.height = height;
		this.delay = delay;
		this.gameColor = gameColor;
		this.gameFont = gameFont;

		keys = null;
		caps = false;

		frame = last_frame = -1;
		computePhases();

		animatorThread = null;
		paused = false;

		deleted_keys = 0;
	}

	public FallingKeysView(int width, int height, int delay) {
		this(width, height, delay, DEFAULT_GAME_COLOR, DEFAULT_GAME_FONT);
	}

	public FallingKeysView(int width, int height) {
		this(width, height, DEFAULT_DELAY);
	}

	public FallingKeysView() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public char[] getKeysSelected() {
		return keys;
	}

	public void setKeysSelected(char keys[]) {
		this.keys = keys;
	}

	public Archiver getArchiver() {
		return archiver;
	}

	public void setArchiver(Archiver archiver) {
		this.archiver = archiver;
	}

	public boolean isCapsSelected() {
		return caps;
	}

	public void setCapsSelected(boolean selected) {
		caps = selected;
	}

	public boolean isActive() {
		return(animatorThread != null);
	}

	public boolean isPaused() {
		return paused;
	}

	private void computePhases() {

		// Compute the minimum number of frames to fade from black to the
		// specified color

		int fade_length = 0;

		fade_length = Math.max(fade_length, gameColor.getRed());
		fade_length = Math.max(fade_length, gameColor.getGreen());
		fade_length = Math.max(fade_length, gameColor.getBlue());

		// Set up the lengths of all the phases

		end_phase[START] = START_LENGTH;
		end_phase[FADE_ON] = end_phase[START] + fade_length;
		end_phase[MIDDLE] = end_phase[FADE_ON] + MIDDLE_LENGTH;
		end_phase[FADE_OFF] = end_phase[MIDDLE] + fade_length;
		end_phase[END] = end_phase[FADE_OFF] + END_LENGTH;
	}

	public void start() {

		// If already running, then first stop

		stop();

		// Erase everything previously painted on screen

		frame = last_frame = -1;
		paused = false;

		falling_keys.removeAllElements();
		deleted_keys = 0;

		offImage = null;

		// Create and start a new thread

		paused = false;
		animatorThread = new Thread(this);
		animatorThread.start();

		// Start the archiver

		if(archiver != null)
			archiver.startArchiving();
	}

	public void stop() {
		if(animatorThread == null)
			return;

		animatorThread.stop();
		animatorThread = null;
		paused = false;
		repaint();
	}

	public void suspend() {
		if((animatorThread == null) || paused)
			return;

		animatorThread.suspend();
		paused = true;
		repaint();
	}

	public void resume() {
		if((animatorThread == null) || !paused)
			return;

		animatorThread.resume();
		paused = false;
		repaint();
	}

	public void run() {

		long time;

		while (Thread.currentThread() == animatorThread) {
			time = System.currentTimeMillis();

			frame++;
			repaint();

			try {
				time += (frame == 0) ? INITIAL_DELAY : delay;
				Thread.sleep(
					Math.max(0, time-System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public boolean mouseDown(Event e, int x, int y) {

		// If stopped, do nothing

		if(animatorThread == null)
			return true;

		// Clicking on the components pauses/restarts the action

		if(paused)
			resume();
		else
			suspend();

		repaint();

		// Post an action event to notify the container

		Event actionEvent = new Event(this, Event.ACTION_EVENT, this);
		postEvent(actionEvent);

		return true;
	}

	public boolean keyDown(Event e, int key) {

		// If stopped, do nothing

		if(animatorThread == null)
			return true;

		// If it's not a ASCII character or is not printable, do nothing

		if((key <= 32) || (key >= 127))
			return true;

		// If there are no keys on the screen, likewise

		if(falling_keys.size()-deleted_keys == 0)
			return true;

		// Take the first non-deleted key and compare with the key just pressed

		FallingKeyRecord falling_key =
			(FallingKeyRecord) falling_keys.elementAt(deleted_keys);
		boolean missed = (falling_key.key != key);

		if(!missed)
			deleted_keys++;

		// Notify the archiver

		if(archiver != null)
			archiver.archiveKey(falling_key.key, missed);

		return true;
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {

		Dimension size = size();
		int curr_frame = frame;
		FontMetrics metrics = getFontMetrics(gameFont);

		// First handle the special cases

		if((curr_frame == 0)
		|| (curr_frame >= end_phase[END] + size.height)) {

			// Draw a message in white over a black screen

			g.setColor(Color.black);
			g.fillRect(0, 0, size.width, size.height);

			if((curr_frame == 0) && (animatorThread == null))
				return;

			String message = (curr_frame == 0) ? "Get Ready" : "Game Over";
			int x = (size.width - metrics.stringWidth(message)) / 2;
			int y = (size.height - metrics.getHeight()) / 2 - metrics.getDescent();

			g.setColor(Color.white);
			g.setFont(gameFont);
			g.drawString(message, x, y);

			if((curr_frame != 0) && (animatorThread != null)) {

				// We won the game, stop the animation

				if(archiver != null)
					archiver.stopArchiving();
				stop();

				Event actionEvent =
					new Event(this, Event.ACTION_EVENT, this);
				postEvent(actionEvent);
			}

			return;
		} else if(paused) {

			// Make the screen invisible

			g.setColor(getBackground());
			g.fillRect(0, 0, size.width, size.height);
			return;
		}

		// Updates the offscreen image

		Graphics offGraphics;
		int clipHeight;
		int fontHeight = metrics.getHeight();
		int n;

		if((offImage == null)
		|| (offImage.getWidth(this) != size.width)
		|| (offImage.getHeight(this) != size.height)) {

			// The clipping area is the entire image

			clipHeight = size.height;

			// Create a new offscreen image

			offImage = createImage(size.width, size.height);
			offGraphics = offImage.getGraphics();

			// Reposition all the letters (if any) that went off-screen

			for(n = 0; n < falling_keys.size(); n++)
				((FallingKeyRecord)
					falling_keys.elementAt(n)).reposition(size.width);
		} else {

			// The clipping area is just what changed from the last time

			clipHeight = curr_frame-last_frame;

			// Shift the old image downwards

			offGraphics = offImage.getGraphics();
			offGraphics.drawImage(offImage, 0, clipHeight, this);
		}

		// Generate as many new keys as needed

		if((end_phase[FADE_ON] < curr_frame)
		&& (curr_frame <= end_phase[MIDDLE])) {

			n = (int) (curr_frame-last_frame) / fontHeight;
			if((curr_frame % fontHeight) < (last_frame % fontHeight))
				n++;

			while(n-- > 0)
				if((keys != null) && (keys.length > 0)) {

					char c = keys[(int)(Math.random()*keys.length)];
					if(Character.isLetter(c) && caps && (Math.random() >= .5))
						c = Character.toUpperCase(c);

					int c_width = metrics.charWidth(c);

					falling_keys.addElement(
						new FallingKeyRecord(c, c_width, size.width));
				}

		}

		// By defult, the cliping area is black

		offGraphics.setColor(Color.black);
		offGraphics.fillRect(0, 0, size.width, clipHeight);

		// Draw the offscreen image

		int i;

		for(i = 1; i < PHASES; i++)
			if((end_phase[i-1] <= curr_frame)
			&& (curr_frame < end_phase[i] + size.height)) {

				int ystart, height;

				ystart = Math.max(0, curr_frame-end_phase[i]);
				height = Math.min(curr_frame, end_phase[i])-end_phase[i-1];
				height = Math.min(height, clipHeight-ystart);

				switch(i) {
					case FADE_ON:
					{
							int inc =
								Math.min(curr_frame, end_phase[i])-end_phase[i-1];

							while(height > 0) {
								offGraphics.setColor(
									incrementColor(inc));
								offGraphics.drawLine(
									0, ystart, size.width-1, ystart);

								ystart++;
								height--;
								inc--;
							}
					}
						break;
					case MIDDLE:
						offGraphics.setColor(gameColor);
						offGraphics.fillRect(0, ystart, size.width, height);

						offGraphics.setFont(gameFont);
						if(ystart == 0)
							ystart = curr_frame % fontHeight;
						else
							ystart += end_phase[i] % fontHeight;
						n = falling_keys.size();
						while(n-- > 0) {

							FallingKeyRecord falling_key =
								(FallingKeyRecord) falling_keys.elementAt(n);

							offGraphics.setColor(
								(n >= deleted_keys) ? Color.black : gameColor);
							offGraphics.drawString(
								"" + falling_key.key + "",
								falling_key.x, ystart);

							if((ystart >= size.height)
							&& (n >= deleted_keys)
							&& (animatorThread != null)) {

								// We lose the game

								stop();

								Event actionEvent =
									new Event(this, Event.ACTION_EVENT, this);
								postEvent(actionEvent);
							}
							ystart += fontHeight;
						}
						break;
					case FADE_OFF:
						{
							int inc =
								end_phase[i]-Math.min(curr_frame, end_phase[i]);

							while(height > 0) {
								offGraphics.setColor(
									incrementColor(inc));
								offGraphics.drawLine(
									0, ystart, size.width-1, ystart);

								ystart++;
								height--;
								inc++;
							}
						}
						break;
					default:	// i = END
						offGraphics.setColor(Color.black);
						offGraphics.fillRect(0, ystart, size.width, height);
						break;
				}
			}

		// Remove the erased letters

		while(deleted_keys > 0) {
			falling_keys.removeElementAt(0);
			deleted_keys--;
		}

		last_frame = curr_frame;

		g.drawImage(offImage, 0, 0, this);
	}

	private Color incrementColor(int inc) {
		return(
			new Color(
				Math.min(inc, gameColor.getRed()),
				Math.min(inc, gameColor.getGreen()),
				Math.min(inc, gameColor.getBlue())));
	}

	public Dimension minimumSize () {
		return(new Dimension(width, height));
	}

	public Dimension preferredSize () {
		return(new Dimension(width, height));
	}
}
