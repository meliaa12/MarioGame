// Piece.java
package com.mathmaurer.objets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Piece extends Objet implements Runnable {
    private float baseY; // Position Y de base
    private float currentY; // Position Y actuelle
    private float amplitude = 10; // Amplitude de l'oscillation
    private float speed = 0.1f; // Vitesse de l'oscillation
    private float time = 0; // Temps pour l'animation
    private boolean isAnimating = true;

    private Texture icoObjet; // Déclaration de l'attribut


    // Updated constructor to accept an image path
    public Piece(int x, int y, String imagePath) {
        super(x, y, 25, 25, imagePath); // Use the provided image path
        this.baseY = y;
        this.currentY = y;
        this.icoObjet = new Texture(imagePath); // Use the provided image path for icoObjet
    }




    // **** METHODES **** //
    @Override
    public void render(SpriteBatch batch) {
        if (isAnimating) {
            // Calculer le mouvement vertical
            time += speed;
            currentY = baseY + (float)(Math.sin(time) * amplitude);
            // Dessiner la pièce à sa position actuelle
            batch.draw(this.getTextureObjet(), this.getX(), currentY);
        }
    }

    @Override
    public void run() {
        while (isAnimating) {
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopAnimation() {
        isAnimating = false;
    }

    @Override
    public void dispose() {
        stopAnimation();
        super.dispose();
    }
}