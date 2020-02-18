package de.etas.tef.ts.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.ts.functions.ActionManager;
import de.etas.tef.ts.functions.Controller;
import de.etas.tef.ts.utils.IConstants;

public class SearchComposite extends Composite
{
	
//	private final Controller controller;
	private Text searchText;
	private Label cancelImage;
	private StringBuilder sb;
	
	public SearchComposite(Composite parent, int style, Controller controller)
	{
		super(parent, style);
//		this.controller = controller;

		GridLayout layout = new GridLayout(4, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0; 
		layout.marginHeight = layout.marginWidth = 3;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumHeight = 30;
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getWhite());
		
		Label label = new Label(this, SWT.NONE);
		label.setImage(controller.getImage(IConstants.IMAGE_SEARCH));
		gd = new GridData();
		label.setLayoutData(gd);
		label.setBackground(controller.getWhite());
		
		searchText = new Text(this, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalSpan = gd.horizontalSpan = 0;
		searchText.setLayoutData(gd);
		searchText.setMessage("Search");
		
		searchText.addModifyListener(new ModifyListener()
		{
			
			@Override
			public void modifyText(ModifyEvent event)
			{
				String text = searchText.getText();
				
				text = filterText(text);
				
				if(!text.isEmpty())
				{
					cancelImage.setVisible(true);
				}
				else
				{
					cancelImage.setVisible(false);
				}
				
				ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_SEARCH, text);
				
			}
		});
		
		cancelImage = new Label(this, SWT.NONE);
		cancelImage.setImage(controller.getImage(IConstants.IMAGE_CANCEL));
		gd = new GridData();
		cancelImage.setLayoutData(gd);
		cancelImage.setBackground(controller.getWhite());
		cancelImage.setVisible(false);
		cancelImage.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseUp(MouseEvent arg0)
			{
				searchText.setText(IConstants.EMPTY_STRING);
				cancelImage.setVisible(false);
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

		});
		
		sb = new StringBuilder();
	}
	
	protected String filterText(String text)
	{
		sb.delete(0, sb.length());
		
		for(char c : text.toCharArray())
		{
			if(c != 'x' && c!='X')
			{
				sb.append(c);
			}
		}
		
		
		return sb.toString();
	}

	public void clearText()
	{
		searchText.setText(IConstants.EMPTY_STRING);
	}
}
