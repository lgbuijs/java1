/*

	Interface: KeyRecordViewer
	Description: Interface used by the viewer class of a key.
				 It makes easier to change the graphical representation the
				 keyboard (e.g. an array of buttons vs. a single custom
				 component).
	Author: Roberto Virga

	All rights reserved. Permission to copy/use only upon previous
	notification to the author(s).

*/

package louw.typingtutor;

interface KeyRecordViewer {
    public void draw(String key, boolean selected);
}