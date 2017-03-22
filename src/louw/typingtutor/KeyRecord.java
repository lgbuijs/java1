/*

	Class: KeyRecord
	Description: Record storing information about a key of the lesson plan.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

class KeyRecord {
    public String key;				// value of the key
    public boolean selected;		// is it currently selected?
    KeyRecordViewer viewer;			// viewer of this key

    public KeyRecord(String key, boolean selected) {
        this.key = key;
        this.selected = selected;
    }

    public KeyRecord(String key) {
        this(key, false);
    }
}
