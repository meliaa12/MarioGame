package com.mathmaurer.personnages;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Objet;

public class Mario extends Personnage {
    private boolean isJumping = false;
    private int vy = 0;
    private static final int GRAVITY = -1; // Gravité qui affecte la vitesse verticale
    private static final int JUMP_STRENGTH = 15; // Force du saut

    private Texture textureMarioSautDroite, textureMarioSautGauche;

    public Mario(float x, float y, float largeur, float hauteur) {
        super(x, y, largeur, hauteur);
        // Charger les textures spécifiques à Mario
        textureMarioSautDroite = new Texture("images/marioSautDroite.png");
        textureMarioSautGauche = new Texture("images/marioSautGauche.png");
    }

    @Override
    public void dessine(SpriteBatch batch) {
        Texture texture;

        if (isJumping) {
            if (versDroite) {
                texture = textureMarioSautDroite;
            } else {
                texture = textureMarioSautGauche;
            }
        } else if (marche) {
            compteur++;
            if (versDroite) {
                if (compteur / 5 % 2 == 0) {
                    texture = textureMarioMarcheDroite;
                } else {
                    texture = textureMarioArretDroite;
                }
            } else {
                if (compteur / 5 % 2 == 0) {
                    texture = textureMarioMarcheGauche;
                } else {
                    texture = textureMarioArretGauche;
                }
            }
        } else {
            if (versDroite) {
                texture = textureMarioArretDroite;
            } else {
                texture = textureMarioArretGauche;
            }
        }

        batch.draw(texture, getX(), getY(), getLargeur(), getHauteur());
    }

    public void update(int hauteurSol, List<Objet> objets) {
        if (isJumping) {
            vy += GRAVITY; // Appliquer la gravité
            setY(getY() + vy); // Mettre à jour la position verticale

            for (Objet objet : objets) {
                if (contactDessous(objet)) {
                    setY(objet.getY() + objet.getHauteur());
                    isJumping = false;
                    vy = 0;
                    break;
                }
            }

            if (getY() <= hauteurSol) {
                setY(hauteurSol); // Mario touche le sol
                isJumping = false;
                vy = 0;
            }
        } else {
            boolean onGround = false;
            for (Objet objet : objets) {
                if (contactDessus(objet)) {
                    setY(objet.getY() + objet.getHauteur());
                    onGround = true;
                    break;
                }
            }
            if (!onGround && getY() > hauteurSol) {
                isJumping = true;
            }
        }
    }

    public void jump(int hauteurSol) {
        if (!isJumping) {
            isJumping = true;
            vy = JUMP_STRENGTH; // Appliquer la force du saut
        }
    }

    public void climb(boolean up, int hauteurSol, int hauteurPlafond) {
        if (up) {
            setY(getY() + 5);
        } else {
            setY(getY() - 5);
        }

        if (getY() < hauteurSol) {
            setY(hauteurSol);
        } else if (getY() > hauteurPlafond) {
            setY(hauteurPlafond);
        }
    }
}