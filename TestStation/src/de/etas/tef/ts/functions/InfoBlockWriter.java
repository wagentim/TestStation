package de.etas.tef.ts.functions;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.RuntimeErrorException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;

import de.etas.tef.ts.utils.IConstants;

public class InfoBlockWriter implements IActionListener
{
	private final StyledText infoBlock;
	
	private final Color red;
	private final Color black;
	private final Color blue;
	private final Color darkGreen;
	private final Color gray;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	private String txt = IConstants.EMPTY_STRING;

	public InfoBlockWriter(final StyledText infoBlock)
	{
		if (null == infoBlock)
		{
			throw new RuntimeErrorException(null, "InfoBlock is null");
		}
		this.infoBlock = infoBlock;
		this.red = infoBlock.getDisplay().getSystemColor(SWT.COLOR_RED);
		this.black = infoBlock.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.blue = infoBlock.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		this.darkGreen = infoBlock.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN);
		this.gray = infoBlock.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		ActionManager.INSTANCE.addActionListener(this);
		
	}
	
	private void moveToLastLine()
	{
		infoBlock.setTopIndex(infoBlock.getLineCount());
	}
	
	private void logError(String text)
	{
		txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[ERROR] " + text + "\n";

		StyleRange sr = new StyleRange();
		sr.start = infoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = red;
		sr.fontStyle = SWT.ITALIC;
		infoBlock.append(txt);
		infoBlock.setStyleRange(sr);
		moveToLastLine();
//		logOutput.addLog(new Log(text, IConstants.EVENT_LOG_WRITE_ERROR, error));
	}

	private void logInfoBlue(String text)
	{
		txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = infoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = blue;
		sr.fontStyle = SWT.NORMAL;
		infoBlock.append(txt);
		infoBlock.setStyleRange(sr);
		moveToLastLine();
//		logOutput.addLog(new Log(text, IConstants.EVENT_LOG_WRITE_INFO, info));
	}
	
	private void logInfoBlack(String text)
	{
		txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = infoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = black;
		sr.fontStyle = SWT.NORMAL;
		infoBlock.append(txt);
		infoBlock.setStyleRange(sr);
		moveToLastLine();
//		logOutput.addLog(new Log(text, IConstants.EVENT_LOG_WRITE_WARNING, warning));
	}

	private void logInfoDarkGreen(String text)
	{
		txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = infoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = darkGreen;
		sr.fontStyle = SWT.NORMAL;
		infoBlock.append(txt);
		infoBlock.setStyleRange(sr);
		moveToLastLine();
//		logOutput.addLog(new Log(text, IConstants.EVENT_LOG_WRITE_HINTS, hints));
	}
	
	private void logInfoGray(String text)
	{
		txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = infoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = gray;
		sr.fontStyle = SWT.NORMAL;
		infoBlock.append(txt);
		infoBlock.setStyleRange(sr);
		moveToLastLine();
//		logOutput.addLog(new Log(text, IConstants.EVENT_LOG_WRITE_HINTS, hints));
	}
	

	@Override
	public void receivedAction(int type, Object content)
	{
		if ((type == IConstants.MSG_ERR))
		{
			logError(content.toString());
		} 
		else if (type == IConstants.MSG_INFO_BLUE)
		{
			logInfoBlue(content.toString());
		}
		else if (type == IConstants.MSG_INFO_BLACK)
		{
			logInfoBlack(content.toString());
		}
		else if (type == IConstants.MSG_INFO_DARKGREEN)
		{
			logInfoDarkGreen(content.toString());
		}
		else if (type == IConstants.MSG_INFO_GRAY)
		{
			logInfoGray(content.toString());
		}
	}
}
