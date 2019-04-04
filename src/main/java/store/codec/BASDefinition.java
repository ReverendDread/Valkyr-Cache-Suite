package store.codec;

import store.ConfigArchive;
import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

public class BASDefinition implements AbstractDefinition, Cloneable {

	public int[][] anIntArrayArray3710;
	public int idle;
	public int[] anIntArray3712 = null;
	public int[] anIntArray3713 = null;
	public int anInt3714 = 0;
	public int anInt3715 = -1;
	public int anInt3716;
	public int walking;
	public int strafe_right;
	public int anInt3720;
	public int running;
	public int anInt3722;
	public int strafe_left;
	public int anInt3724;
	public int anInt3726;
	public int anInt3727;
	public int anInt3728;
	public int anInt3729;
	public int anInt3730;
	public int anInt3731;
	public int[] anIntArray3732;
	public int anInt3733 = -1;
	public int anInt3734;
	public int anInt3735;
	public int anInt3736;
	public int anInt3737;
	public int anInt3738;
	public int anInt3739;
	public int anInt3740;
	public int backwards;
	public int[] anIntArray3742;
	public int anInt3743;
	public int anInt3744;
	public int anInt3745;
	public int anInt3746;
	public int anInt3747;
	public int anInt3748;
	public int anInt3749;
	public int anInt3750;
	public int anInt3751;
	public int anInt3752;
	public int[][] anIntArrayArray3753;
	public int anInt3754;
	public int anInt3755;
	public boolean aBoolean3756;
	public int id;
	
	public BASDefinition(int id) {
		this.id = id;
		idle = -1;
		walking = -1;
		backwards = -1;
		strafe_right = -1;
		strafe_left = -1;
		running = -1;
		anInt3749 = -1;
		anInt3746 = -1;
		anInt3724 = -1;
		anInt3755 = -1;
		anInt3722 = -1;
		anInt3727 = -1;
		anInt3728 = -1;
		anInt3729 = -1;
		anInt3743 = -1;
		anInt3731 = -1;
		anInt3739 = -1;
		anInt3737 = -1;
		anInt3730 = -1;
		anInt3735 = 0;
		anInt3736 = 0;
		anInt3734 = 0;
		anInt3740 = 0;
		anInt3744 = 0;
		anInt3745 = 0;
		anInt3726 = 0;
		anInt3747 = 0;
		anInt3748 = 0;
		anInt3720 = 0;
		anInt3750 = 0;
		anInt3751 = 0;
		anInt3752 = -1;
		anInt3738 = -1;
		anInt3754 = -1;
		anInt3716 = -1;
		aBoolean3756 = true;
	}

