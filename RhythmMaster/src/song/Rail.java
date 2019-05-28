package song;

import java.util.BitSet;

public class Rail {

	protected BitSet bits;
	
	Rail() {
		this.bits = new BitSet(8000);	// each bit is 1/16 beat
	}
	
	Rail(int size) {
		this.bits = new BitSet(size);
	}
	
	Rail(boolean[] data) {
		this.bits = new BitSet(data.length);
		for(int x = 0; x < data.length; x++) {
			this.bits.set(x, data[x]);
		}
	}
	
	public void setBitAt(int index, boolean val) {
		if(index < this.bits.size()) {
			this.bits.set(index, val);
		}
	}
	
	public boolean getBitAt(int index) {
		return bits.get(index);
	}
	

}
