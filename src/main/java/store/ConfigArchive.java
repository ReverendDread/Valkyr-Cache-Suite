/**
 * 
 */
package store;

import lombok.Getter;

/**
 * @author ReverendDread
 * Sep 26, 2018
 */
public enum ConfigArchive {
	
	UNDERLAYS(1),
	IDENTITY_KIT(3),
	OVERLAYS(4),
	INVENTORIES(5),
	PARAMS(11),
	VAR_CLIENT_STRINGS(15),
	VAR_PLAYER(16),
	AREAS(18),
	VAR_CLIENT(19),
	STRUCTS(26),
	SKYBOX(29),
	SUN_DEFINITION(30),
	LIGHT_INTENSITY(31),
	BAS(32),
	CURSORS(33),
	MAP_SCENE(34),
	QUESTS(35),
	HITMARK(46),
	VAR_CLAN(47),
	VAR_CLAN_SETTINGS(54);
	
	@Getter private int value;
	
	private ConfigArchive(int value) {
		this.value = value;
	}
	
}
