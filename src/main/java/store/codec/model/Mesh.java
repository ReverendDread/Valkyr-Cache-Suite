package store.codec.model;

import store.io.InputStream;

public class Mesh {

	public int numVertices;
	public short[] aShortArray615;
	public int[] verticesX;
	public short[] facesMaterial;
	public int[] verticesZ;
	public int[] verticesSkin;
	public short[] aShortArray620;
	public int numFaces;
	public byte[] texturesMappingType;
	public short[] texturesMappingP;
	public short[] facesC;
	public byte[] facesType;
	public int highestFaceIndex;
	public byte[] facesAlpha;
	public byte[] textureDirection;
	public short[] facesColour;
	public int version = 12;
	public int[] facesSkin;
	public byte priority;
	public byte[] facesPriority;
	public int numTextures;
	public byte[] facesTexture;
	public short[] texturesMappingM;
	public int[] textureSpeed;
	public short[] texturesMappingN;
	public int[] textureScaleX;
	public int[] textureScaleY;
	public int[] textureScaleZ;
	public int[] verticesY;
	public int[] textureTransU;
	public short[] facesA;
	public byte[] textureRotation;
	public short[] facesB;
	public int[] textureTransV;
	public int textureUVCoordCount;
	public EmissiveTriangle[] emitters;
	public MagnetConfig[] magnetConfigs;
	public BillboardConfig[] billboardConfigs;
	
	public Mesh(byte[] data, int id) {
		numVertices = 0;
		highestFaceIndex = 0;
		numFaces = 0;
		priority = (byte) 0;
		numTextures = 0;
		if (data[data.length - 1] == -1 && data[data.length - 2] == -1)
			decode(data);
		else
			decodeOld(data);
	}
	
