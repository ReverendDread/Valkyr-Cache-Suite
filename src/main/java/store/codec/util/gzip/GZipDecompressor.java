package store.codec.util.gzip;

import java.util.zip.Inflater;

import store.io.Stream;

public class GZipDecompressor {

	private static final Inflater inflaterInstance = new Inflater(true);

	public static final boolean decompress(Stream stream, byte[] data) {
		synchronized (inflaterInstance) {
			if (stream.getBuffer()[stream.getIndex()] == 31 && stream.getBuffer()[stream.getIndex() + 1] == -117) {
				try {
					inflaterInstance.setInput(stream.getBuffer(), stream.getIndex() + 10,
							-stream.getIndex() - 18 + stream.getBuffer().length);
					inflaterInstance.inflate(data);
				} catch (Exception var5) {
					inflaterInstance.reset();
					return false;
				}

				inflaterInstance.reset();
				return true;
			} else {
				return false;
			}
		}
	}
}
