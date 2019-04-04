package store.codec.util;

import java.math.BigInteger;

import store.FileStore;
import store.io.OutputStream;

public final class Utils {
	
	public static byte[] cryptRSA(byte[] data, BigInteger exponent, BigInteger modulus) {
		return (new BigInteger(data)).modPow(exponent, modulus).toByteArray();
	}

	public static byte[] getArchivePacketData(int indexId, int archiveId, byte[] archive) {
		OutputStream stream = new OutputStream(archive.length + 4);
		stream.writeByte(indexId);
		stream.writeShort(archiveId);
		stream.writeByte(0);
		stream.writeInt(archive.length);
		int offset = 8;

		for (int var6 = 0; var6 < archive.length; ++var6) {
			if (offset == 512) {
				stream.writeByte(-1);
				offset = 1;
			}

			stream.writeByte(archive[var6]);
			++offset;
		}

		byte[] var61 = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(var61, 0, var61.length);
		return var61;
	}

	public static int getNameHash(String name) {
		return name.toLowerCase().hashCode();
	}

	public static final int getInterfaceDefinitionsSize(FileStore store) {
		return store.getIndexes()[3].getLastArchiveId() + 1;
	}

	public static final int getInterfaceDefinitionsComponentsSize(FileStore store, int interfaceId) {
		return store.getIndexes()[3].getLastFileId(interfaceId) + 1;
	}

	public static final int getRenderAnimationDefintionsSize(FileStore store) {
		return store.getIndexes()[2].getLastFileId(32) + 1;
	}

	public static final int getAnimationDefinitionsSize(FileStore store) {
		int lastArchiveId = store.getIndexes()[20].getLastArchiveId();
		return lastArchiveId * 128 + store.getIndexes()[20].getValidFilesCount(lastArchiveId);
	}

	public static final int getItemDefinitionsSize(FileStore store) {
		int lastArchiveId = store.getIndexes()[19].getLastArchiveId();
		return lastArchiveId * 256 + store.getIndexes()[19].getValidFilesCount(lastArchiveId);
	}

	public static int getNPCDefinitionsSize(FileStore store) {
		int lastArchiveId = store.getIndexes()[18].getLastArchiveId();
		return lastArchiveId * 256 + store.getIndexes()[18].getValidFilesCount(lastArchiveId);
	}

	public static final int getObjectDefinitionsSize(FileStore store) {
		int lastArchiveId = store.getIndexes()[16].getLastArchiveId();
		return lastArchiveId * 256 + store.getIndexes()[16].getValidFilesCount(lastArchiveId);
	}

	public static final int getGraphicDefinitionsSize(FileStore store) {
		int lastArchiveId = store.getIndexes()[21].getLastArchiveId();
		return lastArchiveId * 256 + store.getIndexes()[21].getValidFilesCount(lastArchiveId);
	}

	public static int getTextureDiffuseSize(FileStore store) {
		return store.getIndexes()[9].getLastArchiveId();
	}
	
	public static int getSpriteDefinitionSize(FileStore store) {
		return store.getIndexes()[8].getLastArchiveId();
	}

	public static int getParticleConfigSize(FileStore store) {
		return store.getIndexes()[27].getLastFileId(0) + 1;
	}
	
	public static int getMagnetConfigSize(FileStore store) {
		return store.getIndexes()[27].getLastFileId(1) + 1;
	}
	
	public static int getConfigArchive(int id, int bits) {
		return (id) >> bits;
	}

	public static int getConfigFile(int id, int bits) {
		return (id) & (1 << bits) - 1;
	}
	
}
