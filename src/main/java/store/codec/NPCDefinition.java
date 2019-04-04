package store.codec;

import java.util.HashMap;
import java.util.Map;

import store.FileStore;
import store.codec.util.Utils;
import store.io.InputStream;
import store.io.OutputStream;

@SuppressWarnings("unused")
public class NPCDefinition implements AbstractDefinition, Cloneable {

	public int id;
	public int unknownInt13;
	public int unknownInt6;
	public int unknownInt15;
	public byte respawnDirection;
	public int size = 1;
	public int[][] unknownArray3;
	public boolean unknownBoolean2;
	public int unknownInt9;
	public int unknownInt4;
	public int[] unknownArray2;
	public int unknownInt7;
	public int renderEmote;
	public boolean unknownBoolean5 = false;
	public int unknownInt20;
	public byte unknownByte1;
	public boolean unknownBoolean3;
	public int unknownInt3;
	public byte unknownByte2;
	public boolean unknownBoolean6;
	public boolean unknownBoolean4;
	public int[] originalModelColors;
	public int combatLevel;
	public byte[] unknownArray1;
	public short unknownShort1;
	public boolean unknownBoolean1;
	public int npcHeight;
	public String name;
	public int[] modifiedTextureColors;
	public byte walkMask;
	public int[] modelIds;
	public int unknownInt1;
	public int unknownInt21;
	public int unknownInt11;
	public int unknownInt17;
	public int unknownInt14;
	public int unknownInt12;
	public int unknownInt8;
	public int headIcon;
	public int unknownInt19;
	public int[] originalTextureColors;
	public int[][] anIntArrayArray882;
	public int unknownInt10;
	public int[] unknownArray4;
	public int unknownInt5;
	public int unknownInt16;
	public boolean invisibleOnMap;
	public int[] npcChatHeads;
	public short unknownShort2;
	public String[] options;
	public int[] modifiedModelColors;
	public int unknownInt2;
	public int npcId;
	public int unknownInt18;
	public boolean unknownBoolean7;
	public int[] unknownArray5;
	public Map<Integer, Object> params = null;
	public int anInt833;
	public int anInt836;
	public int anInt837;
	public int[][] anIntArrayArray840;
	public boolean aBoolean841;
	public int anInt842;
	public int bConfig;
	public int[] transformTo;
	public int anInt846;
	public boolean aBoolean849 = false;
	public int anInt850;
	public byte aByte851;
	public boolean aBoolean852;
	public int anInt853;
	public byte aByte854;
	public boolean aBoolean856;
	public boolean aBoolean857;
	public short[] recolorToFind;
	public byte[] aByteArray861;
	public short aShort862;
	public boolean renderPriority;
	public short[] retextureToReplace;
	public int ambient;
	public int anInt870;
	public int anInt871;
	public int anInt872;
	public int anInt874;
	public int anInt875;
	public int anInt876;
	public int anInt879;
	public short[] retextureToFind;
	public int anInt884;
	public int[] anIntArray885;
	public int config;
	public int anInt889;
	public int[] anIntArray892;
	public short aShort894;
	public short[] recolorToReplace;
	public int contrast;
	public int npcWidth;
	public int anInt901;
	public boolean aBoolean3190;
	private byte[] aByteArray1293;
	private byte[] aByteArray12930;
	private int[] anIntArray2930;

	public NPCDefinition(int id) {
		this.id = id;
		this.anInt842 = -1;
		this.bConfig = -1;
		this.anInt837 = -1;
		this.anInt846 = -1;
		this.anInt853 = 32;
		this.combatLevel = -1;
		this.anInt836 = -1;
		this.name = "null";
		this.ambient = 0;
		this.walkMask = 0;
		this.anInt850 = 255;
		this.anInt871 = -1;
		this.aBoolean852 = true;
		this.aShort862 = 0;
		this.anInt876 = -1;
		this.aByte851 = -96;
		this.anInt875 = 0;
		this.anInt872 = -1;
		this.renderEmote = -1;
		this.respawnDirection = 7;
		this.aBoolean857 = true;
		this.anInt870 = -1;
		this.anInt874 = -1;
		this.anInt833 = -1;
		this.npcHeight = 128;
		this.headIcon = -1;
		this.aBoolean856 = false;
		this.config = -1;
		this.aByte854 = -16;
		this.renderPriority = false;
		this.invisibleOnMap = true;
		this.anInt889 = -1;
		this.anInt884 = -1;
		this.aBoolean841 = true;
		this.anInt879 = -1;
		this.npcWidth = 128;
		this.aShort894 = 0;
		this.options = new String[5];
		this.contrast = 0;
		this.anInt901 = -1;
		this.modelIds = new int[] { 0 };
	}
	
