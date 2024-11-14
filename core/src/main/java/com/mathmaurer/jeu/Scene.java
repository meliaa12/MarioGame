package com.mathmaurer.jeu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Scene implements Screen {

    private Texture imgFond1, imgFond2, imgMario, imgChateau1, imgDepart;
    private SpriteBatch batch;

    private int xFond1, xFond2, dx, xPos; // Position du fond et de Mario
    private final int VITESSE_FOND = 2;
    private final int largeurFond = 800; // Largeur d'un fond
    private int positionLimiteDepart; // Position limite calculée pour Mario
    private boolean aDeplaceDroite; // Indicateur pour vérifier si Mario a déplacé à droite
    private boolean departAtteint; // Indicateur pour vérifier si Mario a atteint le point de départ

    public Scene() {
        // Initialisation des positions
        this.xFond1 = 0;
        this.xFond2 = largeurFond;
        this.dx = 0;
        this.xPos = 220; // Position de départ de Mario
        this.aDeplaceDroite = false; // Mario n'a pas encore déplacé à droite
        this.departAtteint = false; // Le départ n'est pas encore atteint

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgMario = new Texture("images/marioMarcheDroite.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");

        batch = new SpriteBatch();

        // Position limite de départ pour Mario après `imgDepart`
        positionLimiteDepart = 220 + imgDepart.getWidth();
        xPos = positionLimiteDepart;

        // Gestion des entrées du clavier
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    dx = VITESSE_FOND;
                    aDeplaceDroite = true;
                }
                if (keycode == Input.Keys.LEFT && xPos > positionLimiteDepart) {
                    dx = -VITESSE_FOND; // Déplacement à gauche possible seulement après être allé à droite
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

        // Dessiner le fond
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);

        // Dessiner Mario
        batch.draw(imgMario, xPos, 55); // Mario suit la position `xPos`

        // Affichage du château et du départ uniquement si Mario n'a pas dépassé le point limite
        if (!departAtteint) {
            // Afficher le château et le départ uniquement tant que Mario n'a pas dépassé la position limite
            batch.draw(imgChateau1, 10 + xFond1, 60); // Le château suit le fond
            batch.draw(imgDepart, positionLimiteDepart + xFond1, 60); // Le départ suit le fond

            // Vérification si Mario a dépassé la limite de départ
            if (xPos >= positionLimiteDepart + imgDepart.getWidth()) {
                departAtteint = true; // Une fois Mario a passé le point de départ, on marque l'événement
            }
        }

        batch.end();
    }

    private void deplacementFond() {
        // Déplacement du fond en fonction de la vitesse de Mario
        xFond1 -= dx;
        xFond2 -= dx;

        // Réinitialisation du fond lorsqu'il sort de l'écran
        if (xFond1 <= -largeurFond) {
            xFond1 = xFond2 + largeurFond;
        }
        if (xFond2 <= -largeurFond) {
            xFond2 = xFond1 + largeurFond;
        }
    }

    private void deplacementMario() {
        // Déplacement de Mario avec la position xPos
        if (dx != 0) {
            xPos += dx; // Mise à jour de la position de Mario
        }

        // Limite de départ : Mario ne peut pas aller avant la position de départ
        if (xPos < positionLimiteDepart) {
            xPos = positionLimiteDepart; // Mario ne peut pas aller avant `positionLimiteDepart`
        }

        // Limite du bord droit de l'écran
        if (xPos > Gdx.graphics.getWidth() - imgMario.getWidth()) {
            xPos = Gdx.graphics.getWidth() - imgMario.getWidth(); // Mario ne dépasse pas l'écran
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
