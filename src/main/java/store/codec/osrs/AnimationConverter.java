package store.codec.osrs;
///**
// * 
// */
//package com.alex.definition.impl.osrs;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import com.alex.store.FileStore;
//import com.alex.utils.Constants;
//
///**
// * @author ReverendDread
// * Sep 8, 2018
// */
//public class AnimationConverter {
//	
//	private static final int FRAME_OFFSET = 8000;
//	
//	/**
//	 * Converts animations from osrs format to 718 format, and encodes them into the 718 cache.
//	 */
//	public static void main(String[] args) throws IOException {
//		
//		FileStore cache = new FileStore("C:\\Users\\Andrew\\Desktop\\Mega-Sausage-Server\\data\\cache\\");
//		FileStore osrs = new FileStore("C:\\Users\\Andrew\\Desktop\\172\\");
//		
//		int id = 426;
//		
//		SequenceDefinition sequence = new SequenceDefinition(id, osrs.getIndexes()[2].getFile(12, id));
//		
//		int[] archives = sequence.frameArchives;
//		int[] files = sequence.frameFiles;
//		
//		for (int archive = 0; archive < archives.length; archive++) {
//			for (int file = 0; file < files.length; file++) {
//				byte[] data = osrs.getIndexes()[0].getFile(archives[archive], files[file]);
//				int baseID = (data[0] & 255) << 8 | data[1] & 255;
//				System.out.println("Reading frame " + archives[archive] + ", " + files[file] + " - Base: " + baseID);
//				FrameDefinition definition = new FrameDefinition(data, new BaseDefinition(baseID, osrs.getIndexes()[1].getFile(baseID, 0)));
//				int packedArchive = (archives[archive] + FRAME_OFFSET);
//				cache.getIndexes()[0].putFile(packedArchive, files[file], Constants.GZIP_COMPRESSION, definition.encode(), null, false, false, -1, -1);
//			}
//		}
//		cache.getIndexes()[0].rewriteTable();
//		
//		archives = sequence.baseArchives;
//		files = sequence.baseFiles;
//		
//		if (archives != null && files != null) {
//			for (int archive = 0; archive < archives.length; archive++) {
//				for (int file = 0; file < files.length; file++) {
//					byte[] data = osrs.getIndexes()[1].getFile(archives[archive], files[file]);
//					System.out.println("Reading base " + archives[archive] + ", " + files[file]);
//					BaseDefinition definition = new BaseDefinition(archives[archive], data);
//					int packedArchive = (archives[archive] + FRAME_OFFSET);
//					cache.getIndexes()[1].putFile(packedArchive, files[file], Constants.GZIP_COMPRESSION, definition.encode(), null, false, false, -1, -1);
//				}
//			}
//			cache.getIndexes()[1].rewriteTable();
//		}
//		
//	}
//	
//}
