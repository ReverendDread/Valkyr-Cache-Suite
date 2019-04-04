package misc;
//package com.models;
//
//import java.awt.Color;
//
//import org.lwjgl.LWJGLException;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//import org.lwjgl.opengl.GL11;
//
//import com.alex.loaders.model.Mesh;
//
//public class ModelViewer {
//	
//	private static final int WIDTH = 500, HEIGHT = 500;
//	
//	public static void create(Mesh model) throws LWJGLException {
//
//		Display.setDisplayMode(new DisplayMode(800, 600));
//		Display.setTitle("Model Viewer");
//		Display.setInitialBackground((float) Color.gray.getRed() / 255f, (float) Color.gray.getGreen() / 255f,
//				(float) Color.gray.getBlue() / 255f);
//		Display.create();
//
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glLoadIdentity();
//		
//		double aspect = 1;
//		double near = 1;
//		double far = 100000000000L;
//		double fov = 1;
//		
//		GL11.glFrustum(-aspect * near * fov, aspect * near * fov, -fov, fov, near, far);
//		GL11.glCullFace(GL11.GL_BACK);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);
//		
//		long last = 0;
//
//		while (!Display.isCloseRequested()) {
//			
//			// Clear the screen and depth buffer
//			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//
//			GL11.glBegin(GL11.GL_TRIANGLES);
//
//			for (int i = 0; i < model.numFaces; ++i) {
//				
//				int vertexA = model.facesA[i];
//				int vertexB = model.facesB[i];
//				int vertexC = model.facesC[i];
//
//				int vertexAx = model.verticesX[vertexA];
//				int vertexAy = model.verticesY[vertexA];
//				int vertexAz = model.verticesZ[vertexA];
//
//				int vertexBx = model.verticesX[vertexB];
//				int vertexBy = model.verticesY[vertexB];
//				int vertexBz = model.verticesZ[vertexB];
//
//				int vertexCx = model.verticesX[vertexC];
//				int vertexCy = model.verticesY[vertexC];
//				int vertexCz = model.verticesZ[vertexC];
//
//				short hsb = model.facesColour[i];
//
//				int rgb = hsbToRGB(hsb);
//				Color c = new Color(rgb);
//
//				// convert to range of 0-1
//				float rf = (float) c.getRed() / 255f;
//				float gf = (float) c.getGreen() / 255f;
//				float bf = (float) c.getBlue() / 255f;
//
//				GL11.glColor3f(rf, gf, bf);
//
//				GL11.glVertex3i(vertexAx, vertexAy, vertexAz);
//				GL11.glVertex3i(vertexBx, vertexBy, vertexBz);
//				GL11.glVertex3i(vertexCx, vertexCy, vertexCz);
//				
//				//drawgrid();
//			}
//			
//			GL11.glEnd();
//
//			Display.update();
//			Display.sync(50); // fps
//
//			long delta = System.currentTimeMillis() - last;
//			last = System.currentTimeMillis();
//			
//			Camera.create();
//			Camera.acceptInput(delta);
//
//			Camera.apply();
//			
//		}
//
//		Display.destroy();
//	}
//
//	private static void drawline(float x1, float y1, float x2, float y2) {
//		GL11.glBegin(GL11.GL_LINES);
//		GL11.glVertex2f(x1, y1);
//		GL11.glVertex2f(x2, y2);
//		GL11.glEnd();
//	}
//
//	private static void drawgrid() {
//		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//		GL11.glColor3ub((byte) 240, (byte) 57, (byte) 53);
//		for (float i = 0; i < HEIGHT; i += 10) {
//			if ((int) i % 100 == 0)
//				GL11.glLineWidth(3.0f);
//			else if ((int) i % 50 == 0)
//				GL11.glLineWidth(2.0f);
//			else
//				GL11.glLineWidth(1.0f);
//			drawline(0, i, (float) WIDTH, i);
//		}
//		for (float i = 0; i < WIDTH; i += 10) {
//			if ((int) i % 100 == 0)
//				GL11.glLineWidth(3.0f);
//			else if ((int) i % 50 == 0)
//				GL11.glLineWidth(2.0f);
//			else
//				GL11.glLineWidth(1.0f);
//			drawline(i, 0, i, (float) HEIGHT);
//		}
//	}
//
//	// found these two functions here https://www.rune-server.org/runescape-development/rs2-client/tools/589900-rs2-hsb-color-picker.html
//	public static int rgbToHSB(int red, int green, int blue) {
//		float[] HSB = Color.RGBtoHSB(red, green, blue, null);
//		float hue = (HSB[0]);
//		float saturation = (HSB[1]);
//		float brightness = (HSB[2]);
//		int encode_hue = (int) (hue * 63); // to 6-bits
//		int encode_saturation = (int) (saturation * 7); // to 3-bits
//		int encode_brightness = (int) (brightness * 127); // to 7-bits
//		return (encode_hue << 10) + (encode_saturation << 7) + (encode_brightness);
//	}
//
//	public static int hsbToRGB(int hsb) {
//		int h = (hsb >> 10) & 0x3f;
//		int s = (hsb >> 7) & 0x07;
//		int b = (hsb & 0x7f);
//		return Color.HSBtoRGB((float) h / 63, (float) s / 7, (float) b / 127);
//	}
//	
//}
