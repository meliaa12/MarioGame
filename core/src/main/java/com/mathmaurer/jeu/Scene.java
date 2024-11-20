package com.mathmaurer.jeu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mathmaurer.personnages.Mario;
import com.mathmaurer.objets.TuyauRouge;
import com.mathmaurer.objets.Block;
import com.mathmaurer.objets.Piece;
import com.mathmaurer.personnages.Champ;
import com.mathmaurer.personnages.Tortue;


public class Scene implements Screen {
    private SpriteBatch batch;

    // Textures
    private Texture fond1, fond2, chateau1, depart, drapeau, chateauFin;

    // Objets du jeu
    private Mario mario;
    private Array<TuyauRouge> tuyaux;
    private Array<Block> blocks;
    private Array<Piece> pieces;
    private Array<Champ> champs;
    private Array<Tortue> tortues;

    // Positions
    private int xFond1, xFond2;
    private int dx, xPos;

    public Scene() {
    

        // Chargement des textures
        fond1 = new Texture("images/fondEcran.png");
        fond2 = new Texture("images/fondEcran.png");
        chateau1 = new Texture("images/chateau1.png");
        depart = new Texture("images/depart.png");
        drapeau = new Texture("images/drapeau.png");
        chateauFin = new Texture("images/chateauFin.png");

        batch = new SpriteBatch();

        // Initialisation des positions
        xFond1 = 0;
        xFond2 = 700;
        dx = 0;
        xPos = 220;

        // Création de Mario
        mario = new Mario(300, 245);

        // Initialisation des tuyaux
        tuyaux = new Array<>();
        tuyaux.add(new TuyauRouge(600, 230));
        tuyaux.add(new TuyauRouge(1000, 230));
        // Ajoute les autres tuyaux ici...

        // Initialisation des blocs
        blocks = new Array<>();
        blocks.add(new Block(400, 180));
        blocks.add(new Block(1200, 180));
        // Ajoute les autres blocs ici...

        // Initialisation des pièces
        pieces = new Array<>();
        pieces.add(new Piece(402, 145));
        pieces.add(new Piece(1202, 140));
        // Ajoute les autres pièces ici...

        // Initialisation des ennemis (champs et tortues)
        champs = new Array<>();
        champs.add(new Champ(800, 263));
        champs.add(new Champ(1100, 263));
        // Ajoute les autres champs ici...

        tortues = new Array<>();
        tortues.add(new Tortue(950, 243));
        tortues.add(new Tortue(1500, 243));
        // Ajoute les autres tortues ici...
    }

   
    // Getters
    public Mario getMario() {
        return this.mario;
    }
    
    public int getxFond1() {
    return xFond1;
   } 

    public int getxFond2() {
    return xFond2;
   }

    public int getDx() {
    return dx;
   }

     public int getxPos() {
    return xPos;
    }



// Setters
public void setxFond1(int xFond1) {
    this.xFond1 = xFond1;
}

public void setxFond2(int xFond2) {
    this.xFond2 = xFond2;
}

public void setDx(int dx) {
    this.dx = dx;
}

public void setxPos(int xPos) {
    this.xPos = xPos;
}

    @Override
    public void render(float delta) {
        // Mise à jour des positions
        update();

        // Dessin des éléments
        batch.begin();

        // Dessin des fonds
        batch.draw(fond1, xFond1, 0);
        batch.draw(fond2, xFond2, 0);

        // Dessin de Mario
        mario.render(batch); // Appel de la méthode render de Mario pour dessiner


        // Dessin des tuyaux
        for (TuyauRouge tuyau : tuyaux) {
            tuyau.render(batch);
        }

        // Dessin des blocs
        for (Block block : blocks) {
            block.render(batch);
        }

        // Dessin des pièces
        for (Piece piece : pieces) {
            piece.render(batch);
        }

        // Dessin des ennemis
        for (Champ champ : champs) {
            champ.render(batch);
        }

        for (Tortue tortue : tortues) {
            tortue.render(batch);
        }

        batch.end();
    }

    private void update() {
        // Déplacement du fond
        if (xPos >= 0 && xPos <= 4430) {
            xPos += dx;
            xFond1 -= dx;
            xFond2 -= dx;
        }

        // Boucle infinie des fonds
        if (xFond1 <= -800) xFond1 = 800;
        if (xFond2 <= -800) xFond2 = 800;
        if (xFond1 >= 800) xFond1 = -800;
        if (xFond2 >= 800) xFond2 = -800;

        // Mise à jour de Mario et autres objets
        mario.update();
        for (Champ champ : champs) champ.run();
        for (Tortue tortue : tortues) tortue.run();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fond1.dispose();
        fond2.dispose();
        // Disposez des autres textures...
    }

    // Autres méthodes vides requises par l'interface Screen
    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
