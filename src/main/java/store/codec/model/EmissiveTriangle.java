package store.codec.model;

public class EmissiveTriangle {

	public int faceX;
	public int faceY;
	public int faceZ;
	public int type;
	public byte priority;
	
	public EmissiveTriangle(int type, short faceX, short faceY, short faceZ, byte priority) {
		this.type = type;
		this.faceX = faceX;
		this.faceY = faceY;
		this.faceZ = faceZ;
		this.priority = priority;
	}

}
