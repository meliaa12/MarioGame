package com.mathmaurer.jeu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mathmaurer.personnages.Mario;

public class Clavier implements InputProcessor {

    private Scene scene;  // Référence à la scène pour interagir avec Mario

    public Clavier(Scene scene) {
        this.scene = scene;
    }

    @Override
    public boolean keyDown(int keycode) {
        Mario mario = scene.getMario(); // Accéder à Mario dans la scène

        if (mario.isVivant()) {  // Vérification si Mario est vivant
            // Flèche droite pour marcher à droite
            if (keycode == Input.Keys.RIGHT) {
                if (scene.getxPos() == -1) {
                    scene.setxPos(0); // Réinitialisation de setxPos
                    scene.setxFond1(0); // Réinitialisation de xFond1
                    scene.setxFond2(700); // Réinitialisation de xFond2
                }
                mario.setMarche(true);  // Mario commence à marcher
                mario.setVersDroite(true);  // Mario va vers la droite
                scene.setDx(1);  // Déplacement vers la droite
            }
            // Flèche gauche pour marcher à gauche
            else if (keycode == Input.Keys.LEFT) {
                if (scene.getxPos() == 4431) {
                    scene.setxPos(4430);
                    scene.setxFond1(0);
                    scene.setxFond2(700);
                }
                mario.setMarche(true);  // Mario commence à marcher
                mario.setVersDroite(false);  // Mario va vers la gauche
                scene.setDx(-1);  // Déplacement vers la gauche
            }
            // Touche espace pour sauter
            if (keycode == Input.Keys.SPACE) {
                mario.setSaut(true);  // Mario commence à sauter
            }
        }
        return true;  // L'événement est géré
    }

    @Override
    public boolean keyUp(int keycode) {
        Mario mario = scene.getMario();
        mario.setMarche(false);  // Lorsque la touche est relâchée, Mario arrête de marcher
        scene.setDx(0);  // Arrêt du déplacement
        return true;  // L'événement est géré
    }

    @Override
    public boolean keyTyped(char character) {
        return false;  // Pas utilisé dans cet exemple
    }

    // Méthodes inutilisées pour cet exemple
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;  // Non utilisé
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;  // Non utilisé
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  // Non utilisé
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  // Non utilisé
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;  // Non utilisé
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;  // Vous pouvez retourner false car ce comportement n'est pas nécessaire ici
    }
}
