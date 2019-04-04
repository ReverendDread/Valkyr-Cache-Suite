package store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Arrays;

import store.codec.util.Utils;
import store.codec.util.whirlpool.Whirlpool;
import store.io.OutputStream;

public final class FileStore {
   private Index[] indexes;
   private Cache index255;
   private String path;
   private RandomAccessFile data;
   private boolean newProtocol;

   public FileStore(String path) throws IOException {
      this(path, true);
   }

   public FileStore(String path, boolean newProtocol) throws IOException {
      this(path, newProtocol, (int[][])null);
   }

   public FileStore(String path, boolean newProtocol, int[][] keys) throws IOException {
      this.path = path;
      this.newProtocol = newProtocol;
      this.data = new RandomAccessFile(path + "main_file_cache.dat2", "rw");
      this.index255 = new Cache(255, this.data, new RandomAccessFile(path + "main_file_cache.idx255", "rw"), newProtocol);
      int idxsCount = this.index255.getArchivesCount();
      this.indexes = new Index[idxsCount];

      for(int id = 0; id < idxsCount; ++id) {
         Index index = new Index(this.index255, new Cache(id, this.data, new RandomAccessFile(path + "main_file_cache.idx" + id, "rw"), newProtocol), keys == null?null:keys[id]);
         if(index.getTable() != null) {
            this.indexes[id] = index;
         }
      }

   }

   public final byte[] generateIndex255Archive255Current(BigInteger grab_server_private_exponent, BigInteger grab_server_modulus) {
      OutputStream stream = new OutputStream();
      stream.writeByte(this.getIndexes().length);

      for(int var9 = 0; var9 < this.getIndexes().length; ++var9) {
         if(this.getIndexes()[var9] == null) {
            stream.writeInt(0);
            stream.writeInt(0);
            stream.writeBytes(new byte[64]);
         } else {
            stream.writeInt(this.getIndexes()[var9].getCRC());
            stream.writeInt(this.getIndexes()[var9].getTable().getRevision());
            stream.writeBytes(this.getIndexes()[var9].getWhirlpool());
            if(this.getIndexes()[var9].getKeys() != null) {
               int[] var10 = this.getIndexes()[var9].getKeys();
               int var12 = var10.length;

               for(int i$ = 0; i$ < var12; ++i$) {
                  int key = var10[i$];
                  stream.writeInt(key);
               }
            } else {
               for(int var11 = 0; var11 < 4; ++var11) {
                  stream.writeInt(0);
               }
            }
         }
      }

      byte[] var91 = new byte[stream.getIndex()];
      stream.setOffset(0);
      stream.flipBuffer(var91, 0, var91.length);
      OutputStream var111 = new OutputStream(65);
      var111.writeByte(0);
      var111.writeBytes(Whirlpool.getHash(var91, 0, var91.length));
      byte[] var121 = new byte[var111.getIndex()];
      var111.setOffset(0);
      var111.flipBuffer(var121, 0, var121.length);
      if(grab_server_private_exponent != null && grab_server_modulus != null) {
         var121 = Utils.cryptRSA(var121, grab_server_private_exponent, grab_server_modulus);
      }

      stream.writeBytes(var121);
      var91 = new byte[stream.getIndex()];
      stream.setOffset(0);
      stream.flipBuffer(var91, 0, var91.length);
      return var91;
   }

   public byte[] generateIndex255Archive255() {
      return this.generateIndex255Archive255Current((BigInteger)null, (BigInteger)null);
   }

   public byte[] generateIndex255Archive255Outdated() {
      OutputStream stream = new OutputStream(this.indexes.length * 8);

      for(int var3 = 0; var3 < this.indexes.length; ++var3) {
         if(this.indexes[var3] == null) {
            stream.writeInt(0);
            stream.writeInt(0);
         } else {
            stream.writeInt(this.indexes[var3].getCRC());
            stream.writeInt(this.indexes[var3].getTable().getRevision());
         }
      }

      byte[] var31 = new byte[stream.getIndex()];
      stream.setOffset(0);
      stream.flipBuffer(var31, 0, var31.length);
      return var31;
   }

   public Index[] getIndexes() {
      return this.indexes;
   }

   public Cache getIndex255() {
      return this.index255;
   }

   public int addIndex(boolean named, boolean usesWhirpool, int tableCompression) throws IOException {
      int id = this.indexes.length;
      Index[] newIndexes = (Index[])Arrays.copyOf(this.indexes, this.indexes.length + 1);
      this.resetIndex(id, newIndexes, named, usesWhirpool, tableCompression);
      this.indexes = newIndexes;
      return id;
   }

   public void resetIndex(int id, boolean named, boolean usesWhirpool, int tableCompression) throws FileNotFoundException, IOException {
      this.resetIndex(id, this.indexes, named, usesWhirpool, tableCompression);
   }

   public void resetIndex(int id, Index[] indexes, boolean named, boolean usesWhirpool, int tableCompression) throws FileNotFoundException, IOException {
      OutputStream stream = new OutputStream(4);
      stream.writeByte(5);
      stream.writeByte((named?1:0) | (usesWhirpool?2:0));
      stream.writeShort(0);
      byte[] archiveData = new byte[stream.getIndex()];
      stream.setOffset(0);
      stream.flipBuffer(archiveData, 0, archiveData.length);
      Archive archive = new Archive(id, tableCompression, -1, archiveData);
      this.index255.putArchiveData(id, archive.compress());
      indexes[id] = new Index(this.index255, new Cache(id, this.data, new RandomAccessFile(this.path + "main_file_cache.idx" + id, "rw"), this.newProtocol), (int[])null);
   }
}
