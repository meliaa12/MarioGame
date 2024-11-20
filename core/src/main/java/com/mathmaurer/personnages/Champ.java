package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Objet;
import com.mathmaurer.personnages.Personnage;

public class Champ extends Personnage implements Runnable {

    private Texture imgChamp;
    private float x, y; // Position of the mushroom
    private final int PAUSE = 15; // Pause in milliseconds
    private int dxChamp; // Movement step of the mushroom
    private boolean versDroite; // Direction of the mushroom
    private boolean vivant; // Is the mushroom alive
    private float width, height; // Dimensions of the mushroom

    // Constructor
    public Champ(float x, float y) {
        // Appel explicite du constructeur de la classe parente
        super(x, y, 27, 30); // 27 et 30 sont les dimensions spécifiques du Champ
        
        this.versDroite = true;
        this.vivant = true;
        this.dxChamp = 1;

        this.imgChamp = new Texture("images/champArretDroite.png"); // Path to the image

        // Start a thread for movement logic
        Thread chronoChamp = new Thread(this);
        chronoChamp.start();
    }

    // Getters
    public Texture getImgChamp() {
        return imgChamp;
    }

    public boolean isVivant() {
        return vivant;
    }

    // Setters
    public void setVivant(boolean vivant) {
        this.vivant = vivant;
    }

    public void setVersDroite(boolean versDroite) {
        this.versDroite = versDroite;
    }

    // Movement logic
    public void bouge() {
        if (this.versDroite) {
            this.dxChamp = 1;
        } else {
            this.dxChamp = -1;
        }
        this.x += this.dxChamp;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20); // Wait before starting movement
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            if (this.vivant) {
                this.bouge();
                try {
                    Thread.sleep(PAUSE); // Control movement speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void contact(Objet objet) {
        if (/* collision logic */ true && this.versDroite) {
            this.versDroite = false;
            this.dxChamp = -1;
        } else if (/* collision logic */ true && !this.versDroite) {
            this.versDroite = true;
            this.dxChamp = 1;
        }
    }

    public void contact(Champ personnage) {
        if (/* collision logic */ true && this.versDroite) {
            this.versDroite = false;
            this.dxChamp = -1;
        } else if (/* collision logic */ true && !this.versDroite) {
            this.versDroite = true;
            this.dxChamp = 1;
        }
    }

    public void meurt() {
        this.vivant = false;
        this.imgChamp = new Texture(this.versDroite ? "images/champEcraseDroite.png" : "images/champEcraseGauche.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(imgChamp, x, y, width, height);
    }

    public void dispose() {
        imgChamp.dispose();
    }
}
