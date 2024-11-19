package com.mathmaurer.objets;

import com.badlogic.gdx.graphics.Texture;

public class Block extends Objet {
    //**** VARIABLES ****//
    private Texture imgBloc; // Utilisation de Texture pour LibGDX

    //**** CONSTRUCTEUR ****//
    public Block(int x, int y) {
        super(x, y, 43, 65); // Appel du constructeur de la classe parent
        this.imgBloc = new Texture("images/bloc.png"); // Chargement de la texture
    }

    //**** GETTERS ****//
    public Texture getImgBloc() {
        return imgBloc;
    }

    //**** SETTERS ****//

    //**** MÉTHODES ****//
    @Override
    public void dispose() {
        if (imgBloc != null) {
            imgBloc.dispose(); // Libérer les ressources pour éviter les fuites mémoire
        }
    }
}
