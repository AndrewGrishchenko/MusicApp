package com.andrew.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.andrew.models.AudioFileItem;

public class AudioFileItemRenderer extends JPanel implements ListCellRenderer<AudioFileItem> {
    private JLabel nameLabel = new JLabel();
    private JLabel authorLabel = new JLabel();
    private JLabel durationLabel = new JLabel();
    private JPanel panel = new JPanel();

    public AudioFileItemRenderer () {
        setLayout(new GridLayout(1, 2));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nameLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        authorLabel.setFont(new Font("Dialog", Font.PLAIN, 13));

        panel.add(nameLabel);
        panel.add(authorLabel);

        add(panel);
        add(durationLabel);
    }
    
    @Override
    public Component getListCellRendererComponent (JList<? extends AudioFileItem> list, AudioFileItem item, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText(item.getName());

        if (item.getAuthor() == null) {
            authorLabel.setText("Без автора");
        } else {
            authorLabel.setText(item.getAuthor());
        }

        durationLabel.setText(item.getDuration());

        if (isSelected) {
            nameLabel.setBackground(list.getSelectionBackground());
            authorLabel.setBackground(list.getSelectionBackground());
            durationLabel.setBackground(list.getSelectionBackground());
            panel.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else {
            nameLabel.setBackground(list.getBackground());
            authorLabel.setBackground(list.getBackground());
            durationLabel.setBackground(list.getBackground());
            panel.setBackground(list.getBackground());
            setBackground(list.getBackground());
        }

        return this;
    }
}
