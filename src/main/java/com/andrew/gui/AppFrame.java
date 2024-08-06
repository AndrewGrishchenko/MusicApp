package com.andrew.gui;

import java.awt.*;

import javax.swing.*;
import info.clearthought.layout.*;

import com.andrew.models.AudioFileItem;


/**
 * @author andrew
 */
public class AppFrame extends JFrame {
	public AppFrame () {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Andrew
		directoryPanel = new JPanel();
		chooseDirectoryButton = new JButton();
		currentDirectoryLabel = new JLabel();
		songsPanel = new JPanel();
		downloadedSongsPanel = new JPanel();
		downloadedSongsLabel = new JLabel();
		deletePanel = new JPanel();
		playPauseButton = new JButton();
		deleteButton = new JButton();
		downloadedSongsScrollPane = new JScrollPane();
		downloadedSongsList = new JList<AudioFileItem>();
		searchSongsPanel = new JPanel();
		searchSongsLabel = new JLabel();
		searchPanel = new JPanel();
		downloadButton = new JButton();
		searchTextField = new JTextField();
		searchButton = new JButton();
		searchSongsScrollPane = new JScrollPane();
		searchSongsList = new JList<AudioFileItem>();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

		//======== directoryPanel ========
		{
			directoryPanel.setAlignmentX(0.0F);
			directoryPanel. addPropertyChangeListener (new java. beans.
			PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .
			equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
			directoryPanel.setLayout(new BoxLayout(directoryPanel, BoxLayout.X_AXIS));

			//---- chooseDirectoryButton ----
			chooseDirectoryButton.setText("Выберите директорию");
			directoryPanel.add(chooseDirectoryButton);

			//---- currentDirectoryLabel ----
			currentDirectoryLabel.setText("Текущая директория: ");
			directoryPanel.add(currentDirectoryLabel);
		}
		contentPane.add(directoryPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== songsPanel ========
		{
			songsPanel.setLayout(new GridLayout(1, 2));

			//======== downloadedSongsPanel ========
			{
				downloadedSongsPanel.setAlignmentX(0.1F);
				downloadedSongsPanel.setLayout(new TableLayout(new double[][] {
					{TableLayout.FILL},
					{TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.FILL}}));

				//---- downloadedSongsLabel ----
				downloadedSongsLabel.setText("\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u044b\u0435 \u043f\u0435\u0441\u043d\u0438");
				downloadedSongsLabel.setHorizontalAlignment(SwingConstants.LEFT);
				downloadedSongsPanel.add(downloadedSongsLabel, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//======== deletePanel ========
				{
					deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.X_AXIS));

					//---- playPauseButton ----
					playPauseButton.setText(">");
					playPauseButton.setHorizontalAlignment(SwingConstants.LEFT);
					deletePanel.add(playPauseButton);

					//---- deleteButton ----
					deleteButton.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
					deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
					deletePanel.add(deleteButton);
				}
				downloadedSongsPanel.add(deletePanel, new TableLayoutConstraints(0, 1, 0, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//======== downloadedSongsScrollPane ========
				{

					//---- downloadedSongsList ----
					downloadedSongsList.setAlignmentX(0.0F);
					downloadedSongsList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
					downloadedSongsList.setCellRenderer(new AudioFileItemRenderer());
					downloadedSongsScrollPane.setViewportView(downloadedSongsList);
				}
				downloadedSongsPanel.add(downloadedSongsScrollPane, new TableLayoutConstraints(0, 2, 0, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
			}
			songsPanel.add(downloadedSongsPanel);

			//======== searchSongsPanel ========
			{
				searchSongsPanel.setLayout(new TableLayout(new double[][] {
					{TableLayout.FILL},
					{TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.FILL}}));

				//---- searchSongsLabel ----
				searchSongsLabel.setText("\u041f\u043e\u0438\u0441\u043a \u043f\u0435\u0441\u0435\u043d");
				searchSongsPanel.add(searchSongsLabel, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//======== searchPanel ========
				{
					searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

					//---- downloadButton ----
					downloadButton.setText("Скачать");
					searchPanel.add(downloadButton);
					searchPanel.add(searchTextField);

					//---- searchButton ----
					searchButton.setText("\u041f\u043e\u0438\u0441\u043a");
					searchPanel.add(searchButton);
				}
				searchSongsPanel.add(searchPanel, new TableLayoutConstraints(0, 1, 0, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//======== searchSongsScrollPane ========
				{
					searchSongsScrollPane.setViewportView(searchSongsList);
					searchSongsList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
					searchSongsList.setCellRenderer(new AudioFileItemRenderer());
				}
				searchSongsPanel.add(searchSongsScrollPane, new TableLayoutConstraints(0, 2, 0, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
			}
			songsPanel.add(searchSongsPanel);
		}
		contentPane.add(songsPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Evaluation license - Andrew
	private JPanel directoryPanel;
	private JButton chooseDirectoryButton;
	private JLabel currentDirectoryLabel;
	private JPanel songsPanel;
	private JPanel downloadedSongsPanel;
	private JLabel downloadedSongsLabel;
	private JPanel deletePanel;
	private JButton playPauseButton;
	private JButton deleteButton;
	private JScrollPane downloadedSongsScrollPane;
	private JList<AudioFileItem> downloadedSongsList;
	private JPanel searchSongsPanel;
	private JLabel searchSongsLabel;
	private JPanel searchPanel;
	private JButton downloadButton;
	private JTextField searchTextField;
	private JButton searchButton;
	private JScrollPane searchSongsScrollPane;
	private JList<AudioFileItem> searchSongsList;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

	public JList<AudioFileItem> getDownloadedSongsList() {
		return downloadedSongsList;
	}

	public JList<AudioFileItem> getSearchSongsList() {
		return searchSongsList;
	}

	public JButton getChooseDirectoryButton() {
		return chooseDirectoryButton;
	}

	public JButton getPlayPauseButton() {
		return playPauseButton;
	}

	public JButton getSearchButton () {
		return searchButton;
	}

	public JButton getdownloadButton() {
		return downloadButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JTextField getSearchTextField () {
		return searchTextField;
	}

	public JLabel getCurrentDirectoryLabel () {
		return currentDirectoryLabel;
	}
}
