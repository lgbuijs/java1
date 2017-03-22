/*

	Class: Archiver
	Description: This object archives all the hits/misses during a game and
	             return a sorted list of the most freqently missed keys.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

import java.util.*;

class Archiver {
	private Hashtable hashtable;
    private boolean complete;

    public Archiver() {
    	hashtable = new Hashtable();
        complete = false;
    }

    public void startArchiving() {
        hashtable.clear();
        complete = false;
    }

    public void stopArchiving() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

	public void archiveKey(char key, boolean missed) {

    	ArchiverRecord record =
    		(ArchiverRecord) hashtable.get(new Character(key));

    	if(record == null) {
    		record = new ArchiverRecord(key);
    		hashtable.put(new Character(key), record);
    	}

    	if(missed)
    		record.missed++;
    	record.total++;
	}

    public ArchiverRecord[] getSortedRecords() {

		// Collect all the items into an array

    	int size = hashtable.size();
    	ArchiverRecord result[] = new ArchiverRecord[size];
    	Enumeration enumeration = hashtable.elements();
		int i;

		i = 0;
		while(enumeration.hasMoreElements())
			try {
				result[i++] = (ArchiverRecord) enumeration.nextElement();
			} catch(NoSuchElementException e) {
				break;
			}

		// Sort the array by straight selection

		int max, start;

		for(start = 0; start < size; start++) {

			// Find the largest element in the range start...(size-1)

			max = i = start;
			while(++i < size)
				if(result[i].isBigger(result[max]))
					max = i;

			if(max != start) {

				// Switches record[start] and record[max]

				ArchiverRecord temp = result[start];
				result[start] = result[max];
				result[max] = temp;
			}
		}

    	return result;
    }
}