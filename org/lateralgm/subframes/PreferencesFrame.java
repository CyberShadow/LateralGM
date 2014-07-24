/*
 * Copyright (C) 2013 Robert B. Colton
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.subframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.lateralgm.components.ColorSelect;
import org.lateralgm.components.NumberField;
import org.lateralgm.components.impl.DocumentUndoManager;
import org.lateralgm.main.LGM;
import org.lateralgm.main.Prefs;
import org.lateralgm.main.PrefsStore;
import org.lateralgm.main.Util;
import org.lateralgm.messages.Messages;

public class PreferencesFrame extends JFrame implements ActionListener
	{
	private static final long serialVersionUID = 1L;
	protected JTabbedPane tabs;
	protected JSpinner sSizes;
	protected DocumentUndoManager undoManager = new DocumentUndoManager();
	protected Color fgColor;

	JComboBox<String> themeCombo, iconCombo, langCombo, actionsCombo;
	JCheckBox dndEnable, restrictTreeEnable, extraNodesEnable, dockEvent, backupsEnable;
	JTextField iconPath, themePath, manualPath, actionsPath;

	JTextField soundEditorPath, backgroundEditorPath, spriteEditorPath, codeEditorPath,
			numberBackupsField;
	// Sounds use their own stored filename/extension, which may vary from sound to sound. 
	JTextField backgroundMIME, spriteMIME, scriptMIME;

	// Room editor fields
	NumberField undoHistorySize;
	JCheckBox useFilledRectangle, useInvertedColor;
	ColorSelect viewInsideColor, viewOutsideColor;

	private JPanel makeGeneralPrefs()
		{
		JPanel p = new JPanel();

		JLabel themeLabel = new JLabel(Messages.getString("PreferencesFrame.THEME") + ":");
		Vector<String> comboBoxItems = new Vector<String>();
		comboBoxItems.add("Swing");
		comboBoxItems.add("Native");
		LookAndFeelInfo lnfs[] = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < lnfs.length; i++)
			{
			comboBoxItems.add(lnfs[i].getName());
			if (lnfs[i].getName().toLowerCase().contains("gtk"))
				{
				comboBoxItems.add("Quantum");
				}
			}
		comboBoxItems.add("Custom");
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(comboBoxItems);

		themeCombo = new JComboBox<String>(model);
		themeCombo.setSelectedItem(LGM.themename);
		JLabel iconLabel = new JLabel(Messages.getString("PreferencesFrame.ICONS") + ":");
		String[] iconOptions = { "Swing","Calico","Custom" };
		iconCombo = new JComboBox<String>(iconOptions);
		iconCombo.setSelectedItem(LGM.iconspack);
		JLabel langLabel = new JLabel(Messages.getString("PreferencesFrame.LANGUAGE") + ":");
		String[] langOptions = { "English","French","Turkish","Danish" };
		langCombo = new JComboBox<String>(langOptions);
		langCombo.setSelectedItem(Prefs.languageName);
		dndEnable = new JCheckBox(Messages.getString("PreferencesFrame.ENABLE_DND"));
		dndEnable.setSelected(Prefs.enableDragAndDrop);
		restrictTreeEnable = new JCheckBox(Messages.getString("PreferencesFrame.ENABLE_TREE_RESTRICT"));
		restrictTreeEnable.setSelected(Prefs.restrictHierarchy);
		extraNodesEnable = new JCheckBox(Messages.getString("PreferencesFrame.ENABLE_EXTRA_NODES"));
		extraNodesEnable.setSelected(Prefs.extraNodes);
		dockEvent = new JCheckBox(Messages.getString("PreferencesFrame.DOCK_EVENT_PANEL"));
		dockEvent.setSelected(Prefs.dockEventPanel);
		JLabel themePathLabel = new JLabel(Messages.getString("PreferencesFrame.THEME_PATH") + ":");
		themePath = new JTextField(Prefs.swingThemePath);
		JLabel iconPathLabel = new JLabel(Messages.getString("PreferencesFrame.ICONS_PATH") + ":");
		iconPath = new JTextField(Prefs.iconPath);
		JLabel manualPathLabel = new JLabel(Messages.getString("PreferencesFrame.MANUAL_PATH") + ":");
		manualPath = new JTextField(Prefs.manualPath);
		//JLabel actionsLabel = new JLabel(Messages.getString("PreferencesFrame.ACTIONLIBRARY") + ":");
		String[] actionsOptions = { "Standard","Logic","Custom" };
		actionsCombo = new JComboBox<String>(actionsOptions);
		//actionsCombo.setSelectedItem(Prefs.actionLibrary);
		actionsPath = new JTextField();
		actionsPath.setText(Prefs.userLibraryPath);
		backupsEnable = new JCheckBox(Messages.getString("PreferencesFrame.CREATE_BACKUPS"));
		backupsEnable.setSelected(Prefs.enableBackupSave);
		JLabel backupsLabel = new JLabel(Messages.getString("PreferencesFrame.NUMBER_BACKUPS") + ":");
		numberBackupsField = new JTextField(Prefs.numberofBackups);

		GroupLayout gl = new GroupLayout(p);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(
		/**/gl.createParallelGroup()
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(dndEnable)
		/*		*/.addComponent(restrictTreeEnable)
		/*		*/.addComponent(extraNodesEnable))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(themeLabel)
		/*		*/.addComponent(themeCombo,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*		*/.addComponent(iconLabel)
		/*		*/.addComponent(iconCombo,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*		*/.addComponent(langLabel)
		/*		*/.addComponent(langCombo,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(themePathLabel)
		/*		*/.addComponent(iconPathLabel)
		/*		*/.addComponent(manualPathLabel))
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(themePath)
		/*		*/.addComponent(iconPath)
		/*		*/.addComponent(manualPath))
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(dockEvent))
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(backupsEnable))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(backupsLabel)
		/*		*/.addComponent(numberBackupsField)));

		gl.setVerticalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(dndEnable)
		/*		*/.addComponent(restrictTreeEnable)
		/*		*/.addComponent(extraNodesEnable))
		/*	*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(themeLabel)
		/*		*/.addComponent(themeCombo)
		/*		*/.addComponent(iconLabel)
		/*		*/.addComponent(iconCombo)
		/*		*/.addComponent(langLabel)
		/*		*/.addComponent(langCombo))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(themePathLabel)
		/*		*/.addComponent(themePath,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(iconPathLabel)
		/*		*/.addComponent(iconPath,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(manualPathLabel)
		/*		*/.addComponent(manualPath,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(dockEvent,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(backupsEnable,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE))
		/*	*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*		*/.addComponent(backupsLabel)
		/*		*/.addComponent(numberBackupsField,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)));

		p.setLayout(gl);

		return p;
		}

	private JPanel makeMimePrefixPrefs()
		{
		JPanel p = new JPanel();

		JLabel spriteLabel = new JLabel("Sprite:");
		JTextField spritePrefix = new JTextField("spr_");
		JLabel soundLabel = new JLabel("Sound:");
		JTextField soundPrefix = new JTextField("snd_");
		JLabel backgroundLabel = new JLabel("Background:");
		JTextField backgroundPrefix = new JTextField("bg_");
		JLabel pathLabel = new JLabel("Path:");
		JTextField pathPrefix = new JTextField("pth_");
		JLabel scriptLabel = new JLabel("Script:");
		JTextField scriptPrefix = new JTextField("scr_");
		JLabel fontLabel = new JLabel("Font:");
		JTextField fontPrefix = new JTextField("fnt_");
		JLabel timelineLabel = new JLabel("Timeline:");
		JTextField timelinePrefix = new JTextField("tl_");
		JLabel objectLabel = new JLabel("Object:");
		JTextField objectPrefix = new JTextField("obj_");
		JLabel roomLabel = new JLabel("Room:");
		JTextField roomPrefix = new JTextField("rm_");

		JLabel backgroundMIMELabel = new JLabel("Background MIME:");
		backgroundMIME = new JTextField(Prefs.externalBackgroundExtension);
		JLabel spriteMIMELabel = new JLabel("Sprite MIME:");
		spriteMIME = new JTextField(Prefs.externalSpriteExtension);
		JLabel scriptMIMELabel = new JLabel("Script MIME:");
		scriptMIME = new JTextField(Prefs.externalScriptExtension);

		GroupLayout gl = new GroupLayout(p);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(
		/**/gl.createParallelGroup(Alignment.LEADING)
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addGroup(gl.createParallelGroup(Alignment.TRAILING)
		/*			*/.addComponent(timelineLabel)
		/*			*/.addComponent(pathLabel)
		/*			*/.addComponent(spriteLabel))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.LEADING)
		/*			*/.addComponent(spritePrefix)
		/*			*/.addComponent(timelinePrefix)
		/*			*/.addComponent(pathPrefix))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.LEADING,false)
		/*			*/.addComponent(soundLabel)
		/*			*/.addComponent(objectLabel)
		/*			*/.addComponent(scriptLabel))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.LEADING)
		/*			*/.addComponent(soundPrefix)
		/*			*/.addComponent(scriptPrefix)
		/*			*/.addComponent(objectPrefix))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.LEADING,false)
		/*			*/.addComponent(backgroundLabel)
		/*			*/.addComponent(fontLabel)
		/*			*/.addComponent(roomLabel))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.LEADING)
		/*			*/.addComponent(roomPrefix)
		/*			*/.addComponent(fontPrefix)
		/*			*/.addComponent(backgroundPrefix)))
		/*		*/.addGroup(gl.createSequentialGroup()
		/*			*/.addComponent(backgroundMIMELabel)
		/*			*/.addComponent(backgroundMIME)
		/*			*/.addComponent(spriteMIMELabel)
		/*			*/.addComponent(spriteMIME)
		/*			*/.addComponent(scriptMIMELabel)
		/*			*/.addComponent(scriptMIME)));

		gl.setVerticalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*			*/.addComponent(soundPrefix)
		/*			*/.addComponent(backgroundLabel)
		/*			*/.addComponent(backgroundPrefix)
		/*			*/.addComponent(spritePrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(spriteLabel)
		/*			*/.addComponent(soundLabel))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*			*/.addComponent(pathLabel)
		/*			*/.addComponent(pathPrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(scriptPrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(fontPrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(scriptLabel)
		/*			*/.addComponent(fontLabel))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*			*/.addComponent(timelinePrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(objectPrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(roomPrefix,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(timelineLabel)
		/*			*/.addComponent(objectLabel)
		/*			*/.addComponent(roomLabel,PREFERRED_SIZE,18,PREFERRED_SIZE)))
		/*		*/.addGroup(gl.createParallelGroup(Alignment.BASELINE)
		/*			*/.addComponent(backgroundMIMELabel)
		/*			*/.addComponent(backgroundMIME,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(spriteMIMELabel)
		/*			*/.addComponent(spriteMIME,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)
		/*			*/.addComponent(scriptMIMELabel)
		/*			*/.addComponent(scriptMIME,PREFERRED_SIZE,DEFAULT_SIZE,PREFERRED_SIZE)));

		p.setLayout(gl);

		return p;
		}

	private JPanel makeExternalEditorPrefs()
		{
		JPanel p = new JPanel();

		JLabel codeEditorLabel = new JLabel(Messages.getString("PreferencesFrame.CODE_EDITOR") + ":");
		codeEditorPath = new JTextField(Prefs.externalScriptEditorCommand);

		JLabel spriteEditorLabel = new JLabel(Messages.getString("PreferencesFrame.SPRITE_EDITOR")
				+ ":");
		spriteEditorPath = new JTextField(Prefs.externalSpriteEditorCommand);

		JLabel backgroundEditorLabel = new JLabel(
				Messages.getString("PreferencesFrame.BACKGROUND_EDITOR") + ":");
		backgroundEditorPath = new JTextField(Prefs.externalBackgroundEditorCommand);

		JLabel soundEditorLabel = new JLabel(Messages.getString("PreferencesFrame.SOUND_EDITOR") + ":");
		soundEditorPath = new JTextField(Prefs.externalSoundEditorCommand);

		GroupLayout gl = new GroupLayout(p);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		// TODO: Fix this layout so that it resizes the text fields properly
		gl.setHorizontalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createParallelGroup(Alignment.TRAILING)
		/*		*/.addComponent(spriteEditorLabel)
		/*		*/.addComponent(soundEditorLabel)
		/*		*/.addComponent(backgroundEditorLabel)
		/*		*/.addComponent(codeEditorLabel))
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(spriteEditorPath)
		/*		*/.addComponent(soundEditorPath)
		/*		*/.addComponent(backgroundEditorPath)
		/*		*/.addComponent(codeEditorPath)));

		gl.setVerticalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		/*		*/.addComponent(spriteEditorLabel)
		/*		*/.addComponent(spriteEditorPath))
		/*	*/.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		/*		*/.addComponent(soundEditorLabel)
		/*		*/.addComponent(soundEditorPath))
		/*	*/.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		/*		*/.addComponent(backgroundEditorLabel)
		/*		*/.addComponent(backgroundEditorPath))
		/*	*/.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		/*		*/.addComponent(codeEditorLabel)
		/*		*/.addComponent(codeEditorPath)));

		p.setLayout(gl);

		return p;
		}

	private JPanel makeCodeEditorPrefs()
		{
		JPanel p = new JPanel();

		//TODO: Make components

		GroupLayout gl = new GroupLayout(p);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		//TODO: Add components to layout

		return p;
		}

	// Create the room editor panel
	private Component makeRoomEditorPrefs()
		{
		JPanel roomEditorPanel = new JPanel();

		// Undo settings
		JLabel undoHistorySizeLabel = new JLabel(
				Messages.getString("PreferencesFrame.UNDO_HISTORY_SIZE") + " : ");
		undoHistorySize = new NumberField(-1,999999,Prefs.undoHistorySize);

		// Views settings
		JPanel viewsPanel = new JPanel();
		GroupLayout viewsLayout = new GroupLayout(viewsPanel);
		viewsLayout.setAutoCreateGaps(true);
		viewsLayout.setAutoCreateContainerGaps(true);
		viewsPanel.setLayout(viewsLayout);

		String title = Messages.getString("PreferencesFrame.VIEWS_TITLE");
		viewsPanel.setBorder(BorderFactory.createTitledBorder(title));

		useFilledRectangle = new JCheckBox(Messages.getString("PreferencesFrame.FILLED_RECTANGLE"));
		useFilledRectangle.setSelected(Prefs.useFilledRectangle);

		useInvertedColor = new JCheckBox(Messages.getString("PreferencesFrame.INVERTED_COLOR"));
		useInvertedColor.setSelected(Prefs.useInvertedColor);

		JLabel insideColorLabel = new JLabel(Messages.getString("PreferencesFrame.INSIDE_COLOR")
				+ " : ");
		viewInsideColor = new ColorSelect(Util.convertGmColorWithAlpha(Prefs.viewInsideColor));

		JLabel outsideColorLabel = new JLabel(Messages.getString("PreferencesFrame.OUTSIDE_COLOR")
				+ " : ");
		viewOutsideColor = new ColorSelect(Util.convertGmColorWithAlpha(Prefs.viewOutsideColor));

		// Set the layout for the views
		viewsLayout.setHorizontalGroup(
		/**/viewsLayout.createParallelGroup()
		/*	*/.addGroup(viewsLayout.createSequentialGroup()
		/*		*/.addComponent(useFilledRectangle))
		/*	*/.addGroup(viewsLayout.createSequentialGroup()
		/*		*/.addGroup(viewsLayout.createParallelGroup()
		/*			*/.addComponent(useInvertedColor)
		/*			*/.addComponent(insideColorLabel)
		/*			*/.addComponent(outsideColorLabel))
		/*		*/.addGroup(viewsLayout.createParallelGroup()
		/*			*/.addComponent(viewInsideColor,120,120,120)
		/*			*/.addComponent(viewOutsideColor,120,120,120))));

		viewsLayout.setVerticalGroup(
		/**/viewsLayout.createSequentialGroup()
		/*	*/.addComponent(useFilledRectangle)
		/*	*/.addComponent(useInvertedColor).addGap(10)
		/*	*/.addGroup(viewsLayout.createParallelGroup()
		/*		*/.addComponent(insideColorLabel)
		/*		*/.addComponent(viewInsideColor,18,18,18))
		/*	*/.addGroup(viewsLayout.createParallelGroup()
		/*		*/.addComponent(outsideColorLabel)
		/*		*/.addComponent(viewOutsideColor,18,18,18)));

		// Set the layout for the main panel
		GroupLayout gl = new GroupLayout(roomEditorPanel);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(
		/**/gl.createParallelGroup()
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(undoHistorySizeLabel)
		/*		*/.addComponent(undoHistorySize,100,100,100))
		/*	*/.addGroup(gl.createSequentialGroup()
		/*		*/.addComponent(viewsPanel,320,320,320)));

		gl.setVerticalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createParallelGroup()
		/*		*/.addComponent(undoHistorySizeLabel)
		/*		*/.addComponent(undoHistorySize,18,18,18))
		/*		*/.addComponent(viewsPanel,150,150,150));

		roomEditorPanel.setLayout(gl);

		return roomEditorPanel;
		}

	public PreferencesFrame()
		{
		setAlwaysOnTop(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(600,415);
		setLocationRelativeTo(LGM.frame);
		setTitle(Messages.getString("PreferencesFrame.TITLE"));
		setIconImage(LGM.getIconForKey("Toolbar.PREFERENCES").getImage());
		setResizable(true);

		tabs = new JTabbedPane();
		add(tabs,BorderLayout.CENTER);

		tabs.addTab(Messages.getString("PreferencesFrame.TAB_GENERAL"), //$NON-NLS-1$
				/**/null,makeGeneralPrefs(),Messages.getString("PreferencesFrame.HINT_GENERAL")); //$NON-NLS-1$ 
		JPanel pan = makeExternalEditorPrefs();
		tabs.addTab(Messages.getString("PreferencesFrame.TAB_EXTERNAL_EDITOR"), //$NON-NLS-1$
				/**/null,pan,Messages.getString("PreferencesFrame.HINT_EXTERNAL_EDITOR")); //$NON-NLS-1$ 
		tabs.addTab(Messages.getString("PreferencesFrame.TAB_MIME_PREFIX"), //$NON-NLS-1$
				/**/null,makeMimePrefixPrefs(),Messages.getString("PreferencesFrame.HINT_MIME_PREFIX")); //$NON-NLS-1$
		tabs.addTab(Messages.getString("PreferencesFrame.TAB_CODE_EDITOR"), //$NON-NLS-1$
				/**/null,makeCodeEditorPrefs(),Messages.getString("PreferencesFrame.HINT_CODE_EDITOR")); //$NON-NLS-1$ 
		tabs.addTab(Messages.getString("PreferencesFrame.TAB_ROOM_EDITOR"), //$NON-NLS-1$
				/**/null,makeRoomEditorPrefs(),Messages.getString("PreferencesFrame.HINT_ROOM_EDITOR")); //$NON-NLS-1$ 

		JPanel p = new JPanel();
		GroupLayout gl = new GroupLayout(p);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		String key;

		key = "PreferencesFrame.APPLY_CHANGES";
		JButton applyBut = new JButton(Messages.getString(key));
		applyBut.addActionListener(this);
		applyBut.setActionCommand(key);

		key = "PreferencesFrame.CLOSE";
		JButton closeBut = new JButton(Messages.getString(key));
		closeBut.addActionListener(this);
		closeBut.setActionCommand(key);

		gl.setHorizontalGroup(
		/**/gl.createSequentialGroup()
		/*	*/.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		/*		*/.addComponent(applyBut)
		/*		*/.addComponent(closeBut)));

		add(p,BorderLayout.SOUTH);
		}

	public void SavePreferences()
		{
		LGM.iconspack = (String) iconCombo.getSelectedItem();
		PrefsStore.setIconPack(LGM.iconspack);
		PrefsStore.setIconPath(iconPath.getText());
		PrefsStore.setSwingThemePath(themePath.getText());
		PrefsStore.setSwingTheme(LGM.themename);
		PrefsStore.setManualPath(manualPath.getText());
		PrefsStore.setDNDEnabled(dndEnable.isSelected());
		PrefsStore.setExtraNodes(extraNodesEnable.isSelected());
		PrefsStore.setLanguageName((String) langCombo.getSelectedItem());
		PrefsStore.setUserLibraryPath(actionsPath.getText());
		PrefsStore.setSpriteExt(spriteMIME.getText());
		PrefsStore.setBackgroundExt(backgroundMIME.getText());
		PrefsStore.setScriptExt(scriptMIME.getText());
		PrefsStore.setBackgroundEditorCommand(backgroundEditorPath.getText());
		PrefsStore.setSpriteEditorCommand(spriteEditorPath.getText());
		PrefsStore.setSoundEditorCommand(soundEditorPath.getText());
		PrefsStore.setScriptEditorCommand(codeEditorPath.getText());
		PrefsStore.setDockEventPanel(dockEvent.isSelected());
		PrefsStore.setUndoHistorySize(undoHistorySize.getIntValue());
		PrefsStore.setFilledRectangle(useFilledRectangle.isSelected());
		PrefsStore.setInvertedColor(useInvertedColor.isSelected());
		PrefsStore.setViewInsideColor(Util.getGmColorWithAlpha(viewInsideColor.getSelectedColor()));
		PrefsStore.setViewOutsideColor(Util.getGmColorWithAlpha(viewOutsideColor.getSelectedColor()));
		}

	public void ResetPreferences()
		{
		//TODO: Reset preferences to their active state when this frame was first opened
		// For this one just copy the shit above where I give the controls their default
		// values froms Prefs.
		}

	public void ResetPreferencesToDefault()
		{
		//TODO: Reset the preferences to their settings when LGM was first installed
		}

	public void actionPerformed(ActionEvent ev)
		{
		String com = ev.getActionCommand();
		if (com.equals("PreferencesFrame.APPLY_CHANGES")) //$NON-NLS-1$
			{
			JOptionPane.showMessageDialog(this,
					Messages.getString("PreferencesFrame.APPLY_CHANGES_NOTICE"));
			LGM.SetLookAndFeel((String) themeCombo.getSelectedItem());
			LGM.UpdateLookAndFeel();
			SavePreferences();
			}
		if (com.equals("PreferencesFrame.CLOSE")) //$NON-NLS-1$
			{
			this.setVisible(false);
			}
		}
	}