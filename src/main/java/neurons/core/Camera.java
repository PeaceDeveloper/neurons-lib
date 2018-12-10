package neurons.core;
import com.labviros.is.msgs.camera.CompressedImage;

public class Camera extends AbstractThing {
	private CompressedImage compressedImage;
	
	private AbstractResource<CompressedImage> compressedImageResource;

	public CompressedImage getCompressedImage() {
		return compressedImage;
	}

	public void setCompressedImage(CompressedImage compressedImage) {
		this.compressedImage = compressedImage;
	}

	public AbstractResource<CompressedImage> getCompressedImageResource() {
		return compressedImageResource;
	}

	public void setCompressedImageResource(AbstractResource<CompressedImage> compressedImageResource) {
		this.compressedImageResource = compressedImageResource;
	}	
}