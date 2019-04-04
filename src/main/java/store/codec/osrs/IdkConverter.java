/**
 * 
 */
package store.codec.osrs;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import store.ConfigArchive;
import store.FileStore;
import store.Index;
import store.Indices;
import store.codec.IdentityKit;
import store.codec.util.Constants;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Dec 3, 2018
 */
public class IdkConverter {

	private static final DecimalFormat format = new DecimalFormat("#.##");
	
	private static ArrayList<Integer> modelsToReplace = new ArrayList<Integer>();
	
	public static void main(String[] args) throws IOException {	
		
		FileStore cache = new FileStore("");
		FileStore old_cache = new FileStore("C:\\Users\\Andrew\\Desktop\\667 cache\\");
	
		int size = old_cache.getIndexes()[Indices.CONFIG.getIndex()].getLastFileId(ConfigArchive.IDENTITY_KIT.getValue());
		
		System.out.println("Reading " + size + " IdentityKits...");
		for (int index = 0; index < size; index++) {
			IdentityKit kit = new IdentityKit(index);
			byte[] data = old_cache.getIndexes()[Indices.CONFIG.getIndex()].getFile(ConfigArchive.IDENTITY_KIT.getValue(), index);
			if (data != null) {
				kit.decode(new InputStream(data));
				for (int model = 0; model < kit.bodyModels.length; model++) {
					modelsToReplace.add(kit.bodyModels[model]);
				}
				for (int model = 0; model < kit.headModels.length; model++) {
					modelsToReplace.add(kit.headModels[model]);
				}
			} else {
				System.out.println("data is null for idk " + index);
			}
		}
		
		System.out.println("Replacing models...");
		modelsToReplace.forEach((model) -> {
			byte[] data = old_cache.getIndexes()[Indices.MODELS.getIndex()].getFile(model);
			if (data != null) {
				cache.getIndexes()[Indices.MODELS.getIndex()].putFile(model, 0, Constants.GZIP_COMPRESSION, data, null, false, false, -1, -1);
			}
		});
	
		System.out.println("Rewritting model reference table...");
		cache.getIndexes()[Indices.MODELS.getIndex()].rewriteTable();
		
		System.out.println("Importing new identity kits...");
		transport_archive(old_cache, Indices.CONFIG.getIndex(), cache, Indices.CONFIG.getIndex(), ConfigArchive.IDENTITY_KIT.getValue());
		
	}
	
	private static void transport_index(FileStore source_cache, int source_id, FileStore target_cache, int target_id) throws IOException {
		System.out.println("Attempting to transport index from source id of " + source_id + " and target id of " + target_id);
		Index source_index = source_cache.getIndexes()[source_id];
		if (target_cache.getIndexes().length <= target_id) {
			if (target_cache.getIndexes().length != target_id) {
				throw new IllegalStateException("The cache has more than one gap between the source_index and the target_index!");
			}
			target_cache.addIndex(source_index.getTable().isNamed(), source_index.getTable().usesWhirpool(), Constants.GZIP_COMPRESSION);
			System.out.println("\t ^ Index was created!");
		}

		Index target_index = target_cache.getIndexes()[target_id];
		int num_groups = source_index.getLastArchiveId() + 1;
		System.out.println("\t ^ Attempting to pack " + num_groups + " group(s)..");

		double last = 0.0D;
		for (int group_id = 0; group_id < num_groups; group_id++) {
			if (source_index.archiveExists(group_id)) {
				target_index.putArchive(source_id, group_id, source_cache, false, true);
				double percentage = (double) group_id / (double) num_groups * 100D;
				if (group_id == num_groups - 1 || percentage - last >= 1.0D) {
					System.out.println("\t ^ Percentage Completed: " + format.format(percentage) + "%");
					last = percentage;
				}
			}
		}
		System.out.println("\t ^ Rewriting table..");
		target_index.rewriteTable();
		System.out.println("\t ^ Finished!");
		System.out.println();
	}
	
	private static void transport_archive(FileStore source_cache, int source_id, FileStore target_cache, int target_id, int group_id) throws IOException {
		transport_archive(source_cache, source_id, target_cache, target_id, group_id, true);
	}

	private static void transport_archive(FileStore source_cache, int source_id, FileStore target_cache, int target_id, int group_id, boolean rewrite) throws IOException {
		Index target_index = target_cache.getIndexes()[target_id];
		System.out.println("Attempting to transport group of id " + group_id + "..");
		if (source_cache.getIndexes()[source_id].archiveExists(group_id)) {
			target_index.putArchive(source_id, group_id, source_cache, false, true);
		}
		if (rewrite) {
			System.out.println("\t ^ Rewriting table..");
			target_index.rewriteTable();
		}
		System.out.println("\t ^ Finished!");
		System.out.println();
	}
	
}
