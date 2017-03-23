/*

	Class: AboutDialog
	Description: Dialog which provides information about the application and
				the author.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;

final class AboutDialog extends Dialog {
	public AboutDialog(Frame parent) {

		super(parent,"About Typing Tutor", true);

		setLayout(new BorderLayout());
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setBackground(Color.lightGray);
		setResizable(false);

		// Set up the dialog content

		Panel panel, subpanel;
		GridBagLayout layout;
		GridBagConstraints constraints;
		Label label;
		MultiLineLabel multiLineLabel;
		Button button;

		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.weightx = 1.0;

		panel = new Panel();
		layout.setConstraints(panel, constraints);
		add(panel);
		panel.setLayout(new BorderLayout(10, 10));

		subpanel = new Panel();
		panel.add("Center", subpanel);

		layout = new GridBagLayout();
		subpanel.setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(0, 0, 10, 0);

		label = new Label("Typing Tutor");
		label.setFont(new Font("Dialog", Font.PLAIN, 24));
		layout.setConstraints(label, constraints);
		subpanel.add(label);

		constraints.insets = new Insets(0, 0, 0, 100);

		multiLineLabel = new MultiLineLabel(
						"Version 0.8\n"
					  + "Copyright \u00a9 1997 Roberto Virga\n"
					  + "Carnegie Mellon University\n"
					  + " \n"
					  + "Inspired by \"Typing Arcade\", a NeXT program\n"
					  + "written by Jim Patterson.\n",
					  MultiLineLabel.LEFT);
		layout.setConstraints(multiLineLabel, constraints);
		subpanel.add(multiLineLabel);

		// Add the dialog buttons

		subpanel = new Panel();
		panel.add("South", subpanel);

		layout = new GridBagLayout();
		subpanel.setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.EAST;
		constraints.weightx = 1.0;
		constraints.ipadx = 50;
		constraints.ipady = 2;

		button = new Button("OK");
		layout.setConstraints(button, constraints);
		subpanel.add(button);

		pack();

	}

	public boolean action(Event event, Object arg) {
		if ((event.target instanceof Button) && (arg == "OK")) {

			// Pressing OK closes the dialog

			hide();
			return true;
		} else
			return false;
	}

	public boolean handleEvent(Event event) {
		if (event.id == Event.WINDOW_DESTROY) {
			hide();
			return true;
		} else
			return super.handleEvent(event);
	}
}
