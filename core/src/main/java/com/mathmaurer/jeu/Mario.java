package com.mathmaurer.jeu;

import com.badlogic.gdx.graphics.Texture;

public class Mario {
    private int x; // Position horizontale de Mario
    private int y; // Position verticale de Mario

    private Texture texture; // Texture actuelle de Mario (pour l'animation)
    private boolean movingRight; // Direction du mouvement : true = droite, false = gauche
    private boolean isMoving; // Indicate si Mario est en mouvement
    private boolean isJumping; // Indique si Mario est en train de sauter
    private boolean isGrounded; // Indique si Mario est au sol
    private int compteur; // Compteur pour gérer les animations
    private int frequence; // Fréquence de changement des animations
    private int vy; // Vitesse verticale lors des sauts

    private boolean saut; // Indique si Mario saute
    private boolean peutDoubleSaut; // Indique si un double saut est possible
    private int compteurSaut; // Durée actuelle du saut
    private static final int DUREE_SAUT = 90; // Durée maximale du saut
    private static final int HAUTEUR_SAUT = 200; // Hauteur maximale atteinte par le saut
    private static final int PLAFOND = 700; // Hauteur maximale (plafond)

    private static final int GROUND_HEIGHT = 0; // Niveau du sol, c'est-à-dire la hauteur de base

    private int jumpVelocity;
    private int gravity;
    private int maxJumpHeight;
    private int dx;


    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        this.movingRight = true;
        this.isMoving = false;
        this.isGrounded = true;
        this.isJumping = false;
        this.saut = false;
        this.compteur = 0;
        this.frequence = 10;
        dx = 0;

        jumpVelocity = 20; // Vitesse initiale de saut
        gravity = -1; // Valeur négative pour simuler la gravité
        maxJumpHeight = 100; // Hauteur maximale du saut
        updateAnimation(); // Initialiser l'animation par défaut
    }

    public void updateAnimation() {
        String imagePath;
        
        // Gestion des animations selon les états
        if (saut) {
            imagePath = movingRight ? "images/marioSautDroite.png" : "images/marioSautGauche.png";
        } else if (isMoving) {
            imagePath = (compteur / frequence % 2 == 0) 
                ? (movingRight ? "images/marioMarcheDroite.png" : "images/marioMarcheGauche.png")
                : (movingRight ? "images/marioArretDroite.png" : "images/marioArretGauche.png");
        } else {
            imagePath = movingRight ? "images/marioArretDroite.png" : "images/marioArretGauche.png";
        }

        // Charger la texture
        try {
            this.texture = new Texture(imagePath);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + imagePath);
            this.texture = new Texture("images/default.png");
        }

        compteur++; // Incrémenter pour les animations
    }

   
    public void climb(boolean up) {
        if (up) {
            this.y += 5;
        } else {
            this.y -= 5;
        }

        // Empêcher Mario de dépasser les limites
        if (this.y < getHauteurSol()) this.y = getHauteurSol();
        if (this.y > getHauteurPlafond()) this.y = getHauteurPlafond();
    }

    
    public void update() {
        if (isJumping) {
            y += jumpVelocity;
            jumpVelocity += gravity; // Appliquer la gravité

            // Vérifier si Mario revient au sol
            if (y <= GROUND_HEIGHT) {
                y = GROUND_HEIGHT;
                isJumping = false;
                jumpVelocity = 20; // Réinitialiser la vitesse de saut
            }
        }

        // Gérer le déplacement horizontal
        x += dx;

        // Mettre à jour l'état du mouvement
        updateAnimation(); 
        }

    public void jump(int hauteurSol) {
        if (!isJumping) { // Vérifier que Mario ne saute pas déjà
            isJumping = true;
            y = hauteurSol; // Lancer le saut depuis la hauteur actuelle (le sol)
            jumpVelocity = 20; // Initialiser la vitesse de saut
        }
    }

    public void move(int dx) {
        this.x += dx;
        isMoving = dx != 0;
        updateAnimation();
    }

    public void setDirection(boolean movingRight) {
        this.movingRight = movingRight;
        updateAnimation();
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
        updateAnimation();
    }

    public int getHauteurSol() {
        return 0;
    }

    public int getHauteurPlafond() {
        return PLAFOND;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isJumping() {
        return isJumping;
    }
}
