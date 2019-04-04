package store.codec.model;

public class MagnetConfig {

	public int skin, point;
	
	public MagnetConfig(int skin, int point) {
		this.skin = skin;
		this.point = point;
	}
	
	static final int getTargetMask(int i) {
		return i >> 11 & 0x7f;
	}
	
	MagnetConfig translatePoint(int point) {
		return new MagnetConfig(skin, point);
	}

}
