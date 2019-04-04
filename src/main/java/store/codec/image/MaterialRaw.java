package store.codec.image;

@SuppressWarnings("unused")
public class MaterialRaw {
	
	public int id;
	public boolean hdr;
	public boolean aBoolean518;
	public boolean transparent;
	public int shadow_factor;
	public int brightness;
	public int effectId;
	public int effectParam1;
	public int color;
	public int speedHorizontal;
	public int speedVertical;
	public boolean aBoolean527;
	public boolean aBoolean544;
	public int size;
	public int aByte537;
	public boolean aBoolean540;
	public boolean repeatTexture;
	public boolean aBoolean543;
	public int anInt519;
	public int effectParam2;
	public int blendType;
	public boolean textured;
	public boolean cubiod;
	public byte blending;
	public byte blendingMode;
	
	public MaterialRaw(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "" + this.id;
	}
	
}
