package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;

public class Mario extends Personnage {

    //**** VARIABLES ****//
    private Texture imgMario;

    //**** CONSTRUCTEUR ****//
   
    public Mario(float x, float y) {
        super(x, y, 28, 50, "mario"); // Ajout du nom directement
        this.imgMario = new Texture("images/marioMarcheDroite.png");
    }

    //**** GETTERS ****//
    public Texture getImgMario() {
        return imgMario;
    }

    

    //**** SETTERS ****//
    // Aucun setter requis pour l'image, mais vous pouvez en ajouter si nécessaire.

    //**** MÉTHODES ****//
    /**
     * Libère les ressources de Mario.
     */
    @Override
    public void dispose() {
        super.dispose(); // Appel à la méthode dispose() de la classe parent
        if (imgMario != null) {
            imgMario.dispose(); // Libération de la texture Mario
        }
    }
}
