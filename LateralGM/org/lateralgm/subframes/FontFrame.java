/*
 * Copyright (C) 2007 Clam <ebordin@aapt.net.au>
 * Copyright (C) 2008 IsmAvatar <cmagicj@nni.com>
 * Copyright (C) 2008 Quadduc <quadduc@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.subframes;

import static java.lang.Integer.MAX_VALUE;
import static javax.swing.GroupLayout.DEFAULT_SIZE;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.lateralgm.compare.ResourceComparator;
import org.lateralgm.components.IntegerField;
import org.lateralgm.components.impl.ResNode;
import org.lateralgm.messages.Messages;
import org.lateralgm.resources.Font;

public class FontFrame extends ResourceFrame<Font>
	{
	private static final long serialVersionUID = 1L;

	public JComboBox fonts;
	public IntegerField size;
	public JCheckBox italic, bold;
	public IntegerField charMin, charMax;
	public JLabel preview;
	public JTextField previewText;

	public FontFrame(Font res, ResNode node)
		{
		super(res,node);

		setResizable(false);
		setMaximizable(false);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		setLayout(layout);

		JLabel lName = new JLabel(Messages.getString("FontFrame.NAME")); //$NON-NLS-1$

		JLabel lFont = new JLabel(Messages.getString("FontFrame.FONT")); //$NON-NLS-1$
		fonts = new JComboBox(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fonts.setEditable(true);
		fonts.setSelectedItem(res.fontName);
		fonts.addActionListener(this);

		JLabel lSize = new JLabel(Messages.getString("FontFrame.SIZE")); //$NON-NLS-1$
		size = new IntegerField(1,99,res.size);
		size.setColumns(3);
		size.addActionListener(this);

		bold = new JCheckBox(Messages.getString("FontFrame.BOLD")); //$NON-NLS-1$
		bold.addActionListener(this);
		bold.setSelected(res.bold);
		italic = new JCheckBox(Messages.getString("FontFrame.ITALIC")); //$NON-NLS-1$
		italic.addActionListener(this);
		italic.setSelected(res.italic);

		JPanel crPane = makeCRPane();

		JLabel lPreview = new JLabel(Messages.getString("FontFrame.PREVIEW")); //$NON-NLS-1$
		previewText = new JTextField(Messages.getString("FontFrame.PREVIEW_DEFAULT"));
		previewText.setColumns(10);
		previewText.getDocument().addDocumentListener(new DocumentListener()
			{
				public void changedUpdate(DocumentEvent e)
					{
					}

				public void insertUpdate(DocumentEvent e)
					{
					preview.setText(previewText.getText());
					}

				public void removeUpdate(DocumentEvent e)
					{
					preview.setText(previewText.getText());
					}
			});
		JPanel prev = new JPanel(new BorderLayout());
		prev.setBorder(BorderFactory.createEtchedBorder());
		preview = new JLabel(previewText.getText());
		preview.setFont(new java.awt.Font(res.fontName,makeStyle(res.bold,res.italic),res.size));
		preview.setHorizontalAlignment(SwingConstants.CENTER);
		prev.add(preview,"Center"); //$NON-NLS-1$

		save.setText(Messages.getString("FontFrame.SAVE")); //$NON-NLS-1$

		layout.setHorizontalGroup(layout.createParallelGroup()
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addGroup(layout.createParallelGroup(Alignment.TRAILING)
		/*				*/.addComponent(lName)
		/*				*/.addComponent(lFont)
		/*				*/.addComponent(lSize))
		/*		*/.addGroup(layout.createParallelGroup()
		/*				*/.addComponent(name,DEFAULT_SIZE,120,MAX_VALUE)
		/*				*/.addComponent(fonts,120,160,MAX_VALUE)
		/*				*/.addComponent(size)))
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addComponent(bold)
		/*		*/.addComponent(italic))
		/**/.addComponent(crPane)
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addComponent(lPreview)
		/*		*/.addComponent(previewText))
		/**/.addComponent(prev,120,220,MAX_VALUE)
		/**/.addComponent(save,DEFAULT_SIZE,DEFAULT_SIZE,MAX_VALUE));
		layout.setVerticalGroup(layout.createSequentialGroup()
		/**/.addGroup(layout.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(lName)
		/*		*/.addComponent(name))
		/**/.addGroup(layout.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(lFont)
		/*		*/.addComponent(fonts))
		/**/.addGroup(layout.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(lSize)
		/*		*/.addComponent(size))
		/**/.addGroup(layout.createParallelGroup()
		/*		*/.addComponent(bold)
		/*		*/.addComponent(italic))
		/**/.addComponent(crPane)
		/**/.addGroup(layout.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(lPreview)
		/*		*/.addComponent(previewText))
		/**/.addComponent(prev,DEFAULT_SIZE,120,MAX_VALUE)
		/**/.addComponent(save));
		pack();
		}

	private JPanel makeCRPane()
		{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(Messages.getString("FontFrame.CHARRANGE")));
		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		panel.setLayout(layout);

		charMin = new IntegerField(0,255,res.charRangeMin);
		charMin.setColumns(4);
		charMin.addActionListener(this);
		JLabel lTo = new JLabel(Messages.getString("FontFrame.TO")); //$NON-NLS-1$
		charMax = new IntegerField(0,255,res.charRangeMax);
		charMax.setColumns(4);
		charMax.addActionListener(this);

		JButton crNormal = new JButton(Messages.getString("FontFrame.NORMAL")); //$NON-NLS-1$
		crNormal.setActionCommand("Normal"); //$NON-NLS-1$
		crNormal.addActionListener(this);

		JButton crAll = new JButton(Messages.getString("FontFrame.ALL")); //$NON-NLS-1$
		crAll.setActionCommand("All"); //$NON-NLS-1$
		crAll.addActionListener(this);

		JButton crDigits = new JButton(Messages.getString("FontFrame.DIGITS")); //$NON-NLS-1$
		crDigits.setActionCommand("Digits"); //$NON-NLS-1$
		crDigits.addActionListener(this);

		JButton crLetters = new JButton(Messages.getString("FontFrame.LETTERS")); //$NON-NLS-1$
		crLetters.setActionCommand("Letters"); //$NON-NLS-1$
		crLetters.addActionListener(this);

		layout.setHorizontalGroup(layout.createParallelGroup()
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addComponent(charMin)
		/*		*/.addComponent(lTo)
		/*		*/.addComponent(charMax))
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addGroup(layout.createParallelGroup()
		/*				*/.addComponent(crNormal,DEFAULT_SIZE,DEFAULT_SIZE,MAX_VALUE)
		/*				*/.addComponent(crDigits,DEFAULT_SIZE,DEFAULT_SIZE,MAX_VALUE))
		/*		*/.addGroup(layout.createParallelGroup()
		/*				*/.addComponent(crAll,DEFAULT_SIZE,DEFAULT_SIZE,MAX_VALUE)
		/*				*/.addComponent(crLetters,DEFAULT_SIZE,DEFAULT_SIZE,MAX_VALUE))));
		layout.setVerticalGroup(layout.createSequentialGroup()
		/**/.addGroup(layout.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(charMin)
		/*		*/.addComponent(lTo)
		/*		*/.addComponent(charMax))
		/**/.addGroup(layout.createSequentialGroup()
		/*		*/.addGroup(layout.createParallelGroup()
		/*				*/.addComponent(crNormal)
		/*				*/.addComponent(crAll))
		/*		*/.addGroup(layout.createParallelGroup()
		/*				*/.addComponent(crDigits)
		/*				*/.addComponent(crLetters))));
		return panel;
		}

	public boolean resourceChanged()
		{
		commitChanges();
		return !(new ResourceComparator().areEqual(res,resOriginal));
		}

	public void revertResource()
		{
		resOriginal.updateReference();
		}

	public void commitChanges()
		{
		res.setName(name.getText());
		res.fontName = fonts.getSelectedItem().toString();
		res.size = size.getIntValue();
		res.bold = bold.isSelected();
		res.italic = italic.isSelected();
		res.charRangeMin = charMin.getIntValue();
		res.charRangeMax = charMax.getIntValue();
		}

	public void actionPerformed(ActionEvent e)
		{
		if (e.getSource() == fonts || e.getSource() == bold || e.getSource() == italic
				|| e.getSource() == size)
			{
			updatePreview();
			return;
			}
		if (e.getSource() == charMin)
			{
			if (charMin.getIntValue() > charMax.getIntValue())
				{
				charMax.setIntValue(charMin.getIntValue());
				return;
				}
			}
		if (e.getSource() == charMax)
			{
			if (charMax.getIntValue() < charMin.getIntValue())
				{
				charMin.setIntValue(charMax.getIntValue());
				return;
				}
			}
		if (e.getActionCommand() == "Normal") //$NON-NLS-1$
			{
			charMin.setIntValue(32);
			charMax.setIntValue(127);
			return;
			}
		if (e.getActionCommand() == "All") //$NON-NLS-1$
			{
			charMin.setIntValue(0);
			charMax.setIntValue(255);
			return;
			}
		if (e.getActionCommand() == "Digits") //$NON-NLS-1$
			{
			charMin.setIntValue(48);
			charMax.setIntValue(57);
			return;
			}
		if (e.getActionCommand() == "Letters") //$NON-NLS-1$
			{
			charMin.setIntValue(65);
			charMax.setIntValue(122);
			return;
			}
		super.actionPerformed(e);
		}

	public void updatePreview()
		{
		preview.setFont(new java.awt.Font(fonts.getSelectedItem().toString(),makeStyle(
				bold.isSelected(),italic.isSelected()),size.getIntValue()));
		}

	private static int makeStyle(boolean bold, boolean italic)
		{
		return (italic ? java.awt.Font.ITALIC : 0) | (bold ? java.awt.Font.BOLD : 0);
		}
	}
