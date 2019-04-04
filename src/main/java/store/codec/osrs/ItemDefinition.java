/**
 * 
 */
package store.codec.osrs;

import store.FileStore;
import store.codec.AbstractDefinition;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Sep 30, 2018
 */
public class ItemDefinition implements AbstractDefinition {

	public int inventory_model = -1;
	public String name = "null";
	public int zoom = 2000;
	public int rotation_x;
	public int rotation_y;
	public int offset_x;
	public int offset_y;
	public int stackable;
	public int cost = 1;
	public boolean members;
	public int maleModel0 = -1;
	public int maleOffset;
	public int maleModel1 = -1;
	public int femaleModel0 = -1;
	public int femaleOffset;
	public int femaleModel1 = -1;
	public String[] ground_options = new String[5];
	public String[] inventory_options = new String[5];
	public int[] colorsToFind;
	public int[] colorsToReplace;
	public short[] retexturesToFind;
	public short[] retexturesToReplace;
	public int maleModel2 = -1;
	public int femaleModel2 = -1;
	public int maleHeadModel;
	public int femaleHeadModel;
	public int maleHeadModel2;
	public int femaleHeadModel2;
	public int rotation_z;
	public int notedID;
	public int noteTemplate;
	public int[] stackIds;
	public int[] stackAmounts;
	public int resizeX;
	public int resizeY;
	public int resizeZ = 128;
	public int ambient;
	public int contrast;
	public int team = 0;
	public int id;
	public int equipType = -1;
	public int equipSlot = -1;
	