	void decode(byte[] is) {
		InputStream settings_buffer = new InputStream(is);
		InputStream class298_sub53_152_ = new InputStream(is);
		InputStream class298_sub53_153_ = new InputStream(is);
		InputStream class298_sub53_154_ = new InputStream(is);
		InputStream skin_buffer = new InputStream(is);
		InputStream material_buffer = new InputStream(is);
		InputStream class298_sub53_157_ = new InputStream(is);
		settings_buffer.index = (is.length - 23);
		numVertices = settings_buffer.readUnsignedShort();
		numFaces = settings_buffer.readUnsignedShort();
		numTextures = settings_buffer.readUnsignedByte();
		int flags = settings_buffer.readUnsignedByte();
		boolean hasFillAttributes = (flags & 0x1) == 1;
		boolean hasParticleEffects = (flags & 0x2) == 2;
		boolean hasBillboards = (flags & 0x4) == 4;
		boolean version = (flags & 0x8) == 8;
		if (version) {
			settings_buffer.index -= 7;
			this.version = settings_buffer.readUnsignedByte();
			settings_buffer.index += 6;
		}
		int priority = settings_buffer.readUnsignedByte();
		int hasAlpha = settings_buffer.readUnsignedByte();
		int hasTriangleSkins = settings_buffer.readUnsignedByte();
		int hasFaceTextures = settings_buffer.readUnsignedByte();
		int hasVertexSkins = settings_buffer.readUnsignedByte();
		int modelVerticesX = settings_buffer.readUnsignedShort();
		int modelVerticesY = settings_buffer.readUnsignedShort();
		int modelVerticesZ = settings_buffer.readUnsignedShort();
		int modelVertexPoint = settings_buffer.readUnsignedShort();
		int modelTextureCoords = settings_buffer.readUnsignedShort();
		int simpleTextureFaceCount = 0;
		int complexTextureFaceCount = 0;
		int cubeTextureFaceCount = 0;
		if (numTextures > 0) {
			texturesMappingType = new byte[numTextures];
			settings_buffer.index = 0;
			for (int index = 0; index < numTextures; index++) {
				byte opcode = (texturesMappingType[index] = (byte) settings_buffer.readByte());
				if (opcode == 0)
					simpleTextureFaceCount++;
				if (opcode >= 1 && opcode <= 3)
					complexTextureFaceCount++;
				if (opcode == 2)
					cubeTextureFaceCount++;
			}
		}
		int position = numTextures;
		int i_177_ = position;
		position += numVertices;
		int i_178_ = position;
		if (hasFillAttributes)
			position += numFaces;
		int i_179_ = position;
		position += numFaces;
		int i_180_ = position;
		if (priority == 255)
			position += numFaces;
		int i_181_ = position;
		if (hasTriangleSkins == 1)
			position += numFaces;
		int i_182_ = position;
		if (hasVertexSkins == 1)
			position += numVertices;
		int i_183_ = position;
		if (hasAlpha == 1)
			position += numFaces;
		int i_184_ = position;
		position += modelVertexPoint;
		int i_185_ = position;
		if (hasFaceTextures == 1)
			position += numFaces * 2;
		int i_186_ = position;
		position += modelTextureCoords;
		int i_187_ = position;
		position += numFaces * 2;
		int i_188_ = position;
		position += modelVerticesX;
		int i_189_ = position;
		position += modelVerticesY;
		int i_190_ = position;
		position += modelVerticesZ;
		int i_191_ = position;
		position += simpleTextureFaceCount * 6;
		int i_192_ = position;
		position += complexTextureFaceCount * 6;
		int i_193_ = 6;
		if (this.version == 14)
			i_193_ = 7;
		else if (this.version >= 15)
			i_193_ = 9;
		int i_194_ = position;
		position += complexTextureFaceCount * i_193_;
		int i_195_ = position;
		position += complexTextureFaceCount;
		int i_196_ = position;
		position += complexTextureFaceCount;
		int i_197_ = position;
		position += complexTextureFaceCount + cubeTextureFaceCount * 2;
		int i_198_ = position;
		verticesX = new int[numVertices];
		verticesY = new int[numVertices];
		verticesZ = new int[numVertices];
		facesA = new short[numFaces];
		facesB = new short[numFaces];
		facesC = new short[numFaces];
		if (hasVertexSkins == 1)
			verticesSkin = new int[numVertices];
		if (hasFillAttributes)
			facesType = new byte[numFaces];
		if (priority == 255)
			facesPriority = new byte[numFaces];
		else
			this.priority = (byte) priority;
		if (hasAlpha == 1)
			facesAlpha = new byte[numFaces];
		if (hasTriangleSkins == 1)
			facesSkin = new int[numFaces];
		if (hasFaceTextures == 1)
			facesMaterial = new short[numFaces];
		if (hasFaceTextures == 1 && numTextures > 0)
			facesTexture = new byte[numFaces];
		facesColour = new short[numFaces];
		if (numTextures > 0) {
			texturesMappingP = new short[numTextures];
			texturesMappingM = new short[numTextures];
			texturesMappingN = new short[numTextures];
			if (complexTextureFaceCount > 0) {
				textureScaleX = new int[complexTextureFaceCount];
				textureScaleY = new int[complexTextureFaceCount];
				textureScaleZ = new int[complexTextureFaceCount];
				textureRotation = new byte[complexTextureFaceCount];
				textureDirection = new byte[complexTextureFaceCount];
				textureSpeed = new int[complexTextureFaceCount];
			}
			if (cubeTextureFaceCount > 0) {
				textureTransU = new int[cubeTextureFaceCount];
				textureTransV = new int[cubeTextureFaceCount];
			}
		}
		settings_buffer.index = i_177_;
		class298_sub53_152_.index = i_188_;
		class298_sub53_153_.index = i_189_;
		class298_sub53_154_.index = i_190_;
		skin_buffer.index = i_182_;
		int i_199_ = 0;
		int i_200_ = 0;
		int i_201_ = 0;
		for (int vertex = 0; vertex < numVertices; vertex++) {
			int flag = settings_buffer.readUnsignedByte();
			int offsetX = 0;
			if ((flag & 0x1) != 0)
				offsetX = class298_sub53_152_.readShortSmart();
			int offsetY = 0;
			if ((flag & 0x2) != 0)
				offsetY = class298_sub53_153_.readShortSmart();
			int offsetZ = 0;
			if ((flag & 0x4) != 0)
				offsetZ = class298_sub53_154_.readShortSmart();
			verticesX[vertex] = i_199_ + offsetX;
			verticesY[vertex] = i_200_ + offsetY;
			verticesZ[vertex] = i_201_ + offsetZ;
			i_199_ = verticesX[vertex];
			i_200_ = verticesY[vertex];
			i_201_ = verticesZ[vertex];
			if (hasVertexSkins == 1)
				verticesSkin[vertex] = skin_buffer.readUnsignedByte();
		}
		settings_buffer.index = i_187_;
		class298_sub53_152_.index = i_178_;
		class298_sub53_153_.index = i_180_;
		class298_sub53_154_.index = i_183_;
		skin_buffer.index = i_181_;
		material_buffer.index = i_185_;
		class298_sub53_157_.index = i_186_;
		for (int index = 0; index < numFaces; index++) {
			facesColour[index] = (short) settings_buffer.readUnsignedShort();
			if (hasFillAttributes)
				facesType[index] = (byte) class298_sub53_152_.readByte();
			if (priority == 255)
				facesPriority[index] = (byte) class298_sub53_153_.readByte();
			if (hasAlpha == 1)
				facesAlpha[index] = (byte) class298_sub53_154_.readByte();
			if (hasTriangleSkins == 1)
				facesSkin[index] = skin_buffer.readUnsignedByte();
			if (hasFaceTextures == 1)
				facesMaterial[index] = (short) (material_buffer.readUnsignedShort() - 1);
			if (facesTexture != null) {
				if (facesMaterial[index] != -1)
					facesTexture[index] = (byte) (class298_sub53_157_.readUnsignedByte() - 1);
				else
					facesTexture[index] = (byte) -1;
			}
		}
		highestFaceIndex = -1;
		settings_buffer.index = i_184_;
		class298_sub53_152_.index = i_179_;
		calculateMaxDepth(settings_buffer, class298_sub53_152_);
		settings_buffer.index = i_191_;
		class298_sub53_152_.index = i_192_;
		class298_sub53_153_.index = i_194_;
		class298_sub53_154_.index = i_195_;
		skin_buffer.index = i_196_;
		material_buffer.index = i_197_;
		decodeTexturedTriangles(settings_buffer, class298_sub53_152_, class298_sub53_153_, class298_sub53_154_, skin_buffer, material_buffer);
		settings_buffer.index = i_198_;
		if (hasParticleEffects) {
			int particleLength = settings_buffer.readUnsignedByte();
			if (particleLength > 0) {
				emitters = new EmissiveTriangle[particleLength];
				for (int index = 0; index < particleLength; index++) {
					int type = settings_buffer.readUnsignedShort();
					int face = settings_buffer.readUnsignedShort();
					if (priority == 255)
						priority = facesPriority[face];
					else
						this.priority = (byte) priority;
					emitters[index] = new EmissiveTriangle(type, facesA[face], facesB[face], facesC[face], this.priority);
				}
			}
			int magnetLength = settings_buffer.readUnsignedByte();
			if (magnetLength > 0) {
				magnetConfigs = new MagnetConfig[magnetLength];
				for (int index = 0; index < magnetLength; index++) {
					int type = settings_buffer.readUnsignedShort();
					int vertex = settings_buffer.readUnsignedShort();
					magnetConfigs[index] = new MagnetConfig(type, vertex);
				}
			}
		}
		if (hasBillboards) {
			int billboardLength = settings_buffer.readUnsignedByte();
			if (billboardLength > 0) {
				billboardConfigs = new BillboardConfig[billboardLength];
				for (int index = 0; index < billboardLength; index++) {
					int billboardId = settings_buffer.readUnsignedShort();
					int vertex = settings_buffer.readUnsignedShort();
					int group = settings_buffer.readUnsignedByte();
					byte distance = (byte) settings_buffer.readByte();
					billboardConfigs[index] = new BillboardConfig(billboardId, vertex, group, distance);
				}
			}
		}
	}
	
