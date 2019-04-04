package store.codec;

import java.util.HashMap;
import java.util.Map;

import store.FileStore;
import store.Indices;
import store.codec.util.BitUtils;
import store.codec.util.Utils;
import store.io.InputStream;
import store.io.OutputStream;
import suite.Constants;

public class ItemDefinition implements AbstractDefinition, Cloneable {

	public int id;
	public boolean loaded;
	public int inventory_model;
	public String name;
	public int zoom;
	public int rotation_x;
	public int rotation_y;
	public int offset_x;
	public int offset_y;
	public int opcode3;
	public int opcode199;
	public int opcode223;
	public int opcode198;
	public int opcode186;
	public int opcode29;
	public int opcode238;
	public int opcode153;
	public int opcode155;
	public int opcode99;
	public int opcode251;
	public int opcode22;
	public int opcode192;
	public int opcode245;
	public int opcode45;
	public int opcode56;
	public int opcode248;
	public int opcode237;
	public int opcode243;
	public int opcode185;
	public int opcode221;
	public int opcode240;
	public int opcode154;
	public int opcode158;
	public int opcode137;
	public int opcode143;
	public int opcode61;
	public int opcode80;
	public int opcode196;
	public int opcode85;
	public int opcode239;
	public int opcode177;
	public int opcode163;
	public int opcode150;
	public int opcode152;
	public int opcode135;
	public int opcode120;
	public int opcode204;
	public int opcode81;
	public int opcode208;
	public int opcode242;
	public int opcode15;
	public int opcode233;
	public int opcode213;
	public int opcode207;
	public int opcode216;
	public int opcode206;
	public int opcode50;
	public int opcode193;
	public int opcode71;
	public int opcode10;
	public int opcode55;
	public int opcode144;
	public int opcode235;
	public int opcode188;
	public int opcode241;
	public int opcode236;
	public int opcode182;
	public int opcode169;
	public int opcode190;
	public int opcode178;
	public int opcode88;
	public int opcode200;
	public int opcode184;
	public int opcode176;
	public int opcode197;
	public int opcode247;
	public int opcode218;
	public int opcode250;
	public int opcode174;
	public int opcode210;
	public int opcode164;
	public int opcode142;
	public int opcode148;
	public int opcode133;
	public int opcode222;
	public int opcode138;
	public int opcode194;
	public int opcode119;
	public int opcode202;
	public int opcode149;
	public int opcode64;
	public int opcode147;
	public int opcode214;
	public int opcode74;
	public int opcode86;
	public int opcode167;
	public int opcode161;
	public int opcode58;
	public int opcode59;
	public int opcode187;
	public int opcode77;
	public int opcode229;
	public int opcode230;
	public int opcode17;
	public int opcode67;
	public int opcode131;
	public int opcode225;
	public int opcode203;
	public int opcode19;
	public int opcode43;
	public int opcode168;
	public int opcode46;
	public int opcode209;
	public int opcode166;
	public int opcode54;
	public int opcode21;
	public int opcode73;
	public int opcode159;
	public int opcode123;
	public int opcode146;
	public int opcode180;
	public int opcode20;
	public int opcode165;
	public int opcode84;
	public int opcode28;
	public int opcode175;
	public int opcode141;
	public int opcode205;
	public int opcode220;
	public int opcode136;
	public int opcode212;
	public int opcode49;
	public int opcode69;
	public int opcode72;
	public int opcode60;
	public int opcode62;
	public int opcode219;
	public int opcode44;
	public int opcode227;
	public int opcode76;
	public int opcode234;
	public int opcode57;
	public int opcode51;
	public int opcode124;
	public int opcode70;
	public int opcode231;
	public int opcode162;
	public int opcode160;
	public int opcode181;
	public int opcode183;
	public int opcode191;
	public int opcode189;
	public int opcode179;
	public int opcode173;
	public int opcode48;
	public int opcode172;
	public int opcode42;
	public int opcode47;
	public int opcode246;
	public int opcode89;
	public int opcode195;
	public int opcode145;
	public int opcode224;
	public int opcode63;
	public int opcode94;
	public int opcode201;
	public int opcode217;
	public int equipSlot;
	public int opcode52;
	public int opcode53;
	public int opcode82;
	public int opcode83;
	public int opcode87;
	public int opcode117;
	public int opcode66;
	public int opcode116;
	public int opcode157;
	public int opcode68;
	public int opcode244;
	public int opcode170;
	public int opcode151;
	public int opcode75;
	public int equipType;
	public int opcode27;
	public int opcode9;
	public int opcode232;
	public int opcode211;
	public int opcode254;
	public int opcode118;
	public int opcode228;
	public int opcode226;
	public int opcode255;
	public int opcode253;
	public int opcode252;
	public int opcode156;
	public int opcode215;
	public int opcode171;
	public int stackable;
	public int cost;
	public boolean members;
	public int maleModel0;
	public int femaleModel0;
	public int maleModel1;
	public int femaleModel1;
	public int maleModel2;
	public int femaleModel2;
	public String[] ground_options;
	public String[] inventory_options;
	public int[] colorsToFind;
	public int[] colorsToReplace;
	public short[] retexturesToFind;
	public short[] retexturesToReplace;
	public byte[] unknownArray1;
	public int[] unknownArray2;
	public int[] unknownArray4;
	public int[] unknownArray5;
	public byte[] unknownArray6;
	public byte[] unknownArray3;
	public boolean unnoted;
	public int maleHeadModel0;
	public int femaleHeadModel0;
	public int maleHeadModel2;
	public int femaleHeadModel2;
	public int rotation_z;
	public int unknownInt6;
	public int notedID;
	public int noteTemplate;
	public int[] stackIds;
	public int[] stackAmounts;
	public int resizeX;
	public int resizeY;
	public int resizeZ;
	public int ambient;
	public int contrast;
	public int team;
	public int switchLendItemId;
	public int lendedItemId;
	public int maleModelOffsetX;
	public int maleModelOffsetY;
	public int maleModelOffsetZ;
	public int femaleModelOffsetX;
	public int femaleModelOffsetY;
	public int femaleModelOffsetZ;
	public int unknownInt18;
	public int unknownInt19;
	public int unknownInt20;
	public int unknownInt21;
	public int unknownInt22;
	public int unknownInt23;
	public int unknownValue1;
	public int unknownValue2;
	public Map<Integer, Object> params = null;
	
