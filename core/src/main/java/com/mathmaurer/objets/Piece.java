package com.mathmaurer.objets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Piece extends Objet implements Runnable {

    // **** VARIABLES **** //
    private int compteur;
    private final int PAUSE = 15; // Temps d'attente entre 2 tours de boucle
    private Texture texture2;

    // **** CONSTRUCTEUR **** //
    public Piece(float x, float y) {
        super(x, y, 30, 30, "images/piece1.png"); // Appel au constructeur de Objet
        this.texture2 = new Texture("images/piece2.png"); // Deuxième texture pour l'animation
    }

    // **** METHODES **** //
    @Override
    public void render(SpriteBatch batch) {
        // Appeler bouge pour changer de texture
        this.bouge();
        // Dessiner l'objet avec la texture actuelle
        batch.draw(this.getTextureObjet(), this.getX(), this.getY(), this.getLargeur(), this.getHauteur());
    }

    public void bouge() {
        // Animation : alterner entre deux textures
        this.compteur++;
        if (this.compteur / 100 == 0) {
            this.setTexture(this.texture2); // Changer vers texture2
        } else {
            this.setTexture(super.getTextureObjet()); // Revenir à la texture par défaut
        }
        if (this.compteur == 200) {
            this.compteur = 0; // Réinitialiser le compteur
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20); // Attendre 20 ms avant d'exécuter l'animation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            this.bouge();
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose(); // Libérer la texture parent
        if (this.texture2 != null) {
            this.texture2.dispose(); // Libérer la deuxième texture
        }
    }

    // Setter pour changer la texture courante
    private void setTexture(Texture texture) {
        this.textureObjet = texture;
    }
}