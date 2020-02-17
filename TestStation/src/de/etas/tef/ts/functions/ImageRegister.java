package de.etas.tef.ts.functions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.etas.tef.ts.utils.IConstants;

public class ImageRegister
{
	private final Map<Integer, Image> mapper;
	private final Display display;
	
	public ImageRegister(Display display)
	{
		this.display = display;
		mapper = new HashMap<Integer, Image>();
		initImages();
	}
	
	private void initImages()
	{
		mapper.put(IConstants.IMAGE_TITLE, createImage("icons/person.png"));
		mapper.put(IConstants.IMAGE_EXIT, createImage("icons/exit.png"));
		mapper.put(IConstants.IMAGE_RUN, createImage("icons/play.png"));
	}
	
	private Image createImage(String path)
	{
		return new Image(display, ImageRegister.class.getClassLoader().getResourceAsStream(path));
	}

	public Image getImage(int key)
	{
		return mapper.get(key);
	}
}
