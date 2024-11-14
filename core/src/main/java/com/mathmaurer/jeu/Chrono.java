package com.mathmaurer.jeu;

public class Chrono {

    private final int PAUSE = 3; // temps d'attente entre 2 tours de boucle

    // Vous pouvez déclencher la mise à jour directement dans le Screen
    public void update() {
        try {
            // Simule une attente (qui peut être évitée en libGDX, car la mise à jour est contrôlée par le frame rate)
            Thread.sleep(PAUSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
