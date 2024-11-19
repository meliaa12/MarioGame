package com.mathmaurer.objets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TuyauRouge extends Objet {
    //**** VARIABLES ****//
    private Texture imgTuyauRouge;

    //**** CONSTRUCTEUR ****//
    public TuyauRouge(float x, float y) {
        super(x, y, 43, 65); // Appel du constructeur de la classe parent
        this.imgTuyauRouge = new Texture("images/tuyauRouge.png"); // Chargement de l'image
    }

    //**** GETTERS ****//
    public Texture getImgTuyauRouge() {
        return imgTuyauRouge;
    }

    //**** MÉTHODES ****//
    @Override
    public void dispose() {
        if (imgTuyauRouge != null) {
            imgTuyauRouge.dispose(); // Libération de la ressource texture
        }
    }

	public Vector2 getPosition() {
		return new Vector2(getX(), getY());
	}
}
