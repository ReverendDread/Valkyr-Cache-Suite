package misc;
//package com.utility;
//
//import java.awt.Color;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.file.Files;
//import java.nio.file.NoSuchFileException;
//import java.nio.file.Paths;
//import java.text.DecimalFormat;
//import java.util.Arrays;
//
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//
//import com.alex.definition.impl.ItemDefinition;
//import com.alex.definition.impl.ObjectDefinition;
//import com.alex.definition.impl.SpotDefinition;
//import com.alex.definition.impl.osrs.SequenceDefinition;
//import com.alex.io.InputStream;
//import com.alex.loaders.animations.SequenceDefinitions;
//import com.alex.loaders.impl.ItemLoader;
//import com.alex.loaders.map.OverlayDefinition;
//import com.alex.loaders.material.MaterialLoader;
//import com.alex.loaders.material.MaterialRaw;
//import com.alex.loaders.model.Mesh;
//import com.alex.store.Index;
//import com.alex.store.Indices;
//import com.alex.store.ReferenceTable;
//import com.alex.store.Archive;
//import com.alex.store.ConfigArchive;
//import com.alex.store.FileStore;
//import com.alex.utils.Constants;
//import com.alex.utils.Utils;
//import com.animations.SpotAnimations;
//
///**
// * @author ReverendDread
// * Jan 2, 2018
// */
//@SuppressWarnings("unused")
//public class OSRSPacker {
//	
//	private static final DecimalFormat format = new DecimalFormat("#.##");
//	
//	public static final String CACHE_DIR = "F:\\Mega-Sausage-Server\\data\\cache\\";
//	private static final String OSRS_CACHE = "C:\\Users\\Andrew\\Desktop\\rs685_cache\\"; //"C:\\Users\\Andrew\\Desktop\\177\\";
//	private static final String OSRS_MAPS = "C:\\Users\\Andrew\\Desktop\\177\\";
//	private static final String BASES_DIR = "C:\\Users\\Andrew\\Desktop\\dumps\\bases\\";
//	private static final String FRAMES_DIR = "C:\\Users\\Andrew\\Desktop\\dumps\\frames\\";
//	private static final String SEQS_DIR = "C:\\Users\\Andrew\\Desktop\\seqs\\";
//	private static final String NORMAL_CACHE = "F:\\Jankscape Server V1\\data\\cache\\";
//	
//	private static final int OVERLAY_OFFSET = 1000;
//	private static final int UNDERLAY_OFFSET = 1000;
//	private static final int MODEL_OFFSET = 100_000;
//	private static final int OBJECT_OFFSET = 100_000;
//	private static final int FRAMES_OFFSET = 8000;
//	private static final int BASE_OFFSET = 8000;
//	private static final int SPOTS_OFFSET = 5000;
//	private static final int SEQS_OFFSET = 20000;
//
//	public static void main(String[] args) throws IOException {	
//		FileStore cache = new FileStore(CACHE_DIR);
//		FileStore osrs_cache = new FileStore(NORMAL_CACHE);
//		//pack_osrs_dat(cache, osrs_cache);
//		pack_osrs_items(osrs_cache, cache);	
////		transfer_flos(osrs_cache, cache);
////		transfer_flus(osrs_cache, cache);
////		transport_index(osrs_cache, Indices.OBJECT_DEFINITION.getIndex(), cache, Indices.OBJECT_DEFINITION.getIndex());
////		transport_index(osrs_cache, 7, cache, 7);
////		transport_index(osrs_cache, 5, cache, 5);
////		transport_index(osrs_cache, Indices.NPC_DEFINITION.getIndex(), cache, Indices.NPC_DEFINITION.getIndex());
////		pack_osrs_items(osrs_cache, cache);
////		transport_archive(osrs_cache, 2, cache, 2, 36);
////		transport_index(osrs_cache, 23, cache, 23);	
////		XTEASManager.init();
////		
////		int regionId = 11575;
////		int regionX = (regionId >> 8) * 64;
////		int regionY = (regionId & 0xff) * 64;
////		int mapArchiveId = osrs_cache.getIndexes()[Indices.MAPS.getIndex()].getArchiveId("m" + ((regionX >> 3) / 8) + "_" + ((regionY >> 3) / 8));
////		int landscapeArchiveId = osrs_cache.getIndexes()[Indices.MAPS.getIndex()].getArchiveId("l" + ((regionX >> 3) / 8) + "_" + ((regionY >> 3) / 8));
////		int underwaterArchiveId = osrs_cache.getIndexes()[Indices.MAPS.getIndex()].getArchiveId("u" + ((regionX >> 3) / 8) + "_" + ((regionY >> 3) / 8));
////				
////		if (cache.getIndexes()[Indices.MAPS.getIndex()].putArchive(mapArchiveId, osrs_cache))
////			System.out.println("Packed map for region " + mapArchiveId);
////		
////		if (cache.getIndexes()[Indices.MAPS.getIndex()].putArchive(landscapeArchiveId, osrs_cache))
////			System.out.println("Packed landscape for region " + landscapeArchiveId);
////		
////		if (cache.getIndexes()[Indices.MAPS.getIndex()].putArchive(underwaterArchiveId, osrs_cache))
////			System.out.println("Packed underwater map for region " + landscapeArchiveId);
//		
//	}
//	
//	public static void pack_osrs_items(FileStore cache, FileStore destination) {
//		System.out.println("Starting packing.");
//		int size = Utils.getItemDefinitionsSize(cache);
//		int packed = 0;
//		for (int index = 0; index < 24450; index++) {			
//			byte[] data = destination.getIndexes()[Indices.ITEMS.getIndex()].getFile(Utils.getConfigArchive(index, 8), Utils.getConfigFile(index, 8));		
//			if (data == null) {
//				System.out.println("No old data for " + index + ".");
//				continue;
//			}
//			
//			ItemDefinition definition = new ItemDefinition(index);
//			definition.decode(new InputStream(data));
//			
//			data = cache.getIndexes()[Indices.ITEMS.getIndex()].getFile(Utils.getConfigArchive(index, 8), Utils.getConfigFile(index, 8));
//			if (data == null) {
//				System.out.println("No new data for " + index + ".");
//				continue;
//			}
//			
//			ItemDefinition new_definition = new ItemDefinition(index);
//			new_definition.decode(new InputStream(data));
//			
//			definition.equipSlot = new_definition.equipSlot;
//			definition.equipType = new_definition.equipType;
//			
//			destination.getIndexes()[Indices.ITEMS.getIndex()].putFile(Utils.getConfigArchive(index, 8), Utils.getConfigFile(index, 8), 
//					Constants.GZIP_COMPRESSION, definition.encode(), null, false, false, -1, -1);
//			System.out.println("Repacked item " + index + " with new configs.");
//			packed++;
//		}
//		System.out.println("Repacked " + packed + " new item configs.");
//		System.out.println("Rewritting reference table...");
//		destination.getIndexes()[Indices.ITEMS.getIndex()].rewriteTable();
//		System.out.println("Done.");
//	}
//	
//	public static void pack_osrs_dat(FileStore cache, FileStore osrs_cache) throws IOException {
//		
//		XTEASManager.init(); //Init the xteas manager	
//
////		int toRegionId = 14377; //home location
////		int toRegionX = (toRegionId >> 8);
////		int toRegionY = (toRegionId & 0xff);
////		
////		int mapArchiveId = cache.getIndexes()[5].getArchiveId("m" + (((toRegionX * 64) >> 3) / 8) + "_" + (((toRegionY * 64) >> 3) / 8));
////		int landscapeArchiveId = cache.getIndexes()[5].getArchiveId("l" + (((toRegionX * 64) >> 3) / 8) + "_" + (((toRegionY * 64) >> 3) / 8));
////	
////		cache.getIndexes()[5].putFile(mapArchiveId, 0, Constants.GZIP_COMPRESSION, Files.readAllBytes(new File("C:\\Users\\Andrew\\Desktop\\custom home\\0.dat").toPath()), null, false, false, -1, -1);
////		cache.getIndexes()[5].putFile(landscapeArchiveId, 0, Constants.GZIP_COMPRESSION, Files.readAllBytes(new File("C:\\Users\\Andrew\\Desktop\\custom home\\1.dat").toPath()), XTEASManager.lookup(toRegionId), false, false, -1, -1);
//
////		dump_maps(osrs_cache);
//		
//		pack_region(cache, osrs_cache, 12961, 11815); //scorpia	
//		pack_region(cache, osrs_cache, 9116, 12071); //kraken		
//		pack_region(cache, osrs_cache, 9363, 11303); //thermo
//		pack_region(cache, osrs_cache, 9619, 11559); //thermo
//	
//		pack_osrs_objects(osrs_cache, cache);
//		
//		pack_osrs_models(osrs_cache, cache);
//		
//		import_gfx(cache, osrs_cache);
//		
//		import_gfx(cache, osrs_cache);
//	
//		int[] regions = new int[] {
//				//Theater of blood lobby
//				14385, 14386, 14642, 14641,
//				//Zulrah
//				8751,
//				8752,
//				9007,
//				9008,
//				//Zeah
//				4668, 4924, 5180, 5436,
//				4667, 4923, 5179, 5435, 
//				4666, 4922, 5178, 5434,
//				4665, 4921, 5177, 5433,
//				5695, 5951, 6207, 6463, 6719, 6975, 7231, 7487, 7743, 5694, 5950, 6206, 6462, 6718, 6974, 7230, 7486,
//				7742, 5693, 5949, 6205, 6461, 6717, 6973, 7229, 7485, 7741, 5692, 5949, 6204, 6460, 6716, 6972, 7228,
//				7484, 7740, 5691, 5948, 6203, 6459, 6715, 6971, 7227, 7483, 7739, 5690, 5947, 6202, 6458, 6714, 6970,
//				7226, 7482, 7738, 5689, 5946, 6201, 6457, 6713, 6969, 7225, 7481, 7737, 4664, 4920, 5176, 5432, 5688,
//				5945, 6200, 6457, 6712, 6968, 7224, 7480, 7736, 4663, 4919, 5176, 5431, 5687, 5944, 6199, 6456, 6711,
//				6967, 7223, 7479, 7735, 4662, 6918, 5174, 5430, 5686, 5943, 6198, 6455, 6710, 6966, 7222, 7478, 7734, 5942,
//				5685, 5941, 6197, 6453, 6709, 6965, 7221, 7477, 7733, 5684, 5940, 6196, 6452, 6708, 6964, 7220, 7476, 6454, 6709,
//				7732, 5175,
//				//Cerberus
//				4883,
//				//Skotizo
//				6810,
//				//Vorkath
//				9023,
//				//Adamant & rune dragons
//				6223,
//				//Khorend dungeon
//				6556,
//				6557, 
//				6812, 
//				6813,
//				7069,
//				6301,
//				6299, 
//				6555,
//				6811,
//				7067, 
//				7068,
//				//Keymaster
//				5139,
//				//Inferno
//				9043,
//				//Myths guild
//				9772,
//				//Demonic gorillas
//				8536, 8280,
//				//Raids
//				12889, 13145, 13401,
//				13141, 13397,
//				13140, 13396, 
//				13139, 13395,
//				13138, 13394, 
//				13137, 13393,
//				13136, 13392,
//				9043, 
//				//Gargoyle boss 
//				6727,
//				//Abyssal sire
//				11851, 12107, 12363,
//				11850, 12106, 12362,
//		};
//		pack_regions(cache, osrs_cache, regions);
//		cache.getIndexes()[5].rewriteTable();
//	}
//	
//	/**
//	 * This actually works on 742 please dont remove.
//	 * @param cache
//	 * @throws IOException 
//	 */
//	public static void pack_region(FileStore cache, FileStore osrs_cache, int fromRegion, int toRegion) throws IOException {
//		try {
//			int fromRegionId = fromRegion;
//			int fromRegionX = (fromRegionId >> 8);
//			int fromRegionY = (fromRegionId & 0xff);
//			
//			int toRegionId = toRegion;
//			int toRegionX = (toRegionId >> 8);
//			int toRegionY = (toRegionId & 0xff);
//			
//			int[] xteas = XTEASManager.lookup(fromRegionId);
//			
//			int nameHash;
//			int archiveId;
//			byte[] data;
//			
//			//Map data
//			File file = new File("./maps/m" + fromRegionX + "_" + fromRegionY + ".dat");
//			if (file.exists()) {
//				String name = stripDat(file.getName());
//				data = Files.readAllBytes(file.toPath());
//				nameHash = Utils.getNameHash(name);
//				archiveId = cache.getIndexes()[5].getArchiveId(nameHash);
//				if (archiveId == -1) {
//					archiveId = cache.getIndexes()[5].getLastArchiveId() + 1;
//				}
//				cache.getIndexes()[5].putFile(archiveId, 0, Constants.GZIP_COMPRESSION, data, null, false, false, nameHash, -1);				
//			}
//			//Location data
//			file = new File("./maps/l" + fromRegionX + "_" + fromRegionY + ".dat");
//			if (file.exists()) {
//				String name = stripDat(file.getName());
//				data = Files.readAllBytes(file.toPath());
//				nameHash = Utils.getNameHash(name);
//				archiveId = cache.getIndexes()[5].getArchiveId(nameHash);
//				if (archiveId == -1) {
//					archiveId = cache.getIndexes()[5].getLastArchiveId() + 1;
//				}
//				cache.getIndexes()[5].putFile(archiveId, 0, Constants.GZIP_COMPRESSION, data, xteas, false, false, nameHash, -1);		
//			}
//			System.out.println("Packed region " + toRegionId);
//		} catch (NoSuchFileException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void pack_regions(FileStore cache, FileStore osrs_cache, int[] regions) throws IOException {
//		double percentage;
//		for (int index = 0; index < regions.length; index++) {
//			pack_region(cache, osrs_cache, regions[index], regions[index]);
//			percentage = (double) index / (double) (regions.length) * 100D;
//			System.out.println("Packing regions: " + format.format(percentage) + "%");
//		}
//		System.out.println("Done packing regions.");
//	}
//	
//	private static void dump_maps(FileStore cache) throws IOException {
//		for (int region = 0; region < 32768; region++) {
//			
//			int x = (region >> 8);
//			int y = (region & 0xff);
//			
//			byte[] data;
//			File file;
//			DataOutputStream dos;
//			
//			//Map
//			int map = cache.getIndexes()[5].getArchiveId("m" + x + "_" + y);
//			if (map != -1) {
//				data = cache.getIndexes()[5].getFile(map);
//				file = new File("./maps/", "m" + x + "_" + y + ".dat");
//				dos = new DataOutputStream(new FileOutputStream(file));
//				dos.write(data);
//				dos.close();
//				System.out.println("Dumped map " + file.getName());
//			}
//			
//			//Locations
//			int location =  cache.getIndexes()[5].getArchiveId("l" + x + "_" + y);	
//			if (location != -1) {
//				int[] xteas = XTEASManager.lookup(region);
//				data = cache.getIndexes()[5].getFile(location, 0, xteas);
//				if (data == null)
//					continue;
//				file = new File("./maps/", "l" + x + "_" + y + ".dat");
//				dos = new DataOutputStream(new FileOutputStream(file));
//				dos.write(data);
//				dos.close();
//				System.out.println("Dumped landscape " + file.getName());
//			}
//			
//		}
//	}
//	
//	public static void import_gfx(FileStore cache, FileStore osrs) {
//		int length = osrs.getIndexes()[2].getValidFilesCount(13);
//		for (int index = 0; index < length; index++) {
//			byte[] data = osrs.getIndexes()[2].getFile(13, index);
//			SpotDefinition def = new SpotDefinition((index + 5000));
//			def.decode(new InputStream(data));
//			cache.getIndexes()[21].putFile(Utils.getConfigArchive((index + 5000), 8), 
//					Utils.getConfigFile((index + 5000), 8), Constants.GZIP_COMPRESSION, def.encode(), null, false, false, -1, -1);
//			System.out.println("Packed " + (index + 5000) + " succesfully..");
//		}
//		System.out.println("Rewriting table..");
//		cache.getIndexes()[21].rewriteTable();
//	}
//	
//	private static final void transfer_flos(FileStore osrs_cache, FileStore cache) throws IOException {	
//		for (int file = 0; file < 200; file++) {
//			byte[] data = osrs_cache.getIndexes()[2].getFile(4, file);
//			if (data == null)
//				continue;
//			int id = (file + OVERLAY_OFFSET);
//			System.out.println("Packing overlay " + id + ", size: " + data.length);		
//			cache.getIndexes()[2].putFile(4, id, data);
//			System.out.println("Packed overlay " + file + " as overlay " + id + ".");
//		}
//	}
//	
//	private static final void transfer_flus(FileStore osrs_cache, FileStore cache) throws IOException {	
//		for (int file = 0; file < 200; file++) {
//			byte[] data = osrs_cache.getIndexes()[Indices.CONFIG.getIndex()].getFile(ConfigArchive.UNDERLAYS.getValue(), file);
//			if (data == null)
//				continue;
//			int id = (file + UNDERLAY_OFFSET);
//			System.out.println("Packing underlay " + id + ", size: " + data.length);
//			cache.getIndexes()[Indices.CONFIG.getIndex()].putFile(ConfigArchive.UNDERLAYS.getValue(), id, data);
//			System.out.println("Packed underlay " + file + " as underlay " + id + ".");
//		}	
//	}
//	
//	public static void pack_osrs_objects(FileStore osrs_cache, FileStore cache) throws IOException {
//		double percentage;
//		int valid_objects = osrs_cache.getIndexes()[2].getLastFileId(6);
//		System.out.println("Valids: " + valid_objects);
//		for (int id = 0; id < valid_objects; id++) {
//			byte[] data = osrs_cache.getIndexes()[2].getFile(6, id);
//			if (data == null)
//				continue;
//			cache.getIndexes()[16].putFile(getConfigArchive(id + OBJECT_OFFSET, 8), getConfigFile(id + OBJECT_OFFSET, 8), Constants.GZIP_COMPRESSION, data, null, false, false, -1, -1);
//			percentage = (double) id / (double) valid_objects * 100D;
//			System.out.println("Packing objects: " + format.format(percentage) + "%");
//		}
//		System.out.println("Rewriting table..");
//		cache.getIndexes()[16].rewriteTable();
//	}
//	
//	public static void pack_osrs_models(FileStore osrs_cache, FileStore cache) throws IOException {
//		double percentage;
//		int valid_models = osrs_cache.getIndexes()[7].getValidArchivesCount() + 1;
//		for (int index = 0; index < valid_models; index++) {
//			byte[] data = osrs_cache.getIndexes()[7].getFile(index);
//			if (data == null)
//				continue;
//			cache.getIndexes()[7].putFile((index + MODEL_OFFSET), 0, Constants.GZIP_COMPRESSION, data, null, false, false, -1, -1);
//			percentage = (double) index / (double) valid_models * 100D;
//			System.out.println("Packing models: " + format.format(percentage) + "%");
//		}
//		System.out.println("Rewriting table..");
//		cache.getIndexes()[7].rewriteTable();
//	}
//	
//	private static void transport_archive(FileStore source_cache, int source_id, FileStore target_cache, int target_id, int group_id) throws IOException {
//		transport_archive(source_cache, source_id, target_cache, target_id, group_id, true);
//	}
//
//	private static void transport_archive(FileStore source_cache, int source_id, FileStore target_cache, int target_id, int group_id, boolean rewrite) throws IOException {
//		Index target_index = target_cache.getIndexes()[target_id];
//		System.out.println("Attempting to transport group of id " + group_id + "..");
//		if (source_cache.getIndexes()[source_id].archiveExists(group_id)) {
//			target_index.putArchive(source_id + 1000, group_id, source_cache, false, true);
//		}
//		if (rewrite) {
//			System.out.println("\t ^ Rewriting table..");
//			target_index.rewriteTable();
//		}
//		System.out.println("\t ^ Finished!");
//		System.out.println();
//	}
//	
//	private static void transport_index(FileStore source_cache, int source_id, FileStore target_cache, int target_id) throws IOException {
//		System.out.println("Attempting to transport index from source id of " + source_id + " and target id of " + target_id);
//		Index source_index = source_cache.getIndexes()[source_id];
//		if (target_cache.getIndexes().length <= target_id) {
//			if (target_cache.getIndexes().length != target_id) {
//				throw new IllegalStateException("The cache has more than one gap between the source_index and the target_index!");
//			}
//			target_cache.addIndex(source_index.getTable().isNamed(), source_index.getTable().usesWhirpool(), Constants.GZIP_COMPRESSION);
//			System.out.println("\t ^ Index was created!");
//		}
//
//		Index target_index = target_cache.getIndexes()[target_id];
//		int num_groups = source_index.getLastArchiveId() + 1;
//		System.out.println("\t ^ Attempting to pack " + num_groups + " group(s)..");
//
//		double last = 0.0D;
//		for (int group_id = 0; group_id < num_groups; group_id++) {
//			if (source_index.archiveExists(group_id)) {
//				target_index.putArchive(source_id, group_id, source_cache, false, true);
//				double percentage = (double) group_id / (double) num_groups * 100D;
//				if (group_id == num_groups - 1 || percentage - last >= 1.0D) {
//					System.out.println("\t ^ Percentage Completed: " + format.format(percentage) + "%");
//					last = percentage;
//				}
//			}
//		}
//		System.out.println("\t ^ Rewriting table..");
//		target_index.rewriteTable();
//		System.out.println("\t ^ Finished!");
//		System.out.println();
//	}
//	
//	public static void reset_index(FileStore store, int index) throws FileNotFoundException, IOException {
//		store.resetIndex(index, store.getIndexes()[index].getTable().isNamed(), store.getIndexes()[index].getTable().usesWhirpool(), store.getIndexes()[index].getTable().getCompression());
//	}
//	
//	private static int getConfigArchive(int id, int bits) {
//		return (id) >> bits;
//	}
//
//	private static int getConfigFile(int id, int bits) {
//		return (id) & (1 << bits) - 1;
//	}
//	
//	private static String stripDat(String name) {
//		return name.substring(0, name.length() - ".dat".length());
//	}
//	
//	private static int stripId(String name) {
//		return Integer.parseInt(name.substring(0, name.length() - ".dat".length()));
//	}
//	
//}