	public ItemDefinition(int id) {
		this.id = id;
		this.name = "null";
		this.maleModel0 = -1;
		this.maleModel1 = -1;
		this.femaleModel0 = -1;
		this.femaleModel1 = -1;
		this.zoom = 2000;
		this.switchLendItemId = -1;
		this.lendedItemId = -1;
		this.notedID = -1;
		this.noteTemplate = -1;
		this.resizeZ = 128;
		this.cost = 1;
		this.maleModel2 = -1;
		this.femaleModel2 = -1;
		this.team = 0;
		this.equipType = -1;
		this.equipSlot = -1;
		this.ground_options = new String[] { null, null, "take", null, null };
		this.inventory_options = new String[] { null, null, null, null, "drop" };
	}
	
	@Override
	public void decode(InputStream buffer) {
		while(true) {
			
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
	
			if (opcode == 1) {
				this.inventory_model = buffer.readSmartInt();
			} else if (opcode == 2) {
				this.name = buffer.readString();
			} else if (opcode == 4) {
				this.zoom = buffer.readUnsignedShort();
			} else if (opcode == 5) {
				this.rotation_x = buffer.readUnsignedShort();
			} else if (opcode == 6) {
				this.rotation_y = buffer.readUnsignedShort();
			} else if (opcode == 7) {
				this.offset_x = buffer.readUnsignedShort();
				if (this.offset_x > 32767) {
					this.offset_x -= 65536;
				}
				this.offset_x <<= 0;
			} else if (opcode == 8) {
				this.offset_y = buffer.readUnsignedShort();
				if (this.offset_y > 32767) {
					this.offset_y -= 65536;
				}
				this.offset_y <<= 0;
			} else if (opcode == 11) {
				this.stackable = 1;
			} else if (opcode == 12) {
				this.cost = buffer.readInt();
			} else if (opcode == 13) {
				this.equipSlot = buffer.readUnsignedByte();
			} else if (opcode == 14) {
				this.equipType = buffer.readUnsignedByte();
			} else if (opcode == 16) {
				this.members = true;
			} else if (opcode == 18) {
				buffer.readUnsignedShortLE();
			} else if (opcode == 23) {
				this.maleModel0 = buffer.readSmartInt();
			} else if (opcode == 24) {
				this.maleModel1 = buffer.readSmartInt();
			} else if (opcode == 25) {
				this.femaleModel0 = buffer.readSmartInt();
			} else if (opcode == 26) {
				this.femaleModel1 = buffer.readSmartInt();
			} else if (opcode == 27) {
				buffer.readUnsignedByte();
			} else if (opcode >= 30 && opcode < 35) {
				this.ground_options[opcode - 30] = buffer.readString();
			} else if (opcode >= 35 && opcode < 40) {
				this.inventory_options[opcode - 35] = buffer.readString();
			} else {
				int length;
				int index;
				if (opcode == 40) {
					length = buffer.readUnsignedByte();
					this.colorsToFind = new int[length];
					this.colorsToReplace = new int[length];
					for (index = 0; index < length; ++index) {
						this.colorsToFind[index] = buffer.readUnsignedShort();
						this.colorsToReplace[index] = buffer.readUnsignedShort();
					}
				} else if (opcode == 41) {
					length = buffer.readUnsignedByte();
					this.retexturesToFind = new short[length];
					this.retexturesToReplace = new short[length];
					for (index = 0; index < length; ++index) {
						this.retexturesToFind[index] = (short) buffer.readUnsignedShort();
						this.retexturesToReplace[index] = (short) buffer.readUnsignedShort();
					}
				} else if (opcode == 42) {
					length = buffer.readUnsignedByte();
					this.unknownArray1 = new byte[length];
					for (index = 0; index < length; ++index) {
						this.unknownArray1[index] = (byte) buffer.readByte();
					}
				} else if (opcode == 65) {
					this.unnoted = true;
				} else if (opcode == 78) {
					this.maleModel2 = buffer.readSmartInt();
				} else if (opcode == 79) {
					this.femaleModel2 = buffer.readSmartInt();
				} else if (opcode == 90) {
					this.maleHeadModel0 = buffer.readSmartInt();
				} else if (opcode == 91) {
					this.femaleHeadModel0 = buffer.readSmartInt();
				} else if (opcode == 92) {
					this.maleHeadModel2 = buffer.readSmartInt();
				} else if (opcode == 93) {
					this.femaleHeadModel2 = buffer.readSmartInt();
				} else if (opcode == 95) {
					this.rotation_z = buffer.readUnsignedShort();
				} else if (opcode == 96) {
					this.unknownInt6 = buffer.readUnsignedByte();
				} else if (opcode == 97) {
					this.notedID = buffer.readUnsignedShort();
				} else if (opcode == 98) {
					this.noteTemplate = buffer.readUnsignedShort();
				} else if (opcode >= 100 && opcode < 110) {
					if (this.stackIds == null || this.stackAmounts == null) {
						this.stackIds = new int[10];
						this.stackAmounts = new int[10];
					}
					this.stackIds[opcode - 100] = buffer.readUnsignedShort();
					this.stackAmounts[opcode - 100] = buffer.readUnsignedShort();
				} else if (opcode == 110) {
					this.resizeX = buffer.readUnsignedShort();
				} else if (opcode == 111) {
					this.resizeY = buffer.readUnsignedShort();
				} else if (opcode == 112) {
					this.resizeZ = buffer.readUnsignedShort();
				} else if (opcode == 113) {
					this.ambient = buffer.readByte();
				} else if (opcode == 114) {
					this.contrast = buffer.readByte() * 5;
				} else if (opcode == 115) {
					this.team = buffer.readUnsignedByte();
				} else if (opcode == 121) {
					this.switchLendItemId = buffer.readUnsignedShort();
				} else if (opcode == 122) {
					this.lendedItemId = buffer.readUnsignedShort();
				} else if (opcode == 125) {
					this.maleModelOffsetX = buffer.readByte() << 0;
					this.maleModelOffsetY = buffer.readByte() << 0;
					this.maleModelOffsetZ = buffer.readByte() << 0;
				} else if (opcode == 126) {
					this.femaleModelOffsetX = buffer.readByte() << 0;
					this.femaleModelOffsetY = buffer.readByte() << 0;
					this.femaleModelOffsetZ = buffer.readByte() << 0;
				} else if (opcode == 127) {
					this.unknownInt18 = buffer.readUnsignedByte();
					this.unknownInt19 = buffer.readUnsignedShort();
				} else if (opcode == 128) {
					this.unknownInt20 = buffer.readUnsignedByte();
					this.unknownInt21 = buffer.readUnsignedShort();
				} else if (opcode == 129) {
					this.unknownInt20 = buffer.readUnsignedByte();
					this.unknownInt21 = buffer.readUnsignedShort();
				} else if (opcode == 130) {
					this.unknownInt22 = buffer.readUnsignedByte();
					this.unknownInt23 = buffer.readUnsignedShort();
				} else if (opcode == 132) {
					length = buffer.readUnsignedByte();
					this.unknownArray2 = new int[length];
					for (index = 0; index < length; ++index) {
						this.unknownArray2[index] = buffer.readUnsignedShort();
					}
				} else if (opcode == 134) {
					buffer.readUnsignedByte();
				} else if (opcode == 139) {
					this.unknownValue2 = buffer.readUnsignedShort();
				} else if (opcode == 140) {
					this.unknownValue1 = buffer.readUnsignedShort();
				} else if (opcode == 249) {
					length = buffer.readUnsignedByte();
						this.params = new HashMap<>(BitUtils.nextPowerOfTwo(length));
					for (index = 0; index < length; ++index) {
						boolean stringInstance = buffer.readUnsignedByte() == 1;
						int key = buffer.readMedium();
						Object value = stringInstance ? buffer.readString() : buffer.readInt();
						this.params.put(key, value);
					}
				} else if (opcode == 44) {
					this.opcode44 = buffer.readUnsignedShort();
				} else if (opcode == 117) {
					this.opcode117 = buffer.readUnsignedByte();
				} else if (opcode == 211) {
					this.opcode211 = buffer.readUnsignedByte();
				} else if (opcode == 255) {
					this.opcode255 = buffer.readUnsignedByte();
				} else if (opcode == 75) {
					this.opcode75 = buffer.readUnsignedByte();
				} else if (opcode == 87) {
					this.opcode87 = buffer.readUnsignedByte();
				} else if (opcode == 68) {
					this.opcode68 = buffer.readUnsignedByte();
				} else if (opcode == 118) {
					this.opcode118 = buffer.readUnsignedByte();
				} else if (opcode == 83) {
					this.opcode83 = buffer.readUnsignedByte();
				} else if (opcode == 254) {
					this.opcode254 = buffer.readUnsignedByte();
				} else if (opcode == 156) {
					this.opcode156 = buffer.readUnsignedByte();
				} else if (opcode == 232) {
					this.opcode232 = buffer.readUnsignedByte();
				} else if (opcode == 199) {
					this.opcode199 = buffer.readUnsignedByte();
				} else if (opcode == 253) {
					this.opcode253 = buffer.readUnsignedByte();
				} else if (opcode == 223) {
					this.opcode223 = buffer.readUnsignedByte();
				} else if (opcode == 198) {
					this.opcode198 = buffer.readUnsignedByte();
				} else if (opcode == 186) {
					this.opcode186 = buffer.readUnsignedByte();
				} else if (opcode == 29) {
					this.opcode29 = buffer.readUnsignedByte();
				} else if (opcode == 238) {
					this.opcode238 = buffer.readUnsignedByte();
				} else if (opcode == 153) {
					this.opcode153 = buffer.readUnsignedByte();
				} else if (opcode == 155) {
					this.opcode155 = buffer.readUnsignedByte();
				} else if (opcode == 99) {
					this.opcode99 = buffer.readUnsignedByte();
				} else if (opcode == 251) {
					this.opcode251 = buffer.readUnsignedByte();
				} else if (opcode == 22) {
					this.opcode22 = buffer.readUnsignedByte();
				} else if (opcode == 192) {
					this.opcode192 = buffer.readUnsignedByte();
				} else if (opcode == 245) {
					this.opcode245 = buffer.readUnsignedByte();
				} else if (opcode == 45) {
					this.opcode45 = buffer.readUnsignedByte();
				} else if (opcode == 56) {
					this.opcode56 = buffer.readUnsignedByte();
				} else if (opcode == 248) {
					this.opcode248 = buffer.readUnsignedByte();
				} else if (opcode == 237) {
					this.opcode237 = buffer.readUnsignedByte();
				} else if (opcode == 243) {
					this.opcode243 = buffer.readUnsignedByte();
				} else if (opcode == 185) {
					this.opcode185 = buffer.readUnsignedByte();
				} else if (opcode == 221) {
					this.opcode221 = buffer.readUnsignedByte();
				} else if (opcode == 240) {
					this.opcode240 = buffer.readUnsignedByte();
				} else if (opcode == 154) {
					this.opcode154 = buffer.readUnsignedByte();
				} else if (opcode == 158) {
					this.opcode158 = buffer.readUnsignedByte();
				} else if (opcode == 137) {
					this.opcode137 = buffer.readUnsignedByte();
				} else if (opcode == 143) {
					this.opcode143 = buffer.readUnsignedByte();
				} else if (opcode == 61) {
					this.opcode61 = buffer.readUnsignedByte();
				} else if (opcode == 80) {
					this.opcode80 = buffer.readUnsignedByte();
				} else if (opcode == 196) {
					this.opcode196 = buffer.readUnsignedByte();
				} else if (opcode == 85) {
					this.opcode85 = buffer.readUnsignedByte();
				} else if (opcode == 239) {
					this.opcode239 = buffer.readUnsignedByte();
				} else if (opcode == 177) {
					this.opcode177 = buffer.readUnsignedByte();
				} else if (opcode == 163) {
					this.opcode163 = buffer.readUnsignedByte();
				} else if (opcode == 150) {
					this.opcode150 = buffer.readUnsignedByte();
				} else if (opcode == 152) {
					this.opcode152 = buffer.readUnsignedByte();
				} else if (opcode == 135) {
					this.opcode135 = buffer.readUnsignedByte();
				} else if (opcode == 120) {
					this.opcode120 = buffer.readUnsignedByte();
				} else if (opcode == 204) {
					this.opcode204 = buffer.readUnsignedByte();
				} else if (opcode == 81) {
					this.opcode81 = buffer.readUnsignedByte();
				} else if (opcode == 208) {
					this.opcode208 = buffer.readUnsignedByte();
				} else if (opcode == 242) {
					this.opcode242 = buffer.readUnsignedByte();
				} else if (opcode == 15) {
					this.opcode15 = buffer.readUnsignedByte();
				} else if (opcode == 233) {
					this.opcode233 = buffer.readUnsignedByte();
				} else if (opcode == 213) {
					this.opcode213 = buffer.readUnsignedByte();
				} else if (opcode == 207) {
					this.opcode207 = buffer.readUnsignedByte();
				} else if (opcode == 216) {
					this.opcode216 = buffer.readUnsignedByte();
				} else if (opcode == 206) {
					this.opcode206 = buffer.readUnsignedByte();
				} else if (opcode == 50) {
					this.opcode50 = buffer.readUnsignedByte();
				} else if (opcode == 193) {
					this.opcode193 = buffer.readUnsignedByte();
				} else if (opcode == 71) {
					this.opcode71 = buffer.readUnsignedByte();
				} else if (opcode == 10) {
					this.opcode10 = buffer.readUnsignedByte();
				} else if (opcode == 55) {
					this.opcode55 = buffer.readUnsignedByte();
				} else if (opcode == 144) {
					this.opcode144 = buffer.readUnsignedByte();
				} else if (opcode == 235) {
					this.opcode235 = buffer.readUnsignedByte();
				} else if (opcode == 188) {
					this.opcode188 = buffer.readUnsignedByte();
				} else if (opcode == 241) {
					this.opcode241 = buffer.readUnsignedByte();
				} else if (opcode == 236) {
					this.opcode236 = buffer.readUnsignedByte();
				} else if (opcode == 182) {
					this.opcode182 = buffer.readUnsignedByte();
				} else if (opcode == 169) {
					this.opcode169 = buffer.readUnsignedByte();
				} else if (opcode == 190) {
					this.opcode190 = buffer.readUnsignedByte();
				} else if (opcode == 178) {
					this.opcode178 = buffer.readUnsignedByte();
				} else if (opcode == 88) {
					this.opcode88 = buffer.readUnsignedByte();
				} else if (opcode == 200) {
					this.opcode200 = buffer.readUnsignedByte();
				} else if (opcode == 184) {
					this.opcode184 = buffer.readUnsignedByte();
				} else if (opcode == 176) {
					this.opcode176 = buffer.readUnsignedByte();
				} else if (opcode == 197) {
					this.opcode197 = buffer.readUnsignedByte();
				} else if (opcode == 247) {
					this.opcode247 = buffer.readUnsignedByte();
				} else if (opcode == 218) {
					this.opcode218 = buffer.readUnsignedByte();
				} else if (opcode == 250) {
					this.opcode250 = buffer.readUnsignedByte();
				} else if (opcode == 174) {
					this.opcode174 = buffer.readUnsignedByte();
				} else if (opcode == 210) {
					this.opcode210 = buffer.readUnsignedByte();
				} else if (opcode == 164) {
					this.opcode164 = buffer.readUnsignedByte();
				} else if (opcode == 142) {
					this.opcode142 = buffer.readUnsignedByte();
				} else if (opcode == 148) {
					this.opcode148 = buffer.readUnsignedByte();
				} else if (opcode == 133) {
					this.opcode133 = buffer.readUnsignedByte();
				} else if (opcode == 222) {
					this.opcode222 = buffer.readUnsignedByte();
				} else if (opcode == 138) {
					this.opcode138 = buffer.readUnsignedByte();
				} else if (opcode == 194) {
					this.opcode194 = buffer.readUnsignedByte();
				} else if (opcode == 119) {
					this.opcode119 = buffer.readUnsignedByte();
				} else if (opcode == 202) {
					this.opcode202 = buffer.readUnsignedByte();
				} else if (opcode == 149) {
					this.opcode149 = buffer.readUnsignedByte();
				} else if (opcode == 64) {
					this.opcode64 = buffer.readUnsignedByte();
				} else if (opcode == 147) {
					this.opcode147 = buffer.readUnsignedByte();
				} else if (opcode == 214) {
					this.opcode214 = buffer.readUnsignedByte();
				} else if (opcode == 74) {
					this.opcode74 = buffer.readUnsignedByte();
				} else if (opcode == 86) {
					this.opcode86 = buffer.readUnsignedByte();
				} else if (opcode == 167) {
					this.opcode167 = buffer.readUnsignedByte();
				} else if (opcode == 161) {
					this.opcode161 = buffer.readUnsignedByte();
				} else if (opcode == 58) {
					this.opcode58 = buffer.readUnsignedByte();
				} else if (opcode == 59) {
					this.opcode59 = buffer.readUnsignedByte();
				} else if (opcode == 187) {
					this.opcode187 = buffer.readUnsignedByte();
				} else if (opcode == 77) {
					this.opcode77 = buffer.readUnsignedByte();
				} else if (opcode == 229) {
					this.opcode229 = buffer.readUnsignedByte();
				} else if (opcode == 230) {
					this.opcode230 = buffer.readUnsignedByte();
				} else if (opcode == 17) {
					this.opcode17 = buffer.readUnsignedByte();
				} else if (opcode == 67) {
					this.opcode67 = buffer.readUnsignedByte();
				} else if (opcode == 131) {
					this.opcode131 = buffer.readUnsignedByte();
				} else if (opcode == 225) {
					this.opcode225 = buffer.readUnsignedByte();
				} else if (opcode == 203) {
					this.opcode203 = buffer.readUnsignedByte();
				} else if (opcode == 19) {
					this.opcode19 = buffer.readUnsignedByte();
				} else if (opcode == 43) {
					this.opcode43 = buffer.readUnsignedByte();
				} else if (opcode == 168) {
					this.opcode168 = buffer.readUnsignedByte();
				} else if (opcode == 46) {
					this.opcode46 = buffer.readUnsignedByte();
				} else if (opcode == 209) {
					this.opcode209 = buffer.readUnsignedByte();
				} else if (opcode == 166) {
					this.opcode166 = buffer.readUnsignedByte();
				} else if (opcode == 54) {
					this.opcode54 = buffer.readUnsignedByte();
				} else if (opcode == 21) {
					this.opcode21 = buffer.readUnsignedByte();
				} else if (opcode == 73) {
					this.opcode73 = buffer.readUnsignedByte();
				} else if (opcode == 159) {
					this.opcode159 = buffer.readUnsignedByte();
				} else if (opcode == 123) {
					this.opcode123 = buffer.readUnsignedByte();
				} else if (opcode == 146) {
					this.opcode146 = buffer.readUnsignedByte();
				} else if (opcode == 180) {
					this.opcode180 = buffer.readUnsignedByte();
				} else if (opcode == 20) {
					this.opcode20 = buffer.readUnsignedByte();
				} else if (opcode == 165) {
					this.opcode165 = buffer.readUnsignedByte();
				} else if (opcode == 84) {
					this.opcode84 = buffer.readUnsignedByte();
				} else if (opcode == 28) {
					this.opcode28 = buffer.readUnsignedByte();
				} else if (opcode == 175) {
					this.opcode175 = buffer.readUnsignedByte();
				} else if (opcode == 141) {
					this.opcode141 = buffer.readUnsignedByte();
				} else if (opcode == 205) {
					this.opcode205 = buffer.readUnsignedByte();
				} else if (opcode == 220) {
					this.opcode220 = buffer.readUnsignedByte();
				} else if (opcode == 136) {
					this.opcode136 = buffer.readUnsignedByte();
				} else if (opcode == 212) {
					this.opcode212 = buffer.readUnsignedByte();
				} else if (opcode == 49) {
					this.opcode49 = buffer.readUnsignedByte();
				} else if (opcode == 69) {
					this.opcode69 = buffer.readUnsignedByte();
				} else if (opcode == 72) {
					this.opcode72 = buffer.readUnsignedByte();
				} else if (opcode == 60) {
					this.opcode60 = buffer.readUnsignedByte();
				} else if (opcode == 62) {
					this.opcode62 = buffer.readUnsignedByte();
				} else if (opcode == 219) {
					this.opcode219 = buffer.readUnsignedByte();
				} else if (opcode == 44) {
					this.opcode44 = buffer.readUnsignedByte();
				} else if (opcode == 227) {
					this.opcode227 = buffer.readUnsignedByte();
				} else if (opcode == 76) {
					this.opcode76 = buffer.readUnsignedByte();
				} else if (opcode == 234) {
					this.opcode234 = buffer.readUnsignedByte();
				} else if (opcode == 57) {
					this.opcode57 = buffer.readUnsignedByte();
				} else if (opcode == 51) {
					this.opcode51 = buffer.readUnsignedByte();
				} else if (opcode == 124) {
					this.opcode124 = buffer.readUnsignedByte();
				} else if (opcode == 70) {
					this.opcode70 = buffer.readUnsignedByte();
				} else if (opcode == 231) {
					this.opcode231 = buffer.readUnsignedByte();
				} else if (opcode == 162) {
					this.opcode162 = buffer.readUnsignedByte();
				} else if (opcode == 160) {
					this.opcode160 = buffer.readUnsignedByte();
				} else if (opcode == 181) {
					this.opcode181 = buffer.readUnsignedByte();
				} else if (opcode == 183) {
					this.opcode183 = buffer.readUnsignedByte();
				} else if (opcode == 191) {
					this.opcode191 = buffer.readUnsignedByte();
				} else if (opcode == 189) {
					this.opcode189 = buffer.readUnsignedByte();
				} else if (opcode == 179) {
					this.opcode179 = buffer.readUnsignedByte();
				} else if (opcode == 173) {
					this.opcode173 = buffer.readUnsignedByte();
				} else if (opcode == 48) {
					this.opcode48 = buffer.readUnsignedByte();
				} else if (opcode == 172) {
					this.opcode172 = buffer.readUnsignedByte();
				} else if (opcode == 42) {
					this.opcode42 = buffer.readUnsignedByte();
				} else if (opcode == 47) {
					this.opcode47 = buffer.readUnsignedByte();
				} else if (opcode == 246) {
					this.opcode246 = buffer.readUnsignedByte();
				} else if (opcode == 89) {
					this.opcode89 = buffer.readUnsignedByte();
				} else if (opcode == 195) {
					this.opcode195 = buffer.readUnsignedByte();
				} else if (opcode == 145) {
					this.opcode145 = buffer.readUnsignedByte();
				} else if (opcode == 224) {
					this.opcode224 = buffer.readUnsignedByte();
				} else if (opcode == 63) {
					this.opcode63 = buffer.readUnsignedByte();
				} else if (opcode == 94) {
					this.opcode94 = buffer.readUnsignedByte();
				} else if (opcode == 201) {
					this.opcode201 = buffer.readUnsignedByte();
				} else if (opcode == 217) {
					this.opcode217 = buffer.readUnsignedByte();
				} else if (opcode == 252) {
					this.opcode252 = buffer.readUnsignedByte();
				} else if (opcode == 228) {
					this.opcode228 = buffer.readUnsignedByte();
				} else if (opcode == 82) {
					this.opcode82 = buffer.readUnsignedByte();
				} else if (opcode == 9) {
					this.opcode9 = buffer.readUnsignedByte();
				} else if (opcode == 27) {
					this.opcode27 = buffer.readUnsignedByte();
				} else if (opcode == 66) {
					this.opcode66 = buffer.readUnsignedByte();
				} else if (opcode == 116) {
					this.opcode116 = buffer.readUnsignedByte();
				} else if (opcode == 157) {
					this.opcode157 = buffer.readUnsignedByte();
				} else if (opcode == 244) {
					this.opcode244 = buffer.readUnsignedByte();
				} else if (opcode == 53) {
					this.opcode53 = buffer.readUnsignedByte();
				} else if (opcode == 215) {
					this.opcode215 = buffer.readUnsignedByte();
				} else if (opcode == 171) {
					this.opcode171 = buffer.readUnsignedByte();
				} else if (opcode == 3) {
					this.opcode3 = buffer.readUnsignedByte();
				} else if (opcode == 170) {
					this.opcode170 = buffer.readUnsignedByte();
				} else if (opcode == 226) {
					this.opcode226 = buffer.readUnsignedByte();
				} else if (opcode == 52) {
					this.opcode52 = buffer.readUnsignedByte();
				} else {
					if (opcode != 151) {
						throw new RuntimeException("MISSING OPCODE " + opcode + " FOR ITEM " + this.id);
					}
					this.opcode151 = buffer.readUnsignedByte();
				}
			}
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		stream.writeByte(1);
		stream.writeSmartInt(this.inventory_model);
		
		if (!this.name.equals("null") && this.noteTemplate == -1) {
			stream.writeByte(2);
			stream.writeString(this.name);
		}

		if (this.zoom != 2000) {
			stream.writeByte(4);
			stream.writeShort(this.zoom);
		}

		if (this.rotation_x != 0) {
			stream.writeByte(5);
			stream.writeShort(this.rotation_x);
		}

		if (this.rotation_y != 0) {
			stream.writeByte(6);
			stream.writeShort(this.rotation_y);
		}

		int index;
		if (this.offset_x != 0) {
			stream.writeByte(7);
			index = this.offset_x >> 0;
			this.offset_x = index;
			int value = index;
			if (index < 0) {
				value = index + 65536;
			}
			stream.writeShort(value);
		}

		if (this.offset_y != 0) {
			stream.writeByte(8);
			index = this.offset_y >> 0;
			this.offset_y = index;
			int value = index;
			if (index < 0) {
				value = index + 65536;
			}

			stream.writeShort(value);
		}

		if (this.stackable >= 1 && this.noteTemplate == -1) {
			stream.writeByte(11);
		}

		if (this.cost != 1 && this.lendedItemId == -1) {
			stream.writeByte(12);
			stream.writeInt(this.cost);
		}

		if (this.equipSlot != -1) {
			stream.writeByte(13);
			stream.writeByte(this.equipSlot);
		}

		if (this.equipType != -1) {
			stream.writeByte(14);
			stream.writeByte(this.equipType);
		}

		if (this.members && this.noteTemplate == -1) {
			stream.writeByte(16);
		}

		if (this.maleModel0 != -1) {
			stream.writeByte(23);
			stream.writeSmartInt(this.maleModel0);
		}

		if (this.maleModel1 != -1) {
			stream.writeByte(24);
			stream.writeSmartInt(this.maleModel1);
		}

		if (this.femaleModel0 != -1) {
			stream.writeByte(25);
			stream.writeSmartInt(this.femaleModel0);
		}

		if (this.femaleModel1 != -1) {
			stream.writeByte(26);
			stream.writeSmartInt(this.femaleModel1);
		}

		for (index = 0; index < 4; ++index) {
			if (this.ground_options[index] != null && (index != 2 || !this.ground_options[index].equals("take"))) {
				stream.writeByte(30 + index);
				stream.writeString(this.ground_options[index]);
			}
		}

		for (index = 0; index < 4; ++index) {
			if (this.inventory_options[index] != null && (index != 4 || !this.inventory_options[index].equals("drop"))) {
				stream.writeByte(35 + index);
				stream.writeString(this.inventory_options[index]);
			}
		}

		if (this.colorsToFind != null && this.colorsToReplace != null) {
			stream.writeByte(40);
			stream.writeByte(this.colorsToFind.length);
			for (index = 0; index < this.colorsToFind.length; ++index) {
				stream.writeShort(this.colorsToFind[index]);
				stream.writeShort(this.colorsToReplace[index]);
			}
		}

		if (this.retexturesToFind != null && this.retexturesToReplace != null) {
			stream.writeByte(41);
			stream.writeByte(this.retexturesToFind.length);
			for (index = 0; index < this.retexturesToFind.length; ++index) {
				stream.writeShort(this.retexturesToFind[index]);
				stream.writeShort(this.retexturesToReplace[index]);
			}
		}

		if (this.unknownArray1 != null) {
			stream.writeByte(42);
			stream.writeByte(this.unknownArray1.length);
			for (index = 0; index < this.unknownArray1.length; ++index) {
				stream.writeByte(this.unknownArray1[index]);
			}
		}

		if (this.unnoted) {
			stream.writeByte(65);
		}

		if (this.maleModel2 != -1) {
			stream.writeByte(78);
			stream.writeSmartInt(this.maleModel2);
		}

		if (this.femaleModel2 != -1) {
			stream.writeByte(79);
			stream.writeSmartInt(this.femaleModel2);
		}

		if (this.notedID != -1) {
			stream.writeByte(97);
			stream.writeShort(this.notedID);
		}

		if (this.noteTemplate != -1) {
			stream.writeByte(98);
			stream.writeShort(this.noteTemplate);
		}

		if (this.stackIds != null && this.stackAmounts != null) {
			for (index = 0; index < this.stackIds.length; ++index) {
				stream.writeByte(100 + index);
				stream.writeShort(this.stackIds[index]);
				stream.writeShort(this.stackAmounts[index]);		
			}
		}

		if (this.resizeX != 0) {
			stream.writeByte(110);
			stream.writeShort(resizeX);
		}
		
		if (this.resizeY != 0) {
			stream.writeByte(111);
			stream.writeShort(resizeY);
		}
		
		if (this.resizeZ != 128) {
			stream.writeByte(112);
			stream.writeShort(resizeZ);
		}
		
		if (this.team != 0) {
			stream.writeByte(115);
			stream.writeByte(this.team);
		}

		if (this.switchLendItemId != -1) {
			stream.writeByte(121);
			stream.writeShort(this.switchLendItemId);
		}

		if (this.lendedItemId != -1) {
			stream.writeByte(122);
			stream.writeShort(this.lendedItemId);
		}
		
		if (this.maleModelOffsetX != 0 || this.maleModelOffsetY != 0 || this.maleModelOffsetZ != 0) {
			stream.writeByte(125);
			stream.writeByte(maleModelOffsetX);
			stream.writeByte(maleModelOffsetY);
			stream.writeByte(maleModelOffsetZ);
		}
		
		if (this.femaleModelOffsetX != 0 || this.femaleModelOffsetY != 0 || this.femaleModelOffsetZ != 0) {
			stream.writeByte(126);
			stream.writeByte(femaleModelOffsetX);
			stream.writeByte(femaleModelOffsetY);
			stream.writeByte(femaleModelOffsetZ);
		}
		
		if (this.unknownInt18 != 0 || unknownInt19 != 0) {
			stream.writeByte(127);
			stream.writeByte(unknownInt18);
			stream.writeShort(unknownInt19);
		}
		
		if (this.unknownInt20 != 0 || unknownInt21 != 0) {
			stream.writeByte(128);
			stream.writeByte(unknownInt18);
			stream.writeShort(unknownInt19);
			stream.writeByte(129);
			stream.writeByte(unknownInt18);
			stream.writeShort(unknownInt19);
		}
		
		if (this.unknownInt22 != 0 || unknownInt23 != 0) {
			stream.writeByte(129);
			stream.writeByte(unknownInt22);
			stream.writeShort(unknownInt23);
		}

		if (this.unknownArray2 != null) {
			stream.writeByte(132);
			stream.writeByte(this.unknownArray2.length);
			for (index = 0; index < this.unknownArray2.length; ++index) {
				stream.writeShort(this.unknownArray2[index]);
			}
		}
		
		if (unknownValue2 != 0) {
			stream.writeByte(139);
			stream.writeShort(unknownValue2);
		}
		
		if (unknownValue1 != 0) {
			stream.writeByte(140);
			stream.writeShort(unknownValue1);
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
		byte[] var6 = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(var6, 0, var6.length);
		return var6;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.ITEMS.getIndex()].putFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8), encode());
	}
	
	@Override
	public String toString() {
		return this.id + " - " + this.name + (Constants.settings.debug ? "(" + (this.noteTemplate > 0 ? "Noted" : "Unnoted") + ")" : "");
	}
	
	@Override
	public Cloneable clone() {
		return this;
	}
	
	public void applyEquipData() {
		String name = this.name.toLowerCase();
		
		if (name.contains("chestplate") || name.contains("body")
				|| name.contains("brassard") || name.contains("top") 
					|| name.contains("jacket") || name.contains("shirt")
						|| name.contains("apron") || name.contains("coat")
							|| name.contains("blouse") || name.contains("hauberk") 
								|| name.contains("chestguard") || name.contains("torso") 
									|| name.contains("garb") || name.contains("tunic")
										|| name.contains("armour")) {
			equipSlot = 4;
			equipType = 6;
		}
			
		if (name.contains("legs") || name.contains("skirt")
				|| name.contains("robe bottom") || name.contains("chaps") || name.contains("chainskirt")
					|| name.contains("leggings") || name.contains("tassets") 
						|| name.contains("slacks") || name.contains("bottoms")
							|| name.contains("trousers") || name.contains("greaves")
								|| name.contains("Shorts")) {			
			equipSlot = 7;
		}
		
		if (name.contains("mask") || name.contains("helm")
				|| name.contains("hat") || name.contains("hood") 
					|| name.contains("coif") || name.contains("mitre")
						|| name.contains("eyepatch") || name.contains("mask") 
							|| name.contains("boater") || name.contains("beret")
								|| name.contains("snelm") || name.contains("tiara")
									|| name.contains(" ears") || name.contains("head") 
										|| name.contains("cavalier") || name.contains("wreath") 
											|| name.contains("fedora") || name.contains("fez") 
												|| name.contains("headband") || name.contains("headgear")
													|| name.contains("faceguard")) {
			if (name.contains("mask"))
				equipType = 8;
			equipSlot = 0;
		}
		
		if (name.contains("ring")) {
			equipSlot = 12;
		}
		
		if (name.contains("amulet") || name.contains("necklace") 
				|| name.contains("pendant") || name.contains("scarf") 
					|| name.contains("stole") || name.contains("symbol")) {
			equipSlot = 2;
		}
		
		if (name.contains("boots") || name.contains("feet") 
				|| name.contains("shoes") || name.contains("sandals")) {
			equipSlot = 10;
		}
		
		if (name.contains("gloves") || name.contains("vamb") 
				|| name.contains("bracelet") || name.contains("bracers") 
					|| name.contains("gauntlets") || name.contains("cuffs") 
						|| name.contains("hands") || name.contains("armband")) {
			equipSlot = 9;
		}
		
		if (name.contains("cape") || name.contains("cloak") || name.contains("ava's")) {
			equipSlot = 1;
		}
		
		if (name.contains("shield") || name.contains("defender") 
				|| name.contains("book") || name.contains(" ward") 
					|| name.equalsIgnoreCase("tome of fire") || name.equalsIgnoreCase("toktz-ket-xil")
						|| name.contains("satchel")) {
			equipSlot = 5;
		}
		
		if (name.contains("arrow") || name.contains("bolts") 
				|| name.contains("brutal") || name.contains("arrows") 
					|| name.contains("tar") || name.contains(" blessing") 
						|| name.contains("javelin") || name.contains("grapple")) {
			equipSlot = 13;
		}
		
		if (name.contains("sword") || name.contains("whip") 
				|| name.contains("halbard") || name.contains("claws") 
					|| name.contains("spear") || name.contains("anchor") 
						|| name.contains("dagger") || name.contains("bow")
							|| name.contains("bulwark") || name.contains("axe") 
								|| name.contains("maul") || name.contains("ballista")
									|| name.contains("club") || name.contains("katana")
										|| name.contains("scythe") || name.contains("staff") 
											|| name.contains("wand") || name.contains("lizard") 
												|| name.contains("salamander") || name.contains("flail") 
													|| name.contains("mjolnir") || name.contains("mace") 
														|| name.contains("hammer") || name.contains("arclight")
															|| name.contains("crozier") || name.contains("banner")
																|| name.contains("dart") || name.contains("cane") 
																	|| name.contains("chinchompa") || name.contains("knife")
																		|| name.contains("scimitar") || name.contains("hasta")
																			|| name.contains("rapier") || name.contains("sickle") 
																				|| name.contains("greegree") || name.contains("machete")
																					|| name.contains("blackjack") || name.contains("blowpipe")
																						|| name.contains("trident") || name.contains("2h") || name.contains("halbard") 
																						|| name.contains("spear") || name.contains("bulwark") 
																						|| name.contains("maul") || name.contains(" ballista") 
																							|| name.contains("salamander") || name.contains("lizard") 
																								|| (name.contains("bow") && (!name.contains("cross") && !name.contains("karil's"))) 
																									|| name.contains("club") || name.contains("claws") 
																										|| name.contains("blowpipe") || name.contains("anchor") 
																											|| name.contains("greataxe") || name.contains("godsword")) {
			equipSlot = 3;		
			if (name.contains("2h") || name.contains("halbard") 
					|| name.contains("spear") || name.contains("bulwark") 
						|| name.contains("maul") || name.contains(" ballista") 
							|| name.contains("salamander") || name.contains("lizard") 
								|| (name.contains("bow") && (!name.contains("cross") && !name.contains("karil's"))) 
									|| name.contains("club") || name.contains("claws") 
										|| name.contains("blowpipe") || name.contains("anchor") 
											|| name.contains("greataxe") || name.contains("godsword")) {
				equipType = 5;
			}
			
		}
	}

}
