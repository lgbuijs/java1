/*

	Class: LessonPlan
	Description: Object storing a lesson plan.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.util.*;

class LessonPlan {
    Hashtable hashtable;
    // the records for all the keys are organized in a hash table for faster
    // retrieval

    boolean caps_selected;
    // is the shift key currently selected?

    KeyRecordViewer caps_viewer1, caps_viewer2;
    // viewers for the two shift keys

    public LessonPlan() {
        hashtable = new Hashtable();
        caps_selected = false;
        caps_viewer1 = caps_viewer2 = null;
    }

    public void selectKey(String key, boolean selected) {

    	KeyRecord record = (KeyRecord) hashtable.get(key);

    	if(record == null) {
    		record = new KeyRecord(key);
    		hashtable.put(key, record);
    	}

		if(record.selected != selected) {
			record.selected = selected;
			if(record.viewer != null)
				record.viewer.draw(key, selected);
		}
    }

    public void selectKeys(String key[], boolean selected) {

    	int n;

    	for(n = 0; n < key.length; n++)
    		this.selectKey(key[n], selected);
    }

    public void selectCaps(boolean selected) {
    	if(caps_selected != selected) {
			caps_selected = selected;
			if(caps_viewer1 != null) {
				caps_viewer1.draw("", selected);
				caps_viewer2.draw("", selected);
			}
		}
	}

    public boolean isKeySelected(String key) {

    	KeyRecord record = (KeyRecord) hashtable.get(key);

    	if(record == null)
    		return false;
    	else
    		return record.selected;
    }

    public boolean isCapsSelected() {
    	return caps_selected;
    }

    public void setKeyViewer(String key, KeyRecordViewer viewer) {

    	KeyRecord record = (KeyRecord) hashtable.get(key);

    	if(record == null) {
    		record = new KeyRecord(key);
    		hashtable.put(key, record);
    	}

    	record.viewer = viewer;
    	viewer.draw(key, record.selected);
    }

    public void setCapsViewers(KeyRecordViewer viewer1, KeyRecordViewer viewer2) {
    	caps_viewer1 = viewer1;
    	viewer1.draw("", caps_selected);
    	caps_viewer2 = viewer2;
    	viewer2.draw("", caps_selected);
    }

    public char[] getKeysSelected() {

    	StringBuffer buffer = new StringBuffer();
    	Enumeration elements = hashtable.elements();
    	KeyRecord record;

    	while(elements.hasMoreElements()) {
    		try {
    			record = (KeyRecord) elements.nextElement();
    			if(record.selected)
    				buffer.append(record.key);
    		} catch (NoSuchElementException e) {}
    	}

    	char result[] = new char[buffer.length()];
    	buffer.getChars(0, buffer.length(), result, 0);
    	return result;
    }
}
