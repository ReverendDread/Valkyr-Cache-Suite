/**
 * 
 */
package store.codec.image;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a sub-sprite frame image.
 * @author ReverendDread
 * Sep 11, 2018
 */
public class SpriteFrame {

	@Setter @Getter private int id;
	@Setter @Getter private Image image;
	
	/**
	 * The SpriteFrame constructor.
	 * @param id
	 * 			the id of the frame.
	 * @param image
	 * 			the image assosicated with the frame.
	 */
	public SpriteFrame(int id, Image image) {
		this.id = id;
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Frame - " + id;
	}
	
}
