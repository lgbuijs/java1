/*

	Class: ArchiverRecord
	Description: Record memorizing the hits/misses of a single key.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

class ArchiverRecord {
    public char key;
    public int missed, total;

    public ArchiverRecord(char key, int missed, int total) {
        this.key = key;
        this.missed = missed;
        this.total = total;
    }

	public ArchiverRecord(char key) {
        this(key, 0, 0);
    }

	public float value() {
		return(((float) missed) / total);
	}

    public boolean isBigger(ArchiverRecord other) {

    	// Keys are first compared by value, and then by name.

    	return((this.value() > other.value())
    	    || ((this.value() == other.value()) && (this.key < other.key)));
    }
}
