package store.codec.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.Getter;
import store.FileStore;
import store.io.InputStream;
import store.io.OutputStream;

public final class SpriteContainer {
	
	@Getter private ArrayList<BufferedImage> images;
	public int[] pallete;
	private int[][] pixelsIndexes;
	private byte[][] alpha;
	private boolean[] usesAlpha;
	private int biggestWidth;
	private int biggestHeight;
	private int archive;
	private boolean loaded;

	public SpriteContainer(BufferedImage... images) {
		this.images = new ArrayList<BufferedImage>();
		this.images.addAll(Arrays.asList(images));
	}

	public SpriteContainer(FileStore cache, int archiveId, int fileId) {
		this(cache, 8, archiveId, fileId);
	}

	public SpriteContainer(FileStore cache, int idx, int archiveId, int fileId) {
		this.archive = archiveId;
	}

	public boolean decodeImage(FileStore cache, int idx, int archiveId, int fileId) {
		byte[] data = cache.getIndexes()[idx].getFile(archiveId, fileId);
		try {
			if (data != null) {
				InputStream stream = new InputStream(data);
				stream.setOffset(data.length - 2);
				int count = stream.readUnsignedShort();
				this.images = new ArrayList<BufferedImage>();
				this.pixelsIndexes = new int[count][];
				this.alpha = new byte[count][];
				this.usesAlpha = new boolean[count];
				int[] imagesMinX = new int[count];
				int[] imagesMinY = new int[count];
				int[] imagesWidth = new int[count];
				int[] imagesHeight = new int[count];
				stream.setOffset(data.length - 7 - count * 8);
				this.setBiggestWidth(stream.readShort());
				this.setBiggestHeight(stream.readShort());
				int palleteLength = (stream.readUnsignedByte() & 255) + 1;
	
				int index;
				for (index = 0; index < count; ++index) {
					imagesMinX[index] = stream.readUnsignedShort();
				}
	
				for (index = 0; index < count; ++index) {
					imagesMinY[index] = stream.readUnsignedShort();
				}
	
				for (index = 0; index < count; ++index) {
					imagesWidth[index] = stream.readUnsignedShort();
				}
	
				for (index = 0; index < count; ++index) {
					imagesHeight[index] = stream.readUnsignedShort();
				}
	
				stream.setOffset(data.length - 7 - count * 8 - (palleteLength - 1) * 3);
				this.pallete = new int[palleteLength];
	
				for (index = 1; index < palleteLength; ++index) {
					this.pallete[index] = stream.readMedium();
					if (this.pallete[index] == 0) {
						this.pallete[index] = 1;
					}
				}
	
				stream.setOffset(0);
	
				for (index = 0; index < count; ++index) {
					int pixelsIndexesLength = imagesWidth[index] * imagesHeight[index];
					this.pixelsIndexes[index] = new int[pixelsIndexesLength];
					this.alpha[index] = new byte[pixelsIndexesLength];
					int maskData = stream.readUnsignedByte();
					int i_31_;
					if ((maskData & 2) == 0) {
						int var201;
						if ((maskData & 1) == 0) {
							for (var201 = 0; var201 < pixelsIndexesLength; ++var201) {
								this.pixelsIndexes[index][var201] = (byte) stream.readByte();
							}
						} else {
							for (var201 = 0; var201 < imagesWidth[index]; ++var201) {
								for (i_31_ = 0; i_31_ < imagesHeight[index]; ++i_31_) {
									this.pixelsIndexes[index][var201 + i_31_ * imagesWidth[index]] = (byte) stream
											.readByte();
								}
							}
						}
					} else {
						this.usesAlpha[index] = true;
						boolean var20 = false;
						if ((maskData & 1) == 0) {
							for (i_31_ = 0; i_31_ < pixelsIndexesLength; ++i_31_) {
								this.pixelsIndexes[index][i_31_] = (byte) stream.readByte();
							}
	
							for (i_31_ = 0; i_31_ < pixelsIndexesLength; ++i_31_) {
								byte var21 = this.alpha[index][i_31_] = (byte) stream.readByte();
								var20 |= var21 != -1;
							}
						} else {
							int var211;
							for (i_31_ = 0; i_31_ < imagesWidth[index]; ++i_31_) {
								for (var211 = 0; var211 < imagesHeight[index]; ++var211) {
									this.pixelsIndexes[index][i_31_ + var211 * imagesWidth[index]] = stream.readByte();
								}
							}
	
							for (i_31_ = 0; i_31_ < imagesWidth[index]; ++i_31_) {
								for (var211 = 0; var211 < imagesHeight[index]; ++var211) {
									byte i_33_ = this.alpha[index][i_31_ + var211 * imagesWidth[index]] = (byte) stream
											.readByte();
									var20 |= i_33_ != -1;
								}
							}
						}
	
						if (!var20) {
							this.alpha[index] = null;
						}
					}
					this.images.add(getBufferedImage(imagesWidth[index], imagesHeight[index], 
							this.pixelsIndexes[index], this.alpha[index], this.usesAlpha[index]));
				}
			}
			this.loaded = true;
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	public BufferedImage getBufferedImage(int width, int height, int[] pixelsIndexes, byte[] extraPixels,
			boolean useExtraPixels) {
		if (width > 0 && height > 0) {
			BufferedImage image = new BufferedImage(width, height, 6);
			int[] rgbArray = new int[width * height];
			int i = 0;
			int i_43_ = 0;
			int i_46_;
			int i_47_;
			if (useExtraPixels && extraPixels != null) {
				for (i_46_ = 0; i_46_ < height; ++i_46_) {
					for (i_47_ = 0; i_47_ < width; ++i_47_) {
						rgbArray[i_43_++] = extraPixels[i] << 24 | this.pallete[pixelsIndexes[i] & 255];
						++i;
					}
				}
			} else {
				for (i_46_ = 0; i_46_ < height; ++i_46_) {
					for (i_47_ = 0; i_47_ < width; ++i_47_) {
						int i_48_ = this.pallete[pixelsIndexes[i++] & 255];
						rgbArray[i_43_++] = i_48_ != 0 ? -16777216 | i_48_ : 0;
					}
				}
			}

			image.setRGB(0, 0, width, height, rgbArray, 0, width);
			image.flush();
			return image;
		} else {
			return null;
		}
	}

	public byte[] encode() {
		
		this.biggestHeight = 0;
		this.biggestWidth = 0;
		
		if (this.pallete == null) {
			this.generatePallete();
		}

		OutputStream stream = new OutputStream();

		int container;
		int len$;
		int i$;
		for (container = 0; container < this.images.size(); ++container) {
			len$ = 0;
			if (this.usesAlpha[container]) {
				len$ |= 2;
			}

			stream.writeByte(len$);

			for (i$ = 0; i$ < this.pixelsIndexes[container].length; ++i$) {
				stream.writeByte(this.pixelsIndexes[container][i$]);
			}

			if (this.usesAlpha[container]) {
				for (i$ = 0; i$ < this.alpha[container].length; ++i$) {
					stream.writeByte(this.alpha[container][i$]);
				}
			}
		}

		for (container = 0; container < this.pallete.length; ++container) {
			stream.writeMedium(this.pallete[container]);
		}

		if (this.biggestWidth == 0 && this.biggestHeight == 0) {
			BufferedImage[] var7 = this.images.toArray(new BufferedImage[this.images.size()]);
			len$ = var7.length;

			for (i$ = 0; i$ < len$; ++i$) {
				BufferedImage image = var7[i$];
				if (image.getWidth() > this.biggestWidth) {
					this.biggestWidth = image.getWidth();
				}

				if (image.getHeight() > this.biggestHeight) {
					this.biggestHeight = image.getHeight();
				}
			}
		}

		stream.writeShort(this.biggestWidth);
		stream.writeShort(this.biggestHeight);
		stream.writeByte(this.pallete.length - 1);

		for (container = 0; container < this.images.size(); ++container) {
			stream.writeShort(this.images.get(container).getMinX());
		}

		for (container = 0; container < this.images.size(); ++container) {
			stream.writeShort(this.images.get(container).getMinY());
		}

		for (container = 0; container < this.images.size(); ++container) {
			stream.writeShort(this.images.get(container).getWidth());
		}

		for (container = 0; container < this.images.size(); ++container) {
			stream.writeShort(this.images.get(container).getHeight());
		}

		stream.writeShort(this.images.size());
		byte[] var71 = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(var71, 0, var71.length);
		return var71;
	}

	public int getPalleteIndex(int rgb) {
		if (this.pallete == null) {
			this.pallete = new int[1];
		}

		for (int pallete = 0; pallete < this.pallete.length; ++pallete) {
			if (this.pallete[pallete] == rgb) {
				return pallete;
			}
		}

		if (this.pallete.length == 256) {
			System.out.println("Pallete to big, please reduce images quality.");
			return 0;
		} else {
			int[] newPallete = new int[this.pallete.length + 1];
			System.arraycopy(this.pallete, 0, newPallete, 0, this.pallete.length);
			newPallete[this.pallete.length] = rgb;
			this.pallete = newPallete;
			return this.pallete.length - 1;
		}
	}

	public int addImage(BufferedImage image) {
		this.images.add(image);
		this.pallete = null;
		this.pixelsIndexes = null;
		this.alpha = null;
		this.usesAlpha = null;
		return this.images.size();
	}
	
	public int removeImage(int index) {
		this.images.remove(index);
		this.pallete = null;
		this.pixelsIndexes = null;
		this.alpha = null;
		this.usesAlpha = null;
		return this.images.size();
	}
	
	public List<SpriteFrame> toSpriteFrames() {
		SpriteFrame[] frames = new SpriteFrame[this.images.size()];
		for (int index = 0; index < images.size(); index++) {
			BufferedImage buffered = this.images.get(index);
			if (buffered == null)
				continue;
			Image image = SwingFXUtils.toFXImage(buffered, null);
			frames[index] = new SpriteFrame(index, image);
		}
		return Arrays.asList(frames);
	}

	public void replaceImage(BufferedImage image, int index) {
		this.images.remove(index);
		this.images.add(index, image);
		this.pallete = null;
		this.pixelsIndexes = null;
		this.alpha = null;
		this.usesAlpha = null;
	}

	public void generatePallete() {
		this.pixelsIndexes = new int[this.images.size()][];
		this.alpha = new byte[this.images.size()][];
		this.usesAlpha = new boolean[this.images.size()];

		for (int index = 0; index < this.images.size(); ++index) {
			BufferedImage image = this.images.get(index);
			int[] rgbArray = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), rgbArray, 0, image.getWidth());
			this.pixelsIndexes[index] = new int[image.getWidth() * image.getHeight()];
			this.alpha[index] = new byte[image.getWidth() * image.getHeight()];

			for (int pixel = 0; pixel < this.pixelsIndexes[index].length; ++pixel) {
				int rgb = rgbArray[pixel];
				int medintrgb = this.convertToMediumInt(rgb);
				int i = this.getPalleteIndex(medintrgb);
				this.pixelsIndexes[index][pixel] = i;
				if (rgb >> 24 != 0) {
					this.alpha[index][pixel] = (byte) (rgb >> 24);
					this.usesAlpha[index] = true;
				}
			}
		}

	}

	public int convertToMediumInt(int rgb) {
		OutputStream out = new OutputStream(4);
		out.writeInt(rgb);
		InputStream stream = new InputStream(out.getBuffer());
		stream.setOffset(1);
		rgb = stream.readMedium();
		return rgb;
	}

	public int getBiggestWidth() {
		return this.biggestWidth;
	}

	public void setBiggestWidth(int biggestWidth) {
		this.biggestWidth = biggestWidth;
	}

	public int getBiggestHeight() {
		return this.biggestHeight;
	}

	public void setBiggestHeight(int biggestHeight) {
		this.biggestHeight = biggestHeight;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	@Override public String toString() {
		return "Sprite - " + archive;
	}
	
}
