/*

	Class: EvaluationFrame
	Description: Window containing all the
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;

class EvaluationFrame extends Frame {
	List keyList, accuracyList, missedList;			// frame components
	TextArea recommendArea;

	public EvaluationFrame() {

		super("Performance Evaluation");

		setLayout(new BorderLayout());
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setBackground(Color.lightGray);
		setResizable(false);

		// Set up the dialog content

		Panel panel;
		GridBagLayout layout;
		GridBagConstraints constraints;
		Label label;

		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		panel = new Panel();
		layout.setConstraints(panel, constraints);
		add(panel);

		layout = new GridBagLayout();
		panel.setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;

		label = new Label("Most Commonly Missed Keys", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);
		label.setFont(new Font("Dialog", Font.BOLD, 14));

		constraints.gridwidth = 1;

		label = new Label("Key", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);

		label = new Label("Accuracy", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		label = new Label("Missed", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);

		constraints.gridwidth = 1;
		constraints.weighty = 1.0;

		keyList = new List(7, false);
		layout.setConstraints(keyList, constraints);
		panel.add(keyList);
		keyList.disable();

		accuracyList = new List(7, false);
		layout.setConstraints(accuracyList, constraints);
		panel.add(accuracyList);
		accuracyList.disable();

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		missedList = new List(7, false);
		layout.setConstraints(missedList, constraints);
		panel.add(missedList);
		missedList.disable();

		constraints.weighty = 0.0;

		label = new Label("Recommendation", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);
		label.setFont(new Font("Dialog", Font.BOLD, 14));

		recommendArea = new TextArea(3, 40);
		layout.setConstraints(recommendArea, constraints);
		panel.add(recommendArea);
		recommendArea.setEditable(false);
		recommendArea.setText(
			"You have not played yet.\n"
			+ "You should first play a game before your performance\n"
			+ "can be evaluated and a recommendation can be made.");

		pack();
	}

	public void evaluate(Archiver archiver) {

		// If archiver == null, just reset to blank, otherwise use the
		// information provided by the archiver to make a recommendation

		int missedCount = 0;

		if(archiver != null) {

			ArchiverRecord record[] = archiver.getSortedRecords();
			int i;

			keyList.delItems(0, keyList.countItems()-1);
			accuracyList.delItems(0, accuracyList.countItems()-1);
			missedList.delItems(0, missedList.countItems()-1);

			// Display at the first 7 (at most) most frequently missed keys

			for(i = 0;
				(i < Math.min(record.length, 7)) && (record[i].value() > 0);
				i++) {

				String item;

				item = (new Character(record[i].key)).toString();
				keyList.addItem(item);

				item = (((int) ((1-record[i].value())*1000)) / 10) + "%";
				accuracyList.addItem(item);

				item = record[i].missed + " of " + record[i].total;
				missedList.addItem(item);

				missedCount += record[i].missed;
			}

			// Write a recommendation

			if(archiver.isComplete())
				if(missedCount < 10)
					recommendArea.setText(
						"You did very well!\n"
						+ "You should move to the next lesson,\n"
						+ "or play this lesson again, but faster.");
				else
					recommendArea.setText(
						"You were fast but not accurate.\n"
						+ "Customize a lesson to practice your weak keys.");
			else
				recommendArea.setText(
					"You did not finish.\n"
				     + "Please try again at a lower speed.");
		} else {

			// Reset the information

			keyList.delItems(0, keyList.countItems()-1);
			accuracyList.delItems(0, accuracyList.countItems()-1);
			missedList.delItems(0, missedList.countItems()-1);
			recommendArea.setText("");
		}
	}

	public boolean handleEvent(Event event) {
		if(event.id == Event.WINDOW_DESTROY) {
			hide();
			return true;
		} else
			return super.handleEvent(event);
	}
}