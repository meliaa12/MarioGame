
package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



public class Mario extends Personnage{

    private Texture textureMario;
    private Rectangle bounds; // Pour gérer la position et les collisions
    
    private boolean saut; // vrai si Mario saute
    private float vitesseY; // vitesse verticale
    private final float GRAVITE = -0.5f; // force de gravité
    private final float VITESSE_SAUT = 10f; // force du saut
    private boolean vivant = true; // Variable indiquant si Mario est vivant

    private boolean marche;
    private boolean versDroite;

    // Constructeur
    public Mario(float x, float y) {
        // Appel du constructeur de la classe parent Personnage
        super(x, y, 28, 50);  // Utilisation des valeurs spécifiques pour la taille

        this.textureMario = new Texture("images/marioArretDroite.png");
        this.bounds = new Rectangle(x, y, 28, 50);
        this.saut = false;
        this.vivant = true;
        this.vitesseY = 0;
    }

    // Getter pour l'affichage
    public Texture getTexture() {
        return textureMario;
    }
    public boolean isVivant() {
        return vivant;
    }

    public Rectangle getBounds() {
        return bounds;
    }

 // Getter et Setter pour marche
 public boolean isMarche() {
    return marche;
}

public void setMarche(boolean marche) {
    this.marche = marche;
}


public void setVersDroite(boolean versDroite) {
    this.versDroite = versDroite;
}

public void setSaut(boolean saut) {
    this.saut = saut;
}

      


// Méthode pour mettre à jour l'état de vie de Mario
public void setVivant(boolean vivant) {
    this.vivant = vivant;
}

// Méthode pour savoir si Mario est en train de sauter
public boolean isSaut() {
    return saut;
}

    // Méthodes
    public void update() {
        // Gravité
        if (saut || bounds.y > 0) {
            vitesseY += GRAVITE;
            bounds.y += vitesseY;
        }

        // Gérer le contact avec le sol
        if (bounds.y < 0) {
            bounds.y = 0;
            saut = false;
            vitesseY = 0;
        }
    }

    public void sauter() {
        if (!saut) {
            saut = true;
            vitesseY = VITESSE_SAUT;
        }
    }

    public void dessiner(SpriteBatch batch) {
        batch.draw(textureMario, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void mourir() {
        vivant = false;
        textureMario = new Texture("images/marioMeurt.png");
    }

    public void dispose() {
        textureMario.dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureMario, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    

   



}
