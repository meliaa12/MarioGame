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
import com.mathmaurer.objets.Block;
import com.mathmaurer.objets.Objet;
import com.mathmaurer.objets.TuyauRouge;
import com.mathmaurer.personnages.Champ;
import com.mathmaurer.personnages.Mario;
import com.mathmaurer.personnages.Tortue;

public class Scene implements Screen {
    //**** Variables ****//
    private Texture imgFond1, imgFond2, imgChateau1, imgDepart, imgChateauFin, imgDrapeau;
    private SpriteBatch batch;

    private int xFond1, xFond2, dx, xPos;
    private final int VITESSE_FOND = 4; // Vitesse de déplacement de l'écran
    private final int VITESSE_MARIO = 2; // Vitesse de déplacement de Mario
    private final int largeurFond = 700;
    private int positionLimiteDepart;
    private boolean aDeplaceDroite;
    private boolean departAtteint;

    private Mario mario;
    private List<TuyauRouge> tuyauxRouges;
    private List<Block> blocs;
    private List<Champ> champs;
    private List<Tortue> tortues;

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

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");
        imgChateauFin = new Texture("images/chateauFin.png");
        imgDrapeau = new Texture("images/drapeau.png");

        batch = new SpriteBatch();

        // Initialisation des objets
        mario = new Mario(220, 55, 43, 65); // Initialisation avec la position de départ

        // Initialisation des tuyaux et des blocs
        tuyauxRouges = new ArrayList<>();
        blocs = new ArrayList<>();
        champs = new ArrayList<>();
        tortues = new ArrayList<>();

        // Ajouter des tuyaux rouges
        tuyauxRouges.add(new TuyauRouge(600, 55));
        tuyauxRouges.add(new TuyauRouge(1000, 55));
        tuyauxRouges.add(new TuyauRouge(1600, 55));
        tuyauxRouges.add(new TuyauRouge(1900, 55));
        tuyauxRouges.add(new TuyauRouge(2500, 55));
        tuyauxRouges.add(new TuyauRouge(3000, 55));
        tuyauxRouges.add(new TuyauRouge(3800, 55));
        tuyauxRouges.add(new TuyauRouge(4500, 55));

        // Ajouter des blocs
        blocs.add(new Block(400, 180));
        blocs.add(new Block(1200, 180));
        blocs.add(new Block(1270, 170));
        blocs.add(new Block(1340, 160));
        blocs.add(new Block(2000, 180));
        blocs.add(new Block(2600, 160));
        blocs.add(new Block(2650, 180));
        blocs.add(new Block(3500, 160));
        blocs.add(new Block(3550, 140));
        blocs.add(new Block(4000, 170));
        blocs.add(new Block(4200, 200));
        blocs.add(new Block(4300, 210));

         // Ajouter des champs
        champs.add(new Champ(800, 55));
        champs.add(new Champ(1100, 55));
        champs.add(new Champ(2100, 55));
        champs.add(new Champ(2400, 55));
        champs.add(new Champ(3200, 55));
        champs.add(new Champ(3500, 55));
        champs.add(new Champ(3700, 55));
        champs.add(new Champ(4500, 55));

        // Ajouter des tortues
        tortues.add(new Tortue(950, 55));
        tortues.add(new Tortue(1500, 55));
        tortues.add(new Tortue(1800, 55));
        tortues.add(new Tortue(2400, 55));
        tortues.add(new Tortue(3100, 55));
        tortues.add(new Tortue(3600, 55));
        tortues.add(new Tortue(3900, 55));
        tortues.add(new Tortue(4200, 55));
        tortues.add(new Tortue(4400, 55));

        // Position limite de départ pour Mario
        positionLimiteDepart = 220 + imgDepart.getWidth();
        xPos = positionLimiteDepart;

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (mario.isVivant()) {
                    if (keycode == Input.Keys.RIGHT) {
                        if (xPos == -1) {
                            xPos = 0;
                            xFond1 = -50;
                            xFond2 = 750;
                        }
                        mario.setMarche(true);
                        mario.setVersDroite(true);
                        dx = VITESSE_FOND;
                    }
                    if (keycode == Input.Keys.LEFT) {
                        if (xPos == 4431) {
                            xPos = 4430;
                            xFond1 = -50;
                            xFond2 = 750;
                        }
                        mario.setMarche(true);
                        mario.setVersDroite(false);
                        dx = -VITESSE_FOND;
                    }
                    if (keycode == Input.Keys.SPACE) {
                        mario.jump(HAUTEUR_SOL);
                    }
                    if (keycode == Input.Keys.UP) {
                        mario.climb(true, HAUTEUR_SOL, HAUTEUR_PLAFOND);
                    }
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
                    mario.setMarche(false);
                    dx = 0;
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
        List<Objet> objets = new ArrayList<>();
        objets.addAll(tuyauxRouges);
        objets.addAll(blocs);
        mario.update(HAUTEUR_SOL, objets); // Mettre à jour l'état de Mario (saut, animation, etc.)

        // Déplacer Mario et le fond
        deplacementMario();
        deplacementFond();
        deplacementObjets();

        gererCollisions();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Effacer l'écran
        // Commencer le dessin avec le batch
        batch.begin();

        // Dessiner les éléments de l'arrière-plan
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);

