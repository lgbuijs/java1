/*

	Class: LessonPlanView
	Description: Container grouping together all the keys in a keyboard-like
	             layout.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;

class LessonPlanView extends Panel {
    LessonPlan lessonPlan;

    KeyButton shiftButtonLeft, shiftButtonRight;

    public LessonPlanView(LessonPlan lessonPlan) {

        this.lessonPlan = lessonPlan;

		// Set up the dialog content

		Panel panel, subpanel;
		GridBagLayout layout;
		GridBagConstraints constraints;
		KeyButton button;

        setBackground(Color.lightGray);

		panel = new Panel();
		add("Center", panel);

		layout = new GridBagLayout();
		panel.setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(1, 0, 1, 0);

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		subpanel.setLayout(new FlowLayout(2, 2, FlowLayout.LEFT));

		button = new KeyButton("Esc");
		subpanel.add(button);
		button.disable();

		button = new KeyButton("1", "!");
		subpanel.add(button);
		lessonPlan.setKeyViewer("1", button);
		lessonPlan.setKeyViewer("!", button);

		button = new KeyButton("2", "@");
		subpanel.add(button);
		lessonPlan.setKeyViewer("2", button);
		lessonPlan.setKeyViewer("@", button);

		button = new KeyButton("3", "#");
		subpanel.add(button);
		lessonPlan.setKeyViewer("3", button);
		lessonPlan.setKeyViewer("#", button);

		button = new KeyButton("4", "$");
		subpanel.add(button);
		lessonPlan.setKeyViewer("4", button);
		lessonPlan.setKeyViewer("$", button);

		button = new KeyButton("5", "%");
		subpanel.add(button);
		lessonPlan.setKeyViewer("5", button);
		lessonPlan.setKeyViewer("%", button);

		button = new KeyButton("6", "^");
		subpanel.add(button);
		lessonPlan.setKeyViewer("6", button);
		lessonPlan.setKeyViewer("^", button);

		button = new KeyButton("7", "&");
		subpanel.add(button);
		lessonPlan.setKeyViewer("7", button);
		lessonPlan.setKeyViewer("&", button);

		button = new KeyButton("8", "*");
		subpanel.add(button);
		lessonPlan.setKeyViewer("8", button);
		lessonPlan.setKeyViewer("*", button);

		button = new KeyButton("9", "(");
		subpanel.add(button);
		lessonPlan.setKeyViewer("9", button);
		lessonPlan.setKeyViewer("(", button);

		button = new KeyButton("0", ")");
		subpanel.add(button);
		lessonPlan.setKeyViewer("0", button);
		lessonPlan.setKeyViewer(")", button);

		button = new KeyButton("-", "_");
		subpanel.add(button);
		lessonPlan.setKeyViewer("-", button);
		lessonPlan.setKeyViewer("_", button);

		button = new KeyButton("=", "+");
		subpanel.add(button);
		lessonPlan.setKeyViewer("=", button);
		lessonPlan.setKeyViewer("+", button);

		button = new KeyButton("Back", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		subpanel.setLayout(new FlowLayout(2, 2, FlowLayout.LEFT));

		button = new KeyButton("Tab", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("Q");
		subpanel.add(button);
		lessonPlan.setKeyViewer("q", button);

		button = new KeyButton("W");
		subpanel.add(button);
		lessonPlan.setKeyViewer("w", button);

		button = new KeyButton("E");
		subpanel.add(button);
		lessonPlan.setKeyViewer("e", button);

		button = new KeyButton("R");
		subpanel.add(button);
		lessonPlan.setKeyViewer("r", button);

		button = new KeyButton("T");
		subpanel.add(button);
		lessonPlan.setKeyViewer("t", button);

		button = new KeyButton("Y");
		subpanel.add(button);
		lessonPlan.setKeyViewer("y", button);

		button = new KeyButton("U");
		subpanel.add(button);
		lessonPlan.setKeyViewer("u", button);

		button = new KeyButton("I");
		subpanel.add(button);
		lessonPlan.setKeyViewer("i", button);

		button = new KeyButton("O");
		subpanel.add(button);
		lessonPlan.setKeyViewer("o", button);

		button = new KeyButton("P");
		subpanel.add(button);
		lessonPlan.setKeyViewer("p", button);

		button = new KeyButton("[", "{");
		subpanel.add(button);
		lessonPlan.setKeyViewer("[", button);
		lessonPlan.setKeyViewer("{", button);

		button = new KeyButton("]", "}");
		subpanel.add(button);
		lessonPlan.setKeyViewer("]", button);
		lessonPlan.setKeyViewer("}", button);

		button = new KeyButton("\\", "|");
		subpanel.add(button);
		lessonPlan.setKeyViewer("\\", button);
		lessonPlan.setKeyViewer("|", button);

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		subpanel.setLayout(new FlowLayout(2, 2, FlowLayout.LEFT));

		button = new KeyButton("Caps Lock", (int) (KeyButton.DEFAULT_WIDTH*2 - 1*2));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("A");
		subpanel.add(button);
		lessonPlan.setKeyViewer("a", button);

		button = new KeyButton("S");
		subpanel.add(button);
		lessonPlan.setKeyViewer("s", button);

		button = new KeyButton("D");
		subpanel.add(button);
		lessonPlan.setKeyViewer("d", button);

		button = new KeyButton("F");
		subpanel.add(button);
		lessonPlan.setKeyViewer("f", button);

		button = new KeyButton("G");
		subpanel.add(button);
		lessonPlan.setKeyViewer("g", button);

		button = new KeyButton("H");
		subpanel.add(button);
		lessonPlan.setKeyViewer("h", button);

		button = new KeyButton("J");
		subpanel.add(button);
		lessonPlan.setKeyViewer("j", button);

		button = new KeyButton("K");
		subpanel.add(button);
		lessonPlan.setKeyViewer("k", button);

		button = new KeyButton("L");
		subpanel.add(button);
		lessonPlan.setKeyViewer("l", button);

		button = new KeyButton(";", ":");
		subpanel.add(button);
		lessonPlan.setKeyViewer(";", button);
		lessonPlan.setKeyViewer(":", button);

		button = new KeyButton("'", "\"");
		subpanel.add(button);
		lessonPlan.setKeyViewer("'", button);
		lessonPlan.setKeyViewer("\"", button);

		button = new KeyButton("Enter", (int) (KeyButton.DEFAULT_WIDTH*1.5 + 2*2));
		subpanel.add(button);
		button.disable();

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		subpanel.setLayout(new FlowLayout(2, 2, FlowLayout.LEFT));

		shiftButtonLeft = new KeyButton("Shift", (int) (KeyButton.DEFAULT_WIDTH*2.25 + 1*2));
		subpanel.add(shiftButtonLeft);

		button = new KeyButton("Z");
		subpanel.add(button);
		lessonPlan.setKeyViewer("z", button);

		button = new KeyButton("X");
		subpanel.add(button);
		lessonPlan.setKeyViewer("x", button);

		button = new KeyButton("C");
		subpanel.add(button);
		lessonPlan.setKeyViewer("c", button);

		button = new KeyButton("V");
		subpanel.add(button);
		lessonPlan.setKeyViewer("v", button);

		button = new KeyButton("B");
		subpanel.add(button);
		lessonPlan.setKeyViewer("b", button);

		button = new KeyButton("N");
		subpanel.add(button);
		lessonPlan.setKeyViewer("n", button);

		button = new KeyButton("M");
		subpanel.add(button);
		lessonPlan.setKeyViewer("m", button);

		button = new KeyButton(",", "<");
		subpanel.add(button);
		lessonPlan.setKeyViewer(",", button);
		lessonPlan.setKeyViewer("<", button);

		button = new KeyButton(".", ">");
		subpanel.add(button);
		lessonPlan.setKeyViewer(".", button);
		lessonPlan.setKeyViewer(">", button);

		button = new KeyButton("/", "?");
		subpanel.add(button);
		lessonPlan.setKeyViewer("/", button);
		lessonPlan.setKeyViewer("?", button);

		shiftButtonRight = new KeyButton("Shift", (int) (KeyButton.DEFAULT_WIDTH*2.25 + 1*2));
		subpanel.add(shiftButtonRight);
		lessonPlan.setCapsViewers(shiftButtonLeft, shiftButtonRight);

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		subpanel.setLayout(new FlowLayout(2, 2, FlowLayout.LEFT));

		button = new KeyButton("Ctrl", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("Alt", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("Space Bar", (int) (KeyButton.DEFAULT_WIDTH*7.5 + 8*2));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("`", "~");
		subpanel.add(button);
		lessonPlan.setKeyViewer("`", button);
		lessonPlan.setKeyViewer("~", button);

		button = new KeyButton("Alt", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();

		button = new KeyButton("Ctrl", (int) (KeyButton.DEFAULT_WIDTH*1.5));
		subpanel.add(button);
		button.disable();
	}

	public boolean action(Event event, Object arg) {
		if(event.target instanceof KeyButton) {

			KeyButton button = (KeyButton) event.target;

			if((button == shiftButtonLeft) || (button == shiftButtonRight)) {

				// Synchronize the two buttons

				shiftButtonLeft.setKeySelected(button.isKeySelected());
				shiftButtonRight.setKeySelected(button.isKeySelected());
			} else
				lessonPlan.selectKey(
					button.getKey().toLowerCase(),
					button.isKeySelected());
				if(button.getShiftKey() != null)
					lessonPlan.selectKey(
						button.getShiftKey().toLowerCase(),
						button.isShiftKeySelected());

			Event actionEvt = new Event(this, Event.ACTION_EVENT, this);
			postEvent(actionEvt);

			return true;
		} else
			return super.action(event, arg);
	}
}