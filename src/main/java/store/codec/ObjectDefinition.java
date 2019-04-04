package store.codec;

import java.util.HashMap;
import java.util.Map;

import store.FileStore;
import store.codec.util.Utils;
import store.io.InputStream;
import store.io.OutputStream;

public class ObjectDefinition implements AbstractDefinition, Cloneable {

	public int id;
	public short[] originalColors;
	public int[] transformations;
	public static int anInt3832;
	public int[] anIntArray3833 = null;
	public int anInt3834;
	public int anInt3835;
	public static int anInt3836;
	public byte aByte3837;
	public int anInt3838 = -1;
	public boolean aBoolean3839;
	public int contrast;
	public int modelSizeHeight;
	public int anInt3844;
	public boolean aBoolean3845;
	public byte aByte3847;
	public byte aByte3849;
	public int anInt3850;
	public int anInt3851;
	public boolean secondBool;
	public boolean aBoolean3853;
	public int anInt3855;
	public boolean isSolid;
	public int anInt3857;
	public byte[] aByteArray3858;
	public int[] anIntArray3859;
	public int anInt3860;
	public String[] options;
	public int configFileId;
	public short[] modifiedColors;
	public int anInt3865;
	public boolean aBoolean3866;
	public boolean nonFlatShading;
	public boolean projectileCliped;
	public int[] anIntArray3869;
	public boolean aBoolean3870;
	public int sizeY;
	public boolean aBoolean3872;
	public boolean aBoolean3873;
	public int thirdInt;
	public int anInt3875;
	public int objectAnimation;
	public int anInt3877;
	public int ambient;
	public int clipType;
	public int anInt3881;
	public int anInt3882;
	public int offsetX;
	public int offsetHeight;
	public int sizeX;
	public boolean aBoolean3891;
	public int anInt3892;
	public int secondInt;
	public boolean aBoolean3894;
	public boolean aBoolean3895;
	public int anInt3896;
	public int configId;
	public byte[] shapes;
	public int anInt3900;
	public String name;
	public int modelSizeX;
	public int anInt3904;
	public int anInt3905;
	public boolean aBoolean3906;
	public int[] anIntArray3908;
	public byte aByte3912;
	public int anInt3913;
	public byte aByte3914;
	public int offsetY;
	public int[][] models;
	public int modelSizeY;
	public short[] retextureToReplace;
	public short[] retextureToFind;
	public int anInt3921;
	public Map<Integer, Object> params = null;
	public boolean aBoolean3923;
	public boolean aBoolean3924;
	public int anInt3925;
	public int anInt3336;
	public int anInt3302;
	public int anInt3362;
	public int opcode178;
	public int anInt3383;
	public boolean bloom;
	public boolean opcode177;
	
	public ObjectDefinition(int id) {
		this.id = id;
		anInt3835 = -1;
		anInt3860 = -1;
		configFileId = 0;
		aBoolean3866 = false;
		anInt3851 = -1;
		anInt3865 = 255;
		aBoolean3845 = false;
		nonFlatShading = false;
		anInt3850 = 0;
		anInt3844 = -1;
		anInt3881 = 0;
		anInt3857 = -1;
		aBoolean3872 = true;
		anInt3882 = -1;
		anInt3834 = 0;
		options = new String[5];
		anInt3875 = 0;
		aBoolean3839 = false;
		anIntArray3869 = null;
		sizeY = 1;
		thirdInt = -1;
		offsetX = 0;
		aBoolean3895 = true;
		contrast = 0;
		aBoolean3870 = false;
		offsetHeight = 0;
		aBoolean3853 = true;
		secondBool = false;
		clipType = 2;
		projectileCliped = true;
		isSolid = false;
		anInt3855 = -1;
		ambient = 0;
		anInt3904 = 0;
		sizeX = 1;
		objectAnimation = -1;
		aBoolean3891 = false;
		anInt3905 = 0;
		name = "null";
		anInt3913 = -1;
		aBoolean3906 = false;
		aBoolean3873 = false;
		aByte3914 = (byte) 0;
		offsetY = 0;
		anInt3900 = 0;
		secondInt = -1;
		aBoolean3894 = false;
		aByte3912 = (byte) 0;
		anInt3921 = 0;
		modelSizeX = 128;
		configId = 0;
		anInt3877 = 0;
		anInt3925 = 0;
		anInt3892 = 64;
		aBoolean3923 = false;
		aBoolean3924 = false;
		modelSizeHeight = 128;
		modelSizeY = 128;
	}