	public ItemDefinition(int id, InputStream buffer) {
		this.id = id;
		decode(buffer);
		applyEquipData();
	}
	
	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream buffer) {
		while (true) {		
			int opcode = buffer.readUnsignedByte();			
			if (opcode == 0)
				break;
			if (opcode == 1) {
				inventory_model = buffer.readUnsignedShort();
			} else if (opcode == 2) {
				name = buffer.readString();
			} else if (opcode == 4) {
				zoom = buffer.readUnsignedShort();
			} else if (opcode == 5) {
				rotation_x = buffer.readUnsignedShort();
			} else if (opcode == 6) {
				rotation_y = buffer.readUnsignedShort();
			} else if (opcode == 7) {
				offset_x = buffer.readUnsignedShort();
				if (offset_x > 32767) {
					offset_x -= 65536;
				}
			} else if (opcode == 8) {
				offset_y = buffer.readUnsignedShort();
				if (offset_y > 32767) {
					offset_y -= 65536;
				}
			} else if (opcode == 11) {
				stackable = 1;
			} else if (opcode == 12) {
				cost = buffer.readInt();
			} else if (opcode == 16) {
				members = true;
			} else if (opcode == 23) {
				maleModel0 = buffer.readUnsignedShort();
				maleOffset = buffer.readUnsignedByte();
			} else if (opcode == 24) {
				maleModel1 = buffer.readUnsignedShort();
			} else if (opcode == 25) {
				femaleModel0 = buffer.readUnsignedShort();
				femaleOffset = buffer.readUnsignedByte();
			} else if (opcode == 26) {
				femaleModel1 = buffer.readUnsignedByte();
			} else if (opcode >= 30 && opcode < 35) {
				ground_options[opcode - 30] = buffer.readString();
				if (ground_options[opcode - 30].equalsIgnoreCase("hidden")) {
					ground_options[opcode - 30] = null;
				}
			} else if (opcode >= 35 && opcode < 40) {
				inventory_options[opcode - 35] = buffer.readString();
			} else if (opcode == 40) {
				int length = buffer.readUnsignedByte();
				colorsToFind = new int[length];
				colorsToReplace = new int[length];
				for (int index = 0; index < length; index++) {
					colorsToFind[index] = (short) buffer.readUnsignedShort();
					colorsToReplace[index] = (short) buffer.readUnsignedShort();
				}
			} else if (opcode == 41) {
				int length = buffer.readUnsignedByte();
				retexturesToFind = new short[length];
				retexturesToReplace = new short[length];
				for (int index = 0; index < length; index++) {
					retexturesToFind[index] = (short) buffer.readUnsignedShort();
					retexturesToReplace[index] = (short) buffer.readUnsignedShort();
				}
			} else if (opcode == 78) {
				maleModel2 = buffer.readUnsignedShort();
			} else if (opcode == 79) {
				femaleModel2 = buffer.readUnsignedShort();
			} else if (opcode == 90) {
				maleHeadModel = buffer.readUnsignedShort();
			} else if (opcode == 91) {
				femaleHeadModel = buffer.readUnsignedShort();
			} else if (opcode == 92) {
				maleHeadModel2 = buffer.readUnsignedShort();
			} else if (opcode == 93) {
				femaleHeadModel2 = buffer.readUnsignedShort();
			} else if (opcode == 95) {
				rotation_z = buffer.readUnsignedShort();
			} else if (opcode == 97) {
				notedID = buffer.readUnsignedShort();
			} else if (opcode == 98) {
				noteTemplate = buffer.readUnsignedShort();
			} else if (opcode >= 100 && opcode < 110) {
				if (stackIds == null) {
					stackIds = new int[10];
					stackAmounts = new int[10];
				}
				stackIds[opcode - 100] = buffer.readUnsignedShort();
				stackAmounts[opcode - 100] = buffer.readUnsignedShort();
			} else if (opcode == 110) {
				resizeX = buffer.readUnsignedShort();
			} else if (opcode == 111) {
				resizeY = buffer.readUnsignedShort();
			} else if (opcode == 112) {
				resizeZ = buffer.readUnsignedShort();
			} else if (opcode == 113) {
				ambient = buffer.readUnsignedByte();
			} else if (opcode == 114) {
				contrast = buffer.readUnsignedByte();
			} else if (opcode == 115) {
				team = buffer.readUnsignedByte();
			}
 		}
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#encode()
	 */
	@Override
	public byte[] encode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#save(com.alex.store.FileStore)
	 */
	@Override
	public boolean save(FileStore cache) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public store.codec.ItemDefinition to718Item(int offset) {
		store.codec.ItemDefinition definition = new store.codec.ItemDefinition(this.id);
		definition.name = this.name;
		definition.inventory_model = this.inventory_model;
		definition.maleModel0 = this.maleModel0;
		definition.maleModel1 = this.maleModel1;
		definition.maleModel2 = this.maleModel2;
		definition.femaleModel0 = this.femaleModel0;
		definition.femaleModel1 = this.femaleModel1;
		definition.femaleModel2 = this.femaleModel2;
		definition.inventory_options = this.inventory_options;
		definition.ground_options = this.ground_options;
		definition.notedID = this.notedID + (offset);
		definition.noteTemplate = this.noteTemplate + (offset);
		definition.colorsToFind = this.colorsToFind;
		definition.colorsToReplace = this.colorsToReplace;
		definition.retexturesToFind = this.retexturesToFind;
		definition.retexturesToReplace = this.retexturesToReplace;
		definition.zoom = this.zoom;
		definition.offset_x = this.offset_x;
		definition.offset_y = this.offset_y;
		definition.rotation_x = this.rotation_x;
		definition.rotation_y = this.rotation_y;
		definition.stackIds = this.stackIds;
		definition.stackAmounts = this.stackAmounts;
		definition.stackable = this.stackable;
		definition.team = this.team;
		definition.cost = this.cost;
		definition.maleHeadModel0 = this.maleHeadModel;
		definition.femaleHeadModel0 = this.femaleHeadModel;
		definition.maleHeadModel2 = this.maleHeadModel2;
		definition.femaleHeadModel2 = this.femaleHeadModel2;
		definition.rotation_z = this.rotation_z;
		definition.resizeX = this.resizeX;
		definition.resizeY = this.resizeY;
		definition.resizeZ = this.resizeZ;
		definition.members = this.members;
		definition.equipSlot = this.equipSlot;
		definition.equipType = this.equipType;
		if (this.notedID == -1) {
			definition.unnoted = true;
		}
		return definition;
	}
	
	public void applyEquipData() {
		String name = this.name.toLowerCase();
		
		if (name.contains(" chestplate") || name.contains("body") 
				|| name.contains(" brassard") || name.contains(" top") 
					|| name.contains(" jacket") || name.contains(" shirt")
						|| name.contains(" apron") || name.contains(" coat")
							|| name.contains(" blouse") || name.contains(" hauberk") 
								|| name.contains(" chestguard") || name.contains(" torso") 
									|| name.contains(" garb") || name.contains(" tunic")
										|| name.contains(" armour")) {
			equipSlot = 4;
			equipType = 6;
		}
			
		if (name.contains("legs") || name.contains("skirt") 
				|| name.contains("robe bottom") || name.contains(" chaps")
					|| name.contains(" leggings") || name.contains(" tassets") 
						|| name.contains("slacks") || name.contains(" bottoms")
							|| name.contains(" trousers") || name.contains(" greaves")
								|| name.contains("Shorts")) {			
			equipSlot = 7;
		}
		
		if (name.contains("mask") || name.contains("helm") 
				|| name.contains("hat") || name.contains(" hood") 
					|| name.contains("coif") || name.contains("mitre")
						|| name.contains("eyepatch") || name.contains("mask") 
							|| name.contains(" boater") || name.contains(" beret")
								|| name.contains(" snelm") || name.contains(" tiara")
									|| name.contains(" ears") || name.contains(" head") 
										|| name.contains(" cavalier") || name.contains(" wreath") 
											|| name.contains("fedora") || name.contains("fez") 
												|| name.contains(" headband") || name.contains(" headgear")
													|| name.contains(" faceguard")) {
			if (name.contains("mask"))
				equipType = 8;
			equipSlot = 0;
		}
		
		if (name.contains("ring")) {
			equipSlot = 12;
		}
		
		if (name.contains("amulet") || name.contains("necklace") 
				|| name.contains("pendant") || name.contains("scarf") 
					|| name.contains("stole") || name.contains(" symbol")) {
			equipSlot = 2;
		}
		
		if (name.contains("boots") || name.contains("feet") 
				|| name.contains("shoes") || name.contains("sandals")) {
			equipSlot = 10;
		}
		
		if (name.contains("gloves") || name.contains(" vamb") 
				|| name.contains("bracelet") || name.contains("bracers") 
					|| name.contains("gauntlets") || name.contains(" cuffs") 
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
		
		if (name.contains(" arrow") || name.contains(" bolts") 
				|| name.contains(" brutal") || name.contains(" arrows") 
					|| name.contains(" tar") || name.contains(" blessing") 
						|| name.contains(" javelin") || name.contains(" grapple")) {
			equipSlot = 13;
		}
		
		if (name.contains("sword") || name.contains("whip") 
				|| name.contains("halbard") || name.contains("claws") 
					|| name.contains("spear") || name.contains("anchor") 
						|| name.contains("dagger") || name.contains("bow")
							|| name.contains("bulwark") || name.contains(" axe") 
								|| name.contains(" maul") || name.contains(" ballista")
									|| name.contains(" club") || name.contains("katana")
										|| name.contains("scythe") || name.contains("staff") 
											|| name.contains("wand") || name.contains("lizard") 
												|| name.contains("salamander") || name.contains("flail") 
													|| name.contains("mjolnir") || name.contains("mace") 
														|| name.contains("hammer") || name.contains("arclight")
															|| name.contains("crozier") || name.contains("banner ")
																|| name.contains("dart") || name.contains("cane") 
																	|| name.contains("chinchompa") || name.contains("knife")
																		|| name.contains("scimitar") || name.contains("hasta")
																			|| name.contains("rapier") || name.contains("sickle") 
																				|| name.contains("greegree") || name.contains("machete")
																					|| name.contains("blackjack") || name.contains("blowpipe")
																						|| name.contains("trident")) {
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
