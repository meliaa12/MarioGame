package com.mathmaurer.jeu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Block;
import com.mathmaurer.objets.TuyauRouge;
import com.mathmaurer.personnages.Mario;

public class Scene implements Screen {

    // **** Variables **** //
    private Texture imgFond1, imgFond2, imgChateau1, imgDepart;
    private SpriteBatch batch;

    private int xFond1, xFond2, dx, xPos; // Position du fond et de Mario
    private final int VITESSE_FOND = 2; // Vitesse de déplacement du fond
    private final int largeurFond = 800; // Largeur d'un fond

    private Mario mario;
    private TuyauRouge tuyauRouge1;
    private Block bloc1;
    InputAdapter inputAdapter;

    public Scene() {
        // Initialisation des positions
        this.xFond1 = -50;
        this.xFond2 = 750;
        this.dx = 0;
        this.xPos = -1;

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");

        batch = new SpriteBatch();

        // Initialisation des objets
        mario = new Mario(300, 55);
        tuyauRouge1 = new TuyauRouge(600, 55);
        bloc1 = new Block(400, 180);

        // Gestion des entrées du clavier
        inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    dx = VITESSE_FOND;
                }
                if (keycode == Input.Keys.LEFT) {
                    dx = -VITESSE_FOND;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
                    dx = 0; // Arrêt du mouvement
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputAdapter);
    }

    // **** Méthodes principales **** //
    private void deplacementFond() {
        // Mise à jour des positions des éléments du jeu lors du déplacement
        if (xPos >= 0) {
            xPos += dx;
            xFond1 -= dx;
            xFond2 -= dx;
        }

        // Permanence du fond d'écran
        if (xFond1 <= -largeurFond) {
            xFond1 = largeurFond;
        }
        if (xFond2 <= -largeurFond) {
            xFond2 = largeurFond;
        }
        if (xFond1 >= largeurFond) {
            xFond1 = -largeurFond;
        }
        if (xFond2 >= largeurFond) {
            xFond2 = -largeurFond;
        }
    }

    private void deplacementMario() {
        // Déplacement de Mario avec la position xPos
        if (dx != 0) {
            xPos += dx;
            mario.setX(xPos); // Mise à jour de la position de Mario
        }

        // Mario ne peut pas aller avant la position de départ
        if (xPos < 300) {
            xPos = 300;
            mario.setX(xPos); // Mise à jour de Mario
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Déplacement des fonds et des objets
        deplacementFond();
        deplacementMario();

        // Dessin des éléments
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);
        batch.draw(mario.getImgMario(), mario.getX(), mario.getY(), mario.getLargeur(), mario.getHauteur());
        batch.draw(imgChateau1, 10 - xPos, 95);
        batch.draw(imgDepart, 220 - xPos, 234);
        batch.draw(tuyauRouge1.getImgTuyauRouge(), tuyauRouge1.getX() - xPos, tuyauRouge1.getY());
        batch.draw(bloc1.getImgBloc(), bloc1.getX() - xPos, bloc1.getY());
        
        batch.end();
    }

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

    @Override
    public void dispose() {
        batch.dispose();
        imgFond1.dispose();
        imgFond2.dispose();
        imgChateau1.dispose();
        imgDepart.dispose();
        mario.dispose();
        tuyauRouge1.dispose();
        bloc1.dispose();
    }
}
