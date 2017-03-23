/*

	Class: FallingKeyRecord
	Description: Record storing information about value and (horizontal)
	             screen position of a key currently falling on the screen.
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

class FallingKeyRecord {
    char key;
    int x, key_width;

    public FallingKeyRecord(char key, int key_width, int width) {
        this.key = key;
        this.x = (int) (Math.random()*(width-key_width));
        this.key_width = key_width;
    }

    public void reposition(int width) {
        x = Math.min(x, width-key_width);
    }
}