	void decodeOld(byte[] is) {
		boolean hasFaceType = false;
		boolean hasMaterial = false;
		InputStream class298_sub53 = new InputStream(is);
		InputStream class298_sub53_50_ = new InputStream(is);
		InputStream class298_sub53_51_ = new InputStream(is);
		InputStream class298_sub53_52_ = new InputStream(is);
		InputStream class298_sub53_53_ = new InputStream(is);
		class298_sub53.index = (is.length - 18);
		numVertices = class298_sub53.readUnsignedShort();
		numFaces = class298_sub53.readUnsignedShort();
		numTextures = class298_sub53.readUnsignedByte();
		int i = class298_sub53.readUnsignedByte();
		int i_54_ = class298_sub53.readUnsignedByte();
		int i_55_ = class298_sub53.readUnsignedByte();
		int i_56_ = class298_sub53.readUnsignedByte();
		int i_57_ = class298_sub53.readUnsignedByte();
		int i_58_ = class298_sub53.readUnsignedShort();
		int i_59_ = class298_sub53.readUnsignedShort();
		int i_60_ = class298_sub53.readUnsignedShort();
		int i_61_ = class298_sub53.readUnsignedShort();
		int i_62_ = 0;
		int i_63_ = i_62_;
		i_62_ += numVertices;
		int i_64_ = i_62_;
		i_62_ += numFaces;
		int i_65_ = i_62_;
		if (i_54_ == 255)
			i_62_ += numFaces;
		int i_66_ = i_62_;
		if (i_56_ == 1)
			i_62_ += numFaces;
		int i_67_ = i_62_;
		if (i == 1)
			i_62_ += numFaces;
		int i_68_ = i_62_;
		if (i_57_ == 1)
			i_62_ += numVertices;
		int i_69_ = i_62_;
		if (i_55_ == 1)
			i_62_ += numFaces;
		int i_70_ = i_62_;
		i_62_ += i_61_;
		int i_71_ = i_62_;
		i_62_ += numFaces * 2;
		int i_72_ = i_62_;
		i_62_ += numTextures * 6;
		int i_73_ = i_62_;
		i_62_ += i_58_;
		int i_74_ = i_62_;
		i_62_ += i_59_;
		int i_75_ = i_62_;
		i_62_ += i_60_;
		verticesX = new int[numVertices];
		verticesY = new int[numVertices];
		verticesZ = new int[numVertices];
		facesA = new short[numFaces];
		facesB = new short[numFaces];
		facesC = new short[numFaces];
		if (numTextures > 0) {
			texturesMappingType = new byte[numTextures];
			texturesMappingP = new short[numTextures];
			texturesMappingM = new short[numTextures];
			texturesMappingN = new short[numTextures];
		}
		if (i_57_ == 1)
			verticesSkin = new int[numVertices];
		if (i == 1) {
			facesType = new byte[numFaces];
			facesTexture = new byte[numFaces];
			facesMaterial = new short[numFaces];
		}
		if (i_54_ == 255)
			facesPriority = new byte[numFaces];
		else
			priority = (byte) i_54_;
		if (i_55_ == 1)
			facesAlpha = new byte[numFaces];
		if (i_56_ == 1)
			facesSkin = new int[numFaces];
		facesColour = new short[numFaces];
		class298_sub53.index = i_63_;
		class298_sub53_50_.index = i_73_;
		class298_sub53_51_.index = i_74_;
		class298_sub53_52_.index = i_75_;
		class298_sub53_53_.index = i_68_;
		int i_76_ = 0;
		int i_77_ = 0;
		int i_78_ = 0;
		for (int i_79_ = 0; i_79_ < numVertices; i_79_++) {
			int i_80_ = class298_sub53.readUnsignedByte();
			int i_81_ = 0;
			if ((i_80_ & 0x1) != 0)
				i_81_ = class298_sub53_50_.readShortSmart();
			int i_82_ = 0;
			if ((i_80_ & 0x2) != 0)
				i_82_ = class298_sub53_51_.readShortSmart();
			int i_83_ = 0;
			if ((i_80_ & 0x4) != 0)
				i_83_ = class298_sub53_52_.readShortSmart();
			verticesX[i_79_] = i_76_ + i_81_;
			verticesY[i_79_] = i_77_ + i_82_;
			verticesZ[i_79_] = i_78_ + i_83_;
			i_76_ = verticesX[i_79_];
			i_77_ = verticesY[i_79_];
			i_78_ = verticesZ[i_79_];
			if (i_57_ == 1)
				verticesSkin[i_79_] = class298_sub53_53_.readUnsignedByte();
		}
		class298_sub53.index = i_71_;
		class298_sub53_50_.index = i_67_;
		class298_sub53_51_.index = i_65_;
		class298_sub53_52_.index = i_69_;
		class298_sub53_53_.index = i_66_;
		for (int i_84_ = 0; i_84_ < numFaces; i_84_++) {
			facesColour[i_84_] = (short) class298_sub53.readUnsignedShort();
			if (i == 1) {
				int mask = class298_sub53_50_.readUnsignedByte();
				if ((mask & 0x1) == 1) {
					facesType[i_84_] = (byte) 1;
					hasFaceType = true;
				} else
					facesType[i_84_] = (byte) 0;
				if ((mask & 0x2) == 2) {
					facesTexture[i_84_] = (byte) (mask >> 2);
					facesMaterial[i_84_] = facesColour[i_84_];
					facesColour[i_84_] = (short) 127;
					if (facesMaterial[i_84_] != -1)
						hasMaterial = true;
				} else {
					facesTexture[i_84_] = (byte) -1;
					facesMaterial[i_84_] = (short) -1;
				}
			}
			if (i_54_ == 255)
				facesPriority[i_84_] = (byte) class298_sub53_51_.readByte();
			if (i_55_ == 1)
				facesAlpha[i_84_] = (byte) class298_sub53_52_.readByte();
			if (i_56_ == 1)
				facesSkin[i_84_] = (byte) class298_sub53_53_.readUnsignedByte();
		}
		highestFaceIndex = -1;
		class298_sub53.index = i_70_ * 116413311;
		class298_sub53_50_.index = i_64_ * 116413311;
		short i_86_ = 0;
		short i_87_ = 0;
		short i_88_ = 0;
		int i_89_ = 0;
		for (int i_90_ = 0; i_90_ < numFaces; i_90_++) {
			int i_91_ = class298_sub53_50_.readUnsignedByte();
			if (i_91_ == 1) {
				i_86_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_86_;
				i_87_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_87_;
				i_88_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_88_;
				facesA[i_90_] = i_86_;
				facesB[i_90_] = i_87_;
				facesC[i_90_] = i_88_;
				if (i_86_ > highestFaceIndex)
					highestFaceIndex = i_86_;
				if (i_87_ > highestFaceIndex)
					highestFaceIndex = i_87_;
				if (i_88_ > highestFaceIndex)
					highestFaceIndex = i_88_;
			}
			if (i_91_ == 2) {
				i_87_ = i_88_;
				i_88_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_88_;
				facesA[i_90_] = i_86_;
				facesB[i_90_] = i_87_;
				facesC[i_90_] = i_88_;
				if (i_88_ > highestFaceIndex)
					highestFaceIndex = i_88_;
			}
			if (i_91_ == 3) {
				i_86_ = i_88_;
				i_88_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_88_;
				facesA[i_90_] = i_86_;
				facesB[i_90_] = i_87_;
				facesC[i_90_] = i_88_;
				if (i_88_ > highestFaceIndex)
					highestFaceIndex = i_88_;
			}
			if (i_91_ == 4) {
				short i_92_ = i_86_;
				i_86_ = i_87_;
				i_87_ = i_92_;
				i_88_ = (short) (class298_sub53.readShortSmart() + i_89_);
				i_89_ = i_88_;
				facesA[i_90_] = i_86_;
				facesB[i_90_] = i_87_;
				facesC[i_90_] = i_88_;
				if (i_88_ > highestFaceIndex)
					highestFaceIndex = i_88_;
			}
		}
		highestFaceIndex++;
		class298_sub53.index = i_72_;
		for (int i_93_ = 0; i_93_ < numTextures; i_93_++) {
			texturesMappingType[i_93_] = (byte) 0;
			texturesMappingP[i_93_] = (short) class298_sub53.readUnsignedShort();
			texturesMappingM[i_93_] = (short) class298_sub53.readUnsignedShort();
			texturesMappingN[i_93_] = (short) class298_sub53.readUnsignedShort();
		}
		if (facesTexture != null) {
			boolean textured = false;
			for (int index = 0; index < numFaces; index++) {
				int mask = facesTexture[index] & 0xff;
				if (mask != 255) {
					if (((texturesMappingP[mask] & 0xffff) == facesA[index]) && ((texturesMappingM[mask] & 0xffff) == facesB[index]) && ((texturesMappingN[mask] & 0xffff) == facesC[index]))
						facesTexture[index] = (byte) -1;
					else
						textured = true;
				}
			}
			if (!textured)
				facesTexture = null;
		}
		if (!hasMaterial)
			facesMaterial = null;
		if (!hasFaceType)
			facesType = null;
	}
	
