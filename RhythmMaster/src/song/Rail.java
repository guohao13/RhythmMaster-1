package song;

import java.util.BitSet;

public class Rail {

	protected BitSet bits;
	
	Rail() {
		this.bits = new BitSet(8000);	// each bit is 1/16 note
										// calculated at 120 bpm, 4 min song
	}
	
	public boolean getBitAt(int index) {
		return bits.get(index);
	}
	

}
