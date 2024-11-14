package com.mathmaurer.jeu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

public class Scene implements Screen {

    private Texture imgFond1, imgFond2, imgMario, imgChateau1, imgDepart;
    private SpriteBatch batch;

    private int xFond1, xFond2, dx, xPos; // Position du fond et de Mario
    private final int VITESSE_FOND = 2;
    private int positionLimiteDepart; // Position limite calculée pour Mario
    private final int largeurFond = 800; // Largeur d'un fond
    private boolean aDeplaceDroite; // Indicateur pour vérifier si Mario a déplacé à droite

    public Scene() {
        this.xFond1 = 0; // Position initiale du fond 1
        this.xFond2 = largeurFond; // Position initiale du fond 2
        this.dx = 0;
        this.xPos = 220; // Position initiale de Mario après `imgDepart`
        this.aDeplaceDroite = false; // Mario n'a pas encore déplacé à droite

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgMario = new Texture("images/marioMarcheDroite.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");

        batch = new SpriteBatch();

        // Calcul de la position de départ pour Mario après `imgDepart`
        positionLimiteDepart = 220 + imgDepart.getWidth();
        xPos = positionLimiteDepart; // Position de Mario après `imgDepart`

        // Gestion des entrées du clavier
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    dx = VITESSE_FOND;
                    aDeplaceDroite = true; // Mario a déplacé à droite
                }
                if (keycode == Input.Keys.LEFT && aDeplaceDroite && xPos > positionLimiteDepart) {
                    dx = -VITESSE_FOND; // Permet de déplacer à gauche uniquement après s'être déplacé à droite
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

    @Override
    public void show() {
        // Aucune modification ici, déjà initialisé dans le constructeur
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Déplacer Mario et le fond
        deplacementMario();
        deplacementFond();

        // Dessin des éléments à l'écran
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);
        batch.draw(imgMario, xPos, 55); // Utilisation de xPos pour la position de Mario

        // Affichage du château et du départ, qui se déplacent avec le fond
        // Ces éléments se déplacent avec la scène
        batch.draw(imgChateau1, 10 + xFond1, 60); // Le château suit le fond
        batch.draw(imgDepart, positionLimiteDepart + xFond1, 60); // Le départ suit le fond

        batch.end();
    }

    private void deplacementFond() {
        // Déplacement du fond
        xFond1 -= dx;
        xFond2 -= dx;

        // Réinitialiser la position du fond lorsqu'il est hors écran
        if (xFond1 <= -largeurFond) {
            xFond1 = xFond2 + largeurFond;
        }
        if (xFond2 <= -largeurFond) {
            xFond2 = xFond1 + largeurFond;
        }

        // Réinitialiser lorsque le fond dépasse la largeur de l'écran
        if (xFond1 >= largeurFond) {
            xFond1 = xFond2 - largeurFond;
        }
        if (xFond2 >= largeurFond) {
            xFond2 = xFond1 - largeurFond;
        }
    }

    private void deplacementMario() {
        // Déplacement de Mario avec la position xPos
        if (dx != 0) {
            xPos += dx; // Mise à jour de la position horizontale de Mario
        }

        // Limites de mouvement de Mario
        if (xPos < positionLimiteDepart) {
            xPos = positionLimiteDepart; // Pas de déplacement avant `imgDepart`
        } else if (xPos > Gdx.graphics.getWidth() - imgMario.getWidth()) {
            xPos = Gdx.graphics.getWidth() - imgMario.getWidth(); // Pas de dépassement de l'écran
        }

        // Limiter la position de Mario pour ne pas dépasser le drapeau
        if (xPos >= positionLimiteDepart + imgDepart.getWidth()) {
            xPos = positionLimiteDepart + imgDepart.getWidth(); // Ne pas dépasser la position du départ
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        imgFond1.dispose();
        imgFond2.dispose();
        imgMario.dispose();
        imgChateau1.dispose();
        imgDepart.dispose();
    }
}
