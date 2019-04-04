package store.codec.image;

import java.awt.Image;
import java.awt.Toolkit;

import store.FileStore;

public class LoaderImageArchive {
	
	private byte[] data;

	public LoaderImageArchive(byte[] data) {
		this.data = data;
	}

	public LoaderImageArchive(FileStore cache, int archiveId) {
		this(cache, 32, archiveId, 0);
	}

	private LoaderImageArchive(FileStore cache, int idx, int archiveId, int fileId) {
		this.decodeArchive(cache, idx, archiveId, fileId);
	}

	private void decodeArchive(FileStore cache, int idx, int archiveId, int fileId) {
		byte[] data = cache.getIndexes()[idx].getFile(archiveId, fileId);
		if (data != null) {
			this.data = data;
		}

	}

	public Image getImage() {
		return Toolkit.getDefaultToolkit().createImage(this.data);
	}

	public byte[] getImageData() {
		return this.data;
	}
}
