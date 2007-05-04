/*
 * Copyright (C) 2007 Clam
 * 
 * This file is part of Lateral GM.
 * Lateral GM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.subframes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.lateralgm.components.IntegerField;
import org.lateralgm.components.ResNode;
import org.lateralgm.main.LGM;
import org.lateralgm.resources.Font;

public class FontFrame extends ResourceFrame<Font>
	{
	private static final long serialVersionUID = 1L;
	private static final ImageIcon frameIcon = LGM.findIcon("font.png"); //$NON-NLS-1$
	private static final ImageIcon saveIcon = LGM.findIcon("save.png"); //$NON-NLS-1$

	public JComboBox fonts;
	public IntegerField size;
	public JCheckBox italic;
	public JCheckBox bold;
	public IntegerField charMin;
	public IntegerField charMax;
	public JLabel preview;

	public FontFrame(Font res, ResNode node)
		{
		super(res,node);

		setSize(250,390);
		setResizable(false);
		setMaximizable(false);
		setFrameIcon(frameIcon);

		setContentPane(new JPanel());
		setLayout(new FlowLayout());

		JLabel label = new JLabel(Messages.getString("FontFrame.NAME")); //$NON-NLS-1$
		label.setPreferredSize(new Dimension(40,14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);
		name.setPreferredSize(new Dimension(180,20));
		add(name);

		label = new JLabel(Messages.getString("FontFrame.FONT")); //$NON-NLS-1$
		label.setPreferredSize(new Dimension(40,14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);
		fonts = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fonts.setEditable(true);
		fonts.setSelectedItem(res.FontName);
		fonts.setPreferredSize(new Dimension(180,20));
		fonts.addActionListener(this);
		add(fonts);

		label = new JLabel(Messages.getString("FontFrame.SIZE")); //$NON-NLS-1$
		label.setPreferredSize(new Dimension(40,14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);

		size = new IntegerField(1,99,res.Size);
		size.setPreferredSize(new Dimension(180,20));
		size.addActionListener(this);
		add(size);

		bold = new JCheckBox(Messages.getString("FontFrame.BOLD")); //$NON-NLS-1$
		bold.setPreferredSize(new Dimension(110,16));
		bold.addActionListener(this);
		bold.setSelected(res.Bold);
		add(bold);
		italic = new JCheckBox(Messages.getString("FontFrame.ITALIC")); //$NON-NLS-1$
		italic.setPreferredSize(new Dimension(110,16));
		italic.addActionListener(this);
		italic.setSelected(res.Italic);
		add(italic);

		JPanel crange = new JPanel();
		crange.setBorder(BorderFactory.createTitledBorder(Messages.getString("FontFrame.CHARRANGE"))); //$NON-NLS-1$
		crange.setPreferredSize(new Dimension(220,110));

		charMin = new IntegerField(0,255,res.CharRangeMin);
		charMin.setPreferredSize(new Dimension(70,20));
		charMin.addActionListener(this);
		crange.add(charMin);

		label = new JLabel(Messages.getString("FontFrame.TO")); //$NON-NLS-1$
		label.setPreferredSize(new Dimension(40,16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		crange.add(label);

		charMax = new IntegerField(0,255,res.CharRangeMax);
		charMax.setPreferredSize(new Dimension(70,20));
		charMax.addActionListener(this);
		crange.add(charMax);

		JButton but = new JButton(Messages.getString("FontFrame.NORMAL")); //$NON-NLS-1$
		but.setPreferredSize(new Dimension(90,20));
		but.setActionCommand("Normal"); //$NON-NLS-1$
		but.addActionListener(this);
		crange.add(but);

		but = new JButton(Messages.getString("FontFrame.ALL")); //$NON-NLS-1$
		but.setPreferredSize(new Dimension(90,20));
		but.setActionCommand("All"); //$NON-NLS-1$
		but.addActionListener(this);
		crange.add(but);

		but = new JButton(Messages.getString("FontFrame.DIGITS")); //$NON-NLS-1$
		but.setPreferredSize(new Dimension(90,20));
		but.setActionCommand("Digits"); //$NON-NLS-1$
		but.addActionListener(this);
		crange.add(but);

		but = new JButton(Messages.getString("FontFrame.LETTERS")); //$NON-NLS-1$
		but.setPreferredSize(new Dimension(90,20));
		but.setActionCommand("Letters"); //$NON-NLS-1$
		but.addActionListener(this);
		crange.add(but);

		add(crange);

		JPanel prev = new JPanel(new BorderLayout());
		prev.setBorder(BorderFactory.createEtchedBorder());
		prev.setPreferredSize(new Dimension(220,100));
		preview = new JLabel(Messages.getString("FontFrame.FONT_PREVIEW")); //$NON-NLS-1$
		preview.setFont(new java.awt.Font(res.FontName,makeStyle(res.Bold,res.Italic),res.Size));
		preview.setHorizontalAlignment(SwingConstants.CENTER);
		prev.add(preview,"Center"); //$NON-NLS-1$
		add(prev);

		save.setPreferredSize(new Dimension(100,27));
		save.setIcon(saveIcon);
		save.setText(Messages.getString("FontFrame.SAVE")); //$NON-NLS-1$
		save.setAlignmentX(0.5f);
		add(save);
		}

	public boolean resourceChanged()
		{
		if (!res.name.equals(name.getText()) || !res.FontName.equals(fonts.getSelectedItem().toString())
				|| res.Size != size.getIntValue() || res.Bold != bold.isSelected()
				|| res.Italic != italic.isSelected() || res.CharRangeMin != charMin.getIntValue()
				|| res.CharRangeMax != charMax.getIntValue()) return true;
		return false;
		}

	public void revertResource()
		{
		LGM.currentFile.Fonts.replace(res.Id,resOriginal);
		}

	public void updateResource()
		{
		res.name = name.getText();
		res.FontName = fonts.getSelectedItem().toString();
		res.Size = size.getIntValue();
		res.Bold = bold.isSelected();
		res.Italic = italic.isSelected();
		res.CharRangeMin = charMin.getIntValue();
		res.CharRangeMax = charMax.getIntValue();
		resOriginal = (Font) res.copy(false,null);
		}

	public void actionPerformed(ActionEvent e)
		{
		if (e.getSource() == fonts || e.getSource() == bold || e.getSource() == italic || e.getSource() == size)
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
		preview.setFont(new java.awt.Font(fonts.getSelectedItem().toString(),makeStyle(bold.isSelected(),italic
				.isSelected()),size.getIntValue()));
		}

	private static int makeStyle(boolean bold, boolean italic)
		{
		return (italic ? java.awt.Font.ITALIC : 0) | (bold ? java.awt.Font.BOLD : 0);
		}
	}