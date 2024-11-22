package com.mathmaurer.jeu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mathmaurer.objets.Block;
import com.mathmaurer.objets.TuyauRouge;
import com.mathmaurer.personnages.Mario;

public class Scene implements Screen {
    //**** Variables ****//
    private Texture imgFond1, imgFond2, imgChateau1, imgDepart;
    private SpriteBatch batch;
    private Stage stage;

    private int xFond1, xFond2, dx, xPos;
    private final int VITESSE_FOND = 2;
    private final int largeurFond = 700;
    private int positionLimiteDepart;
    private boolean aDeplaceDroite;
    private boolean departAtteint;

    private Mario mario;
    private List<TuyauRouge> tuyauxRouges;
    private List<Block> blocs;

    private final int HAUTEUR_SOL = 55; // Sol à y = 0
    private final int HAUTEUR_PLAFOND = 300; // Par exemple, plafond à y = 300

    public Scene() {
        // Initialisation des positions
        this.xFond1 = 0;
        this.xFond2 = largeurFond;
        this.dx = 0;
        this.xPos = 220;
        this.aDeplaceDroite = false;
        this.departAtteint = false;
        this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");

        batch = new SpriteBatch();

        // Initialisation des objets
        mario = new Mario(220, 55, 43, 65); // Initialisation avec la position de départ

        // Initialisation des tuyaux et des blocs
        tuyauxRouges = new ArrayList<>();
        blocs = new ArrayList<>();

        // Ajouter des tuyaux rouges
        tuyauxRouges.add(new TuyauRouge(600, 55));
        tuyauxRouges.add(new TuyauRouge(800, 55));
        tuyauxRouges.add(new TuyauRouge(1000, 55));

        // Ajouter des blocs
        blocs.add(new Block(400, 180));
        blocs.add(new Block(420, 180));
        blocs.add(new Block(440, 180));

        // Position limite de départ pour Mario
        positionLimiteDepart = 220 + imgDepart.getWidth();
        xPos = positionLimiteDepart;

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    dx = VITESSE_FOND; // Vitesse du déplacement vers la droite
                    mario.setVersDroite(true); // Mario se déplace à droite
                    mario.setMarche(true); // Mario est en mouvement
                }
                if (keycode == Input.Keys.LEFT) {
                    dx = -VITESSE_FOND; // Vitesse du déplacement vers la gauche
                    mario.setVersDroite(false); // Mario se déplace à gauche
                    mario.setMarche(true); // Mario est en mouvement
                }
                if (keycode == Input.Keys.SPACE) {
                    mario.jump(HAUTEUR_SOL); // Mario saute
                }
                if (keycode == Input.Keys.UP) {
                    mario.climb(true, HAUTEUR_SOL, HAUTEUR_PLAFOND); // Monte
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
                    dx = 0; // Arrêter le mouvement lorsque la touche est relâchée
                    mario.setMarche(false); // Mario arrête de bouger
                }
                return false;
            }
        };

        Gdx.input.setInputProcessor(inputAdapter); // Configurer le processeur d'entrées une seule fois
    }

    @Override
    public void show() {
        // Cette méthode est appelée lorsque ce Screen devient le Screen actuel de l'application
    }

    @Override
    public void render(float delta) {
        // Mettre à jour l'état et l'animation de Mario
        mario.update(HAUTEUR_SOL); // Mettre à jour l'état de Mario (saut, animation, etc.)

        // Déplacer Mario et le fond
        deplacementMario();
        deplacementFond();
        deplacementObjets();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Effacer l'écran
        // Commencer le dessin avec le batch
        batch.begin();

        // Dessiner les éléments de l'arrière-plan
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);

        // Dessiner Mario à sa position actuelle
        mario.dessine(batch);

        // Dessiner les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            batch.draw(tuyau.getTextureObjet(), tuyau.getX(), tuyau.getY());
        }

        // Dessiner les blocs
        for (Block bloc : blocs) {
            batch.draw(bloc.getTextureObjet(), bloc.getX(), bloc.getY());
        }

        // Fin du dessin
        batch.end();
    }

    public int getxPos() {
        return xPos;
    }

    public int getDx() {
        return dx;
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
            mario.setX(mario.getX() + dx); // Déplacement de Mario
        }

        // Limite de départ
        if (mario.getX() < positionLimiteDepart) {
            mario.setX(positionLimiteDepart); // Empêcher Mario de dépasser le point de départ
        }

        // Limite du bord droit de l'écran
        if (mario.getX() > Gdx.graphics.getWidth() - mario.getLargeur()) {
            mario.setX(Gdx.graphics.getWidth() - mario.getLargeur()); // Mario ne dépasse pas l'écran
        }
    }

    private void deplacementObjets() {
        // Déplacer les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            tuyau.setX(tuyau.getX() - dx);
        }

        // Déplacer les blocs
        for (Block bloc : blocs) {
            bloc.setX(bloc.getX() - dx);
        }
    }

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
        for (TuyauRouge tuyau : tuyauxRouges) {
            tuyau.dispose();
        }
        for (Block bloc : blocs) {
            bloc.dispose();
        }
        // Libérer la texture de Mario
    }
}