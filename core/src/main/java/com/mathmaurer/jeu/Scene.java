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
    private final int VITESSE_FOND = 1;
    private final int largeurFond = 700;
    private int positionLimiteDepart;
    private boolean aDeplaceDroite;
    private boolean departAtteint;

    private final int HAUTEUR_SOL = 0; // Sol à y = 0
    private final int HAUTEUR_PLAFOND = 360; // Par exemple, plafond à y = 300

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
        mario = new Mario(250, 55); // Initialisation avec la position de départ

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
                    if (!mario.isJumping()) { // Vérifier si Mario n'est pas déjà en train de sauter
                        mario.jump(mario.getY()); // Mario saute
                    }
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
                return true;
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
        mario.update(); // Mettre à jour l'état de Mario (saut, animation, etc.)
        
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
        //mario.move(dx);
    }
    

      
private void deplacementMario() {
    // Mise à jour de la position horizontale de Mario
    mario.move(dx);

    // Vérifier si Mario atteint la limite de départ
    if (mario.getX() > positionLimiteDepart && !departAtteint) {
        departAtteint = true;
        aDeplaceDroite = true; // Indique que Mario a quitté le point de départ
    }

    // Empêcher Mario de dépasser les bords de l'écran
    if (mario.getX() < 0) {
        mario.move(-mario.getX()); // Ramener Mario à la limite gauche
    }
}

private void deplacementFond() {
    if (departAtteint) {
        // Déplacement du fond uniquement si Mario quitte le point de départ
        xFond1 -= dx;
        xFond2 -= dx;

        // Gestion du défilement infini
        if (xFond1 + largeurFond <= 0) {
            xFond1 = xFond2 + largeurFond;
        }
        if (xFond2 + largeurFond <= 0) {
            xFond2 = xFond1 + largeurFond;
        }
    }
}

@Override
public void resize(int width, int height) {
    // Pas de redimensionnement nécessaire pour cette scène
}

@Override
public void pause() {
    // Pas de gestion spécifique pour la pause
}

@Override
public void resume() {
    // Pas de gestion spécifique pour la reprise
}

@Override
public void hide() {
    // Libérer des ressources si nécessaire
}

@Override
public void dispose() {
    // Libération des ressources utilisées
    imgFond1.dispose();
    imgFond2.dispose();
    imgChateau1.dispose();
    imgDepart.dispose();
    batch.dispose();
    mario.getTexture().dispose();
}

}