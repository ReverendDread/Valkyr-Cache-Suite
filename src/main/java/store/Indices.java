/**
 * 
 */
package store;

import lombok.Getter;

/**
 * @author ReverendDread
 * Sep 26, 2018
 */
public enum Indices {

	FRAMES(0),
	BASES(1),
	CONFIG(2),
	INTERFACE(3),
	SOUND_EFFECTS(4),
	MAPS(5),
	MUSIC(6),
	MODELS(7),
	SPRITES(8),
	TEXTURES_DIFFUSE(9),
	HUFFMAN(10),
	CLIENT_SCRIPT(12),
	FONT_METRICS(13),
	MIDI(14),
	OBJECT_DEFINITION(16),
	ENUMS(17),
	NPC_DEFINITION(18),
	ITEMS(19),
	SEQUENCES(20),
	SPOT_ANIM(21),
	VAR_BIT(22),
	WORLD_MAP(23),
	QUICK_CHAT_MESSAGES(24),
	QUICK_CHAT_MENUS(25),
	MATERIALS_RAW(26),
	PARTICLES(27),
	DEFAULTS(28),
	BILLBOARDS(29),
	NATIVES(30),
	SHADERS(31),
	LOADING_FONTS(32),
	GAME_TIPS(33),
	CUTSCENES(35);
	
	@Getter private int index;
	
	private Indices(int value) {
		this.index = value;
	}
	
}