	@Override
	public void decode(InputStream buffer) {
		try {
			while (true) {
				int opcode = buffer.readUnsignedByte();
				if (opcode == 0)
					break;
				// added opcode
				if (1 == opcode) {
					idle = buffer.readSmartInt(); //Stand animation
					walking = buffer.readSmartInt(); //Walk animation
				} else if (2 == opcode)
					anInt3755 = buffer.readSmartInt();
				else if (opcode == 3)
					anInt3722 = buffer.readSmartInt();
				else if (4 == opcode)
					anInt3727 = buffer.readSmartInt();
				else if (5 == opcode)
					anInt3728 = buffer.readSmartInt();
				else if (opcode == 6)
					running = buffer.readSmartInt(); //Run animation
				else if (opcode == 7)
					anInt3749 = buffer.readSmartInt();
				else if (opcode == 8)
					anInt3746 = buffer.readSmartInt();
				else if (9 == opcode)
					anInt3724 = buffer.readSmartInt();
				else if (26 == opcode) {
					anInt3735 = (short) (buffer.readUnsignedByte() * 4);
					anInt3736 = (short) (buffer.readUnsignedByte() * 4);
				} else if (opcode == 27) {
					if (anIntArrayArray3710 == null)
						anIntArrayArray3710 = new int[12][];
					int length = buffer.readUnsignedByte();
					anIntArrayArray3710[length] = new int[6];
					for (int index = 0; index < 6; index++)
						anIntArrayArray3710[length][index] = buffer.readShort();
				} else if (opcode == 28) {
					int length = buffer.readUnsignedByte();
					anIntArray3732 = new int[length];
					for (int index = 0; index < length; index++) {
						anIntArray3732[index] = buffer.readUnsignedByte();
						if (anIntArray3732[index] == 255)
							anIntArray3732[index] = -1;
					}
				} else if (opcode == 29)
					anInt3744 = buffer.readUnsignedByte();
				else if (opcode == 30)
					anInt3745 = buffer.readUnsignedShort();
				else if (31 == opcode)
					anInt3726 = buffer.readUnsignedByte();
				else if (32 == opcode)
					anInt3747 = buffer.readUnsignedShort();
				else if (33 == opcode)
					anInt3748 = buffer.readShort();
				else if (34 == opcode)
					anInt3720 = buffer.readUnsignedByte();
				else if (opcode == 35)
					anInt3750 = buffer.readUnsignedShort();
				else if (36 == opcode)
					anInt3751 = buffer.readShort();
				else if (37 == opcode)
					anInt3752 = buffer.readUnsignedByte();
				else if (38 == opcode)
					anInt3715 = buffer.readSmartInt();
				else if (39 == opcode)
					anInt3733 = buffer.readSmartInt();
				else if (opcode == 40)
					backwards = buffer.readSmartInt(); //Walk backwards backwards anim
				else if (opcode == 41)
					strafe_right = buffer.readSmartInt(); //Straif right anim
				else if (42 == opcode)
					strafe_left = buffer.readSmartInt(); //Straif left anim
				else if (opcode == 43)
					anInt3738 = buffer.readSmartInt();
				else if (44 == opcode)
					anInt3754 = buffer.readSmartInt();
				else if (opcode == 45)
					anInt3716 = buffer.readUnsignedShort();
				else if (46 == opcode)
					anInt3729 = buffer.readSmartInt();
				else if (opcode == 47)
					anInt3743 = buffer.readSmartInt();
				else if (48 == opcode)
					anInt3731 = buffer.readSmartInt();
				else if (opcode == 49)
					anInt3739 = buffer.readSmartInt();
				else if (50 == opcode)
					anInt3737 = buffer.readSmartInt();
				else if (opcode == 51)
					anInt3730 = buffer.readSmartInt();
				else if (52 == opcode) {
					int length = buffer.readUnsignedByte();
					anIntArray3712 = new int[length];
					anIntArray3713 = new int[length];
					for (int index = 0; index < length; index++) {
						anIntArray3712[index] = buffer.readSmartInt();
						int value = buffer.readUnsignedByte();
						anIntArray3713[index] = value;
						anInt3714 += value;
					}
				} else if (opcode == 53)
					aBoolean3756 = false;
				else if (opcode == 54) {
					anInt3734 = buffer.readUnsignedByte();
					anInt3740 = buffer.readUnsignedByte();
				} else if (55 == opcode) {
					if (anIntArray3742 == null)
						anIntArray3742 = new int[12];
					int index = buffer.readUnsignedByte();
					anIntArray3742[index] = buffer.readUnsignedShort();
				} else if (opcode == 56) {
					if (null == anIntArrayArray3753)
						anIntArrayArray3753 = new int[12][];
					int value = buffer.readUnsignedByte();
					anIntArrayArray3753[value] = new int[3];
					for (int index = 0; index < 3; index++)
						anIntArrayArray3753[value][index] = buffer.readShort();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();

		stream.writeByte(1);
		stream.writeSmartInt(idle);
		stream.writeSmartInt(walking);	
		
		if (this.anInt3755 != -1) {
			stream.writeByte(2);
			stream.writeSmartInt(anInt3755);
		}
		
		if (this.anInt3722 != -1) {
			stream.writeByte(3);
			stream.writeSmartInt(anInt3722);
		}
		
		if (this.anInt3727 != -1) {
			stream.writeByte(4);
			stream.writeSmartInt(anInt3727);
		}
		
		if (this.anInt3728 != -1) {
			stream.writeByte(5);
			stream.writeSmartInt(anInt3728);
		}

		if (this.running != -1) {
			stream.writeByte(6);
			stream.writeSmartInt(running);
		}
		
		if (this.anInt3749 != -1) {
			stream.writeByte(7);
			stream.writeSmartInt(anInt3749);
		}
		
		if (this.anInt3746 != -1) {
			stream.writeByte(8);
			stream.writeSmartInt(anInt3746);
		}
		
		if (this.anInt3724 != -1) {
			stream.writeByte(9);
			stream.writeSmartInt(anInt3724);
		}
		
		if (this.anInt3735 != 0 && this.anInt3736 != 0) {
			stream.writeByte(10);
			stream.writeShort((int) (anInt3735 / 4));
			stream.writeShort((int) (anInt3736 / 4));
		}
		
		if (this.anIntArrayArray3710 != null) {
			stream.writeByte(27);
			int length = anIntArrayArray3710.length;
			stream.writeByte(length);
			for (int index = 0; index < 6; index++) {
				stream.writeShort(anIntArrayArray3710[length][index]);
			}
		}
		
		if (this.anIntArray3732 != null) {
			stream.writeByte(28);
			int length = anIntArray3732.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeShort(anIntArray3732[index]);
			}
		}
		
		if (this.anInt3744 != 0) {
			stream.writeByte(29);
			stream.writeByte(anInt3744);
		}
		
		if (this.anInt3745 != 0) {
			stream.writeByte(30);
			stream.writeShort(anInt3745);
		}
		
		if (this.anInt3726 != 0) {
			stream.writeByte(31);
			stream.writeByte(anInt3726);
		}
		
		if (this.anInt3747 != 0) {
			stream.writeByte(32);
			stream.writeShort(anInt3747);
		}
		
		if (this.anInt3748 != 0) {
			stream.writeByte(33);
			stream.writeShort(anInt3748);
		}
		
		if (this.anInt3720 != 0) {
			stream.writeByte(34);
			stream.writeByte(anInt3720);
		}
		
		if (this.anInt3750 != 0) {
			stream.writeByte(35);
			stream.writeShort(anInt3750);
		}
		
		if (this.anInt3751 != 0) {
			stream.writeByte(36);
			stream.writeShort(anInt3751);
		}
		
		if (this.anInt3752 != -1) {
			stream.writeByte(37);
			stream.writeByte(anInt3752);
		}
		
		if (this.anInt3715 != -1) {
			stream.writeByte(38);
			stream.writeSmartInt(anInt3715);
		}
		
		if (this.anInt3733 != -1) {
			stream.writeByte(39);
			stream.writeSmartInt(anInt3733);
		}
		
		if (this.backwards != -1) {
			stream.writeByte(40);
			stream.writeSmartInt(backwards);
		}
		
		if (this.strafe_right != -1) {
			stream.writeByte(41);
			stream.writeSmartInt(strafe_right);
		}
		
		if (this.strafe_left != -1) {
			stream.writeByte(42);
			stream.writeSmartInt(strafe_left);
		}
		
		if (this.anInt3738 != -1) {
			stream.writeByte(43);
			stream.writeSmartInt(anInt3738);
		}
		
		if (this.anInt3754 != -1) {
			stream.writeByte(44);
			stream.writeSmartInt(anInt3754);
		}
		
		if (this.anInt3716 != -1) {
			stream.writeByte(45);
			stream.writeShort(anInt3716);
		}
		
		if (this.anInt3729 != -1) {
			stream.writeByte(46);
			stream.writeSmartInt(anInt3729);
		}
		
		if (this.anInt3743 != -1) {
			stream.writeByte(47);
			stream.writeSmartInt(anInt3743);
		}
		
		if (this.anInt3731 != -1) {
			stream.writeByte(48);
			stream.writeSmartInt(anInt3731);
		}
		
		if (this.anInt3739 != -1) {
			stream.writeByte(49);
			stream.writeSmartInt(anInt3739);
		}
		
		if (this.anInt3737 != -1) {
			stream.writeByte(50);
			stream.writeSmartInt(anInt3737);
		}
		
		if (this.anInt3730 != -1) {
			stream.writeByte(51);
			stream.writeSmartInt(anInt3730);
		}
		
		if (anIntArray3712 != null && anIntArray3713 != null) {
			stream.writeByte(52);
			int length = anIntArray3712.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeSmartInt(anIntArray3712[index]);
				stream.writeByte(anIntArray3713[index]);
			}
		}
		
		if (!aBoolean3756) {
			stream.writeByte(53);
		}
		
		if (anInt3734 != 0 && anInt3740 != 0) {
			stream.writeByte(54);
			stream.writeByte(anInt3734);
			stream.writeByte(anInt3740);
		}
		
		if (anIntArray3742 != null) {
			stream.writeByte(55);
			int length = anIntArray3742.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeShort(anIntArray3742[index]);
			}
		}
		
		if (anIntArrayArray3753 != null) {
			stream.writeByte(56);
			int length = anIntArrayArray3753.length;
			stream.writeByte(length);
			for (int index = 0; index < 3; index++) {
				stream.writeShort(anIntArrayArray3753[length][index]);
			}
		}
		
		stream.writeByte(0);
		byte[] offset_stream = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(offset_stream, 0, offset_stream.length);	
		return offset_stream;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.CONFIG.getIndex()].putFile(ConfigArchive.BAS.getValue(), id, encode());
	}
	
	
	@Override
	public String toString() {
		return "" + id;
	}

}