	@Override
	public void decode(InputStream stream) {
		while (true) {	
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;		
			if (opcode != 1) {
				if (opcode == 2)
					name = stream.readString();
				else if ((opcode ^ 0xffffffff) != -13) {
					if (opcode >= 30 && (opcode ^ 0xffffffff) > -36) {
						options[opcode - 30] = stream.readString();
						if (options[-30 + opcode].equalsIgnoreCase("Hidden"))
							options[-30 + opcode] = null;
					} else if ((opcode ^ 0xffffffff) != -41) {
						if (opcode == 41) {
							int i = stream.readUnsignedByte();
							retextureToFind = new short[i];
							retextureToReplace = new short[i];
							for (int i_54_ = 0; (i_54_ ^ 0xffffffff) > (i ^ 0xffffffff); i_54_++) {
								retextureToFind[i_54_] = (short) stream.readUnsignedShort();
								retextureToReplace[i_54_] = (short) stream.readUnsignedShort();
							}
						} else if (opcode == 44) {
							int i_24_ = (short) stream.readUnsignedShort();
							int i_25_ = 0;
							for (int i_26_ = i_24_; i_26_ > 0; i_26_ >>= 1)
								i_25_++;
							// aByteArray12930 = new byte[i_25_];
							byte i_27_ = 0;
							for (int i_28_ = 0; i_28_ < i_25_; i_28_++) {
								if ((i_24_ & 1 << i_28_) > 0) {
									// aByteArray12930[i_28_] = i_27_;
									i_27_++;
								}
								// aByteArray12930[i_28_] = (byte) -1;
							}
						} else if (45 == opcode) {
							int i_29_ = (short) stream.readUnsignedShort();
							int i_30_ = 0;
							for (int i_31_ = i_29_; i_31_ > 0; i_31_ >>= 1)
								i_30_++;
							// aByteArray1293 = new byte[i_30_];
							byte i_32_ = 0;
							for (int i_33_ = 0; i_33_ < i_30_; i_33_++) {
								if ((i_29_ & 1 << i_33_) > 0) {
									// aByteArray1293[i_33_] = i_32_;
									i_32_++;
								}
								// aByteArray1293[i_33_] = (byte) -1;
							}
						} else if ((opcode ^ 0xffffffff) == -43) {
							int i = stream.readUnsignedByte();
							aByteArray861 = new byte[i];
							for (int i_55_ = 0; i > i_55_; i_55_++)
								aByteArray861[i_55_] = (byte) stream.readByte();
						} else if ((opcode ^ 0xffffffff) != -61) {
							if (opcode == 93)
								invisibleOnMap = false;
							else if ((opcode ^ 0xffffffff) == -96)
								combatLevel = stream.readUnsignedShort();
							else if (opcode != 97) {
								if ((opcode ^ 0xffffffff) == -99)
									npcWidth = stream.readUnsignedShort();
								else if ((opcode ^ 0xffffffff) == -100)
									renderPriority = true;
								else if (opcode == 100)
									ambient = stream.readByte();
								else if ((opcode ^ 0xffffffff) == -102)
									contrast = stream.readByte() * 5;
								else if ((opcode ^ 0xffffffff) == -103)
									headIcon = stream.readUnsignedShort();
								else if (opcode != 103) {
									if (opcode == 106 || opcode == 118) {
										bConfig = stream.readUnsignedShort();
										if (bConfig == 65535)
											bConfig = -1;
										config = stream.readUnsignedShort();
										if (config == 65535)
											config = -1;
										int i = -1;
										if ((opcode ^ 0xffffffff) == -119) {
											i = stream.readUnsignedShort();
											if ((i ^ 0xffffffff) == -65536)
												i = -1;
										}
										int i_56_ = stream.readUnsignedByte();
										transformTo = new int[2 + i_56_];
										for (int i_57_ = 0; i_56_ >= i_57_; i_57_++) {
											transformTo[i_57_] = stream.readUnsignedShort();
											if (transformTo[i_57_] == 65535)
												transformTo[i_57_] = -1;
										}
										transformTo[i_56_ - -1] = i;
									} else if ((opcode ^ 0xffffffff) != -108) {
										if ((opcode ^ 0xffffffff) == -110)
											aBoolean852 = false;
										else if ((opcode ^ 0xffffffff) != -112) {
											if (opcode != 113) {
												if (opcode == 114) {
													aByte851 = (byte) (stream.readByte());
													aByte854 = (byte) (stream.readByte());
												} else if (opcode == 115) {
													stream.readUnsignedByte();
													stream.readUnsignedByte();
												} else if ((opcode ^ 0xffffffff) != -120) {
													if (opcode != 121) {
														if ((opcode ^ 0xffffffff) != -123) {
															if (opcode == 123)
																anInt846 = (stream.readUnsignedShort());
															else if (opcode != 125) {
																if (opcode == 127)
																	renderEmote = (stream.readUnsignedShort());
																else if ((opcode ^ 0xffffffff) == -129)
																	stream.readUnsignedByte();
																else if (opcode != 134) {
																	if (opcode == 135) {
																		anInt833 = stream.readUnsignedByte();
																		anInt874 = stream.readUnsignedShort();
																	} else if (opcode != 136) {
																		if (opcode != 137) {
																			if (opcode != 138) {
																				if ((opcode ^ 0xffffffff) != -140) {
																					if (opcode == 140)
																						anInt850 = stream
																								.readUnsignedByte();
																					else if (opcode == 141)
																						aBoolean849 = true;
																					else if ((opcode
																							^ 0xffffffff) != -143) {
																						if (opcode == 143)
																							aBoolean856 = true;
																						else if ((opcode
																								^ 0xffffffff) <= -151
																								&& opcode < 155) {
																							options[opcode - 150] = stream
																									.readString();
																							if (options[opcode - 150]
																									.equalsIgnoreCase(
																											"Hidden"))
																								options[opcode
																										+ -150] = null;
																						} else if ((opcode
																								^ 0xffffffff) == -161) {
																							int i = stream
																									.readUnsignedByte();
																							anIntArray885 = new int[i];
																							for (int i_58_ = 0; i > i_58_; i_58_++)
																								anIntArray885[i_58_] = stream
																										.readUnsignedShort();

																							// all
																							// added
																							// after
																							// here
																						} else if (opcode == 155) {
																							int aByte821 = stream
																									.readByte();
																							int aByte824 = stream
																									.readByte();
																							int aByte843 = stream
																									.readByte();
																							int aByte855 = stream
																									.readByte();
																						} else if (opcode == 158) {
																							byte aByte833 = (byte) 1;
																						} else if (opcode == 159) {
																							byte aByte833 = (byte) 0;
																						} else if (opcode == 162) { // added
																							// opcode
																							// aBoolean3190
																							// =
																							// true;
																						} else if (opcode == 163) { // added
																							// opcode
																							int anInt864 = stream
																									.readUnsignedByte();
																						} else if (opcode == 164) {
																							int anInt848 = stream
																									.readUnsignedShort();
																							int anInt837 = stream
																									.readUnsignedShort();
																						} else if (opcode == 165) {
																							int anInt847 = stream
																									.readUnsignedByte();
																						} else if (opcode == 168) {
																							int anInt828 = stream
																									.readUnsignedByte();
																						} else if (opcode >= 170
																								&& opcode < 176) {
																							// if
																							// (null
																							// ==
																							// anIntArray2930)
																							// {
																							// anIntArray2930
																							// =
																							// new
																							// int[6];
																							// Arrays.fill(anIntArray2930,
																							// -1);
																							// }
																							int i_44_ = (short) stream
																									.readUnsignedShort();
																							if (i_44_ == 65535)
																								i_44_ = -1;
																							// anIntArray2930[opcode
																							// -
																							// 170]
																							// =
																							// i_44_;
																						} else if (opcode == 249) {
																							int i = stream
																									.readUnsignedByte();
																							if (params == null) {
																								params = new HashMap<Integer, Object>(
																										i);
																							}
																							for (int i_60_ = 0; i > i_60_; i_60_++) {
																								boolean stringInstance = stream
																										.readUnsignedByte() == 1;
																								int key = stream
																										.readMedium();
																								Object value;
																								if (stringInstance)
																									value = stream
																											.readString();
																								else
																									value = stream
																											.readInt();
																								params.put(key,
																										value);
																							}
																						}
																					} else
																						anInt870 = stream
																								.readUnsignedShort();
																				} else
																					anInt879 = stream.readSmartInt();
																			} else
																				anInt901 = stream.readSmartInt();
																		} else
																			anInt872 = stream.readUnsignedShort();
																	} else {
																		anInt837 = stream.readUnsignedByte();
																		anInt889 = stream.readUnsignedShort();
																	}
																} else {
																	anInt876 = (stream.readUnsignedShort());
																	if (anInt876 == 65535)
																		anInt876 = -1;
																	anInt842 = (stream.readUnsignedShort());
																	if (anInt842 == 65535)
																		anInt842 = -1;
																	anInt884 = (stream.readUnsignedShort());
																	if ((anInt884 ^ 0xffffffff) == -65536)
																		anInt884 = -1;
																	anInt871 = (stream.readUnsignedShort());
																	if ((anInt871 ^ 0xffffffff) == -65536)
																		anInt871 = -1;
																	anInt875 = (stream.readUnsignedByte());
																}
															} else
																respawnDirection = (byte) (stream.readByte());
														} else
															anInt836 = stream.readSmartInt();
													} else {
														anIntArrayArray840 = (new int[modelIds.length][]);
														int i = (stream.readUnsignedByte());
														for (int i_62_ = 0; ((i_62_ ^ 0xffffffff) > (i
																^ 0xffffffff)); i_62_++) {
															int i_63_ = (stream.readUnsignedByte());
															int[] is = (anIntArrayArray840[i_63_] = (new int[3]));
															is[0] = (stream.readByte());
															is[1] = (stream.readByte());
															is[2] = (stream.readByte());
														}
													}
												} else
													walkMask = (byte) (stream.readByte());
											} else {
												aShort862 = (short) (stream.readUnsignedShort());
												aShort894 = (short) (stream.readUnsignedShort());
											}
										} else
											aBoolean857 = false;
									} else
										aBoolean841 = false;
								} else
									anInt853 = stream.readUnsignedShort();
							} else
								npcHeight = stream.readUnsignedShort();
						} else {
							int i = stream.readUnsignedByte();
							npcChatHeads = new int[i];
							for (int i_64_ = 0; (i_64_ ^ 0xffffffff) > (i ^ 0xffffffff); i_64_++)
								npcChatHeads[i_64_] = stream.readSmartInt();
						}
					} else {
						int i = stream.readUnsignedByte();
						recolorToFind = new short[i];
						recolorToReplace = new short[i];
						for (int i_65_ = 0; (i ^ 0xffffffff) < (i_65_ ^ 0xffffffff); i_65_++) {
							recolorToReplace[i_65_] = (short) stream.readUnsignedShort();
							recolorToFind[i_65_] = (short) stream.readUnsignedShort();
						}
					}
				} else
					size = stream.readUnsignedByte();
			} else {
				int i = stream.readUnsignedByte();
				modelIds = new int[i];
				for (int i_66_ = 0; i_66_ < i; i_66_++) {
					modelIds[i_66_] = stream.readSmartInt();
					if ((modelIds[i_66_] ^ 0xffffffff) == -65536)
						modelIds[i_66_] = -1;
				}
			}
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		int data;
		if (this.modelIds != null) {
			stream.writeByte(1);
			stream.writeByte(this.modelIds.length);
			for (data = 0; data < this.modelIds.length; ++data) {
				stream.writeSmartInt(this.modelIds[data]);
			}
		}

		if (!this.name.equals("null")) {
			stream.writeByte(2);
			stream.writeString(this.name);
		}

		if (this.size != 0) {
			stream.writeByte(12);
			stream.writeByte(this.size);
		}

		for (data = 0; data < this.options.length; ++data) {
			if (this.options[data] != null && this.options[data] != "Hidden") {
				stream.writeByte(30 + data);
				stream.writeString(this.options[data]);
			}
		}

		if (this.originalModelColors != null && this.modifiedModelColors != null) {
			stream.writeByte(40);
			stream.writeByte(this.originalModelColors.length);
			for (data = 0; data < this.originalModelColors.length; ++data) {
				stream.writeShort(this.originalModelColors[data]);
				stream.writeShort(this.modifiedModelColors[data]);
			}
		}

		if (this.originalTextureColors != null && this.modifiedTextureColors != null) {
			stream.writeByte(41);
			stream.writeByte(this.originalTextureColors.length);
			for (data = 0; data < this.originalTextureColors.length; ++data) {
				stream.writeShort(this.originalTextureColors[data]);
				stream.writeShort(this.modifiedTextureColors[data]);
			}
		}

		if (this.unknownArray1 != null) {
			stream.writeByte(42);
			stream.writeByte(this.unknownArray1.length);
			for (data = 0; data < this.unknownArray1.length; ++data) {
				stream.writeByte(this.unknownArray1[data]);
			}
		}

		if (this.npcChatHeads != null) {
			stream.writeByte(60);
			stream.writeByte(this.npcChatHeads.length);
			for (data = 0; data < this.npcChatHeads.length; ++data) {
				stream.writeSmartInt(this.npcChatHeads[data]);
			}
		}

		if (this.invisibleOnMap) {
			stream.writeByte(93);
		}

		if (this.combatLevel != -1) {
			stream.writeByte(95);
			stream.writeShort(this.combatLevel);
		}

		if (this.npcHeight != 0) {
			stream.writeByte(97);
			stream.writeShort(this.npcHeight);
		}

		if (this.npcWidth != 0) {
			stream.writeByte(98);
			stream.writeShort(this.npcWidth);
		}

		if (this.unknownBoolean1) {
			stream.writeByte(99);
		}

		if (this.unknownInt1 != 0) {
			stream.writeByte(100);
			stream.writeByte(this.unknownInt1);
		}

		if (this.unknownInt2 != 0) {
			stream.writeByte(101);
			stream.writeByte(this.unknownInt2 / 5);
		}

		if (this.headIcon != -1) {
			stream.writeByte(102);
			stream.writeShort(this.headIcon);
		}

		if (this.walkMask != -1) {
			stream.writeByte(119);
			stream.writeByte(this.walkMask);
		}

		if (this.respawnDirection != 7) {
			stream.writeByte(125);
			stream.writeByte(this.respawnDirection);
		}

		if (this.renderEmote != -1) {
			stream.writeByte(127);
			stream.writeShort(this.renderEmote);
		}

		if (this.params != null) {
            stream.writeByte(249);
            stream.writeByte(params.size());
            for (int key : params.keySet()) {
                Object value = params.get(key);
                stream.writeByte(value instanceof String ? 1 : 0);
                stream.writeMedium(key);
                if (value instanceof String) {
                    stream.writeString((String) value);
                } else {
                    stream.writeInt((Integer) value);
                }
            }
		}

		stream.writeByte(0);
		byte[] var61 = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(var61, 0, var61.length);
		return var61;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[18].putFile(Utils.getConfigArchive(id, 7), Utils.getConfigFile(id, 7), encode());
	}
	
	@Override
	public String toString() {
		return this.id + " - " + this.name;
	}
	
	@Override
	public Cloneable clone() {
		return this;
	}

}
