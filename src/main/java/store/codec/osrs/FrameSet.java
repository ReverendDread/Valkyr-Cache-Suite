/**
 * 
 */
package store.codec.osrs;

import java.util.LinkedList;

import store.FileStore;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Sep 8, 2018
 */
public class FrameSet {

	int sequenceId;
	int archive;
	private FrameDefinition[] frameType;
	private byte[][] frameData;
	private FileStore cache;
	
	public FrameSet(FileStore cache, int archive, int sequenceId) {
		this.cache = cache;
		this.archive = archive;
		this.sequenceId = sequenceId;
		decodeOSRS();
	}
	
	/**
	 * 
	 */
	private void decodeOSRS() {

	}

	/**
	 * Decodes a sequence frame set.
	 * @return
	 */
	public boolean decode718() {
		
		//If frames are null, return false.
		if (frameType != null)
			return true;
		
		//Get the frames data for the set.
		if (frameData == null) {
			if (!cache.getIndexes()[0].archiveExists(archive))
				return false;
			int[] frames = cache.getIndexes()[0].getFileIds(archive);
			frameData = new byte[frames.length][];
			for (int index = 0; index < frames.length; index++) {
				frameData[index] = cache.getIndexes()[0].getFile(archive, frames[index]);
			}
		}
		
		//Check if the base exists for each frame
		boolean exists = true;
		for (int index = 0; index < frameData.length; index++) {
			byte[] data = frameData[index];
			InputStream stream = new InputStream(data);
			int id = stream.readUnsignedShort();
			exists &= cache.getIndexes()[1].fileExists(id, 0);
		}
		
		//if the base doesn't exist, return false.
		if (!exists) {
			return false;
		}
		
		LinkedList<BaseDefinition> list = new LinkedList<BaseDefinition>();
		
		int[] frameFiles;
		int length = cache.getIndexes()[0].getValidFilesCount(archive);
		frameType = new FrameDefinition[length];
		frameFiles = cache.getIndexes()[0].getFileIds(archive);
		for (int index = 0; index < frameFiles.length; index++) {
			byte[] data = frameData[index];
			InputStream stream = new InputStream(data);
			int id = stream.readUnsignedShort();
			BaseDefinition type = null;
			for (BaseDefinition baseDefinition = list.peek(); baseDefinition != null; baseDefinition = list.poll()) {
				if (id == (baseDefinition.id)) {
					type = baseDefinition;
					break;
				}
			}
			if (type == null)
			type = (new BaseDefinition(id, cache.getIndexes()[1].getFile(id)));
			frameType[frameFiles[index]] = new FrameDefinition(data, type);
		}
		frameData = null;
		return true;
	}
	
}
