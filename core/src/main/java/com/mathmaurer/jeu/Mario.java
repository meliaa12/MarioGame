package com.mathmaurer.jeu;

import com.badlogic.gdx.graphics.Texture;

public class Mario {
    private int x;
    private int y = 0; // Position verticale de Mario

    private Texture texture;
    private boolean movingRight; // Direction du mouvement (vers la droite ou vers la gauche)
    private int compteur; // Compteur pour gérer le changement d'images
    private int frequence; // Fréquence à laquelle l'image change pour l'animation
    private boolean isMoving; // Indicateur si Mario est en mouvement
    private boolean isGrounded = true; // Indique si Mario est au sol
    private boolean isJumping = false; // Indique si Mario est en train de sauter
    private int vy = 0; // Vitesse verticale de Mario


private MarioState currentState = MarioState.REPOS;


private boolean saut; // Indique si Mario saute
private boolean peutDoubleSaut; // Indique si Mario peut effectuer un double saut
private int compteurSaut; // Compteur de la durée du saut
private static final int DUREE_SAUT = 40; // Durée du saut
private static final int HAUTEUR_SAUT = 400; // Hauteur maximale du saut
private static final int PLAFOND = 700; // Hauteur plafond
private int positionSautInitiale; // Position de Mario avant le saut
private int vitesseSaut; // Vitesse du saut


    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        this.movingRight = true; // Initialement Mario regarde à droite
        this.isMoving = false; // Initialement Mario n'est pas en mouvement
        this.saut = false; // Initialement Mario ne saute pas
        this.compteur = 0;
        this.compteurSaut = 0;
        this.frequence = 10; // Vous pouvez ajuster la fréquence pour accélérer ou ralentir l'animation
        updateAnimation(); // Initialiser avec l'image d'arrêt
    }

    public void updateAnimation() {
        String imagePath; // Stocker le chemin de l'image à afficher
    
        // Vérifier si Mario saute
        if (saut) {
            // Animation de saut
            if (movingRight) {
                imagePath = "images/marioSautDroite.png"; // Image de Mario sautant à droite
            } else {
                imagePath = "images/marioSautGauche.png"; // Image de Mario sautant à gauche
            }
        } else if (isMoving) {
            // Si Mario est en mouvement et n'est pas en train de sauter
            if (movingRight) {
                if (compteur / frequence % 2 == 0) {
                    imagePath = "images/marioMarcheDroite.png"; // Mario marchant à droite
                } else {
                    imagePath = "images/marioArretDroite.png"; // Mario arrêté à droite
                }
            } else {
                if (compteur / frequence % 2 == 0) {
                    imagePath = "images/marioMarcheGauche.png"; // Mario marchant à gauche
                } else {
                    imagePath = "images/marioArretGauche.png"; // Mario arrêté à gauche
                }
            }
        } else {
            // Animation d'arrêt (si Mario ne bouge pas)
            if (movingRight) {
                imagePath = "images/marioArretDroite.png"; // Mario arrêté à droite
            } else {
                imagePath = "images/marioArretGauche.png"; // Mario arrêté à gauche
            }
        }
    
        // Si Mario touche le sol (retour à l'animation de marche après un saut)
        if (isGrounded && !saut) {
            if (isMoving) {
                // Retour à l'animation de marche si Mario se déplace
                if (movingRight) {
                    imagePath = "images/marioMarcheDroite.png";
                } else {
                    imagePath = "images/marioMarcheGauche.png";
                }
            } else {
                // Si Mario ne se déplace pas, animation d'arrêt
                if (movingRight) {
                    imagePath = "images/marioArretDroite.png";
                } else {
                    imagePath = "images/marioArretGauche.png";
                }
            }
        }
    
        // Chargement de l'image de l'animation
        try {
            this.texture = new Texture(imagePath);
        } catch (Exception e) {
            System.err.println("Erreur de chargement de l'image : " + imagePath);
            this.texture = new Texture("images/default.png"); // Image par défaut en cas d'erreur
        }
    
        compteur++; // Incrémentation pour l'animation de marche
    }

    
    
    public void climb(boolean up) {
        if (up) {
            this.y += 5; // Monter de 5 unités
        } else {
            this.y -= 5; // Descendre de 5 unités
        }
    
        // Empêcher Mario de dépasser les limites verticales
        if (this.y < getHauteurSol()) {
            this.y = getHauteurSol();
        } else if (this.y > getHauteurPlafond()) {
            this.y = getHauteurPlafond();
        }
    }
    
    public void jump(int hauteurSol) {
        if (!isJumping) { // Vérifie si Mario saute déjà
            isJumping = true;
            this.vy = 15; // Vitesse initiale du saut
        }
    }
    
    

public void update(int hauteurSol) {
    // Gestion du saut
    if (saut) {
        if (compteurSaut <= DUREE_SAUT / 2) {
            if (y < positionSautInitiale + HAUTEUR_SAUT && y < PLAFOND) {
                y += (int) (HAUTEUR_SAUT / (DUREE_SAUT / 2)); // Montée
            }
        } else if (compteurSaut < DUREE_SAUT) {
            y -= (int) (HAUTEUR_SAUT / (DUREE_SAUT / 2)); // Descente
        }

        if (y <= hauteurSol) {
            y = hauteurSol; // Mario touche le sol
            saut = false;
            peutDoubleSaut = false;
        } else if (y >= PLAFOND) {
            y = PLAFOND; // Mario atteint le plafond
            saut = false;
        }

        compteurSaut++;
    }

    // Gestion de l'animation
    updateAnimation();
}

   

// Méthode pour gérer les différentes animations ou états de Mario
public enum MarioState {
    REPOS, MARCHER, SAUTER
}


public void setState(MarioState state) {
    this.currentState = state;
}


    
public int getHauteurSol() {
    return 0; // Retourne la hauteur minimale (sol)
}

public int getHauteurPlafond() {
    return PLAFOND; // Retourne la hauteur maximale (plafond)
}


    // Getter pour obtenir la texture de Mario
    public Texture getTexture() {
        return texture;
    }

    // Getter pour la position X de Mario
    public int getX() {
        return x;
    }

    // Getter pour la position Y de Mario
    public int getY() {
        return y;
    }

    public void move(int dx) {
        this.x += dx;
        isMoving = dx != 0; // Déterminer automatiquement si Mario bouge
        updateAnimation();
    }
    

    // Définir la direction du mouvement de Mario
    public void setDirection(boolean movingRight) {
        this.movingRight = movingRight;
        updateAnimation(); // Mettre à jour l'animation selon la direction
    }

    // Définir si Mario est en mouvement
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
        updateAnimation(); // Mettre à jour l'animation en fonction du mouvement
    }

}