	@Override
	public void decode(InputStream stream) {
		try {
			while(true) {
				
				int opcode = stream.readUnsignedByte();
				
				if (opcode == 0)
					break;
				
				if (opcode != 1 && opcode != 5) {
					if (opcode != 2) {
						if (opcode != 14) {
							if (opcode != 15) {
								if (opcode == 17) { // nocliped
									projectileCliped = false;
									clipType = 0;
								} else if (opcode != 18) {
									if (opcode == 19)
										secondInt = stream.readUnsignedByte();
									else if (opcode == 21)
										aByte3912 = (byte) 1;
									else if (opcode != 22) {
										if (opcode != 23) {
											if (opcode != 24) {
												if (opcode == 27) // cliped, no idea
																	// diff between 2
																	// and 1
													clipType = 1;
												else if (opcode == 28)
													anInt3892 = (stream
															.readUnsignedByte() << 2);
												else if (opcode != 29) {
													if (opcode != 39) {
														if (opcode < 30 || opcode >= 35) {
															if (opcode == 40) {
																int i_53_ = (stream
																		.readUnsignedByte());
																originalColors = new short[i_53_];
																modifiedColors = new short[i_53_];
																for (int i_54_ = 0; i_53_ > i_54_; i_54_++) {
																	originalColors[i_54_] = (short) (stream
																			.readUnsignedShort());
																	modifiedColors[i_54_] = (short) (stream
																			.readUnsignedShort());
																}
															} else if (opcode != 41) { // object
																						// anim
																if (opcode != 42) {
																	if (opcode != 62) {
																		if (opcode != 64) {
																			if (opcode == 65)
																				modelSizeX = stream
																						.readUnsignedShort();
																			else if (opcode != 66) {
																				if (opcode != 67) {
																					if (opcode == 69)
																						anInt3925 = stream
																								.readUnsignedByte();
																					else if (opcode != 70) {
																						if (opcode == 71)
																							offsetHeight = stream
																									.readShort() << 2;
																						else if (opcode != 72) {
																							if (opcode == 73)
																								secondBool = true;
																							else if (opcode == 74)
																								isSolid = true;
																							else if (opcode != 75) {
																								if (opcode != 77
																										&& opcode != 92) {
																									if (opcode == 78) {
																										anInt3860 = stream
																												.readUnsignedShort();
																										anInt3904 = stream
																												.readUnsignedByte();
																									} else if (opcode != 79) {
																										if (opcode == 81) {
																											aByte3912 = (byte) 2;
																											anInt3882 = 256 * stream
																													.readUnsignedByte();
																										} else if (opcode != 82) {
																											if (opcode == 88)
																												aBoolean3853 = false;
																											else if (opcode != 89) {
																												if (opcode == 90)
																													aBoolean3870 = true;
																												else if (opcode != 91) {
																													if (opcode != 93) {
																														if (opcode == 94)
																															aByte3912 = (byte) 4;
																														else if (opcode != 95) {
																															if (opcode != 96) {
																																if (opcode == 97)
																																	aBoolean3866 = true;
																																else if (opcode == 98)
																																	aBoolean3923 = true;
																																else if (opcode == 99) {
																																	anInt3857 = stream
																																			.readUnsignedByte();
																																	anInt3835 = stream
																																			.readUnsignedShort();
																																} else if (opcode == 100) {
																																	anInt3844 = stream
																																			.readUnsignedByte();
																																	anInt3913 = stream
																																			.readUnsignedShort();
																																} else if (opcode != 101) {
																																	if (opcode == 102)
																																		anInt3838 = stream
																																				.readUnsignedShort();
																																	else if (opcode == 103)
																																		thirdInt = 0;
																																	else if (opcode != 104) {
																																		if (opcode == 105)
																																			aBoolean3906 = true;
																																		else if (opcode == 106) {
																																			int i_55_ = stream
																																					.readUnsignedByte();
																																			anIntArray3869 = new int[i_55_];
																																			anIntArray3833 = new int[i_55_];
																																			for (int i_56_ = 0; i_56_ < i_55_; i_56_++) {
																																				anIntArray3833[i_56_] = stream.readSmartInt();
																																				int i_57_ = stream
																																						.readUnsignedByte();
																																				anIntArray3869[i_56_] = i_57_;
																																				anInt3881 += i_57_;
																																			}
																																		} else if (opcode == 107)
																																			anInt3851 = stream
																																					.readUnsignedShort();
																																		else if (opcode >= 150
																																				&& opcode < 155) {
																																			options[opcode
																																					+ -150] = stream
																																					.readString();
																																		} else if (opcode != 160) {
																																			if (opcode == 162) {
																																				aByte3912 = (byte) 3;
																																				anInt3882 = stream
																																						.readInt();
																																			} else if (opcode == 163) {
																																				aByte3847 = (byte) stream
																																						.readByte();
																																				aByte3849 = (byte) stream
																																						.readByte();
																																				aByte3837 = (byte) stream
																																						.readByte();
																																				aByte3914 = (byte) stream
																																						.readByte();
																																			} else if (opcode != 164) {
																																				if (opcode != 165) {
																																					if (opcode != 166) {
																																						if (opcode == 167)
																																							anInt3921 = stream
																																									.readUnsignedShort();
																																						else if (opcode != 168) {
																																							if (opcode == 169) {
																																								aBoolean3845 = true;
																																								// added
																																								// opcode
																																							} else if (opcode == 170) {
																																								anInt3383 = stream
																																										.readUnsignedSmart();
																																								// added
																																								// opcode
																																							} else if (opcode == 171) {
																																								anInt3362 = stream
																																										.readUnsignedSmart();
																																							} else if (opcode == 173) {
																																								anInt3302 = stream
																																										.readUnsignedShort();
																																								anInt3336 = stream
																																										.readUnsignedShort();
																																							} else if (opcode == 177) {
																																								opcode177 = true;
																																							} else if (opcode == 178) {
																																								opcode178 = stream
																																									.readUnsignedByte();
																																							} else if (opcode == 189) {
																																								bloom = true;
																																							} else if (opcode == 249) {
																																								int length = stream
																																										.readUnsignedByte();
																																								if (params == null)
																																									params = new HashMap<Integer, Object>(
																																											length);
																																								for (int i_60_ = 0; i_60_ < length; i_60_++) {
																																									boolean isString = stream
																																											.readUnsignedByte() == 1;
																																									int i_61_ = stream
																																											.readMedium();
																																									if (!isString)
																																										params
																																												.put(i_61_,
																																														stream.readInt());
																																									else
																																										params
																																												.put(i_61_,
																																														stream.readString());
	
																																								}
																																							}
																																						} else
																																							aBoolean3894 = true;
																																					} else
																																						anInt3877 = stream
																																								.readShort();
																																				} else
																																					anInt3875 = stream
																																							.readShort();
																																			} else
																																				anInt3834 = stream
																																						.readShort();
																																		} else {
																																			int i_62_ = stream
																																					.readUnsignedByte();
																																			anIntArray3908 = new int[i_62_];
																																			for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
																																				anIntArray3908[i_63_] = stream
																																						.readUnsignedShort();
																																		}
																																	} else
																																		anInt3865 = stream
																																				.readUnsignedByte();
																																} else
																																	anInt3850 = stream
																																			.readUnsignedByte();
																															} else
																																aBoolean3924 = true;
																														} else {
																															aByte3912 = (byte) 5;
																															anInt3882 = stream
																																	.readShort();
																														}
																													} else {
																														aByte3912 = (byte) 3;
																														anInt3882 = stream
																																.readUnsignedShort();
																													}
																												} else
																													aBoolean3873 = true;
																											} else
																												aBoolean3895 = false;
																										} else
																											aBoolean3891 = true;
																									} else {
																										anInt3900 = stream
																												.readUnsignedShort();
																										anInt3905 = stream
																												.readUnsignedShort();
																										anInt3904 = stream
																												.readUnsignedByte();
																										int i_64_ = stream
																												.readUnsignedByte();
																										anIntArray3859 = new int[i_64_];
																										for (int i_65_ = 0; i_65_ < i_64_; i_65_++)
																											anIntArray3859[i_65_] = stream
																													.readUnsignedShort();
																									}
																								} else {
																									configFileId = stream.readUnsignedShort();
																									if (configFileId == 65535)
																										configFileId = -1;
																									configId = stream.readUnsignedShort();
																									if (configId == 65535)
																										configId = -1;
																									int i_66_ = -1;
																									if (opcode == 92) {
																										i_66_ = stream.readSmartInt();
																									}
																									int length = stream.readUnsignedByte();
																									transformations = new int[length - -2];
																									for (int transform = 0; length >= transform; transform++) {
																										transformations[transform] = stream.readSmartInt();
																									}
																									transformations[length + 1] = i_66_;
																								}
																							} else
																								anInt3855 = stream
																										.readUnsignedByte();
																						} else
																							offsetY = stream
																									.readShort() << 2;
																					} else
																						offsetX = stream
																								.readShort() << 2;
																				} else
																					modelSizeY = stream
																							.readUnsignedShort();
																			} else
																				modelSizeHeight = stream
																						.readUnsignedShort();
																		} else
																			// 64
																			aBoolean3872 = false;
																	} else
																		aBoolean3839 = true;
																} else {
																	int i_69_ = (stream
																			.readUnsignedByte());
																	aByteArray3858 = (new byte[i_69_]);
																	for (int i_70_ = 0; i_70_ < i_69_; i_70_++)
																		aByteArray3858[i_70_] = (byte) (stream
																				.readByte());
																}
															} else {
																int i_71_ = (stream
																		.readUnsignedByte());
																retextureToFind = new short[i_71_];
																retextureToReplace = new short[i_71_];
																for (int i_72_ = 0; i_71_ > i_72_; i_72_++) {
																	retextureToFind[i_72_] = (short) (stream.readUnsignedShort());
																	retextureToReplace[i_72_] = (short) (stream.readUnsignedShort());
																}
															}
														} else {
															options[-30 + opcode] = (stream
																	.readString());
														}
													} else
														// 39
														contrast = (stream.readByte() * 5);
												} else {// 29
													ambient = stream.readByte();
												}
											} else {
												objectAnimation = stream.readSmartInt();
											}
										} else
											thirdInt = 1;
									} else
										nonFlatShading = true;
								} else
									projectileCliped = false;
							} else
								// 15
								sizeY = stream.readUnsignedByte();
						} else
							// 14
							sizeX = stream.readUnsignedByte();
					} else {
						name = stream.readString();
					}
				} else {
					int length = stream.readUnsignedByte();
					models = new int[length][];
					shapes = new byte[length];
					for (int type = 0; type < length; type++) {
						shapes[type] = (byte) stream.readByte();
						int count = stream.readUnsignedByte();
						models[type] = new int[count];
						for (int model_id = 0; count > model_id; model_id++)
							models[type][model_id] = stream.readSmartInt();
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		try {
			
			if (models != null && shapes != null) {
				//Model shit
				stream.writeByte(1);	
				int length = models.length;
				stream.writeByte(length);	
				for (int shape = 0; shape < length; shape++) {
					stream.writeByte(shapes[shape]);
					int count = models[shape].length;
					stream.writeByte(count);
					for (int model = 0; count > model; model++) {
						stream.writeSmartInt(models[shape][model]);
					}
				}			
			}
	
			if (!this.name.equalsIgnoreCase("null")) {
				stream.writeByte(2);
				stream.writeString(this.name);
			}
			
			if (this.anInt3925 > 0) {
				stream.writeByte(69);
				stream.writeByte(this.anInt3925);
			}
			
			if (this.offsetX > 0) { 
				stream.writeByte(70);
				stream.writeShort(this.offsetX >> 2);
			}
			
			if (this.offsetHeight > 0) {
				stream.writeByte(71);
				stream.writeShort(this.offsetHeight >> 2);
			}
			
			if (this.offsetY > 0) {
				stream.writeByte(72);
				stream.writeShort(this.offsetY >> 2);
			}
		
			if (this.nonFlatShading) {
				stream.writeByte(22);
			}
			
			if (this.sizeX > 0) {
				stream.writeByte(14);
				stream.writeByte(this.sizeX);
			}
			
			if (this.ambient > 0) {
				stream.writeByte(29);
				stream.writeByte(this.ambient);
			}
			
			if (this.contrast > 0) {
				stream.writeByte(39);
				stream.writeByte(this.contrast / 5);
			}
	
			if (this.sizeY > 0) {
				stream.writeByte(15);
				stream.writeByte(this.sizeY);
			}
	
			if (this.objectAnimation != -1) {
				stream.writeByte(24);
				stream.writeSmartInt(this.objectAnimation);
			}
			
//			configFileId = stream.readUnsignedShort();
//			if (configFileId == 65535)
//				configFileId = -1;
//			configId = stream.readUnsignedShort();
//			if (configId == 65535)
//				configId = -1;
//			int i_66_ = -1;
//			if (opcode == 92) {
//				i_66_ = stream.readSmartInt();
//			}
//			int i_67_ = stream.readUnsignedByte();
//			transformations = new int[i_67_ - -2];
//			for (int i_68_ = 0; i_67_ >= i_68_; i_68_++) {
//				transformations[i_68_] = stream.readSmartInt();
//			}
//			transformations[i_67_ + 1] = i_66_;
			
			if (transformations != null) {
				int length = transformations.length;
				boolean hasTransforms = length > 0;
				stream.writeByte(hasTransforms ? 92 : 77);
				stream.writeShort(configFileId);
				stream.writeShort(configId);
				if (hasTransforms) {
					System.out.println("length? " + transformations[length - 1]);
					stream.writeSmartInt(transformations[length - 1]);
				}
				stream.writeByte(length - 2);
				for (int transform = 0; transform <= (length - 1); transform++) {
					int id = transformations[transform]; System.out.println(id);
					stream.writeSmartInt(transformations[transform]);
				}
			}

			for (int option = 0; option < this.options.length; ++option) {
				if (this.options[option] != null && !this.options[option].equalsIgnoreCase("null") && !this.options[option].equalsIgnoreCase("Hidden")) {
					stream.writeByte(30 + option);
					stream.writeString(this.options[option]);
				}
			}		
	
			if (this.originalColors != null && this.modifiedColors != null) {
				stream.writeByte(40);
				stream.writeByte(this.originalColors.length);	
				for (int color = 0; color < this.originalColors.length; ++color) {
					stream.writeShort(this.originalColors[color]);
					stream.writeShort(this.modifiedColors[color]);
				}
			}
			
			if (this.retextureToFind != null && this.retextureToReplace != null) {
				stream.writeByte(41);
				stream.writeByte(this.retextureToFind.length);	
				for (int color = 0; color < this.retextureToFind.length; ++color) {
					stream.writeShort(this.retextureToFind[color]);
					stream.writeShort(this.retextureToReplace[color]);
				}
			}
	
			if (this.clipType == 0 && this.projectileCliped) {
				stream.writeByte(17);
			}
	
			if (this.projectileCliped) {
				stream.writeByte(18);
			}
	
			if (this.clipType == 1 || this.clipType == 2) {
				stream.writeByte(27);
			}
			
			if (this.isSolid) {
				stream.writeByte(74);
			}	
			
			if (this.modelSizeX > 0) {
				stream.writeByte(65);
				stream.writeShort(this.modelSizeX);
			}
			
			if (this.modelSizeHeight > 0) {
				stream.writeByte(66);
				stream.writeShort(this.modelSizeHeight);
			}
			
			if (this.modelSizeY > 0) {
				stream.writeByte(67);
				stream.writeShort(this.modelSizeY);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stream.writeByte(0);
		byte[] var61 = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(var61, 0, var61.length);
		
		return var61;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[16].putFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8), encode());
	}
	
	@Override
	public String toString() {
		return this.id + " - " + this.name;
	}
	
	@Override
	public Cloneable clone() {
		return this;
	}
	
	public final void method3287() {
		if (this.secondInt == -1) {
			this.secondInt = 0;
			if (this.shapes != null && this.shapes.length == 1 && this.shapes[0] == 10) {
				this.secondInt = 1;
			}
			for (int i_13_ = 0; i_13_ < 5; ++i_13_) {
				if (this.options[i_13_] != null) {
					this.secondInt = 1;
					break;
				}
			}
		}
		if (this.anInt3855 == -1) {
			this.anInt3855 = this.clipType != 0 ? 1 : 0;
		}
	}

}