        // Dessiner le château et le panneau
        if (!departAtteint) {
            batch.draw(imgChateau1, 10 + xFond1, 60);
            batch.draw(imgDepart, 200 + xFond1, 60);

            // Vérifier si Mario a dépassé la position limite de départ
            if (mario.getX() >= positionLimiteDepart + imgDepart.getWidth()) {
                departAtteint = true;
            }
        }

        // Dessiner le château de fin et le drapeau
        batch.draw(imgChateauFin, 5000, 55);
        batch.draw(imgDrapeau, 4950, 55);

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

        // Dessiner les champs
        for (Champ champ : champs) {
            champ.dessine(batch);
        }

        // Dessiner les tortues
        for (Tortue tortue : tortues) {
            tortue.dessine(batch);
        }

        // Fin du dessin
        batch.end();
    }

    // Getters et Setters
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getxFond1() {
        return xFond1;
    }

    public void setxFond1(int xFond1) {
        this.xFond1 = xFond1;
    }

    public int getxFond2() {
        return xFond2;
    }

    public void setxFond2(int xFond2) {
        this.xFond2 = xFond2;
    }

    private void deplacementFond() {
        // Déplacement du fond en fonction de la vitesse de Mario
        xFond1 -= dx * 2; // Déplacement plus rapide du fond
        xFond2 -= dx * 2;

        // Réinitialisation du fond lorsqu'il sort de l'écran
        if (xFond1 <= -largeurFond) {
            xFond1 = xFond2 + largeurFond;
        }
        if (xFond2 <= -largeurFond) {
            xFond2 = xFond1 + largeurFond;
        }
    }

    private void deplacementMario() {
        mario.setX(Gdx.graphics.getWidth() / 2 - mario.getLargeur() / 2);

        // Déplacement de Mario avec la position xPos
        if (dx != 0) {
            mario.setX(mario.getX() + dx * VITESSE_MARIO); // Déplacement de Mario
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
            tuyau.setX(tuyau.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les blocs
        for (Block bloc : blocs) {
            bloc.setX(bloc.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les champs
        for (Champ champ : champs) {
            champ.setX(champ.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les tortues
        for (Tortue tortue : tortues) {
            tortue.setX(tortue.getX() - dx * 2); // Déplacement plus rapide des objets
        }
    }

    private void gererCollisions() {
        // Gérer les collisions entre Mario et les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            if (mario.contactAvant(tuyau) || mario.contactArriere(tuyau) || mario.contactDessous(tuyau) || mario.contactDessus(tuyau)) {
                // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                mario.setMarche(false);
                dx = 0;
            }
        }

        // Gérer les collisions entre Mario et les blocs
        for (Block bloc : blocs) {
            if (mario.contactAvant(bloc) || mario.contactArriere(bloc) || mario.contactDessous(bloc) || mario.contactDessus(bloc)) {
                // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                mario.setMarche(false);
                dx = 0;
            }
        }

         // Gérer les collisions entre Mario et les champs
         for (Champ champ : champs) {
            if (mario.contactAvant(champ) || mario.contactArriere(champ) || mario.contactDessous(champ)) {
                if (mario.contactDessous(champ)) {
                    champ.meurt(); // Mario écrase le champ
                } else {
                    // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                    mario.setMarche(false);
                    dx = 0;
                }
            }
        }

        // Gérer les collisions entre Mario et les tortues
        for (Tortue tortue : tortues) {
            if (mario.contactAvant(tortue) || mario.contactArriere(tortue) || mario.contactDessous(tortue) ) {
                if (mario.contactDessous(tortue)) {
                    tortue.mourir(); // Mario écrase la tortue
                } else {
                    // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                    mario.setMarche(false);
                    dx = 0;
                }
            }
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
        imgChateauFin.dispose();
        imgDrapeau.dispose();
        for (TuyauRouge tuyau : tuyauxRouges) {
            tuyau.dispose();
        }
        for (Block bloc : blocs) {
            bloc.dispose();
        }
        for (Champ champ : champs) {
            champ.dispose();
        }
        for (Tortue tortue : tortues) {
            tortue.dispose();
        }
        // mario.getTexture().dispose(); // Libérer la texture de Mario
    }
}