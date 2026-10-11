/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Pasindu
 */
    public class BackgroundPanel extends JPanel {

        private Image backgroundImage;
        private float opacity = 0.45f;

        public BackgroundPanel() {
            try {
                java.net.URL imageURL =
                    getClass().getResource("/images/chemical_background.png");

                if (imageURL == null) {
                    System.err.println("Background image not found!");
                } else {
                    backgroundImage = javax.imageio.ImageIO.read(imageURL);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            setOpaque(true);
            setBackground(new java.awt.Color(247, 250, 255));
        }

        public void setImageOpacity(float opacity) {
            this.opacity = Math.max(0f, Math.min(1f, opacity));
            repaint();
        }

        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setComposite(
                    AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, opacity
                    )
                );

                g2.drawImage(
                    backgroundImage, 0, 0,
                    getWidth(), getHeight(), this
                );

                g2.dispose();
            }
        }

    }
