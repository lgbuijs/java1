/*

	Class: LessonPlanFrame
	Description:
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.awt.*;

class LessonPlanFrame extends Frame {
	// key classes
	private static final String home_keys[] =
		{"a", "s", "d", "f", "j", "k", "l", ";"};
	private static final String letters[] =
		{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
		 "a", "s", "d", "f", "g", "h", "j", "k", "l",
		 "z", "x", "c", "v", "b", "n", "m"};
	private static final String punctuation[] =
		{";", ":", "'", "`", "\"", ",", ".", "?", "!"};
	private static final String digits[] =
		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	private static final String symbols[] =
		{"@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+",
		 "[", "{", "]", "}", "\\", "|", "<", ">", "/", "~"};

	LessonPlan lessonPlan;				// current lesson plan
	int lesson_type;					// current lesson type (1 ... 14)

	LessonPlanView lessonPlanView;
	CheckboxGroup checkboxGroup;
	Checkbox customizedCheckbox;

	public LessonPlanFrame() {

		super("Lesson Plan");

		lessonPlan = new LessonPlan();
		setLessonType(1);

		// Set up the dialog content

		setLayout(new BorderLayout());
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setBackground(Color.lightGray);
		setResizable(false);

		Panel panel, subpanel;
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

		label = new Label("Included Keys", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);
		label.setFont(new Font("Dialog", Font.BOLD, 14));

		constraints.insets = new Insets(0, 0, 10, 0);

		lessonPlanView = new LessonPlanView(lessonPlan);
		layout.setConstraints(lessonPlanView, constraints);
		panel.add(lessonPlanView);

		constraints.insets = new Insets(0, 0, 0, 0);

		label = new Label("Lessons", Label.CENTER);
		layout.setConstraints(label, constraints);
		panel.add(label);
		label.setFont(new Font("Dialog", Font.BOLD, 14));

		constraints.weighty = 1.0;
		constraints.insets = new Insets(5, 5, 5, 5);

		subpanel = new Panel();
		layout.setConstraints(subpanel, constraints);
		panel.add(subpanel);

		layout = new GridBagLayout();
		subpanel.setLayout(layout);
		constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		Checkbox checkbox;

		checkboxGroup = new CheckboxGroup();

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 1: home keys", checkboxGroup, true);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 8: digits and letters", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 2: lower case letters", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 9: digits, letters and punctuation", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 3: upper case and lower case letters", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 10: symbols", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 4: punctuation", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 11: symbols and letters", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 5: lower case letters and punctuation", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 12: symbols, digits and letters", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 6: letters and punctuation", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		checkbox = new Checkbox("Lesson 13: everything", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = 1;

		checkbox = new Checkbox("Lesson 7: digits", checkboxGroup, false);
		layout.setConstraints(checkbox, constraints);
		subpanel.add(checkbox);

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		customizedCheckbox = new Checkbox("Lesson 14: customized lesson", checkboxGroup, false);
		layout.setConstraints(customizedCheckbox, constraints);
		subpanel.add(customizedCheckbox);

		pack();
	}

	private void setLessonType(int type) {

		// Change the lesson plan according to one of the 14 preselected types
		switch(type) {
			case 1:
				lessonPlan.selectKeys(letters, false);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectKeys(home_keys, true);
				lessonPlan.selectCaps(false);
				break;
			case 2:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(false);
				break;
			case 3:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(true);
				break;
			case 4:
				lessonPlan.selectKeys(letters, false);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(punctuation, true);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(false);
				break;
			case 5:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(punctuation, true);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(false);
				break;
			case 6:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(punctuation, true);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(true);
				break;
			case 7:
				lessonPlan.selectKeys(letters, false);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(digits, true);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(false);
				break;
			case 8:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(digits, true);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(true);
				break;
			case 9:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(digits, true);
				lessonPlan.selectKeys(punctuation, true);
				lessonPlan.selectKeys(symbols, false);
				lessonPlan.selectCaps(true);
				break;
			case 10:
				lessonPlan.selectKeys(letters, false);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(symbols, true);
				lessonPlan.selectCaps(false);
				break;
			case 11:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(digits, false);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(symbols, true);
				lessonPlan.selectCaps(true);
				break;
			case 12:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(digits, true);
				lessonPlan.selectKeys(punctuation, false);
				lessonPlan.selectKeys(symbols, true);
				lessonPlan.selectCaps(true);
				break;
			case 13:
				lessonPlan.selectKeys(letters, true);
				lessonPlan.selectKeys(digits, true);
				lessonPlan.selectKeys(punctuation, true);
				lessonPlan.selectKeys(symbols, true);
				lessonPlan.selectCaps(true);
				break;
			default:
				break;
		}

		lesson_type = type;
	}

	public LessonPlan getLessonPlan() {
		return lessonPlan;
	}

	public boolean action(Event event, Object arg) {
		if(event.target instanceof Checkbox) {

			// Determine the lesson number by looking at the label of the
			// checkbox

			String label = ((Checkbox) event.target).getLabel();
			int type;

			try {
				type = Integer.parseInt(
				           label.substring(
						       label.indexOf(" ")+1,
						       label.indexOf(":")));
			} catch (NumberFormatException e) {
				type = 14;
			}

			this.setLessonType(type);
		} else if(event.target == lessonPlanView) {

			// If we change manually the lesson plan, change the type to
			// "custom"

			lesson_type = 14;
			checkboxGroup.setCurrent(customizedCheckbox);
		}

		return super.action(event, arg);
	}

	public boolean handleEvent(Event event) {
		if (event.id == Event.WINDOW_DESTROY) {
			hide();
			return true;
		} else
			return super.handleEvent(event);
	}
}