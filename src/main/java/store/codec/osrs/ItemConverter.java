/**
 * 
 */
package store.codec.osrs;

import java.io.IOException;
import java.text.DecimalFormat;

import store.FileStore;
import store.Indices;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Oct 6, 2018
 */
public class ItemConverter {

	private static final DecimalFormat format = new DecimalFormat("#.##");
	
	public static void main(String[] args) throws IOException {	
		
		FileStore cache = new FileStore("F:\\Mega-Sausage-Server\\data\\cache\\");
		FileStore osrs = new FileStore("C:\\Users\\Andrew\\Desktop\\177\\");
		
		final int size = osrs.getIndexes()[Indices.CONFIG.getIndex()].getLastFileId(10);
		
		System.out.println("Packing " + size + " items...");
		
		byte[] data;	
		final int offset = 30000;
		double progress;
		
		for (int index = 0; index < size; index++) {			
			data = osrs.getIndexes()[Indices.CONFIG.getIndex()].getFile(10, index);		
//			cache.getIndexes()[Indices.ITEMS.getIndex()].putFile(Utils.getConfigArchive((index + 30_000), 8), 
//					Utils.getConfigFile((index + 30_000), 8), Constants.GZIP_COMPRESSION, data, null, false, false, -1, -1);
			ItemDefinition osrs_definition = new ItemDefinition(index + offset, new InputStream(data));		
			store.codec.ItemDefinition definition = osrs_definition.to718Item(30_000);		
			definition.save(cache);		
			progress = ((double) index / (double) size * 100D);		
			System.out.println("[Progress -  " + format.format(progress) + "%]");
		}
		cache.getIndexes()[Indices.ITEMS.getIndex()].rewriteTable();
		System.out.println("Done packing data.");
				
	}
	
}