	void decodeTexturedTriangles(InputStream class298_sub53, InputStream class298_sub53_43_, InputStream scale_buffer, InputStream class298_sub53_45_, InputStream class298_sub53_46_, InputStream material_buffer) {
		for (int index = 0; index < numTextures; index++) {
			int mask = texturesMappingType[index] & 0xff;
			if (mask == 0) {
				texturesMappingP[index] = (short) class298_sub53.readUnsignedShort();
				texturesMappingM[index] = (short) class298_sub53.readUnsignedShort();
				texturesMappingN[index] = (short) class298_sub53.readUnsignedShort();
			}
			if (mask == 1) {
				texturesMappingP[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingM[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingN[index] = (short) class298_sub53_43_.readUnsignedShort();
				if (version < 15) {
					textureScaleX[index] = scale_buffer.readUnsignedShort();
					if (version < 14)
						textureScaleY[index] = scale_buffer.readUnsignedShort();
					else
						textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.readUnsignedShort();
				} else {
					textureScaleX[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.read24BitUnsignedInteger();
				}
				textureRotation[index] = (byte) class298_sub53_45_.readByte();
				textureDirection[index] = (byte) class298_sub53_46_.readByte();
				textureSpeed[index] = material_buffer.readByte();
			}
			if (mask == 2) {
				texturesMappingP[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingM[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingN[index] = (short) class298_sub53_43_.readUnsignedShort();
				if (version < 15) {
					textureScaleX[index] = scale_buffer.readUnsignedShort();
					if (version < 14)
						textureScaleY[index] = scale_buffer.readUnsignedShort();
					else
						textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.readUnsignedShort();
				} else {
					textureScaleX[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.read24BitUnsignedInteger();
				}
				textureRotation[index] = (byte) class298_sub53_45_.readByte();
				textureDirection[index] = (byte) class298_sub53_46_.readByte();
				textureSpeed[index] = material_buffer.readByte();
				textureTransU[index] = material_buffer.readByte();
				textureTransV[index] = material_buffer.readByte();
			}
			if (mask == 3) {
				texturesMappingP[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingM[index] = (short) class298_sub53_43_.readUnsignedShort();
				texturesMappingN[index] = (short) class298_sub53_43_.readUnsignedShort();
				if (version < 15) {
					textureScaleX[index] = scale_buffer.readUnsignedShort();
					if (version < 14)
						textureScaleY[index] = scale_buffer.readUnsignedShort();
					else
						textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.readUnsignedShort();
				} else {
					textureScaleX[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleY[index] = scale_buffer.read24BitUnsignedInteger();
					textureScaleZ[index] = scale_buffer.read24BitUnsignedInteger();
				}
				textureRotation[index] = (byte) class298_sub53_45_.readByte();
				textureDirection[index] = (byte) class298_sub53_46_.readByte();
				textureSpeed[index] = material_buffer.readByte();
			}
		}
	}
	
	void calculateMaxDepth(InputStream class298_sub53, InputStream class298_sub53_36_) {
		short i = 0;
		short i_37_ = 0;
		short i_38_ = 0;
		int i_39_ = 0;
		for (int i_40_ = 0; i_40_ < numFaces; i_40_++) {
			int i_41_ = class298_sub53_36_.readUnsignedByte();
			if (i_41_ == 1) {
				i = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i;
				i_37_ = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i_37_;
				i_38_ = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i_38_;
				facesA[i_40_] = i;
				facesB[i_40_] = i_37_;
				facesC[i_40_] = i_38_;
				if (i > highestFaceIndex)
					highestFaceIndex = i;
				if (i_37_ > highestFaceIndex)
					highestFaceIndex = i_37_;
				if (i_38_ > highestFaceIndex)
					highestFaceIndex = i_38_;
			}
			if (i_41_ == 2) {
				i_37_ = i_38_;
				i_38_ = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i_38_;
				facesA[i_40_] = i;
				facesB[i_40_] = i_37_;
				facesC[i_40_] = i_38_;
				if (i_38_ > highestFaceIndex)
					highestFaceIndex = i_38_;
			}
			if (i_41_ == 3) {
				i = i_38_;
				i_38_ = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i_38_;
				facesA[i_40_] = i;
				facesB[i_40_] = i_37_;
				facesC[i_40_] = i_38_;
				if (i_38_ > highestFaceIndex)
					highestFaceIndex = i_38_;
			}
			if (i_41_ == 4) {
				short i_42_ = i;
				i = i_37_;
				i_37_ = i_42_;
				i_38_ = (short) (class298_sub53.readShortSmart() + i_39_);
				i_39_ = i_38_;
				facesA[i_40_] = i;
				facesB[i_40_] = i_37_;
				facesC[i_40_] = i_38_;
				if (i_38_ > highestFaceIndex)
					highestFaceIndex = i_38_;
			}
		}
		highestFaceIndex++;
	}
	
}
