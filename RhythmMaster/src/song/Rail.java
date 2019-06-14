package song;

import java.util.BitSet;

public class Rail {

	protected BitSet bits;
	
	Rail() {
		bits = new BitSet(8000);	// each bit is 1/16 beat
	}
	
	Rail(int size) {
		bits = new BitSet(size);
	}
	
	Rail(boolean[] data) {
		bits = new BitSet(data.length);
		for(int x = 0; x < data.length; x++) {
			bits.set(x, data[x]);
		}
	}
	
	public void setBitAt(int index, boolean val) {
		if(index < bits.size()) {
			bits.set(index, val);
		}
	}
	
	public boolean getBitAt(int index) {
		return bits.get(index);
	}
}
