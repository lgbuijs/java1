/*

	Class: KeyButton
	Description: On/off button to select the keys of the lesson plan.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;

class KeyButton extends Canvas implements KeyRecordViewer {
	public static final int DEFAULT_WIDTH = 36;		// default size
	private static final int DEFAULT_HEIGHT = 36;

	private static final int BORDER_SIZE = 2;

	private String key, shiftKey;
	// if the button has no shift-key, then shiftKey == null

    private int width;
    // preferred width of the button

	private boolean keySelected, shiftKeySelected;
	// are the key or the shift-key currently selected?

	public KeyButton(String key, String shiftKey, int width) {
		this.key = key;
		this.shiftKey = shiftKey;
		this.width = width;
		keySelected = shiftKeySelected = false;
	}

	public KeyButton(String key, String shiftKey) {
		this(key, shiftKey, DEFAULT_WIDTH);
	}

	public KeyButton(String key, int width) {
		this(key, null, width);
	}

	public KeyButton(String key) {
		this(key, null, DEFAULT_WIDTH);
	}

	public String getKey() {
		return key;
	}

	public String getShiftKey() {
		return shiftKey;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setShiftKey(String shiftKey) {
		this.shiftKey = shiftKey;
	}

	public boolean isKeySelected() {
		return keySelected;
	}

	public boolean isShiftKeySelected() {
		return shiftKeySelected;
	}

	public void setKeySelected(boolean selected) {
		if(keySelected != selected) {
			keySelected = selected;
			if(shiftKey == null)
				shiftKeySelected = selected;
			repaint();
		}
	}

	public void setShiftKeySelected(boolean selected) {
		if(shiftKeySelected != selected) {
			shiftKeySelected = selected;
			repaint();
		}
	}

	public void draw(String key, boolean selected) {
		if((shiftKey != null) && (key.toUpperCase()).equals(this.shiftKey.toUpperCase()))
			this.setShiftKeySelected(selected);
		else
			this.setKeySelected(selected);
	}

	public boolean mouseDown(Event e, int x, int y) {
		if(shiftKey != null) {

			// Find out in which half the mouse has been pressed

			if(y < (this.size().height /2))
				shiftKeySelected = !shiftKeySelected;
			else
				keySelected = !keySelected;
		} else
			shiftKeySelected = keySelected = !keySelected;;

		repaint();

		// Notify the container by sending an action event

		Event actionEvt = new Event(this, Event.ACTION_EVENT, this);
		postEvent(actionEvt);

		return super.mouseDown(e, x, y);
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {

		Dimension size = this.size();

		Color foreground = this.getForeground();
		Color background = this.getBackground();
		int i;

		g.setColor(background);
		for(i = 0; i < BORDER_SIZE; i++)
			g.draw3DRect(i, i, size.width-2*i, size.height-2*i, true);

		g.setColor(shiftKeySelected ? background.brighter() : background);
		g.fillRect(i, i, size.width-2*i, (size.height-2*i)/2 + 1);
		g.setColor(keySelected ? background.brighter() : background);
		g.fillRect(i, (size.height/2)-i + 1, size.width-2*i, (size.height-2*i)/2 + 1);

		g.setColor(this.isEnabled() ? foreground : background.brighter());

		FontMetrics fontMetrics = this.getFontMetrics(this.getFont());
		int x, y;

		if(shiftKey != null) {
			x = (size.width - fontMetrics.stringWidth(shiftKey)) / 2;
			y = ((size.height / 2) - fontMetrics.getHeight()) / 2;
			y += fontMetrics.getAscent() + (BORDER_SIZE / 2);
			g.drawString(shiftKey, x, y);

			x = (size.width - fontMetrics.stringWidth(key)) / 2;
			y += (size.height / 2) - BORDER_SIZE;
			g.drawString(key, x, y);
		} else {
			x = (size.width - fontMetrics.stringWidth(key)) / 2;
			y = (size.height - fontMetrics.getHeight()) / 2;
			y += fontMetrics.getAscent();
			g.drawString(key, x, y);
		}
	}

	public Dimension preferredSize() {
		return(new Dimension(width, DEFAULT_HEIGHT));
	}

	public Dimension minimumSize() {
		return(new Dimension(width, DEFAULT_HEIGHT));
	}
}
