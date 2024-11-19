package com.mathmaurer.jeu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

public class Scene implements Screen {

    private Texture imgFond1, imgFond2, imgChateau1, imgDepart;
    private SpriteBatch batch;
    private Mario mario;

    private int xFond1, xFond2, dx, xPos;
    private final int VITESSE_FOND = 2;
    private final int largeurFond = 700;
    private int positionLimiteDepart;
    private boolean aDeplaceDroite;
    private boolean departAtteint;

    private final int HAUTEUR_SOL = 0; // Sol à y = 0
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

        batch = new SpriteBatch();

        // Initialisation de Mario
        mario = new Mario(220, 55); // Initialisation avec la position de départ

        // Position limite de départ pour Mario
        positionLimiteDepart = 220 + imgDepart.getWidth();
        xPos = positionLimiteDepart;


       InputAdapter inputAdapter = new InputAdapter() {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.RIGHT) {
            dx = VITESSE_FOND; // Vitesse du déplacement vers la droite
            mario.setDirection(true); // Mario se déplace à droite
            mario.setMoving(true); // Mario est en mouvement
        }
        if (keycode == Input.Keys.LEFT) {
            dx = -VITESSE_FOND; // Vitesse du déplacement vers la gauche
            mario.setDirection(false); // Mario se déplace à gauche
            mario.setMoving(true); // Mario est en mouvement
        }
        if (keycode == Input.Keys.SPACE) {
            mario.jump(getHauteurSol()); // Mario saute
        }
        if (keycode == Input.Keys.UP) {
            mario.climb(true); // Monte
        }
        if (keycode == Input.Keys.DOWN) {
            mario.climb(false); // Descend
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            dx *= 2; // Doubler la vitesse
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
            dx = 0; // Arrêter le mouvement lorsque la touche est relâchée
            mario.setMoving(false); // Mario arrête de bouger
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            dx /= 2; // Revenir à la vitesse normale
        }
        return false;
    }
};

        
        Gdx.input.setInputProcessor(inputAdapter); // Configurer le processeur d'entrées une seule fois
        
    }
    
     // Getter pour la hauteur du sol
     public int getHauteurSol() {
        return HAUTEUR_SOL;
    }

    // Getter pour la hauteur du plafond
    public int getHauteurPlafond() {
        return HAUTEUR_PLAFOND;
    }
    
    @Override
    public void show() {
        // Initialisation déjà effectuée dans le constructeur
    }


    @Override
    public void render(float delta) {
        // Mettre à jour l'état et l'animation de Mario
        mario.update(getHauteurSol()); // Mettre à jour l'état de Mario (saut, animation, etc.)
        
        // Déplacer Mario et le fond
        deplacementMario();
        deplacementFond();
        
        // Commencer le dessin avec le batch
        batch.begin();
        
        // Dessiner les éléments de l'arrière-plan
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);
        
        // Dessiner Mario à sa position actuelle
        batch.draw(mario.getTexture(), mario.getX(), mario.getY());
        
        // Afficher le château et le départ si Mario n'a pas encore dépassé la limite
        if (!departAtteint) {
            batch.draw(imgChateau1, 10 + xFond1, 60);
            batch.draw(imgDepart, 200 + xFond1, 60);
    
            // Vérifier si Mario a dépassé la position limite de départ
            if (mario.getX() >= positionLimiteDepart + imgDepart.getWidth()) {
                departAtteint = true;
            }
        }
        
        // Fin du dessin
        batch.end();
        
        // Déplacer Mario horizontalement (dx correspond à la vitesse ou direction)
        mario.move(dx);
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
            mario.move(dx); // Déplacement de Mario
        }

        // Limite de départ
        if (mario.getX() < positionLimiteDepart) {
            mario.move(positionLimiteDepart - mario.getX()); // Empêcher Mario de dépasser le point de départ
        }

        // Limite du bord droit de l'écran
        if (mario.getX() > Gdx.graphics.getWidth() - mario.getTexture().getWidth()) {
            mario.move(Gdx.graphics.getWidth() - mario.getTexture().getWidth() - mario.getX()); // Mario ne dépasse pas l'écran
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
        imgChateau1.dispose();
        imgDepart.dispose();
        mario.getTexture().dispose(); // Libérer la texture de Mario
    }
}

