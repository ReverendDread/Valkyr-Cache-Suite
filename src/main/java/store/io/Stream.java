package store.io;

public abstract class Stream {
	
   public int index;
   protected int length;
   protected byte[] buffer;
   protected int bitPosition;

   public int getLength() {
      return this.length;
   }

   public byte[] getBuffer() {
      return this.buffer;
   }

   public int getIndex() {
      return this.index;
   }

   public void decodeXTEA(int[] keys) {
      this.decodeXTEA(keys, 5, this.length);
   }

   public void decodeXTEA(int[] keys, int start, int end) {
      int l = this.index;
      this.index = start;
      int i1 = (end - start) / 8;

      for(int j1 = 0; j1 < i1; ++j1) {
         int k1 = this.readInt();
         int l1 = this.readInt();
         int sum = -957401312;
         int delta = -1640531527;

         for(int k2 = 32; k2-- > 0; k1 -= (l1 >>> 5 ^ l1 << 4) + l1 ^ keys[sum & 3] + sum) {
            l1 -= keys[(sum & 7300) >>> 11] + sum ^ (k1 >>> 5 ^ k1 << 4) + k1;
            sum -= delta;
         }

         this.index -= 8;
         this.writeInt(k1);
         this.writeInt(l1);
      }

      this.index = l;
   }

   public final void encodeXTEA(int[] keys, int start, int end) {
      int o = this.index;
      int j = (end - start) / 8;
      this.index = start;

      for(int k = 0; k < j; ++k) {
         int l = this.readInt();
         int i1 = this.readInt();
         int sum = 0;
         int delta = -1640531527;

         for(int l1 = 32; l1-- > 0; i1 += l + (l >>> 5 ^ l << 4) ^ keys[(7916 & sum) >>> 11] + sum) {
            l += sum + keys[3 & sum] ^ i1 + (i1 >>> 5 ^ i1 << 4);
            sum += delta;
         }

         this.index -= 8;
         this.writeInt(l);
         this.writeInt(i1);
      }

      this.index = o;
   }

   private final int readInt() {
      this.index += 4;
      return ((255 & this.buffer[-3 + this.index]) << 16) + ((255 & this.buffer[-4 + this.index]) << 24) + ((this.buffer[-2 + this.index] & 255) << 8) + (this.buffer[-1 + this.index] & 255);
   }

   public void writeInt(int value) {
      this.buffer[this.index++] = (byte)(value >> 24);
      this.buffer[this.index++] = (byte)(value >> 16);
      this.buffer[this.index++] = (byte)(value >> 8);
      this.buffer[this.index++] = (byte)value;
   }

	public final void flipBuffer(byte[] data, int off, int len) {
		for (int k = off; k < len + off; ++k) {
			data[k] = this.buffer[this.index++];
		}
	}
}
