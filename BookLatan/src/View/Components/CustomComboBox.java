/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;

import Utilities.Design;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author Joseph Rey
 */
public class CustomComboBox extends BasicComboBoxUI{
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton("▼");
        button.setBackground(Design.PRIME_COLOR);
        return button;
    }

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup popup = (BasicComboPopup) super.createPopup();
        popup.getList().setBackground(Design.PRIME_COLOR); // dropdown background
        popup.getList().setForeground(Design.TEXT_COLOR_DINEL); // dropdown text
        return popup;
    }
}
