package store.codec.osrs;
///**
// * 
// */
//package com.alex.definition.impl.osrs;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import com.alex.definition.impl.BASDefinition;
//import com.alex.store.FileStore;
//import com.alex.store.Indices;
//
///**
// * @author ReverendDread
// * Sep 18, 2018
// */
//public class RenderAnimCreator {
//	
//	public static void main(String[] args) throws IOException {
//		FileStore osrs = new FileStore("C:\\Users\\Andrew\\Desktop\\175\\");
//		FileStore cache = new FileStore("C:\\Users\\Andrew\\Desktop\\Mega-Sausage-Server\\data\\cache\\");
//		
//		int size = osrs.getIndexes()[2].getValidFilesCount(9);
//		
//		NPCDefinition osrsDef;
//		com.alex.definition.impl.NPCDefinition def;
//		BASDefinition bas;
//		
//		for (int index = 0; index < size; index++) {
//			byte[] data = osrs.getIndexes()[2].getFile(9, index);
//			
//			osrsDef = new NPCDefinition(data);
//			
//			def = new com.alex.definition.impl.NPCDefinition(index + 20_000);
//			def.name = osrsDef.name;
//			def.size = osrsDef.size;
//			def.modelIds = osrsDef.models;
//			def.options = osrsDef.options;
//			def.originalModelColors = osrsDef.recolorToFind;
//			def.modifiedModelColors = osrsDef.recolorToReplace;
//			def.originalTextureColors = osrsDef.retextureToFind;
//			def.modifiedTextureColors = osrsDef.retextureToReplace;
//			def.npcChatHeads = osrsDef.chatHeads;
//			def.invisibleOnMap = !osrsDef.invisable;
//			def.combatLevel = osrsDef.combatLevel;
//			def.npcWidth = osrsDef.resizeX;
//			def.npcHeight = osrsDef.resizeY;
//			def.renderPriority = osrsDef.renderPriority;
//			def.ambient = osrsDef.ambient;
//			def.contrast = osrsDef.contrast;
//			def.headIcon = osrsDef.headIcon;
//			def.anInt853 = osrsDef.anInt6181;
//			def.bConfig = osrsDef.bConfig;
//			def.config = osrsDef.config;
//			def.aBoolean852 = osrsDef.aBoolean6164;
//			def.aBoolean857 = osrsDef.aBoolean6165;
//			def.renderEmote = (index + 3000);
//			
//			def.save(cache);
//			System.out.println("Saved: " + def.toString());
//			
//			bas = new BASDefinition(index + 3000);
//			
//			if (osrsDef.idleAnimation > 0)
//				bas.idle = osrsDef.idleAnimation;
//			if (osrsDef.walkAnimation > 0)
//				bas.walking = osrsDef.walkAnimation;
//			if (osrsDef.anInt2165 > 0)
//				bas.running = osrsDef.anInt2165;
//			if (osrsDef.rotate90LeftAnimation > 0)
//				bas.strafe_left = osrsDef.rotate90LeftAnimation;
//			if (osrsDef.rotate90RightAnimation > 0)
//				bas.strafe_right = osrsDef.rotate90RightAnimation;
//			if (osrsDef.rotate180Animation > 0)
//				bas.backwards = osrsDef.rotate180Animation;
//			
//			bas.save(cache); 
//			System.out.println("Saved new BAS " + bas.id);
//		}
//		
//		cache.getIndexes()[Indices.NPC_DEFINITION.getValue()].rewriteTable();
//		cache.getIndexes()[Indices.CONFIG.getValue()].rewriteTable();
//		
//	}
//	
//}
